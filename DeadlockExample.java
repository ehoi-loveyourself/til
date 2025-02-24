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
        lock1.lock();
        System.out.println("method1에서 lock1 획득");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock2.lock();
        System.out.println("method1에서 lock2 획득");
        lock2.unlock();
        lock1.unlock();
    }

    public void method2() {
        lock2.lock();
        System.out.println("method2에서 lock2 획득");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock1.lock();
        System.out.println("method2에서 lock1 획득");
        lock1.unlock();
        lock2.unlock();
    }
}