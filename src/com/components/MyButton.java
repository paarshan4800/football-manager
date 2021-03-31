package com.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MyButton extends JButton {
    public MyButton(String text) {
        this(text,null);
    }

    public MyButton(String text, String filePath) {

        setText(text);

        Font font = new MyFont().getFontPrimary();
        setFont(font);

        setFocusable(false);
        setBackground(new MyColor().getPrimaryColor());
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(15, 20, 15, 20)));
        setCursor(new Cursor(12));

        if(filePath != null) {
            setIcon(new ImageIcon(new MyImage().getImage(filePath).getScaledInstance(40, 40, Image.SCALE_FAST)));
        }

    }

}
