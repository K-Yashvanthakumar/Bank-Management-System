package bank.management.system;

import java.sql.*;

public class ResetDatabase {
    
    public static void main(String[] args) {
        System.out.println("🔄 Resetting Bank Management System Database...");
        
        try {
            Con c = new Con();
            Statement stmt = c.statement;
            
            // Drop all existing tables
            System.out.println("Dropping existing tables...");
            try {
                stmt.executeUpdate("DROP TABLE IF EXISTS bank");
                stmt.executeUpdate("DROP TABLE IF EXISTS login");
                stmt.executeUpdate("DROP TABLE IF EXISTS signup");
                stmt.executeUpdate("DROP TABLE IF EXISTS admin_users");
                System.out.println("✓ All existing tables dropped");
            } catch (Exception e) {
                System.out.println("⚠ Some tables might not exist: " + e.getMessage());
            }
            
            // Create fresh tables
            System.out.println("\nCreating fresh tables...");
            
            // 1. Create SIGNUP table
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
                "facility VARCHAR(200)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.executeUpdate(createSignup);
            System.out.println("✓ SIGNUP table created");
            
            // 2. Create LOGIN table
            String createLogin = "CREATE TABLE login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "PRIMARY KEY (card_number, pin)" +
                ")";
            stmt.executeUpdate(createLogin);
            System.out.println("✓ LOGIN table created");
            
            // 3. Create BANK table
            String createBank = "CREATE TABLE bank (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pin VARCHAR(10) NOT NULL," +
                "date DATE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "amount VARCHAR(20) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.executeUpdate(createBank);
            System.out.println("✓ BANK table created");
            
            // 4. Create ADMIN_USERS table
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
            
            // Insert default admin user
            String insertAdmin = "INSERT INTO admin_users (username, password, full_name, email) " +
                "VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com')";
            stmt.executeUpdate(insertAdmin);
            System.out.println("✓ Default admin user created");
            
            // Insert sample data
            System.out.println("\nInserting sample data...");
            
            // Sample user
            String sampleSignup = "INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                "address, city, pincode, state, religion, category, income, education, occupation, " +
                "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                "VALUES ('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', " +
                "'Unmarried', '123 Main St', 'Mumbai', '400001', 'Maharashtra', " +
                "'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried', " +
                "'ABCDE1234F', '123456789012', 'No', 'No', " +
                "'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking')";
            stmt.executeUpdate(sampleSignup);
            
            String sampleLogin = "INSERT INTO login (formno, card_number, pin) VALUES " +
                "('1001', '5040936000000001', '1234')";
            stmt.executeUpdate(sampleLogin);
            
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
                stmt.executeUpdate(transaction);
            }
            
            System.out.println("✓ Sample data inserted");
            
            // Verify table structure
            System.out.println("\n📊 Verifying table structure...");
            ResultSet rs = stmt.executeQuery("DESCRIBE signup");
            System.out.println("SIGNUP table columns:");
            while (rs.next()) {
                System.out.println("  - " + rs.getString("Field") + " (" + rs.getString("Type") + ")");
            }
            
            System.out.println("\n🎉 Database reset completed successfully!");
            System.out.println("Test Account: Card Number: 5040936000000001, PIN: 1234");
            System.out.println("Admin Login: Username: admin, Password: admin123");
            
        } catch (Exception e) {
            System.err.println("❌ Error resetting database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
