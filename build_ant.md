# 🐜 Ant 빌드로 JAR 만들기

## 상황

- A 프로젝트에서 로직을 개발하다가 Exception을 던져야 하는 상황
- 예외 코드에 대한 세부 설정(httpStatus, message)은 JAR 형식으로 B 프로젝트의 yml 파일에서 읽어 들여오고 있었다.
- 해당 yml파일에서 메타에 맞지 않은 용어를 사용하고 있었고, 이를 수정하기 위해서 B 프로젝트에서 작업하고 빌드하여 생성된 jar 파일을 A 프로젝트로 옮겨오려고 계획
- build 를 하려고 봤더니 gradle 파일이 아니라 xml 파일이었고 해당 파일을 빌드 하기 위해서는 Ant로 빌드를 해야 한다고 함

그래서 아래의 글은 이와 같은 상황에서 찾아보고 시도하며 알게 된 것과 해결했던 에러를 기록한 것이다.

# Ant build

build를 하려고 gradle을 찾고 있었는데 아무리 봐도 build.xml만 존재했다. 찾아보니 Ant를 사용해서 빌드를 해야 한다고 했다.

Ant는 Gradle이나 Maven과는 다르게 XML 기반의 빌드 스크립트를 사용한다고 한다.

## 🔨 인텔리제이에서 Ant 빌드 설정하는 방법

1. **Ant 빌드 도구 활성화**
    - `View` → `Tool Windows` → `Ant Build`를 선택
    - 또는 `Ctrl + Shift + A` (`Cmd + Shift + A` on Mac) 를 누르고 "Ant Build" 검색 후 실행
2. **`build.xml` 파일 추가**
    - `Ant Build` 창이 열리면 왼쪽 상단의 **➕**(Add) 버튼 클릭
    - `build.xml` 파일이 있는 프로젝트 루트 폴더에서 선택
3. **JAR 빌드 실행**
    - `Ant Build` 창에서 `jar` 또는 `build` 타겟을 선택 후 실행 버튼(▶) 클릭

---

## 📄 build.xml 해석하기

빌드를 해서 jar를 만들었지만 이 jar가 어디에 생성되었는지, 무엇을 바탕으로 빌드를 하는지 아무것도 모르고 있었다.

그래서 build.xml을 해석해보기로 했다.

### 🔍 **JAR 파일 생성 위치 찾는 법**

```xml
<target name="jar" depends="compile">
    <mkdir dir="dist"/>
    <jar destfile="dist/myproject.jar" basedir="build/classes"/>
</target>
```

- 먼저 `jar` 타겟을 찾는다.
- `mkdir` 태그를 보면 알 수 있듯 `dist` 라는 디렉토리를 만들고
- `basedir`에 정의되어 있는 `build/classes` 폴더 아래에 있는 코드를
- `destfile`에 정의되어 있는 `dist` 폴더 아래에 `myproject.jar` 라는 이름으로 파일이 만들어질 것이다.

### 📌 `todir`과 `basedir`이 있는 경우

```xml
<target name="jar" depends="compile">
    <mkdir dir="output"/>
    <copy todir="output">
        <fileset dir="build/classes"/>
    </copy>
    <jar destfile="output/app.jar" basedir="output"/>
</target>
```

- `todir="output"` → `output/` 폴더로 `"build/classes"` 에 있는 클래스 파일을복사
- `basedir="output"` → `output/`을 기준으로 JAR 생성
- 결과: `output/app.jar` 파일이 생성됨

---

그런데 내 경우에는 `${}` 문법을 통해서 변수명을 정의하고 있었다.

## 🐜 Ant의 property 참조 방식

`${변수명}` 에서 해당 변수는

1. `<property>` 태그에서 정의하거나,
2. 외부 파일/환경 변수에 정의할 수도 있다.

🔍 **1. `build.xml` 내부에서 정의된 경우**

