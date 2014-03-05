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

import java.util.Enumeration;
import java.util.List;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author tomas
 */
public class TimelineTreeHandler implements TreeModelListener, TreeSelectionListener, TreeExpansionListener {

    protected JTree tree;
    protected DefaultMutableTreeNode root;
    protected DefaultTreeModel model;
    protected TimelineManager manager;

    public TimelineTreeHandler(JTree tree) {
        this.tree = tree;
        root = new DefaultMutableTreeNode("Root");
        model = new DefaultTreeModel(root);
        tree.setModel(model);

        tree.setShowsRootHandles(true);
        tree.setRootVisible(false);

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        tree.addTreeExpansionListener(this);
        model.addTreeModelListener(this);

        tree.setRowHeight(20);
    }

    public void reload() {
        model.reload();
    }

    public TimelineManager getManager() {
        return manager;
    }

    public void setManager(TimelineManager manager) {
        this.manager = manager;
    }

    public boolean isExpanded(DefaultMutableTreeNode node) {
        return tree.isExpanded(new TreePath(model.getPathToRoot(node)));
    }

    public int getRowHeight() {
        return tree.getRowHeight();
    }

    public int getTreeWidth() {
        return tree.getWidth() + ((JSplitPane) tree.getParent()).getDividerSize();
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void clear() {
        root.removeAllChildren();
        model.reload();
    }

    public void addTimelineNode(Timeline timeline) {
        DefaultMutableTreeNode node = addObject(getRoot(), timeline, false);

        List<TimelineProperty> timelines = timeline.getProperties();
        for (TimelineProperty timelineProperty : timelines) {
            addObject(node, timelineProperty, false);
        }

        model.reload();
    }

    public boolean removeTimelineNode(Timeline timeline) {
        DefaultMutableTreeNode node = findNode(timeline);
        if (node != null) {
            model.removeNodeFromParent(node);
            return true;
        }

        return false;
    }

    public DefaultMutableTreeNode findNode(Timeline timeline) {
        Enumeration children = root.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode timelineNode = (DefaultMutableTreeNode) children.nextElement();

            if (timeline == timelineNode.getUserObject()) {
                return timelineNode;
            }
        }

        return null;
    }

    private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        model.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        return childNode;
    }

    public JTree getTree() {
        return tree;
    }

    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        manager.repaintPanels();
    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {
        manager.repaintPanels();
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
        manager.repaintPanels();
    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {
        manager.repaintPanels();
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
        setSelected(node);

        manager.repaintPanels();

//        if (currentTimeline != null && currentTimeline instanceof SpatialTimeline) {
//            SceneApplication.getApplication().showModel(((SpatialTimeline)currentTimeline).getObject().getName());
//        }
    }

    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        manager.repaintPanels();
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        manager.repaintPanels();
    }
    private Timeline currentTimeline;
    private TimelineProperty currentProperty;

    public TimelineProperty getCurrentProperty() {
        return currentProperty;
    }

    public Timeline getCurrentTimeline() {
        return currentTimeline;
    }

    private void setSelected(DefaultMutableTreeNode node) {
        if (currentTimeline != null) {
            currentTimeline.setSelected(false);
        }

        if (currentProperty != null) {
            currentProperty.setSelected(false);
        }

        if (node.getUserObject() instanceof Timeline) {
            currentTimeline = ((Timeline) node.getUserObject());
            currentProperty = null;

            currentTimeline.setSelected(true);
        } else if (node.getUserObject() instanceof TimelineProperty) {
            currentProperty = ((TimelineProperty) node.getUserObject());
            currentProperty.setSelected(true);

            currentTimeline = (Timeline) ((DefaultMutableTreeNode) node.getParent()).getUserObject();
            currentTimeline.setSelected(true);
        }
    }
}
