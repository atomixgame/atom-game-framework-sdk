/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.multiview.java2d;

import com.jme3.gde.gui.file.niftyfile.NiftyGuiDataObject;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;

/**
 *
 * @author normenhansen
 */
public class J2DPreviewViewDesc extends DesignMultiViewDesc {

    private int type;

    public J2DPreviewViewDesc(NiftyGuiDataObject dObj, int type) {
        super(dObj, "J2D Design");
        this.type = type;
    }

    @Override
    public org.netbeans.core.spi.multiview.MultiViewElement createElement() {
        NiftyGuiDataObject dObj = (NiftyGuiDataObject) getDataObject();
        return new J2DPreviewToolbarElement(dObj);
    }

    @Override
    public java.awt.Image getIcon() {
        return org.openide.util.Utilities.loadImage("com/jme3/gde/gui/resources/images/Computer_File_043.gif"); //NOI18N
    }

    @Override
    public String preferredID() {
        return "J2D_Nifty_multiview_design" + String.valueOf(type);
    }
}
