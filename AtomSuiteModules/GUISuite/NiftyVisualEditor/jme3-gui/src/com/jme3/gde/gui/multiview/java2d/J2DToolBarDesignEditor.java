/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.multiview.java2d;

import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class J2DToolBarDesignEditor extends ToolBarDesignEditor{
    private InstanceContent content = new InstanceContent();
    
    public J2DToolBarDesignEditor(){
        super();
        //associateLookup(Lookup.EMPTY);
    }
    private void activeNavigator() {
        //navigatorPanel = new NiftyNavPanelImpl();
        //NavigatorHandler.activatePanel(navigatorPanel);
    }
}
