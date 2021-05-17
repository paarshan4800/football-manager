package com.football_manager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import com.components.*;
import com.sql.ManagerSQL;
import com.sql.SQL;
import org.springframework
        .security
        .crypto
        .bcrypt
        .BCrypt;

import static com.sql.ManagerSQL.managerLogin;

public class LoginFrame extends JFrame implements ActionListener {

    MyFont myFont = new MyFont();
    MyColor myColor = new MyColor();

    MyFormField usernameField;
    MyPasswordField passwordField;
    MyButton loginButton;
    Font fontPrimary = new MyFont().getFontPrimary();

    public LoginFrame() {
//        Header
        JLabel header = new JLabel();
        header.setText("LOGIN");
        header.setForeground(myColor.getPrimaryColor());
        header.setFont(myFont.getFontBold().deriveFont(40f));
        header.setHorizontalAlignment(SwingConstants.CENTER);

//        Username Form
        usernameField = new MyFormField("Username");

//        Password Form
        passwordField = new MyPasswordField("Password");


//        Button
        loginButton = new MyButton("Login");
        loginButton.addActionListener(this);


        // Logo Label
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(new MyImage().getLogo().getScaledInstance(400, 200, Image.SCALE_FAST)));
        logoLabel.setFont(myFont.getFontPrimary().deriveFont(40f));
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Left Panel -> for Logo

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(myColor.getBackgroundColor());
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(logoLabel, BorderLayout.CENTER);

        // Right Panel -> for login form

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(myColor.getBackgroundColor());
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
        wrapper.setBackground(myColor.getBackgroundColor());

        //        Scrollable Wrapper
        JScrollPane scrollable = new JScrollPane(wrapper);


//        Frame
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.add(scrollable);
        this.setIconImage(new MyImage().getLogo());
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getInputField().getText();
            String password = String.valueOf(passwordField.getInputField().getPassword());

            if (managerLogin(username, password)) {
                System.out.println(username + " authenticated");
                this.dispose();
                new DashboardFrame(ManagerSQL.getManagerGivenUsername(username));
            } else {
                // User doesn't exists
                JOptionPane.showMessageDialog(this, "Invalid Username/Password", "Authentication Failed", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

}
