package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchUser extends JFrame implements ActionListener {
    JTextField searchField;
    JButton searchButton, clearButton, backButton;
    JRadioButton pinRadio, cardRadio;
    ButtonGroup searchTypeGroup;
    JPanel resultPanel;

    SearchUser() {
        setTitle("Search User");

        // Set layout and size
        setLayout(null);
        setSize(800, 700);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(new Color(240, 248, 255));

        // Title
        JLabel title = new JLabel("Search User");
        title.setFont(new Font("Raleway", Font.BOLD, 30));
        title.setBounds(300, 20, 200, 40);
        add(title);

        // Search Type Radio Buttons
        JLabel searchTypeLabel = new JLabel("Search By:");
        searchTypeLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        searchTypeLabel.setBounds(150, 80, 100, 30);
        add(searchTypeLabel);

        pinRadio = new JRadioButton("PIN");
        pinRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        pinRadio.setBounds(250, 80, 60, 30);
        pinRadio.setBackground(new Color(240, 248, 255));
        pinRadio.setSelected(true);
        add(pinRadio);

        cardRadio = new JRadioButton("Card Number");
        cardRadio.setFont(new Font("Raleway", Font.PLAIN, 14));
        cardRadio.setBounds(320, 80, 120, 30);
        cardRadio.setBackground(new Color(240, 248, 255));
        add(cardRadio);

        searchTypeGroup = new ButtonGroup();
        searchTypeGroup.add(pinRadio);
        searchTypeGroup.add(cardRadio);

        // Search Field
        JLabel searchLabel = new JLabel("Enter Value:");
        searchLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        searchLabel.setBounds(150, 130, 100, 30);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBounds(250, 130, 250, 30);
        add(searchField);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Raleway", Font.BOLD, 14));
        searchButton.setBounds(520, 130, 100, 30);
        searchButton.setBackground(new Color(65, 125, 128));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(this);
        add(searchButton);

        // Clear Button
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Raleway", Font.BOLD, 14));
        clearButton.setBounds(520, 170, 100, 30);
        clearButton.setBackground(Color.GRAY);
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(this);
        add(clearButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton.setBounds(350, 620, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        // Result Panel
        resultPanel = new JPanel();
        resultPanel.setLayout(null);
        resultPanel.setBounds(100, 220, 600, 380);
        resultPanel.setBackground(new Color(230, 240, 250));
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resultPanel.setVisible(false);
        add(resultPanel);

        setVisible(true);
    }

    private void searchUser() {
        // Clear previous results
        resultPanel.removeAll();
        resultPanel.setVisible(false);

        String searchValue = searchField.getText().trim();

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search value");
            return;
        }

        try {
            Con c = new Con();
            String query;

            if (pinRadio.isSelected()) {
                // Try both exact match and trimmed match for PIN
                query = "SELECT name, fname, dob, gender, email, address, city, state, " +
                        "accounttype, card_number, pin, facility " +
                        "FROM signup " +
                        "WHERE pin = '" + searchValue + "' OR TRIM(pin) = '" + searchValue + "'";
            } else {
                // Try both exact match and trimmed match for card number
                query = "SELECT name, fname, dob, gender, email, address, city, state, " +
                        "accounttype, card_number, pin, facility " +
                        "FROM signup " +
                        "WHERE card_number = '" + searchValue + "' OR TRIM(card_number) = '" + searchValue + "'";
            }

            System.out.println("=== SEARCH DEBUG ===");
            System.out.println("Search Value: " + searchValue);
            System.out.println("Search Type: " + (pinRadio.isSelected() ? "PIN" : "Card Number"));
            System.out.println("Query: " + query);

            ResultSet rs = c.statement.executeQuery(query);

            if (rs.next()) {
                System.out.println("✓ User found in database");
                // User found, display details
                displayUserDetails(rs);
            } else {
                System.out.println("❌ No user found with search criteria");

                // Debug: Check if any users exist at all
                ResultSet allUsers = c.statement.executeQuery("SELECT COUNT(*) as count FROM signup");
                if (allUsers.next()) {
                    int totalUsers = allUsers.getInt("count");
                    System.out.println("Total users in database: " + totalUsers);

                    if (totalUsers > 0) {
                        // Show some sample data for debugging
                        ResultSet sampleUsers = c.statement.executeQuery("SELECT pin, card_number, name FROM signup LIMIT 3");
                        System.out.println("Sample users in database:");
                        while (sampleUsers.next()) {
                            System.out.println("  - PIN: " + sampleUsers.getString("pin") +
                                ", Card: " + sampleUsers.getString("card_number") +
                                ", Name: " + sampleUsers.getString("name"));
                        }
                    }
                }

                JOptionPane.showMessageDialog(this, "No user found with the given " +
                                             (pinRadio.isSelected() ? "PIN" : "Card Number") +
                                             "\n\nSearch Value: " + searchValue +
                                             "\nPlease check the console for debug information.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for user: " + e.getMessage());
        }
    }

    private void displayUserDetails(ResultSet rs) throws SQLException {
        resultPanel.removeAll();

        // Get user details with null checking
        String name = rs.getString("name") != null ? rs.getString("name") : "N/A";
        String fname = rs.getString("fname") != null ? rs.getString("fname") : "N/A";
        String dob = rs.getString("dob") != null ? rs.getString("dob") : "N/A";
        String gender = rs.getString("gender") != null ? rs.getString("gender") : "N/A";
        String email = rs.getString("email") != null ? rs.getString("email") : "N/A";
        String address = rs.getString("address") != null ? rs.getString("address") : "N/A";
        String city = rs.getString("city") != null ? rs.getString("city") : "N/A";
        String state = rs.getString("state") != null ? rs.getString("state") : "N/A";
        String accountType = rs.getString("accounttype") != null ? rs.getString("accounttype") : "N/A";
        String cardNumber = rs.getString("card_number") != null ? rs.getString("card_number") : "N/A";
        String pin = rs.getString("pin") != null ? rs.getString("pin") : "N/A";
        String facility = rs.getString("facility") != null ? rs.getString("facility") : "N/A";

        // Calculate balance
        int balance = calculateBalance(pin);

        // Create labels for user details
        JLabel nameLabel = new JLabel("Name: " + name);
        nameLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        nameLabel.setBounds(20, 20, 560, 25);
        resultPanel.add(nameLabel);

        JLabel fnameLabel = new JLabel("Father's Name: " + fname);
        fnameLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        fnameLabel.setBounds(20, 50, 560, 25);
        resultPanel.add(fnameLabel);

        JLabel dobLabel = new JLabel("Date of Birth: " + dob);
        dobLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        dobLabel.setBounds(20, 80, 280, 25);
        resultPanel.add(dobLabel);

        JLabel genderLabel = new JLabel("Gender: " + gender);
        genderLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        genderLabel.setBounds(320, 80, 280, 25);
        resultPanel.add(genderLabel);

        JLabel emailLabel = new JLabel("Email: " + email);
        emailLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        emailLabel.setBounds(20, 110, 560, 25);
        resultPanel.add(emailLabel);

        JLabel addressLabel = new JLabel("Address: " + address + ", " + city + ", " + state);
        addressLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        addressLabel.setBounds(20, 140, 560, 25);
        resultPanel.add(addressLabel);

        JLabel accountTypeLabel = new JLabel("Account Type: " + accountType);
        accountTypeLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        accountTypeLabel.setBounds(20, 180, 280, 25);
        resultPanel.add(accountTypeLabel);

        // Mask card number for security
        String maskedCardNumber;
        if (cardNumber != null && cardNumber.length() >= 4) {
            maskedCardNumber = "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4);
        } else {
            maskedCardNumber = "N/A";
        }
        JLabel cardLabel = new JLabel("Card Number: " + maskedCardNumber);
        cardLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        cardLabel.setBounds(320, 180, 280, 25);
        resultPanel.add(cardLabel);

        JLabel pinLabel = new JLabel("PIN: " + pin);
        pinLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        pinLabel.setBounds(20, 210, 280, 25);
        resultPanel.add(pinLabel);

        JLabel balanceLabel = new JLabel("Current Balance: ₹" + balance);
        balanceLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        balanceLabel.setBounds(320, 210, 280, 25);
        resultPanel.add(balanceLabel);

        JLabel facilityLabel = new JLabel("Facilities: " + facility);
        facilityLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        facilityLabel.setBounds(20, 240, 560, 25);
        resultPanel.add(facilityLabel);

        // Get account creation date
        String creationDate = getAccountCreationDate(pin);
        JLabel creationDateLabel = new JLabel("Account Creation Date: " + creationDate);
        creationDateLabel.setFont(new Font("Raleway", Font.PLAIN, 14));
        creationDateLabel.setBounds(20, 270, 560, 25);
        resultPanel.add(creationDateLabel);

        // Make result panel visible
        resultPanel.setVisible(true);
        resultPanel.repaint();
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

    private String getAccountCreationDate(String pin) {
        String date = "N/A";
        try {
            Con c = new Con();
            ResultSet rs = c.statement.executeQuery("SELECT MIN(date) as creation_date FROM bank WHERE pin = '" + pin + "'");

            if (rs.next() && rs.getString("creation_date") != null) {
                date = rs.getString("creation_date");
            } else {
                // If no transactions, use current date as fallback
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.format(new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchUser();
        } else if (e.getSource() == clearButton) {
            searchField.setText("");
            resultPanel.removeAll();
            resultPanel.setVisible(false);
        } else if (e.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new SearchUser();
    }
}
