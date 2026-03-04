package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteUser extends JFrame implements ActionListener {
    JTextField searchField;
    JButton searchButton, deleteButton, backButton;
    JRadioButton pinRadio, cardRadio;
    ButtonGroup searchTypeGroup;
    JPanel resultPanel;
    JLabel userInfoLabel;

    // Store user details for deletion
    private String userPin = "";
    private String userCardNumber = "";
    private String userFormNo = "";

    DeleteUser() {
        setTitle("Delete User Account");

        // Set layout and size
        setLayout(null);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(255, 240, 240));

        // Title
        JLabel title = new JLabel("Delete User Account");
        title.setFont(new Font("Raleway", Font.BOLD, 30));
        title.setBounds(200, 20, 300, 40);
        add(title);

        // Warning Label
        JLabel warningLabel = new JLabel("WARNING: This action cannot be undone!");
        warningLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(200, 60, 300, 30);
        add(warningLabel);

        // Search Type Radio Buttons
        JLabel searchTypeLabel = new JLabel("Search By:");
        searchTypeLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        searchTypeLabel.setBounds(100, 110, 100, 30);
        add(searchTypeLabel);

        pinRadio = new JRadioButton("PIN");
        pinRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        pinRadio.setBounds(200, 110, 60, 30);
        pinRadio.setBackground(new Color(255, 240, 240));
        pinRadio.setSelected(true);
        add(pinRadio);

        cardRadio = new JRadioButton("Card Number");
        cardRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        cardRadio.setBounds(270, 110, 120, 30);
        cardRadio.setBackground(new Color(255, 240, 240));
        add(cardRadio);

        searchTypeGroup = new ButtonGroup();
        searchTypeGroup.add(pinRadio);
        searchTypeGroup.add(cardRadio);

        // Search Field
        JLabel searchLabel = new JLabel("Enter Value:");
        searchLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        searchLabel.setBounds(100, 150, 100, 30);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBounds(200, 150, 250, 30);
        add(searchField);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Raleway", Font.BOLD, 14));
        searchButton.setBounds(470, 150, 100, 30);
        searchButton.setBackground(new Color(65, 125, 128));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        add(searchButton);

        // Result Panel
        resultPanel = new JPanel();
        resultPanel.setLayout(null);
        resultPanel.setBounds(100, 200, 500, 150);
        resultPanel.setBackground(new Color(255, 230, 230));
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resultPanel.setVisible(false);
        add(resultPanel);

        // User Info Label (will be populated when user is found)
        userInfoLabel = new JLabel();
        userInfoLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        userInfoLabel.setBounds(20, 20, 460, 80);
        userInfoLabel.setVerticalAlignment(SwingConstants.TOP);
        resultPanel.add(userInfoLabel);

        // Delete Button
        deleteButton = new JButton("DELETE USER");
        deleteButton.setFont(new Font("Raleway", Font.BOLD, 14));
        deleteButton.setBounds(180, 100, 150, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(this);
        deleteButton.setVisible(false);
        resultPanel.add(deleteButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton.setBounds(300, 380, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    private void searchUser() {
        // Reset user details
        userPin = "";
        userCardNumber = "";
        userFormNo = "";

        // Hide result panel and delete button
        resultPanel.setVisible(false);
        deleteButton.setVisible(false);

        String searchValue = searchField.getText().trim();

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search value");
            return;
        }

        try {
            Con c = new Con();
            String query;

            if (pinRadio.isSelected()) {
                query = "SELECT formno, name, card_number, pin " +
                        "FROM signup " +
                        "WHERE pin = '" + searchValue + "' OR TRIM(pin) = '" + searchValue + "'";
            } else {
                query = "SELECT formno, name, card_number, pin " +
                        "FROM signup " +
                        "WHERE card_number = '" + searchValue + "' OR TRIM(card_number) = '" + searchValue + "'";
            }

            System.out.println("=== DELETE USER DEBUG ===");
            System.out.println("Search Value: " + searchValue);
            System.out.println("Query: " + query);

            ResultSet rs = c.statement.executeQuery(query);

            if (rs.next()) {
                System.out.println("✓ User found for deletion");
                // User found, display details
                userFormNo = rs.getString("formno");
                String name = rs.getString("name");
                userCardNumber = rs.getString("card_number");
                userPin = rs.getString("pin");

                // Calculate balance
                int balance = calculateBalance(userPin);

                // Mask card number for security
                String maskedCardNumber = "XXXX-XXXX-XXXX-" + userCardNumber.substring(userCardNumber.length() - 4);

                // Set user info text
                userInfoLabel.setText("<html>User Found:<br>" +
                                     "Name: " + name + "<br>" +
                                     "Card Number: " + maskedCardNumber + "<br>" +
                                     "PIN: " + userPin + "<br>" +
                                     "Current Balance: ₹" + balance + "</html>");

                // Show result panel and delete button
                resultPanel.setVisible(true);
                deleteButton.setVisible(true);
            } else {
                System.out.println("❌ No user found for deletion");

                // Debug: Check total users
                ResultSet countRs = c.statement.executeQuery("SELECT COUNT(*) as count FROM signup");
                if (countRs.next()) {
                    System.out.println("Total users in database: " + countRs.getInt("count"));
                }

                JOptionPane.showMessageDialog(this, "No user found with the given " +
                                             (pinRadio.isSelected() ? "PIN" : "Card Number") +
                                             "\n\nSearch Value: " + searchValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for user: " + e.getMessage());
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

    private void deleteUser() {
        if (userPin.isEmpty() || userCardNumber.isEmpty() || userFormNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No user selected for deletion");
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this user?\nThis action cannot be undone!",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Con c = new Con();

            // Delete from bank table (transactions)
            c.statement.executeUpdate("DELETE FROM bank WHERE pin = '" + userPin + "'");

            // Delete from login table
            c.statement.executeUpdate("DELETE FROM login WHERE card_number = '" + userCardNumber + "' AND pin = '" + userPin + "'");

            // Delete from signup table (now contains all user data)
            c.statement.executeUpdate("DELETE FROM signup WHERE formno = '" + userFormNo + "'");

            JOptionPane.showMessageDialog(this, "User account deleted successfully");

            // Reset UI
            searchField.setText("");
            resultPanel.setVisible(false);

            // Reset user details
            userPin = "";
            userCardNumber = "";
            userFormNo = "";

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchUser();
        } else if (e.getSource() == deleteButton) {
            deleteUser();
        } else if (e.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new DeleteUser();
    }
}
