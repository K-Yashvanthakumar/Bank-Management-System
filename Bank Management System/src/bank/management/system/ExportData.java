package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;

public class ExportData extends JFrame implements ActionListener {
    JRadioButton usersRadio, transactionsRadio;
    ButtonGroup exportTypeGroup;
    JTextField fromDateField, toDateField, pinField, cardField;
    JButton exportButton, backButton;
    JCheckBox includePersonalCheck, includeFinancialCheck;
    JComboBox<String> transactionTypeCombo;

    ExportData() {
        setTitle("Export Data");

        // Set layout and size
        setLayout(null);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(240, 255, 255));

        // Title
        JLabel title = new JLabel("Export Data to CSV");
        title.setFont(new Font("Raleway", Font.BOLD, 30));
        title.setBounds(220, 20, 300, 40);
        add(title);

        // Export Type Panel
        JPanel typePanel = new JPanel();
        typePanel.setLayout(null);
        typePanel.setBounds(50, 80, 600, 60);
        typePanel.setBackground(new Color(230, 250, 250));
        typePanel.setBorder(BorderFactory.createTitledBorder("Export Type"));
        add(typePanel);

        // Export Type Radio Buttons
        usersRadio = new JRadioButton("Users Data");
        usersRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        usersRadio.setBounds(100, 20, 100, 25);
        usersRadio.setBackground(new Color(230, 250, 250));
        usersRadio.setSelected(true);
        usersRadio.addActionListener(this);
        typePanel.add(usersRadio);

        transactionsRadio = new JRadioButton("Transactions Data");
        transactionsRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        transactionsRadio.setBounds(300, 20, 200, 25);
        transactionsRadio.setBackground(new Color(230, 250, 250));
        transactionsRadio.addActionListener(this);
        typePanel.add(transactionsRadio);

        exportTypeGroup = new ButtonGroup();
        exportTypeGroup.add(usersRadio);
        exportTypeGroup.add(transactionsRadio);

        // Users Options Panel
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(null);
        usersPanel.setBounds(50, 150, 600, 100);
        usersPanel.setBackground(new Color(230, 250, 250));
        usersPanel.setBorder(BorderFactory.createTitledBorder("User Data Options"));
        add(usersPanel);

        // Include options
        includePersonalCheck = new JCheckBox("Include Personal Details");
        includePersonalCheck.setFont(new Font("Raleway", Font.PLAIN, 14));
        includePersonalCheck.setBounds(50, 30, 200, 25);
        includePersonalCheck.setBackground(new Color(230, 250, 250));
        includePersonalCheck.setSelected(true);
        usersPanel.add(includePersonalCheck);

        includeFinancialCheck = new JCheckBox("Include Financial Details");
        includeFinancialCheck.setFont(new Font("Raleway", Font.PLAIN, 14));
        includeFinancialCheck.setBounds(300, 30, 200, 25);
        includeFinancialCheck.setBackground(new Color(230, 250, 250));
        includeFinancialCheck.setSelected(true);
        usersPanel.add(includeFinancialCheck);

        // Specific user filter
        JLabel userFilterLabel = new JLabel("Filter by Card Number (optional):");
        userFilterLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        userFilterLabel.setBounds(50, 60, 220, 25);
        usersPanel.add(userFilterLabel);

        cardField = new JTextField();
        cardField.setFont(new Font("Arial", Font.PLAIN, 14));
        cardField.setBounds(280, 60, 250, 25);
        usersPanel.add(cardField);

        // Transactions Options Panel
        JPanel transactionsPanel = new JPanel();
        transactionsPanel.setLayout(null);
        transactionsPanel.setBounds(50, 260, 600, 130);
        transactionsPanel.setBackground(new Color(230, 250, 250));
        transactionsPanel.setBorder(BorderFactory.createTitledBorder("Transaction Data Options"));
        transactionsPanel.setVisible(false);
        add(transactionsPanel);

