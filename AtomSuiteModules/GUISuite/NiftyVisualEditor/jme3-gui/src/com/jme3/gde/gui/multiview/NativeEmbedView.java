/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.multiview;

import com.jme3.gde.core.assets.ProjectAssetManager;
import com.jme3.gde.core.scene.OffScenePanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import org.netbeans.modules.xml.multiview.Error;
import org.netbeans.modules.xml.multiview.ui.PanelView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.openide.nodes.Node;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class NativeEmbedView extends PanelView implements ErrorHandler {

    protected ToolBarDesignEditor comp;
    protected Document doc;
    protected ErrorPanel errors;
    protected OffScenePanel offPanel;
    protected String screen = "";
    protected JScrollPane scrollPanel;
    protected int height = 480;
    protected int width = 640;

    public NativeEmbedView() {
        super();
    }

    public abstract void cleanup();

    protected void createToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(10000, 24));
        toolBar.setMaximumSize(new Dimension(10000, 24));
        toolBar.setFloatable(false);

        // FIXME: Load common display resolution from config files.
        JComboBox comboBox = new JComboBox(new String[]{"640x480", "480x800", "800x480", "800x600", "1024x768", "1280x720"});
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String string = (String) e.getItem();
                if ("640x480".equals(string)) {
                    width = 640;
                    height = 480;
                } else if ("1024x768".equals(string)) {
                    width = 1024;
                    height = 768;
                } else if ("1280x720".equals(string)) {
                    width = 1280;
                    height = 720;
                } else if ("800x600".equals(string)) {
                    width = 800;
                    height = 600;
                } else if ("800x480".equals(string)) {
                    width = 800;
                    height = 480;
                } else if ("480x800".equals(string)) {
                    width = 480;
                    height = 800;
                } else {
                    width = 640;
                    height = 480;
                }
                offPanel.resizeGLView(width, height);
                viewResized(width, height);
                //                updatePreView();
            }
        });
        toolBar.add(comboBox);
        toolBar.add(new JPanel());
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        errors = new ErrorPanel();
        errors.setPreferredSize(new Dimension(0, 80));
        add(errors, BorderLayout.SOUTH);
    }

    protected void viewResized(int width, int height) {
    }

    public void error(SAXParseException exception) throws SAXException {
        //errors.addError("Line " + exception.getLineNumber() + " : " + exception.getMessage());
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        //errors.addError("Line " + exception.getLineNumber() + " : " + exception.getMessage());
    }

    protected void prepareInputHandler() {
    }

    protected abstract void preparePreview();

    @Override
    public void showSelection(Node[] nodes) {
        this.screen = nodes[0].getName();
        final String screen = this.screen;

    }

    public abstract void updatePreView();

    protected void changeScreen(String screen) {
    }

    protected ProjectAssetManager getProjectAssetManager() {
        return null;
    }

    protected void parseGUIFile() {
    }

    protected void validateGUIFile() {
    }

    protected void heavyRefresh() {
    }

    public void updatePreView(final String screen) {
        errors.clear();
        parseGUIFile();
        validateGUIFile();
        heavyRefresh();
        //Tree refresh
        //        java.awt.EventQueue.invokeLater(new Runnable() {
        //
        //            public void run() {
        //                validateTree();
        //            }
        //        });
    }

    @Override
    protected Error validateView() {
        return null;
    }

    public void warning(SAXParseException exception) throws SAXException {
        //errors.addWarning("Line " + exception.getLineNumber() + " : " + exception.getMessage());
    }
}
