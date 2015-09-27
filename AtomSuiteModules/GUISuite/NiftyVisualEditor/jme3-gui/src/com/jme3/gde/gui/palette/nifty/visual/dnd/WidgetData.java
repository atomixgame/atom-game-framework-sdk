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
package com.jme3.gde.gui.palette.nifty.visual.dnd;

import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.base.model.elements.GElement;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Widget data is the cross function of default Swing Transferable and Netbean's
 * Pallete mechanism
 *
 * WidgetData information needed for a single Nifty specific Element's template
 * are: Element's Type, Style, Position and predefined controller, etc...
 *
 * @author cris , atomix
 */
public class WidgetData implements Transferable {
    // FIXME: Add flavors

    public static final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "widget");
    public static final DataFlavor POINTFLAVOR = new DataFlavor(Point2D.class, "poin2d");
    public static final DataFlavor TYPE = new DataFlavor(GUITypes.class, "type");
    private GElement e;
    private Point2D p;
    private GUITypes type;

    public WidgetData(GElement element, int diffX, int diffY) {
        e = element;
        p = new Point2D.Double(diffX, diffY);
    }

    public WidgetData(GElement element, GUITypes type) {
        this.e = element;
        this.type = type;
        //p = null;
        //p = new Point2D.Double();
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        //Logger.getLogger(WidgetData.class.getName()).log(Level.INFO, "Call this getTransferDataFlavors");
        return new DataFlavor[]{FLAVOR, POINTFLAVOR, TYPE};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        //Logger.getLogger(WidgetData.class.getName()).log(Level.INFO, "Call this");
        if (flavor == FLAVOR && e != null) {
            return true;
        } else if (flavor == POINTFLAVOR && p != null) {
            return true;
        } else if (flavor == TYPE && type != null) {
            return true;
        }
        return false;

    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (isDataFlavorSupported(flavor)) {
            if (flavor == FLAVOR) {
                return e;
            } else if (flavor == POINTFLAVOR) {
                return p;
            } else {
                return type;
            }
        } else {
            throw new UnsupportedFlavorException(flavor);
        }

    }

    public GUITypes getType() {
        return type;
    }
}
