
import java.io.FileWriter;
import java.io.IOException;

public class SaveTransaction {


    public void saveData(Transaction data) {
        try {
            FileWriter writer = new FileWriter("TransactionData.txt", true);
            writer.write(data.getDescription() + " | " + data.getAmount() + " | " + data.getDate() + " | " + data.isIncome() + "\n");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
