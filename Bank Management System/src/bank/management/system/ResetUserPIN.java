package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class ResetUserPIN extends JFrame implements ActionListener {
    JTextField searchField, newPinField, confirmPinField;
    JButton searchButton, resetButton, backButton, generateButton;
    JRadioButton pinRadio, cardRadio;
    ButtonGroup searchTypeGroup;
    JPanel resultPanel;
    JLabel userInfoLabel;

    // Store user details for PIN reset
    private String userPin = "";
    private String userCardNumber = "";
    private String userFormNo = "";
    private String userName = "";

    ResetUserPIN() {
        setTitle("Reset User PIN");

        // Set layout and size
        setLayout(null);
        setSize(700, 550);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(240, 240, 255));

        // Title
        JLabel title = new JLabel("Reset User PIN");
        title.setFont(new Font("Raleway", Font.BOLD, 30));
        title.setBounds(250, 20, 200, 40);
        add(title);

        // Search Type Radio Buttons
        JLabel searchTypeLabel = new JLabel("Search By:");
        searchTypeLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        searchTypeLabel.setBounds(100, 80, 100, 30);
        add(searchTypeLabel);

        pinRadio = new JRadioButton("PIN");
        pinRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        pinRadio.setBounds(200, 80, 60, 30);
        pinRadio.setBackground(new Color(240, 240, 255));
        pinRadio.setSelected(true);
        add(pinRadio);

        cardRadio = new JRadioButton("Card Number");
        cardRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        cardRadio.setBounds(270, 80, 120, 30);
        cardRadio.setBackground(new Color(240, 240, 255));
        add(cardRadio);

        searchTypeGroup = new ButtonGroup();
        searchTypeGroup.add(pinRadio);
        searchTypeGroup.add(cardRadio);

        // Search Field
        JLabel searchLabel = new JLabel("Enter Value:");
        searchLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        searchLabel.setBounds(100, 120, 100, 30);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBounds(200, 120, 250, 30);
        add(searchField);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Raleway", Font.BOLD, 14));
        searchButton.setBounds(470, 120, 100, 30);
        searchButton.setBackground(new Color(65, 125, 128));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        add(searchButton);

        // Result Panel
        resultPanel = new JPanel();
        resultPanel.setLayout(null);
        resultPanel.setBounds(100, 170, 500, 280);
        resultPanel.setBackground(new Color(230, 230, 250));
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resultPanel.setVisible(false);
        add(resultPanel);

        // User Info Label (will be populated when user is found)
        userInfoLabel = new JLabel();
        userInfoLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        userInfoLabel.setBounds(20, 20, 460, 60);
        userInfoLabel.setVerticalAlignment(SwingConstants.TOP);
        resultPanel.add(userInfoLabel);

        // New PIN Field
        JLabel newPinLabel = new JLabel("New PIN:");
        newPinLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        newPinLabel.setBounds(20, 90, 100, 30);
        resultPanel.add(newPinLabel);

        newPinField = new JTextField();
        newPinField.setFont(new Font("Arial", Font.PLAIN, 14));
        newPinField.setBounds(120, 90, 150, 30);
        resultPanel.add(newPinField);

        // Generate PIN Button
        generateButton = new JButton("Generate PIN");
        generateButton.setFont(new Font("Raleway", Font.BOLD, 14));
        generateButton.setBounds(290, 90, 150, 30);
        generateButton.setBackground(new Color(100, 100, 200));
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(this);
        resultPanel.add(generateButton);

        // Confirm PIN Field
        JLabel confirmPinLabel = new JLabel("Confirm PIN:");
        confirmPinLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        confirmPinLabel.setBounds(20, 140, 100, 30);
        resultPanel.add(confirmPinLabel);

        confirmPinField = new JTextField();
        confirmPinField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPinField.setBounds(120, 140, 150, 30);
        resultPanel.add(confirmPinField);

        // Reset Button
        resetButton = new JButton("Reset PIN");
        resetButton.setFont(new Font("Raleway", Font.BOLD, 14));
        resetButton.setBounds(180, 200, 150, 30);
        resetButton.setBackground(new Color(0, 100, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(this);
        resultPanel.add(resetButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton.setBounds(300, 470, 100, 30);
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
        userName = "";

        // Hide result panel
        resultPanel.setVisible(false);

        // Clear PIN fields
        newPinField.setText("");
        confirmPinField.setText("");

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
                        "WHERE pin = '" + searchValue + "'";
            } else {
                query = "SELECT formno, name, card_number, pin " +
                        "FROM signup " +
                        "WHERE card_number = '" + searchValue + "'";
            }

            ResultSet rs = c.statement.executeQuery(query);

            if (rs.next()) {
                // User found, display details
                userFormNo = rs.getString("formno");
                userName = rs.getString("name");
                userCardNumber = rs.getString("card_number");
                userPin = rs.getString("pin");

                // Mask card number for security
                String maskedCardNumber = "XXXX-XXXX-XXXX-" + userCardNumber.substring(userCardNumber.length() - 4);

                // Set user info text
                userInfoLabel.setText("<html>User Found:<br>" +
                                     "Name: " + userName + "<br>" +
                                     "Card Number: " + maskedCardNumber + "<br>" +
                                     "Current PIN: " + userPin + "</html>");

                // Show result panel
                resultPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No user found with the given " +
                                             (pinRadio.isSelected() ? "PIN" : "Card Number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for user: " + e.getMessage());
        }
    }

    private void generateRandomPIN() {
        Random ran = new Random();
        long first3 = (ran.nextLong() % 9000L) + 1000L;
        String pin = "" + Math.abs(first3);

        newPinField.setText(pin);
        confirmPinField.setText(pin);
    }

    private void resetPIN() {
        if (userPin.isEmpty() || userCardNumber.isEmpty() || userFormNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No user selected");
            return;
        }

        String newPin = newPinField.getText().trim();
        String confirmPin = confirmPinField.getText().trim();

        if (newPin.isEmpty() || confirmPin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter and confirm the new PIN");
            return;
        }

        if (!newPin.equals(confirmPin)) {
            JOptionPane.showMessageDialog(this, "PINs do not match");
            return;
        }

        if (newPin.length() != 4 || !newPin.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "PIN must be a 4-digit number");
            return;
        }

        if (newPin.equals(userPin)) {
            JOptionPane.showMessageDialog(this, "New PIN cannot be the same as the current PIN");
            return;
        }

        try {
            Con c = new Con();

            // Update PIN in all relevant tables
            String q1 = "UPDATE bank SET pin = '" + newPin + "' WHERE pin = '" + userPin + "'";
            String q2 = "UPDATE login SET pin = '" + newPin + "' WHERE pin = '" + userPin + "'";
            String q3 = "UPDATE signup SET pin = '" + newPin + "' WHERE pin = '" + userPin + "'";

            c.statement.executeUpdate(q1);
            c.statement.executeUpdate(q2);
            c.statement.executeUpdate(q3);

            JOptionPane.showMessageDialog(this, "PIN reset successfully for " + userName);

            // Reset UI
            searchField.setText("");
            newPinField.setText("");
            confirmPinField.setText("");
            resultPanel.setVisible(false);

            // Reset user details
            userPin = "";
            userCardNumber = "";
            userFormNo = "";
            userName = "";

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error resetting PIN: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchUser();
        } else if (e.getSource() == generateButton) {
            generateRandomPIN();
        } else if (e.getSource() == resetButton) {
            resetPIN();
        } else if (e.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ResetUserPIN();
    }
}
