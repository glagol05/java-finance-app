
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class ReadTransaction {
    
    public void loadData(){

        try {
            BufferedReader reader = new BufferedReader(new FileReader("TransactionData.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 4) continue;

                String description = parts[0].trim();
                int amount = Integer.parseInt(parts[1].trim());
                LocalDate date = LocalDate.parse(parts[2].trim());
                boolean isIncome = Boolean.parseBoolean(parts[3].trim());

                Transaction transaction = new Transaction(description, amount, date, isIncome);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
