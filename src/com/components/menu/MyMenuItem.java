package com.components.menu;

import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyMenuBar;

import javax.swing.*;
import java.awt.*;

public class MyMenuItem extends JMenuItem {

    MyImage myImage = new MyImage();
    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    public MyMenuItem(String text, String filepath, MyMenuBar holder) {
        setText(text);
        setCursor(new Cursor(12));
        setIcon(new ImageIcon(myImage.getImage(filepath).getScaledInstance(20,20, Image.SCALE_SMOOTH)));
        addActionListener(holder);
    }
}
