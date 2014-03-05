package com.lightdev.demo.gantt.dnd;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 * TreeTableNodeMover.java
 *
 * <p>Transfer handler implementation that supports to move a selected tree node
 * within a
 * <code>JXTreeTable</code>.</p>
 *
 * <p>This class actually is a copy of TreeNodeMover slightly adapted for use
 * with JXTreeTable.</p>
 *
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License, for
 * details see file license.txt in the distribution package of this software
 *
 * @version 1, 30.03.2006
 */
public class TreeTableNodeMover extends TransferHandler {

    /**
     * constructor
     */
    public TreeTableNodeMover() {
        super();
    }

    /**
     * create a transferable that contains all paths that are currently selected
     * in a given tree
     *
     * @see
     * javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
     * @return all selected paths in the given tree (or null if the given
     * component is not a tree table)
     */
    protected Transferable createTransferable(JComponent c) {
        System.out.println("TreeTableNodeMover.createTransferable");
        Transferable t = null;
        if (c instanceof JXTreeTable) {
            JXTreeTable treeTable = (JXTreeTable) c;
            t = new GenericTransferable(treeTable.getPathForRow(treeTable.getSelectedRow()));
            dragPath = treeTable.getPathForRow(treeTable.getSelectedRow());
            if (dragPath != null) {
                draggedNode = (DefaultMutableTreeTableNode) dragPath.getLastPathComponent();
            }
        }
        return t;
    }

    /**
     * move selected paths when export of drag is done
     *
     * @param source the component that was the source of the data
     * @param data the data that was transferred or possibly null if the action
     * is NONE.
     * @param action the actual action that was performed
     */
    protected void exportDone(JComponent source, Transferable data, int action) {
        System.out.println("TreeTableNodeMover.exportDone, source=" + source.getClass().getName());
        if (source instanceof JXTreeTable) {
            JXTreeTable treeTable = (JXTreeTable) source;
            DefaultTreeTableModel model = (DefaultTreeTableModel) treeTable.getTreeTableModel();
            TreePath currentPath = treeTable.getPathForRow(treeTable.getSelectedRow());
            if (currentPath != null) {
                addNodes(currentPath, model, data);
            } else {
                insertNodes(treeTable, model, data);
            }
        }
        draggedNode = null;
        super.exportDone(source, data, action);
    }

    /**
     * add a number of given nodes
     *
     * @param currentPath the tree path currently selected
     * @param model tree model containing the nodes
     * @param data nodes to add
     */
    protected void addNodes(TreePath currentPath, DefaultTreeTableModel model, Transferable data) {
        DefaultMutableTreeTableNode targetNode = (DefaultMutableTreeTableNode) currentPath.getLastPathComponent();
        try {
            TreePath movedPath = (TreePath) data.getTransferData(DataFlavor.stringFlavor);
            DefaultMutableTreeTableNode moveNode = (DefaultMutableTreeTableNode) movedPath.getLastPathComponent();
            if (!moveNode.equals(targetNode)) {
                model.removeNodeFromParent(moveNode);
                model.insertNodeInto(moveNode, targetNode, targetNode.getChildCount());
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * insert a number of given nodes
     *
     * @param tree the tree table showing the nodes
     * @param model the model containing the nodes
     * @param data the nodes to insert
     */
    protected void insertNodes(JXTreeTable treeTable, DefaultTreeTableModel model, Transferable data) {
        Point location = ((TreeTableDropTarget) treeTable.getDropTarget()).getMostRecentDragLocation();
        TreePath path = treeTable.getPathForLocation(location.x, location.y);
        DefaultMutableTreeTableNode targetNode = (DefaultMutableTreeTableNode) path.getLastPathComponent();
        DefaultMutableTreeTableNode parent = (DefaultMutableTreeTableNode) targetNode.getParent();
        try {
            TreePath movedPath = (TreePath) data.getTransferData(DataFlavor.stringFlavor);
            DefaultMutableTreeTableNode moveNode = (DefaultMutableTreeTableNode) movedPath.getLastPathComponent();
            if (!moveNode.equals(targetNode)) {
                model.removeNodeFromParent(moveNode);
                model.insertNodeInto(moveNode, parent, model.getIndexOfChild(parent, targetNode));
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the type of transfer actions supported by the source. This
     * transfer handler supports moving of tree nodes so it returns MOVE.
     *
     * @return TransferHandler.MOVE
     */
    public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE;
    }

    /**
     * get a drag image from the currently dragged node (if any)
     *
     * @param tree the tree table showing the node
     * @return the image to draw during drag
     */
    public BufferedImage getDragImage(JXTreeTable tree) {
        BufferedImage image = null;
        try {
            if (dragPath != null) {
                int row = tree.getRowForPath(dragPath);
                Rectangle pathBounds = tree.getCellRect(row, 0, false);
                TableCellRenderer r = tree.getCellRenderer(row, 0);
                JComponent lbl = (JComponent) r.getTableCellRendererComponent(tree, draggedNode.toString(),
                        false, false, row, 0);
                lbl.setBounds(pathBounds);
                image = new BufferedImage((int) pathBounds.getWidth(), (int) pathBounds.getHeight(),
                        java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D graphics = image.createGraphics();
                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                lbl.setOpaque(false);
                lbl.paint(graphics);
                graphics.dispose();
            }
        } catch (RuntimeException re) {
        }
        return image;
    }
    /**
     * remember the path to the currently dragged node here (got from
     * createTransferable)
     */
    protected TreeTableNode draggedNode;
    /**
     * remember the currently dragged node here (got from createTransferable)
     */
    protected TreePath dragPath;
}
