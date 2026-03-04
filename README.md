# Online Banking Management System

## Project Overview

The **Online Banking Management System** is a desktop-based banking application developed using **Advanced Java and MySQL**.
This system allows administrators to manage bank users, view transaction history, and perform banking operations efficiently.

The project demonstrates concepts of **Java Swing GUI, JDBC database connectivity, and SQL database management**.

---

## Features

### Admin Features

* Admin Login Authentication
* Add New Bank Users
* View Customer Details
* View Transaction History
* Manage Bank Accounts

### User Features

* User Login
* Deposit Money
* Withdraw Money
* Balance Inquiry
* View Transaction History

---

## Technologies Used

* **Programming Language:** Java
* **GUI Framework:** Java Swing
* **Backend:** JDBC
* **Database:** MySQL
* **Database Tool:** MySQL Workbench
* **IDE:** IntelliJ IDEA / Eclipse / NetBeans
* **Version Control:** Git & GitHub

---

## Project Structure

BankManagementSystem
│
├── src
│   └── bank.management.system
│       ├── AdminLogin.java
│       ├── AdminDashboard.java
│       ├── AdminAddUser.java
│       ├── AdminTransactionHistory.java
│       ├── Con.java
│
├── database_setup.sql
└── README.md

---

## Database Setup

1. Open **MySQL Workbench**
2. Create a new database

```sql
CREATE DATABASE bank_management_system;
USE bank_management_system;
```

3. Run the SQL script provided in:

```
database_setup.sql
```

This will create the required tables.

---

## How to Run the Project

1. Clone the repository

```
git clone https://github.com/yourusername/BankManagementSystem.git
```

2. Open the project in **IntelliJ / Eclipse / NetBeans**

3. Configure MySQL database connection inside:

```
Con.java
```

Example:

```java
Connection c = DriverManager.getConnection(
"jdbc:mysql://localhost:3306/bank_management_system",
"root",
"password");
```

4. Run the **AdminLogin.java** file to start the application.

---

## Learning Outcomes

This project helps understand:

* Java Swing GUI development
* JDBC database connectivity
* SQL database operations
* CRUD operations
* Banking system workflow

---

## Future Improvements

* Add online banking features
* Implement secure password encryption
* Add mobile banking support
* Add OTP verification

---

## Author

Developed by **K Yashvanthakumar**
as part of an **Advanced Java Project**.

---
