package expenses;

import db.DatabaseConnection;
import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExpenseTracker {

    private static final List<String> CATEGORIES = Arrays.asList("Food", "Education", "Travel", "Groceries", "Entertainment", "Bills", "Shopping", "Health", "Transport", "Others");

    public void addExpense(String username, String category, double amount) {
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO expenses (username, category, amount, date, time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, category);
            ps.setDouble(3, amount);
            ps.setString(4, date);
            ps.setString(5, time);
            ps.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayExpenses(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT category, amount, date, time FROM expenses WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            System.out.printf("| %-15s | %10s | %-12s | %-8s |%n", "Category", "Amount", "Date", "Time");
            System.out.println("------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("| %-15s | %10.2f | %-12s | %-8s |%n",
                    rs.getString("category"),
                    rs.getDouble("amount"),
                    rs.getString("date"),
                    rs.getString("time"));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getCategories() {
        return CATEGORIES;
    }
}