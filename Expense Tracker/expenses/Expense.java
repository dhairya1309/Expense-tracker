package expenses;

public abstract class Expense {

    protected String category;
    protected double amount;
    protected String date;
    protected String time;

    public Expense(String category, double amount, String date, String time) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public abstract void display();
}
