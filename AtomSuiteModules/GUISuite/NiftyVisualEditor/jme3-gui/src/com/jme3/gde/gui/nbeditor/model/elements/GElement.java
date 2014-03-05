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
package com.jme3.gde.gui.nbeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.loaderv2.types.StyleType;
import de.lessvoid.xml.xpp3.Attributes;
import com.jme3.gde.gui.nbeditor.model.Types;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Cross-functions Wraper (Proxy pattern) for DOM element, GTree's Element and
 * Nifty's Element
 *
 * @author cris
 */
public abstract class GElement {
    // Global Id for the whole class;

    static private int UID = 0;
    // Unique Id for this Element.
    private int UniID;
    // Shared Id for all the Elements in all context.
    protected String id;
    //GElement Tree
    protected LinkedList<GElement> children;
    protected GElement parent;
    //DOM
    protected org.w3c.dom.Element domElement;
    //Nifty
    protected Element niftyElement;
    protected ElementBuilder builder;

    protected GElement() {
        domElement = null;
    }

    public GElement(org.w3c.dom.Element docElement) {
        if (docElement != null) {

            if (docElement.hasAttribute("id")) {
                this.id = docElement.getAttribute("id");
            } else {
                id = "";
            }

            this.domElement = docElement;
            this.parent = null;

            this.children = new LinkedList<GElement>();

            createUniID();
        } else {
            throw new IllegalArgumentException("Element null");
        }
    }

    public GElement(String id, org.w3c.dom.Element docElement) throws IllegalArgumentException {
        if (docElement != null) {
            this.id = id;
            this.domElement = docElement;
            this.parent = null;

            this.children = new LinkedList<GElement>();

            createUniID();
            this.domElement.setAttribute("id", id);
        } else {
            throw new IllegalArgumentException("Element null");
        }

    }

    public void createUniID() {
        // Get an unique ID
        this.UniID = UID;
        UID++;
    }

    public int getUniID() {
        return this.UniID;
    }

    public void setIndex(int index) {
        //nElement.setIndex(index);
        if ((index + 1) < children.size()) {
            GElement after = children.get(index + 1);
            parent.domElement.removeChild(this.domElement);
            parent.domElement.insertBefore(domElement, after.domElement);
        } else {
            GElement after = parent.children.get(index);
            parent.domElement.insertBefore(domElement, after.domElement);
        }
    }

    public void removeFromParent() {
        if (parent != null) {
            this.parent.domElement.removeChild(this.domElement);
            this.parent.children.remove(this);
            this.parent = null;
        }
    }

    public void addChild(GElement toAdd, boolean xml) {
        this.children.add(toAdd);
        toAdd.parent = this;
        if (xml) {
            this.domElement.appendChild(toAdd.domElement);
        }

    }

    public String getID() {
        return id;
    }

    public void setParent(GElement parent) {
        this.parent = parent;
    }

    public java.awt.Rectangle getBounds() {
        int ex = niftyElement.getX();
        int ey = niftyElement.getY();
        int ew = niftyElement.getWidth();
        int eh = niftyElement.getHeight();
        return new java.awt.Rectangle(ex, ey, ew, eh);
    }
    //GUI editor Vecchio

    public boolean contains(Point2D point) {
        int ex = niftyElement.getX();
        int ey = niftyElement.getY();
        int ew = niftyElement.getWidth();
        int eh = niftyElement.getHeight();
        return new java.awt.Rectangle(ex, ey, ew, eh).contains(point.getX(), point.getY());
    }

    public GElement getParent() {
        return this.parent;
    }

    public Element getNiftyElement() {
        return niftyElement;
    }

    public org.w3c.dom.Element toXml() {
        return this.domElement;
    }

    public LinkedList<GElement> getElements() {
        return this.children;
    }

