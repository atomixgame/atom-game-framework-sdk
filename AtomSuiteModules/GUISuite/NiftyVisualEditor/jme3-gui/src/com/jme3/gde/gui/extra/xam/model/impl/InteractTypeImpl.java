/*
 
 */
package com.jme3.gde.gui.extra.xam.model.impl;

import com.jme3.gde.gui.extra.xam.model.NiftyAttribute;
import org.w3c.dom.Element;
import com.jme3.gde.gui.extra.xam.model.InteractType;
import com.jme3.gde.gui.extra.xam.model.NiftyModel;
import com.jme3.gde.gui.extra.xam.model.NiftyQName;
import com.jme3.gde.gui.extra.xam.model.spi.NiftyComponentBase;
import com.jme3.gde.gui.extra.xam.model.visitor.NiftyComponentVisitor;	

/**
 * Implementation for domain component interactType.
 * 
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public class InteractTypeImpl extends NiftyComponentBase implements InteractType {

    public InteractTypeImpl(NiftyModel model, Element element) {
        super(model, element);
    }
    
    public InteractTypeImpl(NiftyModel model) {
        this(model, createElementNS(model, NiftyQName.INTERACTTYPE));
    }

    // attributes

    public String getOnClick() {
        return getAttribute(NiftyAttribute.ONCLICK);
    }

    public void setOnClick(String onClick) {
        setAttribute(ONCLICK_PROPERTY, NiftyAttribute.ONCLICK, onClick);
    }

    public String getOnRelease() {
        return getAttribute(NiftyAttribute.ONRELEASE);
    }

    public void setOnRelease(String onRelease) {
        setAttribute(ONRELEASE_PROPERTY, NiftyAttribute.ONRELEASE, onRelease);
    }

    public String getOnMouseOver() {
        return getAttribute(NiftyAttribute.ONMOUSEOVER);
    }

    public void setOnMouseOver(String onMouseOver) {
        setAttribute(ONMOUSEOVER_PROPERTY, NiftyAttribute.ONMOUSEOVER, onMouseOver);
    }

    public String getOnClickRepeat() {
        return getAttribute(NiftyAttribute.ONCLICKREPEAT);
    }

    public void setOnClickRepeat(String onClickRepeat) {
        setAttribute(ONCLICKREPEAT_PROPERTY, NiftyAttribute.ONCLICKREPEAT, onClickRepeat);
    }

    public String getOnClickMouseMove() {
        return getAttribute(NiftyAttribute.ONCLICKMOUSEMOVE);
    }

    public void setOnClickMouseMove(String onClickMouseMove) {
        setAttribute(ONCLICKMOUSEMOVE_PROPERTY, NiftyAttribute.ONCLICKMOUSEMOVE, onClickMouseMove);
    }

    public String getOnClickAlternateKey() {
        return getAttribute(NiftyAttribute.ONCLICKALTERNATEKEY);
    }

    public void setOnClickAlternateKey(String onClickAlternateKey) {
        setAttribute(ONCLICKALTERNATEKEY_PROPERTY, NiftyAttribute.ONCLICKALTERNATEKEY, onClickAlternateKey);
    }

    // child elements

    public void accept(NiftyComponentVisitor visitor) {
        visitor.visit(this);
    }

}