import java.time.LocalDate;

public class Transaction {
    private final String description;
    private final double amount;
    private final LocalDate date;
    private final boolean isIncome;

    public Transaction(String description, double amount, LocalDate date, boolean isIncome) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isIncome = isIncome;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
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
        return description + " | " + amount + " | " + date + " | " + isIncome;
    }
}
