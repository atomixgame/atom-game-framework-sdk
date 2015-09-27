/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.services.niftygui;

import com.jme3.gde.gui.base.model.AbstractGUI;
import com.jme3.gde.gui.base.model.GUIFactory;
import de.lessvoid.nifty.Nifty;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NiftyGUIFactory extends GUIFactory<NiftyGUI, Nifty> {

    private NiftyGUI gui;
    private static NiftyGUIFactory instance = null;

    public NiftyGUI createGUI(Nifty nifty) throws ParserConfigurationException {
        gui = new NiftyGUI(nifty);
        return gui;
    }

    public NiftyGUI createGUI(Nifty nifty, Document doc) {
        try {
            gui = new NiftyGUI(nifty, doc);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GUIFactory.class.getName()).log(Level.SEVERE, "Can not create new GUI Model");
            Exceptions.printStackTrace(ex);
        }
        return gui;
    }



    public static NiftyGUIFactory getInstance() {
        if (instance == null) {
            //instance = new GUIFactory();
        }
        return instance;
    }
    @Override
    public AbstractGUI getGUI() {
        return gui;
    }
    @Override
    public Nifty getService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
