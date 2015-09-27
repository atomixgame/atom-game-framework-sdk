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
package com.jme3.gde.gui.base.model;

import com.jme3.gde.gui.base.model.elements.GElement;
import com.jme3.gde.gui.base.model.elements.GImage;
import com.jme3.gde.gui.base.model.elements.GLayer;
import com.jme3.gde.gui.base.model.elements.GPanel;
import com.jme3.gde.gui.base.model.elements.GScreen;
import com.jme3.gde.gui.base.model.exception.NoProductException;
import com.jme3.gde.gui.services.niftygui.NiftyGUI;
import de.lessvoid.nifty.Nifty;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * With this factory you can retrieve gui elements or a new GUI.
 *
 * @author cris
 */
public abstract class GUIFactory<T extends AbstractGUI, S> {

    // FIXME: Class-based loadding methods for Factory is dangerous! Soon to be replaced with Dependency Injections!
    // NOTE: IMPORTANT! [Hidden trap] Keep the orders of this two field and method . As "products" has to be initilized before "static method" load all the components classess!
    private static HashMap<String, GElement> products = new HashMap<String, GElement>();

    public GUIFactory() {
        if (products == null) {
            products = new HashMap<String, GElement>();

        }
        registerProduct(new GLayer());
        registerProduct(new GScreen());
        registerProduct(new GPanel());
        registerProduct(new GImage());
    }

    public static void registerProduct(GElement ele) {
        products.put(ele.getType().toString(), ele);
    }

    public GElement newGElement(String tag) {
        // FIXME: Create element by tag. Very error prone!
        if (!products.containsKey(tag)) {
            throw new IllegalArgumentException("No product for tag : " + tag);
        }

        Element tempDomElement;

        GUITypes type = GUITypes.valueOf(GUITypes.convert(tag));

        /*
         if (type != null) {
         return newGElement(type);
         } else {
         throw new IllegalArgumentException("Tag incompatiable with a valid type, or tag not supported!");
         }
         */

        if (type.isControl()) {
            tempDomElement = createDomElementFactory(GUITypes.CONTROL_TAG);
        } else {
            tempDomElement = createDomElementFactory(tag);
        }
        String id = IDGenerator.getInstance().generate(type);
        GElement orignalElement = products.get(tag);
        GElement result = orignalElement.create(id, tempDomElement);
        result.initDefault();

        Logger.getLogger(GUIFactory.class.getName()).log(Level.INFO, "Add new element via Clone from original Type!");
        return result;
    }

    public GElement newGElement(GUITypes type) {
        return newGElement(type.toString());
    }

    public GElement createGElement(Element ele) throws NoProductException {
        String key;
        String tag = ele.getTagName();
        key = tag;

        if (tag.equals(GUITypes.CONTROL_TAG)) {
            key = ele.getAttribute("name");
        }

        if (!products.containsKey(key)) {
            throw new NoProductException(tag);
        }
        String id = ele.getAttribute("id");
        GElement temp = products.get(key);

        GUITypes t = temp.getType();
        if (IDGenerator.getInstance().isUnique(t, id)) {
            return products.get(key).create(id, ele);
        } else {
            return products.get(key).create(IDGenerator.getInstance().generate(t), ele);
        }
    }

    public abstract T createGUI(S service) throws ParserConfigurationException;

    public abstract T createGUI(S service, Document doc);

    public abstract AbstractGUI getGUI();

    public abstract S getService();
    
    protected Element createDomElementFactory(String tag) {
        return null;
    }
}
