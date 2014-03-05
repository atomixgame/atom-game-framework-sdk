/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package mytest.testPropertyPanel;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

public class CarNode extends BeanNode implements PropertyChangeListener {

    public CarNode(Car bean) throws IntrospectionException {
        super(bean, Children.LEAF, Lookups.singleton(bean));
        setDisplayName(bean.getType());
        addPropertyChangeListener(this);
    }

    @Override
    public String getDisplayName() {
        Car c = getLookup().lookup(Car.class);
        if (null != c.getType()) {
            return c.getType();
        }
        return super.getDisplayName();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("type")) {
            String oldDisplayName = evt.getOldValue().toString();
            String newDisplayName = evt.getNewValue().toString();
            fireDisplayNameChange(oldDisplayName, newDisplayName);
        }
    }
}