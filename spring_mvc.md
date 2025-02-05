## 📎 `@RestController`와 `@Controller`의 차이점

🧠 며칠 전에 배워놓고도 내 입으로 직접 이야기하라고 하면 어떻게 말하면 좋을까? 라는 생각이 들어 정리


🗣️ 
`@Controller` 대신에 `@RestController` 어노테이션을 사용하면, 
해당 컨트롤러에 모두 `@ResponseBody`가 적용되는 효과가 있다. 
따라서 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 데이터를 입력한다.(이게 `@ResponseBody`의 기능) 

이름 그대로 Rest API(HTTP API)를 만들 때 사용하는 컨트롤러이다.

`@ResponseBody` 는 클래스 레벨에 두면 전체 메서드에 적용되는데,

`@Controller` + `@ResponseBody` = `@RestController` 라고 생각하면 된다.

`@RestController` 어노테이션 안을 살펴보면 `@ResponseBody` 가 적용되어 있다.


![@RestController](./images/RestController.png)

