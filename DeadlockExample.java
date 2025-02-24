import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(() -> resource.method1());
        Thread t2 = new Thread(() -> resource.method2());

        t1.start();
        t2.start();
    }
}

class SharedResource {
    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    public void method1() {
        try {
            if (lock1.tryLock(3, TimeUnit.SECONDS)) {
                System.out.println("method1에서 lock1 획득");

                if (lock2.tryLock(3, TimeUnit.SECONDS)) {
                    System.out.println("method1에서 lock2 획득");
                    lock2.unlock();
                }
                lock1.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method2() {
        try {
            if (lock2.tryLock(3, TimeUnit.SECONDS)) {
                System.out.println("method2에서 lock2 획득");

                if (lock1.tryLock(3, TimeUnit.SECONDS)) {
                    System.out.println("method2에서 lock1 획득");
                    lock1.unlock();
                }
                lock2.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}