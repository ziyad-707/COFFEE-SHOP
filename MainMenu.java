import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame {

    public void mainmenu() {
        setTitle("Coffee Shop Management System");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel("Select one of the following Operations", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(title);

        JButton insertBtn = new JButton("Insert");
        JButton searchBtn = new JButton("Search");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        add(insertBtn);
        add(searchBtn);
        add(updateBtn);
        add(deleteBtn);

        insertBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Insert().insert());
        });

        searchBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Search().search());
        });

        updateBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new update().update());
        });

        deleteBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new delete().delete());
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().mainmenu());
    }
}
