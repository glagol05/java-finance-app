
import java.util.List;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainWindow mainFrame = new MainWindow();
                
                ReadTransaction reader = new ReadTransaction();
                List<Transaction> loadedTransactions = reader.loadData();
                mainFrame.setAllTransactions(loadedTransactions);
                mainFrame.displayTransactions(loadedTransactions);
            }
        });
    }
}
