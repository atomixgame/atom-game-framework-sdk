/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.ui.node;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author hungcuong
 */
public class EventNode extends AbstractNode {

    public EventNode(Event obj) {
        super(Children.create(new EventChildFactory(), true), Lookups.singleton(obj));
        setDisplayName("Event " + obj.getIndex());
    }

    public EventNode() {
        super(Children.create(new EventChildFactory(), true));
        setDisplayName("Root");
    }
}
