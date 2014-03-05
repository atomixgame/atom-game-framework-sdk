/*
 
 */
package com.jme3.gde.gui.extra.xam.model.impl;

import com.jme3.gde.gui.extra.xam.model.NiftyAttribute;
import org.w3c.dom.Element;
import com.jme3.gde.gui.extra.xam.model.NiftyModel;
import com.jme3.gde.gui.extra.xam.model.NiftyQName;
import com.jme3.gde.gui.extra.xam.model.RegisterMouseCursorType;
import com.jme3.gde.gui.extra.xam.model.spi.NiftyComponentBase;
import com.jme3.gde.gui.extra.xam.model.visitor.NiftyComponentVisitor;	

/**
 * Implementation for domain component registerMouseCursorType.
 * 
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public class RegisterMouseCursorTypeImpl extends NiftyComponentBase implements RegisterMouseCursorType {

    public RegisterMouseCursorTypeImpl(NiftyModel model, Element element) {
        super(model, element);
    }
    
    public RegisterMouseCursorTypeImpl(NiftyModel model) {
        this(model, createElementNS(model, NiftyQName.REGISTERMOUSECURSORTYPE));
    }

    // attributes

    public String getId() {
        return getAttribute(NiftyAttribute.ID);
    }

    public void setId(String id) {
        setAttribute(ID_PROPERTY, NiftyAttribute.ID, id);
    }

    public String getFilename() {
        return getAttribute(NiftyAttribute.FILENAME);
    }

    public void setFilename(String filename) {
        setAttribute(FILENAME_PROPERTY, NiftyAttribute.FILENAME, filename);
    }

    public String getHotspotX() {
        return getAttribute(NiftyAttribute.HOTSPOTX);
    }

    public void setHotspotX(String hotspotX) {
        setAttribute(HOTSPOTX_PROPERTY, NiftyAttribute.HOTSPOTX, hotspotX);
    }

    public String getHotspotY() {
        return getAttribute(NiftyAttribute.HOTSPOTY);
    }

    public void setHotspotY(String hotspotY) {
        setAttribute(HOTSPOTY_PROPERTY, NiftyAttribute.HOTSPOTY, hotspotY);
    }

    // child elements

    public void accept(NiftyComponentVisitor visitor) {
        visitor.visit(this);
    }

}