/*
 *  Copyright (c) 2009-2010 jMonkeyEngine
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.gde.cinematics;

import com.jme3.gde.cinematics.timeline.TimelineManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 *
 * @author tomas
 */
public class TimelineBottomPanel extends JPanel implements MouseListener, MouseMotionListener, AdjustmentListener {

    // Timeline Manager handles the state and comunication of every part that makes up the timeline
    protected TimelineManager manager;
    // Scroll bar to determine the viewport
    protected JScrollBar scrollBar;
    private static final float MIN_BAR_PERCETANGE = 0.10f;
    private static final int SCROLLBAR_MIN = 30;
    private static final int ARROWS_SIZE = 13;
    private int leftEdge = 25;
    private int rightEdge = 75;
    private Dimension scrollDim = new Dimension();

    // Controls Dragging
    protected enum Handle {

        NONE {

            @Override
            public boolean contains(Point p) {
                return false;
            }
        },
        BAR(Color.black, true) {

            @Override
            protected void setupShape(boolean leftSide) {
                shape = new Polygon(new int[]{-3, 3, 3, -3}, new int[]{0, 0, 70, 70}, 4);
            }
        },
        TIMELINE_START(Color.red, true),
        TIMELINE_END(Color.red, false),
        SELECTION_START(Color.green, true),
        SELECTION_END(Color.green, false);
        // Definition begins
        protected Polygon shape;
        protected Point pos = new Point();
        protected Color color;

        Handle() {
        }

        Handle(Color color, boolean leftSide) {
            this.color = color;

            setupShape(leftSide);
        }

        protected void setupShape(boolean leftSide) {
            if (leftSide) {
                shape = new Polygon(new int[]{1, 1, -ARROWS_SIZE}, new int[]{0, ARROWS_SIZE, 0}, 3);
            } else {
                shape = new Polygon(new int[]{0, ARROWS_SIZE, 0}, new int[]{0, 0, ARROWS_SIZE}, 3);
            }
        }

        public boolean contains(Point p) {
            return shape.contains(p);
        }

        public Color getColor() {
            return color;
        }

        public void setLocation(int x, int y) {
            int deltaX = x - pos.x;
            int deltaY = y - pos.y;
            shape.translate(deltaX, deltaY);
            pos.x += deltaX;
            pos.y += deltaY;
        }

        public Point getPosition() {
            return pos;
        }

        public Polygon getShape() {
            return shape;
        }
    }
    protected Handle dragging = Handle.NONE;
    protected Point draggOffset = new Point();

