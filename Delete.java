
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class delete extends JFrame {

    public void delete() {

        setTitle("Choose what to Delete");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);
        JButton customerButton = new JButton("Customer");
        JButton beveragesButton = new JButton("Beverages Types");
        JButton beverageMenuButton = new JButton("Beverages from menu");
        JButton beverageOrderButton = new JButton("Beverage Order");

        add(customerButton);
        add(beveragesButton);
        add(beverageMenuButton);
        add(beverageOrderButton);

        customerButton.addActionListener(e -> deleteCustomer());
        beveragesButton.addActionListener(e -> deleteBeverageType());
        beverageMenuButton.addActionListener(e -> deleteBeverageFromMenu());
        beverageOrderButton.addActionListener(e -> deleteBeverageOrder());

        setVisible(true);

    }

    private void deleteCustomer() {
        JFrame customerFrame = new JFrame("Delete a customer");
        customerFrame.setSize(300, 200);
        customerFrame.setLayout(new GridLayout(2, 2));
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JComboBox jc = new JComboBox();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT c.CustomerID,c.CustomerName FROM customer c";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(customerFrame, "There is no data to delete from!");
                return;
            }
            do {
                int id = (int) rs.getInt("CustomerID");
                String name = (String) rs.getString("CustomerName");
                jc.addItem(id + " : " + name);
            } while (rs.next());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(customerFrame, e.getMessage());
        }
        customerFrame.add(new JLabel("Customer: "));
        customerFrame.add(jc);
        JButton deleteButton = new JButton("Delete");
        customerFrame.add(deleteButton);
        JButton cancelButton = new JButton("Cancel");
        customerFrame.add(cancelButton);

        deleteButton.addActionListener(e -> {

            try {
                String selectJcID = (String) jc.getSelectedItem();
                String extractID = selectJcID.split(" : ")[0];
                int id = Integer.parseInt(extractID);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String sql = "DELETE FROM customer WHERE CustomerID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                int row = stmt.executeUpdate();
                if (row > 0) {
                    jc.removeItem(jc.getSelectedItem());
                    JOptionPane.showMessageDialog(customerFrame, "Customer deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "No customer found with that ID");

                }

            } catch (SQLException s) {
                JOptionPane.showMessageDialog(customerFrame, s.getMessage());
            }
        });

        cancelButton.addActionListener(e -> customerFrame.dispose());
        customerFrame.setVisible(true);

    }

    private void deleteBeverageType() {
        JFrame beverageTypeFrame = new JFrame("Delete Beverage Type");
        beverageTypeFrame.setSize(300, 200);
        beverageTypeFrame.setLayout(new GridLayout(3, 2));
        beverageTypeFrame.setLocationRelativeTo(null);
        beverageTypeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JComboBox jc = new JComboBox();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageTypeID,BeverageTypeName FROM beveragestypes ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(beverageTypeFrame, "There is no data to delete from");
                return;
            }
            do {
                int id = rs.getInt("BeverageTypeID");
                String type = rs.getString("BeverageTypeName");
                jc.addItem(id + " : " + type);
            } while (rs.next());

        } catch (SQLException p) {
            JOptionPane.showMessageDialog(beverageTypeFrame, p.getMessage());
        }
        beverageTypeFrame.add(new JLabel("Beverage Type"));
        beverageTypeFrame.add(jc);
        JButton deleteButton = new JButton("Delete");
        beverageTypeFrame.add(deleteButton);
        JButton cancelButton = new JButton("Cancel");
        beverageTypeFrame.add(cancelButton);

        deleteButton.addActionListener(e -> {
            try {
                String selectJcID = (String) jc.getSelectedItem();
                String extractID = selectJcID.split(" : ")[0];
                int id = Integer.parseInt(extractID);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String sql = "DELETE FROM beveragestypes WHERE BeverageTypeID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);

                int row = stmt.executeUpdate();
                if (row > 0) {
                    jc.removeItem(jc.getSelectedItem());
                    JOptionPane.showMessageDialog(beverageTypeFrame, id + " Deleted successfully");
                } else
                    JOptionPane.showMessageDialog(beverageTypeFrame, "Delete Failed");

            } catch (SQLException p) {
                JOptionPane.showMessageDialog(beverageTypeFrame, p.getMessage());
            }

        });
        cancelButton.addActionListener(e -> beverageTypeFrame.dispose());
        beverageTypeFrame.setVisible(true);

    }

    private void deleteBeverageFromMenu() {
        JFrame beverageMenu = new JFrame();
        beverageMenu.setSize(300, 200);
        beverageMenu.setLayout(new GridLayout(3, 2));
        beverageMenu.setLocationRelativeTo(null);
        beverageMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JComboBox jc = new JComboBox();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageID,BeverageName FROM beveragesmenu ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(beverageMenu, "There is no data to delete from!");
                return;
            }
            do {
                int id = rs.getInt("BeverageID");
                String name = rs.getString("BeverageName");
                jc.addItem(id + " : " + name);
            } while (rs.next());

        } catch (SQLException p) {
            JOptionPane.showMessageDialog(beverageMenu, p.getMessage());
        }
        beverageMenu.add(new JLabel("Beverage:"));
        beverageMenu.add(jc);
        JButton deleteButton = new JButton("Delete");
        beverageMenu.add(deleteButton);
        JButton cancelButton = new JButton("Cancel");
        beverageMenu.add(cancelButton);

        deleteButton.addActionListener(e -> {
            try {
                String selectJcID = (String) jc.getSelectedItem();
                String extractID = selectJcID.split(" : ")[0];
                int id = Integer.parseInt(extractID);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String sql = "DELETE FROM beveragesmenu WHERE BeverageID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                int row = stmt.executeUpdate();
                if (row > 0) {
                    jc.removeItem(jc.getSelectedItem());
                    JOptionPane.showMessageDialog(beverageMenu, "Beverage deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(beverageMenu, "There is no beverage with that ID");
                }
            } catch (SQLException p) {
                JOptionPane.showMessageDialog(beverageMenu, p.getMessage());
            }
        });
        cancelButton.addActionListener(e -> beverageMenu.dispose());
        beverageMenu.setVisible(true);
    }

    private void deleteBeverageOrder() {
        JFrame beverageOrder = new JFrame();
        beverageOrder.setSize(300, 200);
        beverageOrder.setLayout(new GridLayout(2, 2));
        beverageOrder.setLocationRelativeTo(null);
        beverageOrder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JComboBox jc = new JComboBox();
        beverageOrder.add(new JLabel("Order:"));
        beverageOrder.add(jc);
        JButton deleteButton = new JButton("Delete");
        beverageOrder.add(deleteButton);
        JButton cancelButton = new JButton("Cancel");
        beverageOrder.add(cancelButton);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT b.BeverageOrderID,BeverageOrderDate,OrderPrice,c.CustomerName FROM beveragesorder b INNER JOIN customer c ON  b.Customer_CustomerID=c.CustomerID";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(beverageOrder, "There is no data to delete from!");
                return;
            }
            do {
                int id = rs.getInt("BeverageOrderID");
                String name = rs.getString("CustomerName");

                jc.addItem(id + " : " + name);
            } while (rs.next());

        } catch (SQLException p) {
            JOptionPane.showMessageDialog(beverageOrder, p.getMessage());
        }
        deleteButton.addActionListener(e -> {
            try {
                String selectJcID = (String) jc.getSelectedItem();
                String extractID = selectJcID.split(" : ")[0];
                int id = Integer.parseInt(extractID);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
                String sql = "DELETE FROM beveragesorder WHERE BeverageOrderID = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setInt(1, id);
                int row = stmt.executeUpdate();
                if (row > 0) {
                    jc.removeItem(jc.getSelectedItem());
                    JOptionPane.showMessageDialog(beverageOrder, "Beverage order deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(beverageOrder, "There is no beverage order with that ID");

                }

            } catch (SQLException p) {
                JOptionPane.showMessageDialog(beverageOrder, p.getMessage());
            }
        });
        cancelButton.addActionListener(e -> beverageOrder.dispose());
        beverageOrder.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new delete().delete());
    }
}
