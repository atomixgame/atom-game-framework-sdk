/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.services.niftygui.jme;

import com.jme3.gde.gui.services.niftygui.jme.NiftyPreviewPanel;
import com.jme3.gde.gui.file.niftyfile.NiftyGuiDataObject;
import org.netbeans.modules.xml.multiview.ToolBarMultiViewElement;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.openide.nodes.Node;

/**
 *
 * @author normenhansen
 */
public class JMEPreviewToolbarElement extends ToolBarMultiViewElement {
//    private NiftyGuiDataObject dObj;

    private ToolBarDesignEditor comp;
    private NiftyPreviewPanel viewPanel;

    public JMEPreviewToolbarElement(NiftyGuiDataObject dObj) {
        super(dObj);
//        this.dObj = dObj;
        comp = new ToolBarDesignEditor();
        setVisualEditor(comp);
        viewPanel = new NiftyPreviewPanel(dObj, comp);
    }

    @Override
    public SectionView getSectionView() {
        return null;
    }

    @Override
    public void componentShowing() {
        super.componentShowing();
        viewPanel.updatePreView();
    }

    @Override
    public void componentClosed() {
        super.componentClosed();
        viewPanel.cleanup();
    }
}
