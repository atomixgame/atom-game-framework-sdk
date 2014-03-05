package com.lightdev.demo.gantt.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.swing.tree.TreeNode;

import com.lightdev.demo.gantt.Activity;
import com.lightdev.demo.gantt.GanttBar;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * A data model to manage activity objects for use with a JXTreeTable
 *
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License, for
 * details see file license.txt in the distribution package of this software
 *
 * @version 1, 04.04.2006
 */
public class ActivityTreeTableModel extends DefaultTreeTableModel {

    /**
     * construct a new instance of a ActivityTreeTableModel object
     */
    public ActivityTreeTableModel() {
        super();
        setRoot(createDummyRootNode());
        createDummyData();
    }

    /* ------------------- TreeTableModel implementation ----------------- */
    /**
     * get the value of a given table column
     *
     * @param node the node object, i.e. the data object of the current table
     * row
     * @param column index of column to get the value for
     * @return value of node object part designated by column index
     */
    public Object getValueAt(Object node, int column) {
        Object value = null;
        if (node instanceof DefaultMutableTreeTableNode) {
            DefaultMutableTreeTableNode mutableNode = (DefaultMutableTreeTableNode) node;
            Object o = mutableNode.getUserObject();
            if (o != null && o instanceof Activity) {
                Activity activity = (Activity) o;
                switch (column) {
                    case COL_TREE:
                        value = activity.getName();
                        break;
                    case COL_START:
                        value = activity.getStart();
                        break;
                    case COL_END:
                        value = activity.getEnd();
                        break;
                    case COL_DAYS: // a computed row, i.e. data is not stored explicitly in model
                        double startInMilliseconds = (double) activity.getStart().getTime();
                        double endInMilliseconds = (double) activity.getEnd().getTime();
                        value = (endInMilliseconds - startInMilliseconds) / MILLISECONDS_IN_A_DAY;
                        break;
                    case COL_GANTT:
                        value = activity;
                        if (mutableNode.isLeaf()) {
                            ((Activity) value).setType(Activity.ACTIVITY_TYPE_BAR);
                        } else {
                            ((Activity) value).setType(Activity.ACTIVITY_TYPE_GROUP);
                        }
                        break;
                }
            }
        }
        return value;
    }

    /**
     * set the value of a given table cell
     *
     * @param aValue the value to set respective cell to
     * @param node the node containing the cell to set the value of
     * @param column number of cell to set value of
     */
    public void setValueAt(Object aValue, Object node, int column) {
        if (node instanceof DefaultMutableTreeTableNode) {
            DefaultMutableTreeTableNode mutableNode = (DefaultMutableTreeTableNode) node;
            Object o = mutableNode.getUserObject();
            if (o != null && o instanceof Activity) {
                Activity activity = (Activity) o;
                switch (column) {
                    case COL_TREE:
                        activity.setName(aValue.toString());
                        break;
                    case COL_START:
                        Date newStartDate = (Date) aValue;
                        double startInMilliseconds = (double) activity.getStart().getTime();
                        double newStartInMilliseconds = (double) newStartDate.getTime();
                        double differenceInMilliseconds = newStartInMilliseconds - startInMilliseconds;
                        double endInMilliseconds = (double) activity.getEnd().getTime();
                        double newEndInMilliseconds = endInMilliseconds + differenceInMilliseconds;
                        activity.setStart(newStartDate);
                        activity.setEnd(new Date((long) newEndInMilliseconds));
                        updateNode((DefaultMutableTreeTableNode) mutableNode.getParent());
                        break;
                    case COL_END:
                        activity.setEnd((Date) aValue);
                        updateNode((DefaultMutableTreeTableNode) mutableNode.getParent());
                        break;
                    case COL_DAYS:
                        startInMilliseconds = (double) activity.getStart().getTime();
                        newEndInMilliseconds =
                                startInMilliseconds + ((Double) aValue).doubleValue() * MILLISECONDS_IN_A_DAY;
                        activity.setEnd(new Date((long) newEndInMilliseconds));
                        updateNode((DefaultMutableTreeTableNode) mutableNode.getParent());
                        break;
                    case COL_GANTT:
                        break;
                }
            }
        }
    }

    /**
     * determine whether or not a given column may be edited
     *
     * <p>This particular implementation allows to edit dates, durations and
     * names of all leaf nodes as well as the names of parent nodes. It refuses
     * editing for the gantt bar column and the date/duration columns of parent
     * nodes because date and duration columns of parent nodes are set to the
     * min/max dates of child nodes automatically.</p>
     *
     * @param node the node (i.e. row) for which editing is to be determined
     * @param column the column for which editing is to be determined
     * @return true, when editing is allowed for the given node and column,
     * false if not
     */
    public boolean isCellEditable(Object node, int column) {
        boolean editable = false;
        if (column < COL_GANTT) {
            if (((TreeNode) node).isLeaf()) {
                editable = true;
            } else {
                if (column == COL_TREE) {
                    editable = true;
                }
            }
        }
        return editable;
    }

    /**
     * get the number of columns in this data model
     *
     * @return the number of cell
     */
    public int getColumnCount() {
        return cNames.length;
    }

    /**
     * get name of column with a given number
     *
     * @param number of column to get name of
     * @return the column name
     */
    public String getColumnName(int column) {
        return cNames[column];
    }

    /**
     * get the class of the column with a given column index
     *
     * <p>JTable uses this method to control which cell renderer and editor to
     * use for a column.</p>
     *
     * @param column index of column to get the class for
     * @return class of column with given index
     */
    public Class getColumnClass(int column) {
        if (column == COL_TREE) {
            return super.getColumnClass(column);
        } else {
            return cTypes[column];
        }
    }

