import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;

public class ExpenseActionListener implements ActionListener {

    private JTextArea expenseDescription;
    private JTextArea expenseCost;
    private JLabel errorLabel;
    private JLabel balanceLabel;
    private JLabel expenseInfoLabel;
    private JPanel viewPanelExpense;

    private MainWindow main;

    public ExpenseActionListener(MainWindow main,
                                JTextArea expenseDescription,
                                JTextArea expenseCost,
                                JLabel errorLabel,
                                JLabel balanceLabel,
                                JLabel expenseInfoLabel,
                                JPanel viewPanelExpense) {
        this.main = main;
        this.expenseDescription = expenseDescription;
        this.expenseCost = expenseCost;
        this.errorLabel = errorLabel;
        this.balanceLabel = balanceLabel;
        this.expenseInfoLabel = expenseInfoLabel;
        this.viewPanelExpense = viewPanelExpense;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int expenseValue = Integer.parseInt(expenseCost.getText());

            errorLabel.setText("");

            JLabel dateLabel = new JLabel(LocalDate.now().toString());
            String description = expenseDescription.getText();

            JPanel row = new JPanel();
            row.add(new JLabel(description + ": " + expenseValue + " kr"));
            row.add(dateLabel);

            JButton removeBtn = new JButton("Remove expense");
            row.add(removeBtn);

            main.addExpense(expenseValue);
            balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
            expenseInfoLabel.setText("Total expenses: " + main.getTotalExpense() + " kr");

            viewPanelExpense.add(row);
            viewPanelExpense.revalidate();
            viewPanelExpense.repaint();

            removeBtn.addActionListener(ev -> {
                main.removeExpense(expenseValue);
                balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
                expenseInfoLabel.setText("Total expenses: " + main.getTotalExpense() + " kr");

                viewPanelExpense.remove(row);
                viewPanelExpense.revalidate();
                viewPanelExpense.repaint();
            });

            expenseDescription.setText("");
            expenseCost.setText("");

        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid number!");
        }
    }
}
