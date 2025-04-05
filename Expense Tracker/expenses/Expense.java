package expenses;

abstract class Expense {
    protected String category;
    protected double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public abstract void display();
}


 class GeneralExpense extends Expense {
    public GeneralExpense(String category, double amount) {
        super(category, amount);
    }

    @Override
    public void display() {
        System.out.println("Category: " + category + ", Amount: " + amount);
    }
}
