package com.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyPasswordField implements FocusListener {

    JLabel inputLabel;
    JPasswordField inputField;
    Font fontPrimary = new MyFont().getFontPrimary();

    public MyPasswordField(String label) {
        // Label
        inputLabel = new JLabel();
        inputLabel.setText(label);
        inputLabel.setFont(fontPrimary.deriveFont(20f));
        inputLabel.setForeground(Color.white);

        // Password Field
        inputField = new JPasswordField();
        inputField.setPreferredSize(new Dimension(150, 50));
        inputField.setFont(fontPrimary.deriveFont(16f));
        inputField.addFocusListener(this);
    }

    public JLabel getInputLabel() {
        return inputLabel;
    }

    public JPasswordField getInputField() {
        return inputField;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == inputField) {
            inputField.setBorder(new LineBorder(new MyColor().getPrimaryColor(), 3));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == inputField) {
            inputField.setBorder(new JTextField().getBorder());
        }
    }

}
