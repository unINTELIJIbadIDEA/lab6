import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadsManager {

    private record TaskEntry(Task task, Thread thread) { }

    private final AtomicInteger nextTaskId = new AtomicInteger(1);
    private final Map<Integer, TaskEntry> tasks = new ConcurrentHashMap<>();

    public int addTask() {
        int taskId = nextTaskId.getAndIncrement();
        Task task = new Task();
        Thread thread = new Thread(task, "Task-" + taskId);
        thread.start();
        tasks.put(taskId, new TaskEntry(task, thread));
        return taskId;
    }

    public List<Integer> addMultipleTasks(int count) {
        List<Integer> createdIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            createdIds.add(addTask());
        }
        return createdIds;
    }

    public void stopTask(int taskId) {
        TaskEntry entry = tasks.get(taskId);
        if (entry != null) {
            entry.thread.interrupt();
        }
    }

    public void stopAllTasks() {
        tasks.values().forEach(entry -> entry.thread.interrupt());
    }

    public void listTasks() {
        System.out.println("\nID | Status      | Ostatnia wiadomość");
        System.out.println("---------------------------------------");

        tasks.forEach((id, entry) -> {
            String isAlive = entry.thread.isAlive() ? "Działa   " : "Zakończone";
            String message = entry.task.getMessage();
            System.out.printf("%-2d | %-10s | %s%n", id, isAlive, message);
        });
    }

    public String getTaskStatus(int taskId) {
        TaskEntry entry = tasks.get(taskId);

        if (entry == null)
            return "NIE ISTNIEJE";

        return entry.thread.getState().toString();
    }

    public Map<Integer, String> getAllTasksStatus() {
        Map<Integer, String> statuses = new LinkedHashMap<>();

        tasks.forEach((id, entry) -> {
            statuses.put(id, entry.thread.getState().toString());
        });

        return statuses;
    }
}