package bank.management.system;

import java.sql.*;

public class DirectDatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("🔍 Direct Database Connection Test...");
        
        try {
            // Test direct connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankSystem", "root", "1234");
            Statement stmt = conn.createStatement();
            System.out.println("✓ Direct database connection successful");
            
            // Create database if not exists
            try {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS bankSystem");
                stmt.executeUpdate("USE bankSystem");
                System.out.println("✓ Database bankSystem created/selected");
            } catch (Exception e) {
                System.out.println("⚠ Database issue: " + e.getMessage());
            }
            
            // Drop and recreate tables
            System.out.println("\n🔄 Recreating tables...");
            
            stmt.executeUpdate("DROP TABLE IF EXISTS bank");
            stmt.executeUpdate("DROP TABLE IF EXISTS login");
            stmt.executeUpdate("DROP TABLE IF EXISTS signup");
            stmt.executeUpdate("DROP TABLE IF EXISTS admin_users");
            System.out.println("✓ Old tables dropped");
            
            // Create signup table
            String createSignup = "CREATE TABLE signup (" +
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
            stmt.executeUpdate(createSignup);
            System.out.println("✓ SIGNUP table created");
            
            // Create login table
            String createLogin = "CREATE TABLE login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "PRIMARY KEY (card_number, pin)" +
                ")";
            stmt.executeUpdate(createLogin);
            System.out.println("✓ LOGIN table created");
            
            // Create bank table
            String createBank = "CREATE TABLE bank (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pin VARCHAR(10) NOT NULL," +
                "date DATE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "amount VARCHAR(20) NOT NULL" +
                ")";
            stmt.executeUpdate(createBank);
            System.out.println("✓ BANK table created");
            
            // Create admin_users table
            String createAdminUsers = "CREATE TABLE admin_users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "full_name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "role VARCHAR(20) DEFAULT 'admin'" +
                ")";
            stmt.executeUpdate(createAdminUsers);
            System.out.println("✓ ADMIN_USERS table created");
            
            // Insert test data
            System.out.println("\n📊 Inserting test data...");
            
            // Insert admin
            stmt.executeUpdate("INSERT INTO admin_users (username, password, full_name, email) " +
                "VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com')");
            System.out.println("✓ Admin user inserted");
            
            // Insert test user
            stmt.executeUpdate("INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                "address, city, pincode, state, religion, category, income, education, occupation, " +
                "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                "VALUES ('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', " +
                "'Unmarried', '123 Main St', 'Mumbai', '400001', 'Maharashtra', " +
                "'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried', " +
                "'ABCDE1234F', '123456789012', 'No', 'No', " +
                "'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking')");
            System.out.println("✓ Test user inserted");
            
            // Insert login
            stmt.executeUpdate("INSERT INTO login (formno, card_number, pin) VALUES " +
                "('1001', '5040936000000001', '1234')");
            System.out.println("✓ Login credentials inserted");
            
            // Insert transactions
            stmt.executeUpdate("INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-15', 'Deposit', '5000')");
            stmt.executeUpdate("INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-16', 'Withdrawl', '1000')");
            stmt.executeUpdate("INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-01', 'Deposit', '3000')");
            System.out.println("✓ Test transactions inserted");
            
            // Verify data
            System.out.println("\n🔍 Verifying data...");
            
            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) as count FROM signup");
            if (rs1.next()) {
                System.out.println("SIGNUP records: " + rs1.getInt("count"));
            }
            
            ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) as count FROM login");
            if (rs2.next()) {
                System.out.println("LOGIN records: " + rs2.getInt("count"));
            }
            
            ResultSet rs3 = stmt.executeQuery("SELECT COUNT(*) as count FROM bank");
            if (rs3.next()) {
                System.out.println("BANK records: " + rs3.getInt("count"));
            }
            
            ResultSet rs4 = stmt.executeQuery("SELECT COUNT(*) as count FROM admin_users");
            if (rs4.next()) {
                System.out.println("ADMIN_USERS records: " + rs4.getInt("count"));
            }
            
            // Test specific queries
            System.out.println("\n🧪 Testing specific queries...");
            
            // Test user retrieval
            ResultSet userRs = stmt.executeQuery("SELECT name, card_number, pin FROM signup WHERE formno = '1001'");
            if (userRs.next()) {
                System.out.println("✓ User found: " + userRs.getString("name") + 
                    " (Card: " + userRs.getString("card_number") + ", PIN: " + userRs.getString("pin") + ")");
            } else {
                System.out.println("❌ User not found!");
            }
            
            // Test login
            ResultSet loginRs = stmt.executeQuery("SELECT * FROM login WHERE card_number = '5040936000000001' AND pin = '1234'");
            if (loginRs.next()) {
                System.out.println("✓ Login test successful");
            } else {
                System.out.println("❌ Login test failed");
            }
            
            // Test admin
            ResultSet adminRs = stmt.executeQuery("SELECT * FROM admin_users WHERE username = 'admin' AND password = 'admin123'");
            if (adminRs.next()) {
                System.out.println("✓ Admin login test successful");
            } else {
                System.out.println("❌ Admin login test failed");
            }
            
            System.out.println("\n🎉 Database setup completed successfully!");
            System.out.println("Test Account: Card Number: 5040936000000001, PIN: 1234");
            System.out.println("Admin Login: Username: admin, Password: admin123");
            
            conn.close();
            
        } catch (Exception e) {
            System.err.println("❌ Database test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
