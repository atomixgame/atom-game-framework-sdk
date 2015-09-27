/* Copyright 2012 Aguzzi Cristiano

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.jme3.gde.gui.services.niftygui;

import com.jme3.gde.gui.base.model.BaseGUI;
import com.jme3.gde.gui.base.model.GUITypes;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.window.WindowControl;
import com.jme3.gde.gui.base.model.elements.GElement;
import com.jme3.gde.gui.base.model.elements.GLayer;
import com.jme3.gde.gui.base.model.elements.GScreen;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Main model class it is a container for all GUI elements.
 *
 * Wraper for Nifty, GTree , Dom.
 *
 * GUI instance can be created from a DOM object, and marshalled to DOM. GUI
 * will modify the other trees to synchronize all trees accordingly.
 *
 * @author cris
 */
public class NiftyGUI extends BaseGUI {
    /* Nifty elements */

    private final Nifty nifty;

    /**
     * Creates a new gui
     *
     * @param nifty
     */
    public NiftyGUI(Nifty nifty) throws ParserConfigurationException {
        this(nifty, null);
    }

    public NiftyGUI(Nifty nifty, Document doc) throws ParserConfigurationException {
        this.nifty = nifty;
        domRoot = (Element) document.getElementsByTagName("nifty").item(0);
    }

    public Nifty getNifty() {
        return nifty;
    }

    @Override
    public boolean addElement(GElement child, GElement parent) {
        super.addElement(child, parent);
        //child.createNiftyElement(nifty);
        Logger.getLogger(NiftyGUI.class.getName()).log(Level.INFO, "Added element and refresh");
        return true;
    }

    @Override
    public void addScreen(GScreen screen) {
        super.addScreen(screen);
        screen.createNiftyElement(nifty);
        nifty.gotoScreen(screen.getID());
    }

    @Override
    public void goTo(GScreen screen) {
        this.nifty.gotoScreen(screen.getID());
    }

    @Override
    public void reloadAfterFresh() {
        for (GScreen sel : this.screens) {
            sel.reloadElement(nifty);
        }
        for (GLayer lay : this.currentlayers) {
            lay.reloadElement(nifty);
            for (GElement ele : this.getAllChild(lay)) {
                ele.reloadElement(nifty);
            }
        }
    }

    @Override
    public void removeElement(GElement e) {
        if (e.getType().equals(GUITypes.SCREEN)) {
            this.screens.remove(e);
            nifty.removeScreen(e.getID());
        } else if (e.getType().equals(GUITypes.LAYER)) {
            this.currentlayers.remove(e);
            nifty.removeElement(nifty.getCurrentScreen(), e.getNiftyElement());
        } else {
            nifty.removeElement(nifty.getCurrentScreen(), e.getNiftyElement());
        }
        e.removeFromParent();
    }

    @Override
    public void move(Point2D to, GElement toElement, GElement fromElement) {
        if (!toElement.equals(fromElement)) {
            de.lessvoid.nifty.elements.Element nTo;
            if (toElement.getType().equals(GUITypes.WINDOW)) {
                nTo = toElement.getNiftyElement().getNiftyControl(WindowControl.class).getContent();
            } else {
                nTo = toElement.getNiftyElement();
            }

            if (toElement.getAttribute("childLayout").equals("absolute")) {
                int parentX = toElement.getNiftyElement().getX();
                int parentY = toElement.getNiftyElement().getY();
                fromElement.addAttribute("x", "" + (int) (to.getX() - parentX));
                fromElement.addAttribute("y", "" + (int) (to.getY() - parentY));
                if (!fromElement.getParent().equals(toElement)) {
                    this.nifty.moveElement(this.nifty.getCurrentScreen(), fromElement.getNiftyElement(), nTo, null);
                }
                fromElement.lightRefresh();
            } else {
                this.nifty.moveElement(this.nifty.getCurrentScreen(), fromElement.getNiftyElement(), nTo, null);
            }
            fromElement.removeFromParent();
            toElement.addChild(fromElement, true);
        }
    }
}