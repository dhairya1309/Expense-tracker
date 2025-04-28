import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Tracker_GUI extends MainTracker{

    public static void main(String[] args){

        JFrame frame = new JFrame("Expense Tracker");
        JPanel panel = new JPanel();
        frame.setSize(100,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);
        
    }
    
}
