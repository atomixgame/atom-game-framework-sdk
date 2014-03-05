/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.palette.visual;

import com.jme3.gde.gui.nbeditor.model.GUIFactory;
import com.jme3.gde.gui.nbeditor.model.Types;
import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import com.jme3.gde.gui.palette.visual.dnd.WidgetData;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NiftyElementPaletteNode extends AbstractNode {

    private NiftyElementIcon key;

    public NiftyElementPaletteNode(NiftyElementIcon key) {
        super(Children.LEAF);
        String imgPath = key.getImage();
        setIconBaseWithExtension(imgPath);
        String imageName = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("."));
        setShortDescription(imageName);

        this.key = key;
    }

    @Override
    public Transferable drag() throws IOException {
        Logger.getLogger(NiftyElementPaletteNode.class.getName()).log(Level.INFO, "Create Drag transferable!");
        return getData();
    }

    public WidgetData getData() {
        //GElement e = GUIFactory.getInstance().newGElement(key.getType().toString());
        return new WidgetData(null, key.getType());
    }
}
