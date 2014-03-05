/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.nbeditor.nodes.factory;

import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import com.jme3.gde.gui.nbeditor.nodes.NiftyControlDefNode;
import com.jme3.gde.gui.nbeditor.nodes.NiftyControlNode;
import com.jme3.gde.gui.nbeditor.nodes.NiftyElementNode;
import com.jme3.gde.gui.nbeditor.nodes.NiftyLayerNode;
import com.jme3.gde.gui.nbeditor.nodes.NiftyScreenNode;
import com.jme3.gde.gui.nbeditor.nodes.NiftyStyleRefNode;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author hungcuong
 */
/**
 * This class provide the creation of Nifty Nodes
 *
 *
 */
public class NiftyNodeFactory extends ChildFactory<GElement> {

    private GElement gTreeNode;

    public NiftyNodeFactory(GElement gTreeNode) {
        this.gTreeNode = gTreeNode;
    }

    @Override
    protected boolean createKeys(List<GElement> list) {
        /*
         Element curElement = XmlHelper.findFirstChildElement(gTreeNode);
         while (curElement != null) {
         list.add(curElement);
         curElement = XmlHelper.findNextSiblingElement(curElement);
         }
         */

        list.addAll(gTreeNode.getElements());
        return true;
    }

    @Override
    protected Node createNodeForKey(GElement gTreeNode) {
        String nodeName = gTreeNode.getType().toString();
        if (nodeName.equals("useStyles")) {
            return new NiftyStyleRefNode(gTreeNode);
        } else if (nodeName.equals("useControls")) {
            return new NiftyControlDefNode(gTreeNode);
        } else if (nodeName.equals("useControls")) {
            return new AbstractNode(Children.LEAF);
        } else if (nodeName.equals("screen")) {
            return new NiftyScreenNode(gTreeNode);
        } else if (nodeName.equals("layer")) {
            return new NiftyLayerNode(gTreeNode);
        } else if (nodeName.equals("control") || nodeName.equals("text")) {
            return new NiftyControlNode(gTreeNode);
        } else {
            return new NiftyElementNode(gTreeNode);
        }
    }
}
