import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExpenseManager {

    ArrayList<Expense> list = new ArrayList<>();

    public void addExpense(String category, int amount) {
        list.add(new Expense(category, amount));
    }

    public int getTotalExpense() {
        int total = 0;
        for (Expense e : list) {
            total += e.amount;
        }
        return total;
    }

    public int getPrediction() {
        if (list.isEmpty()) return 0;
        return getTotalExpense() / list.size();
    }

    public void showExpensesToGUI(JTextArea output) {
        if (list.isEmpty()) {
            output.setText("No expenses found!");
            return;
        }
        output.setText("--- Expense List ---\n");
        for (Expense e : list) {
            output.append(e.category + " - ₹" + e.amount + "\n");
        }
    }

    public void categoryWiseToGUI(JTextArea output) {
        int food = 0, travel = 0, shopping = 0, others = 0;
        for (Expense e : list) {
            if (e.category.equalsIgnoreCase("Food")) food += e.amount;
            else if (e.category.equalsIgnoreCase("Travel")) travel += e.amount;
            else if (e.category.equalsIgnoreCase("Shopping")) shopping += e.amount;
            else others += e.amount;
        }
        output.append("\n--- Category Breakdown ---\n");
        output.append("Food: ₹" + food + "\nTravel: ₹" + travel + "\nShopping: ₹" + shopping + "\nOthers: ₹" + others + "\n");
    }

    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        JFrame frame = new JFrame("Smart Expense Tracker");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField catField = new JTextField();
        JTextField amtField = new JTextField();
        JButton addBtn = new JButton("Add Expense");
        JButton viewBtn = new JButton("View All");
        JButton totalBtn = new JButton("Show Total");
        JButton categoryBtn = new JButton("Category Wise");

        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);

        panel.add(new JLabel("Category:")); panel.add(catField);
        panel.add(new JLabel("Amount:")); panel.add(amtField);
        panel.add(addBtn); panel.add(viewBtn);
        panel.add(totalBtn); panel.add(categoryBtn);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setVisible(true);

        addBtn.addActionListener(e -> {
            try {
                String cat = catField.getText().trim();
                int amt = Integer.parseInt(amtField.getText().trim());
                manager.addExpense(cat, amt);
                output.setText("Added Successfully!");
                catField.setText(""); amtField.setText("");
            } catch (Exception ex) {
                output.setText("Error: Enter valid amount!");
            }
        });

        viewBtn.addActionListener(e -> { output.setText(""); manager.showExpensesToGUI(output); });
        totalBtn.addActionListener(e -> { output.setText("Total: ₹" + manager.getTotalExpense()); });
        categoryBtn.addActionListener(e -> { output.setText(""); manager.categoryWiseToGUI(output); });
    }
}