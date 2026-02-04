
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class update extends JFrame {

    public void update() {
        setTitle("Update Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        JButton customerButton = new JButton("Customer");
        JButton beveragesButton = new JButton("Beverage Types");
        JButton beveragesMenuButton = new JButton("Beverages Menu");
        add(customerButton);
        add(beveragesButton);
        add(beveragesMenuButton);
        customerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> updateCustomer());
        });
        beveragesButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> updateBeverageTypes());
        });
        beveragesMenuButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> updateBeveragesMenu());
        });
        setVisible(true);

    }

    private void updateBeverageTypes() {
        JFrame beveragesTypeFrame = new JFrame("Update Beverage Type in the Menu");
        beveragesTypeFrame.setSize(300, 200);
        beveragesTypeFrame.setLayout(new GridLayout(3, 2));
        beveragesTypeFrame.setLocationRelativeTo(null);
        beveragesTypeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextField typeField = new JTextField();
        JComboBox<String> jc = new JComboBox<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageTypeID, BeverageTypeName FROM beveragestypes";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(beveragesTypeFrame, "There is no data to update!");
                return;
            }
            do{
                int id = rs.getInt("BeverageTypeID");
                String name = rs.getString("BeverageTypeName");
                jc.addItem(id + " : " + name);
            } while (rs.next());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(beveragesTypeFrame, e.getMessage());
        }

        beveragesTypeFrame.add(new JLabel("Type: "));
        beveragesTypeFrame.add(jc);
        beveragesTypeFrame.add(new JLabel("New Type Name:"));
        beveragesTypeFrame.add(typeField);

        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        beveragesTypeFrame.add(updateButton);
        beveragesTypeFrame.add(cancelButton);

        updateButton.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String selected = (String) jc.getSelectedItem();
                if(selected == null){
                    JOptionPane.showMessageDialog(beveragesTypeFrame, "No beverage type selected.");
                    return;
                }
                int id = Integer.parseInt(selected.split(" : ")[0]);
                String newName = typeField.getText();
                String sql = "UPDATE beveragestypes SET BeverageTypeName = ? WHERE BeverageTypeID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, newName);
                stmt.setInt(2, id);

                int row = stmt.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(beveragesTypeFrame, "Beverage Type updated successfully!");
                    jc.removeItem(selected);
                    jc.addItem(id+" : "+newName);
                    typeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(beveragesTypeFrame, "Update Failed!");
                }
            } catch (SQLException p) {
                JOptionPane.showMessageDialog(beveragesTypeFrame, "Database error: " + p.getMessage());
            }
        });
        cancelButton.addActionListener(e -> beveragesTypeFrame.dispose());
        beveragesTypeFrame.setVisible(true);
    }

    private void updateBeveragesMenu() {
        JFrame beveragesMenuFrame = new JFrame("Update the Customer");
        beveragesMenuFrame.setSize(300, 200);
        beveragesMenuFrame.setLayout(new GridLayout(4, 2));
        beveragesMenuFrame.setLocationRelativeTo(null);
        beveragesMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JComboBox<String> jc = new JComboBox<>();
        String savedName;
        double savedPrice;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageID, BeverageName, BeveragesPrice FROM beveragesmenu";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(beveragesMenuFrame, "There is no data to update!");
                return;
            }
            do{
                int id = rs.getInt("BeverageID");
                String name = rs.getString("BeverageName");
                double price = rs.getDouble("BeveragesPrice");
                jc.addItem(id + " : " + name+" : "+price+"SAR");
            }while (rs.next()) ;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(beveragesMenuFrame, e.getMessage());
        }

        beveragesMenuFrame.add(new JLabel("Beverage: "));
        beveragesMenuFrame.add(jc);
        beveragesMenuFrame.add(new JLabel("New Beverage Name:"));
        beveragesMenuFrame.add(nameField);
        beveragesMenuFrame.add(new JLabel("New Beverage Price:"));
        beveragesMenuFrame.add(priceField);

        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        beveragesMenuFrame.add(updateButton);
        beveragesMenuFrame.add(cancelButton);

        
        updateButton.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String selected = (String) jc.getSelectedItem();
                int id = Integer.parseInt(selected.split(" : ")[0]);
                String newName = nameField.getText();
                double newPrice = Double.parseDouble(priceField.getText());
                String sql5 = "UPDATE loyaltycard lc INNER JOIN beveragesorder bo ON lc.Customer_CustomerID = bo.Customer_CustomerID SET lc.LoyaltyPoints = (lc.LoyaltyPoints-(bo.OrderPrice*0.25))"; // Remove LoyaltyPoints based on old price
                String sql = "UPDATE beveragesmenu SET BeverageName = ?, BeveragesPrice = ? WHERE BeverageID = ?"; // Update Name
                String sql2 = "UPDATE beverageorderitems SET Subtotal = (Quantity*?) WHERE BeveragesMenu_BeverageID = ?"; // Update Subtotal
                String sql3 = "UPDATE beveragesorder bo INNER JOIN (SELECT BeveragesOrder_OrderID, SUM(Subtotal) AS totalprice FROM beverageorderitems GROUP BY BeveragesOrder_OrderID) AS temp ON bo.BeverageOrderID = temp.BeveragesOrder_OrderID SET bo.OrderPrice = temp.totalprice"; // Update OrderPrice
                String sql4 = "UPDATE loyaltycard lc INNER JOIN beveragesorder bo ON lc.Customer_CustomerID = bo.Customer_CustomerID SET lc.LoyaltyPoints = (lc.LoyaltyPoints+(bo.OrderPrice*0.25))"; // Add LoyaltyPints based on new price
                PreparedStatement stmt5 = conn.prepareStatement(sql5);
                PreparedStatement stmt = conn.prepareStatement(sql);
                PreparedStatement stmt2 = conn.prepareStatement(sql2);
                PreparedStatement stmt3 = conn.prepareStatement(sql3);
                PreparedStatement stmt4 = conn.prepareStatement(sql4);
                stmt.setString(1, newName);
                stmt.setDouble(2, newPrice);
                stmt.setInt(3, id);
                stmt2.setDouble(1,newPrice);
                stmt2.setInt(2,id);
                int row5 = stmt5.executeUpdate();
                int row = stmt.executeUpdate();
                int row2 = stmt2.executeUpdate();
                int row3 = stmt3.executeUpdate();
                int row4 = stmt4.executeUpdate();
                if (row > 0 && row2 > 0 && row3 > 0 && row4 > 0 && row5 >0) {
                    JOptionPane.showMessageDialog(beveragesMenuFrame, "Beverage updated successfully!");
                    jc.removeItem(selected);
                    jc.addItem(id+" : "+newName+" : "+newPrice+"SAR");
                    nameField.setText("");
                    priceField.setText("");
                } else {
                    JOptionPane.showMessageDialog(beveragesMenuFrame, "Update Failed!");
                }
            } catch (SQLException p) {
                JOptionPane.showMessageDialog(beveragesMenuFrame, "Database error: " + p.getMessage());
            }
        });
        cancelButton.addActionListener(e -> beveragesMenuFrame.dispose());
        beveragesMenuFrame.setVisible(true);
    }

    private void updateCustomer() {
        JFrame customerFrame = new JFrame("Update Customers");
        customerFrame.setSize(300, 200);
        customerFrame.setLayout(new GridLayout(4, 2));
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JComboBox<String> jc = new JComboBox<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT CustomerID, CustomerName, CustomerPhoneNumber FROM customer";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(customerFrame, "There is no data to update!");
                return;
            }
            do{
                int id = rs.getInt("CustomerID");
                String name = rs.getString("CustomerName");
                jc.addItem(id + " : " + name);
            } while (rs.next());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(customerFrame, e.getMessage());
        }

        customerFrame.add(new JLabel("Customer: "));
        customerFrame.add(jc);
        customerFrame.add(new JLabel("New Customer Name:"));
        customerFrame.add(nameField);
        customerFrame.add(new JLabel("New Customer Phone Number:"));
        customerFrame.add(phoneField);

        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");
        customerFrame.add(updateButton);
        customerFrame.add(cancelButton);

        updateButton.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String selected = (String) jc.getSelectedItem();
                if(selected == null){
                    JOptionPane.showMessageDialog(customerFrame, "No customer selected.");
                    return;
                }
                int id = Integer.parseInt(selected.split(" : ")[0]);
                String newName = nameField.getText();
                String newPhone = phoneField.getText();
                String sql = "UPDATE customer SET CustomerName = ?, CustomerPhoneNumber = ? WHERE CustomerID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, newName);
                stmt.setString(2, newPhone);
                stmt.setInt(3, id);
                int row = stmt.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(customerFrame, "Customer updated successfully!");
                    jc.removeItem(selected);
                    jc.addItem(id+" : "+newName);
                    nameField.setText("");
                    phoneField.setText("");
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Update Failed!");
                }
            } catch (SQLException p) {
                JOptionPane.showMessageDialog(customerFrame, "Database error: " + p.getMessage());
            }
        });
        cancelButton.addActionListener(e -> customerFrame.dispose());
        customerFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new update().update());

    }
}
