# 🤖 프로세스 & 쓰레드

## 공부하는 이유

프로세스와 쓰레드는 컴퓨터의 기본 동작을 이해하는 데에 매우 중요함.

멀티태스킹을 비롯해서 현대 운영체제를 핵심 개념이기 때문에 다른 개념을 학습하는 데에도  도움이 됨.

## 공부할 내용

- 프로세스, 쓰레드, 멀티쓰레드, 프로세스와 쓰레드의 차이점
- **Context Switching, Concurrency, Parallelism**
- 멀티 쓰레드 환경에서 발생할 수 있는 문제점

이런 부분을 공부하면 CPU Scheduling을 할 때와 동기화 개념을 배울 때 유리하다.

## 프로세스

- **운영체제(OS)로부터** 자원을 할당받아 독립적으로 실행되는 단위
- CPU, 메모리, 파일 핸들 등을 포함하고 있어서 **다른 프로세스와 자원을 공유하지 않음**

예제:

- 게임, 음악 플레이어, 웹 브라우저, 코드 편집기 등 각 애플리케이션이 하나의 독립된 프로세스
- 서로 독립적으로 실행되며, 한 프로세스가 종료되어도 다른 프로세스에 영향을 주지 않음

## 쓰레드

- 프로세스 내부에서 실행되는 **작업** 단위
- 하나의 프로세스 안에 여러 쓰레드가 존재할 수 있고, **같은 프로세스의 자원을 공유**함

예제:

- 웹 브라우저에서 여러 개의 탭이 각각 쓰레드로 동작할 수 있다.
    - 일부 브라우저에서는 각 탭을 별도의 프로세스로 관리하는 경우도 있다.
- 게임에서는 렌더링, 사운드 처리, 네트워크 통신을 각각 다른 쓰레드가 수행할 수 있다.