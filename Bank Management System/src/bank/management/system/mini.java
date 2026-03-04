package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class mini extends JFrame implements ActionListener {
    String pin;
    JButton exitBtn, showAllBtn, filterBtn;
    JTextField fromDateField, toDateField;
    JLabel transactionLabel, balanceLabel;

    mini(String pin) {
        this.pin = pin;

        setTitle("Mini Statement");
        getContentPane().setBackground(new Color(255, 204, 204));
        setSize(550, 700);
        setLocation(100, 50);
        setLayout(null);

        JLabel title = new JLabel("TechCoder A.V");
        title.setFont(new Font("System", Font.BOLD, 18));
        title.setBounds(190, 10, 200, 25);
        add(title);

        JLabel cardLabel = new JLabel();
        cardLabel.setBounds(20, 50, 400, 20);
        add(cardLabel);

        JLabel fromLabel = new JLabel("From Date (yyyy-MM-dd):");
        fromLabel.setFont(new Font("Raleway", Font.BOLD, 12));
        fromLabel.setBounds(20, 90, 150, 20);
        add(fromLabel);

        fromDateField = new JTextField();
        fromDateField.setFont(new Font("Arial", Font.PLAIN, 12));
        fromDateField.setBounds(170, 90, 120, 20);
        fromDateField.setToolTipText("Format: yyyy-MM-dd (e.g., 2024-01-15)");
        add(fromDateField);

        JLabel toLabel = new JLabel("To Date (yyyy-MM-dd):");
        toLabel.setFont(new Font("Raleway", Font.BOLD, 12));
        toLabel.setBounds(20, 120, 150, 20);
        add(toLabel);

        toDateField = new JTextField();
        toDateField.setFont(new Font("Arial", Font.PLAIN, 12));
        toDateField.setBounds(170, 120, 120, 20);
        toDateField.setToolTipText("Format: yyyy-MM-dd (e.g., 2024-12-31)");
        add(toDateField);

        filterBtn = new JButton("Date-to-Date Statement");
        filterBtn.setBounds(310, 90, 200, 25);
        filterBtn.setBackground(new Color(0, 100, 0));
        filterBtn.setForeground(Color.WHITE);
        filterBtn.setFont(new Font("Raleway", Font.BOLD, 12));
        filterBtn.addActionListener(e -> showStatement(true));
        add(filterBtn);

        showAllBtn = new JButton("Show Full Statement");
        showAllBtn.setBounds(20, 160, 150, 25);
        showAllBtn.setBackground(new Color(0, 0, 150));
        showAllBtn.setForeground(Color.WHITE);
        showAllBtn.setFont(new Font("Raleway", Font.BOLD, 12));
        showAllBtn.addActionListener(e -> showStatement(false));
        add(showAllBtn);

        JButton clearBtn = new JButton("Clear Dates");
        clearBtn.setBounds(310, 120, 100, 25);
        clearBtn.setBackground(Color.GRAY);
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Raleway", Font.BOLD, 12));
        clearBtn.addActionListener(e -> {
            fromDateField.setText("");
            toDateField.setText("");
            transactionLabel.setText("");
            balanceLabel.setText("");
        });
        add(clearBtn);

        JButton todayBtn = new JButton("Today");
        todayBtn.setBounds(420, 120, 90, 25);
        todayBtn.setBackground(new Color(150, 75, 0));
        todayBtn.setForeground(Color.WHITE);
        todayBtn.setFont(new Font("Raleway", Font.BOLD, 12));
        todayBtn.addActionListener(e -> {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(new java.util.Date());
            fromDateField.setText(today);
            toDateField.setText(today);
        });
        add(todayBtn);

        transactionLabel = new JLabel();
        transactionLabel.setVerticalAlignment(SwingConstants.TOP);
        transactionLabel.setBounds(20, 200, 500, 350);
        add(transactionLabel);

        balanceLabel = new JLabel();
        balanceLabel.setBounds(20, 560, 400, 20);
        add(balanceLabel);

        exitBtn = new JButton("Back");
        exitBtn.setBounds(20, 600, 100, 30);
        exitBtn.setBackground(Color.BLACK);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Raleway", Font.BOLD, 12));
        exitBtn.addActionListener(e -> {
            setVisible(false);
            new main_Class(pin);
        });
        add(exitBtn);

        JButton printBtn = new JButton("Print Statement");
        printBtn.setBounds(180, 160, 150, 25);
        printBtn.setBackground(new Color(128, 0, 128));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFont(new Font("Raleway", Font.BOLD, 12));
        printBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Statement printed successfully!\n(Feature simulated)");
        });
        add(printBtn);

        // Fetch and show card number
        try {
            Con c = new Con();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM login WHERE pin = '" + pin + "'");
            if (rs.next()) {
                String card = rs.getString("card_number");
                cardLabel.setText("Card Number: " + card.substring(0, 4) + "XXXXXXXX" + card.substring(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public void showStatement(boolean isFiltered) {
        String fromDate = fromDateField.getText().trim();
        String toDate = toDateField.getText().trim();

        if (isFiltered) {
            if (fromDate.isEmpty() || toDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both From and To dates in yyyy-MM-dd format.");
                return;
            }

            // Validate date format
            if (!fromDate.matches("\\d{4}-\\d{2}-\\d{2}") || !toDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Please enter dates in yyyy-MM-dd format (e.g., 2024-01-15).");
                return;
            }
        }

        try {
            Con c = new Con();

            // First, calculate total balance (all transactions)
            String balanceQuery = "SELECT * FROM bank WHERE pin = '" + pin + "' ORDER BY date ASC";
            ResultSet balanceRs = c.statement.executeQuery(balanceQuery);
            int totalBalance = 0;
            while (balanceRs.next()) {
                String type = balanceRs.getString("type");
                int amount = Integer.parseInt(balanceRs.getString("amount"));
                if (type.equalsIgnoreCase("Deposit")) {
                    totalBalance += amount;
                } else {
                    totalBalance -= amount;
                }
            }

            // Now get filtered or all transactions
            String query;
            if (isFiltered) {
                query = "SELECT * FROM bank WHERE pin = '" + pin + "' AND date BETWEEN '" + fromDate + "' AND '" + toDate + "' ORDER BY date DESC";
            } else {
                query = "SELECT * FROM bank WHERE pin = '" + pin + "' ORDER BY date DESC";
            }

            ResultSet rs = c.statement.executeQuery(query);
            StringBuilder history = new StringBuilder("<html><body style='font-family: Arial; font-size: 12px;'>");
            int periodBalance = 0;
            boolean found = false;
            int transactionCount = 0;

            // Add header
            if (isFiltered) {
                history.append("<h3>Mini Statement (").append(fromDate).append(" to ").append(toDate).append(")</h3>");
            } else {
                history.append("<h3>Complete Mini Statement</h3>");
            }

            history.append("<table border='1' cellpadding='5' cellspacing='0' width='100%'>");
            history.append("<tr style='background-color: #f0f0f0;'>");
            history.append("<th>Date</th><th>Type</th><th>Amount</th></tr>");

            // Show transactions
            while (rs.next()) {
                found = true;
                transactionCount++;
                String date = rs.getString("date");
                String type = rs.getString("type");
                int amount = Integer.parseInt(rs.getString("amount"));

                String rowColor = type.equalsIgnoreCase("Deposit") ? "#e8f5e8" : "#ffe8e8";
                history.append("<tr style='background-color: ").append(rowColor).append(";'>");
                history.append("<td>").append(date).append("</td>");
                history.append("<td>").append(type).append("</td>");
                history.append("<td>₹").append(amount).append("</td>");
                history.append("</tr>");

                if (type.equalsIgnoreCase("Deposit")) {
                    periodBalance += amount;
                } else {
                    periodBalance -= amount;
                }
            }

            history.append("</table>");

            if (found) {
                history.append("<br><b>Transactions shown: ").append(transactionCount).append("</b>");
                transactionLabel.setText(history.append("</body></html>").toString());

                if (isFiltered) {
                    balanceLabel.setText("<html><b>Period Balance: ₹" + periodBalance + " | Total Account Balance: ₹" + totalBalance + "</b></html>");
                } else {
                    balanceLabel.setText("<html><b>Total Account Balance: ₹" + totalBalance + "</b></html>");
                }
            } else {
                if (isFiltered) {
                    transactionLabel.setText("<html><body><h3>No Transactions Found</h3>" +
                        "<p>No transactions found between <b>" + fromDate + "</b> and <b>" + toDate + "</b></p>" +
                        "<p>Please check the date range and try again.</p></body></html>");
                } else {
                    transactionLabel.setText("<html><body><h3>No Transactions</h3>" +
                        "<p>No transactions found for this account.</p>" +
                        "<p>Please make a deposit or withdrawal first.</p></body></html>");
                }
                balanceLabel.setText("<html><b>Total Account Balance: ₹" + totalBalance + "</b></html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            transactionLabel.setText("<html><body><h3>Error</h3>" +
                "<p><b>Error retrieving statement:</b></p>" +
                "<p>" + e.getMessage() + "</p>" +
                "<p>Please check your database connection and try again.</p></body></html>");
            balanceLabel.setText("");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new mini("1234"); // Pass valid pin
    }
}
