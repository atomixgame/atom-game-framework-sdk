/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.nbeditor.nodes;

import com.jme3.gde.gui.nbeditor.nodes.factory.NiftyNodeFactory;
import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author hungcuong
 */
public class NiftyElementNode extends AbstractNode {

    public NiftyElementNode(GElement gTreeNode) {
        super(Children.create(new NiftyNodeFactory(gTreeNode), true), Lookups.singleton(gTreeNode));
        setDisplayName("Element " + gTreeNode.getAttribute("id"));
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/panel.png");
    }

    @Override
    public Image getOpenedIcon(int type) {
        return ImageUtilities.loadImage("com/jme3/gde/gui/resources/icons/components/16/panel.png");
    }

    @Override
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        
        GElement obj = getLookup().lookup(GElement.class);

        try {

            //Node.Property prop = new 
            /*
            Node.Property uidProp = new PropertySupport.Reflection<String>(obj, String.class, "getUniID", null);
            uidProp.setName("UniqueIndex");
            set.put(uidProp);
*/
            for (String att : obj.getAttributeNames()) {
                Node.Property prop = new ElementAttributeProperty(obj, att);
                set.put(prop);
            }
        } catch (Exception ex) {
            //ErrorManager.getDefault();
        }

        sheet.put(set);
        return sheet;

    }

    class ElementAttributeProperty extends Node.Property {

        private String attributeName;
        private GElement element;

        public ElementAttributeProperty(GElement element, String attributeName) {
            super(String.class);
            this.element = element;
            this.attributeName = attributeName;
            this.setName(attributeName);
        }

        @Override
        public boolean canRead() {
            return true;
        }

        @Override
        public String getValue() throws IllegalAccessException, InvocationTargetException {
            return element.getAttribute(attributeName);
        }

        @Override
        public boolean canWrite() {
            return true;
        }

        @Override
        public void setValue(Object val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            element.setAttribute(attributeName, val);
            element.lightRefresh();
        }
    };
}