    public Map<String, String> getAttributes() {
        // DOM
        Map<String, String> res = new HashMap<String, String>();
        NamedNodeMap attributes = domElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            Node attr = attributes.item(i);
            res.put(attr.getNodeName(), attr.getNodeValue());
        }
        return res;
    }

    public ArrayList<String> getAttributeNames() {

        ArrayList<String> res = new ArrayList<String>();
        NamedNodeMap attributes = domElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            Node attr = attributes.item(i);
            res.add(attr.getNodeName());
            //attr.getNodeValue();
        }

        return res;
    }

    public void removeAttribute(String key) {
        //DOM
        this.domElement.removeAttribute(key);
        //Nifty
        Attributes att = this.niftyElement.getElementType().getAttributes();
        att.remove(key);
    }

    public String getAttribute(String key) {
        /*
         // Nifty
         Attributes att = this.niftyElement.getElementType().getAttributes();

         if (att.get(key) == null) {
         return "";
         }
         return att.get(key);
         */
        // DOM
        NamedNodeMap att = this.domElement.getAttributes();

        if (att.getNamedItem(key) == null) {
            return "";
        }
        return att.getNamedItem(key).getNodeValue();
    }

    public void setAttribute(String key, Object val) {
        // Nifty
        Attributes att = this.niftyElement.getElementType().getAttributes();
        
        att.set(key, val.toString());
        // DOM
        this.domElement.setAttribute(key, val.toString());
    }

    public void addAttribute(String key, String val) {
        if (key.equals("id")) {
            this.id = val;
        }
        // DOM
        this.domElement.setAttribute(key, val);

        // Nifty
        Attributes att = this.niftyElement.getElementType().getAttributes();
        att.set(key, val);
    }

    /**
     * Heavy method for controls should be called not often
     */
    public void refresh() {
        Nifty temp = niftyElement.getNifty();
        Attributes att = this.niftyElement.getElementType().getAttributes();
        StyleType style = temp.getDefaultStyleResolver().resolve(niftyElement.getStyle());
        if (style != null) {
            style.applyTo(niftyElement.getElementType(), temp.getDefaultStyleResolver());
        }
        niftyElement.setId(id);

        if (getType().isControl()) {
            this.heavyRefresh(temp);
        } else {
            this.lightRefresh();
        }


    }
    
    /**
     * used for simple elment attributes
     */
    public void lightRefresh() {
        Nifty temp = niftyElement.getNifty();
        Attributes att = this.niftyElement.getElementType().getAttributes();
        niftyElement.initializeFromAttributes(att, temp.getRenderEngine());
        temp.getCurrentScreen().layoutLayers();
    }

    private void heavyRefresh(Nifty nifty) {
        int index = parent.getNiftyElement().getElements().indexOf(niftyElement);
        Attributes att = this.niftyElement.getElementType().getAttributes();
        if (att.isSet("renderOrder")) {
            //FIXME: @atomix changed renderOrder
            //nElement.setRenderOrder(att.getAsInteger("renderOrder"));
        }
        niftyElement.markForRemoval();
        final HashMap<String, String> attributes = new HashMap<String, String>();
        for (int i = 0; i < domElement.getAttributes().getLength(); i++) {
            Node n = domElement.getAttributes().item(i);
            attributes.put(n.getNodeName(), n.getNodeValue());

        }
        for (String sel : attributes.keySet()) {
            builder.set(sel, attributes.get(sel));
        }
        niftyElement = builder.build(nifty, nifty.getCurrentScreen(), this.parent.getDropContext());
        nifty.getCurrentScreen().layoutLayers();
    }

    public void reloadElement(Nifty manager) {
        Nifty nif = manager;
        if (niftyElement != null) {
            nif = niftyElement.getNifty();
        }
        niftyElement = nif.getCurrentScreen().findElementByName(id);
    }

    @Override
    public String toString() {
        return this.id;
        //return domElement.toString();
    }

    @Override
    public boolean equals(Object e) {
        if (e instanceof GElement) {
            GElement temp = (GElement) e;
            return UniID == temp.getUniID();
        } else {
            return false;
        }
    }

    protected Element getDropContext() {
        return niftyElement;
    }

    private void createWithChildren(Nifty nifty) {
        this.createNiftyElement(nifty);
        for (GElement ele : children) {
            ele.createWithChildren(nifty);
        }
    }

    /**
     * Sync attributes from a Dom element to Nifty.
     *
     * @param nifty
     */
    public void createNiftyElement(Nifty nifty) {
        final HashMap<String, String> attributes = new HashMap<String, String>();

        //FIXME: Copy attributes
        for (int i = 0; i < domElement.getAttributes().getLength(); i++) {
            Node n = domElement.getAttributes().item(i);
            attributes.put(n.getNodeName(), n.getNodeValue());
        }

        for (String sel : attributes.keySet()) {
            builder.set(sel, attributes.get(sel));
        }

        niftyElement = builder.build(nifty, nifty.getCurrentScreen(), this.parent.getDropContext());
        Logger.getLogger(GElement.class.getName()).log(Level.INFO, "Finish creating a similar nifty element");
    }

    public abstract Types getType();

    public abstract GElement create(String id, org.w3c.dom.Element ele);

    public abstract void initDefault();
}
