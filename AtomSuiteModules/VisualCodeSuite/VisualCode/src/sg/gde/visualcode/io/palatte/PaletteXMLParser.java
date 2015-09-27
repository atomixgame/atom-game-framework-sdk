/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.visualcode.io.palatte;

import java.io.IOException;
import java.io.InputStream;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class PaletteXMLParser {
    public void loadJAXB(){
        
    }
    public void loadDigester(){
        
    }
    public void load(){
                // "XML Structure" tab is created in Output Window for writing the list of tags:
        InputOutput io = IOProvider.getDefault().getIO("VisualCode Logs", false);
        io.select(); //"XML Structure" tab is selected
        try {
            //Get the InputStream from the EditorCookie:
            InputStream is = this.getClass().getResourceAsStream("PaletteNodes.xml");
            //InputStream is = ClassLoader.getSystemResourceAsStream("sg.atom.visualcode.palette.PaletteNodes.xml");
            //Use the NetBeans org.openide.xml.XMLUtil class to create a org.w3c.dom.Document:
            Document doc = XMLUtil.parse(new InputSource(is), true, true, null, null);
            //Create a list of nodes, for all the elements:
            NodeList list = doc.getElementsByTagName("*");
            //Iterate through the list:
            for (int i = 0; i < list.getLength(); i++) {
                //For each node in the list, create a org.w3c.dom.Node:
                org.w3c.dom.Node mainNode = list.item(i);
                //Create a map for all the attributes of the org.w3c.dom.Node:
                NamedNodeMap map = mainNode.getAttributes();
                //Get the name of the node:
                String nodeName = mainNode.getNodeName();
                //Create a StringBuilder for the Attributes of the Node:
                StringBuilder attrBuilder = new StringBuilder();
                
                
                //Iterate through the map of attributes:
                for (int j = 0; j < map.getLength(); j++) {
                    //Each iteration, create a new Node:
                    org.w3c.dom.Node attrNode = map.item(j);
                    //Get the name of the current Attribute:
                    String attrName = attrNode.getNodeName();
                    //Add the current Attribute to the StringBuilder:
                    attrBuilder.append("*").append(attrName).append(" ");
                }
                //Print the element and its attributes to the Output window:
                io.getOut().println("ELEMENT: " + nodeName
                        + " --> ATTRIBUTES: " + attrBuilder.toString());
            }
            //Close the InputStream:
            is.close();
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    public void eachCategory(){
        
    }
    
    public void eachItem(){
        
    }
}
