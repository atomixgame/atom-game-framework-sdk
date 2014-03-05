/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package mytest.testPropertyPanel;

import java.beans.IntrospectionException;
import java.util.Arrays;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author hungcuong
 */
class MyChildren extends ChildFactory<Car> {

    public MyChildren() {
    }
    String name[] = {"Honda1", "Yamaha", "Nissan", "Chevolet", "Mana"};

    @Override
    protected boolean createKeys(List<Car> toPopulate) {
        Car[] objs = new Car[5];
        for (int i = 0; i < objs.length; i++) {
            objs[i] = new Car(name[i], 1990 + i);
        }
        toPopulate.addAll(Arrays.asList(objs));
        return true;
    }

    @Override
    protected Node createNodeForKey(Car key) {
        Node result = null;
        try {
            result = new CarNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }

        result.setDisplayName(key.toString());
        return result;
    }
}
