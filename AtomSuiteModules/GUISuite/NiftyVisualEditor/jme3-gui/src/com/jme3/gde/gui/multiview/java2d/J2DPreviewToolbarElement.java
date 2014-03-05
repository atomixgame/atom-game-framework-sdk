/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.multiview.java2d;

import com.jme3.gde.gui.file.niftyfile.NiftyGuiDataObject;
import com.jme3.gde.gui.nbeditor.components.toolbar.NiftyMainViewToolBar;
import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import com.jme3.gde.gui.nbeditor.controller.selection.GuiSelectionListener;
import com.jme3.gde.gui.palette.visual.NiftyElementPaletteSupport;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import org.netbeans.modules.xml.multiview.ToolBarMultiViewElement;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.netbeans.spi.navigator.NavigatorLookupHint;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;

/**
 * ToolbarElement is the proxy for Topcomponent and its functions, such as
 * Selection..etc
 *
 * It also stand on the Netbean side to communicate with the GUIEditor, which
 * suite better for pure swing
 *
 * @author normenhansen, atomix
 */
public class J2DPreviewToolbarElement extends ToolBarMultiViewElement {

    private NiftyGuiDataObject niftyDataObject;
    private Lookup proxyLookup;
    private InstanceContent content = new InstanceContent();
    private ToolBarDesignEditor comp;
    private J2DNiftyViewContainer viewPanel;
    private JToolBar toolBar;
    private GUIEditor guiEditor;
    //private NiftyNavPanelImpl navigatorPanel;

    public J2DPreviewToolbarElement(NiftyGuiDataObject dObj) {
        super(dObj);
        this.niftyDataObject = dObj;

        // Link to visual components and controllers
        comp = new J2DToolBarDesignEditor();
        setVisualEditor(comp);
        guiEditor = niftyDataObject.getGUIEditor();
        viewPanel = new J2DNiftyViewContainer(dObj, comp);
        viewPanel.setGUIEditor(guiEditor);
        guiEditor.setViewContainer(viewPanel);
        
        // Setup the lookups
        Lookup dynamicLookup = new AbstractLookup(content);
        proxyLookup = new ProxyLookup(dynamicLookup, super.getLookup());
        content.add(niftyDataObject);
        guiEditor.bindNodeLookup(content);
        
        setupPalette();
        setupNav();
    }

    private void activeNavigator() {
        //
    }
    public void setupNav() {
        content.add(new NavigatorLookupHint() {
            public String getContentType() {
                return "text/x-niftygui+xml+nav";
            }
        });
    }

    public void setupPalette() {
        content.add(NiftyElementPaletteSupport.createPalette());
    }

    @Override
    public Lookup getLookup() {
        return this.proxyLookup;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        toolBar = new NiftyMainViewToolBar(niftyDataObject.getGUIEditor());
        //createMainToolbarButtons();
        toolBar.setPreferredSize(new Dimension(800, 30));
        return toolBar;
    }

    @Override
    public SectionView getSectionView() {
        return null;
    }

    @Override
    public void componentShowing() {
        super.componentShowing();
        loadFile();
        viewPanel.updatePreView();
        guiEditor.startRenderThread();
        activeNavigator();
    }

    @Override
    public void componentClosed() {
        super.componentClosed();
        guiEditor.pauseRenderThread();
        viewPanel.cleanup();
    }

    @Override
    public void componentHidden() {
        super.componentHidden();
        guiEditor.pauseRenderThread();
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
    }

    @Override
    public void componentActivated() {
        super.componentActivated();
    }

    @Override
    public void componentDeactivated() {
        super.componentDeactivated();
    }

    public void loadFile() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                guiEditor.loadFile(niftyDataObject.getPrimaryFile(), viewPanel.getNifty());
                guiEditor.setNodeContext();
            }
        });
    }

    
    public J2DNiftyViewContainer getViewPanel(){
        return viewPanel;
    }
}
