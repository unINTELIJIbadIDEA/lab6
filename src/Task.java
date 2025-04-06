import java.util.Random;

public class Task implements Runnable {

    private static final Random rand = new Random();

    private String message;
    private final int sleepTime;
    private final int bound;
    private String result = "";

    public Task() {
        sleepTime = rand.nextInt(1000,2000);
        bound = rand.nextInt(100,200);
    }

    @Override
    public void run() {
        int i = 0;

        try {

            while (i <= bound) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                message = Integer.toString(i);
                Thread.sleep(sleepTime);
                Thread.yield();

                i++;
            }

            System.out.println(Thread.currentThread().getName() + " zakończył działanie");
            result = Thread.currentThread().getName() + " - sukces. Wynik: " + i;

        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " przerwany.");
            result = Thread.currentThread().getName() + " - przerwany. Wynik: " + i;
        }

    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }
}