/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.nbeditor.nodes;

import com.jme3.gde.gui.nbeditor.nodes.factory.NiftyNodeFactory;
import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import java.awt.Image;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author hungcuong
 */
public class NiftyLayerNode extends AbstractNode {

    public NiftyLayerNode(GElement gTreeNode) {
        super(Children.create(new NiftyNodeFactory(gTreeNode), true), Lookups.singleton(gTreeNode));
        setDisplayName("Layer " + gTreeNode.getAttribute("id"));
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/layer.png");
    }
    @Override
    public Image getOpenedIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/layer.png");
    }
    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        GElement obj = getLookup().lookup(GElement.class);

        try {

            Node.Property idProp = new PropertySupport.Reflection(obj.getAttribute("id"), String.class, "toString", null);
            idProp.setName("index");
            set.put(idProp);

        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        sheet.put(set);
        return sheet;

    }
}
