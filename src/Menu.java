import java.util.Scanner;

public class Menu {
    private final ThreadsManager threadsManager;
    private final Scanner scanner;

    public Menu(ThreadsManager threadsManager) {
        this.threadsManager = threadsManager;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() throws InterruptedException {
        while (true) {

            Thread.sleep(10);

            System.out.println("\n" + "-".repeat(15) + " Menu " + "-".repeat(15));
            System.out.println("1. Dodaj pojedynczy task");
            System.out.println("2. Dodaj wiele tasków");
            System.out.println("3. Zatrzymaj task");
            System.out.println("4. Zatrzymaj wszystkie taski");
            System.out.println("5. Liczba aktywnych wątków");
            System.out.println("6. Wypisz aktywne taski");
            System.out.println("7. Wypisz wszystkie taski");
            System.out.println("8. Szczegóły taska");
            System.out.println("9. Wyjście");
            System.out.println("-".repeat(38));
            System.out.print("Wybierz opcję: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> threadsManager.addTask();
                case 2 -> handleAddMultipleTasks();
                case 3 -> handleStopTask();
                case 4 -> threadsManager.stopAllTasks();
                case 5 -> System.out.println("Liczba aktywnych wątków: " + threadsManager.getActiveThreadCount());
                case 6 -> threadsManager.listActiveTasks();
                case 7 -> threadsManager.listAllTasks();
                case 8 -> handleTaskDetails();
                case 9 -> {
                    threadsManager.stopAllTasks();
                    System.out.println("Zamykanie programu...");
                    return;
                }
                default -> System.out.println("Nieprawidłowa opcja!");
            }
        }
    }

    private void handleAddMultipleTasks() {
        System.out.print("Podaj liczbę tasków do dodania: ");
        int count = getIntInput();
        if (count > 0) {
            threadsManager.addMultipleTasks(count);
        } else {
            System.out.println("Nieprawidłowa liczba!");
        }
    }

    private void handleStopTask() {
        System.out.print("Podaj ID taska: ");
        long id = getLongInput();
        threadsManager.stopTask(id);
    }

    private void handleTaskDetails() {
        System.out.print("Podaj ID taska: ");
        long id = getLongInput();
        threadsManager.taskDetails(id);
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Nieprawidłowe dane! Podaj liczbę.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private long getLongInput() {
        while (!scanner.hasNextLong()) {
            System.out.println("Nieprawidłowe dane! Podaj liczbę.");
            scanner.next();
        }
        return scanner.nextLong();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadsManager manager = new ThreadsManager();
        Menu menu = new Menu(manager);
        menu.displayMenu();
    }
}
