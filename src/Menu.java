import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private final ThreadsManager threadsManager;
    private final Scanner scanner;

    public Menu(ThreadsManager threadsManager) {
        this.threadsManager = threadsManager;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n" + "-".repeat(15) + " Menu " + "-".repeat(15));
            System.out.println("1. Dodaj pojedyncze zadanie");
            System.out.println("2. Dodaj wiele zadań");
            System.out.println("3. Zatrzymaj zadanie po ID");
            System.out.println("4. Zatrzymaj wszystkie zadania");
            System.out.println("5. Pokaż wszystkie zadania");
            System.out.println("6. Sprawdź status zadania");
            System.out.println("7. Sprawdź status wszystkich zadań");
            System.out.println("8. Wyjście");
            System.out.println("\n" + "-".repeat(36));
            System.out.print("Wybierz opcję: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> handleAddTask();
                case 2 -> handleAddMultipleTasks();
                case 3 -> handleStopTask();
                case 4 -> handleStopAllTasks();
                case 5 -> threadsManager.listTasks();
                case 6 -> checkTaskStatus();
                case 7 -> checkAllTasksStatus();
                case 8 -> {
                    handleStopAllTasks();
                    System.out.println("Zamykanie programu");
                    return;
                }
                default -> System.out.println("Nieprawidłowa opcja!");
            }
        }
    }

    private void handleAddTask() {
        int taskId = threadsManager.addTask();
        System.out.println("Dodano zadanie o ID: " + taskId);
    }

    private void handleAddMultipleTasks() {
        System.out.print("Podaj liczbę zadań do dodania: ");
        int count = getIntInput();
        if (count > 0) {
            List<Integer> ids = threadsManager.addMultipleTasks(count);
            System.out.println("Utworzono zadania o ID: " + ids);
        } else {
            System.out.println("Nieprawidłowa liczba!");
        }
    }

    private void handleStopTask() {
        System.out.print("Podaj ID zadania: ");
        int id = getIntInput();
        threadsManager.stopTask(id);
        System.out.println("Zatrzymano zadanie " + id);
    }

    private void handleStopAllTasks() {
        threadsManager.stopAllTasks();
        System.out.println("Zatrzymano wszystkie zadania");
    }

    private void checkTaskStatus() {
        System.out.print("Podaj ID zadania: ");
        int id = getIntInput();
        String status = threadsManager.getTaskStatus(id);
        System.out.println("Status zadania " + id + ": " + status);
    }

    private void checkAllTasksStatus() {
        Map<Integer, String> statuses = threadsManager.getAllTasksStatus();
        System.out.println("\nStatusy wszystkich zadań:");
        statuses.forEach((id, status) ->
                System.out.printf("Zadanie %d: %s%n", id, status));
        System.out.println("--------------------------");
    }

    private int getIntInput() {
        try {
            return scanner.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowe dane!");
            return -1;
        }
    }

    public static void main(String[] args) {
        ThreadsManager manager = new ThreadsManager();
        Menu menu = new Menu(manager);
        menu.displayMenu();
    }
}