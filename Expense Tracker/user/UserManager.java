package user;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String FILE_PATH = "users.txt";
    private static Map<String, User> users = new HashMap<>();

    static {
        loadUsers();
    }

    public static void loadUsers() {
        try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = fis.read()) != -1) {
                sb.append((char) ch);
            }
            String[] lines = sb.toString().split("\n");
            for (String line : lines) {
                String[] parts = line.trim().split(",");
                if (parts.length == 2) {
                    users.put(parts[0], new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {}
    }

    public static void saveUser(User user) {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH, true)) {
            String data = user.getUsername() + "," + user.getPassword() + "\n";
            fos.write(data.getBytes());
        } catch (IOException e) {}
    }

    public static String signUp(String username, String password) {
        if (!users.containsKey(username)) {
            User newUser = new User(username, password);
            users.put(username, newUser);
            saveUser(newUser);
            return "success";
        }
        return "exists";
    }

    public static String signIn(String username, String password) {
        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            return "success";
        }
        return "fail";
    }
}
