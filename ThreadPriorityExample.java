class PriorityThread extends Thread {
    private int priority;

    public PriorityThread(String name, int priority) {
        super(name);
        this.priority = priority;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        for (int i = 0; i < 5; i++) {
            System.out.println(getName() + " 실행 중 (우선순위: " + getPriority() + ")");
        }
    }
}

public class ThreadPriorityExample {
    public static void main(String[] args) {
        PriorityThread t1 = new PriorityThread("LowPriority", Thread.MIN_PRIORITY);
        PriorityThread t2 = new PriorityThread("HighPriority", Thread.MAX_PRIORITY);

        t1.start();
        t2.start();
    }
}