import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;

public class IncomeActionListener implements ActionListener {

    private JTextArea inputDescription;
    private JTextArea inputCost;
    private JLabel errorLabel;
    private JLabel balanceLabel;
    private JLabel incomeInfoLabel;
    private JPanel viewPanelIncome;

    private MainWindow main;

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
        try {
            int incomeValue = Integer.parseInt(inputCost.getText());

            errorLabel.setText("");

            JLabel dateLabel = new JLabel(LocalDate.now().toString());
            String description = inputDescription.getText();

            JPanel row = new JPanel();
            row.add(new JLabel(description + ": " + incomeValue + " kr"));
            row.add(dateLabel);

            JButton removeBtn = new JButton("Remove income");
            row.add(removeBtn);

            // Update totals
            main.addIncome(incomeValue);
            balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
            incomeInfoLabel.setText("Total income: " + main.getTotalIncome() + " kr");

            viewPanelIncome.add(row);
            viewPanelIncome.revalidate();
            viewPanelIncome.repaint();

            // Remove button logic
            removeBtn.addActionListener(ev -> {
                main.removeIncome(incomeValue);
                balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
                incomeInfoLabel.setText("Total income: " + main.getTotalIncome() + " kr");

                viewPanelIncome.remove(row);
                viewPanelIncome.revalidate();
                viewPanelIncome.repaint();
            });

            inputDescription.setText("");
            inputCost.setText("");

        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid number!");
        }
    }
}
