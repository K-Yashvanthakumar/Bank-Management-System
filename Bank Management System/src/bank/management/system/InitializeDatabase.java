package bank.management.system;

import java.sql.*;

public class InitializeDatabase {

    public static void createTablesIfNotExist() {
        try {
            Con c = new Con();
            Statement stmt = c.statement;

            // Drop existing signup table if it exists (to recreate with correct structure)
            try {
                stmt.executeUpdate("DROP TABLE IF EXISTS signup");
                System.out.println("✓ Dropped existing signup table");
            } catch (Exception e) {
                // Table might not exist, continue
            }

            // Create consolidated signup table
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
            System.out.println("✓ SIGNUP table (consolidated) created/verified");

            // Create login table
            String createLogin = "CREATE TABLE IF NOT EXISTS login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "PRIMARY KEY (card_number, pin)" +
                ")";
            stmt.executeUpdate(createLogin);

            // Create bank table for transactions
            String createBank = "CREATE TABLE IF NOT EXISTS bank (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pin VARCHAR(10) NOT NULL," +
                "date DATE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "amount VARCHAR(20) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.executeUpdate(createBank);

            // Create admin_users table
            String createAdminUsers = "CREATE TABLE IF NOT EXISTS admin_users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "full_name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "role VARCHAR(20) DEFAULT 'admin'" +
                ")";
            stmt.executeUpdate(createAdminUsers);

            // Insert default admin user
            String insertAdmin = "INSERT IGNORE INTO admin_users (username, password, full_name, email) " +
                "VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com')";
            stmt.executeUpdate(insertAdmin);

            // Create indexes for better performance
            try {
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_bank_pin ON bank(pin)");
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_bank_date ON bank(date)");
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_signup_pin ON signup(pin)");
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_signup_card ON signup(card_number)");
            } catch (SQLException e) {
                // Indexes might already exist, ignore error
            }

            System.out.println("Database tables initialized successfully!");

        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void insertSampleData() {
        try {
            Con c = new Con();
            Statement stmt = c.statement;

            // Check if sample data already exists
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM signup WHERE formno = '1001'");
            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("Sample data already exists.");
                return;
            }

            // Insert sample user
            String sampleSignup = "INSERT INTO signup VALUES " +
                "('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', " +
                "'Unmarried', '123 Main St', 'Mumbai', '400001', 'Maharashtra', " +
                "'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried', " +
                "'ABCDE1234F', '123456789012', 'No', 'No', " +
                "'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking', NOW())";
            stmt.executeUpdate(sampleSignup);

            String sampleLogin = "INSERT INTO login VALUES " +
                "('1001', '5040936000000001', '1234', NOW())";
            stmt.executeUpdate(sampleLogin);

            // Insert sample transactions
            String[] sampleTransactions = {
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-15', 'Deposit', '5000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-16', 'Withdrawl', '1000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-17', 'Deposit', '2000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-18', 'Withdrawl', '500')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-01', 'Deposit', '3000')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-02', 'Withdrawl', '800')",
                "INSERT INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-03', 'Deposit', '1500')"
            };

            for (String transaction : sampleTransactions) {
                stmt.executeUpdate(transaction);
            }

            System.out.println("Sample data inserted successfully!");
            System.out.println("Test account: Card Number: 5040936000000001, PIN: 1234");

        } catch (Exception e) {
            System.err.println("Error inserting sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Initializing Bank Management System Database...");
        createTablesIfNotExist();

        // Uncomment the line below to insert sample data
        insertSampleData();

        System.out.println("Database initialization complete!");
    }
}
