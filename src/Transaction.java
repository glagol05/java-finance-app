import java.time.LocalDate;

public class Transaction {
    private String description;
    private int amount;
    private LocalDate date;
    private boolean isIncome;

    public Transaction(String description, int amount, LocalDate date, boolean isIncome) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isIncome = isIncome;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isIncome() {
        return isIncome;
    }

    @Override
    public String toString() {
        return description + " | " + amount + " kr | " + date + " | " + isIncome;
    }
}
