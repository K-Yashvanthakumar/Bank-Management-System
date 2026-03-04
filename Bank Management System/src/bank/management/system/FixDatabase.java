package bank.management.system;

import java.sql.*;

public class FixDatabase {
    
    public static void main(String[] args) {
        System.out.println("🔧 Fixing Database Structure...");
        fixDatabaseStructure();
    }
    
    public static void fixDatabaseStructure() {
        try {
            Con c = new Con();
            Statement stmt = c.statement;
            
            System.out.println("Dropping existing tables...");
            
            // Drop all tables in correct order (to handle foreign keys)
            try {
                stmt.executeUpdate("DROP TABLE IF EXISTS bank");
                stmt.executeUpdate("DROP TABLE IF EXISTS login");
                stmt.executeUpdate("DROP TABLE IF EXISTS signup");
                stmt.executeUpdate("DROP TABLE IF EXISTS admin_users");
                System.out.println("✓ Existing tables dropped");
            } catch (Exception e) {
                System.out.println("⚠ Some tables might not exist: " + e.getMessage());
            }
            
            System.out.println("Creating tables with correct structure...");
            
            // 1. Create SIGNUP table with correct structure
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
            
            // 2. Create LOGIN table
            String createLogin = "CREATE TABLE login (" +
                "formno VARCHAR(20)," +
                "card_number VARCHAR(20) UNIQUE NOT NULL," +
                "pin VARCHAR(10) NOT NULL," +
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
                "amount VARCHAR(20) NOT NULL" +
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
            
            // Verify table structure
            System.out.println("\n📊 Verifying SIGNUP table structure:");
            ResultSet rs = stmt.executeQuery("DESCRIBE signup");
            while (rs.next()) {
                System.out.println("  - " + rs.getString("Field") + " (" + rs.getString("Type") + ")");
            }
            
            System.out.println("\n🎉 Database structure fixed successfully!");
            System.out.println("You can now use the signup feature without errors.");
            
        } catch (Exception e) {
            System.err.println("❌ Error fixing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
