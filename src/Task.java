import java.util.Random;

public class Task implements Runnable {

    private static final Random rand = new Random();

    private String message;
    private final int interruptTime;
    private final int bound;

    public Task() {
        interruptTime = rand.nextInt(1000,2000);
        bound = rand.nextInt(100,200);
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= bound; i++) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                message = (Thread.currentThread().getName() + ": " + i);
                Thread.sleep(interruptTime);
                Thread.yield();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " przerwany.");
        }
        System.out.println(Thread.currentThread().getName() + " zakończył działanie");
    }

    public String getMessage() {
        return message;
    }
}