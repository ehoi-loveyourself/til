class MyThread extends Thread {
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("쓰레드 " + name + " 시작!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("쓰레드 " + name + " 종료!");
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("1번");
        MyThread t2 = new MyThread("2번");

        t1.start();
        t2.start();

        try {
            t1.join();;
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("모든 쓰레드 종료!");
    }
}