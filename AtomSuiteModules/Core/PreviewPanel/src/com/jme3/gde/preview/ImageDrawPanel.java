/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.jme3.gde.preview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author hungcuong
 */
public class ImageDrawPanel extends JPanel {

    private BufferedImage bi;
    int gridX = 10, gridY = 10;
    Color gridGrayColor = Color.LIGHT_GRAY;
    int thumbnailWidth = 256, thumbnailHeight = 256;
    float[] scales = {1f, 1f, 1f, 0.5f};
    float[] offsets = new float[4];
    RescaleOp rop;

    public ImageDrawPanel() {
        setOpacity(1f);
    }

    public void setOpacity(float opacity) {
        scales[3] = opacity;
        rop = new RescaleOp(scales, offsets, null);
    }

    @Override
    public Dimension getPreferredSize() {
        //return new Dimension(bi.getWidth(null), bi.getHeight(null));
        return new Dimension(thumbnailWidth, thumbnailHeight);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (bi != null) {
            paintImage(g2d);
        } else {
            paintErrorLabel(g2d);
        }
    }

    void paintErrorLabel(Graphics2D g2d) {
        int w = getWidth();
        int h = getHeight();
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Dialog", Font.BOLD, 14));
        g2d.drawString("Can not load the file!", w / 2, h / 2);

    }

    void paintImage(Graphics2D g2d) {
        paintGrid(g2d);
        g2d.drawImage(bi, rop, 0, 0);
    }

    void paintGrid(Graphics2D g2d) {
        int w = getWidth();
        int h = getHeight();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, w, h);
        g2d.setColor(gridGrayColor);
        boolean xd = true;
        boolean yd = true;
        for (int xc = 0; xc < w; xc = xc + gridX) {
            yd = xd;
            xd = !xd;
            for (int yc = 0; yc < h; yc = yc + gridY) {
                if (yd) {
                    g2d.fillRect(xc, yc, gridX, gridY);
                }
                yd = !yd;
            }
        }

    }

    public void setImage(BufferedImage bi) {
        this.bi = bi;
        repaint();
    }
}
