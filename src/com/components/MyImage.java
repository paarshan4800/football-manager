package com.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyImage {


    public MyImage() {

    }

    public BufferedImage getLogo() {
        return getImage("/resources/images/logo.png");
    }

    public BufferedImage getImage(String filePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return image;
        }
    }

}
