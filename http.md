# HTTP

##  2025.07.16 배운 내용
1. HTTP 헤더의 값은 문자열이다.
   - 상황: Request의 조건을 클라이언트가 직접 지정할 수 있는 작업을 하던 중에, value의 type을 String, Number, Boolean으로 선택할 수 있도록 하라는 지시가 있었다. 하지만 요청이 들어왔을 때 클라이언트가 지정한 type에 맞게 요청을 했는지 알 수 없었다.
   - 이유: HTTP 스펙상에서는 Request Header의 값은 항상 문자열로 취급되기 때문이다.
     ```
     A field value is defined as a sequence of characters, not a strongly typed value.
     ```
     즉, 모두 문자열인 것이다.
     ```http
     GET /example HTTP/1.1
     Host: example.com
     X-Custom-Boolean: true
     X-Custom-Number: 123
     ```
     위의 예시에서 헤더 값들은 모두 문자열을 나타내지, number이나 boolean 같은 타입을 구분할 수 없다.
     - `X-Custom-Boolean`: `"true` (문자열)
     - `X-Custom-Number`: `"123` (문자열)
2. HTTP 헤더는 대소문자를 구분하지 않는다.
   - HTTP/1.1 명세인 RFC 7230에는 아래와 같이 나와있다
     ```
     Each header field consists of a case-insensitive field name followed by a colon(":") and the field value.
     ```
     즉, 다음의 어떤 경우라도 같은 헤더로 인식된다.
     ```
     Content-Type: application/json
     content-type: application/json
     CoNTeNt-tYPe: application/json
     ```
3. 그렇지만 헤더의 값(value)는 대소문자를 구분하는 경우가 있다.
   예를 들어 `Authorization: Bearer abc123`에서 `Bearer`와 `bearer`는 의미가 달라질 수 있다.