import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;

public class ExpenseActionListener implements ActionListener {

    private final JTextArea expenseDescription;
    private final JTextArea expenseCost;
    private final JLabel errorLabel;
    private final JLabel balanceLabel;
    private final JLabel expenseInfoLabel;
    private final JPanel viewPanelExpense;

    private final MainWindow main;

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
            double expenseValue = Double.parseDouble(expenseCost.getText());

            errorLabel.setText("");

            JLabel dateLabel = new JLabel(main.getCurrentDate().toString());
            LocalDate date = main.getCurrentDate();

            String description = expenseDescription.getText();

            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.add(new JLabel(description + ": " + expenseValue + " kr"));
            row.add(dateLabel);

            JButton removeBtn = new JButton("remove");
            row.add(removeBtn);

            main.addExpense(expenseValue);
            balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
            expenseInfoLabel.setText("Total expenses: " + main.getTotalExpense() + " kr");

            Transaction expense = new Transaction(description, expenseValue, date, false);
            main.getAllTransactionsList().add(expense);

            SaveTransaction saver = new SaveTransaction();
            saver.saveData(expense);

            main.updateTotalsForDate(main.getCurrentDate());

            viewPanelExpense.add(row);
            viewPanelExpense.revalidate();
            viewPanelExpense.repaint();

            removeBtn.addActionListener(_ -> {
                main.removeExpense(expenseValue);
                main.getAllTransactionsList().remove(expense);

                balanceLabel.setText("Your total balance: " + main.getTotal() + " kr");
                expenseInfoLabel.setText("Total expenses: " + main.getTotalExpense() + " kr");

                main.updateTotalsForDate(main.getCurrentDate());

                viewPanelExpense.remove(row);
                viewPanelExpense.revalidate();
                viewPanelExpense.repaint();

                RemoveTransaction remove = new RemoveTransaction();
                remove.removeData(expense);

            });

            expenseDescription.setText("");
            expenseCost.setText("");

        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid number!");
            errorLabel.setBackground(Color.RED);

        }
    }
}
