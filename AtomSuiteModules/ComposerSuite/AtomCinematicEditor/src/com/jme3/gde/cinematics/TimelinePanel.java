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

import com.jme3.gde.cinematics.timeline.KeyFrame;
import com.jme3.gde.cinematics.timeline.TimelineManager;
import com.jme3.gde.cinematics.timeline.TimelineProperty;
import com.jme3.gde.cinematics.timeline.Timeline;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author tomas
 */
public class TimelinePanel extends JPanel implements MouseListener {

    protected TimelineManager manager;

    public TimelinePanel() {
        addMouseListener(this);
    }

    public void setManager(TimelineManager manager) {
        this.manager = manager;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        manager.paintPanelLines(g);

        // Draw timelines
        if (manager != null) {
            int height = manager.getTreeHandler().getRowHeight();
            int y = 0;

            Rectangle rect = new Rectangle(0, y, getWidth(), height);

            for (Timeline timeline : manager.getTimelines()) {
                timeline.paintTimelinePanel(g, rect);
                rect.translate(0, height);

                List<TimelineProperty> properties = timeline.getPropertiesList();
                for (TimelineProperty timelineProperty : properties) {
                    List<KeyFrame> keyFrames = timelineProperty.getKeyFrames();
                    for (KeyFrame keyFrame : keyFrames) {
                        keyFrame.setX(manager.getTimePosition(keyFrame.getTime()));
                    }

                    timelineProperty.paintTimelinePanel(g, rect);
                    rect.translate(0, height);
                }
            }
        }

        // Draw current time
        int currentTimeX = manager.getTimePosition(manager.getCurrentTime());
        g.setColor(Color.white);
        g.drawLine(currentTimeX, 0, currentTimeX, getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Do nothing
        Point point = e.getPoint();
        // Draw timelines
        if (manager != null) {
            for (Timeline timeline : manager.getTimelines()) {
                List<TimelineProperty> properties = timeline.getPropertiesList();
                for (TimelineProperty timelineProperty : properties) {
                    KeyFrame keyframe = timelineProperty.getKeyFrameAt(point);

                    if (keyframe != null) {
                        keyframe.triggerEditor();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }
}
