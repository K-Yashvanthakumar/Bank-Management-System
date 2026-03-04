package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.sql.*;

public class SignupComplete extends JFrame implements ActionListener {
    // Personal Details
    JTextField nameField, fnameField, emailField, addressField, cityField, stateField, pincodeField, dobField;
    JRadioButton maleRadio, femaleRadio, marriedRadio, unmarriedRadio, otherRadio;

    // Additional Details
    JTextField panField, aadharField;
    JComboBox<String> religionCombo, categoryCombo, incomeCombo, educationCombo, occupationCombo;
    JRadioButton seniorCitizenYes, seniorCitizenNo, existingAccountYes, existingAccountNo;

    // Store data from previous pages
    private String storedName, storedFname, storedDob, storedGender, storedEmail, storedMarital;
    private String storedAddress, storedCity, storedState, storedPincode;
    private String storedReligion, storedCategory, storedIncome, storedEducation, storedOccupation;
    private String storedPan, storedAadhar, storedSeniorCitizen, storedExistingAccount;

    // Account Details
    JRadioButton savingRadio, currentRadio, fdRadio, rdRadio;
    JCheckBox atmCheck, internetCheck, mobileCheck, emailCheck, chequeCheck, eStatementCheck;

    JButton submitButton, clearButton, backButton;

    // For generating random numbers
    Random random = new Random();
    String formNumber;

