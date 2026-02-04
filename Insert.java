
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;

public class Insert extends JFrame {

    public void insert() {

        setTitle("Insert Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);
        JButton customerButton = new JButton("Customer");
        JButton beveragesButton = new JButton("Beverage Type");
        JButton beveragesMenuButton = new JButton("Beverages Menu");
        JButton beverageOrderButoon = new JButton("Order");
        add(customerButton);
        add(beveragesButton);
        add(beveragesMenuButton);
        add(beverageOrderButoon);

        customerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> insertCustomer());
        });
        beveragesButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> insertBeverages());
        });
        beveragesMenuButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> insertBeveragesMenu());
        });

        beverageOrderButoon.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> insertOrder());
        });
        setVisible(true);

    }

    private void insertBeverages() {
        JFrame beveragesFrame = new JFrame("Insert a New Beverage Type in the Menu");
        beveragesFrame.setSize(300, 200);
        beveragesFrame.setLayout(new GridLayout(3, 2));
        beveragesFrame.setLocationRelativeTo(null);
        beveragesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField idField = new JTextField();
        JTextField typeField = new JTextField();

        beveragesFrame.add(new JLabel("ID:"));
        beveragesFrame.add(idField);
        beveragesFrame.add(new JLabel("Type:"));
        beveragesFrame.add(typeField);

        JButton insertButton = new JButton("Insert");
        JButton cancelButton = new JButton("Cancel");

        beveragesFrame.add(insertButton);
        beveragesFrame.add(cancelButton);

        insertButton.addActionListener(e -> {

            try {

                String id = idField.getText();
                String type = typeField.getText();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");

                String sql = "INSERT INTO beveragestypes (BeverageTypeID,BeverageTypeName) VALUES (?,?) ";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);
                stmt.setString(2, type);

                int row = stmt.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(beveragesFrame, "Beverage inserted successfully!");
                    idField.setText("");
                    typeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(beveragesFrame, "Insert Failed!");
                }

            } catch (SQLException p) {
                JOptionPane.showMessageDialog(beveragesFrame, "Database error: " + p.getMessage());
            }

        });

        cancelButton.addActionListener(e -> beveragesFrame.dispose());

        beveragesFrame.setVisible(true);

    }

    private void insertBeveragesMenu() {
        JFrame beveragesMenuFrame = new JFrame("Insert a new Beverage Menu Item");
        beveragesMenuFrame.setSize(300, 200);
        beveragesMenuFrame.setLayout(new GridLayout(5, 2));
        beveragesMenuFrame.setLocationRelativeTo(null);
        beveragesMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();

        beveragesMenuFrame.add(new JLabel("ID:"));
        beveragesMenuFrame.add(idField);
        beveragesMenuFrame.add(new JLabel("Name:"));
        beveragesMenuFrame.add(nameField);
        JComboBox jc = new JComboBox();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT * FROM beveragestypes";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(beveragesMenuFrame,
                        "Cannot insert, because there is no data in the fk beveragestypesID");
                return;
            }
            do {
                int typeID = rs.getInt("BeverageTypeID");
                String typeName = rs.getString("BeverageTypeName");
                jc.addItem(typeID + " : " + typeName);
            } while (rs.next());
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(beveragesMenuFrame, se.getMessage());
        }
        beveragesMenuFrame.add(new JLabel("Price:"));
        beveragesMenuFrame.add(priceField);
        beveragesMenuFrame.add(new JLabel("Type:"));
        beveragesMenuFrame.add(jc);
        JButton insertButton = new JButton("Insert");
        JButton cancelButton = new JButton("Cancel");
        beveragesMenuFrame.add(insertButton);
        beveragesMenuFrame.add(cancelButton);
        insertButton.addActionListener(e -> {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(beveragesMenuFrame, "You must enter an ID!");
                return;
            }
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");

                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String price = priceField.getText();
                int fktypeid = (int) jc.getSelectedItem();

                String sql = "INSERT INTO beveragesmenu (BeverageID,BeverageName,BeveragesPrice,InsertBeverages_BeverageTypeID) VALUES (?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.setString(2, name);
                stmt.setString(3, price);
                stmt.setInt(4, fktypeid);

                int row = stmt.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(beveragesMenuFrame, "Beverage inserted successfully!");
                    idField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                } else {
                    JOptionPane.showMessageDialog(beveragesMenuFrame, "Insert Failed!");
                }

            } catch (SQLException p) {
                JOptionPane.showMessageDialog(beveragesMenuFrame, p.getMessage());
            }
        });

        cancelButton.addActionListener(e -> beveragesMenuFrame.dispose());

        beveragesMenuFrame.setVisible(true);
    }

    private void insertOrder() {
        JFrame orderFrame = new JFrame("Insert New Order");
        orderFrame.setSize(300, 200);
        orderFrame.setLayout(new GridLayout(5, 2, 10, 10));
        orderFrame.setLocationRelativeTo(null);
        orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField idField = new JTextField();
        JTextField quantityField = new JTextField();

        orderFrame.add(new JLabel("Order ID:"));
        orderFrame.add(idField);

        JComboBox jc = new JComboBox();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT c.CustomerID,c.CustomerName FROM customer c";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = (int) rs.getInt("CustomerID");
                String name = (String) rs.getString("CustomerName");
                String Customer = id + " : " + name;
                jc.addItem(Customer);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(orderFrame, e.getMessage());
        }
        orderFrame.add(new JLabel("Customer: "));
        orderFrame.add(jc);

        JComboBox jc2 = new JComboBox();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageID,BeverageName,BeveragesPrice FROM  beveragesmenu";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = (int) rs.getInt("BeverageID");
                String name = (String) rs.getString("BeverageName");
                double beverageprice = (double) rs.getDouble("BeveragesPrice");
                String Beverage = id + " : " + name + " : " + beverageprice + "SAR";
                jc2.addItem(Beverage);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(orderFrame, e.getMessage());
        }
        orderFrame.add(new JLabel("Beverage:"));
        orderFrame.add(jc2);
        orderFrame.add(new JLabel("Quantity:"));
        orderFrame.add(quantityField);

        JButton insertButton = new JButton("Insert");
        JButton cancelButton = new JButton("Cancel");
        orderFrame.add(insertButton);
        orderFrame.add(cancelButton);
        cancelButton.addActionListener(e -> orderFrame.dispose());
        insertButton.addActionListener(e -> {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(orderFrame, "You must enter an ID!");
                return;
            }
            int id = Integer.parseInt(idField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            LocalDate date = LocalDate.now();

            String selectJc2ID = (String) jc2.getSelectedItem();
            String extractID = selectJc2ID.split(" : ")[0];
            int beverageMenuID = Integer.parseInt(extractID);

            String selectedJc2Price = (String) jc2.getSelectedItem();
            String extractPrice = selectedJc2Price.split(" : ")[2].replace("SAR", "").trim();
            double price = Double.parseDouble(extractPrice);
            String selectJc3ID = (String) jc.getSelectedItem();
            String extractCustomerID = selectJc3ID.split(" : ")[0];
            int customerID = Integer.parseInt(extractCustomerID);

            double subtotal = quantity * price;
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String checkSql = "SELECT Customer_CustomerID,OrderPrice FROM beveragesorder WHERE BeverageOrderID = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, id);
                ResultSet checkRs = checkStmt.executeQuery();
                double savedPrice = 0;
                if (checkRs.next()) {
                    int existingCustomerID = checkRs.getInt("Customer_CustomerID");
                    if (existingCustomerID != customerID) {
                        JOptionPane.showMessageDialog(orderFrame, "This Order ID is already used by another customer.");
                        return;
                    } else {
                        savedPrice = checkRs.getDouble("OrderPrice");
                        String sql = "UPDATE beveragesorder SET OrderPrice=? WHERE BeverageOrderID=?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setDouble(1, savedPrice + subtotal);
                        stmt.setInt(2, id);
                        int row = stmt.executeUpdate();
                        if (row > 0) {
                            JOptionPane.showMessageDialog(orderFrame, "Order total price updated successfully");
                            quantityField.setText("");
                        } else
                            JOptionPane.showMessageDialog(orderFrame, "Inserted failed!");
                    }
                } else {
                    String sql = "INSERT INTO beveragesorder (BeverageOrderID, BeverageOrderDate, Customer_CustomerID, OrderPrice) VALUES (?, ?, ?,?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    stmt.setString(2, date.toString());
                    stmt.setInt(3, customerID);
                    stmt.setDouble(4, subtotal);
                    int row = stmt.executeUpdate();
                    if (row > 0) {
                        quantityField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(orderFrame, "Inserted failed!");
                    }
                }
            } catch (SQLException s) {
            }

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String sql = "INSERT INTO beverageorderitems (BeveragesMenu_BeverageID,BeveragesOrder_OrderID,Quantity,Subtotal) VALUES (?,?,?,?) ";
                String sql2 = "UPDATE loyaltycard lc INNER JOIN beveragesorder bo ON lc.Customer_CustomerID = bo.Customer_CustomerID SET lc.LoyaltyPoints = (lc.LoyaltyPoints+(bo.OrderPrice*0.25)) WHERE bo.BeverageOrderID=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                stmt.setInt(1, beverageMenuID);
                stmt.setInt(2, id);
                stmt.setDouble(3, quantity);
                stmt.setDouble(4, subtotal);
                stmt2.setInt(1, id);
                int row = stmt.executeUpdate();
                int row2 = stmt2.executeUpdate();
                if (row > 0 && row2 > 0) {
                    JOptionPane.showMessageDialog(orderFrame, "Order inserted successfully");
                    quantityField.setText("");
                } else {
                    JOptionPane.showMessageDialog(orderFrame, "Inserted failed!");
                }

            } catch (SQLException s) {
                JOptionPane.showMessageDialog(orderFrame, s.getMessage());
            }

        });

        orderFrame.setVisible(true);
    }

    private void insertCustomer() {
        JFrame customerFrame = new JFrame("Insert a new Customer");
        customerFrame.setSize(300, 200);
        customerFrame.setLayout(new GridLayout(4, 2, 10, 10));
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();

        customerFrame.add(new JLabel("ID:"));
        customerFrame.add(idField);
        customerFrame.add(new JLabel("Name:"));
        customerFrame.add(nameField);
        customerFrame.add(new JLabel("Phone:"));
        customerFrame.add(phoneField);

        JButton insertButton = new JButton("Insert");
        JButton cancelButton = new JButton("Cancel");
        customerFrame.add(insertButton);
        customerFrame.add(cancelButton);

        insertButton.addActionListener(e -> {
            String ID = idField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String CardNumber = "1000" + ID;
            LocalDate today = LocalDate.now();
            LocalDate expiryDate = today.plusYears(1);

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "")) {
                String sql = "INSERT INTO Customer (CustomerID, CustomerName, CustomerPhoneNumber) VALUES (?, ?, ?)";
                String sql2 = "INSERT INTO LoyaltyCard (CardNumber, LoyaltyPoints, CardExpiryDate, Customer_CustomerID) VALUES (?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                stmt.setString(1, ID);
                stmt.setString(2, name);
                stmt.setString(3, phone);
                stmt2.setString(1, CardNumber);
                stmt2.setString(2, "0");
                stmt2.setString(3, expiryDate.toString());
                stmt2.setString(4, ID);

                int rows = stmt.executeUpdate();
                int rows2 = stmt2.executeUpdate();
                if (rows > 0 && rows2 > 0) {
                    JOptionPane.showMessageDialog(customerFrame, "Customer inserted successfully with Loyalty Card!");
                    idField.setText("");
                    nameField.setText("");
                    phoneField.setText("");
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Insert failed.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(customerFrame, "Database error: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> customerFrame.dispose());

        customerFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Insert().insert());
    }
}
