import java.util.concurrent.atomic.AtomicInteger;

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
    private AtomicInteger balance = new AtomicInteger(100);

    public void withdraw(String name, int amount) {
        if (balance.get() >= amount) {
            System.out.println(name + " 출금 시도: " + amount);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance.addAndGet(-amount);
            System.out.println(name + " 출금 완료! 남은 잔액: " + balance);
        } else {
            System.out.println(name + " 출금 실패! 잔액 부족.");
        }
    }
}