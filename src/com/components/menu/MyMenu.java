package com.components.menu;

import com.components.MyColor;
import com.components.MyFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MyMenu extends JMenu {

    private final static MyFont myFont = new MyFont();
    private final static MyColor myColor = new MyColor();

    public MyMenu(String text) {
        setText(text);
        setForeground(myColor.getTextColor());
        setCursor(new Cursor(12));// set cursor pointer to hand
        setBackground(myColor.getBackgroundColor());
        setFont(myFont.getFontPrimary().deriveFont(16f));
        setBorder(new EmptyBorder(5,10,5,10));
    }
}
