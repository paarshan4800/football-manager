package com.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MyImage {

    private final static String basePath = "/resources/images";

    public MyImage() {

    }

    public BufferedImage getLogo() {
        return getImage("/logo.png");
    }

    public BufferedImage getImage(String filePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(basePath + filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return image;
        }
    }
    public BufferedImage getImageFromURL(String _url) {
        BufferedImage image = null;

        try {
            URL url = new URL(_url);
            try {
                image = ImageIO.read(url);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } finally {
            return image;
        }
    }


}
