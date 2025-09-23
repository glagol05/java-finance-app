import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class MainWindow {
    
    private JFrame frame;
    private int total = 0;
    private JLabel balanceLabel;

    public MainWindow() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame();
        this.frame.setTitle("Finance application");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800,500);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        inputPanel.setBackground(Color.RED);
        inputPanel.setPreferredSize(new Dimension(frame.getWidth(), 70));

        JTextArea inputDescription = new JTextArea(1,20);
        inputDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(inputDescription);

        JTextArea inputCost = new JTextArea(1, 20);
        inputCost.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(inputCost);

        JButton addNumber = new JButton("Add income");

        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        balancePanel.setBackground(Color.WHITE);

        balanceLabel = new JLabel("Your total balance: " + Integer.toString(total));
        balancePanel.add(balanceLabel);

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.X_AXIS));

        JPanel viewPanelIncome = new JPanel();
        viewPanelIncome.setLayout(new BoxLayout(viewPanelIncome, BoxLayout.Y_AXIS));

        JLabel incomeInfoLabel = new JLabel("Your income: ");
        incomeInfoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        viewPanelIncome.add(incomeInfoLabel);

        JPanel viewPanelExpense = new JPanel();
        viewPanelExpense.setLayout(new BoxLayout(viewPanelExpense, BoxLayout.Y_AXIS));

        JLabel expenseInfoLabel = new JLabel("Your expenses: ");
        expenseInfoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        viewPanelExpense.add(expenseInfoLabel);

        JScrollPane scrollPaneIncome = new JScrollPane(viewPanelIncome);
        scrollPaneIncome.setBorder(new LineBorder(Color.GRAY, 1));
        scrollPaneIncome.setPreferredSize(new Dimension(400, 0));

        JScrollPane scrollPaneExpense = new JScrollPane(viewPanelExpense);
        scrollPaneExpense.setBorder(new LineBorder(Color.GRAY, 1));
        scrollPaneExpense.setPreferredSize(new Dimension(400, 0));

        JPanel scrollContainer = new JPanel();
        scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.X_AXIS));

        scrollContainer.add(scrollPaneIncome);
        scrollContainer.add(scrollPaneExpense);


        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        viewPanel.add(errorLabel);

        addNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int incomeValue = Integer.parseInt(inputCost.getText());

                    errorLabel.setText("");

                    total += incomeValue;
                    balanceLabel.setText("Your total balance: " + Integer.toString(total) + " kr");

                    String description = inputDescription.getText();

                    JPanel expenseRow = new JPanel();
                    expenseRow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

                    JLabel expenseLabel = new JLabel(description + ": " + incomeValue + " kr");
                    expenseRow.add(expenseLabel);

                    JButton removeExpense = new JButton("Remove income");
                    expenseRow.add(removeExpense);

                    expenseRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, expenseRow.getPreferredSize().height));

                    viewPanelIncome.add(expenseRow);
                    viewPanelIncome.revalidate();
                    viewPanelIncome.repaint();

                    removeExpense.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            total -= incomeValue;
                            balanceLabel.setText("Your total balance: " + total + " kr");

                            expenseRow.remove(expenseLabel);
                            expenseRow.remove(removeExpense);

                            viewPanelIncome.remove(expenseRow);

                            viewPanelIncome.revalidate();
                            viewPanelIncome.repaint();
                        }
                    });

                    inputDescription.setText("");
                    inputCost.setText("");

                } catch (NumberFormatException ex) {
                    errorLabel.setText("Please enter a valid number!");

                    inputDescription.setText("");
                    inputCost.setText("");
                }
            }
        });

        inputPanel.add(addNumber);

        JTextArea expenseDescription = new JTextArea(1, 20);
        expenseDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(expenseDescription);

        JTextArea expenseCost = new JTextArea(1, 20);
        expenseCost.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(expenseCost);

        JButton addExpense = new JButton("Add expense");

        addExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int expenseValue = Integer.parseInt(expenseCost.getText());

                    errorLabel.setText("");

                    total -= expenseValue;
                    balanceLabel.setText("Your total balance: " + Integer.toString(total) + " kr");

                    String description = expenseDescription.getText();

                    JPanel expenseRow = new JPanel();
                    expenseRow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

                    JLabel expenseLabel = new JLabel("Expense: " + description + ": " + expenseValue + " kr");
                    expenseRow.add(expenseLabel);

                    JButton removeExpense = new JButton("Remove expense");
                    expenseRow.add(removeExpense);

                    expenseRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, expenseRow.getPreferredSize().height));

                    viewPanelExpense.add(expenseRow);
                    viewPanelExpense.revalidate();
                    viewPanelExpense.repaint();

                    removeExpense.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            total += expenseValue;
                            balanceLabel.setText("Your total balance: " + total + " kr");

                            expenseRow.remove(expenseLabel);
                            expenseRow.remove(removeExpense);

                            viewPanelExpense.remove(expenseRow);

                            viewPanelExpense.revalidate();
                            viewPanelExpense.repaint();
                        }
                    });

                    expenseDescription.setText("");
                    expenseCost.setText("");

                    
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Please enter a valid number!");

                    expenseDescription.setText("");
                    expenseCost.setText("");
                }
            }
        });

        inputPanel.add(addExpense);

        frame.add(scrollContainer, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(balancePanel, BorderLayout.SOUTH);
    }
}