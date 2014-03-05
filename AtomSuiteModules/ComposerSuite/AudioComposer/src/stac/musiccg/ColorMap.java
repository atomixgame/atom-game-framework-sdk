/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stac.musiccg;

import java.awt.Color;
import org.apache.batik.ext.awt.MultipleGradientPaint;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ColorMap {

    MultipleGradientPaint gradient;

    ColorMap() {
    }

    ColorMap(MultipleGradientPaint gradient) {
        this.gradient = gradient;
    }
    /*
    Color interpolate(Color c1,Color c2){
    
    }
     * 
     */

    Color getColorByValue(float value) {
        Color c;
        try {
            /*
            int r = (int) Math.round(255 * value);
            int g = (int) Math.round(255 * value * 0.8f);
            int b = (int) Math.round(255 * value * 0.5f);
             * 
             */
            float roll = -0.1f;
            float rollValue = (value - roll > 0) ? value - roll : 1 - (roll - value);
            c = Color.getHSBColor(rollValue, 1, value * value * value);
            /*
            int r = (int) Math.round(255 * value * value);
            int g = 0;
            int b = (int) Math.round(255 * (1 - value));
            c = new Color(r, g, b);
             */

        } catch (IllegalArgumentException e) {
            c = new Color(255, 255, 255);
            System.out.println(value);
        }
        return c;
    }
}