        // Date Range Filter
        JLabel fromLabel = new JLabel("From Date (yyyy-MM-dd):");
        fromLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        fromLabel.setBounds(20, 30, 180, 25);
        transactionsPanel.add(fromLabel);

        fromDateField = new JTextField();
        fromDateField.setFont(new Font("Arial", Font.PLAIN, 14));
        fromDateField.setBounds(200, 30, 120, 25);
        transactionsPanel.add(fromDateField);

        JLabel toLabel = new JLabel("To Date (yyyy-MM-dd):");
        toLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        toLabel.setBounds(330, 30, 160, 25);
        transactionsPanel.add(toLabel);

        toDateField = new JTextField();
        toDateField.setFont(new Font("Arial", Font.PLAIN, 14));
        toDateField.setBounds(490, 30, 120, 25);
        transactionsPanel.add(toDateField);

        // Transaction Type Filter
        JLabel typeLabel = new JLabel("Transaction Type:");
        typeLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        typeLabel.setBounds(20, 70, 150, 25);
        transactionsPanel.add(typeLabel);

        String[] types = {"All", "Deposit", "Withdrawl"};
        transactionTypeCombo = new JComboBox<>(types);
        transactionTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTypeCombo.setBounds(170, 70, 120, 25);
        transactionsPanel.add(transactionTypeCombo);

        // PIN Filter
        JLabel pinLabel = new JLabel("Filter by PIN (optional):");
        pinLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        pinLabel.setBounds(300, 70, 160, 25);
        transactionsPanel.add(pinLabel);

        pinField = new JTextField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setBounds(460, 70, 120, 25);
        transactionsPanel.add(pinField);

