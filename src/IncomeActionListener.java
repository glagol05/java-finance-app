import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;

public class IncomeActionListener implements ActionListener {

    //Tar alla element som behövs från MainWindow.java
    private final JTextArea inputDescription;
    private final JTextArea inputCost;
    private final JLabel errorLabel;
    private final JLabel balanceLabel;
    private final JLabel incomeInfoLabel;
    private final JPanel viewPanelIncome;

    private final MainWindow main;

    public IncomeActionListener(MainWindow main,
                                JTextArea inputDescription,
                                JTextArea inputCost,
                                JLabel errorLabel,
                                JLabel balanceLabel,
                                JLabel incomeInfoLabel,
                                JPanel viewPanelIncome) {
        this.main = main;
        this.inputDescription = inputDescription;
        this.inputCost = inputCost;
        this.errorLabel = errorLabel;
        this.balanceLabel = balanceLabel;
        this.incomeInfoLabel = incomeInfoLabel;
        this.viewPanelIncome = viewPanelIncome;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Tar data från olika inputs och skapar Transaction object av dessa. Sparas sedan i txt filen.
        try {
            double incomeValue = Double.parseDouble(inputCost.getText());

            errorLabel.setText("");

            JLabel dateLabel = new JLabel(main.getCurrentDate().toString());
            LocalDate date = main.getCurrentDate();

            String description = inputDescription.getText();

            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.add(new JLabel(description + ": " + incomeValue + " kr"));
            row.add(dateLabel);

            JButton removeBtn = new JButton("remove");
            row.add(removeBtn);

            main.addIncome(incomeValue);

            balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
            incomeInfoLabel.setText("Total income: " + main.getTotalIncome() + " kr");

            //Skapar object av data och sparar den till allTransactions där summor för dag/vecka/månad/år uppdateras
            Transaction income = new Transaction(description, incomeValue, date, true);
            main.getAllTransactionsList().add(income);

            //Sparar transaktion till txt
            SaveTransaction saver = new SaveTransaction();
            saver.saveData(income);

            main.updateTotalsForDate(main.getCurrentDate());

            viewPanelIncome.add(row);
            viewPanelIncome.revalidate();
            viewPanelIncome.repaint();

            removeBtn.addActionListener(_ -> {
                main.removeIncome(incomeValue);
                main.getAllTransactionsList().remove(income);

                balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
                incomeInfoLabel.setText("Total income: " + main.getTotalIncome() + " kr");

                main.updateTotalsForDate(main.getCurrentDate());

                viewPanelIncome.remove(row);
                viewPanelIncome.revalidate();
                viewPanelIncome.repaint();

                RemoveTransaction remove = new RemoveTransaction();
                remove.removeData(income);

            });

            inputDescription.setText("");
            inputCost.setText("");

        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid number!");
            errorLabel.setForeground(Color.RED);
            
        }
    }
}
