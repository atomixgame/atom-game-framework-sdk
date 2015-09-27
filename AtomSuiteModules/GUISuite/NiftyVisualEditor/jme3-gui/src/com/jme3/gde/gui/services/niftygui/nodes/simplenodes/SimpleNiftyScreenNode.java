/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jme3.gde.gui.services.niftygui.nodes.simplenodes;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author normenhansen
 */
public class SimpleNiftyScreenNode extends AbstractNode{

    public SimpleNiftyScreenNode(String name) {
        super(Children.LEAF);
        setName(name);
    }

}
