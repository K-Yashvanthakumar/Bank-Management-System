package bank.management.system;

import java.sql.*;

public class DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("🧪 Testing Database Operations...");
        testDatabaseOperations();
    }
    
    public static void testDatabaseOperations() {
        try {
            Con c = new Con();
            System.out.println("✓ Database connection successful");
            
            // Test 1: Create tables
            System.out.println("\n1️⃣ Creating tables...");
            createTables(c);
            
            // Test 2: Insert test data
            System.out.println("\n2️⃣ Inserting test data...");
            insertTestData(c);
            
            // Test 3: Retrieve data
            System.out.println("\n3️⃣ Retrieving data...");
            retrieveData(c);
            
            // Test 4: Test login functionality
            System.out.println("\n4️⃣ Testing login...");
            testLogin(c);
            
        } catch (Exception e) {
            System.err.println("❌ Database test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createTables(Con c) {
        try {
            // Drop existing tables
            c.statement.executeUpdate("DROP TABLE IF EXISTS bank");
            c.statement.executeUpdate("DROP TABLE IF EXISTS login");
            c.statement.executeUpdate("DROP TABLE IF EXISTS signup");
            c.statement.executeUpdate("DROP TABLE IF EXISTS admin_users");
            
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
            c.statement.executeUpdate(createSignup);
            System.out.println("✓ SIGNUP table created");
            
            // Create login table
            String createLogin = "CREATE TABLE login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "PRIMARY KEY (card_number, pin)" +
                ")";
            c.statement.executeUpdate(createLogin);
            System.out.println("✓ LOGIN table created");
            
            // Create bank table
            String createBank = "CREATE TABLE bank (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pin VARCHAR(10) NOT NULL," +
                "date DATE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "amount VARCHAR(20) NOT NULL" +
                ")";
            c.statement.executeUpdate(createBank);
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
            c.statement.executeUpdate(createAdminUsers);
            System.out.println("✓ ADMIN_USERS table created");
            
        } catch (Exception e) {
            System.err.println("❌ Error creating tables: " + e.getMessage());
        }
    }
    
    private static void insertTestData(Con c) {
        try {
            // Insert admin user
            String insertAdmin = "INSERT INTO admin_users (username, password, full_name, email) " +
                "VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com')";
            c.statement.executeUpdate(insertAdmin);
            System.out.println("✓ Admin user inserted");
            
            // Insert test user
            String insertSignup = "INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                "address, city, pincode, state, religion, category, income, education, occupation, " +
                "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                "VALUES ('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', " +
                "'Unmarried', '123 Main St', 'Mumbai', '400001', 'Maharashtra', " +
                "'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried', " +
                "'ABCDE1234F', '123456789012', 'No', 'No', " +
                "'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking')";
            c.statement.executeUpdate(insertSignup);
            System.out.println("✓ Test user inserted");
            
            // Insert login credentials
            String insertLogin = "INSERT INTO login (formno, card_number, pin) VALUES " +
                "('1001', '5040936000000001', '1234')";
            c.statement.executeUpdate(insertLogin);
            System.out.println("✓ Login credentials inserted");
            
            // Insert test transactions
            String[] transactions = {
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-15', 'Deposit', '5000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-16', 'Withdrawl', '1000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-01', 'Deposit', '3000')"
            };
            
            for (String transaction : transactions) {
                c.statement.executeUpdate(transaction);
            }
            System.out.println("✓ Test transactions inserted");
            
        } catch (Exception e) {
            System.err.println("❌ Error inserting test data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void retrieveData(Con c) {
        try {
            // Check signup table
            ResultSet signupRs = c.statement.executeQuery("SELECT COUNT(*) as count FROM signup");
            if (signupRs.next()) {
                System.out.println("SIGNUP table: " + signupRs.getInt("count") + " records");
            }
            
            // Check login table
            ResultSet loginRs = c.statement.executeQuery("SELECT COUNT(*) as count FROM login");
            if (loginRs.next()) {
                System.out.println("LOGIN table: " + loginRs.getInt("count") + " records");
            }
            
            // Check bank table
            ResultSet bankRs = c.statement.executeQuery("SELECT COUNT(*) as count FROM bank");
            if (bankRs.next()) {
                System.out.println("BANK table: " + bankRs.getInt("count") + " records");
            }
            
            // Show actual user data
            ResultSet userRs = c.statement.executeQuery("SELECT name, card_number, pin FROM signup");
            System.out.println("Users in database:");
            while (userRs.next()) {
                System.out.println("  - " + userRs.getString("name") + 
                    " (Card: " + userRs.getString("card_number") + ", PIN: " + userRs.getString("pin") + ")");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error retrieving data: " + e.getMessage());
        }
    }
    
    private static void testLogin(Con c) {
        try {
            // Test login query
            String testCard = "5040936000000001";
            String testPin = "1234";
            
            String loginQuery = "SELECT * FROM login WHERE card_number = '" + testCard + "' AND pin = '" + testPin + "'";
            ResultSet loginRs = c.statement.executeQuery(loginQuery);
            
            if (loginRs.next()) {
                System.out.println("✓ Login test successful for card: " + testCard);
            } else {
                System.out.println("❌ Login test failed for card: " + testCard);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Login test error: " + e.getMessage());
        }
    }
}
