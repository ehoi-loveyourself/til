import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolOptimization {
    public static void main(String[] args) {
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU 코어 개수: " + coreCount);

        // CPU 작업 위주라면 coreCount + 1, I/O 작업 위주라면 coreCount * 2
        ExecutorService executorService = Executors.newFixedThreadPool(coreCount + 1);

        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executorService.execute(() -> {
                System.out.println("작업 " + taskId + " 실행 중! - " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // 1초 동안 작업 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}