    public TimelineBottomPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);

        scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
        add(scrollBar);
        scrollBar.setMaximum(SCROLLBAR_MIN);
        scrollBar.setValue(scrollBar.getMaximum() / 2);
        scrollBar.addAdjustmentListener(this);
    }

    public TimelineManager getManager() {
        return manager;
    }

    public void setManager(TimelineManager manager) {
        this.manager = manager;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        manager.paintBottomPanelLines(g);

        int treeWidth = manager.getTreeHandler().getTreeWidth();

        // Draw current time
        Handle.BAR.setLocation((treeWidth + manager.getTimePosition(manager.getCurrentTime())), 0);
        Handle.SELECTION_START.setLocation((treeWidth + manager.getTimePosition(manager.getSelectionStartTime())), ARROWS_SIZE);
        Handle.SELECTION_END.setLocation((treeWidth + manager.getTimePosition(manager.getSelectionEndTime())), ARROWS_SIZE);
        Handle.TIMELINE_START.setLocation((treeWidth + manager.getTimePosition(manager.getStartTime())), 0);
        Handle.TIMELINE_END.setLocation((treeWidth + manager.getTimePosition(manager.getEndTime())), 0);

        for (int i = 0; i < Handle.values().length; i++) {
            Handle handle = Handle.values()[i];
            if (handle == Handle.NONE) {
                continue;
            }
            g.setColor(handle.getColor());
            ((Graphics2D) g).fill(handle.getShape());
        }

        int width = getWidth() - treeWidth;
        scrollBar.setLocation(treeWidth, getHeight() - 20);
        scrollDim.setSize(width, 20);
        scrollBar.setSize(scrollDim);
        paintChildren(g); // This is like... wrong in so many ways but it works.

        g.setColor(Color.black);
        g.fillRect(0, 0, treeWidth, getHeight());

        g.drawLine(0, 0, getWidth(), 0);
    }

    public void mouseClicked(MouseEvent e) {
        dragging = Handle.NONE;
    }

    public void mousePressed(MouseEvent e) {
        Point click = e.getPoint();
        for (int i = 0; i < Handle.values().length; i++) {
            Handle handle = Handle.values()[i];
            if (handle.contains(click)) {
                dragging = handle;
                Point pos = dragging.getPosition();
                draggOffset = new Point(pos.x - click.x, pos.y - click.y);
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        dragging = Handle.NONE;
    }

    public void mouseDragged(MouseEvent e) {
        if (dragging == Handle.NONE) {
            return;
        }

        float time = ((e.getPoint().x + draggOffset.x - manager.getTreeHandler().getTreeWidth()) / (TimelineManager.BAR_SEPARATION * manager.getZoomlevel())) + manager.getViewStartTime();
        switch (dragging) {
            case BAR:
                // Changing current time automatically repaints scene
                manager.setCurrentTime(time);
                break;
            case SELECTION_START:
                manager.setSelectionStartTime(time);
                manager.repaintPanels();
                break;
            case SELECTION_END:
                manager.setSelectionEndTime(time);
                manager.repaintPanels();
                break;
            case TIMELINE_START:
                manager.setStartTime(time);
                manager.repaintPanels();
                break;
            case TIMELINE_END:
                manager.setEndTime(time);
                manager.repaintPanels();
                break;
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Handles the scroll bar event. It makes it work as if it were infinite
     * by expanding the maximum value allowed. It's a little funky but it
     * works in general.
     * @param e
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        // Remove listener to avoid a cyclic update that ends in a stackoverflow
        scrollBar.removeAdjustmentListener(this);

        leftEdge = (int) (scrollBar.getMaximum() * MIN_BAR_PERCETANGE);
        rightEdge = (int) (scrollBar.getMaximum() * getMaxPercentage());

        int val = e.getValue();
        if (val < leftEdge) {
            scrollBar.setMaximum((int) (scrollBar.getMaximum() * 1.05f));
            leftEdge = val = (int) (scrollBar.getMaximum() * MIN_BAR_PERCETANGE); // Watch out, double assignment
            rightEdge = (int) (scrollBar.getMaximum() * getMaxPercentage());
            scrollBar.setValue((int) (scrollBar.getMaximum() * MIN_BAR_PERCETANGE));
        } else if (val > rightEdge) {
            scrollBar.setMaximum((int) (scrollBar.getMaximum() * 1.05f));
            leftEdge = (int) (scrollBar.getMaximum() * MIN_BAR_PERCETANGE);
            rightEdge = val = (int) (scrollBar.getMaximum() * getMaxPercentage()); // Watch out, double assignment
            scrollBar.setValue((int) (scrollBar.getMaximum() * getMaxPercentage()));
        } else {
            if (scrollBar.getMaximum() > SCROLLBAR_MIN) {
                int oldMax = scrollBar.getMaximum();
                int max = (int) (scrollBar.getMaximum() / 1.05f);
                max = max < SCROLLBAR_MIN ? SCROLLBAR_MIN : max;
                scrollBar.setMaximum(max);
                scrollBar.setValue(max * val / oldMax);
            }
        }

        float a = 2 * viewPortUnitsWidth() / (rightEdge - leftEdge);
        float b = -viewPortUnitsWidth() - (leftEdge * a);
        manager.setViewStartTime(a * val + b);

        scrollBar.addAdjustmentListener(this);

        manager.repaintPanels();
    }

    private float viewPortUnitsWidth() {
        float minViewPortSize = (getWidth() - manager.getTreeHandler().getTreeWidth()) / (TimelineManager.BAR_SEPARATION * manager.getZoomlevel());
        return (scrollBar.getMaximum() * minViewPortSize) / 100.0f;
    }

    private float getMaxPercentage() {
        // Pretty random but it works
        if (scrollBar.getMaximum() > 80) {
            return 0.85f;
        } else {
            return 0.5f + scrollBar.getMaximum() / 250.0f;
        }
    }
}
