
import java.io.FileWriter;
import java.io.IOException;

public class SaveTransaction {

    //Sparar data till txt fil på ett specifikt formatterat sätt
    public void saveData(Transaction data) {
        try {
            FileWriter writer = new FileWriter("TransactionData.txt", true);
            writer.write(data.getDescription().trim() + " | " + data.getAmount() + " | " + data.getDate() + " | " + data.isIncome() + "\n");
            writer.close();
        }
        catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
        }
    }
}
