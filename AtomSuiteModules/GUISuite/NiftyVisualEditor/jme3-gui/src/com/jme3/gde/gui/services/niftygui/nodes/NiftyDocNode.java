/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.services.niftygui.nodes;

import com.jme3.gde.gui.services.niftygui.nodes.factory.DocChildrenFactory;
import com.jme3.gde.gui.services.niftygui.NiftyGUI;
import java.awt.Image;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author hungcuong
 */
public class NiftyDocNode extends AbstractNode {

    DocChildrenFactory docChildrenFactory;

    public NiftyDocNode(NiftyGUI docModel) {
        super(Children.LEAF);
        docChildrenFactory = new DocChildrenFactory(docModel);
        setChildren(Children.create(docChildrenFactory, true));
        setDisplayName("Nifty Doc");
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/doc.png");
    }

    @Override
    public Image getOpenedIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/doc.png");
    }

    public void refresh() {
        //FIXME: Need better method for refresh the node tree!
        docChildrenFactory.heavyRefresh();
        Logger.getLogger(NiftyDocNode.class.getName()).log(Level.INFO, "Heavy refresh the element Netbean 's Node tree");
    }
}
