package com.components;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MyFont {

    String filePath = "/resources/fonts/Montserrat/";

    public MyFont() {

    }

    public Font getFontPrimary() {
        return getFont(filePath + "Regular.ttf");
    }

    public Font getFontMedium() {
        return getFont(filePath + "Medium.ttf");
    }

    public Font getFontBold() {
        return getFont(filePath + "Bold.ttf");
    }

    private Font getFont(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
