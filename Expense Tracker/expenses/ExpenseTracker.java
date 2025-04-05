package expenses;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExpenseTracker {

    private List<Expense> expenses = new ArrayList<>();
    private static final String FILE_PATH = "expenses.txt";
    private static final List<String> CATEGORIES = Arrays.asList("Food", "Education", "Travel", "Groceries", "Entertainment", "Bills", "Shopping", "Health", "Transport", "Others");

    public void addExpense(String category, double amount) {
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Expense expense = new GeneralExpense(category, amount, date, time);
        expenses.add(expense);
        saveExpenses();
    }

    public void displayExpenses() {
        System.out.printf("| %-15s | %10s | %-12s | %-8s |%n", "Category", "Amount", "Date", "Time");
        System.out.println("------------------------------------------------------------------");
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
            System.out.printf("%s: %.2f (%.2f%%)%n", entry.getKey(), entry.getValue(), percentage);
        }
    }

    public void saveExpenses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Expense e : expenses) {
                bw.write(e.category + "," + e.amount + "," + e.date + "," + e.time + "\n");
            }
        } catch (IOException e) {
        }
    }

    public void loadExpenses() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    expenses.add(new GeneralExpense(parts[0], Double.parseDouble(parts[1]), parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
        }
    }

    public static List<String> getCategories() {
        return CATEGORIES;
    }
}