    SignupComplete() {
        setTitle("Complete Account Registration");

        // Generate form number
        long first4 = (random.nextLong() % 9000L) + 1000L;
        formNumber = "" + Math.abs(first4);

        // Set layout and size
        setLayout(null);
        setSize(900, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color
        getContentPane().setBackground(new Color(222, 255, 228));

        // Bank Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(25, 10, 100, 100);
        add(image);

        // Title
        JLabel title = new JLabel("APPLICATION FORM NO. " + formNumber);
        title.setFont(new Font("Raleway", Font.BOLD, 38));
        title.setBounds(160, 20, 600, 40);
        add(title);

        // Personal Details Section
        JLabel personalLabel = new JLabel("Personal Details");
        personalLabel.setFont(new Font("Raleway", Font.BOLD, 22));
        personalLabel.setBounds(290, 80, 400, 30);
        add(personalLabel);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        nameLabel.setBounds(100, 140, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Raleway", Font.BOLD, 14));
        nameField.setBounds(300, 140, 400, 30);
        add(nameField);

        // Father's Name
        JLabel fnameLabel = new JLabel("Father's Name:");
        fnameLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        fnameLabel.setBounds(100, 190, 200, 30);
        add(fnameLabel);

        fnameField = new JTextField();
        fnameField.setFont(new Font("Raleway", Font.BOLD, 14));
        fnameField.setBounds(300, 190, 400, 30);
        add(fnameField);

        // Date of Birth
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        dobLabel.setBounds(100, 240, 200, 30);
        add(dobLabel);

        dobField = new JTextField("DD-MM-YYYY");
        dobField.setForeground(new Color(105, 105, 105));
        dobField.setBounds(300, 240, 400, 30);
        add(dobField);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        genderLabel.setBounds(100, 290, 200, 30);
        add(genderLabel);

        maleRadio = new JRadioButton("Male");
        maleRadio.setFont(new Font("Raleway", Font.BOLD, 14));
        maleRadio.setBackground(new Color(222, 255, 228));
        maleRadio.setBounds(300, 290, 60, 30);
        add(maleRadio);

        femaleRadio = new JRadioButton("Female");
        femaleRadio.setFont(new Font("Raleway", Font.BOLD, 14));
        femaleRadio.setBackground(new Color(222, 255, 228));
        femaleRadio.setBounds(450, 290, 120, 30);
        add(femaleRadio);

        otherRadio = new JRadioButton("Other");
        otherRadio.setFont(new Font("Raleway", Font.BOLD, 14));
        otherRadio.setBackground(new Color(222, 255, 228));
        otherRadio.setBounds(600, 290, 100, 30);
        add(otherRadio);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);

        // Email
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        emailLabel.setBounds(100, 340, 200, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Raleway", Font.BOLD, 14));
        emailField.setBounds(300, 340, 400, 30);
        add(emailField);

        // Marital Status
        JLabel maritalLabel = new JLabel("Marital Status:");
        maritalLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        maritalLabel.setBounds(100, 390, 200, 30);
        add(maritalLabel);

        marriedRadio = new JRadioButton("Married");
        marriedRadio.setFont(new Font("Raleway", Font.BOLD, 14));
        marriedRadio.setBackground(new Color(222, 255, 228));
        marriedRadio.setBounds(300, 390, 100, 30);
        add(marriedRadio);

        unmarriedRadio = new JRadioButton("Unmarried");
        unmarriedRadio.setFont(new Font("Raleway", Font.BOLD, 14));
        unmarriedRadio.setBackground(new Color(222, 255, 228));
        unmarriedRadio.setBounds(450, 390, 100, 30);
        add(unmarriedRadio);

        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(marriedRadio);
        maritalGroup.add(unmarriedRadio);

        // Address
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        addressLabel.setBounds(100, 440, 200, 30);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setFont(new Font("Raleway", Font.BOLD, 14));
        addressField.setBounds(300, 440, 400, 30);
        add(addressField);

        // City
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        cityLabel.setBounds(100, 490, 200, 30);
        add(cityLabel);

        cityField = new JTextField();
        cityField.setFont(new Font("Raleway", Font.BOLD, 14));
        cityField.setBounds(300, 490, 400, 30);
        add(cityField);

        // State
        JLabel stateLabel = new JLabel("State:");
        stateLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        stateLabel.setBounds(100, 540, 200, 30);
        add(stateLabel);

        stateField = new JTextField();
        stateField.setFont(new Font("Raleway", Font.BOLD, 14));
        stateField.setBounds(300, 540, 400, 30);
        add(stateField);

        // Pincode
        JLabel pincodeLabel = new JLabel("Pincode:");
        pincodeLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        pincodeLabel.setBounds(100, 590, 200, 30);
        add(pincodeLabel);

        pincodeField = new JTextField();
        pincodeField.setFont(new Font("Raleway", Font.BOLD, 14));
        pincodeField.setBounds(300, 590, 400, 30);
        add(pincodeField);

        // Next Button to continue to page 2
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Raleway", Font.BOLD, 14));
        nextButton.setBounds(620, 660, 80, 30);
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> validateAndShowPage2());
        add(nextButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton.setBounds(100, 660, 80, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    private void showPage2() {
        // Hide page 1 components and show page 2
        setTitle("Complete Account Registration - Page 2");
        getContentPane().removeAll();

        // Set background color
        getContentPane().setBackground(new Color(222, 255, 228));

        // Bank Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(150, 5, 100, 100);
        add(image);

        // Title
        JLabel title = new JLabel("Page 2: Additional Details");
        title.setFont(new Font("Raleway", Font.BOLD, 22));
        title.setBounds(300, 30, 600, 40);
        add(title);

        // Religion
        JLabel religionLabel = new JLabel("Religion:");
        religionLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        religionLabel.setBounds(100, 120, 100, 30);
        add(religionLabel);

        String[] religions = {"Hindu", "Muslim", "Sikh", "Christian", "Other"};
        religionCombo = new JComboBox<>(religions);
        religionCombo.setFont(new Font("Raleway", Font.BOLD, 14));
        religionCombo.setBounds(350, 120, 320, 30);
        add(religionCombo);

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        categoryLabel.setBounds(100, 170, 100, 30);
        add(categoryLabel);

        String[] categories = {"General", "OBC", "SC", "ST", "Other"};
        categoryCombo = new JComboBox<>(categories);
        categoryCombo.setFont(new Font("Raleway", Font.BOLD, 14));
        categoryCombo.setBounds(350, 170, 320, 30);
        add(categoryCombo);

        // Income
        JLabel incomeLabel = new JLabel("Income:");
        incomeLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        incomeLabel.setBounds(100, 220, 100, 30);
        add(incomeLabel);

        String[] incomes = {"Null", "<1,50,000", "<2,50,000", "<5,00,000", "Upto 10,00,000", "Above 10,00,000"};
        incomeCombo = new JComboBox<>(incomes);
        incomeCombo.setFont(new Font("Raleway", Font.BOLD, 14));
        incomeCombo.setBounds(350, 220, 320, 30);
        add(incomeCombo);

        // Education
        JLabel educationLabel = new JLabel("Educational");
        educationLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        educationLabel.setBounds(100, 270, 150, 30);
        add(educationLabel);

        JLabel qualificationLabel = new JLabel("Qualification:");
        qualificationLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        qualificationLabel.setBounds(100, 290, 150, 30);
        add(qualificationLabel);

        String[] educations = {"Non-Graduate", "Graduate", "Post-Graduate", "Doctrate", "Others"};
        educationCombo = new JComboBox<>(educations);
        educationCombo.setFont(new Font("Raleway", Font.BOLD, 14));
        educationCombo.setBounds(350, 280, 320, 30);
        add(educationCombo);

        // Occupation
        JLabel occupationLabel = new JLabel("Occupation:");
        occupationLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        occupationLabel.setBounds(100, 330, 150, 30);
        add(occupationLabel);

        String[] occupations = {"Salaried", "Self-Employed", "Business", "Student", "Retired", "Others"};
        occupationCombo = new JComboBox<>(occupations);
        occupationCombo.setFont(new Font("Raleway", Font.BOLD, 14));
        occupationCombo.setBounds(350, 330, 320, 30);
        add(occupationCombo);

        // PAN Number
        JLabel panLabel = new JLabel("PAN Number:");
        panLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        panLabel.setBounds(100, 380, 150, 30);
        add(panLabel);

        panField = new JTextField();
        panField.setFont(new Font("Raleway", Font.BOLD, 14));
        panField.setBounds(350, 380, 320, 30);
        add(panField);

        // Aadhar Number
        JLabel aadharLabel = new JLabel("Aadhar Number:");
        aadharLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        aadharLabel.setBounds(100, 430, 150, 30);
        add(aadharLabel);

        aadharField = new JTextField();
        aadharField.setFont(new Font("Raleway", Font.BOLD, 14));
        aadharField.setBounds(350, 430, 320, 30);
        add(aadharField);

        // Senior Citizen
        JLabel seniorLabel = new JLabel("Senior Citizen:");
        seniorLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        seniorLabel.setBounds(100, 480, 150, 30);
        add(seniorLabel);

        seniorCitizenYes = new JRadioButton("Yes");
        seniorCitizenYes.setFont(new Font("Raleway", Font.BOLD, 14));
        seniorCitizenYes.setBackground(new Color(222, 255, 228));
        seniorCitizenYes.setBounds(350, 480, 100, 30);
        add(seniorCitizenYes);

        seniorCitizenNo = new JRadioButton("No");
        seniorCitizenNo.setFont(new Font("Raleway", Font.BOLD, 14));
        seniorCitizenNo.setBackground(new Color(222, 255, 228));
        seniorCitizenNo.setBounds(460, 480, 100, 30);
        seniorCitizenNo.setSelected(true);
        add(seniorCitizenNo);

        ButtonGroup seniorGroup = new ButtonGroup();
        seniorGroup.add(seniorCitizenYes);
        seniorGroup.add(seniorCitizenNo);

        // Existing Account
        JLabel existingLabel = new JLabel("Existing Account:");
        existingLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        existingLabel.setBounds(100, 530, 150, 30);
        add(existingLabel);

        existingAccountYes = new JRadioButton("Yes");
        existingAccountYes.setFont(new Font("Raleway", Font.BOLD, 14));
        existingAccountYes.setBackground(new Color(222, 255, 228));
        existingAccountYes.setBounds(350, 530, 100, 30);
        add(existingAccountYes);

        existingAccountNo = new JRadioButton("No");
        existingAccountNo.setFont(new Font("Raleway", Font.BOLD, 14));
        existingAccountNo.setBackground(new Color(222, 255, 228));
        existingAccountNo.setBounds(460, 530, 100, 30);
        existingAccountNo.setSelected(true);
        add(existingAccountNo);

        ButtonGroup existingGroup = new ButtonGroup();
        existingGroup.add(existingAccountYes);
        existingGroup.add(existingAccountNo);

        // Continue to page 3
        JButton nextButton2 = new JButton("Next");
        nextButton2.setFont(new Font("Raleway", Font.BOLD, 14));
        nextButton2.setBounds(620, 600, 80, 30);
        nextButton2.setBackground(Color.BLACK);
        nextButton2.setForeground(Color.WHITE);
        nextButton2.addActionListener(e -> validateAndShowPage3());
        add(nextButton2);

        // Back Button
        JButton backButton2 = new JButton("Back");
        backButton2.setFont(new Font("Raleway", Font.BOLD, 14));
        backButton2.setBounds(100, 600, 80, 30);
        backButton2.setBackground(Color.BLACK);
        backButton2.setForeground(Color.WHITE);
        backButton2.addActionListener(e -> {
            getContentPane().removeAll();
            new SignupComplete();
            setVisible(false);
        });
        add(backButton2);

        repaint();
    }

    private void showPage3() {
        // Hide page 2 components and show page 3
        setTitle("Complete Account Registration - Page 3");
        getContentPane().removeAll();

        // Set background color
        getContentPane().setBackground(new Color(222, 255, 228));

        // Bank Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(150, 5, 100, 100);
        add(image);

        // Title
        JLabel title = new JLabel("Page 3: Account Details");
        title.setFont(new Font("Raleway", Font.BOLD, 22));
        title.setBounds(300, 30, 600, 40);
        add(title);

        // Account Type
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        accountTypeLabel.setBounds(100, 120, 150, 30);
        add(accountTypeLabel);

        savingRadio = new JRadioButton("Saving Account");
        savingRadio.setFont(new Font("Raleway", Font.BOLD, 16));
        savingRadio.setBackground(new Color(222, 255, 228));
        savingRadio.setBounds(100, 160, 150, 30);
        savingRadio.setSelected(true);
        add(savingRadio);

        fdRadio = new JRadioButton("Fixed Deposit Account");
        fdRadio.setFont(new Font("Raleway", Font.BOLD, 16));
        fdRadio.setBackground(new Color(222, 255, 228));
        fdRadio.setBounds(350, 160, 300, 30);
        add(fdRadio);

        currentRadio = new JRadioButton("Current Account");
        currentRadio.setFont(new Font("Raleway", Font.BOLD, 16));
        currentRadio.setBackground(new Color(222, 255, 228));
        currentRadio.setBounds(100, 200, 150, 30);
        add(currentRadio);

        rdRadio = new JRadioButton("Recurring Deposit Account");
        rdRadio.setFont(new Font("Raleway", Font.BOLD, 16));
        rdRadio.setBackground(new Color(222, 255, 228));
        rdRadio.setBounds(350, 200, 300, 30);
        add(rdRadio);

        ButtonGroup accountGroup = new ButtonGroup();
        accountGroup.add(savingRadio);
        accountGroup.add(fdRadio);
        accountGroup.add(currentRadio);
        accountGroup.add(rdRadio);

        // Card Number
        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        cardLabel.setBounds(100, 250, 200, 30);
        add(cardLabel);

        JLabel cardNumberLabel = new JLabel("XXXX-XXXX-XXXX-4841");
        cardNumberLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        cardNumberLabel.setBounds(330, 250, 250, 30);
        add(cardNumberLabel);

        JLabel cardInfoLabel = new JLabel("Your 16 Digit Card Number");
        cardInfoLabel.setFont(new Font("Raleway", Font.BOLD, 12));
        cardInfoLabel.setBounds(100, 275, 200, 15);
        add(cardInfoLabel);

        // PIN
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        pinLabel.setBounds(100, 300, 200, 30);
        add(pinLabel);

        JLabel pinNumberLabel = new JLabel("XXXX");
        pinNumberLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        pinNumberLabel.setBounds(330, 300, 250, 30);
        add(pinNumberLabel);

        JLabel pinInfoLabel = new JLabel("Your 4 Digit Password");
        pinInfoLabel.setFont(new Font("Raleway", Font.BOLD, 12));
        pinInfoLabel.setBounds(100, 325, 200, 15);
        add(pinInfoLabel);

        // Services Required
        JLabel servicesLabel = new JLabel("Services Required:");
        servicesLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        servicesLabel.setBounds(100, 350, 200, 30);
        add(servicesLabel);

        atmCheck = new JCheckBox("ATM CARD");
        atmCheck.setBackground(new Color(222, 255, 228));
        atmCheck.setFont(new Font("Raleway", Font.BOLD, 14));
        atmCheck.setBounds(100, 380, 150, 25);
        atmCheck.setSelected(true);
        add(atmCheck);

        internetCheck = new JCheckBox("Internet Banking");
        internetCheck.setBackground(new Color(222, 255, 228));
        internetCheck.setFont(new Font("Raleway", Font.BOLD, 14));
        internetCheck.setBounds(270, 380, 150, 25);
        add(internetCheck);

        mobileCheck = new JCheckBox("Mobile Banking");
        mobileCheck.setBackground(new Color(222, 255, 228));
        mobileCheck.setFont(new Font("Raleway", Font.BOLD, 14));
        mobileCheck.setBounds(440, 380, 150, 25);
        add(mobileCheck);

        emailCheck = new JCheckBox("EMAIL Alerts");
        emailCheck.setBackground(new Color(222, 255, 228));
        emailCheck.setFont(new Font("Raleway", Font.BOLD, 14));
        emailCheck.setBounds(100, 410, 150, 25);
        add(emailCheck);

        chequeCheck = new JCheckBox("Cheque Book");
        chequeCheck.setBackground(new Color(222, 255, 228));
        chequeCheck.setFont(new Font("Raleway", Font.BOLD, 14));
        chequeCheck.setBounds(270, 410, 150, 25);
        add(chequeCheck);

        eStatementCheck = new JCheckBox("E-Statement");
        eStatementCheck.setBackground(new Color(222, 255, 228));
        eStatementCheck.setFont(new Font("Raleway", Font.BOLD, 14));
        eStatementCheck.setBounds(440, 410, 150, 25);
        add(eStatementCheck);

        // Declaration
        JLabel declarationLabel = new JLabel("I hereby declare that the above entered details are correct to the best of my knowledge.");
        declarationLabel.setFont(new Font("Raleway", Font.BOLD, 12));
        declarationLabel.setBounds(100, 450, 600, 20);
        add(declarationLabel);

        JCheckBox declarationCheck = new JCheckBox("I agree to the terms and conditions");
        declarationCheck.setBackground(new Color(222, 255, 228));
        declarationCheck.setFont(new Font("Raleway", Font.BOLD, 12));
        declarationCheck.setBounds(100, 475, 300, 25);
        declarationCheck.setSelected(true);
        add(declarationCheck);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Raleway", Font.BOLD, 16));
        submitButton.setBounds(620, 520, 120, 40);
        submitButton.setBackground(new Color(0, 150, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        add(submitButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Raleway", Font.BOLD, 16));
        cancelButton.setBounds(100, 520, 120, 40);
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> {
            setVisible(false);
            new Login();
        });
        add(cancelButton);

        // Back to Page 2 Button
        JButton backButton3 = new JButton("Back");
        backButton3.setFont(new Font("Raleway", Font.BOLD, 16));
        backButton3.setBounds(360, 520, 120, 40);
        backButton3.setBackground(Color.GRAY);
        backButton3.setForeground(Color.WHITE);
        backButton3.addActionListener(e -> showPage2());
        add(backButton3);

        // Set window size for page 3 - now much smaller
        setSize(920, 650);
        setLocationRelativeTo(null);

        repaint();
    }

    // Validation method for page 1
    private void validateAndShowPage2() {
        // Check if all required fields are filled
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name");
            return;
        }
        if (fnameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your father's name");
            return;
        }
        if (dobField.getText().trim().isEmpty() || dobField.getText().equals("DD-MM-YYYY")) {
            JOptionPane.showMessageDialog(this, "Please enter your date of birth in DD-MM-YYYY format");
            return;
        }
        if (!dobField.getText().matches("\\d{2}-\\d{2}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Please enter date of birth in correct format (DD-MM-YYYY)");
            return;
        }
        if (!maleRadio.isSelected() && !femaleRadio.isSelected() && !otherRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select your gender");
            return;
        }
        if (emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email address");
            return;
        }
        if (!emailField.getText().contains("@") || !emailField.getText().contains(".")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address");
            return;
        }
        if (addressField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your address");
            return;
        }
        if (cityField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your city");
            return;
        }
        if (stateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your state");
            return;
        }
        if (pincodeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your pincode");
            return;
        }
        if (!pincodeField.getText().matches("\\d{6}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 6-digit pincode");
            return;
        }

        // Store page 1 data
        storedName = nameField.getText().trim().replace("'", "''");
        storedFname = fnameField.getText().trim().replace("'", "''");
        storedDob = dobField.getText().trim();
        storedGender = maleRadio.isSelected() ? "Male" : (femaleRadio.isSelected() ? "Female" : "Other");
        storedEmail = emailField.getText().trim().replace("'", "''");
        storedMarital = marriedRadio.isSelected() ? "Married" : "Unmarried";
        storedAddress = addressField.getText().trim().replace("'", "''");
        storedCity = cityField.getText().trim().replace("'", "''");
        storedState = stateField.getText().trim().replace("'", "''");
        storedPincode = pincodeField.getText().trim();

        // If all validations pass, go to page 2
        showPage2();
    }

    // Validation method for page 2
    private void validateAndShowPage3() {
        // Check PAN and Aadhar fields
        if (panField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your PAN number");
            return;
        }
        if (!panField.getText().matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid PAN number (Format: ABCDE1234F)");
            return;
        }
        if (aadharField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your Aadhar number");
            return;
        }
        if (!aadharField.getText().matches("\\d{12}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 12-digit Aadhar number");
            return;
        }

        // Store page 2 data
        storedReligion = (String) religionCombo.getSelectedItem();
        storedCategory = (String) categoryCombo.getSelectedItem();
        storedIncome = (String) incomeCombo.getSelectedItem();
        storedEducation = (String) educationCombo.getSelectedItem();
        storedOccupation = (String) occupationCombo.getSelectedItem();
        storedPan = panField.getText().trim().replace("'", "''");
        storedAadhar = aadharField.getText().trim();
        storedSeniorCitizen = seniorCitizenYes.isSelected() ? "Yes" : "No";
        storedExistingAccount = existingAccountYes.isSelected() ? "Yes" : "No";

        // If all validations pass, go to page 3
        showPage3();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Comprehensive validation before submission
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name");
                return;
            }
            if (fnameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your father's name");
                return;
            }
            if (emailField.getText().trim().isEmpty() || !emailField.getText().contains("@")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address");
                return;
            }
            if (addressField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your address");
                return;
            }
            if (cityField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your city");
                return;
            }
            if (stateField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your state");
                return;
            }
            if (pincodeField.getText().trim().isEmpty() || !pincodeField.getText().matches("\\d{6}")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid 6-digit pincode");
                return;
            }
            if (panField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your PAN number");
                return;
            }
            if (aadharField.getText().trim().isEmpty() || !aadharField.getText().matches("\\d{12}")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid 12-digit Aadhar number");
                return;
            }
            if (!savingRadio.isSelected() && !currentRadio.isSelected() && !fdRadio.isSelected() && !rdRadio.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select an account type");
                return;
            }

            // Declare variables outside try block for error handling
            String signupQuery = "";
            String loginQuery = "";

            try {
                // Check if data from previous pages is available
                if (storedName == null || storedFname == null) {
                    JOptionPane.showMessageDialog(this, "Please go back and fill all required fields in previous pages.");
                    return;
                }

                // Use stored data from previous pages
                String name = storedName != null ? storedName : "";
                String fname = storedFname != null ? storedFname : "";
                String dob = storedDob != null ? storedDob : "";
                String gender = storedGender != null ? storedGender : "Male";
                String email = storedEmail != null ? storedEmail : "";
                String marital = storedMarital != null ? storedMarital : "Unmarried";
                String address = storedAddress != null ? storedAddress : "";
                String city = storedCity != null ? storedCity : "";
                String state = storedState != null ? storedState : "";
                String pincode = storedPincode != null ? storedPincode : "";
                String religion = storedReligion != null ? storedReligion : "Hindu";
                String category = storedCategory != null ? storedCategory : "General";
                String income = storedIncome != null ? storedIncome : "<1,50,000";
                String education = storedEducation != null ? storedEducation : "Graduate";
                String occupation = storedOccupation != null ? storedOccupation : "Salaried";
                String pan = storedPan != null ? storedPan : "";
                String aadhar = storedAadhar != null ? storedAadhar : "";

                String seniorCitizen = storedSeniorCitizen != null ? storedSeniorCitizen : "No";
                String existingAccount = storedExistingAccount != null ? storedExistingAccount : "No";

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

                // Ensure tables exist
                try {
                    // Create signup table if not exists
                    String createSignup = "CREATE TABLE IF NOT EXISTS signup (" +
                        "formno VARCHAR(20) PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL," +
                        "fname VARCHAR(100) NOT NULL," +
                        "dob VARCHAR(20) NOT NULL," +
                        "gender VARCHAR(10) NOT NULL," +
                        "email VARCHAR(100) NOT NULL," +
                        "marital VARCHAR(20) NOT NULL," +
                        "address VARCHAR(200) NOT NULL," +
                        "city VARCHAR(50) NOT NULL," +
                        "pincode VARCHAR(10) NOT NULL," +
                        "state VARCHAR(50) NOT NULL," +
                        "religion VARCHAR(50)," +
                        "category VARCHAR(50)," +
                        "income VARCHAR(50)," +
                        "education VARCHAR(50)," +
                        "occupation VARCHAR(50)," +
                        "pan VARCHAR(20) NOT NULL," +
                        "aadhar VARCHAR(20) NOT NULL," +
                        "seniorcitizen VARCHAR(10) DEFAULT 'No'," +
                        "existingaccount VARCHAR(10) DEFAULT 'No'," +
                        "accounttype VARCHAR(50) NOT NULL," +
                        "card_number VARCHAR(20) UNIQUE NOT NULL," +
                        "pin VARCHAR(10) NOT NULL," +
                        "facility VARCHAR(200)" +
                        ")";
                    c.statement.executeUpdate(createSignup);

                    // Create login table if not exists
                    String createLogin = "CREATE TABLE IF NOT EXISTS login (" +
                        "formno VARCHAR(20)," +
                        "card_number VARCHAR(20) UNIQUE NOT NULL," +
                        "pin VARCHAR(10) NOT NULL," +
                        "PRIMARY KEY (card_number, pin)" +
                        ")";
                    c.statement.executeUpdate(createLogin);

                    System.out.println("✓ Tables ensured to exist");
                } catch (Exception tableEx) {
                    System.err.println("⚠ Table creation issue: " + tableEx.getMessage());
                }

                // Insert into single signup table with all information (using explicit column names)
                signupQuery = "INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                            "address, city, pincode, state, religion, category, income, education, occupation, " +
                            "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                            "VALUES ('" + formNumber + "', '" + name + "', '" + fname + "', '" +
                            dob + "', '" + gender + "', '" + email + "', '" + marital + "', '" +
                            address + "', '" + city + "', '" + pincode + "', '" + state + "', '" +
                            religion + "', '" + category + "', '" + income + "', '" + education + "', '" +
                            occupation + "', '" + pan + "', '" + aadhar + "', '" + seniorCitizen + "', '" +
                            existingAccount + "', '" + accountType + "', '" + cardNumber + "', '" + pin + "', '" +
                            services + "')";

                // Insert into login table (using explicit column names)
                loginQuery = "INSERT INTO login (formno, card_number, pin) VALUES ('" +
                            formNumber + "', '" + cardNumber + "', '" + pin + "')";

                // Debug: Print queries and data
                System.out.println("=== SIGNUP DEBUG ===");
                System.out.println("Form Number: " + formNumber);
                System.out.println("Card Number: " + cardNumber);
                System.out.println("PIN: " + pin);
                System.out.println("Name: " + name);
                System.out.println("Signup Query: " + signupQuery);
                System.out.println("Login Query: " + loginQuery);

                // Execute queries with detailed tracking
                System.out.println("Executing signup query...");
                int signupResult = c.statement.executeUpdate(signupQuery);
                System.out.println("✓ Signup query executed successfully. Rows affected: " + signupResult);

                System.out.println("Executing login query...");
                int loginResult = c.statement.executeUpdate(loginQuery);
                System.out.println("✓ Login query executed successfully. Rows affected: " + loginResult);

                // Verify data was inserted
                System.out.println("Verifying data insertion...");
                ResultSet verifyRs = c.statement.executeQuery("SELECT name, card_number, pin FROM signup WHERE formno = '" + formNumber + "'");
                if (verifyRs.next()) {
                    System.out.println("✓ Data verified in signup table: " + verifyRs.getString("name"));
                } else {
                    System.out.println("❌ Data NOT found in signup table!");
                }

                // Show success message with card number and PIN
                JOptionPane.showMessageDialog(this,
                    "Account created successfully!\n\n" +
                    "Card Number: " + cardNumber + "\n" +
                    "PIN: " + pin + "\n\n" +
                    "Please note these details for future reference.");

                // Go to deposit screen
                setVisible(false);
                new Deposit(pin);

            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Signup Query: " + signupQuery);
                System.err.println("Login Query: " + loginQuery);

                String errorMsg = "Error creating account: " + ex.getMessage();
                if (ex.getMessage().contains("Duplicate entry")) {
                    errorMsg = "Account with this card number or form number already exists. Please try again.";
                } else if (ex.getMessage().contains("doesn't exist")) {
                    errorMsg = "Database table not found. Please contact administrator.";
                } else if (ex.getMessage().contains("Data too long")) {
                    errorMsg = "Some data is too long. Please check your entries and try again.";
                }

                JOptionPane.showMessageDialog(this, errorMsg);
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new SignupComplete();
    }
}