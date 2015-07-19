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
package com.jme3.gde.gui.nbeditor.controller.dnd;

import com.jme3.gde.gui.services.niftygui.java2d.J2DNiftyView;
import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import com.jme3.gde.gui.services.niftygui.events.SimpleNiftyEditorEvent;
import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.base.model.elements.GElement;
import com.jme3.gde.gui.palette.nifty.visual.dnd.WidgetData;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * TransferHandling is TransferHandler implementation to transfer WidgetData,
 * which implemented Transferable. It create an appropriate WidgetData according
 * to the mouse position
 *
 * @author cris
 */
public class TransferHandling extends TransferHandler implements Observer {

    private GUIEditor gui;

    @Override
    public Transferable createTransferable(JComponent c) {
        if (c instanceof J2DNiftyView) {
            
            Rectangle rec = gui.getSelected().getBounds();
            int a = (int) rec.getCenterX() - gui.getSelected().getNiftyElement().getX();
            int b = (int) rec.getCenterY() - gui.getSelected().getNiftyElement().getY();
            
            return new WidgetData(gui.getSelected(), a, b);
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public void exportAsDrag(JComponent comp, InputEvent e, int action) {
        super.exportAsDrag(comp, e, action);

    }

    @Override
    public int getSourceActions(JComponent c) {

        return MOVE;

    }

    @Override
    public boolean canImport(TransferSupport support) {

        try {
            GElement ele = (GElement) support.getTransferable().getTransferData(WidgetData.FLAVOR);
            if (ele.getType().equals("" + GUITypes.LAYER)) {
                return false;
            }
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(TransferHandling.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TransferHandling.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (((SimpleNiftyEditorEvent) arg).getType() == SimpleNiftyEditorEvent.NEW) {
            this.gui = ((GUIEditor) o);
        }
    }
}
