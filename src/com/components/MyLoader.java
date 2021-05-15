package com.components;

import javax.swing.*;

public class MyLoader extends JLabel {
    MyFont myFont = new MyFont();
    MyColor myColor = new MyColor();

    public MyLoader() {
        setText("Loading...");
        setFont(myFont.getFontPrimary().deriveFont(25f));
        setForeground(myColor.getTextColor());
    }

}