```xml
<project name="MyProject" default="jar" basedir=".">
    <property name="projectName" value="myproject_svc"/>
    <property name="dist" value="target/dist"/>
    <property name="dest" value="target/dest"/>
</project>
```

name 속성으로 파일 내부에서는 사용하지만 실제 값은 value 이다.

🔍 **2. 외부 `build.properties` 파일에서 가져오는 경우**

`build.xml` 내부에서 속성을 정의하지 않았다면, **외부 파일에서 로드**될 수도 있어.

`build.xml` 에서 이와 같이 정의하고 있다면 `build.properties` 에 속성을 정의해둔 것이다.

```xml
<property file="build.properties"/>
```

`build.properties`

```
projectName=myproject_svc
dist=target/dist
dest=target/dest
```

### ✏️ 내 프로젝트의 `build.xml` 을 살펴보며 문법 정리하기

`jar` 타겟

```xml
<target name="jar" depends="clean, compile">
	<copy todir="${dist}">
		<fileset dir="src">
			<include name="**/*.yml" />
			<include name="**/*.xml" />
		</fileset>
	</copy>
	<jar basedir="${dist}" destfile="${dest}/${projectName}.jar" />
</target>
```

- `<target name="jar" depends="clean, compile">` : `clean`, `compile` 타겟을 먼저 실행 후 `jar` 타겟을 실행하겠다는 뜻이다.
    - `clean` → 기존 빌드 결과물을 삭제 (예: `dist` 폴더나 `.class` 파일 삭제)
    - `compile` → 소스 코드를 컴파일하여 `.class` 파일 생성
- `<copy todir="${dist}">`: `${dist}` 디렉토리에 아래의 내용을 `copy` 하겠다는 뜻이다.
- `<fileset dir="src">`: `src` 디렉토리에 있는 파일 모두가 그 대상이라는 뜻이다.
- `<include name="**/*.yml" />`, `<include name="**/*.xml" />` 파일도 포함하겠다는 뜻이다.

`clean` 타겟

```xml
<target name="clean">
	<delete dir="${dist}" />
	<delete dir="${dest}" />
</target>
```

- `<delete dir="${dist}" />`:  `${dist}` 디렉토리 삭제

`prepare` 타겟

```xml
<target name="clean">
	<mkdir dir="${dist}" />
	<mkdir dir="${dest}" />
</target>
```

- `<mkdir dir="${dist}" />`: `${dist}` 디렉토리 생성

`compile` 타겟

```xml
<target name="compile" depends="prepare">
	<javac srcdir="src" destdir="${dist}" source="17" encoding="UTF-8" debug="true">
		<classpath refid="op_userclass_lib" />
	</javac>
</target>
```

- `javac`는 `java compiler`의 준말이다. 기억하자
- `build.xml`에서 사용하는 `javac`는 **Ant 빌드 도구**의 일부로, Ant가 자동으로 Java 컴파일 작업을 처리할 수 있게 한다.
- `<classpath refid="op_userclass_lib" />`

  이 부분은 **컴파일 시 사용될 클래스패스**를 설정하는 부분

  `refid="op_userclass_lib"`는 클래스패스를 `op_userclass_lib`라는 아이디로 지정된 클래스패스 설정을 참조한다는 의미임 → 그래서 아래처럼 path 태그로 정의를 해둔 게 분명 있을 것임


`path` 태그

```xml
<path id="op_userclass_lib">
    <fileset dir="libs">
        <include name="**/*.jar" />
    </fileset>
</path>
```

즉, 빌드 과정은 이렇게 진행:

1️⃣ `clean` 실행 → 기존 디렉토리 삭제

2️⃣ `prepare` 실행 → 디렉토리 생성

3️⃣ `compile` 실행 → 소스 코드 컴파일 (`.java` → `.class`)

4️⃣ `jar` 실행 → 컴파일된 `.class` 파일 및 설정 파일들을 `.jar`로 묶음