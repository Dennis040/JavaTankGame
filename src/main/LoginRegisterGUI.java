package main;

import database.DatabaseConnection;
import gui.MyFrame;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class LoginRegisterGUI extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SECONDARY_COLOR = new Color(135, 206, 250);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    private JTabbedPane tabbedPane;

    public LoginRegisterGUI() {
        setTitle("Đăng nhập / Đăng ký");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.addTab("Đăng nhập", createLoginPanel());
        tabbedPane.addTab("Đăng ký", createRegisterPanel());

        add(tabbedPane);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Đăng nhập", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel userLabel = createLabel("Tên đăng nhập:");
        JTextField userField = createTextField();
        JLabel passLabel = createLabel("Mật khẩu:");
        JPasswordField passField = createPasswordField();
        JButton loginButton = createButton("Đăng nhập");

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (loginUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");

                // Đóng cửa sổ đăng nhập hiện tại
                this.dispose();

                // Mở trang chính
                SwingUtilities.invokeLater(() -> {
                    MyFrame myFrame = new MyFrame();
//                    mainPage.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Đăng ký", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel userLabel = createLabel("Tên đăng nhập:");
        JTextField userField = createTextField();
        JLabel passLabel = createLabel("Mật khẩu:");
        JPasswordField passField = createPasswordField();
        JLabel confirmPassLabel = createLabel("Xác nhận mật khẩu:");
        JPasswordField confirmPassField = createPasswordField();
        JButton registerButton = createButton("Đăng ký");

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(confirmPassField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không khớp!");
                return;
            }

            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                // Chuyển về tab đăng nhập
                tabbedPane.setSelectedIndex(0);

                // Xóa các trường trong form đăng ký
                userField.setText("");
                passField.setText("");
                confirmPassField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Đăng ký thất bại. Vui lòng thử lại.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(confirmPassLabel, gbc);

        gbc.gridx = 1;
        panel.add(confirmPassField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(15);
        field.setFont(FIELD_FONT);
        //field.setPreferredSize(new Dimension(200, 30));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(15);
        field.setFont(FIELD_FONT);
        //field.setPreferredSize(new Dimension(200, 30));
        return field;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    public static boolean registerUser(String username, String password) {
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
        DatabaseConnection sql = new DatabaseConnection();
        Connection connection = sql.getConnection();
        if(connection != null) {
            try{
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, password); // Lưu ý: Trong thực tế, bạn nên mã hóa mật khẩu

                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    //    public static void loginUser(String username, String password) {
//        String sql = "Select username,password from Users";
//
//        try (Connection conn = DatabaseConnection.getConnection()){
//            Statement smt = conn.createStatement();
//            ResultSet resultSet = smt.executeQuery(sql);
//            while (resultSet.next()) {
//                Integer id = resultSet.getInt(1);
//                String storeName = resultSet.getString(2);
//                int owner_id = resultSet.getInt(3);
//                java.util.Date updated_at = resultSet.getDate(4);
//                byte[] image = resultSet.getBytes(5);
//                tempArr.add(new Stores(image, id, owner_id, storeName, (java.sql.Date) updated_at));
//            }
//            connection.close();
//            pstmt.setString(1, username);
//            pstmt.setString(2, password); // Lưu ý: Trong thực tế, bạn nên mã hóa mật khẩu
//
//            int affectedRows = pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public static boolean loginUser(String username, String password) {
        String query = "SELECT username, password FROM Users WHERE username = ? AND password = ?";
        DatabaseConnection sql = new DatabaseConnection();
        Connection connection = sql.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Lưu ý: Trong thực tế, bạn nên mã hóa mật khẩu

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Trả về true nếu tìm thấy user, false nếu không
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginRegisterGUI().setVisible(true);
        });
    }
}
