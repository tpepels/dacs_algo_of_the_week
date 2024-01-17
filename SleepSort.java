// A thread is like a separate path of execution that can run concurrently with other threads. 
// Think of it as a worker assigned a specific task in a factory. 
// In a program, multiple threads can execute different parts of the code simultaneously.
class MyThread extends Thread {
    int value;

    public MyThread(int value) {
        this.value = value;
    }

    // This method is executed when the thread starts
    public void run() {
        try {
            // Thread.sleep causes the current thread to pause for the specified number of
            // milliseconds.
            // Here, the duration of the pause is proportional to the value of the element.
            // This is the essence of Sleep Sort: larger numbers sleep longer.
            Thread.sleep(value * 10l); // Multiplied by 10 (long) to make it quick yet observable

            // After sleeping, print the value. This prints numbers in ascending order of
            // sleep time.
            System.out.println(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SleepSort {
    public static void main(String[] args) {
        int[] numbers = { 9, 8, 7, 6, 18, 20, 10 };
        MyThread[] threads = new MyThread[numbers.length];

        // Creating and starting a thread for each element in the array
        for (int i = 0; i < numbers.length; i++) {
            threads[i] = new MyThread(numbers[i]);
            threads[i].start(); // Start the thread
        }

        // Wait for all threads to finish. This ensures that the program waits for all
        // sorting to complete.
        for (MyThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
