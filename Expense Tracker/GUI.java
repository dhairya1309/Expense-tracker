import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import user.UserManager;
import expenses.ExpenseTracker;

public class GUI {

    private static String currentUser;
    private static final ExpenseTracker tracker = new ExpenseTracker();
    private static final JFrame frame = new JFrame();

    public static void main(String[] args) {
        createLoginScreen();
    }

    private static void createLoginScreen() {
        frame.setTitle("Smart Expense Tracker - Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton signInButton = new JButton("Sign In");
        JButton signUpButton = new JButton("Sign Up");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(signInButton);
        panel.add(signUpButton);

        signInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (UserManager.signIn(username, password) == 1) {
                currentUser = username;
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });

        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (UserManager.signUp(username, password) == 1) {
                JOptionPane.showMessageDialog(frame, "Sign-up successful. You can now sign in.");
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists.");
            }
        });

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private static void showMainMenu() {
        frame.getContentPane().removeAll();
        frame.setTitle("Smart Expense Tracker - Main Menu");
        frame.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser, SwingConstants.CENTER);
        JButton addExpenseButton = new JButton("Add Expense");
        JButton viewExpensesButton = new JButton("View Expenses");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton logoutButton = new JButton("Logout");
        JButton exitButton = new JButton("Exit");

        frame.add(welcomeLabel);
        frame.add(addExpenseButton);
        frame.add(viewExpensesButton);
        frame.add(deleteAccountButton);
        frame.add(logoutButton);
        frame.add(exitButton);

        addExpenseButton.addActionListener(e -> showAddExpenseScreen());
        viewExpensesButton.addActionListener(e -> showExpenses());
        deleteAccountButton.addActionListener(e -> {
            if (UserManager.deleteUser(currentUser) == 1) {
                JOptionPane.showMessageDialog(frame, "Account deleted.");
                createLoginScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to delete account.");
            }
        });
        logoutButton.addActionListener(e -> createLoginScreen());
        exitButton.addActionListener(e -> System.exit(0));

        frame.revalidate();
        frame.repaint();
    }

     private static void showAddExpenseScreen() { 
           JFrame addFrame = new JFrame("Add Expense"); 
           addFrame.setSize(300, 250); 
           JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); 

           List<String> categories = ExpenseTracker.getCategories(); 
           JComboBox<String> categoryBox = new JComboBox<>(categories.toArray(new String[0])); 
           JTextField amountField = new JTextField(); 
           JButton addButton = new JButton("Add"); 

           panel.add(new JLabel("Category:")); 
           panel.add(categoryBox); 
           panel.add(new JLabel("Amount:")); 
           panel.add(amountField); 
           panel.add(new JLabel("")); 
           panel.add(addButton);
    
           addButton.addActionListener(e -> { 
               try {
                   String category = (String) categoryBox.getSelectedItem(); 
                   double amount = Double.parseDouble(amountField.getText()); 
                   tracker.addExpense(currentUser, category, amount); 
                   JOptionPane.showMessageDialog(addFrame, "Expense added!"); 
                   addFrame.dispose(); 
               } catch (NumberFormatException ex) { 
                   JOptionPane.showMessageDialog(addFrame, "Invalid amount.");
               } 
           }); 
    
           addFrame.add(panel); 
           addFrame.setVisible(true); 
       } 
    
     private static void showExpenses() {
        JFrame viewFrame = new JFrame("Your Expenses");
        viewFrame.setSize(600, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        // Fetch and display expenses in JTextArea
        String expensesOutput = tracker.displayExpenses(currentUser);
        textArea.setText(expensesOutput);

        viewFrame.add(new JScrollPane(textArea));
        viewFrame.setVisible(true);
    }
}
