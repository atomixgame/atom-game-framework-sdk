/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.nbeditor.components.toolbar;

import com.jme3.gde.gui.nbeditor.components.about.AboutForm;
import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NiftyMainViewToolBar extends NBEditorToolBar implements ActionListener {

    public NiftyMainViewToolBar(GUIEditor guiEditor) {
        super(guiEditor);
        createToolbarButtons();
    }

    public void createToolbarButtons() {
        add(makeIconButton("new", "New", "New", "New"));
        add(makeIconButton("open", "Open", "Open", "Open"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("copy", "Copy", "Copy", "Copy"));
        add(makeIconButton("cut", "Cut", "Cut", "Cut"));
        add(makeIconButton("delete", "Delete", "Delete", "Delete"));
        add(makeIconButton("undo", "Undo", "Undo", "Undo"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("select", "Select", "Select", "Select"));
        add(makeIconButton("rectangle", "Select rect", "Select rect", "Select rect"));
        add(makeIconButton("arrow", "Move to", "Move to", "Move to"));
        add(makeIconButton("connect", "Link", "Link", "Link"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("toback", "To back", "To back", "To back"));
        add(makeIconButton("tofront", "To front", "To front", "To front"));
        add(makeIconButton("ungroup", "Ungroup", "Ungroup", "Ungroup"));
        add(makeIconButton("up", "To parent", "To parent", "To parent"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("zoom", "Zoom", "Zoom", "Zoom"));
        add(makeIconButton("zoomin", "Zoom in", "Zoom in", "Zoom in"));
        add(makeIconButton("zoomout", "Zoom out", "Zoom out", "Zoom out"));
        add(makeIconButton("zoomactual", "Zoom actual", "Zoom actual", "Zoom actual"));
        //add(new JSeparator(SwingConstants.VERTICAL));
        add(makeIconButton("tree", "Debug", "Debug", "Debug"));

        add(makeIconButton("", "About", "About", "A"));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Debug")) {
            guiEditor.toogleNiftyDebug();
        } else if (e.getActionCommand().equals("About")) {
            //viewPanel.toogleNiftyDebug();
            AboutForm.getInstance().setVisible(true);
        } else if (e.getActionCommand().equals("Zoom in")) {
            guiEditor.getNiftyView().zoomIn();

        } else if (e.getActionCommand().equals("Zoom out")) {
            guiEditor.getNiftyView().zoomOut();

        } else if (e.getActionCommand().equals("Zoom actual")) {
            guiEditor.getNiftyView().zoomReset();

        } else {
            //
        }
    }
}
