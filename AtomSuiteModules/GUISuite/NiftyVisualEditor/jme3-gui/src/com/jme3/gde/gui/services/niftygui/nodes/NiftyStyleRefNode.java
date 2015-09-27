/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.services.niftygui.nodes;

import com.jme3.gde.gui.base.model.elements.GElement;
import java.awt.Image;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;
import org.w3c.dom.Element;

/**
 *
 * @author hungcuong
 */
public class NiftyStyleRefNode extends AbstractNode {

    public NiftyStyleRefNode(GElement gTreeNode) {
        super(Children.LEAF, Lookups.singleton(gTreeNode));
        setDisplayName("StyleDef " + gTreeNode.getAttribute("filename"));
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/style.png");
    }

    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        GElement obj = getLookup().lookup(GElement.class);

        try {

            Node.Property idProp = new PropertySupport.Reflection(obj.getAttribute("filename"), String.class, "toString", null);
            idProp.setName("index");
            set.put(idProp);

        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        sheet.put(set);
        return sheet;

    }
}