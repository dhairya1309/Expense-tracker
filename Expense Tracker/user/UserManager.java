package user;

import db.DatabaseConnection;
import java.sql.*;

public class UserManager {

    public static int signUp(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return 1;
        } 
        catch (SQLException e) {
            return 0;
        }
    }

    public static int signIn(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? 1 : 0;
        }
        catch (SQLException e) {
            return 0;
        }
    }

    public static int deleteUser(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            int affected = ps.executeUpdate();
            return affected > 0 ? 1 : 0;
        }
        catch (SQLException e) {
            return 0;
        }
    }
}