import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Search extends JFrame {
    public void search(){
        setTitle("Search Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        JButton customerButton = new JButton("Customer");
        JButton beveragesButton = new JButton("Beverages");
        JButton beverageOrderButton = new JButton("Order");
        add(customerButton);
        add(beveragesButton);
        add(beverageOrderButton);

        customerButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> searchCustomer());
        });
        beveragesButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> searchBeverages());
        });
        beverageOrderButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> searchOrder());
        });
        setVisible(true);
    }

    public void searchCustomer(){
        JFrame customerFrame=new JFrame("Search for Customer");
        customerFrame.setSize(600, 400);
        customerFrame.setLayout(new BorderLayout(10, 10));
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10)); 
        JTextField nameField =new JTextField();
        inputPanel.add(new JLabel("Customer Name:"));
        inputPanel.add(nameField);
        JButton detailsButton = new JButton("Get Customer Details");
        JButton ordersButton = new JButton("Get Customer Orders");
        JButton cancelButton = new JButton("Cancel");
        inputPanel.add(detailsButton);
        inputPanel.add(ordersButton);
        inputPanel.add(cancelButton);
        customerFrame.add(inputPanel, BorderLayout.NORTH);
        
        DefaultTableModel dtm = new DefaultTableModel();
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        customerFrame.add(scrollPane, BorderLayout.CENTER);

        cancelButton.addActionListener(e -> customerFrame.dispose());

        detailsButton.addActionListener(e -> {
        try {
            dtm.setRowCount(0);
            dtm.setColumnCount(0);
            String name = nameField.getText();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT CustomerID,CustomerName,CustomerPhoneNumber,CardNumber,LoyaltyPoints,CardExpiryDate FROM customer c INNER JOIN loyaltycard on c.CustomerID=Customer_CustomerID WHERE CustomerName LIKE ?";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, "%"+name+"%");
           ResultSet rs=stmt.executeQuery();
          
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                dtm.addColumn(rsmd.getColumnName(i));
            }
           
           while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(customerFrame, ee.getMessage());
        } 
        });
        ordersButton.addActionListener(e -> {
            try {
            dtm.setRowCount(0);
            dtm.setColumnCount(0);
            String name = nameField.getText();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT bo.* FROM customer c INNER JOIN beveragesorder bo on c.CustomerID=bo.Customer_CustomerID WHERE CustomerName LIKE ?";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, "%"+name+"%");
           ResultSet rs=stmt.executeQuery();
          
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                dtm.addColumn(rsmd.getColumnName(i));
            }
           
           while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(customerFrame, ee.getMessage());
        } 
        });

        customerFrame.setVisible(true);
    }

    public void searchBeverages(){
        JFrame beveragesMenuFrame=new JFrame("Search for Beverages");
        beveragesMenuFrame.setSize(600, 400);
        beveragesMenuFrame.setLayout(new BorderLayout(10, 10));
        beveragesMenuFrame.setLocationRelativeTo(null);
        beveragesMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10)); 
        JTextField nameField =new JTextField();
        inputPanel.add(new JLabel("Beverage Name/Beverage Type:"));
        inputPanel.add(nameField);
        JButton detailsButton = new JButton("Get Beverage Details and Total Sales");
        JButton beveragesButton = new JButton("Get Beverages from the Type Name");
        JButton cancelButton = new JButton("Cancel");
        inputPanel.add(detailsButton);
        inputPanel.add(beveragesButton);
        inputPanel.add(cancelButton);
        beveragesMenuFrame.add(inputPanel, BorderLayout.NORTH);
        
        DefaultTableModel dtm = new DefaultTableModel();
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        beveragesMenuFrame.add(scrollPane, BorderLayout.CENTER);

        cancelButton.addActionListener(e -> beveragesMenuFrame.dispose());

        detailsButton.addActionListener(e -> {
        try {
            dtm.setRowCount(0);
            dtm.setColumnCount(0);
            String name = nameField.getText();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageID,BeverageName,BeveragesPrice,BeverageTypeID,BeverageTypeName, SUM(Subtotal) AS Total_Sales FROM beveragesmenu INNER JOIN beveragestypes ON InsertBeverages_BeverageTypeID=BeverageTypeID INNER JOIN beverageorderitems ON BeveragesMenu_BeverageID = BeverageID WHERE BeverageName LIKE ? ";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, "%"+name+"%");
           ResultSet rs=stmt.executeQuery();
          
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                dtm.addColumn(rsmd.getColumnName(i));
            }
           
           while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(beveragesMenuFrame, ee.getMessage());
        } 
        });
        beveragesButton.addActionListener(e -> {
            try {
            dtm.setRowCount(0);
            dtm.setColumnCount(0);
            String name = nameField.getText();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageID,BeverageName,BeveragesPrice FROM beveragesmenu INNER JOIN beveragestypes ON InsertBeverages_BeverageTypeID=BeverageTypeID WHERE BeverageTypeName LIKE ?";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, "%"+name+"%");
           ResultSet rs=stmt.executeQuery();
          
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                dtm.addColumn(rsmd.getColumnName(i));
            }
           
           while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(beveragesMenuFrame, ee.getMessage());
        } 
        });

        beveragesMenuFrame.setVisible(true);
    }
    
    public void searchOrder(){
        JFrame orderFrame=new JFrame("Search for Order");
        orderFrame.setSize(600, 400);
        orderFrame.setLayout(new BorderLayout(10, 10));
        orderFrame.setLocationRelativeTo(null);
        orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10)); 
        JTextField nameField =new JTextField();
        inputPanel.add(new JLabel("Order ID:"));
        inputPanel.add(nameField);
     //   JDateChooser selectedDate = new JDateChooser();
        JButton detailsButton = new JButton("Get Order Details");
        JButton dateButton = new JButton("Get All Orders in Specified Date");
        JButton cancelButton = new JButton("Cancel");
        inputPanel.add(detailsButton);
        inputPanel.add(dateButton);
        inputPanel.add(cancelButton);
        orderFrame.add(inputPanel, BorderLayout.NORTH);
        
        DefaultTableModel dtm = new DefaultTableModel();
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        orderFrame.add(scrollPane, BorderLayout.CENTER);

        cancelButton.addActionListener(e -> orderFrame.dispose());

        detailsButton.addActionListener(e -> {
        try {
            dtm.setRowCount(0);
            dtm.setColumnCount(0);
            String name = nameField.getText();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageID,BeverageName,BeveragesPrice,BeverageTypeID,BeverageTypeName, SUM(Subtotal) AS Total_Sales FROM beveragesmenu INNER JOIN beveragestypes ON InsertBeverages_BeverageTypeID=BeverageTypeID INNER JOIN beverageorderitems ON BeveragesMenu_BeverageID = BeverageID WHERE BeverageName LIKE ? ";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, "%"+name+"%");
           ResultSet rs=stmt.executeQuery();
          
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                dtm.addColumn(rsmd.getColumnName(i));
            }
           
           while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(orderFrame, ee.getMessage());
        } 
        });
        dateButton.addActionListener(e -> {
            try {
            dtm.setRowCount(0);
            dtm.setColumnCount(0);
            String name = nameField.getText();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coffee_shop", "root", "");
            String sql = "SELECT BeverageID,BeverageName,BeveragesPrice FROM beveragesmenu INNER JOIN beveragestypes ON InsertBeverages_BeverageTypeID=BeverageTypeID WHERE BeverageTypeName LIKE ?";
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setString(1, "%"+name+"%");
           ResultSet rs=stmt.executeQuery();
          
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                dtm.addColumn(rsmd.getColumnName(i));
            }
           
           while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            JOptionPane.showMessageDialog(orderFrame, ee.getMessage());
        } 
        });

        orderFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Search().search());
    }
}