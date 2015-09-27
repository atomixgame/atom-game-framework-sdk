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
package sg.gde.cinematics.timeline;

import sg.gde.cinematics.TimelineBottomPanel;
import sg.gde.cinematics.TimelinePanel;
import sg.gde.cinematics.timeline.keyframes.Vector3fKeyFrame;
import com.jme3.gde.core.scene.PreviewRequest;
import com.jme3.gde.core.scene.SceneListener;
import com.jme3.gde.core.scene.SceneRequest;
import com.jme3.gde.core.sceneexplorer.nodes.JmeSpatial;
import com.jme3.math.Vector3f;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * All time meassures are un seconds.
 * 
 * @author tomas
 */
public class TimelineManager implements TimelinePropertyListener, SceneListener {

    // A second width in pixels with zoomlevel of 100%
    public static int BAR_SEPARATION = 50;
    public static int MIN_BAR_SEPARATION = 40;
    protected static final Color SELECTION_COLOR = new Color(67, 125, 84, 180);
    // Zoomlevel 1.0f is 100%
    protected float zoomlevel = 1.0f;
    // Current Marker Time in seconds
    protected float currentTime = 0f;
    // Start time
    protected float startTime = 0f;
    // End time
    protected float endTime = 6f;
    // Selected area start time
    protected float selectionStartTime = 1f;
    // Selected area end time
    protected float selectionEndTime = 5f;
    // Timeline time that is in position 0 in the panels
    protected float viewStartTime = 0.0f;
    // Panels
    protected TimelinePanel panel;
    protected TimelineBottomPanel bottomPanel;
    protected TimelineTreeHandler treeHandler;
    protected ArrayList<Timeline> timelines = new ArrayList<Timeline>();
    // Graphic vars
    private float[] BAR_JUMPS = new float[]{0.025f, 0.05f, 0.1f, 0.25f, 0.5f, 1.0f, 2.0f, 2.5f, 5.0f, 10.0f, 20.0f, 25.0f, 50.0f};
    private int jumpIdx;
    private float pixelJump;
    private float[][] barPositions = new float[2][30];
    private int totalBars = 0;

    public TimelineManager() {
    }

    public List<Timeline> getTimelines() {
        return timelines;
    }
    // TODO remove me

    public void populateRoot() {
        Timeline timeline;
        TimelineProperty<Vector3f> prop;

        timeline = new SpatialTimeline();
        timeline.setName("Spatial");

        prop = new SpatialPropertyTimeline("Movement");

        Vector3fKeyFrame keyFrame = new Vector3fKeyFrame();
        keyFrame.setTime(0);
        keyFrame.setValue(new Vector3f());
        prop.add(keyFrame);

        keyFrame = new Vector3fKeyFrame();
        keyFrame.setTime(100);
        keyFrame.setValue(new Vector3f(50, 100, 10));
        prop.add(keyFrame);

        timeline.add(prop);

        add(timeline);

//        timeline = new Timeline();
//        timeline.setName("Spatial");
//        timeline.add(new TimelineProperty("Movement"));
//
//        add(timeline);
    }

    public boolean remove(Timeline timeline) {
        timeline.removeTimelinePropertyListener(this);
        treeHandler.removeTimelineNode(timeline);
        return timelines.remove(timeline);
    }

    public void clearTimelines() {
        treeHandler.clear();
        timelines.clear();
    }

    public boolean add(Timeline timeline) {
        timeline.addTimelinePropertyListener(this);
        treeHandler.addTimelineNode(timeline);
        return timelines.add(timeline);
    }

    private void calcLines() {
        int totalWidth = getBottomPanel().getWidth() - getTreeHandler().getTreeWidth();
        float totalSeconds = pixelsToSeconds(totalWidth);
        jumpIdx = 0;
        while (jumpIdx < BAR_JUMPS.length && totalSeconds / BAR_JUMPS[jumpIdx] > 20) {
            jumpIdx++;
        }

        pixelJump = BAR_JUMPS[jumpIdx] * TimelineManager.BAR_SEPARATION * getZoomlevel();

        // First occurence of a bar starting from left to rigth
        float time = (int) ((getViewStartTime() / BAR_JUMPS[jumpIdx])) * BAR_JUMPS[jumpIdx];
        float pos = secondsToPixels(time - getViewStartTime());
        float delta = TimelineManager.MIN_BAR_SEPARATION;

        totalBars = 0;
        for (int i = 0; i < barPositions[0].length; i++) {
            if (delta >= TimelineManager.MIN_BAR_SEPARATION) {
                barPositions[0][i] = pos;
                barPositions[1][i] = time;
                totalBars++;
                delta = 0;
            } else {
                i--;
            }

            pos += pixelJump;
            time += BAR_JUMPS[jumpIdx];
            delta += pixelJump;

            if (pos > totalWidth) {
                break;
            }
        }
    }

    /**
     * Return the position in pixel of a given time
     * @param time
     * @return
     */
    public int getTimePosition(float time) {
        return (int) secondsToPixels(time - getViewStartTime());
    }

