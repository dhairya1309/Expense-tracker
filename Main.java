
import java.io.*;
import java.util.*;

// User Authentication System
class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

class UserManager {

    private static final String FILE_PATH = "users.txt";
    private static Map<String, User> users = new HashMap<>();

    static {
        loadUsers();
    }

    public static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], new User(parts[0], parts[1]));
                }
            }
        } catch (IOException ignored) {
        }
    }

    public static void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(user.getUsername() + "," + user.authenticate(user.getUsername()) + "\n");
        } catch (IOException ignored) {
        }
    }

    public static boolean signUp(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUser(newUser);
        return true;
    }

    public static boolean signIn(String username, String password) {
        return users.containsKey(username) && users.get(username).authenticate(password);
    }
}

// Expense Tracker System
abstract class Expense {

    protected String category;
    protected double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public abstract void display();
}

class GeneralExpense extends Expense {

    public GeneralExpense(String category, double amount) {
        super(category, amount);
    }

    @Override
    public void display() {
        System.out.println("Category: " + category + ", Amount: " + amount);
    }
}

class ExpenseTracker {

    private List<Expense> expenses = new ArrayList<>();
    private static final String FILE_PATH = "expenses.txt";
    private static final List<String> CATEGORIES = Arrays.asList("Food", "Education", "Travel", "Groceries", "Entertainment", "Bills", "Shopping", "Health", "Transport", "Others");

    public void addExpense(String category, double amount) {
        Expense expense = new GeneralExpense(category, amount);
        expenses.add(expense);
        saveExpenses();
    }

    public void displayExpenses() {
        expenses.forEach(Expense::display);
    }

    public void displayTotalExpenditureByCategory() {
        Map<String, Double> categoryTotals = new HashMap<>();
        double totalExpense = 0;

        for (Expense e : expenses) {
            categoryTotals.put(e.category, categoryTotals.getOrDefault(e.category, 0.0) + e.amount);
            totalExpense += e.amount;
        }

        System.out.println("Total Expenditure: " + totalExpense);
        System.out.println("Expenditure by Category:");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            double percentage = (entry.getValue() / totalExpense) * 100;
            System.out.printf("%s: %.2f (%.2f%%)\n", entry.getKey(), entry.getValue(), percentage);
        }
    }

    public void saveExpenses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Expense e : expenses) {
                bw.write(e.category + "," + e.amount + "\n");
            }
        } catch (IOException ignored) {
        }
    }

    public void loadExpenses() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    expenses.add(new GeneralExpense(parts[0], Double.parseDouble(parts[1])));
                }
            }
        } catch (IOException ignored) {
        }
    }

    public static List<String> getCategories() {
        return CATEGORIES;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();
        tracker.loadExpenses();

        System.out.println("Welcome to Smart Expense Tracker!");
        boolean authenticated = false;
        while (!authenticated) {
            System.out.println("1. Sign Up\n2. Sign In");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (choice == 1) {
                if (UserManager.signUp(username, password)) {
                    System.out.println("Sign-up successful. Please sign in.");
                } else {
                    System.out.println("Username already exists.");
                }
            } else if (choice == 2) {
                if (UserManager.signIn(username, password)) {
                    System.out.println("Sign-in successful!");
                    authenticated = true;
                } else {
                    System.out.println("Invalid credentials. Try again.");
                }
            }
        }

        while (true) {
            System.out.println("1. Add Expense\n2. View Expenses\n3. Expense Analysis\n4. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Choose a category:");
                    List<String> categories = ExpenseTracker.getCategories();
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i));
                    }
                    int categoryChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (categoryChoice < 1 || categoryChoice > categories.size()) {
                        System.out.println("Invalid choice. Try again.");
                        break;
                    }
                    String category = categories.get(categoryChoice - 1);
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    tracker.addExpense(category, amount);
                    System.out.println("Expense added successfully!");
                    break;
                case 2:
                    System.out.println("Your Expenses:");
                    tracker.displayExpenses();
                    break;
                case 3:
                    tracker.displayTotalExpenditureByCategory();
                    break;
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
