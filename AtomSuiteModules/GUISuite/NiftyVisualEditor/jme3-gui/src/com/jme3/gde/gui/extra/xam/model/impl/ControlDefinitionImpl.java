/*
 
 */
package com.jme3.gde.gui.extra.xam.model.impl;

import com.jme3.gde.gui.extra.xam.model.NiftyAttribute;
import java.util.List;
import org.w3c.dom.Element;
import com.jme3.gde.gui.extra.xam.model.ControlDefinition;
import com.jme3.gde.gui.extra.xam.model.ElementType;
import com.jme3.gde.gui.extra.xam.model.NiftyModel;
import com.jme3.gde.gui.extra.xam.model.NiftyQName;
import com.jme3.gde.gui.extra.xam.model.visitor.NiftyComponentVisitor;	

/**
 * Implementation for domain component controlDefinition.
 * 
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public class ControlDefinitionImpl extends NameableNiftyComponentImpl implements ControlDefinition {

    public ControlDefinitionImpl(NiftyModel model, Element element) {
        super(model, element);
    }
    
    public ControlDefinitionImpl(NiftyModel model) {
        this(model, createElementNS(model, NiftyQName.CONTROLDEFINITION));
    }

    // attributes

    public String getController() {
        return getAttribute(NiftyAttribute.CONTROLLER);
    }

    public void setController(String controller) {
        setAttribute(CONTROLLER_PROPERTY, NiftyAttribute.CONTROLLER, controller);
    }

    // child elements

    public List<ElementType> getElementTypes() {
        return getChildren(ElementType.class);
    }

    public void addElementType(ElementType elementType) {
        appendChild(ELEMENTTYPE_PROPERTY, elementType);
    }

    public void removeElementType(ElementType elementType) {
        removeChild(ELEMENTTYPE_PROPERTY, elementType);
    }

    public void accept(NiftyComponentVisitor visitor) {
        visitor.visit(this);
    }

}