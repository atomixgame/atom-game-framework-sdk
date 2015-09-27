/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.base.model;

import com.jme3.gde.gui.base.model.elements.GElement;
import com.jme3.gde.gui.base.model.elements.GLayer;
import com.jme3.gde.gui.base.model.elements.GScreen;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface AbstractGUI {

    boolean addElement(GElement child, GElement parent);

    void addElementToParent(GElement child, GElement parent);

    void addScreen(GScreen screen);

    void buildGTree();

    void buildTrees();

    void buildXAMTree();

    LinkedList<GElement> getAllChild(GElement element);

    Document getDefaultDOMDoc();

    GElement getRootNode();

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
    LinkedList<GScreen> getScreens();

    DOMSource getSource();
    //FIXME: Add synchonize mechanism

    GLayer getTopLayer();

    GScreen getTopScreen();

    void goTo(GScreen screen);

    /**
     * Move an element from one position to another and from one parent to
     * another.
     *
     * @param to
     * @param toEle
     * @param from
     */
    void move(Point2D to, GElement toEle, GElement from);

    void reloadAfterFresh();

    void removeElement(GElement e);

    String toString();
    
}
