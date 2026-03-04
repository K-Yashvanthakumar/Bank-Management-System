package bank.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminTransactionHistory extends JFrame implements ActionListener {
    JTable transactionTable;
    JScrollPane scrollPane;
    JButton backButton, searchButton, clearButton, exportButton;
    JTextField fromDateField, toDateField, pinField, cardField;
    JComboBox<String> typeComboBox;
    DefaultTableModel model;
    
    AdminTransactionHistory() {
        setTitle("Transaction History");
        
        // Set layout and size
        setLayout(null);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Set background color
        getContentPane().setBackground(new Color(240, 255, 240));
        
        // Title
        JLabel title = new JLabel("All Transactions");
        title.setFont(new Font("Raleway", Font.BOLD, 30));
        title.setBounds(400, 20, 300, 40);
        add(title);
        
        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(null);
        filterPanel.setBounds(50, 70, 900, 120);
        filterPanel.setBackground(new Color(230, 250, 230));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
        add(filterPanel);
        
        // Date Range Filter
        JLabel fromLabel = new JLabel("From Date (yyyy-MM-dd):");
        fromLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        fromLabel.setBounds(20, 30, 180, 25);
        filterPanel.add(fromLabel);
        
        fromDateField = new JTextField();
        fromDateField.setFont(new Font("Arial", Font.PLAIN, 14));
        fromDateField.setBounds(200, 30, 120, 25);
        filterPanel.add(fromDateField);
        
        JLabel toLabel = new JLabel("To Date (yyyy-MM-dd):");
        toLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        toLabel.setBounds(340, 30, 160, 25);
        filterPanel.add(toLabel);
        
        toDateField = new JTextField();
        toDateField.setFont(new Font("Arial", Font.PLAIN, 14));
        toDateField.setBounds(500, 30, 120, 25);
        filterPanel.add(toDateField);
        
        // Transaction Type Filter
        JLabel typeLabel = new JLabel("Transaction Type:");
        typeLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        typeLabel.setBounds(20, 70, 150, 25);
        filterPanel.add(typeLabel);
        
        String[] types = {"All", "Deposit", "Withdrawl"};
        typeComboBox = new JComboBox<>(types);
        typeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        typeComboBox.setBounds(200, 70, 120, 25);
        filterPanel.add(typeComboBox);
        
        // PIN/Card Filter
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        pinLabel.setBounds(340, 70, 50, 25);
        filterPanel.add(pinLabel);
        
        pinField = new JTextField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setBounds(390, 70, 80, 25);
        filterPanel.add(pinField);
        
        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        cardLabel.setBounds(490, 70, 100, 25);
        filterPanel.add(cardLabel);
        
        cardField = new JTextField();
        cardField.setFont(new Font("Arial", Font.PLAIN, 14));
        cardField.setBounds(590, 70, 150, 25);
        filterPanel.add(cardField);
        
        // Search Button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Raleway", Font.BOLD, 14));
        searchButton.setBounds(750, 30, 100, 25);
        searchButton.setBackground(new Color(65, 125, 128));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        filterPanel.add(searchButton);
        
        // Clear Button
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Raleway", Font.BOLD, 14));
        clearButton.setBounds(750, 70, 100, 25);
        clearButton.setBackground(Color.GRAY);
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(this);
        filterPanel.add(clearButton);
        
        // Create table model with column names
        String[] columns = {"Date", "PIN", "Card Number", "Type", "Amount", "Balance After"};
        model = new DefaultTableModel(columns, 0);
        
        // Create table with the model
        transactionTable = new JTable(model);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionTable.setRowHeight(25);
        
        // Add table to scroll pane
        scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(50, 210, 900, 380);
        add(scrollPane);
        
        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton.setBounds(400, 610, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);
        
        // Export Button
        exportButton = new JButton("Export to CSV");
        exportButton.setFont(new Font("Raleway", Font.BOLD, 14));
        exportButton.setBounds(520, 610, 150, 30);
        exportButton.setBackground(new Color(0, 100, 0));
        exportButton.setForeground(Color.WHITE);
        exportButton.addActionListener(this);
        add(exportButton);
        
        // Load all transactions by default
        loadTransactions();
        
        setVisible(true);
    }
    
    private void loadTransactions() {
        // Clear existing data
        model.setRowCount(0);
        
        try {
            Con c = new Con();
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
            
            if (typeComboBox.getSelectedIndex() > 0) {
                queryBuilder.append("AND b.type = '").append(typeComboBox.getSelectedItem()).append("' ");
            }
            
            if (!pinField.getText().trim().isEmpty()) {
                queryBuilder.append("AND b.pin = '").append(pinField.getText().trim()).append("' ");
            }
            
            if (!cardField.getText().trim().isEmpty()) {
                queryBuilder.append("AND l.card_number = '").append(cardField.getText().trim()).append("' ");
            }
            
            queryBuilder.append("ORDER BY b.date DESC");
            
            ResultSet rs = c.statement.executeQuery(queryBuilder.toString());
            
            while (rs.next()) {
                String date = rs.getString("date");
                String pin = rs.getString("pin");
                String type = rs.getString("type");
                String amount = rs.getString("amount");
                String cardNumber = rs.getString("card_number");
                
                // Calculate balance after this transaction
                int balanceAfter = calculateBalanceAfterTransaction(pin, date, type, Integer.parseInt(amount));
                
                // Mask card number for security
                String maskedCardNumber = cardNumber != null ? 
                    "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4) : "N/A";
                
                // Add row to table
                model.addRow(new Object[]{date, pin, maskedCardNumber, type, "₹" + amount, "₹" + balanceAfter});
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading transaction data: " + e.getMessage());
        }
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
    
    private void clearFilters() {
        fromDateField.setText("");
        toDateField.setText("");
        typeComboBox.setSelectedIndex(0);
        pinField.setText("");
        cardField.setText("");
    }
    
    private void exportToCSV() {
        // This is a simplified version - in a real app, you'd use a file chooser
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = "transactions_export_" + formatter.format(new Date()) + ".csv";
            
            StringBuilder csv = new StringBuilder();
            
            // Add header
            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.append(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) {
                    csv.append(",");
                }
            }
            csv.append("\n");
            
            // Add data
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    csv.append(model.getValueAt(i, j));
                    if (j < model.getColumnCount() - 1) {
                        csv.append(",");
                    }
                }
                csv.append("\n");
            }
            
            // Save to file
            java.io.FileWriter fw = new java.io.FileWriter(fileName);
            fw.write(csv.toString());
            fw.close();
            
            JOptionPane.showMessageDialog(this, "Data exported successfully to " + fileName);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
        } else if (e.getSource() == searchButton) {
            loadTransactions();
        } else if (e.getSource() == clearButton) {
            clearFilters();
            loadTransactions();
        } else if (e.getSource() == exportButton) {
            exportToCSV();
        }
    }
    
    public static void main(String[] args) {
        new AdminTransactionHistory();
    }
}
