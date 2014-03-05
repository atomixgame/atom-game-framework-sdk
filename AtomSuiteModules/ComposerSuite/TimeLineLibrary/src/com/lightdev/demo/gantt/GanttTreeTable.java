package com.lightdev.demo.gantt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;

import com.lightdev.demo.gantt.data.ActivityTreeTableModel;
import com.lightdev.demo.gantt.data.Deliverable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

/**
 * A tree table for gantt charts
 *
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License, for
 * details see file license.txt in the distribution package of this software
 *
 * @version 1, 01.04.2006
 */
public class GanttTreeTable extends JXTreeTable implements ActionListener {

    /**
     * create a new instance of a GanttTreeTable object
     *
     * @param treeModel the data model to use
     * @param ganttRenderer the renderer to use for gantt cell
     * @param dateEditor the editor to use for date cells
     */
    public GanttTreeTable(ActivityTreeTableModel treeModel, GanttTableCellRenderer ganttRenderer,
            DateCellEditor dateEditor) {
        super(treeModel);
        setDefaultRenderer(GanttBar.class, ganttRenderer);
        setDefaultEditor(Date.class, dateEditor);
    }

    /**
     * handle actions related to gantt chart, i.e. repaint in case of changes or
     * create and delete deliverables
     *
     * @param e event that led to this method call
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals(CMD_NEW_ACTIVITY)) {
            createInSelectedRow();
        } else if (cmd.equals(CMD_DELETE_ACTIVITY)) {
            deleteSelectedRow();
        } else if (cmd.equals(TimeFrameProvider.CMD_CHANGE_FRAME_START_DATE)) {
            repaint();
        } else if (cmd.equals(TimeFrameProvider.CMD_CHANGE_FRAME_END_DATE)) {
            repaint();
        }
    }

    /**
     * create a new deliverable in the currently selected row (if any)
     */
    private void createInSelectedRow() {
        TreePath selectedPath = getPathForRow(getSelectedRow());
        if (selectedPath != null) {
            MutableTreeTableNode selectedNode = (MutableTreeTableNode) selectedPath.getLastPathComponent();
            ActivityTreeTableModel model = (ActivityTreeTableModel) getTreeTableModel();
            Deliverable d = new Deliverable();
            d.setName("new activity");
            GregorianCalendar now = new GregorianCalendar();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH);
            int today = now.get(Calendar.DAY_OF_MONTH);
            d.setPlanStartDate(new GregorianCalendar(year, month, today).getTime());
            d.setPlanEndDate(new GregorianCalendar(year, month, today + 5).getTime());
            model.insertNodeInto(new DefaultMutableTreeTableNode(d),
                    selectedNode, selectedNode.getChildCount());
        }
    }

    /**
     * delete the currently selected row, if there is one and if it is not the
     * root node
     */
    private void deleteSelectedRow() {
        TreePath selectedPath = getPathForRow(getSelectedRow());
        if (selectedPath != null) {
            DefaultMutableTreeTableNode selectedNode = (DefaultMutableTreeTableNode) selectedPath.getLastPathComponent();
            if (selectedNode.getParent()!=null) {
                ActivityTreeTableModel model = (ActivityTreeTableModel) getTreeTableModel();
                model.removeNodeFromParent(selectedNode);
            }
        }
    }
    /* --------------- class constants ----------------------- */
    /**
     * action command to create a new activity
     */
    public static final String CMD_NEW_ACTIVITY = "newActivity";
    /**
     * action command to delete an activity
     */
    public static final String CMD_DELETE_ACTIVITY = "deleteActivity";
}
