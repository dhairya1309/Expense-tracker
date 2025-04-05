package expenses;

import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private List<Expense> expenses = new ArrayList<>();
    private static final String FILE_PATH = "expenses.txt";
    private static final List<String> CATEGORIES = Arrays.asList(
        "Food", "Education", "Travel", "Groceries", "Entertainment", 
        "Bills", "Shopping", "Health", "Transport", "Others"
    );

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
            System.out.printf("%s: %f (%f%%)\n", entry.getKey(), entry.getValue(), percentage);
        }
    }

    public void saveExpenses() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            for (Expense ex : expenses) {
                String line = ex.category + "," + ex.amount + "\n";
                fos.write(line.getBytes());
            }
        } catch (IOException e) {}
    }

    public void loadExpenses() {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            String[] lines = new String(data).split("\n");
            for (String line : lines) {
                String[] parts = line.trim().split(",");
                if (parts.length == 2) {
                    expenses.add(new GeneralExpense(parts[0], Double.parseDouble(parts[1])));
                }
            }
            fis.close();
        } catch (IOException e) {}
    }

    public static List<String> getCategories() {
        return CATEGORIES;
    }
}
