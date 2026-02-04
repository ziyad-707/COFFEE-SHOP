☕ Coffee Shop Management System (Java Swing + MySQL)
- README Generated with the help of ChatGPT

📋 Overview
This project implements a **desktop management system** for a coffee shop using **Java Swing** and **MySQL**.  
It supports **CRUD operations** for customers, beverages, and orders, along with loyalty point management.  
The system demonstrates real-world **database integration**, **GUI design**, and **business logic implementation**.

The system supports multiple operations:
- Insert customers, beverages, menu items, and orders
- Update existing records
- Search for customers, beverages, and orders
- Delete records safely with relational database constraints
- Automatically update loyalty points based on order changes

📚 Features
- Interactive GUI using **Java Swing** (`JFrame`, `JTable`, `JComboBox`)
- CRUD operations for all entities
- Automatic loyalty points calculation
- Data validation and user feedback dialogs
- Search with dynamic table display for detailed results
- Handles foreign key relationships (e.g., beverages linked to types, orders linked to customers)

🧵 Core Components
- `MainMenu`: Main interface for selecting operations
- `Insert`: Add new customers, beverages, menu items, and orders
- `update`: Update existing records
- `Search`: Search for customers, beverages, and orders with filters
- `delete`: Delete records safely from the database
- Loyalty program: Automatically updates points on new orders or price changes

📁 File Structure
- `MainMenu.java`       // Launches the main menu
- `Insert.java`         // Handles all insert operations
- `update.java`         // Handles all update operations
- `Search.java`         // Handles all search operations
- `delete.java`         // Handles all delete operations

📄 Database
**MySQL database `coffee_shop`** with tables:
- `customer`            // Stores customer details
- `loyaltycard`         // Stores loyalty points for customers
- `beveragestypes`      // Beverage categories
- `beveragesmenu`       // Beverages and prices
- `beveragesorder`      // Customer orders
- `beverageorderitems`  // Items in each order

⚙️ How It Works
1. Launch `MainMenu.java`
2. Choose an operation: Insert / Update / Search / Delete
3. Use interactive dialogs to input or view data
4. CRUD operations are reflected directly in the MySQL database
5. Loyalty points are automatically updated based on order totals

🛠 How to Run
1. Ensure MySQL is running and `coffee_shop` database exists with proper tables
2. Update database username/password in the code if needed
3. Compile all Java files:
   ```bash
   javac *.java
4. Run the program:
   ```bash
   java MainMenu
5. Follow the GUI menu to manage customers, beverages, and orders

📊 Output
- Success/failure dialogs for all operations
- Dynamic tables displaying search results
- Automatic loyalty point calculation and updates
- Consistent updates across all related tables

📌 Notes
- Uses JDBC PreparedStatements for secure database access
- Handles relational integrity and foreign keys
- GUI is built entirely with Java Swing
- Orders, beverages, and customers are linked for consistent data management

🎯 Learning Objectives
- Understand Java Swing GUI development
- Integrate Java applications with MySQL databases
- Implement full CRUD functionality
- Apply business logic for real-world scenarios (loyalty points, orders)
- Visualize relational data dynamically in tables
