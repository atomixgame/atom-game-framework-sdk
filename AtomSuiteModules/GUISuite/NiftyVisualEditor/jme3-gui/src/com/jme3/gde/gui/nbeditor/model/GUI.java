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
package com.jme3.gde.gui.nbeditor.model;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.window.WindowControl;
import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import com.jme3.gde.gui.nbeditor.model.elements.GLayer;
import com.jme3.gde.gui.nbeditor.model.elements.GScreen;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
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
public class GUI extends Observable {

    private static int GUIID = 0;
    /* Nifty elements */
    private final Nifty nifty;
    /* GTree elements */
    private LinkedList<GScreen> screens;
    private LinkedList<GLayer> currentlayers;
    private GScreen currentS;
    private GLayer currentL;
    /* Dom elements */
    private static Document document;
    private Element domRoot;

    public static Element createDomElementFactory(String tag) {
        if (document != null) {
            return document.createElement(tag);
        }
        return null;
    }

    /**
     * Creates a new gui
     *
     * @param nifty
     */
    protected GUI(Nifty nifty) throws ParserConfigurationException {
        this(nifty, null);
    }

    protected GUI(Nifty nifty, Document doc) throws ParserConfigurationException {
        this.nifty = nifty;
        this.screens = new LinkedList<GScreen>();
        this.currentlayers = new LinkedList<GLayer>();
        this.currentS = null;
        this.currentL = null;

        if (doc != null) {
            document = doc;
        } else {
            document = getDefaultNiftyDoc();
        }
        domRoot = (Element) document.getElementsByTagName("nifty").item(0);

        this.GUIID++;
    }

    public Document getDefaultNiftyDoc() throws ParserConfigurationException {
        Document newDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        
        Element newDomRoot = newDoc.createElement("nifty");
        Element style = newDoc.createElement("useStyles");
        Element controls = newDoc.createElement("useControls");

        style.setAttribute("filename", "nifty-default-styles.xml");
        controls.setAttribute("filename", "nifty-default-controls.xml");

        newDoc.appendChild(newDomRoot);
        newDomRoot.appendChild(style);
        newDomRoot.appendChild(controls);
        return newDoc;
    }

    public DOMSource getSource() {
        return new DOMSource(document);
    }
    //FIXME: Add synchonize mechanism

    public void buildTrees() {
    }

    public void buildGTree() {
    }

    public void buildXAMTree() {
    }

    public void addScreen(GScreen screen) {
        this.screens.add(screen);
        domRoot.appendChild(screen.toXml());
        screen.createNiftyElement(nifty);
        nifty.gotoScreen(screen.getID());
    }

    /**
     * Creates a new gui from a specific file
     *
     * @param nifty
     * @param res
     */
    /*
     * FIXME: Remove empty construtor
     public GUI(Nifty nifty, String res) {
     this.nifty = nifty;

     }
     */
    public LinkedList<GScreen> getScreens() {
        return this.screens;
    }

    public void addElementToParent(GElement child, GElement parent) {
        // FIXME: Hierachy building method is confusing and unclean!
        if (parent == null) {
            // It's Screen->Child
            GScreen screen = (GScreen) child;
            this.screens.add(screen);
            this.currentS = screen;
            domRoot.appendChild(screen.toXml());
        } else if (parent.getType().equals(Types.SCREEN)) {

            // It's a Layer->Child 
            GLayer temp = (GLayer) child;
            this.currentL = temp;
            this.currentlayers.add(temp);
            if (this.currentS != null) {
                parent.addChild(child, false);
            }
        } else {
            parent.addChild(child, false);
        }
        this.setChanged();
        //this.notifyObservers(new Action(Action.ADD,child));
        this.clearChanged();

    }

    public boolean addElement(GElement child, GElement parent) {
        // FIXME: It's wrong!
        parent.addChild(child, true);
        child.createNiftyElement(nifty);
        
        Logger.getLogger(GUI.class.getName()).log(Level.INFO, "Added element and refresh");
        
        return true;
    }

    /**
     * Move an element from one position to another and from one parent to
     * another.
     *
     * @param to
     * @param toEle
     * @param from
     */
    public void move(Point2D to, GElement toEle, GElement from) {
        if (!toEle.equals(from)) {
            de.lessvoid.nifty.elements.Element nTo;
            if (toEle.getType().equals(Types.WINDOW)) {
                nTo = toEle.getNiftyElement().getNiftyControl(WindowControl.class).getContent();
            } else {
                nTo = toEle.getNiftyElement();
            }
            if (toEle.getAttribute("childLayout").equals("absolute")) {
                int parentX = toEle.getNiftyElement().getX();
                int parentY = toEle.getNiftyElement().getY();

                from.addAttribute("x", "" + (int) (to.getX() - parentX));
                from.addAttribute("y", "" + (int) (to.getY() - parentY));
                if (!from.getParent().equals(toEle)) {
                    this.nifty.moveElement(this.nifty.getCurrentScreen(), from.getNiftyElement(), nTo, null);
                }
                from.lightRefresh();
            } else {
                this.nifty.moveElement(this.nifty.getCurrentScreen(), from.getNiftyElement(), nTo, null);
            }
            from.removeFromParent();
            toEle.addChild(from, true);

        }
    }

    public void removeElement(GElement e) {
        if (e.getType().equals(Types.SCREEN)) {
            this.screens.remove(e);
            nifty.removeScreen(e.getID());
        } else if (e.getType().equals(Types.LAYER)) {
            this.currentlayers.remove(e);
            nifty.removeElement(nifty.getCurrentScreen(), e.getNiftyElement());
        } else {
            nifty.removeElement(nifty.getCurrentScreen(), e.getNiftyElement());
        }
        e.removeFromParent();
    }

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

    public LinkedList<GElement> getAllChild(GElement element) {
        LinkedList<GElement> res = new LinkedList<GElement>();
        for (GElement ele : element.getElements()) {
            res.add(ele);
            res.addAll(getAllChild(ele));
        }
        return res;
    }

    public GScreen getTopScreen() {
        return this.screens.peekLast();
    }

    public void goTo(GScreen screen) {
        this.nifty.gotoScreen(screen.getID());
    }

    @Override
    public String toString() {
        return "" + this.GUIID;
    }

    public GLayer getTopLayer() {
        return this.currentlayers.peekLast();
    }

    public GElement getRootNode() {
        //throw new UnsupportedOperationException("Not yet implemented");
        // FIXME: Determinate which one is the GTree rootNode
        return null;
    }

    public Nifty getNifty() {
        return nifty;
    }
}