        // Export Button
        exportButton = new JButton("Export to CSV");
        exportButton.setFont(new Font("Raleway", Font.BOLD, 14));
        exportButton.setBounds(250, 410, 150, 30);
        exportButton.setBackground(new Color(0, 100, 0));
        exportButton.setForeground(Color.WHITE);
        exportButton.addActionListener(this);
        add(exportButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton.setBounds(420, 410, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    private void exportUsersData() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = "users_export_" + formatter.format(new Date()) + ".csv";

            StringBuilder csv = new StringBuilder();

            // Build query based on options
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT formno, name, fname, dob, gender, email, marital, ");
            queryBuilder.append("address, city, pincode, state, ");
            queryBuilder.append("accounttype, card_number, pin, facility ");
            queryBuilder.append("FROM signup ");

            // Apply card number filter if provided
            if (!cardField.getText().trim().isEmpty()) {
                queryBuilder.append("WHERE card_number = '").append(cardField.getText().trim()).append("' ");
            }

            Con c = new Con();
            ResultSet rs = c.statement.executeQuery(queryBuilder.toString());

            // Build CSV header
            csv.append("Form No,Name");

            if (includePersonalCheck.isSelected()) {
                csv.append(",Father's Name,DOB,Gender,Email,Marital Status,Address,City,Pincode,State");
            }

            if (includeFinancialCheck.isSelected()) {
                csv.append(",Account Type,Card Number,PIN,Facilities,Balance");
            }

            csv.append("\n");

            // Add data rows
            while (rs.next()) {
                String formno = rs.getString("formno");
                String name = rs.getString("name");

                csv.append(formno).append(",").append(name);

                if (includePersonalCheck.isSelected()) {
                    csv.append(",").append(rs.getString("fname"));
                    csv.append(",").append(rs.getString("dob"));
                    csv.append(",").append(rs.getString("gender"));
                    csv.append(",").append(rs.getString("email"));
                    csv.append(",").append(rs.getString("marital"));
                    csv.append(",").append(rs.getString("address"));
                    csv.append(",").append(rs.getString("city"));
                    csv.append(",").append(rs.getString("pincode"));
                    csv.append(",").append(rs.getString("state"));
                }

                if (includeFinancialCheck.isSelected()) {
                    csv.append(",").append(rs.getString("accounttype"));
                    csv.append(",").append(rs.getString("card_number"));
                    csv.append(",").append(rs.getString("pin"));
                    csv.append(",").append(rs.getString("facility"));

                    // Calculate balance
                    int balance = calculateBalance(rs.getString("pin"));
                    csv.append(",").append(balance);
                }

                csv.append("\n");
            }

            // Save to file
            FileWriter fw = new FileWriter(fileName);
            fw.write(csv.toString());
            fw.close();

            JOptionPane.showMessageDialog(this, "Users data exported successfully to " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting users data: " + e.getMessage());
        }
    }

    private void exportTransactionsData() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = "transactions_export_" + formatter.format(new Date()) + ".csv";

            StringBuilder csv = new StringBuilder();

            // Build query based on options
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT b.date, b.pin, b.type, b.amount, l.card_number ");
            queryBuilder.append("FROM bank b ");
            queryBuilder.append("LEFT JOIN login l ON b.pin = l.pin ");
            queryBuilder.append("WHERE 1=1 ");

            // Apply filters
            if (!fromDateField.getText().trim().isEmpty() && !toDateField.getText().trim().isEmpty()) {
                queryBuilder.append("AND b.date BETWEEN '").append(fromDateField.getText().trim())
                           .append("' AND '").append(toDateField.getText().trim()).append("' ");
            }

            if (transactionTypeCombo.getSelectedIndex() > 0) {
                queryBuilder.append("AND b.type = '").append(transactionTypeCombo.getSelectedItem()).append("' ");
            }

            if (!pinField.getText().trim().isEmpty()) {
                queryBuilder.append("AND b.pin = '").append(pinField.getText().trim()).append("' ");
            }

            queryBuilder.append("ORDER BY b.date DESC");

            Con c = new Con();
            ResultSet rs = c.statement.executeQuery(queryBuilder.toString());

            // Build CSV header
            csv.append("Date,PIN,Card Number,Transaction Type,Amount,Balance After\n");

            // Add data rows
            while (rs.next()) {
                String date = rs.getString("date");
                String pin = rs.getString("pin");
                String type = rs.getString("type");
                String amount = rs.getString("amount");
                String cardNumber = rs.getString("card_number");

                // Calculate balance after this transaction
                int balanceAfter = calculateBalanceAfterTransaction(pin, date, type, Integer.parseInt(amount));

                csv.append(date).append(",");
                csv.append(pin).append(",");
                csv.append(cardNumber != null ? cardNumber : "N/A").append(",");
                csv.append(type).append(",");
                csv.append(amount).append(",");
                csv.append(balanceAfter).append("\n");
            }

            // Save to file
            FileWriter fw = new FileWriter(fileName);
            fw.write(csv.toString());
            fw.close();

            JOptionPane.showMessageDialog(this, "Transactions data exported successfully to " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting transactions data: " + e.getMessage());
        }
    }

    private int calculateBalance(String pin) {
        int balance = 0;
        try {
            Con c = new Con();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");

            while (rs.next()) {
                if (rs.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }

    private int calculateBalanceAfterTransaction(String pin, String transactionDate, String transactionType, int transactionAmount) {
        int balance = 0;
        try {
            Con c = new Con();
            // Get all transactions up to and including this one
            String query = "SELECT * FROM bank WHERE pin = '" + pin + "' AND date <= '" + transactionDate + "' ORDER BY date ASC";
            ResultSet rs = c.statement.executeQuery(query);

            while (rs.next()) {
                String type = rs.getString("type");
                int amount = Integer.parseInt(rs.getString("amount"));

                if (type.equals("Deposit")) {
                    balance += amount;
                } else {
                    balance -= amount;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }

    // Reference to the transactions panel
    JPanel transactionsPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == usersRadio) {
            // Show users panel, hide transactions panel
            transactionsPanel.setVisible(false);
        } else if (e.getSource() == transactionsRadio) {
            // Show transactions panel, hide users panel
            transactionsPanel.setVisible(true);
        } else if (e.getSource() == exportButton) {
            if (usersRadio.isSelected()) {
                exportUsersData();
            } else {
                exportTransactionsData();
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ExportData();
    }
}
