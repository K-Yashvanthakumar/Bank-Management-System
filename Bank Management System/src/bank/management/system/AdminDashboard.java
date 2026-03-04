package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {
    JButton viewAllUsersBtn, searchUserBtn, transactionHistoryBtn, deleteUserBtn, resetPinBtn, addUserBtn, exportDataBtn, logoutBtn;
    
    AdminDashboard() {
        setTitle("ADMIN DASHBOARD");
        
        // Set layout and size
        setLayout(null);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set background color
        getContentPane().setBackground(new Color(204, 229, 255));
        
        // Bank Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 20, 100, 100);
        add(image);
        
        // Title
        JLabel title = new JLabel("ADMIN DASHBOARD");
        title.setFont(new Font("Raleway", Font.BOLD, 38));
        title.setBounds(280, 130, 400, 40);
        add(title);
        
        // View All Users Button
        viewAllUsersBtn = new JButton("View All Users");
        viewAllUsersBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        viewAllUsersBtn.setBounds(150, 200, 250, 50);
        viewAllUsersBtn.setBackground(new Color(65, 125, 128));
        viewAllUsersBtn.setForeground(Color.WHITE);
        viewAllUsersBtn.addActionListener(this);
        add(viewAllUsersBtn);
        
        // Search User Button
        searchUserBtn = new JButton("Search User");
        searchUserBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        searchUserBtn.setBounds(500, 200, 250, 50);
        searchUserBtn.setBackground(new Color(65, 125, 128));
        searchUserBtn.setForeground(Color.WHITE);
        searchUserBtn.addActionListener(this);
        add(searchUserBtn);
        
        // Transaction History Button
        transactionHistoryBtn = new JButton("Transaction History");
        transactionHistoryBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        transactionHistoryBtn.setBounds(150, 280, 250, 50);
        transactionHistoryBtn.setBackground(new Color(65, 125, 128));
        transactionHistoryBtn.setForeground(Color.WHITE);
        transactionHistoryBtn.addActionListener(this);
        add(transactionHistoryBtn);
        
        // Delete User Button
        deleteUserBtn = new JButton("Delete User Account");
        deleteUserBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        deleteUserBtn.setBounds(500, 280, 250, 50);
        deleteUserBtn.setBackground(new Color(65, 125, 128));
        deleteUserBtn.setForeground(Color.WHITE);
        deleteUserBtn.addActionListener(this);
        add(deleteUserBtn);
        
        // Reset PIN Button
        resetPinBtn = new JButton("Reset User PIN");
        resetPinBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        resetPinBtn.setBounds(150, 360, 250, 50);
        resetPinBtn.setBackground(new Color(65, 125, 128));
        resetPinBtn.setForeground(Color.WHITE);
        resetPinBtn.addActionListener(this);
        add(resetPinBtn);
        
        // Add New User Button
        addUserBtn = new JButton("Add New User");
        addUserBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        addUserBtn.setBounds(500, 360, 250, 50);
        addUserBtn.setBackground(new Color(65, 125, 128));
        addUserBtn.setForeground(Color.WHITE);
        addUserBtn.addActionListener(this);
        add(addUserBtn);
        
        // Export Data Button
        exportDataBtn = new JButton("Export Data");
        exportDataBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        exportDataBtn.setBounds(150, 440, 250, 50);
        exportDataBtn.setBackground(new Color(65, 125, 128));
        exportDataBtn.setForeground(Color.WHITE);
        exportDataBtn.addActionListener(this);
        add(exportDataBtn);
        
        // Logout Button
        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Raleway", Font.BOLD, 18));
        logoutBtn.setBounds(500, 440, 250, 50);
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.addActionListener(this);
        add(logoutBtn);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAllUsersBtn) {
            new ViewAllUsers();
        } else if (e.getSource() == searchUserBtn) {
            new SearchUser();
        } else if (e.getSource() == transactionHistoryBtn) {
            new AdminTransactionHistory();
        } else if (e.getSource() == deleteUserBtn) {
            new DeleteUser();
        } else if (e.getSource() == resetPinBtn) {
            new ResetUserPIN();
        } else if (e.getSource() == addUserBtn) {
            new AdminAddUser();
        } else if (e.getSource() == exportDataBtn) {
            new ExportData();
        } else if (e.getSource() == logoutBtn) {
            setVisible(false);
            new AdminLogin();
        }
    }
    
    public static void main(String[] args) {
        new AdminDashboard();
    }
}
