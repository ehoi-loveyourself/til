import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
        // 스레드 풀 생성: 3개의 스레드만 실행 가능
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 여러 작업을 제출
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                try {
                    System.out.println("작업 " + taskId + " 시작! - " + Thread.currentThread().getName());
                    Thread.sleep(1000); // 1초 동안 작업 대기
                    System.out.println("작업 " + taskId + " 종료! - " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 모든 작업이 끝날 때까지 기다리기
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("모든 작업 종료!");
    }
}