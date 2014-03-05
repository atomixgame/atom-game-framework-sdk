/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.palette.visual.dnd;

import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import com.jme3.gde.gui.nbeditor.model.Types;
import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import com.jme3.gde.gui.nbeditor.model.exception.IllegalDropException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * VisualNiftyDropTargetListener callback used by the DropTarget
 *
 * @author cuong.nguyenmanh2
 */
public class VisualNiftyDropTargetListener implements DropTargetListener {

    GUIEditor guiEditor;

    public VisualNiftyDropTargetListener(GUIEditor guiEditor) {
        this.guiEditor = guiEditor;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void drop(DropTargetDropEvent dtde) {
        Logger.getLogger(VisualNiftyDropTargetListener.class.getName()).info("Drag here! VisualNiftyDropTargetListener");

        if (dtde.isDataFlavorSupported(WidgetData.FLAVOR) || dtde.isDataFlavorSupported(WidgetData.TYPE) || dtde.isDataFlavorSupported(WidgetData.POINTFLAVOR)) {
            try {
                if (dtde.getTransferable().isDataFlavorSupported(WidgetData.TYPE)) {

                    //FIXME: Add a Type
                    Logger.getLogger(VisualNiftyDropTargetListener.class.getName()).info("Add a Type");
                    Types res = (Types) dtde.getTransferable().getTransferData(WidgetData.TYPE);

                    // FIXME: Fix the Editor's behaviour
                    guiEditor.addElement(res, dtde.getLocation());
                    dtde.dropComplete(true);

                } else {
                    Logger.getLogger(VisualNiftyDropTargetListener.class.getName()).info("Add a Element");

                    if (dtde.getDropAction() == DnDConstants.ACTION_COPY) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY);

                        GElement res = (GElement) dtde.getTransferable().getTransferData(WidgetData.FLAVOR);

                        // FIXME: Fix the Editor's behaviour
                        guiEditor.addElement(res, dtde.getLocation());

                        dtde.dropComplete(true);
                    } else if (dtde.getDropAction() == DnDConstants.ACTION_MOVE) {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);

                        /*
                         GElement from = (GElement) dtde.getTransferable().getTransferData(WidgetData.FLAVOR);
                         if (dtde.getTransferable().isDataFlavorSupported(WidgetData.POINTFLAVOR)) {

                         Point2D point = (Point2D) dtde.getTransferable().getTransferData(WidgetData.POINTFLAVOR);
                        
                         dtde.getLocation().x = (int) (dtde.getLocation().x - point.getX());
                         dtde.getLocation().y = (int) (dtde.getLocation().y - point.getY());
                         }
                         */
                        // FIXME: Fix the Editor's behaviour
                        //editor.move(dtde.getLocation(), from);

                        dtde.dropComplete(true);
                    }




                    /*
                     Object transData = dtde.getTransferable().getTransferData(WidgetData.FLAVOR);
                
                
                     //if (transData instanceof WidgetData) {
                     dtde.acceptDrop(DnDConstants.ACTION_COPY);
                
                
                     //WidgetData c = (WidgetData) dtde.getTransferable().getTransferData(WidgetData.FLAVOR);
                     Types c = (Types) dtde.getTransferable().getTransferData(WidgetData.TYPE);;
                     // do something after accept

                     J2DNiftyViewContainer comp = (J2DNiftyViewContainer) dtde.getDropTargetContext().getComponent();
                
                     //FIXME: Add real Element adding mechanism
                
                     comp.addNewElement(c.toString());
                     comp.revalidate();
                
                     comp.addNewElement(c);
                
                     System.out.println("Add new " + c.toString());
                     */
                    //}

                }
            } catch (IllegalDropException ex) {
                //JOptionPane.showMessageDialog(dtde.getDropTargetContext().getComponent(), ex.getMessage());
                NotifyDescriptor d = new NotifyDescriptor.Message(ex.getMessage(), NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(d);

            } catch (UnsupportedFlavorException ufe) {
                //ufe.printStackTrace();
                dtde.rejectDrop();
                dtde.dropComplete(true);
                Logger.getLogger(VisualNiftyDropTargetListener.class.getName()).log(Level.SEVERE, null, ufe);
            } catch (IOException ioe) {
                //ioe.printStackTrace();
                dtde.rejectDrop();
                dtde.dropComplete(false);
                Logger.getLogger(VisualNiftyDropTargetListener.class.getName()).log(Level.SEVERE, null, ioe);
            }
        } else {
            Logger.getLogger(VisualNiftyDropTargetListener.class.getName()).info("No support tof drag type!");
            dtde.rejectDrop();
            dtde.dropComplete(false);
        }
    }
}
