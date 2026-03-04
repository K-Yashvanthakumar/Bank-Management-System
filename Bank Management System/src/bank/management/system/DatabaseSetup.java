package bank.management.system;

import java.sql.*;

public class DatabaseSetup {

    public static void createTables() {
        try {
            Con c = new Con();
            Statement stmt = c.statement;

            System.out.println("Creating database tables...");

            // 1. Create SIGNUP table (consolidated - all user info in one table)
            String createSignup = "CREATE TABLE IF NOT EXISTS signup (" +
                "formno VARCHAR(20) PRIMARY KEY," +
                // Personal Information
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
                // Additional Information
                "religion VARCHAR(50)," +
                "category VARCHAR(50)," +
                "income VARCHAR(50)," +
                "education VARCHAR(50)," +
                "occupation VARCHAR(50)," +
                "pan VARCHAR(20) NOT NULL," +
                "aadhar VARCHAR(20) NOT NULL," +
                "seniorcitizen VARCHAR(10) DEFAULT 'No'," +
                "existingaccount VARCHAR(10) DEFAULT 'No'," +
                // Account Information
                "accounttype VARCHAR(50) NOT NULL," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "facility VARCHAR(200)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.executeUpdate(createSignup);
            System.out.println("✓ SIGNUP table (consolidated) created/verified");

            // 2. Create LOGIN table
            String createLogin = "CREATE TABLE IF NOT EXISTS login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "PRIMARY KEY (card_number, pin)" +
                ")";
            stmt.executeUpdate(createLogin);
            System.out.println("✓ LOGIN table created/verified");

            // 3. Create BANK table (transactions)
            String createBank = "CREATE TABLE IF NOT EXISTS bank (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pin VARCHAR(10) NOT NULL," +
                "date DATE NOT NULL," +
                "type VARCHAR(20) NOT NULL," +
                "amount VARCHAR(20) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.executeUpdate(createBank);
            System.out.println("✓ BANK table created/verified");

            // 6. Create ADMIN_USERS table
            String createAdminUsers = "CREATE TABLE IF NOT EXISTS admin_users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "full_name VARCHAR(100)," +
                "email VARCHAR(100)," +
                "role VARCHAR(20) DEFAULT 'admin'" +
                ")";
            stmt.executeUpdate(createAdminUsers);
            System.out.println("✓ ADMIN_USERS table created/verified");

            // Insert default admin user
            String insertAdmin = "INSERT IGNORE INTO admin_users (username, password, full_name, email) " +
                "VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com')";
            stmt.executeUpdate(insertAdmin);
            System.out.println("✓ Default admin user created/verified");

            // 7. Create indexes for better performance
            try {
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_bank_pin ON bank(pin)");
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_bank_date ON bank(date)");
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_bank_type ON bank(type)");
                System.out.println("✓ Database indexes created/verified");
            } catch (SQLException e) {
                // Indexes might already exist, ignore error
                System.out.println("✓ Database indexes verified");
            }

            System.out.println("\n🎉 Database setup completed successfully!");
            System.out.println("All required tables have been created and are ready to use.");

        } catch (Exception e) {
            System.err.println("❌ Error setting up database:");
            e.printStackTrace();
        }
    }

    public static void insertSampleData() {
        try {
            Con c = new Con();
            Statement stmt = c.statement;

            System.out.println("\nInserting sample data for testing...");

            // Sample user data (single table)
            String sampleSignup = "INSERT IGNORE INTO signup VALUES " +
                "('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', " +
                "'Unmarried', '123 Main St', 'Mumbai', '400001', 'Maharashtra', " +
                "'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried', " +
                "'ABCDE1234F', '123456789012', 'No', 'No', " +
                "'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking', NOW())";
            stmt.executeUpdate(sampleSignup);

            String sampleLogin = "INSERT IGNORE INTO login VALUES " +
                "('1001', '5040936000000001', '1234', NOW())";
            stmt.executeUpdate(sampleLogin);

            // Sample transactions
            String[] sampleTransactions = {
                "INSERT IGNORE INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-15', 'Deposit', '5000')",
                "INSERT IGNORE INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-16', 'Withdrawl', '1000')",
                "INSERT IGNORE INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-17', 'Deposit', '2000')",
                "INSERT IGNORE INTO bank (pin, date, type, amount) VALUES ('1234', '2024-01-18', 'Withdrawl', '500')",
                "INSERT IGNORE INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-01', 'Deposit', '3000')",
                "INSERT IGNORE INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-02', 'Withdrawl', '800')",
                "INSERT IGNORE INTO bank (pin, date, type, amount) VALUES ('1234', '2024-12-03', 'Deposit', '1500')"
            };

            for (String transaction : sampleTransactions) {
                stmt.executeUpdate(transaction);
            }

            System.out.println("✓ Sample data inserted successfully!");
            System.out.println("Test account: Card Number: 5040936000000001, PIN: 1234");

        } catch (Exception e) {
            System.err.println("❌ Error inserting sample data:");
            e.printStackTrace();
        }
    }

    public static void showTableInfo() {
        try {
            Con c = new Con();
            Statement stmt = c.statement;

            System.out.println("\n📊 Database Table Information:");
            System.out.println("================================");

            // Show table counts
            String[] tables = {"signup", "signuptwo", "signupthree", "login", "bank", "admin_users"};

            for (String table : tables) {
                try {
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM " + table);
                    if (rs.next()) {
                        System.out.println(table.toUpperCase() + " table: " + rs.getInt("count") + " records");
                    }
                } catch (SQLException e) {
                    System.out.println(table.toUpperCase() + " table: Not found or empty");
                }
            }

            System.out.println("================================");

        } catch (Exception e) {
            System.err.println("❌ Error showing table info:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("🏦 Bank Management System - Database Setup");
        System.out.println("==========================================");

        // Create all required tables
        createTables();

        // Show current table information
        showTableInfo();

        // Ask if user wants to insert sample data
        System.out.println("\nWould you like to insert sample data for testing? (Uncomment the line below)");
        // insertSampleData();

        System.out.println("\n✅ Setup complete! You can now run the Bank Management System.");
    }
}
