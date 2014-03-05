/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.multiview.jme;

import com.jme3.gde.gui.file.niftyfile.NiftyGuiDataObject;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;

/**
 *
 * @author normenhansen
 */
public class JMEPreviewViewDesc extends DesignMultiViewDesc {

    private int type;

    public JMEPreviewViewDesc(NiftyGuiDataObject dObj, int type) {
        super(dObj, "JME Preview");
        this.type = type;
    }

    @Override
    public org.netbeans.core.spi.multiview.MultiViewElement createElement() {
        NiftyGuiDataObject dObj = (NiftyGuiDataObject) getDataObject();
        return new JMEPreviewToolbarElement(dObj);
    }

    @Override
    public java.awt.Image getIcon() {
        return org.openide.util.Utilities.loadImage("com/jme3/gde/gui/resources/images/Computer_File_043.gif"); //NOI18N
    }

    public String preferredID() {
        return "JME_Nifty_multiview_design" + String.valueOf(type);
    }
}
