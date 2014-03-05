/*
 
 */
package com.jme3.gde.gui.extra.xam.model.spi;

import java.util.Set;
import javax.xml.namespace.QName;
import com.jme3.gde.gui.extra.xam.model.NiftyComponent;
import org.w3c.dom.Element;

/**
 * Factory for creating domain components. This factory must be provided by 
 * <code>NiftyElementFactoryProvider</code> to be able to plugin to the domain model.
 *
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public interface ElementFactory {

    /**
     * Returns the QNames of the elements this factory is for.
     */
    Set<QName> getElementQNames();
    
    /**
     * Creates a NiftyComponent from a DOM element given the 
     * container component.
     *
     * @param container component requesting creation
     * @param element DOM element from which to create the component
     *
     * @return the domain component being created
     */
    NiftyComponent create(NiftyComponent container, Element element);

}
