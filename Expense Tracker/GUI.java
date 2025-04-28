import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class GUI implements ActionListener {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Expense Tracker");
        frame.setSize(300, 300);
        frame.setLayout(null); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 300, 300);
        panel.setLayout(null); 

        frame.add(panel);

        JLabel userlabel = new JLabel("User");
        userlabel.setBounds(10, 20, 80, 25);
        panel.add(userlabel);

        JTextField user = new JTextField(20);
        user.setBounds(100,20,165,25);
        panel.add(user);

        JLabel passwordlabel = new JLabel("Password");
        passwordlabel.setBounds(10, 50, 80, 25);
        panel.add(passwordlabel);

        JPasswordField password = new JPasswordField(20);
        password.setBounds(100,50,165,25);
        panel.add(password);

        JButton b = new JButton("Login");
        b.setBounds(10,100,80,25);
        b.addActionListener(new GUI());
        panel.add(b);
         
        frame.setVisible(true);
    }
}
