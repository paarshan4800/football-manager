package com.football_manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;

import org.springframework
        .security
        .crypto
        .bcrypt
        .BCrypt;

public class LoginFrame extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton button;

    public LoginFrame() {
//        Header
        JLabel header = new JLabel();
        header.setText("Login");
        header.setForeground(new Color(0x6930C3));
        header.setFont(new Font("Arial", Font.BOLD, 40));

//        Username Field
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 50));

//        Password Field
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 50));

//        Button
        button = new JButton();
        button.setText("Login");
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.addActionListener(this);
        button.setFocusable(false);
        button.setBackground(new Color(0x6930c3));
        button.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(), new EmptyBorder(10, 10, 10, 10)));
        button.setCursor(new Cursor(12));

//        Frame Icon
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource("/resources/images/frame_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Frame
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setSize(1000, 500);
        this.getContentPane().setBackground(new Color(0x252525));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(header, gbc);

        gbc.gridy = 1;
        this.add(usernameField, gbc);

        gbc.gridy = 2;
        this.add(passwordField, gbc);

        gbc.gridy = 3;
        this.add(button, gbc);

        this.setIconImage(image);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            // Check user existence
            if (checkUsername(username)) {
                // Check password
                if(checkPassword(username,password)) {
                    System.out.println("correct");
                }
                else {
                    System.out.println("wrongpwd");
                }

            } else {
                // User doesn't exists
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
                if (rs.getString("username").equals(username) && verifyPasswordHash(password,rs.getString("password"))) {
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
