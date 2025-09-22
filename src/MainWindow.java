import java.awt.BorderLayout;
import java.awt.Color;
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

public class MainWindow {
    
    private JFrame frame;
    private int total = 0;
    private JLabel balanceLabel;
    private JLabel expenseLabel;
    private JButton removeExpense;

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

        JTextArea inputDescription = new JTextArea(1,20);
        inputDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(inputDescription);

        JTextArea inputCost = new JTextArea(1, 20);
        inputCost.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(inputCost);

        JButton addNumber = new JButton("Add");

        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        balancePanel.setBackground(Color.WHITE);

        balanceLabel = new JLabel("Your total balance: " + Integer.toString(total));
        balancePanel.add(balanceLabel);

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBackground(Color.GREEN);

        JScrollPane scrollPane = new JScrollPane(viewPanel);

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        viewPanel.add(errorLabel);

        addNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int value = Integer.parseInt(inputCost.getText());

                    errorLabel.setText("");

                    total += value;
                    balanceLabel.setText("Your total balance: " + Integer.toString(total) + " kr");

                    String description = inputDescription.getText();

                    expenseLabel = new JLabel();
                    expenseLabel.setText(description + ": " + Integer.toString(value) + " kr");
                    viewPanel.add(expenseLabel);

                    inputDescription.setText("");
                    inputCost.setText("");

                } catch (NumberFormatException ex) {
                    errorLabel.setText("Please enter a valid number!");
                }
            }
        });

        inputPanel.add(addNumber);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(balancePanel, BorderLayout.SOUTH);
    }

}
