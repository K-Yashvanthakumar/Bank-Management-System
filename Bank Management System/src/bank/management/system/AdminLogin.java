package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminLogin extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, clearButton, backButton;

    AdminLogin() {
        setTitle("ADMIN LOGIN");

        // Set layout and size
        setLayout(null);
        setSize(800, 480);
        setLocationRelativeTo(null);
        setVisible(true);

        // Set background color
        getContentPane().setBackground(new Color(222, 255, 228));

        // Bank Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 10, 100, 100);
        add(image);

        // Title
        JLabel title = new JLabel("ADMIN LOGIN");
        title.setFont(new Font("Raleway", Font.BOLD, 38));
        title.setBounds(280, 125, 400, 40);
        add(title);

        // Username Label and Field
        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Raleway", Font.BOLD, 28));
        username.setBounds(150, 190, 150, 30);
        add(username);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField.setBounds(320, 190, 250, 30);
        add(usernameField);

        // Password Label and Field
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Raleway", Font.BOLD, 28));
        password.setBounds(150, 250, 150, 30);
        add(password);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField.setBounds(320, 250, 250, 30);
        add(passwordField);

        // Login Button
        loginButton = new JButton("SIGN IN");
        loginButton.setBounds(320, 300, 100, 30);
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        add(loginButton);

        // Clear Button
        clearButton = new JButton("CLEAR");
        clearButton.setBounds(470, 300, 100, 30);
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(this);
        add(clearButton);

        // Back Button
        backButton = new JButton("BACK");
        backButton.setBounds(150, 300, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password!");
                return;
            }

            try {
                // Verify database structure first
                DatabaseVerifier.verifyAndFixDatabase();

                // Check admin credentials from database
                Con c = new Con();
                String query = "SELECT * FROM admin_users WHERE username = '" + username + "' AND password = '" + password + "'";
                ResultSet rs = c.statement.executeQuery(query);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Admin Login Successful!\nWelcome, " + rs.getString("full_name"));
                    setVisible(false);
                    new AdminDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid admin credentials!\nPlease check your username and password.");
                    usernameField.setText("");
                    passwordField.setText("");
                }
            } catch (Exception ex) {
                // Fallback to hardcoded credentials if database check fails
                if (username.equals("admin") && password.equals("admin123")) {
                    JOptionPane.showMessageDialog(this, "Admin Login Successful! (Fallback mode)");
                    setVisible(false);
                    new AdminDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid admin credentials!");
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        } else if (e.getSource() == clearButton) {
            usernameField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == backButton) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
