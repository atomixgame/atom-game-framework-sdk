/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ImageUtil {

    public static Image loadImage(Class clazz, String path) {
        return null;
    }

    public static Image loadImage(Class clazz, String path, int width, int height) {
        Image image = null;

        try {
            BufferedImage tempimage = ImageIO.read(clazz.getResourceAsStream(path));
            image = tempimage.getScaledInstance(width, height, Image.SCALE_FAST);
        } catch (IOException ex1) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex1);
        }

        return image;
    }
}
