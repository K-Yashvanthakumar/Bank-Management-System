package bank.management.system;

import java.sql.*;

public class QuickDatabaseCheck {
    
    public static void main(String[] args) {
        System.out.println("🔍 Quick Database Check...");
        checkDatabase();
    }
    
    public static void checkDatabase() {
        try {
            Con c = new Con();
            
            // Check all users in signup table
            System.out.println("\n📊 All users in SIGNUP table:");
            ResultSet allUsers = c.statement.executeQuery("SELECT formno, name, card_number, pin FROM signup");
            int userCount = 0;
            while (allUsers.next()) {
                userCount++;
                String formno = allUsers.getString("formno");
                String name = allUsers.getString("name");
                String cardNumber = allUsers.getString("card_number");
                String pin = allUsers.getString("pin");
                
                System.out.println(userCount + ". Form: " + formno + ", Name: " + name + 
                    ", Card: " + cardNumber + ", PIN: " + pin);
            }
            
            if (userCount == 0) {
                System.out.println("❌ No users found in signup table!");
                
                // Try to insert a test user
                System.out.println("\n🔧 Inserting test user...");
                String insertUser = "INSERT INTO signup (formno, name, fname, dob, gender, email, marital, " +
                    "address, city, pincode, state, religion, category, income, education, occupation, " +
                    "pan, aadhar, seniorcitizen, existingaccount, accounttype, card_number, pin, facility) " +
                    "VALUES ('TEST001', 'Test User', 'Test Father', '01-01-1990', 'Male', 'test@email.com', " +
                    "'Unmarried', 'Test Address', 'Test City', '123456', 'Test State', " +
                    "'Hindu', 'General', '<1,50,000', 'Graduate', 'Salaried', " +
                    "'TESTPAN123', '123456789012', 'No', 'No', " +
                    "'Saving Account', '1234567890123456', '9999', 'ATM Card')";
                
                c.statement.executeUpdate(insertUser);
                
                String insertLogin = "INSERT INTO login (formno, card_number, pin) VALUES " +
                    "('TEST001', '1234567890123456', '9999')";
                c.statement.executeUpdate(insertLogin);
                
                System.out.println("✓ Test user inserted");
                System.out.println("Test Card: 1234567890123456, PIN: 9999");
            } else {
                System.out.println("✓ Found " + userCount + " users in database");
            }
            
            // Test specific search queries
            System.out.println("\n🧪 Testing search queries...");
            
            // Test PIN search
            String testPin = "1234";
            ResultSet pinSearch = c.statement.executeQuery("SELECT * FROM signup WHERE pin = '" + testPin + "'");
            if (pinSearch.next()) {
                System.out.println("✓ PIN search works for PIN: " + testPin);
                System.out.println("  Found user: " + pinSearch.getString("name"));
            } else {
                System.out.println("❌ PIN search failed for PIN: " + testPin);
            }
            
            // Test card search
            String testCard = "5040936000000001";
            ResultSet cardSearch = c.statement.executeQuery("SELECT * FROM signup WHERE card_number = '" + testCard + "'");
            if (cardSearch.next()) {
                System.out.println("✓ Card search works for Card: " + testCard);
                System.out.println("  Found user: " + cardSearch.getString("name"));
            } else {
                System.out.println("❌ Card search failed for Card: " + testCard);
            }
            
            // Show exact data for debugging
            System.out.println("\n📋 Exact data in database:");
            ResultSet exactData = c.statement.executeQuery("SELECT pin, card_number, name FROM signup LIMIT 5");
            while (exactData.next()) {
                String pin = exactData.getString("pin");
                String card = exactData.getString("card_number");
                String name = exactData.getString("name");
                
                System.out.println("PIN: '" + pin + "' (length: " + pin.length() + ")");
                System.out.println("Card: '" + card + "' (length: " + card.length() + ")");
                System.out.println("Name: '" + name + "'");
                System.out.println("---");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Database check failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
