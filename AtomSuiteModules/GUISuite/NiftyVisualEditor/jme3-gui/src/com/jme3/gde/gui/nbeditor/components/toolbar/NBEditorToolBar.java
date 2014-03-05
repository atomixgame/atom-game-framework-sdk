/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.nbeditor.components.toolbar;

import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NBEditorToolBar extends JToolBar implements ActionListener {
    protected GUIEditor guiEditor;

    public NBEditorToolBar(GUIEditor guiEditor) {
        this.guiEditor = guiEditor;
    }
    
    
    protected JButton makeIconButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {
        //Look for the image.
        String imgLocation = "com/jme3/gde/gui/resources/icons/tools/"
                + imageName
                + ".gif";
        URL imageURL = NBEditorToolBar.class.getClassLoader().getResource(imgLocation);

        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) {                      //image found
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }

    public void actionPerformed(ActionEvent e) {
    }
}
