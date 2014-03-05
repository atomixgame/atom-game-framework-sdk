/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3timeline.components.widget;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FontLabel extends JLabel {

    public FontLabel(String text) {
        super(text, JLabel.CENTER);
        setBackground(Color.white);
        setForeground(Color.black);
        setOpaque(true);
        setBorder(new LineBorder(Color.black));
        setPreferredSize(new Dimension(120, 40));
    }
    
}
