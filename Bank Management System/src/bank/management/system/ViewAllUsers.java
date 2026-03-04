package bank.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewAllUsers extends JFrame implements ActionListener {
    JTable userTable;
    JScrollPane scrollPane;
    JButton backButton, refreshButton;
    DefaultTableModel model;

    ViewAllUsers() {
        setTitle("All Users");

        // Set layout and size
        setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(255, 255, 240));

        // Title
        JLabel title = new JLabel("All Registered Users");
        title.setFont(new Font("Raleway", Font.BOLD, 30));
        title.setBounds(350, 20, 300, 40);
        add(title);

        // Create table model with column names
        String[] columns = {"Name", "Card Number", "PIN", "Balance", "Account Type", "Creation Date"};
        model = new DefaultTableModel(columns, 0);

        // Create table with the model
        userTable = new JTable(model);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.setRowHeight(25);

        // Add table to scroll pane
        scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(50, 80, 900, 400);
        add(scrollPane);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton.setBounds(400, 500, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        // Refresh Button
        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Raleway", Font.BOLD, 14));
        refreshButton.setBounds(520, 500, 100, 30);
        refreshButton.setBackground(Color.BLACK);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(this);
        add(refreshButton);

        // Load user data
        loadUserData();

        setVisible(true);
    }

    private void loadUserData() {
        // Clear existing data
        model.setRowCount(0);

        try {
            Con c = new Con();

            // Query from single signup table
            String query = "SELECT name, card_number, pin, accounttype, formno " +
                          "FROM signup ORDER BY formno DESC";

            ResultSet rs = c.statement.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name") != null ? rs.getString("name") : "N/A";
                String cardNumber = rs.getString("card_number") != null ? rs.getString("card_number") : "N/A";
                String pin = rs.getString("pin") != null ? rs.getString("pin") : "N/A";
                String accountType = rs.getString("accounttype") != null ? rs.getString("accounttype") : "N/A";
                String formno = rs.getString("formno") != null ? rs.getString("formno") : "N/A";

                // Calculate balance for each user
                int balance = calculateBalance(pin);

                // Get account creation date (using formno as reference)
                String creationDate = getAccountCreationDate(formno);

                // Mask card number for security (show only last 4 digits)
                String maskedCardNumber;
                if (cardNumber != null && !cardNumber.equals("N/A") && cardNumber.length() >= 4) {
                    maskedCardNumber = "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4);
                } else {
                    maskedCardNumber = "N/A";
                }

                // Add row to table
                model.addRow(new Object[]{name, maskedCardNumber, pin, "₹" + balance, accountType, creationDate});
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading user data: " + e.getMessage());
        }
    }

    private int calculateBalance(String pin) {
        int balance = 0;
        if (pin == null || pin.equals("N/A")) {
            return balance;
        }

        try {
            Con c = new Con();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");

            while (rs.next()) {
                String type = rs.getString("type");
                String amountStr = rs.getString("amount");

                if (type != null && amountStr != null) {
                    try {
                        int amount = Integer.parseInt(amountStr);
                        if (type.equalsIgnoreCase("Deposit")) {
                            balance += amount;
                        } else {
                            balance -= amount;
                        }
                    } catch (NumberFormatException nfe) {
                        // Skip invalid amount entries
                        System.err.println("Invalid amount format: " + amountStr);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error calculating balance for PIN " + pin + ": " + e.getMessage());
        }
        return balance;
    }

    private String getAccountCreationDate(String formno) {
        String date = "N/A";
        try {
            Con c = new Con();
            // Get creation date from signup table
            ResultSet rs = c.statement.executeQuery("SELECT created_at FROM signup WHERE formno = '" + formno + "'");

            if (rs.next() && rs.getString("created_at") != null) {
                date = rs.getString("created_at").substring(0, 10); // Extract date part
            } else {
                // If no creation date, use current date as fallback
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.format(new Date());
            }
        } catch (Exception e) {
            // If error, try to get first transaction date
            try {
                Con c2 = new Con();
                ResultSet rs2 = c2.statement.executeQuery("SELECT MIN(date) as creation_date FROM bank WHERE pin = (SELECT pin FROM signup WHERE formno = '" + formno + "')");
                if (rs2.next() && rs2.getString("creation_date") != null) {
                    date = rs2.getString("creation_date");
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    date = formatter.format(new Date());
                }
            } catch (Exception e2) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.format(new Date());
            }
        }
        return date;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
        } else if (e.getSource() == refreshButton) {
            loadUserData();
        }
    }

    public static void main(String[] args) {
        new ViewAllUsers();
    }
}
