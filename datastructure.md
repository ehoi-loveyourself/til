# Set 
## add() 메서드
Set 자료구조는 중복을 허용하지 않는다는 특징이 있다.

그래서 add() 메서드를 이용해서 set에 값을 넣고자 했을 때, 이미 중복된 값이 있다면 false를 리턴하고 중복된 값이 없어서 값을 넣을 수 있다면 true를 리턴한다.

알고리즘 문제를 풀고 나서 chatGPT에게 개선 요청을 했는데, 이 메서드를 활용해서 코드를 더욱 깔끔하게 만들어 주는 것을 보고, 메서드를 잘 알고 활용하는 능력도 중요하다고 생각했다.

순열이면 1, 아니면 0을 리턴하는 문제다. [문제 링크](https://app.codility.com/programmers/lessons/4-counting_elements/perm_check/start/)

풀이 코드 
```java
import java.util.*;

class Solution {
    public int solution(int[] A) {
        // N을 기준으로 체크
        int N = A.length;

        // 중복 확인을 위한 Set
        Set<Integer> set = new HashSet<>();

        for (int num : A) {
            // 1부터 N 범위 이외의 숫자가 있는지 확인
            if (num < 1 || num > N) {
                return 0; // 순열이 아님
            }

            // 중복 확인
            if (!set.add(num)) {
                return 0; // 중복된 값이 존재
            }
        }

        // 모든 조건을 만족하면 순열
        return 1;
    }
}
```