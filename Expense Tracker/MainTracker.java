import user.UserManager;
import expenses.ExpenseTracker;

import java.util.*;

public class MainTracker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseTracker tr = new ExpenseTracker();
        tr.loadExpenses();

        System.out.println("Welcome to Smart Expense Tracker!");
        int authenticated = 0;
        String currentUser = null;
        while (authenticated == 0) {
            System.out.println("1. Sign Up\n2. Sign In");
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (choice == 1) {
                if (UserManager.signUp(username, password) == 1) {
                    System.out.println("Sign-up successful. Please sign in.");
                } else {
                    System.out.println("Username already exists.");
                }
            } else if (choice == 2) {
                if (UserManager.signIn(username, password) == 1) {
                    System.out.println("Sign-in successful!");
                    authenticated = 1;
                    currentUser = username;
                } else {
                    System.out.println("Invalid credentials. Try again.");
                }
            }
        }

        while (true) {
            System.out.println("1. Add Expense\n2. View Expenses\n3. Expense Analysis\n4. Delete Account\n5. Exit");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Choose a category:");
                    List<String> categories = ExpenseTracker.getCategories();
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i));
                    }
                    int categoryChoice = sc.nextInt();
                    sc.nextLine();
                    if (categoryChoice < 1 || categoryChoice > categories.size()) {
                        System.out.println("Invalid choice. Try again.");
                        break;
                    }
                    String category = categories.get(categoryChoice - 1);
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    tr.addExpense(category, amount);
                    System.out.println("Expense added successfully!");
                    break;
                case 2:
                    System.out.println("Your Expenses:");
                    tr.displayExpenses();
                    break;
                case 3:
                    tr.displayTotalExpenditureByCategory();
                    break;
                case 4:
                    if (UserManager.deleteUser(currentUser) == 1) {
                        System.out.println("Account deleted successfully. Exiting.");
                        return;
                    } else {
                        System.out.println("Failed to delete account.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
