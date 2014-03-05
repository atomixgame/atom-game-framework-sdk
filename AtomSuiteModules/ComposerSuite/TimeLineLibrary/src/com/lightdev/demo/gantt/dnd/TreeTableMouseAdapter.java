package com.lightdev.demo.gantt.dnd;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;

/**
 * For some reason selected nodes can not be expanded/collapsed when drag is
 * enabled for a JXTreeTable. This MouseAdapter expands or collapses a selected
 * node of a JXTreeTable for which drag is enabled.
 *
 * <p>Unfortunately related code is buried in BasicTreeUI which is not
 * accessible through JXTreeTable so this is a dirty workaround ideally to be
 * eliminated through making available relavant TreeUI methods in future
 * versions of JXTreeTable.</p>
 *
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License, for
 * details see file license.txt in the distribution package of this software
 *
 * @version 1, 31.03.2006
 */
public class TreeTableMouseAdapter extends MouseAdapter {

    /**
     * construct a new instance of a TreeTableMouseAdapter object
     */
    public TreeTableMouseAdapter() {
        super();
    }

    /**
     * whenever the mouse button is released on a JXTreeTable this MouseAdapter
     * is associated to, this method finds out whether or not the mouse was
     * clicked on a row that is currently selected. If yes, it determines the
     * click location. If it is inside the expand/collapse handle (as opposed to
     * icon or name of the node), an expanded row is collapsed and vice versa.
     *
     * @param e the event that led to this method call
     */
    public void mouseReleased(MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JXTreeTable) {
            JXTreeTable treeTable = (JXTreeTable) src;
            TreePath selectedPath = treeTable.getPathForRow(treeTable.getSelectedRow());
            if (selectedPath == null) {
                return;
            }

            int count = selectedPath.getPathCount();
            Point p = e.getPoint();
            int clickRow = treeTable.rowAtPoint(p);
            if (lastRow == clickRow && p.getX() < 16 * count) {
                if (treeTable.isExpanded(clickRow)) {
                    treeTable.collapseRow(clickRow);
                } else {
                    treeTable.expandRow(clickRow);
                }
            } else {
            }
            lastRow = clickRow;
            treeTable.getSelectionModel().setSelectionInterval(clickRow, clickRow);
        }
        super.mouseReleased(e);
    }
    /**
     * the last row a mouseReleased event happened on
     */
    private int lastRow = -1;
}
