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
           System.out.println("Error");
        }
    }

    public String displayExpenses(String username) {
        	    String output = "";
    	    try (Connection conn = DatabaseConnection.getConnection();
    	         PreparedStatement ps = conn.prepareStatement("SELECT category, amount, date, time FROM expenses WHERE username = ?")) {
    	        ps.setString(1, username);
    	        ResultSet rs = ps.executeQuery();

    	        output += "| Category        |     Amount | Date         | Time     |\n";
    	        output += "------------------------------------------------------------------\n";

    	        while (rs.next()) {
    	            String category = rs.getString("category");
    	            double amount = rs.getDouble("amount");
    	            String date = rs.getString("date");
    	            String time = rs.getString("time");
    	            output += String.format("| %-15s | %10.2f | %-12s | %-8s |%n", category, amount, date, time);
    	        }

    	    } catch (SQLException e) {
    	        System.err.println("Error : " + e.getMessage());
    	        output += "Error\n";
    	    }
    	    return output;
    	

    }
   
    public static List<String> getCategories() {
        return CATEGORIES;
    }
}