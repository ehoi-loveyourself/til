# nginx

## 2025.07.15 배운 내용
Web에서 API를 호출했는데 404 Not Found 에러가 뜸.
분명 다른 uri들은 호출이 잘 되고 있는 상태이고, 서버 주소도 동일한데 해당 uri만 에러가 나는 것을 이상하게 생각함.

WebSecurityConfig에 해당 uri로 시작하는 요청을 받을 수 있도록 추가도 해 둔 상태.

알고 보니 nginx.conf 파일에서도 uri의 prefix를 설정하여 요청을 받을 수 있도록 설정을 해두어야 했던 것. 이와 관련된 개념을 알지 못하니, 추론 조차 하지 못했다.

아는 게 많이 없는 것 같아서 조바심도 나고 슬프지만, 극복해보자!