/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.palette.nifty.visual.dnd;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.BeanInfo;
import java.io.IOException;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NiftyElementDnDHandler extends DragAndDropHandler {

    @Override
    public void customize(ExTransferable exTransferable, Lookup lookup) {
        Node node = lookup.lookup(Node.class);
        // FIXME: Decorator pattern!
        /*
        final Image image = (Image) node.getIcon(BeanInfo.ICON_COLOR_16x16);
        
        // FIXME: This will send the icon image, we need the WidgetData instead!
        exTransferable.put(new ExTransferable.Single(DataFlavor.imageFlavor) {
            @Override
            protected Object getData() throws IOException, UnsupportedFlavorException {
                
                return image;
            }
        });
        */
        
    }
    
}
