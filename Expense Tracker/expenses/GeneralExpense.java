package expenses;

public class GeneralExpense extends Expense {

    public GeneralExpense(String category, double amount, String date, String time) {
        super(category, amount, date, time);
    }

    @Override
    public void display() {
        System.out.printf("| %-15s | %10.2f | %-12s | %-8s |%n", category, amount, date, time);
    }
}
