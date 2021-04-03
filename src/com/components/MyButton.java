package com.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MyButton extends JButton {

    MyFont myFont = new MyFont();
    MyColor myColor = new MyColor();

    // Button with only text
    public MyButton(String text) {
        this(text, null);
    }

    // Button with text and image
    public MyButton(String text, String filePath) {

        setText(text);// set text
        setFont(myFont.getFontPrimary());// set Font
        setFocusable(false);// set focussable to false
        setBackground(myColor.getPrimaryColor());// set background color
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(15, 20, 15, 20)));// set border
        setCursor(new Cursor(12));// set cursor pointer to hand

        // If filepath is not null
        if (filePath != null) {
            setIcon(new ImageIcon(new MyImage().getImage(filePath).getScaledInstance(30, 30, Image.SCALE_FAST))); // set icon to button
        }

    }

}
