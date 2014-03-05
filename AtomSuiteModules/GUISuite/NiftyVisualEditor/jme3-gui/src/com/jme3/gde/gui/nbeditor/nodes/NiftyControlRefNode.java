/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.nbeditor.nodes;

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
import org.w3c.dom.Element;

/**
 *
 * @author hungcuong
 */
public class NiftyControlRefNode extends AbstractNode {

    public NiftyControlRefNode(GElement gTreeNode) {
        super(Children.LEAF, Lookups.singleton(gTreeNode));
        setDisplayName("ControlDef " + gTreeNode.getAttribute("filename"));
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/control.png");
    }
    @Override
    public Image getOpenedIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/control.png");
    }
    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        Element obj = getLookup().lookup(Element.class);

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