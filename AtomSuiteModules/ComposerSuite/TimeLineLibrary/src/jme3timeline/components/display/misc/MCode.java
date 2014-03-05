package jme3timeline.components.display.misc;

/**
 *
 * @author cuong.nguyenmanh2
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class MCode {

    public String outp, outp2;
    public int xpos;
    public int delay;
    public int ypos;
    Color cur = new Color(0, 255, 0);
    public int dly = 0;
    public boolean make = true;
    private int chnu;

    ///constructors
    public void init() {
        //
    }

    public MCode(String inp, int xp, int yp, int del) {
        ////initialize
        outp = inp;
        xpos = xp;
        ypos = yp;
        delay = ((int) (Math.random() * 200) + del);

        chchar();


    }

    public void paint(Graphics g) {

        //super.paint(g);

        update(g);

    }

    public void update(Graphics g) {
        if (make == true) {

            g.setColor(Color.white);
            g.drawString(outp, xpos, ypos);

            if (ypos > 10) {
                g.setColor(Color.green);
                g.drawString(outp2, xpos, ypos - 10);
            }

            outp2 = outp;

            chchar();



        } else {
            g.setColor(Color.black);
            g.fillRect(xpos, ypos - 10, 10, 10);
        }




        if (ypos > 500) {
            if (make == true) {
                make = false;
            } else {
                make = true;
            }

            ypos = 0;

            delay = ((int) (Math.random() * Math.random() * 100)) + 20;

        }

        ypos += 10;
    }

    public void chchar() {

        chnu = ((int) (Math.random() * (11)));

        if (chnu == 0) {
            outp = "Ç";
        } else if (chnu == 1) {
            outp = "ÿ";
        } else if (chnu == 2) {
            outp = "¢";
        } else if (chnu == 3) {
            outp = "µ";
        } else if (chnu == 4) {
            outp = "ó";
        } else if (chnu == 5) {
            outp = "¥";
        } else if (chnu == 6) {
            outp = "þ";
        } else if (chnu == 7) {
            outp = "ö";
        } else if (chnu == 8) {
            outp = "ø";
        } else if (chnu == 9) {
            outp = "§";
        } else if (chnu == 10) {
            outp = "£";
        }



    }
}
