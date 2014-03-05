/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3timeline.components.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ColorComboBox extends JComboBox {

    public ColorComboBox() {
        int[] values = new int[]{0, 128, 192, 255};
        for (int r = 0; r < values.length; r++) {
            for (int g = 0; g < values.length; g++) {
                for (int b = 0; b < values.length; b++) {
                    Color c = new Color(values[r], values[g], values[b]);
                    addItem(c);
                }
            }
        }
        setRenderer(new ColorComboRenderer1());
    }

    class ColorComboRenderer1 extends JPanel implements ListCellRenderer {

        protected Color m_c = Color.black;

        public ColorComboRenderer1() {
            super();
            setBorder(new CompoundBorder(new MatteBorder(2, 10, 2, 10, Color.white), new LineBorder(Color.black)));
        }

        public Component getListCellRendererComponent(JList list, Object obj, int row, boolean sel, boolean hasFocus) {
            if (obj instanceof Color) {
                m_c = (Color) obj;
            }
            return this;
        }

        public void paint(Graphics g) {
            setBackground(m_c);
            super.paint(g);
        }
    }
    
}
