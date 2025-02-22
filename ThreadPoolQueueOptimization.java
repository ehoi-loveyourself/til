import java.util.concurrent.*;

public class ThreadPoolQueueOptimization {
    public static void main(String[] args) {
        int coreCount = Runtime.getRuntime().availableProcessors();

        // 최대 5개의 작업만 대기할 수 있는 큐
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                coreCount, coreCount * 2, 1, TimeUnit.SECONDS, queue
        );

        for (int i = 0; i < 20; i++) { // 15개의 작업 추가
            final int taskId = i;
            try {
                executor.submit(() -> {
                    System.out.println("작업 " + taskId + " 실행 중! - " + Thread.currentThread().getName());
                });
                try {
                    Thread.sleep(2000); // 1초 동안 작업 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (RejectedExecutionException e) {
                System.out.println("작업 " + taskId + "이(가) 큐가 가득 차서 거부됨!");
            }
        }

        executor.shutdown();
    }
}