    public float secondsToPixels(float time) {
        return time * TimelineManager.BAR_SEPARATION * getZoomlevel();
    }

    public float pixelsToSeconds(float pixels) {
        return pixels / (TimelineManager.BAR_SEPARATION * getZoomlevel());
    }

    public void paintBottomPanelLines(Graphics g) {
        calcLines();

        int treeWidth = getTreeHandler().getTreeWidth();

        g.setColor(Color.lightGray);
        for (int i = 0; i < totalBars; i++) {
            int pos = treeWidth + (int) barPositions[0][i];
            g.drawLine(pos, 0, pos, getBottomPanel().getHeight());
            g.drawString(String.format("%.3f", barPositions[1][i]), pos + 3, 40);
        }
    }

    public void paintPanelLines(Graphics g) {
        calcLines();

        int height = getPanel().getHeight();

        // Fill unselected area
        int startX = getTimePosition(startTime);
        int endX = getTimePosition(endTime);

        g.setColor(Color.darkGray);
        if (startX > 0) {
            if (startX > getPanel().getWidth()) {
                startX = getPanel().getWidth();
            }
            g.fillRect(0, 0, startX, height);
        }

        if (endX < getPanel().getWidth()) {
            if (endX < 0) {
                endX = 0;
            }
            g.fillRect(endX, 0, getPanel().getWidth(), height);
        }

        // Draw start and end time
        g.setColor(Color.white);
        g.drawLine(startX, 0, startX, height);
        g.drawLine(endX, 0, endX, height);

        // Draw vertical lines
        g.setColor(Color.lightGray);
        for (int i = 0; i < totalBars; i++) {
            int pos = (int) barPositions[0][i];
            // Draw vertical lines only insde timeline life
            if (pos >= startX && pos <= endX) {
                g.drawLine(pos, 0, pos, height);
            }
        }

        // Fill selected area
        g.setColor(SELECTION_COLOR);
        int selStartX = getTimePosition(selectionStartTime);
        int selEndX = getTimePosition(selectionEndTime);
        g.fillRect(selStartX, 0, selEndX - selStartX, height);
        g.setColor(Color.white);
        g.drawLine(selStartX, 0, selStartX, height);
        g.drawLine(selEndX, 0, selEndX, height);
    }

    public TimelineBottomPanel getBottomPanel() {
        return bottomPanel;
    }

    public void setBottomPanel(TimelineBottomPanel bottomPanel) {
        this.bottomPanel = bottomPanel;
        if (this.bottomPanel != null) {
            this.bottomPanel.setManager(null);
        }
        bottomPanel.setManager(this);
    }

    public TimelinePanel getPanel() {
        return panel;
    }

    public void setPanel(TimelinePanel panel) {
        this.panel = panel;
        if (this.panel != null) {
            this.panel.setManager(null);
        }
        panel.setManager(this);
    }

    public TimelineTreeHandler getTreeHandler() {
        return treeHandler;
    }

    public void setTreeHandler(TimelineTreeHandler treeHandler) {
        this.treeHandler = treeHandler;
        if (this.treeHandler != null) {
            this.treeHandler.setManager(null);
        }
        treeHandler.setManager(this);
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
        repaintPanels();
    }

    public float getZoomlevel() {
        return zoomlevel;
    }

    public void setZoomlevel(float zoomlevel) {
        this.zoomlevel = zoomlevel;
    }

    public void repaintPanels() {
        panel.repaint();
        bottomPanel.repaint();
    }

    public float getViewStartTime() {
        return viewStartTime;
    }

    public void setViewStartTime(float time) {
        viewStartTime = time;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public float getSelectionEndTime() {
        return selectionEndTime;
    }

    public void setSelectionEndTime(float selectionEndTime) {
        this.selectionEndTime = selectionEndTime;
    }

    public float getSelectionStartTime() {
        return selectionStartTime;
    }

    public void setSelectionStartTime(float selectionStartTime) {
        this.selectionStartTime = selectionStartTime;
    }

    @Override
    public void addedTimelineProperty(Timeline timeline, TimelineProperty property) {
        // TODO Improve cheap reload tactic
        remove(timeline);
        add(timeline);
    }

    @Override
    public void removedTimelineProperty(Timeline timeline, TimelineProperty property) {
        // TODO Improve cheap reload tactic
        remove(timeline);
        add(timeline);
    }


    public void sceneRequested(SceneRequest request) {
        ((SpatialTimeline)timelines.get(0)).setObject(request.getJmeNode());

        reloadControls();
    }

    private void reloadControls() {
        for (Timeline timeline : timelines) {
            timeline.reloadControls();
        }
    }

    public void nodeSelected(JmeSpatial spatial) {
       // TODO
    }

    public void sceneOpened(SceneRequest sr) {

    }

    public void sceneClosed(SceneRequest sr) {

    }

    public void previewCreated(PreviewRequest request) {

    }
}
