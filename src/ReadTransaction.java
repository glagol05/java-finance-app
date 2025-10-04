
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReadTransaction {
    
        //Laddar alla transaktioner och lägger de i en arraylist
        public List<Transaction> loadData() {
        List<Transaction> transactions = new ArrayList<>();

        //data som laddas från textfilen formatteras först och skickas sedan vidare som Transaction objects
        try (BufferedReader reader = new BufferedReader(new FileReader("TransactionData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length != 4) continue;

                String description = parts[0].trim();
                double amount = Double.parseDouble(parts[1].trim());
                LocalDate date = LocalDate.parse(parts[2].trim());
                boolean isIncome = Boolean.parseBoolean(parts[3].trim());

                transactions.add(new Transaction(description, amount, date, isIncome));
            }
        } catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
        }

        return transactions;
    }
}
