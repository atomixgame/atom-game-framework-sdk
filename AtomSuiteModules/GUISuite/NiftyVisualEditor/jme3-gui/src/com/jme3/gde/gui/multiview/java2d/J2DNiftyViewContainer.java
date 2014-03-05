/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.multiview.java2d;

import com.jme3.gde.gui.file.niftyfile.NiftyGuiDataObject;
import com.jme3.gde.gui.nbeditor.components.toolbar.NiftyElementToolBar;
import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import com.jme3.gde.gui.nbeditor.model.Types;
import com.jme3.gde.gui.palette.visual.dnd.PaletteDropTarget;
import com.jme3.gde.gui.palette.visual.dnd.VisualNiftyDropTargetListener;
import de.lessvoid.nifty.Nifty;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import org.netbeans.modules.xml.multiview.Error;
import org.netbeans.modules.xml.multiview.ui.PanelView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.openide.nodes.Node;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class J2DNiftyViewContainer extends PanelView implements ActionListener {

    private NiftyGuiDataObject niftyDataObject;
    private ToolBarDesignEditor comp;
    private J2DNiftyView offPanel;
    private JScrollPane scrollPanel;
    private JToolBar toolBar;
    private JPanel blackPanel;
    private GUIEditor editor;

    public J2DNiftyViewContainer(NiftyGuiDataObject niftyObject, ToolBarDesignEditor comp) {
        super();
        setRoot(Node.EMPTY);
        this.niftyDataObject = niftyObject;
        this.comp = comp;
        
        comp.setContentView(this);
        comp.setRootContext(Node.EMPTY);
        addToolBar();
        //getTopLevelAncestor()

    }

    @Override
    public void initComponents() {
        super.initComponents();
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        scrollPanel = new JScrollPane();
        offPanel = new J2DNiftyView(640, 480);
        offPanel.init();
        offPanel.createNiftyInstance();
        add(offPanel);
        
        //scrollPanel.getViewport().add(offPanel);
        /*
         blackPanel = new JPanel();
         blackPanel.setBackground(Color.red);
         blackPanel.add(new JLabel("Hello world"));
         scrollPanel.getViewport().add(blackPanel);
        
         add(scrollPanel);
         */


    }

    public void addToolBar() {
        // Buttons
        toolBar = new NiftyElementToolBar(niftyDataObject.getGUIEditor());
        //createMainToolbarButtons();
        toolBar.setPreferredSize(new Dimension(800, 30));
        add(toolBar);
    }

    void updatePreView() {
    }

    void cleanup() {
    }

    @Override
    protected Error validateView() {
        return null;
    }

    @Override
    public void showSelection(Node[] nodes) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void start() {
        offPanel.setManager(editor);
        offPanel.start();
    }

    public void setGUIEditor(GUIEditor guiEditor) {
        this.editor = guiEditor;
        offPanel.setManager(editor);
        setupDnD(guiEditor);
    }

    public Nifty getNifty() {
        return offPanel.getNifty();
    }

    public void stop() {
        offPanel.getNifty().exit();
    }

    /* Drag and drop utilities */
    public void addNewElement(String type) {
        //blackPanel.add(new JLabel(type));
    }

    public void addNewElement(Types c) {
        this.editor.addElement(c);
    }

    public J2DNiftyView getNiftyView() {
        return offPanel;
    }

    public void setupDnD(GUIEditor guiEditor) {
        // setup DnD
        VisualNiftyDropTargetListener dtl = new VisualNiftyDropTargetListener(guiEditor);
        PaletteDropTarget dt = new PaletteDropTarget(this, dtl);
        dt.setDefaultActions(DnDConstants.ACTION_COPY);
        dt.setActive(true);
    }
}
