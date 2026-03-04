package bank.management.system;

import java.sql.*;

public class TestAdminFeatures {

    public static void main(String[] args) {
        System.out.println("Testing Admin Features...");

        // Test database connection
        testDatabaseConnection();

        // Test table existence
        testTableExistence();

        // Test admin user creation
        testAdminUserCreation();

        // Test sample data
        testSampleData();

        System.out.println("Admin Features Test Complete!");
    }

    public static void testDatabaseConnection() {
        try {
            Con c = new Con();
            System.out.println("✓ Database connection successful");
        } catch (Exception e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
        }
    }

    public static void testTableExistence() {
        try {
            Con c = new Con();

            // Test signup table
            ResultSet rs1 = c.statement.executeQuery("SELECT COUNT(*) FROM signup");
            if (rs1.next()) {
                System.out.println("✓ SIGNUP table exists with " + rs1.getInt(1) + " records");
            }

            // Test login table
            ResultSet rs2 = c.statement.executeQuery("SELECT COUNT(*) FROM login");
            if (rs2.next()) {
                System.out.println("✓ LOGIN table exists with " + rs2.getInt(1) + " records");
            }

            // Test bank table
            ResultSet rs3 = c.statement.executeQuery("SELECT COUNT(*) FROM bank");
            if (rs3.next()) {
                System.out.println("✓ BANK table exists with " + rs3.getInt(1) + " records");
            }

            // Test admin_users table
            ResultSet rs4 = c.statement.executeQuery("SELECT COUNT(*) FROM admin_users");
            if (rs4.next()) {
                System.out.println("✓ ADMIN_USERS table exists with " + rs4.getInt(1) + " records");
            }

        } catch (Exception e) {
            System.out.println("❌ Table check failed: " + e.getMessage());
            System.out.println("Creating tables...");
            createTables();
        }
    }

    public static void createTables() {
        try {
            Con c = new Con();

            // Create signup table
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
                "facility VARCHAR(200)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            c.statement.executeUpdate(createSignup);
            System.out.println("✓ SIGNUP table created");

            // Create login table
            String createLogin = "CREATE TABLE IF NOT EXISTS login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "PRIMARY KEY (card_number, pin)" +
                ")";
            c.statement.executeUpdate(createLogin);
            System.out.println("✓ LOGIN table created");

            // Create bank table
            String createBank = "CREATE TABLE IF NOT EXISTS bank (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pin VARCHAR(10) NOT NULL," +
                "date DATE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "amount VARCHAR(20) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            c.statement.executeUpdate(createBank);
            System.out.println("✓ BANK table created");

            // Create admin_users table
            String createAdminUsers = "CREATE TABLE IF NOT EXISTS admin_users (" +
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
            System.out.println("❌ Error creating tables: " + e.getMessage());
        }
    }

    public static void testAdminUserCreation() {
        try {
            Con c = new Con();

            // Insert admin user
            String insertAdmin = "INSERT IGNORE INTO admin_users (username, password, full_name, email) " +
                "VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com')";
            c.statement.executeUpdate(insertAdmin);

            // Verify admin user exists
            ResultSet rs = c.statement.executeQuery("SELECT * FROM admin_users WHERE username = 'admin'");
            if (rs.next()) {
                System.out.println("✓ Admin user exists: " + rs.getString("full_name"));
            } else {
                System.out.println("❌ Admin user not found");
            }

        } catch (Exception e) {
            System.out.println("❌ Admin user creation failed: " + e.getMessage());
        }
    }

    public static void testSampleData() {
        try {
            Con c = new Con();

            // Check if sample user exists
            ResultSet rs = c.statement.executeQuery("SELECT COUNT(*) FROM signup WHERE formno = '1001'");
            if (rs.next() && rs.getInt(1) == 0) {
                // Insert sample user (using explicit column names)
                String sampleSignup = "INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                    "address, city, pincode, state, religion, category, income, education, occupation, " +
                    "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                    "VALUES ('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', " +
                    "'Unmarried', '123 Main St', 'Mumbai', '400001', 'Maharashtra', " +
                    "'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried', " +
                    "'ABCDE1234F', '123456789012', 'No', 'No', " +
                    "'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking')";
                c.statement.executeUpdate(sampleSignup);

                String sampleLogin = "INSERT INTO login (formno, card_number, pin) VALUES " +
                    "('1001', '5040936000000001', '1234')";
                c.statement.executeUpdate(sampleLogin);

                // Insert sample transactions
                String[] sampleTransactions = {
                    "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-15', 'Deposit', '5000')",
                    "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-16', 'Withdrawl', '1000')",
                    "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-17', 'Deposit', '2000')",
                    "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-01', 'Deposit', '3000')",
                    "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-02', 'Withdrawl', '800')"
                };

                for (String transaction : sampleTransactions) {
                    c.statement.executeUpdate(transaction);
                }

                System.out.println("✓ Sample data inserted");
            } else {
                System.out.println("✓ Sample data already exists");
            }

        } catch (Exception e) {
            System.out.println("❌ Sample data creation failed: " + e.getMessage());
        }
    }
}
