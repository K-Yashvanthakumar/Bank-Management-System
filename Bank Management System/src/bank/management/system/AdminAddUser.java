package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AdminAddUser extends JFrame implements ActionListener {
    JTextField nameField, fnameField, emailField, addressField, cityField, stateField, pincodeField;
    JTextField panField, aadharField;
    JRadioButton maleRadio, femaleRadio, otherRadio;
    JRadioButton marriedRadio, unmarriedRadio, otherStatusRadio;
    JRadioButton savingRadio, currentRadio, fdRadio, rdRadio;
    JCheckBox atmCheck, internetCheck, mobileCheck, emailCheck, chequeCheck, eStatementCheck;
    JButton createButton, backButton, generateButton;
    JComboBox<String> religionCombo, categoryCombo, incomeCombo, educationCombo, occupationCombo;
    JComboBox<String> dayCombo, monthCombo, yearCombo;

    // For generating random numbers
    Random random = new Random();

    AdminAddUser() {
        setTitle("Admin - Add New User");

        // Set layout and size
        setLayout(null);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color
        getContentPane().setBackground(new Color(245, 250, 255));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1000, 80);
        headerPanel.setBackground(new Color(70, 130, 180));
        add(headerPanel);

        // Title
        JLabel title = new JLabel("ADD NEW USER");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(400, 20, 250, 40);
        headerPanel.add(title);

        // Admin label
        JLabel adminLabel = new JLabel("Admin Panel");
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setBounds(50, 25, 100, 30);
        headerPanel.add(adminLabel);

        // Personal Details Panel
        JPanel personalPanel = new JPanel();
        personalPanel.setLayout(null);
        personalPanel.setBounds(50, 100, 900, 320);
        personalPanel.setBackground(Color.WHITE);
        personalPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Personal Details",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            new Color(70, 130, 180)));
        add(personalPanel);

        // Name
        JLabel nameLabel = new JLabel("Full Name *:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setBounds(30, 40, 120, 30);
        personalPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBounds(150, 40, 280, 30);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        personalPanel.add(nameField);

        // Father's Name
        JLabel fnameLabel = new JLabel("Father's Name *:");
        fnameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fnameLabel.setBounds(460, 40, 130, 30);
        personalPanel.add(fnameLabel);

        fnameField = new JTextField();
        fnameField.setFont(new Font("Arial", Font.PLAIN, 14));
        fnameField.setBounds(590, 40, 280, 30);
        fnameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        personalPanel.add(fnameField);

        // Date of Birth
        JLabel dobLabel = new JLabel("Date of Birth *:");
        dobLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dobLabel.setBounds(30, 90, 120, 30);
        personalPanel.add(dobLabel);

        // Day, Month, Year dropdowns
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i-1] = String.valueOf(i);
        }
        dayCombo = new JComboBox<>(days);
        dayCombo.setBounds(150, 90, 70, 30);
        dayCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        personalPanel.add(dayCombo);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthCombo = new JComboBox<>(months);
        monthCombo.setBounds(230, 90, 80, 30);
        monthCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        personalPanel.add(monthCombo);

        String[] years = new String[100];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        yearCombo = new JComboBox<>(years);
        yearCombo.setBounds(320, 90, 110, 30);
        yearCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        personalPanel.add(yearCombo);

        // Gender
        JLabel genderLabel = new JLabel("Gender *:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        genderLabel.setBounds(460, 90, 100, 30);
        personalPanel.add(genderLabel);

        maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(590, 90, 80, 30);
        maleRadio.setBackground(Color.WHITE);
        maleRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        maleRadio.setSelected(true);
        personalPanel.add(maleRadio);

        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(680, 90, 90, 30);
        femaleRadio.setBackground(Color.WHITE);
        femaleRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        personalPanel.add(femaleRadio);

        otherRadio = new JRadioButton("Other");
        otherRadio.setBounds(780, 90, 80, 30);
        otherRadio.setBackground(Color.WHITE);
        otherRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        personalPanel.add(otherRadio);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);

        // Email
        JLabel emailLabel = new JLabel("Email Address *:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setBounds(30, 140, 120, 30);
        personalPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(150, 140, 280, 30);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        personalPanel.add(emailField);

        // Marital Status
        JLabel maritalLabel = new JLabel("Marital Status:");
        maritalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        maritalLabel.setBounds(460, 140, 120, 30);
        personalPanel.add(maritalLabel);

        marriedRadio = new JRadioButton("Married");
        marriedRadio.setBounds(590, 140, 90, 30);
        marriedRadio.setBackground(Color.WHITE);
        marriedRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        personalPanel.add(marriedRadio);

        unmarriedRadio = new JRadioButton("Unmarried");
        unmarriedRadio.setBounds(690, 140, 110, 30);
        unmarriedRadio.setBackground(Color.WHITE);
        unmarriedRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        unmarriedRadio.setSelected(true);
        personalPanel.add(unmarriedRadio);

        otherStatusRadio = new JRadioButton("Other");
        otherStatusRadio.setBounds(810, 140, 80, 30);
        otherStatusRadio.setBackground(Color.WHITE);
        otherStatusRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        personalPanel.add(otherStatusRadio);

        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(marriedRadio);
        maritalGroup.add(unmarriedRadio);
        maritalGroup.add(otherStatusRadio);

        // Address
        JLabel addressLabel = new JLabel("Full Address *:");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addressLabel.setBounds(30, 190, 120, 30);
        personalPanel.add(addressLabel);

        addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
        addressField.setBounds(150, 190, 720, 30);
        addressField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        personalPanel.add(addressField);

        // City
        JLabel cityLabel = new JLabel("City *:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cityLabel.setBounds(30, 240, 120, 30);
        personalPanel.add(cityLabel);

        cityField = new JTextField();
        cityField.setFont(new Font("Arial", Font.PLAIN, 14));
        cityField.setBounds(150, 240, 200, 30);
        cityField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        personalPanel.add(cityField);

        // State
        JLabel stateLabel = new JLabel("State *:");
        stateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        stateLabel.setBounds(380, 240, 120, 30);
        personalPanel.add(stateLabel);

        stateField = new JTextField();
        stateField.setFont(new Font("Arial", Font.PLAIN, 14));
        stateField.setBounds(500, 240, 200, 30);
        stateField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        personalPanel.add(stateField);

        // Pincode
        JLabel pincodeLabel = new JLabel("Pincode *:");
        pincodeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pincodeLabel.setBounds(730, 240, 120, 30);
        personalPanel.add(pincodeLabel);

        pincodeField = new JTextField();
        pincodeField.setFont(new Font("Arial", Font.PLAIN, 14));
        pincodeField.setBounds(820, 240, 120, 30);
        pincodeField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        personalPanel.add(pincodeField);

        // Additional Details Panel
        JPanel additionalPanel = new JPanel();
        additionalPanel.setLayout(null);
        additionalPanel.setBounds(50, 440, 900, 280);
        additionalPanel.setBackground(Color.WHITE);
        additionalPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Additional Details & Account Information",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            new Color(70, 130, 180)));
        add(additionalPanel);

        // Religion
        JLabel religionLabel = new JLabel("Religion:");
        religionLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        religionLabel.setBounds(20, 30, 100, 25);
        additionalPanel.add(religionLabel);

        String[] religions = {"Hindu", "Muslim", "Sikh", "Christian", "Other"};
        religionCombo = new JComboBox<>(religions);
        religionCombo.setBounds(120, 30, 150, 25);
        additionalPanel.add(religionCombo);

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        categoryLabel.setBounds(300, 30, 100, 25);
        additionalPanel.add(categoryLabel);

        String[] categories = {"General", "OBC", "SC", "ST", "Other"};
        categoryCombo = new JComboBox<>(categories);
        categoryCombo.setBounds(400, 30, 150, 25);
        additionalPanel.add(categoryCombo);

        // Income
        JLabel incomeLabel = new JLabel("Income:");
        incomeLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        incomeLabel.setBounds(20, 70, 100, 25);
        additionalPanel.add(incomeLabel);

        String[] incomes = {"Null", "<1,50,000", "<2,50,000", "<5,00,000", "Upto 10,00,000", "Above 10,00,000"};
        incomeCombo = new JComboBox<>(incomes);
        incomeCombo.setBounds(120, 70, 150, 25);
        additionalPanel.add(incomeCombo);

        // Education
        JLabel educationLabel = new JLabel("Education:");
        educationLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        educationLabel.setBounds(300, 70, 100, 25);
        additionalPanel.add(educationLabel);

        String[] educations = {"Non-Graduate", "Graduate", "Post-Graduate", "Doctrate", "Others"};
        educationCombo = new JComboBox<>(educations);
        educationCombo.setBounds(400, 70, 150, 25);
        additionalPanel.add(educationCombo);

        // Occupation
        JLabel occupationLabel = new JLabel("Occupation:");
        occupationLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        occupationLabel.setBounds(20, 110, 100, 25);
        additionalPanel.add(occupationLabel);

        String[] occupations = {"Salaried", "Self-Employed", "Business", "Student", "Retired", "Others"};
        occupationCombo = new JComboBox<>(occupations);
        occupationCombo.setBounds(120, 110, 150, 25);
        additionalPanel.add(occupationCombo);

        // PAN Number
        JLabel panLabel = new JLabel("PAN Number:");
        panLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        panLabel.setBounds(300, 110, 100, 25);
        additionalPanel.add(panLabel);

        panField = new JTextField();
        panField.setFont(new Font("Arial", Font.PLAIN, 14));
        panField.setBounds(400, 110, 150, 25);
        additionalPanel.add(panField);

        // Aadhar Number
        JLabel aadharLabel = new JLabel("Aadhar No:");
        aadharLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        aadharLabel.setBounds(20, 150, 100, 25);
        additionalPanel.add(aadharLabel);

        aadharField = new JTextField();
        aadharField.setFont(new Font("Arial", Font.PLAIN, 14));
        aadharField.setBounds(120, 150, 150, 25);
        additionalPanel.add(aadharField);

        // Account Type
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        accountTypeLabel.setBounds(300, 150, 100, 25);
        additionalPanel.add(accountTypeLabel);

        savingRadio = new JRadioButton("Saving Account");
        savingRadio.setBounds(400, 150, 130, 25);
        savingRadio.setBackground(new Color(230, 250, 230));
        savingRadio.setSelected(true);
        additionalPanel.add(savingRadio);

        currentRadio = new JRadioButton("Current Account");
        currentRadio.setBounds(530, 150, 140, 25);
        currentRadio.setBackground(new Color(230, 250, 230));
        additionalPanel.add(currentRadio);

        fdRadio = new JRadioButton("FD Account");
        fdRadio.setBounds(400, 180, 100, 25);
        fdRadio.setBackground(new Color(230, 250, 230));
        additionalPanel.add(fdRadio);

        rdRadio = new JRadioButton("RD Account");
        rdRadio.setBounds(530, 180, 100, 25);
        rdRadio.setBackground(new Color(230, 250, 230));
        additionalPanel.add(rdRadio);

        ButtonGroup accountTypeGroup = new ButtonGroup();
        accountTypeGroup.add(savingRadio);
        accountTypeGroup.add(currentRadio);
        accountTypeGroup.add(fdRadio);
        accountTypeGroup.add(rdRadio);

        // Services
        JLabel servicesLabel = new JLabel("Services:");
        servicesLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        servicesLabel.setBounds(20, 190, 100, 25);
        additionalPanel.add(servicesLabel);

        atmCheck = new JCheckBox("ATM Card");
        atmCheck.setBounds(120, 190, 100, 25);
        atmCheck.setBackground(new Color(230, 250, 230));
        atmCheck.setSelected(true);
        additionalPanel.add(atmCheck);

        internetCheck = new JCheckBox("Internet Banking");
        internetCheck.setBounds(220, 190, 140, 25);
        internetCheck.setBackground(new Color(230, 250, 230));
        additionalPanel.add(internetCheck);

        mobileCheck = new JCheckBox("Mobile Banking");
        mobileCheck.setBounds(360, 190, 140, 25);
        mobileCheck.setBackground(new Color(230, 250, 230));
        additionalPanel.add(mobileCheck);

        emailCheck = new JCheckBox("Email Alerts");
        emailCheck.setBounds(500, 190, 120, 25);
        emailCheck.setBackground(new Color(230, 250, 230));
        additionalPanel.add(emailCheck);

        chequeCheck = new JCheckBox("Cheque Book");
        chequeCheck.setBounds(120, 220, 120, 25);
        chequeCheck.setBackground(new Color(230, 250, 230));
        additionalPanel.add(chequeCheck);

        eStatementCheck = new JCheckBox("E-Statement");
        eStatementCheck.setBounds(240, 220, 120, 25);
        eStatementCheck.setBackground(new Color(230, 250, 230));
        additionalPanel.add(eStatementCheck);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, 740, 900, 60);
        buttonPanel.setBackground(new Color(245, 250, 255));
        add(buttonPanel);

        // Generate Button
        generateButton = new JButton("🎲 Generate Card & PIN");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setBounds(200, 15, 200, 40);
        generateButton.setBackground(new Color(70, 130, 180));
        generateButton.setForeground(Color.WHITE);
        generateButton.setBorder(BorderFactory.createRaisedBevelBorder());
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(this);
        buttonPanel.add(generateButton);

        // Create Account Button
        createButton = new JButton("✅ Create Account");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBounds(420, 15, 180, 40);
        createButton.setBackground(new Color(34, 139, 34));
        createButton.setForeground(Color.WHITE);
        createButton.setBorder(BorderFactory.createRaisedBevelBorder());
        createButton.setFocusPainted(false);
        createButton.addActionListener(this);
        buttonPanel.add(createButton);

        // Back Button
        backButton = new JButton("⬅ Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBounds(620, 15, 180, 40);
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createRaisedBevelBorder());
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        setVisible(true);
    }

    private void createAccount() {
        // Validate required fields
        if (nameField.getText().trim().isEmpty() || fnameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty() ||
            cityField.getText().trim().isEmpty() || stateField.getText().trim().isEmpty() ||
            pincodeField.getText().trim().isEmpty() || panField.getText().trim().isEmpty() ||
            aadharField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill all required fields");
            return;
        }

        try {
            // Generate form number
            long formNo = Math.abs(random.nextLong() % 9000L) + 1000L;

            // Get personal details
            String name = nameField.getText().trim();
            String fname = fnameField.getText().trim();

            // Format date of birth
            String day = (String) dayCombo.getSelectedItem();
            String month = (String) monthCombo.getSelectedItem();
            String year = (String) yearCombo.getSelectedItem();
            String dob = day + "-" + month + "-" + year;

            // Get gender
            String gender = "";
            if (maleRadio.isSelected()) gender = "Male";
            else if (femaleRadio.isSelected()) gender = "Female";
            else gender = "Other";

            String email = emailField.getText().trim();

            // Get marital status
            String marital = "";
            if (marriedRadio.isSelected()) marital = "Married";
            else if (unmarriedRadio.isSelected()) marital = "Unmarried";
            else marital = "Other";

            String address = addressField.getText().trim();
            String city = cityField.getText().trim();
            String state = stateField.getText().trim();
            String pincode = pincodeField.getText().trim();

            // Get additional details
            String religion = (String) religionCombo.getSelectedItem();
            String category = (String) categoryCombo.getSelectedItem();
            String income = (String) incomeCombo.getSelectedItem();
            String education = (String) educationCombo.getSelectedItem();
            String occupation = (String) occupationCombo.getSelectedItem();
            String pan = panField.getText().trim();
            String aadhar = aadharField.getText().trim();

            // Get account type
            String accountType = "";
            if (savingRadio.isSelected()) accountType = "Saving Account";
            else if (currentRadio.isSelected()) accountType = "Current Account";
            else if (fdRadio.isSelected()) accountType = "Fixed Deposit Account";
            else accountType = "Recurring Deposit Account";

            // Get services
            String services = "";
            if (atmCheck.isSelected()) services += "ATM Card ";
            if (internetCheck.isSelected()) services += "Internet Banking ";
            if (mobileCheck.isSelected()) services += "Mobile Banking ";
            if (emailCheck.isSelected()) services += "Email Alerts ";
            if (chequeCheck.isSelected()) services += "Cheque Book ";
            if (eStatementCheck.isSelected()) services += "E-Statement ";

            // Generate card number and PIN
            long cardNumberRaw = Math.abs(random.nextLong() % 90000000L) + 5040936000000000L;
            String cardNumber = "" + cardNumberRaw;

            long pinRaw = Math.abs(random.nextLong() % 9000L) + 1000L;
            String pin = "" + pinRaw;

            // Connect to database
            Con c = new Con();

            // Insert into single signup table with all information (using explicit column names)
            String signupQuery = "INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                        "address, city, pincode, state, religion, category, income, education, occupation, " +
                        "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                        "VALUES ('" + formNo + "', '" + name + "', '" + fname + "', '" +
                        dob + "', '" + gender + "', '" + email + "', '" + marital + "', '" +
                        address + "', '" + city + "', '" + pincode + "', '" + state + "', '" +
                        religion + "', '" + category + "', '" + income + "', '" + education + "', '" +
                        occupation + "', '" + pan + "', '" + aadhar + "', 'No', 'No', '" +
                        accountType + "', '" + cardNumber + "', '" + pin + "', '" + services + "')";

            // Insert into login table (using explicit column names)
            String loginQuery = "INSERT INTO login (formno, card_number, pin) VALUES ('" +
                        formNo + "', '" + cardNumber + "', '" + pin + "')";

            // Execute queries
            c.statement.executeUpdate(signupQuery);
            c.statement.executeUpdate(loginQuery);

            // Show success message with card number and PIN
            JOptionPane.showMessageDialog(this,
                "Account created successfully!\n\n" +
                "Card Number: " + cardNumber + "\n" +
                "PIN: " + pin + "\n\n" +
                "Please note these details for future reference.");

            // Clear all fields
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating account: " + e.getMessage());
        }
    }

    private void generateCardAndPIN() {
        // Generate card number
        long cardNumberRaw = Math.abs(random.nextLong() % 90000000L) + 5040936000000000L;
        String cardNumber = "" + cardNumberRaw;

        // Generate PIN
        long pinRaw = Math.abs(random.nextLong() % 9000L) + 1000L;
        String pin = "" + pinRaw;

        // Show in dialog
        JOptionPane.showMessageDialog(this,
            "Generated Card Number and PIN:\n\n" +
            "Card Number: " + cardNumber + "\n" +
            "PIN: " + pin + "\n\n" +
            "These will be used when creating the account.");
    }

    private void clearFields() {
        nameField.setText("");
        fnameField.setText("");
        emailField.setText("");
        addressField.setText("");
        cityField.setText("");
        stateField.setText("");
        pincodeField.setText("");
        panField.setText("");
        aadharField.setText("");

        // Reset selections
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);

        maleRadio.setSelected(true);
        unmarriedRadio.setSelected(true);
        savingRadio.setSelected(true);

        religionCombo.setSelectedIndex(0);
        categoryCombo.setSelectedIndex(0);
        incomeCombo.setSelectedIndex(0);
        educationCombo.setSelectedIndex(0);
        occupationCombo.setSelectedIndex(0);

        atmCheck.setSelected(true);
        internetCheck.setSelected(false);
        mobileCheck.setSelected(false);
        emailCheck.setSelected(false);
        chequeCheck.setSelected(false);
        eStatementCheck.setSelected(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            createAccount();
        } else if (e.getSource() == generateButton) {
            generateCardAndPIN();
        } else if (e.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AdminAddUser();
    }
}