    /* ----------------------- application specific additions ---------------------- */
    /**
     * overridden so that parent nodes can be updated to reflect the correct
     * date ranges of children
     */
    public void insertNodeInto(MutableTreeTableNode newChild, MutableTreeTableNode parent, int index) {
        super.insertNodeInto(newChild, parent, index);
        updateNode((DefaultMutableTreeTableNode) parent);
    }

    /**
     * overridden so that parent nodes can be updated to reflect the correct
     * date ranges of children
     */
    public void removeNodeFromParent(MutableTreeTableNode node) {
        DefaultMutableTreeTableNode parent = (DefaultMutableTreeTableNode) node.getParent();
        super.removeNodeFromParent(node);
        updateNode(parent);
    }

    /**
     * recursively update parent nodes, i.e. set start and end date of parent
     * nodes to min/max of children
     *
     * @param parentNode the parent to update with its childrens min/max dates
     */
    private void updateNode(DefaultMutableTreeTableNode parentNode) {
        if (parentNode != null && !parentNode.isLeaf()) {
            Activity activity = (Activity) parentNode.getUserObject();
            long startDate = activity.getStart().getTime();
            long endDate = activity.getEnd().getTime();
            long newStart = Long.MAX_VALUE;
            long newEnd = Long.MIN_VALUE;
            Enumeration children = parentNode.children();
            while (children.hasMoreElements()) {
                DefaultMutableTreeTableNode child = (DefaultMutableTreeTableNode) children.nextElement();
                Activity childD = (Activity) child.getUserObject();
                newStart = Math.min(newStart, childD.getStart().getTime());
                newEnd = Math.max(newEnd, childD.getEnd().getTime());
            }
            if (newStart != startDate || newEnd != endDate) {
                activity.setStart(new Date(newStart));
                activity.setEnd(new Date(newEnd));
            }
            if (parentNode.getParent() != null) {
                updateNode((DefaultMutableTreeTableNode) parentNode.getParent());
            }
        }
    }

    /**
     * create a "dummy" root node for demonstration purposes
     *
     * @return the root node
     */
    private TreeTableNode createDummyRootNode() {
        GregorianCalendar now = new GregorianCalendar();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);

        DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode();
        Activity activity = new Deliverable();
        activity.setName("Project root");
        activity.setStart(new GregorianCalendar(year, month, 1).getTime());
        activity.setEnd(new GregorianCalendar(year, month, 15).getTime());
        root.setUserObject(activity);

        return root;
    }

    /**
     * create some "dummy" data for demonstration purposes.
     */
    private void createDummyData() {
        GregorianCalendar now = new GregorianCalendar();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);

        DefaultMutableTreeTableNode root = (DefaultMutableTreeTableNode) getRoot();

        Activity activity = new Deliverable();
        activity.setName("Activity 1");
        activity.setStart(new GregorianCalendar(year, month, 3).getTime());
        activity.setEnd(new GregorianCalendar(year, month, 8).getTime());
        insertNodeInto(new DefaultMutableTreeTableNode(activity), root, root.getChildCount());

        activity = new Deliverable();
        activity.setName("Activity 2");
        activity.setStart(new GregorianCalendar(year, month, 7).getTime());
        activity.setEnd(new GregorianCalendar(year, month, 21).getTime());
        DefaultMutableTreeTableNode a2 = new DefaultMutableTreeTableNode(activity);
        insertNodeInto(a2, root, root.getChildCount());

        activity = new Deliverable();
        activity.setName("Activity 3");
        activity.setStart(new GregorianCalendar(year, month, 5).getTime());
        activity.setEnd(new GregorianCalendar(year, month, 10).getTime());
        insertNodeInto(new DefaultMutableTreeTableNode(activity), a2, a2.getChildCount());

        activity = new Deliverable();
        activity.setName("Activity 4");
        activity.setStart(new GregorianCalendar(year, month, 7).getTime());
        activity.setEnd(new GregorianCalendar(year, month, 21).getTime());
        insertNodeInto(new DefaultMutableTreeTableNode(activity), a2, a2.getChildCount());

        activity = new Deliverable();
        activity.setName("Activity 5");
        activity.setStart(new GregorianCalendar(year, month, 17).getTime());
        activity.setEnd(new GregorianCalendar(year, month, 26).getTime());
        insertNodeInto(new DefaultMutableTreeTableNode(activity), root, root.getChildCount());
    }
    /* ---------------------- class fields ------------------------- */
    /**
     * table column names
     */
    static protected String[] cNames = {"Activity", "Start", "End", "Days", "Gantt"};
    /**
     * table column types
     */
    static protected Class[] cTypes = {
        String.class, Date.class, Date.class, Double.class, GanttBar.class};
    /* ---------------------- class constants ------------------------- */
    /**
     * index of tree column
     */
    public static final int COL_TREE = 0;
    /**
     * index of start date column
     */
    public static final int COL_START = 1;
    /**
     * index of end date column
     */
    public static final int COL_END = 2;
    /**
     * index of duration column
     */
    public static final int COL_DAYS = 3;
    /**
     * index of gantt column
     */
    public static final int COL_GANTT = 4;
    /**
     * number of milliseconds in a day as a constant for easier date computation
     */
    public static final double MILLISECONDS_IN_A_DAY = (double) (24 * 60 * 60 * 1000);
}
