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
package com.jme3.gde.cinematics.timeline;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class TimelineProperty<T> {

    protected String name;
    protected boolean selected;
    protected ArrayList<KeyFrame<T>> keyframes = new ArrayList<KeyFrame<T>>();
    protected static Color SELECTED_COLOR = new Color(195, 131, 28, 180);

    public TimelineProperty(String name) {
        this.name = name;
    }

    public void paintTimelinePanel(Graphics g, Rectangle rect) {
        Graphics2D g2 = (Graphics2D) g;
        if (selected) {
            g2.setColor(SELECTED_COLOR);
            g2.fill(rect);
        }

        g2.setColor(Color.black);
        g2.draw(rect);

        for (KeyFrame<T> keyFrame : keyframes) {
            keyFrame.setY((int) rect.getCenterY());
            keyFrame.paint(g);
        }
    }

    public KeyFrame<T> getKeyFrameAt(Point point) {
        for (KeyFrame<T> keyFrame : keyframes) {
            if (keyFrame.isPointInside(point)) {
                return keyFrame;
            }
        }

        return null;
    }

    public boolean remove(KeyFrame<T> kf) {
        return keyframes.remove(kf);
    }

    public boolean add(KeyFrame<T> kf) {
        return keyframes.add(kf);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<KeyFrame<T>> getKeyFrames() {
        return keyframes;
    }

    @Override
    public String toString() {
        return name;
    }
}
