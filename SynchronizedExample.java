import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Thread t1 = new Thread(() -> account.withdraw("스레드1", 70));
        Thread t2 = new Thread(() -> account.withdraw("스레드2", 70));

        t1.start();
        t2.start();
    }
}

class BankAccount {
    private int balance = 100;
    private ReentrantLock lock = new ReentrantLock(); // 락 선언

    public void withdraw(String name, int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                System.out.println(name + " 출금 시도: " + amount);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance -= amount;
                System.out.println(name + " 출금 완료! 남은 잔액: " + balance);
            } else {
                System.out.println(name + " 출금 실패! 잔액 부족.");
            }
        } finally {
            lock.unlock(); // 반드시 락 해제
        }
    }
}