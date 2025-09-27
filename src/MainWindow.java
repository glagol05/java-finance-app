import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
    private int totalIncome = 0;
    private int totalExpense = 0;
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
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setPreferredSize(new Dimension(frame.getWidth(), 120));

        JTextArea inputDescription = new JTextArea(1,20);
        inputDescription.setFont(new Font("Arial", Font.PLAIN, 16));

        JTextArea inputCost = new JTextArea(1, 20);
        inputCost.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton addNumber = new JButton("Add income");

        JTextArea expenseDescription = new JTextArea(1, 20);
        expenseDescription.setFont(new Font("Arial", Font.PLAIN, 16));

        JTextArea expenseCost = new JTextArea(1, 20);
        expenseCost.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton addExpense = new JButton("Add expense");

        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        fieldsPanel.add(inputDescription);
        fieldsPanel.add(inputCost);
        fieldsPanel.add(addNumber);
        fieldsPanel.add(expenseDescription);
        fieldsPanel.add(expenseCost);
        fieldsPanel.add(addExpense);

        inputPanel.add(fieldsPanel);

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        inputPanel.add(errorLabel);

        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        balancePanel.setBackground(Color.WHITE);

        balanceLabel = new JLabel("Your total balance: " + Integer.toString(total));
        balancePanel.add(balanceLabel);

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.X_AXIS));

        JPanel viewPanelIncome = new JPanel();
        viewPanelIncome.setLayout(new BoxLayout(viewPanelIncome, BoxLayout.Y_AXIS));

        JLabel incomeInfoLabel = new JLabel("Total income: ");
        incomeInfoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        viewPanelIncome.add(incomeInfoLabel);

        JPanel viewPanelExpense = new JPanel();
        viewPanelExpense.setLayout(new BoxLayout(viewPanelExpense, BoxLayout.Y_AXIS));

        JLabel expenseInfoLabel = new JLabel("Total expenses: ");
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

        

        addNumber.addActionListener(
            new IncomeActionListener(this, inputDescription, inputCost, errorLabel, balanceLabel, incomeInfoLabel, viewPanelIncome)
        );

        addExpense.addActionListener(
            new ExpenseActionListener(this, expenseDescription, expenseCost, errorLabel, balanceLabel, expenseInfoLabel, viewPanelExpense)
        );

        frame.add(scrollContainer, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(balancePanel, BorderLayout.SOUTH);
    }

    public int getTotal() {
        return total;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void addIncome(int value) {
        total += value;
        totalIncome += value;
    }

    public void removeIncome(int value) {
        total -= value;
        totalIncome -= value;
    }

    public int getTotalExpense() {
        return totalExpense;
    }

    public void addExpense(int value) {
        total += value;
        totalExpense += value;
    }

    public void removeExpense(int value) {
        total -= value;
        totalExpense -= value;
    }

}