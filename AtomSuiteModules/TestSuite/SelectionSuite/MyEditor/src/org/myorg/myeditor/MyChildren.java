/*
 * MyChildren.java
 *
 * Created on Jul 25, 2007, 11:37:12 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.myeditor;

import org.myorg.myapi.APIObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gw152771
 */
public class MyChildren extends Children.Keys {

    public MyChildren() {
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    protected void addNotify() {
        APIObject[] objs = new APIObject[5];
        for (int i = 0; i < objs.length; i++) {
            objs[i] = new APIObject();
        }
        setKeys(objs);
    }

    protected Node[] createNodes(Object o) {
        APIObject obj = (APIObject) o;
        return new Node[]{new MyNode(obj)};
    }
}
