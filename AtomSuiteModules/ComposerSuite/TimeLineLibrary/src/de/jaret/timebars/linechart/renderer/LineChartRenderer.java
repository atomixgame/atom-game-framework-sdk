/*
 *  File: LineChartRenderer.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
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

import de.jaret.util.ui.timebars.TimeBarViewerDelegate;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import java.awt.event.MouseEvent;
import java.util.List;



import sg.atom.timeline.model.cine.DataPoint;
import sg.atom.timeline.model.cine.LineChartInterval;
import sg.atom.timeline.model.cine.creator.DataPointModelCreator;
import de.jaret.util.date.Interval;
import de.jaret.util.ui.timebars.swing.renderer.TimeBarRenderer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;

/**
 * renderer rendering a line chart in a LineChartInterval.
 * 
 * @author Peter Kliem
 * @version $Id: LineChartRenderer.java 766 2008-05-28 21:36:48Z kliem $
 */
public class LineChartRenderer implements TimeBarRenderer {

    LineChartRendererComponent _component = new LineChartRendererComponent();

    /**
     * Construct renderer for screen use.
     * 
     */
    public LineChartRenderer() {
    }

    @Override
    public JComponent getTimeBarRendererComponent(TimeBarViewer tbv, Interval value, boolean isSelected, boolean overlapping) {
        _component.setSelected(isSelected);
        _component.setInterval(value);

        return _component;

    }

    @Override
    public Rectangle getPreferredDrawingBounds(Rectangle intervalDrawingArea, TimeBarViewerDelegate delegate, Interval interval, boolean selected, boolean overlap) {
        _component.setDelegate(delegate);
        return intervalDrawingArea;
    }

    class LineChartRendererComponent extends JComponent {

        boolean _selected;
        Interval interval;
        TimeBarViewerDelegate delegate;

        public void setInterval(Interval t) {
            interval = t;
        }

        public LineChartRendererComponent() {
            setLayout(null);
            setOpaque(false);
            addMouseMotionListener(new MouseMotionAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    super.mouseMoved(e);

                    System.out.println("Mouse : X " + e.getX() + " Y " + e.getY());
                }
            });
        }

        public void setSelected(boolean selected) {
            _selected = selected;
        }

        @Override
        protected void paintComponent(Graphics gc) {
            super.paintComponent(gc);

            Color bg = getBackground();
            Graphics2D g2 = (Graphics2D) gc;

            gc.setColor(Color.WHITE);
            g2.fill(gc.getClipBounds());

            // draw lines for 10, 50, 90
            Color fg = gc.getColor();
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
            int ly = yForValue(drawingArea, 10);
            gc.drawLine(0, ly, gc.getClipBounds().x + gc.getClipBounds().width, ly);
            // 50
            ly = yForValue(drawingArea, 50);
            gc.drawLine(0, ly, gc.getClipBounds().x + gc.getClipBounds().width, ly);
            // 90
            ly = yForValue(drawingArea, 90);
            gc.drawLine(0, ly, gc.getClipBounds().x + gc.getClipBounds().width, ly);

            g2.setStroke(defaultStroke);
            gc.setColor(fg);

            // get all points to be drawn
            LineChartInterval lci = (LineChartInterval) interval;

            // get the data points to draw
            // since the drawing of the connecting lines will fail due to the scroll optimization take some more points
            // on each side
            List<DataPoint> points = lci.getDataPoints(delegate.getStartDate().copy().backHours(3), delegate.getEndDate().copy().advanceHours(3));

            Point last = null;

            for (DataPoint dataPoint : points) {
                int x = delegate.xForDate(dataPoint.getTime());
                int y = yForValue(drawingArea, dataPoint.getValue());


                gc.setColor(Color.BLACK);

                if (last != null) {
                    gc.drawLine(last.x, last.y, x, y);
                }
                last = new Point(x, y);
                drawPoint(gc, x, y);
            }
            setBackground(bg);
        }

        private void drawPoint(Graphics gc, int x, int y) {
            int size = 3;
            Color fg = gc.getColor();
            gc.setColor(Color.MAGENTA);

            gc.drawLine(x - size, y - size, x + size, y + size);
            gc.drawLine(x - size, y + size, x + size, y - size);

            gc.setColor(fg);
        }

        public String getToolTipText(Interval interval, Rectangle drawingArea, int x, int y, boolean overlapping) {
            // return the value
            return "" + valueForY(drawingArea, y);
        }

        public boolean contains(Interval interval, Rectangle drawingArea, int x, int y, boolean overlapping) {
            return true;
        }

        public Rectangle getContainingRectangle(Interval interval, Rectangle drawingArea, boolean overlapping) {
            return drawingArea;
        }

        private void setDelegate(TimeBarViewerDelegate delegate) {
            this.delegate = delegate;
        }
    }

    /**
     * Calculate y value for a given value in the line chart example.
     * 
     * @param drawingArea drawing area (of which the height is needed)
     * @param value value to project
     * @return projected y coordinate
     */
    public static int yForValue(Rectangle drawingArea, int value) {
        double vForPix = (double) drawingArea.height / DataPointModelCreator.MAX;
        return drawingArea.y + drawingArea.height - (int) (vForPix * value);
    }

    /**
     * Calculate the value represented by an y coordinate in the line chart example.
     * 
     * @param drawingArea drawing area as the base for the projection
     * @param y y coordinate
     * @return the value
     */
    public static int valueForY(Rectangle drawingArea, int y) {
        double vForPix = (double) drawingArea.height / DataPointModelCreator.MAX;
        int off = drawingArea.height - y;
        return (int) ((double) off / vForPix);
    }
}
