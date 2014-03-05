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
package com.jme3.gde.gui.io.saveload;

import com.jme3.gde.gui.nbeditor.model.Types;
import de.lessvoid.nifty.Nifty;
import com.jme3.gde.gui.nbeditor.model.GUI;
import com.jme3.gde.gui.nbeditor.model.GUIFactory;
import com.jme3.gde.gui.nbeditor.model.elements.GElement;
import com.jme3.gde.gui.nbeditor.model.exception.NoProductException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * GUIReader is the mediator between Dom-> GTree and Dom -> XML.
 *
 * This goingto be the main entry when it comes to Netbean's XAM API!
 *
 * @author cris
 */
public class GUIReader implements ErrorHandler {

    private Nifty loader;
    private Set<String> errors;

    public GUIReader(Nifty loader) {
        this.loader = loader;
        this.errors = new HashSet<String>();
    }

    public GUI readGUI(File f) throws ParserConfigurationException, IOException, SAXException, NoProductException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);
        GUI result = GUIFactory.getInstance().createGUI(loader, document);
        Element root = (Element) document.getElementsByTagName("nifty").item(0);

        NodeList screens = root.getElementsByTagName(Types.SCREEN.toString());
        Logger.getLogger(GUIReader.class.getName()).log(Level.INFO, "Screens num:" + screens.getLength());

        for (int i = 0; i < screens.getLength(); i++) {
            Element screen = (Element) screens.item(i);
            try {
                this.addRecursiveChild(screen, null, result);
            } catch (NoProductException ex) {
                this.errors.add(ex.getProduct());
                Logger.getLogger(GUIReader.class.getName()).log(Level.WARNING, "No product for " + screen.getTagName());
                continue;
            }
        }
        return result;
    }

    private void addRecursiveChild(Element element, GElement parent, GUI gui) throws NoProductException {

        GElement Gchild = GUIFactory.getInstance().createGElement(element);
        gui.addElementToParent(Gchild, parent);

        // Continue down
        NodeList children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element cur = (Element) children.item(i);
                try {
                    addRecursiveChild(cur, Gchild, gui);
                } catch (NoProductException e) {

                    Logger.getLogger(GUIReader.class.getName()).log(Level.WARNING, "No product for " + cur.getTagName());
                    this.errors.add(e.getProduct());
                    continue;
                }
            }
        }


    }

    /**
     * Provide all tag not loaded by the editor
     *
     * @return a string with all the tag
     */
    public String getTagNotLoaded() {
        String res = "";
        for (String sel : this.errors) {
            res += sel + ", ";
        }
        return res.substring(0, res.length() > 0 ? res.length() - 1 : 0);
    }

    public void warning(SAXParseException exception) throws SAXException {
        //errors.addWarning("Line " + exception.getLineNumber() + " : " + exception.getMessage());
    }

    public void error(SAXParseException exception) throws SAXException {
        //errors.addError("Line " + exception.getLineNumber() + " : " + exception.getMessage());
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        //errors.addError("Line " + exception.getLineNumber() + " : " + exception.getMessage());
    }
}
