package com.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MyCheckBox extends JCheckBox {
    MyFont myFont = new MyFont();
    MyColor myColor = new MyColor();

    public MyCheckBox(String text, Boolean filterIncluded) {
        setBackground(myColor.getBackgroundColor());
        setText(text);
        setFocusable(false);
        setFont(myFont.getFontPrimary().deriveFont(18f));
        setForeground(myColor.getTextColor());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setSelected(filterIncluded);
        setCursor(new Cursor(12));
    }
    
}
