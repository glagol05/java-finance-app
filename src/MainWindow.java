import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class MainWindow {

    //Element som behöver vara tillgängliga "globalt" inom denna class, läses annars inte pga closure
    private JFrame frame;
    private double total = 0;
    private double totalIncome = 0;
    private double totalExpense = 0;
    private JLabel balanceLabel;
    private LocalDate currentDate;
    private List<Transaction> allTransactions = new ArrayList<>();

    private JPanel viewPanelIncome;
    private JPanel viewPanelExpense;

    private JLabel dailyIncomeLabel;
    private JLabel dailyExpenseLabel;
    private JLabel weeklyIncomeLabel;
    private JLabel weeklyExpenseLabel;
    private JLabel monthlyIncomeLabel;
    private JLabel monthlyExpenseLabel;
    private JLabel yearlyIncomeLabel;
    private JLabel yearlyExpenseLabel;


    //Tar siffror från allTransactions och summerar för dag, vecka, månad och år baserat på dagens datum
    private void recalcTotals() {
        totalIncome = allTransactions.stream()
                                     .filter(t -> t.isIncome())
                                     .mapToDouble(t -> t.getAmount())
                                     .sum();
        totalExpense = allTransactions.stream()
                                      .filter(t -> !t.isIncome())
                                      .mapToDouble(t -> t.getAmount())
                                      .sum();
        total = totalIncome - totalExpense;
    }

    private double sumDaily(LocalDate day, boolean income) {
        return allTransactions.stream()
                .filter(t -> t.isIncome() == income)
                .filter(t -> t.getDate().equals(day))
                .mapToDouble(t -> t.getAmount())
                .sum();
    }

    private double sumWeekly(LocalDate day, boolean income) {
        WeekFields week = WeekFields.of(Locale.getDefault());
        int weekNumber = day.get(week.weekOfWeekBasedYear());
        int year = day.getYear();

        return allTransactions.stream()
                .filter(t -> t.isIncome() == income)
                .filter(t -> {
                    int tWeek = t.getDate().get(week.weekOfWeekBasedYear());
                    int tYear = t.getDate().getYear();
                    return tWeek == weekNumber && tYear == year;
                })
                .mapToDouble(t -> t.getAmount())
                .sum();
    }

    private double sumMonthly(LocalDate day, boolean income) {
        int month = day.getMonthValue();
        int year = day.getYear();

        return allTransactions.stream()
                .filter(t -> t.isIncome() == income)
                .filter(t -> t.getDate().getMonthValue() == month && t.getDate().getYear() == year)
                .mapToDouble(t -> t.getAmount())
                .sum();
    }

    private double sumYearly(LocalDate day, boolean income) {
        int year = day.getYear();

        return allTransactions.stream()
                .filter(t -> t.isIncome() == income)
                .filter(t -> t.getDate().getYear() == year)
                .mapToDouble(t -> t.getAmount())
                .sum();
    }

    //Skapar labels som visar summor enligt ovanstående kod
    public void updateTotalsForDate(LocalDate date) {
        dailyIncomeLabel.setText("Daily income: " + sumDaily(date, true));
        dailyExpenseLabel.setText("Daily expenses: " + sumDaily(date, false));

        weeklyIncomeLabel.setText("Weekly income: " + sumWeekly(date, true));
        weeklyExpenseLabel.setText("Weekly expenses: " + sumWeekly(date, false));

        monthlyIncomeLabel.setText("Monthly income: " + sumMonthly(date, true));
        monthlyExpenseLabel.setText("Monthly expenses: " + sumMonthly(date, false));

        yearlyIncomeLabel.setText("Yearly income: " + sumYearly(date, true));
        yearlyExpenseLabel.setText("Yearly expenses: " + sumYearly(date, false));

        balanceLabel.setText("Your total balance: " + total);
    }

    //En metod för att lägga till placeholdertext i inputs. Blir då tydligt vad användaren ska skriva
    public void PlaceHolderText(JTextArea input, String placeholder) {
        
        input.setText(placeholder);
        input.setForeground(Color.GRAY);

        input.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (input.getText().equals(placeholder)) {
                    input.setText("");
                    input.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (input.getText().isEmpty()) {
                    input.setText(placeholder);
                    input.setForeground(Color.GRAY);
                }
            }
        });
    }


    public MainWindow() {
        initialize();
    }
    
    //Tar transaktioner från txt filen och fyller GUI med data
    public void displayTransactions(List<Transaction> transactions) {
        viewPanelIncome.removeAll();
        viewPanelExpense.removeAll();
        
        for (Transaction t : transactions) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.add(new JLabel(t.getDescription() + ": " + t.getAmount() + " kr"));
            row.add(new JLabel(t.getDate().toString()));

            JButton removeBtn = new JButton("remove");
            row.add(removeBtn);

            //Om transaktion är inkomst läggs de i viewPanelIncome, annars i viewPanelExpense
            if (t.isIncome()) {
                viewPanelIncome.add(row);

                removeBtn.addActionListener(ev -> {
                    removeIncome(t.getAmount());
                    balanceLabel.setText("Your total balance: " + total);

                    allTransactions.remove(t);
                    updateTotalsForDate(currentDate);

                    viewPanelIncome.remove(row);
                    viewPanelIncome.revalidate();
                    viewPanelIncome.repaint();
                    new RemoveTransaction().removeData(t);
                });
            } else {
                viewPanelExpense.add(row);

                removeBtn.addActionListener(ev -> {
                    removeExpense(t.getAmount());
                    balanceLabel.setText("Your total balance: " + total);

                    allTransactions.remove(t);
                    updateTotalsForDate(currentDate);

                    viewPanelExpense.remove(row);
                    viewPanelExpense.revalidate();
                    viewPanelExpense.repaint();
                    new RemoveTransaction().removeData(t);
                });
            }
        }
        viewPanelIncome.revalidate();
        viewPanelExpense.revalidate();

        balanceLabel.setText("Your total balance: " + total);
    }

    //Kod för att initiera GUI nedan
    public void initialize() {
        frame = new JFrame();
        this.frame.setTitle("Finance application");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800,800);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.requestFocusInWindow();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setPreferredSize(new Dimension(frame.getWidth(), 150));

        JTextArea inputDescription = new JTextArea(1,20);
        inputDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        PlaceHolderText(inputDescription, "Enter description of income");

        JTextArea inputCost = new JTextArea(1, 20);
        inputCost.setFont(new Font("Arial", Font.PLAIN, 16));
        PlaceHolderText(inputCost, "Enter cost");

        JButton addNumber = new JButton("Add income");

        JTextArea expenseDescription = new JTextArea(1, 20);
        expenseDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        PlaceHolderText(expenseDescription, "Enter description of expense");

        JTextArea expenseCost = new JTextArea(1, 20);
        expenseCost.setFont(new Font("Arial", Font.PLAIN, 16));
        PlaceHolderText(expenseCost, "Enter cost");

        JButton addExpense = new JButton("Add expense");

        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        fieldsPanel.add(inputDescription);
        fieldsPanel.add(inputCost);
        fieldsPanel.add(addNumber);
        fieldsPanel.add(expenseDescription);
        fieldsPanel.add(expenseCost);
        fieldsPanel.add(addExpense);

        inputPanel.add(fieldsPanel);

        JButton dateButton = new JButton("Change date");
        inputPanel.add(dateButton);

        currentDate = LocalDate.now();
        JLabel currentDateLabel = new JLabel(currentDate.toString());
        inputPanel.add(currentDateLabel);

        //ActionListener där användaren kan välja datum. Total inkomst/utkomst uppdateras baserat på detta
        dateButton.addActionListener(e -> {
            JDateChooser dateChooser = new JDateChooser();
            int result = JOptionPane.showConfirmDialog(
                frame,
                dateChooser,
                "Selected date",
                JOptionPane.OK_CANCEL_OPTION
            );
            if (result == JOptionPane.OK_OPTION) {
                java.util.Date chosen = dateChooser.getDate();
                LocalDate local = chosen.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                currentDate = local;
                currentDateLabel.setText(local.toString());
                updateTotalsForDate(local);
            }
        });

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        inputPanel.add(errorLabel);

        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new BorderLayout());
        balancePanel.setPreferredSize(new Dimension(frame.getWidth(), 120));
        balancePanel.setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new java.awt.GridLayout(4, 2, 20, 10));

        dailyIncomeLabel = new JLabel("Daily income", JLabel.CENTER);
        dailyExpenseLabel = new JLabel("Daily expenses", JLabel.CENTER);
        weeklyIncomeLabel = new JLabel("Weekly income", JLabel.CENTER);
        weeklyExpenseLabel = new JLabel("Weekly expenses", JLabel.CENTER);
        monthlyIncomeLabel = new JLabel("Monthly income", JLabel.CENTER);
        monthlyExpenseLabel = new JLabel("Monthly expenses", JLabel.CENTER);
        yearlyIncomeLabel = new JLabel("Yearly income", JLabel.CENTER);
        yearlyExpenseLabel = new JLabel("Yearly expenses", JLabel.CENTER);

        gridPanel.add(dailyIncomeLabel);
        gridPanel.add(dailyExpenseLabel);
        gridPanel.add(weeklyIncomeLabel);
        gridPanel.add(weeklyExpenseLabel);
        gridPanel.add(monthlyIncomeLabel);
        gridPanel.add(monthlyExpenseLabel);
        gridPanel.add(yearlyIncomeLabel);
        gridPanel.add(yearlyExpenseLabel);

        balancePanel.add(gridPanel, BorderLayout.CENTER);

        balanceLabel = new JLabel("Your total balance: " + total, JLabel.CENTER);
        balancePanel.add(balanceLabel, BorderLayout.SOUTH);

        viewPanelIncome = new JPanel();
        viewPanelIncome.setLayout(new BoxLayout(viewPanelIncome, BoxLayout.Y_AXIS));

        JLabel incomeInfoLabel = new JLabel("Total income: ");
        incomeInfoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        viewPanelIncome.add(incomeInfoLabel);

        viewPanelExpense = new JPanel();
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


        //ActionListener till add income och add expense knapparna, låter användaren skapa transaktioner
        addNumber.addActionListener(
            new IncomeActionListener(this, inputDescription, inputCost, errorLabel, balanceLabel, incomeInfoLabel, viewPanelIncome)
        );
        addExpense.addActionListener(
            new ExpenseActionListener(this, expenseDescription, expenseCost, errorLabel, balanceLabel, expenseInfoLabel, viewPanelExpense)
        );

        frame.add(scrollContainer, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(balancePanel, BorderLayout.SOUTH);


        //Här läses all data och metod för att skapa GUI kallas här
        ReadTransaction reader = new ReadTransaction();
        List<Transaction> loadedTransactions = reader.loadData();

        if (loadedTransactions != null) {
            allTransactions.addAll(loadedTransactions);
            recalcTotals();
            displayTransactions(allTransactions);
            balanceLabel.setText("Your total balance: " + total);
        }

        updateTotalsForDate(currentDate);
    }


    //Getters/setters för ActionListener
    public double getTotal() {
        return total;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void addIncome(double value) {
        total += value;
        totalIncome += value;
    }

    public void removeIncome(double value) {
        total -= value;
        totalIncome -= value;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void addExpense(double value) {
        total -= value;
        totalExpense += value;
    }

    public void removeExpense(double value) {
        total += value;
        totalExpense -= value;
    }

    public void setAllTransactions(List<Transaction> transactions) {
        this.allTransactions = transactions;
    }

    public List<Transaction> getAllTransactionsList() {
        return allTransactions;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }
}