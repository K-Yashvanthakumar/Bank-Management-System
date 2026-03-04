-- Bank Management System Database Setup
-- Run this script to create all required tables

-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS bankmanagementsystem;
USE bankmanagementsystem;

-- Drop existing tables if they exist (for clean setup)
DROP TABLE IF EXISTS transaction_log;
DROP TABLE IF EXISTS account_balance;
DROP TABLE IF EXISTS bank;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS admin_users;
DROP TABLE IF EXISTS signup;

-- 1. SIGNUP TABLE - Stores all user information in one table
CREATE TABLE signup (
    formno VARCHAR(20) PRIMARY KEY,
    -- Personal Information
    name VARCHAR(100) NOT NULL,
    fname VARCHAR(100) NOT NULL,
    dob VARCHAR(20) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    marital VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    city VARCHAR(50) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    state VARCHAR(50) NOT NULL,
    -- Additional Information
    religion VARCHAR(50),
    category VARCHAR(50),
    income VARCHAR(50),
    education VARCHAR(50),
    occupation VARCHAR(50),
    pan VARCHAR(20) NOT NULL,
    aadhar VARCHAR(20) NOT NULL,
    seniorcitizen VARCHAR(10) DEFAULT 'No',
    existingaccount VARCHAR(10) DEFAULT 'No',
    -- Account Information
    accounttype VARCHAR(50) NOT NULL,
    card_number VARCHAR(20) UNIQUE NOT NULL,
    pin VARCHAR(10) NOT NULL,
    facility VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. LOGIN TABLE - Stores login credentials
CREATE TABLE login (
    formno VARCHAR(20),
    card_number VARCHAR(20) UNIQUE NOT NULL,
    pin VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (card_number, pin)
);

-- 3. BANK TABLE - Stores all transactions (deposits and withdrawals)
CREATE TABLE bank (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pin VARCHAR(10) NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(20) NOT NULL, -- 'Deposit' or 'Withdrawl'
    amount VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. ADMIN_USERS TABLE - Stores admin login credentials (for admin features)
CREATE TABLE admin_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
);

-- Insert default admin user
INSERT IGNORE INTO admin_users (username, password, full_name, email)
VALUES ('admin', 'admin123', 'System Administrator', 'admin@bank.com');

-- 5. ACCOUNT_BALANCE TABLE - Stores current balance for each account (optional for performance)
CREATE TABLE account_balance (
    pin VARCHAR(10) PRIMARY KEY,
    card_number VARCHAR(20) UNIQUE NOT NULL,
    current_balance DECIMAL(15,2) DEFAULT 0.00,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 6. TRANSACTION_LOG TABLE - Detailed transaction log for audit purposes
CREATE TABLE transaction_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pin VARCHAR(10) NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    balance_before DECIMAL(15,2),
    balance_after DECIMAL(15,2),
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'SUCCESS',
    notes TEXT
);

-- Create indexes for better performance
CREATE INDEX idx_bank_pin ON bank(pin);
CREATE INDEX idx_bank_date ON bank(date);
CREATE INDEX idx_bank_type ON bank(type);
CREATE INDEX idx_signup_pin ON signup(pin);
CREATE INDEX idx_signup_card ON signup(card_number);
CREATE INDEX idx_signup_name ON signup(name);
CREATE INDEX idx_signup_email ON signup(email);
CREATE INDEX idx_login_card ON login(card_number);
CREATE INDEX idx_login_pin ON login(pin);

-- Sample data for testing (optional)
-- Uncomment the following lines if you want to insert sample data

-- Sample user data (consolidated table)
INSERT IGNORE INTO signup VALUES
('1001', 'John Doe', 'Robert Doe', '15-01-1990', 'Male', 'john.doe@email.com', 'Unmarried',
 '123 Main St', 'Mumbai', '400001', 'Maharashtra',
 'Hindu', 'General', '<2,50,000', 'Graduate', 'Salaried',
 'ABCDE1234F', '123456789012', 'No', 'No',
 'Saving Account', '5040936000000001', '1234', 'ATM Card Internet Banking Mobile Banking',
 NOW());

-- Sample login data
INSERT IGNORE INTO login VALUES
('1001', '5040936000000001', '1234', NOW());

-- Sample transactions
INSERT IGNORE INTO bank (pin, date, type, amount) VALUES
('1234', '2024-01-15', 'Deposit', '5000'),
('1234', '2024-01-16', 'Withdrawl', '1000'),
('1234', '2024-01-17', 'Deposit', '2000'),
('1234', '2024-01-18', 'Withdrawl', '500'),
('1234', '2024-12-01', 'Deposit', '3000'),
('1234', '2024-12-02', 'Withdrawl', '800'),
('1234', '2024-12-03', 'Deposit', '1500');

-- Display table creation status
SELECT 'Database setup completed successfully!' as Status;
SELECT 'Tables created: signup, login, bank, admin_users, account_balance, transaction_log' as Tables;
SELECT 'Indexes created for better performance' as Indexes;
SELECT 'Sample data inserted for testing' as SampleData;
SELECT 'Test Account: Card Number: 5040936000000001, PIN: 1234' as TestAccount;
