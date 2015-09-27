/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.nbeditor.components.toolbar;

import com.jme3.gde.core.scene.SceneApplication;
import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.Callable;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ElementEditingToolBar extends NBEditorToolBar implements ActionListener {

    public ElementEditingToolBar(GUIEditor guiEditor) {
        super(guiEditor);
        createToolbarButtons();
        
    }


    public void createToolbarButtons() {
        add(makeIconButton("center", "Text center", "Text center", "Text center"));
        add(makeIconButton("left", "Text left", "Text left", "Text left"));
        add(makeIconButton("middle", "Text middle", "Text middle", "Text middle"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("aligncenter", "Align center", "Align center", "Align center"));
        add(makeIconButton("alignleft", "Align left", "Align left", "Align left"));
        add(makeIconButton("alignmiddle", "Align middle", "Align middle", "Align middle"));
        add(makeIconButton("alignbottom", "Align bottom", "Align bottom", "Align bottom"));
        add(makeIconButton("alignright", "Align right", "Align right", "Align right"));
        add(makeIconButton("aligntop", "Align top", "Align top", "Align top"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("font", "Font", "Font", "Font"));
        add(makeIconButton("fontcolor", "Font color", "Font color", "Font color"));
        add(makeIconButton("plain", "Plain", "Plain", "Plain"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("expand", "Expand", "Expand", "Expand"));
        add(makeIconButton("collapse", "Collapse", "Collapse", "Collapse"));
        add(makeIconButton("fit", "Fit", "Fit", "Fit"));
        add(makeIconButton("", "Help", "Help", "H"));
    }

    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
