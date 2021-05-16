package com.components.menu;

import com.components.MyImage;

import javax.swing.*;
import java.awt.*;

public class MyRefreshLabel extends JLabel {

    MyImage myImage = new MyImage();

    public MyRefreshLabel() {
        setIcon(new ImageIcon(myImage.getImage("/icons/color/icon_refresh.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
