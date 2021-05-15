package com.components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MyDataLabel extends JLabel {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    public MyDataLabel(String text, String iconPath, String tooltipText) {
        System.out.println("hi");
        this.setText(text);
        this.setIcon(new ImageIcon(new MyImage().getImage(iconPath).getScaledInstance(30, 30, Image.SCALE_FAST)));
        this.setFont(myFont.getFontPrimary().deriveFont(18f));
        this.setForeground(myColor.getTextColor());
        this.setToolTipText(tooltipText);
        Border margin = new EmptyBorder(10,0,10,0);
        this.setBorder(new CompoundBorder(this.getBorder(),margin));
        this.setIconTextGap(15);

        setHorizontalAlignment(SwingConstants.CENTER);
    }

}
