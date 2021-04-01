package com.components;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MyFont {

    String filePath = "/resources/fonts/";

    public MyFont() {

    }

    public Font getFontPrimary() {
        return getFont(filePath + "Spotify/GothamMedium.ttf");
    }

    public Font getFontMedium() {
        return getFont(filePath + "Spotify/Gotham-Bold.otf");
    }

    public Font getFontBold() {
        return getFont(filePath + "Spotify/Gotham-Black.otf");
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
