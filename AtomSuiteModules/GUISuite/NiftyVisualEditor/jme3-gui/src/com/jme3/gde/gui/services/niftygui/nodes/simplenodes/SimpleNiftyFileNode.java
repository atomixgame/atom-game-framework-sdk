/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jme3.gde.gui.services.niftygui.nodes.simplenodes;

import org.openide.nodes.AbstractNode;
import org.w3c.dom.Element;

/**
 *
 * @author normenhansen
 */
public class SimpleNiftyFileNode extends AbstractNode{

    public SimpleNiftyFileNode(Element xmlNode) {
        super(new SimpleNiftyFileChildren(xmlNode));
    }
    

}
