import java.util.*;

public class ThreadsManager {

    private record TaskEntry(Task task, Thread thread) { }

    private final List<TaskEntry> tasks = new ArrayList<>();

    public void addTask() {
        Task task = new Task();
        Thread thread = new Thread(task, "Task-" + task.hashCode());
        thread.start();
        tasks.add(new TaskEntry(task, thread));
        System.out.println("Dodano taska: " + thread.getId());
    }

    public void addMultipleTasks(int count) {
        for (int i = 0; i < count; i++) {
            addTask();
        }
    }

    public void stopTask(long threadId) {
        for (TaskEntry entry : tasks) {
            if (entry.thread.getId() == threadId) {
                entry.thread.interrupt();
                System.out.println("Zatrzymano taska: " + threadId);
                return;
            }
        }
        System.out.println("Nie znaleziono taska o ID: " + threadId);
    }

    public void stopAllTasks() {
        for (TaskEntry entry : tasks) {
            entry.thread.interrupt();
        }
        System.out.println("Zatrzymano wszystkie taski");
    }

    public int getActiveThreadCount() {
        return Thread.activeCount();
    }

    public void listActiveTasks() {
        Thread[] threads = new Thread[Thread.activeCount()];
        int count = Thread.enumerate(threads);

        System.out.println("\n--- Aktywne Taski ---");
        for (int i = 0; i < count; i++) {
            Thread t = threads[i];
            for (TaskEntry entry : tasks) {
                if (entry.thread.getId() == t.getId()) {
                    System.out.println("Task-" + t.getId() + " | Wiadomość: " + entry.task.getMessage());
                }
            }
        }
    }

    public void listAllTasks() {
        System.out.println("\n--- Wszystkie Taski ---");
        for (TaskEntry entry : tasks) {
            System.out.println("Task-" + entry.thread.getId() + " | Status: " + entry.thread.getState());
        }
    }

    public void taskDetails(long threadId) {
        for (TaskEntry entry : tasks) {
            if (entry.thread.getId() == threadId) {
                System.out.println("\n--- Szczegóły Taska ---");
                System.out.println("ID: " + entry.thread.getId());
                System.out.println("Nazwa: " + entry.thread.getName());
                System.out.println("Status: " + entry.thread.getState());
                System.out.println("Priorytet: " + entry.thread.getPriority());
                System.out.println("Wątek jest żywy?: " + entry.thread.isAlive());
                System.out.println("Wynik Taska: " + entry.task.getResult());
                System.out.println("Wiadomość Taska: " + entry.task.getMessage());
                return;
            }
        }
        System.out.println("Nie znaleziono taska o ID: " + threadId);
    }
}
