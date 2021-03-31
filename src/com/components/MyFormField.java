package com.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyFormField implements FocusListener {

    JLabel inputLabel;
    JTextField inputField;
    Font fontPrimary = new MyFont().getFontPrimary();

    public MyFormField(String label) {
        // Label
        inputLabel = new JLabel();
        inputLabel.setText(label);
        inputLabel.setFont(fontPrimary.deriveFont(20f));
        inputLabel.setForeground(Color.white);

//        Text Field
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(150, 50));
        inputField.setFont(fontPrimary.deriveFont(16f));
        inputField.addFocusListener(this);
    }

    public JLabel getInputLabel() {
        return inputLabel;
    }

    public JTextField getInputField() {
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
