package bank.management.system;

import java.sql.*;

public class DatabaseVerifier {
    
    public static void main(String[] args) {
        System.out.println("🔍 Verifying Database Connection and Data...");
        verifyAndFixDatabase();
    }
    
    public static void verifyAndFixDatabase() {
        try {
            // Test connection
            Con c = new Con();
            System.out.println("✓ Database connection successful");
            
            // Create database if it doesn't exist
            try {
                c.statement.executeUpdate("CREATE DATABASE IF NOT EXISTS bankSystem");
                c.statement.executeUpdate("USE bankSystem");
                System.out.println("✓ Database 'bankSystem' ensured");
            } catch (Exception e) {
                System.out.println("⚠ Database creation issue: " + e.getMessage());
            }
            
            // Drop and recreate all tables to ensure correct structure
            System.out.println("\n🔄 Recreating all tables...");
            
            // Drop existing tables
            String[] dropTables = {"bank", "login", "signup", "admin_users"};
            for (String table : dropTables) {
                try {
                    c.statement.executeUpdate("DROP TABLE IF EXISTS " + table);
                    System.out.println("✓ Dropped table: " + table);
                } catch (Exception e) {
                    System.out.println("⚠ Could not drop " + table + ": " + e.getMessage());
                }
            }
            
            // Create SIGNUP table
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
            
            // Create LOGIN table
            String createLogin = "CREATE TABLE login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "PRIMARY KEY (card_number, pin)" +
                ")";
            c.statement.executeUpdate(createLogin);
            System.out.println("✓ LOGIN table created");
            
            // Create BANK table
            String createBank = "CREATE TABLE bank (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pin VARCHAR(10) NOT NULL," +
                "date DATE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "amount VARCHAR(20) NOT NULL" +
                ")";
            c.statement.executeUpdate(createBank);
            System.out.println("✓ BANK table created");
            
            // Create ADMIN_USERS table
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
            
            // Insert admin user
            String insertAdmin = "INSERT INTO admin_users (username, password, full_name, email) " +
                "VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com')";
            c.statement.executeUpdate(insertAdmin);
            System.out.println("✓ Admin user inserted");
            
            // Insert sample data
            System.out.println("\n📊 Inserting sample data...");
            
            // Sample user
            String sampleSignup = "INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                "address, city, pincode, state, religion, category, income, education, occupation, " +
                "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                "VALUES ('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', " +
                "'Unmarried', '123 Main St', 'Mumbai', '400001', 'Maharashtra', " +
                "'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried', " +
                "'ABCDE1234F', '123456789012', 'No', 'No', " +
                "'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking')";
            c.statement.executeUpdate(sampleSignup);
            System.out.println("✓ Sample user inserted");
            
            String sampleLogin = "INSERT INTO login (formno, card_number, pin) VALUES " +
                "('1001', '5040936000000001', '1234')";
            c.statement.executeUpdate(sampleLogin);
            System.out.println("✓ Sample login inserted");
            
            // Sample transactions
            String[] sampleTransactions = {
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-15', 'Deposit', '5000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-16', 'Withdrawl', '1000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-17', 'Deposit', '2000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-01', 'Deposit', '3000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-02', 'Withdrawl', '800')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-03', 'Deposit', '1500')"
            };
            
            for (String transaction : sampleTransactions) {
                c.statement.executeUpdate(transaction);
            }
            System.out.println("✓ Sample transactions inserted");
            
            // Verify data
            System.out.println("\n🔍 Verifying inserted data...");
            
            ResultSet signupCount = c.statement.executeQuery("SELECT COUNT(*) as count FROM signup");
            if (signupCount.next()) {
                System.out.println("SIGNUP table: " + signupCount.getInt("count") + " records");
            }
            
            ResultSet loginCount = c.statement.executeQuery("SELECT COUNT(*) as count FROM login");
            if (loginCount.next()) {
                System.out.println("LOGIN table: " + loginCount.getInt("count") + " records");
            }
            
            ResultSet bankCount = c.statement.executeQuery("SELECT COUNT(*) as count FROM bank");
            if (bankCount.next()) {
                System.out.println("BANK table: " + bankCount.getInt("count") + " records");
            }
            
            ResultSet adminCount = c.statement.executeQuery("SELECT COUNT(*) as count FROM admin_users");
            if (adminCount.next()) {
                System.out.println("ADMIN_USERS table: " + adminCount.getInt("count") + " records");
            }
            
            // Test sample user retrieval
            System.out.println("\n🧪 Testing data retrieval...");
            ResultSet testUser = c.statement.executeQuery("SELECT name, card_number, pin FROM signup WHERE formno = '1001'");
            if (testUser.next()) {
                System.out.println("✓ Sample user found: " + testUser.getString("name") + 
                    " (Card: " + testUser.getString("card_number") + ", PIN: " + testUser.getString("pin") + ")");
            } else {
                System.out.println("❌ Sample user not found!");
            }
            
            System.out.println("\n🎉 Database verification and setup completed successfully!");
            System.out.println("Test Account: Card Number: 5040936000000001, PIN: 1234");
            System.out.println("Admin Login: Username: admin, Password: admin123");
            
        } catch (Exception e) {
            System.err.println("❌ Database verification failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
