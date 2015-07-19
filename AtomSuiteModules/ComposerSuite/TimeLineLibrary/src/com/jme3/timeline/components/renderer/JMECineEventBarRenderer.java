package com.jme3.timeline.components.renderer;

import de.jaret.timebars.hierarchy.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import de.jaret.util.date.Interval;
import de.jaret.util.ui.timebars.TimeBarViewerDelegate;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.TimeBarRenderer;

/**
 * @author Peter Kliem
 * @version $Id: SumRenderer.java 869 2009-07-07 19:32:45Z kliem $
 */
public class JMECineEventBarRenderer implements TimeBarRenderer {

    SumRendererComponent _sumComponent;

    public JMECineEventBarRenderer() {
        _sumComponent = new SumRendererComponent();
    }

    @Override
    public JComponent getTimeBarRendererComponent(TimeBarViewer tbv, Interval value, boolean isSelected, boolean overlapping) {
        _sumComponent.setInterval(value);
        _sumComponent.setSelected(isSelected);
        return _sumComponent;
    }

    public class SumRendererComponent extends JComponent {

        Interval _interval;
        boolean _selected;

        public SumRendererComponent() {
            setLayout(null);
            setOpaque(false);
        }

        public void setInterval(Interval interval) {
            _interval = interval;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.JComponent#getToolTipText()
         */
        public String getToolTipText() {
            return _interval.toString();
        }

        public void setSelected(boolean selected) {
            _selected = selected;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int height = getHeight();
            int width = getWidth();

            int y = height / 3 - WIDTH;
            int bheight = WIDTH;
            int yEnd = y + bheight;

            if (_selected) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillRect(0, y, width - 1, bheight);

            int leftx[] = {0, 0, 4};
            int lefty[] = {y + bheight, y + bheight + 7, y + bheight};

            int rightx[] = {width - 1, width - 1, width - 1 - 4};
            int righty[] = {y + bheight, y + bheight + 7, y + bheight};

            g.fillPolygon(leftx, lefty, 3);
            g.fillPolygon(rightx, righty, 3);

            if (_selected) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.fillRect(0, 0, width, height);
            
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, width, height);
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.JComponent#contains(int, int)
         */
        @Override
        public boolean contains(int x, int y) {
            if (y >= getHeight() / 3 && y <= getHeight() / 3 + getHeight() / 3) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * {@inheritDoc} Simple default implementation.
     */
    public Rectangle getPreferredDrawingBounds(Rectangle intervalDrawingArea,
            TimeBarViewerDelegate delegate, Interval interval,
            boolean selected, boolean overlap) {
        return intervalDrawingArea;
    }
}
