/* Copyright 2012 Aguzzi Cristiano

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.jme3.gde.gui.palette.visual.dnd;

import com.jme3.gde.gui.nbeditor.model.exception.IllegalDropException;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author cris
 */
public class PaletteDropTarget extends DropTarget implements Observer {

    //private GUIEditor editor;
    public PaletteDropTarget(Component c, DropTargetListener dtl) throws HeadlessException {
        super(c, dtl);
    }

    @Override
    public synchronized void dragOver(DropTargetDragEvent dtde) {
        super.dragOver(dtde);
        /*
         // FIXME: Fix the connection between Component and the Pallete
         J2DNiftyView comp = (J2DNiftyView) this.getComponent();
        
         // FIXME: Fix the behavior to indicate draging
         if (dtde.getDropAction() == DnDConstants.ACTION_MOVE) {
         comp.moveRect(dtde.getLocation().x, dtde.getLocation().y);
         }
         */
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        //Logger.getLogger(PaletteDropTarget.class.getName()).info("Drag here!");
        if (dtde.isDataFlavorSupported(WidgetData.FLAVOR) || dtde.isDataFlavorSupported(WidgetData.TYPE) || dtde.isDataFlavorSupported(WidgetData.POINTFLAVOR)) {

            try {
                if (dtde.getDropAction() == DnDConstants.ACTION_COPY) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Logger.getLogger(PaletteDropTarget.class.getName()).info("ACTION_COPY!");
                    //GElement res = (GElement) dtde.getTransferable().getTransferData(WidgetData.FLAVOR);
                    
                    //dtde.dropComplete(true);
                } else if (dtde.getDropAction() == DnDConstants.ACTION_MOVE) {
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    Logger.getLogger(PaletteDropTarget.class.getName()).info("ACTION_MOVE!");
                    //dtde.dropComplete(true);
                }
            } catch (IllegalDropException ex) {
                Logger.getLogger(PaletteDropTarget.class.getName()).info("getDropTargetContext");
                JOptionPane.showMessageDialog(dtde.getDropTargetContext().getComponent(), ex.getMessage());
            }

            Logger.getLogger(PaletteDropTarget.class.getName()).info("Check drop action!");
        } else {
            Logger.getLogger(PaletteDropTarget.class.getName()).info("Rejected drop action!");
            dtde.rejectDrop();
        }
        super.drop(dtde);
    }

    @Override
    public void update(Observable o, Object arg) {
        /*
         if (((SimpleNiftyEditorEvent) arg).getType() == SimpleNiftyEditorEvent.NEW) {
         this.editor = ((GUIEditor) o);
         }
         */
    }
}
