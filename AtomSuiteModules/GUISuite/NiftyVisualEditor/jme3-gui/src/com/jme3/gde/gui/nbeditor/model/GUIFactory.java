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

import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import com.jme3.gde.gui.nbeditor.model.elements.GImage;
import com.jme3.gde.gui.nbeditor.model.elements.GLayer;
import com.jme3.gde.gui.nbeditor.model.elements.GPanel;
import com.jme3.gde.gui.nbeditor.model.elements.GScreen;
import com.jme3.gde.gui.nbeditor.model.exception.NoProductException;
import de.lessvoid.nifty.Nifty;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * With this factory you can retrieve gui elements or a new GUI
 *
 * @author cris
 */
public class GUIFactory {

    // FIXME: Class-based loadding methods for Factory is dangerous! Soon to be replaced with Dependency Injections!
    // NOTE: IMPORTANT! [Hidden trap] Keep the orders of this two field and method . As "products" has to be initilized before "static method" load all the components classess!
    private static HashMap<String, GElement> products = new HashMap<String, GElement>();
    /*
     private static String classPack = "com.jme3.gde.gui.nbeditor.model.elements";

     static {
     try {
     System.out.println("Loading all gui components");

     for (Types type : Types.values()) {
     if (!type.equals(Types.NIFTYCONSOLE)) {
     String suffix = "G" + Character.toUpperCase(type.toString().charAt(0));
     String name = suffix + type.toString().substring(1);
     Class.forName(classPack + "." + name);
     }
     }
     Class.forName(classPack + "." + "GConsole");
     System.out.println("Done");
     } catch (ClassNotFoundException ex) {
     Logger.getLogger(GUIFactory.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     */
    private static GUIFactory instance = null;
    private GUI gui;

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

    public static GUIFactory getInstance() {
        if (instance == null) {
            instance = new GUIFactory();
        }
        return instance;
    }

    public GUI createGUI(Nifty nifty) throws ParserConfigurationException {
        gui = new GUI(nifty);
        return gui;
    }

    public GUI createGUI(Nifty nifty, Document doc) {
        try {
            gui = new GUI(nifty, doc);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GUIFactory.class.getName()).log(Level.SEVERE, "Can not create new GUI Model");
            Exceptions.printStackTrace(ex);
        }
        return gui;
    }

    public GElement newGElement(String tag) {
        // FIXME: Create element by tag. Very error prone!
        if (!products.containsKey(tag)) {
            throw new IllegalArgumentException("No product for tag : " + tag);
        }

        Element tempDomElement;

        Types type = Types.valueOf(Types.convert(tag));
        
        /*
         if (type != null) {
         return newGElement(type);
         } else {
         throw new IllegalArgumentException("Tag incompatiable with a valid type, or tag not supported!");
         }
         */
        
        if (type.isControl()) {
            tempDomElement = gui.createDomElementFactory(Types.CONTROL_TAG);
        } else {
            tempDomElement = gui.createDomElementFactory(tag);
        }
        String id = IDgenerator.getInstance().generate(type);
        GElement orignalElement = products.get(tag);
        GElement result = orignalElement.create(id, tempDomElement);
        result.initDefault();

        Logger.getLogger(GUIFactory.class.getName()).log(Level.INFO, "Add new element via Clone from original Type!");
        return result;
    }

    public GElement newGElement(Types type) {
        return newGElement(type.toString());
    }

    public GElement createGElement(Element ele) throws NoProductException {
        String key;
        String tag = ele.getTagName();
        key = tag;

        if (tag.equals(Types.CONTROL_TAG)) {
            key = ele.getAttribute("name");
        }

        if (!products.containsKey(key)) {
            throw new NoProductException(tag);
        }
        String id = ele.getAttribute("id");
        GElement temp = products.get(key);

        Types t = temp.getType();
        if (IDgenerator.getInstance().isUnique(t, id)) {
            return products.get(key).create(id, ele);
        } else {
            return products.get(key).create(IDgenerator.getInstance().generate(t), ele);
        }
    }
}
