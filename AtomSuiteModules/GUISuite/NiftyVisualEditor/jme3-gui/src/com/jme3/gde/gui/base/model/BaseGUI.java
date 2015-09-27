/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.base.model;

import com.jme3.gde.gui.base.model.elements.GElement;
import com.jme3.gde.gui.base.model.elements.GLayer;
import com.jme3.gde.gui.base.model.elements.GScreen;
import de.lessvoid.nifty.controls.window.WindowControl;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Observable;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class BaseGUI extends Observable implements AbstractGUI {

    protected static int GUIID = 0;
    /* Dom elements */
    protected static Document document;
    protected GScreen currentS;
    protected LinkedList<GLayer> currentlayers;
    protected Element domRoot;
    /* GTree elements */
    protected LinkedList<GScreen> screens;
    protected GLayer currentL;

    protected BaseGUI(Document doc) throws javax.xml.parsers.ParserConfigurationException {

        this.screens = new LinkedList<GScreen>();
        this.currentlayers = new LinkedList<GLayer>();
        this.currentS = null;
        this.currentL = null;
        this.GUIID++;
        if (doc != null) {
            document = doc;
        } else {
            document = getDefaultDOMDoc();
        }
    }

    public BaseGUI() {
    }

    public static Element createDomElementFactory(String tag) {
        if (document != null) {
            return document.createElement(tag);
        }
        return null;
    }

    @Override
    public boolean addElement(GElement child, GElement parent) {
        // FIXME: It's wrong!
        parent.addChild(child, true);
        return true;
    }

    @Override
    public void addElementToParent(GElement child, GElement parent) {
        // FIXME: Hierachy building method is confusing and unclean!
        if (parent == null) {
            // It's Screen->Child
            GScreen screen = (GScreen) child;
            this.screens.add(screen);
            this.currentS = screen;
            domRoot.appendChild(screen.toXml());
        } else if (parent.getType().equals(GUITypes.SCREEN)) {
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

    @Override
    public void addScreen(GScreen screen) {
        this.screens.add(screen);
        domRoot.appendChild(screen.toXml());
    }

    @Override
    public void buildGTree() {
    }

    @Override
    public void buildTrees() {
    }

    @Override
    public void buildXAMTree() {
    }

    @Override
    public LinkedList<GElement> getAllChild(GElement element) {
        LinkedList<GElement> res = new LinkedList<GElement>();
        for (GElement ele : element.getElements()) {
            res.add(ele);
            res.addAll(getAllChild(ele));
        }
        return res;
    }

    @Override
    public Document getDefaultDOMDoc() {
        return getDefaultNiftyDoc();
    }

    public Document getDefaultNiftyDoc() {
        // throws ParserConfigurationException {
        Document newDoc;
        try {
            newDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element newDomRoot = newDoc.createElement("nifty");
            Element style = newDoc.createElement("useStyles");
            Element controls = newDoc.createElement("useControls");
            style.setAttribute("filename", "nifty-default-styles.xml");
            controls.setAttribute("filename", "nifty-default-controls.xml");
            newDoc.appendChild(newDomRoot);
            newDomRoot.appendChild(style);
            newDomRoot.appendChild(controls);
            return newDoc;
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    public GElement getRootNode() {
        //throw new UnsupportedOperationException("Not yet implemented");
        // FIXME: Determinate which one is the GTree rootNode
        return null;
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
    @Override
    public LinkedList<GScreen> getScreens() {
        return this.screens;
    }

    @Override
    public DOMSource getSource() {
        return new DOMSource(document);
    }
    //FIXME: Add synchonize mechanism

    @Override
    public GLayer getTopLayer() {
        return this.currentlayers.peekLast();
    }

    @Override
    public GScreen getTopScreen() {
        return this.screens.peekLast();
    }

    @Override
    public void goTo(GScreen screen) {
    }

    /**
     * Move an element from one position to another and from one parent to
     * another.
     *
     * @param to
     * @param toElement
     * @param fromElement
     */
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
                }
                fromElement.lightRefresh();
            } else {
            }
            fromElement.removeFromParent();
            toElement.addChild(fromElement, true);
        }
    }

    @Override
    public void reloadAfterFresh() {
    }

    @Override
    public void removeElement(GElement e) {
        if (e.getType().equals(GUITypes.SCREEN)) {
            this.screens.remove(e);
        } else if (e.getType().equals(GUITypes.LAYER)) {
            this.currentlayers.remove(e);
        } else {
        }
        e.removeFromParent();
    }

    @Override
    public String toString() {
        return "" + this.GUIID;
    }
}
