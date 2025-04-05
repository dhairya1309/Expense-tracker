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

    public static int signUp(String username, String password) {
        if (users.containsKey(username)) {
            return 0;
        }
        User newUser = new User(username, password);
        users.put(username, newUser);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(username + "," + password + "\n");
        } catch (IOException ignored) {
        }
        return 1;
    }

    public static int signIn(String username, String password) {
        return (users.containsKey(username) && users.get(username).authenticate(password) == 1) ? 1 : 0;
    }

    public static int deleteUser(String username) {
        if (!users.containsKey(username)) {
            return 0;
        }
        users.remove(username);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, User> entry : users.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue().getUsername() + "\n");
            }
        } catch (IOException ignored) {
        }
        return 1;
    }
}