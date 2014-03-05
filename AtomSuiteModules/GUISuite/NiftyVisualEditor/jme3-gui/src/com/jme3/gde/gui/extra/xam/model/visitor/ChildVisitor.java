/*
 
 */
package com.jme3.gde.gui.extra.xam.model.visitor;

import com.jme3.gde.gui.extra.xam.model.NiftyComponent;

/**
 * Visitor for deep traversing of the model components.
 *
 * @author cuongnguyen
 */
// Generated by XAM AutoGen Tool v0.2
public class ChildVisitor extends DefaultVisitor {
    @Override
    protected void visitComponent(NiftyComponent component) {
        for (NiftyComponent child : component.getChildren()) {
            child.accept(this);
        }
    }
}