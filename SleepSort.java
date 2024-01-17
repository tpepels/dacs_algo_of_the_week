class MyThread extends Thread {
    int id;
    String s;

    public MyThread(int value) {
        this.id = value;
        this.start();
    }

    public void run() {
        try {
            Thread.sleep(id);
            System.out.println(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SleepSort {
    public static void main(String[] args) {
        int a[] = { 9, 8, 7, 6, 18, 20, 10 };
        for (int i = 0; i < a.length; i++) {
            new MyThread(a[i]);
        }
    }
}