package com.football_manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;

import com.components.*;
import org.springframework
        .security
        .crypto
        .bcrypt
        .BCrypt;

public class LoginFrame extends JFrame implements ActionListener {
    MyFormField usernameField;
    MyPasswordField passwordField;
    MyButton loginButton;
    Font fontPrimary = new MyFont().getFontPrimary();

    public LoginFrame() {
//        Header
        JLabel header = new JLabel();
        header.setText("LOGIN");
        header.setForeground(new Color(0x289672));
        header.setFont(fontPrimary.deriveFont(40f));
        header.setHorizontalAlignment(SwingConstants.CENTER);

//        Username Form
        usernameField = new MyFormField("Username");

//        Password Form
        passwordField = new MyPasswordField("Password");


//        Button
        loginButton = new MyButton("Login");
        loginButton.addActionListener(this);

        //        Frame Icon
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource("/resources/images/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Logo Label
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(image.getScaledInstance(400, 200, Image.SCALE_FAST)));
        logoLabel.setFont(fontPrimary.deriveFont(40f));
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Left Panel -> for Logo

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new MyColor().getBackgroundColor());
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(logoLabel, BorderLayout.CENTER);

        // Right Panel -> for login form

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new MyColor().getBackgroundColor());
        rightPanel.setLayout(new GridBagLayout());

        // Grid Bag Layout for Right Panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 50, 50, 50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(header, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        rightPanel.add(usernameField.getInputLabel(), gbc);
        gbc.gridy = 1;
        gbc.gridx = 1;
        rightPanel.add(usernameField.getInputField(), gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        rightPanel.add(passwordField.getInputLabel(), gbc);
        gbc.gridy = 2;
        gbc.gridx = 1;
        rightPanel.add(passwordField.getInputField(), gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 3;
        gbc.gridx = 0;
        rightPanel.add(loginButton, gbc);


//        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridLayout(1, 2));
        wrapper.add(leftPanel);
        wrapper.add(rightPanel);
        wrapper.setBorder(new EmptyBorder(100, 100, 100, 100));
        wrapper.setBackground(new MyColor().getBackgroundColor());

        //        Scrollable Wrapper
        JScrollPane scrollable = new JScrollPane(wrapper);


//        Frame
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.add(scrollable);
        this.setIconImage(image);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getInputField().getText();
            String password = String.valueOf(passwordField.getInputField().getPassword());

            // Check user existence
            if (checkUsername(username)) {
                // Check password
                if (checkPassword(username, password)) {
                    System.out.println(username + " authenticated");
                    this.dispose();
                    new DashboardFrame();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username/Password", "Authentication Failed", JOptionPane.WARNING_MESSAGE);
                    System.out.println("wrongpwd");
                }

            } else {
                // User doesn't exists
                JOptionPane.showMessageDialog(this, "Invalid Username/Password", "Authentication Failed", JOptionPane.WARNING_MESSAGE);
                System.out.println("wronguser");
            }

        }
    }

    public static boolean checkUsername(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select username from managers");

            while (rs.next()) {
                // If user exists
                if (rs.getString("username").equals(username)) {
                    return true;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return false;
    }

    public static boolean checkPassword(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement("select username,password from managers where username=?");
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                // Check correct user and password hash
                if (rs.getString("username").equals(username) && verifyPasswordHash(password, rs.getString("password"))) {
                    return true;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return false;
    }

    public static boolean verifyPasswordHash(
            String password,
            String hashed_password) {
        return BCrypt.checkpw(
                password, hashed_password);
    }
}
