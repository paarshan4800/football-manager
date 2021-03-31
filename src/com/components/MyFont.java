package com.components;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MyFont {

    public MyFont() {

    }

    public Font getFontPrimary() {
        try {
            InputStream is = getClass().getResourceAsStream("/resources/fonts/Ubuntu-Regular.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
