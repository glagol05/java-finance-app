import java.io.*;
import java.util.*;

public class RemoveTransaction {

    private static final String FILE_NAME = "TransactionData.txt";

    public void removeData(Transaction transactionToRemove) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.trim().equals(transactionToRemove.toString())) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading/writing file: " + e.getMessage());
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (int i = 0; i < lines.size(); i++) {
                String l = lines.get(i);
                bufferedWriter.write(l);
                bufferedWriter.newLine();
            }
        } catch (IOException e) 
        {
            System.err.println("Error reading/writing file: " + e.getMessage());
        }
    }
}
