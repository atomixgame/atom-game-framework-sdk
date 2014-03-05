/*
 *  File: LineChartHeaderRenderer.java 
 *  Copyright (c) 2004-2008  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package de.jaret.timebars.linechart.renderer;

import de.jaret.util.ui.timebars.model.TimeBarRow;
import de.jaret.util.ui.timebars.model.TimeBarRowHeader;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.HeaderRenderer;
import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import javax.swing.JComponent;

/**
 * Simple header renderer for the linechart example. Draws the lbels for the value markers.
 * 
 * @author Peter Kliem
 * @version $Id: LineChartHeaderRenderer.java 801 2008-12-27 22:44:54Z kliem $
 */
public class LineChartHeaderRenderer implements HeaderRenderer {

    LineChartHeaderRendererComp comp = new LineChartHeaderRendererComp();
    /** line width when printing. */
    private static final int PRINTING_LINEWIDTH = 3;

    /**
     * Constructor for screen use.
     * 
     */
    public LineChartHeaderRenderer() {
    }

    class LineChartHeaderRendererComp extends JComponent {

        @Override
        protected void paintComponent(Graphics gc) {
            super.paintComponent(gc);
            Color bg = getBackground();
            Graphics2D g2 = (Graphics2D) gc;

            setBackground(Color.WHITE);
            g2.fill(getBounds());

            // draw lines for 10, 50, 90
            Color fg = getForeground();
            gc.setColor(Color.GRAY);
            
            Stroke defaultStroke = g2.getStroke();
            
            float dash1[] = {10.0f};
            BasicStroke dashed =
                    new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);

            g2.setStroke(dashed);
            
            Rectangle drawingArea = getBounds();
            // 10
            int ly = LineChartRenderer.yForValue(drawingArea, 10);
            gc.drawLine(0, ly, gc.getClipBounds().x + gc.getClipBounds().width, ly);
            gc.drawString("10", 0, ly);
            // 50
            ly = LineChartRenderer.yForValue(drawingArea, 50);
            gc.drawLine(0, ly, gc.getClipBounds().x + gc.getClipBounds().width, ly);
            gc.drawString("50", 0, ly);
            // 90
            ly = LineChartRenderer.yForValue(drawingArea, 90);
            gc.drawLine(0, ly, gc.getClipBounds().x + gc.getClipBounds().width, ly);
            gc.drawString("90", 0, ly);

            g2.setStroke(defaultStroke);

            gc.setColor(fg);

            setBackground(bg);
        }

        /**
         * {@inheritDoc}
         */
        public String getToolTipText(TimeBarRow row, Rectangle drawingArea, int x, int y) {
            return "Header";
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(Rectangle drawingArea, int x, int y) {
        return true;
    }

    @Override
    public JComponent getHeaderRendererComponent(TimeBarViewer tbv, TimeBarRowHeader value, boolean isSelected) {
        return comp;
    }

    @Override
    public int getWidth() {
        return 10;
    }
}
