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
package com.jme3.gde.gui.nbeditor.controller;

import com.jme3.gde.gui.base.model.AbstractGUI;
import com.jme3.gde.gui.io.saveload.GUIReader;
import com.jme3.gde.gui.io.saveload.GUIWriter;
import com.jme3.gde.gui.services.niftygui.java2d.J2DNiftyView;
import com.jme3.gde.gui.services.niftygui.java2d.J2DNiftyViewContainer;
import com.jme3.gde.gui.nbeditor.controller.selection.GUISelectionListener;
import com.jme3.gde.gui.services.niftygui.events.SimpleNiftyEditorEvent;
import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.base.model.elements.GElement;
import com.jme3.gde.gui.base.model.elements.GLayer;
import com.jme3.gde.gui.base.model.elements.GScreen;
import com.jme3.gde.gui.nbeditor.exception.IllegalDropException;
import com.jme3.gde.gui.base.model.exception.NoProductException;
import com.jme3.gde.gui.services.niftygui.NiftyGUIFactory;
import com.jme3.gde.gui.services.niftygui.nodes.NiftyDocNode;
import de.lessvoid.nifty.Nifty;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.xml.parsers.ParserConfigurationException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.lookup.InstanceContent;
import org.xml.sax.SAXException;

/**
 * Editor controller provides a list of method to interact with GUI.
 *
 * <p>Deal with GTree and Netbean's Node elements. Synchronization automaticly.
 *
 * <p>Provide travel methods
 *
 * <p>Create element via script.
 *
 * <p>Manage mapping between instance element - original element - styles.
 *
 * @see GUI and its elements.
 * @author Cris
 */
public class GUIEditor extends Observable {
    /* GTree elements */

    private AbstractGUI gui;
    private GUIWriter writer;
    private ElementEditor elementEditor;
    // Working layer and screen
    private LinkedList<GLayer> currentLayers;
    private GScreen currentS;
    private GLayer currentL;
    /* Netbean Nodes elements */
    
    private InstanceContent content;
    /* Manage reander thread and operations */
    private Thread renderThread;
    private NiftyDocNode rootContext;
    private J2DNiftyViewContainer viewContainer;
    private String defaultFilePath = "com/jme3/gde/gui/resources/xml/empty.xml";
    // Selection
    private GElement selectedElement;
    private GUISelectionListener guiSelectionListener;

    public GUIEditor() {
        currentLayers = new LinkedList<GLayer>();
        elementEditor = new ElementEditor(null);
    }

    /**
     * Create a new empty gui with one screen
     *
     * @param nifty a valid Nifty instace
     * @see Nifty
     * @throws ParserConfigurationException if controller failed to create a
     * valid document instance
     */
    public void createNewGui(Nifty nifty) throws ParserConfigurationException {
        gui = NiftyGUIFactory.getInstance().createGUI(nifty);

        GScreen screen = (GScreen) NiftyGUIFactory.getInstance().newGElement("" + GUITypes.SCREEN);
        getGui().addScreen(screen);

        this.currentS = screen;
        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.NEW, screen));
        this.clearChanged();

        this.writer = new GUIWriter(gui);
    }

    /**
     * Create a new gui from a file
     *
     * @param nifty a valid Nifty instance
     * @see Nifty
     * @throws ParserConfigurationException if controller failed to create a
     * valid document instance
     * @throws IOException
     * @throws SAXException
     * @throws Exception if nifty can't create the gui
     * @return A string with the elements that weren't loaded
     *
     */
    public String createNewGui(Nifty nifty, File filename) throws ParserConfigurationException, IOException, SAXException, NoProductException, Exception {
        GUIReader reader = new GUIReader(NiftyGUIFactory.getInstance());
        String res = "";

        // Load the XML file and report errors
        this.gui = reader.readGUI(filename);
        res = reader.getTagNotLoaded();
        this.writer = new GUIWriter(gui);
        // Show only the top screen
        GScreen screen = this.getGui().getTopScreen();

        //FIXME: Let's Nifty do its job!
        /*
         for (String sel : nifty.getAllScreensName()) {
         nifty.removeScreen(sel);
         }
         */
        nifty.fromXml("" + this.getGui(), writer.getDocumentStream(), screen.getID());

        reloadAfterFresh();

        // Refresh and update chains
        currentL = gui.getTopLayer();
        currentS = gui.getTopScreen();

        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.NEW, screen));
        this.clearChanged();
        //FIXME: Setup selection mechanism
        //setupNetbeanSelection();

        return res;

    }

    public void saveGui(String filename) throws FileNotFoundException {
        writer.writeGUI(filename);
    }

    /**
     * Refresh the current gui . It causes a call to nifty.fromXml method.
     *
     * @param nifty
     * @throws Exception if nifty can't load the old gui
     */
    public void refresh(Nifty nifty) throws Exception {
        if (getGui() != null) {
            String screenID = this.currentS.getID();

            for (String sel : nifty.getAllScreensName()) {
                nifty.removeScreen(sel);
            }
            nifty.fromXml("" + this.getGui(), writer.getDocumentStream(), screenID);

            //FIXME: Change the reload mechanism
            this.reloadAfterFresh();
        }
    }

    /**
     *
     * @return true if there's no gui in the editor
     */
    public boolean isFirstGui() {
        return getGui() == null;
    }

    /**
     * Select an element in x and y screen coordinates
     *
     * @param x
     * @param y
     */
    public void selectElement(int x, int y) {
        this.selectedElement = findElement(new Point(x, y));
        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.SEL, this.selectedElement));
        this.clearChanged();
    }

    /**
     * Select a specific element
     *
     * @param targetElement GElement to select
     */
    public void selectElement(GElement targetElement) {

        if (targetElement.getType().equals(GUITypes.SCREEN)) {
            if (this.currentS != targetElement) {
                this.currentS = (GScreen) targetElement;
                this.currentLayers.clear();
                for (GElement lay : currentS.getElements()) {
                    this.currentLayers.add((GLayer) lay);
                }
                this.currentL = this.currentLayers.peekLast();

                // Change screen to current selected screen
                this.getGui().goTo(currentS);
            }

        } else if (targetElement.getType().equals(GUITypes.LAYER)) {
            this.currentL = (GLayer) targetElement;
        }

        this.selectedElement = targetElement;

        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.SEL, targetElement));
        this.clearChanged();
    }

    /**
     * Find the first gui element by its id
     *
     * @param id id attribute
     * @return null if there's no Element with that id
     */
    public GElement findElement(String id) {
        GElement res = null;
        for (GScreen screen : this.gui.getScreens()) {
            if (screen.getID().equals(id)) {
                return screen;
            }
            for (GElement layer : screen.getElements()) {
                if (layer.getID().equals(id)) {
                    return layer;
                }
                for (GElement ele : this.gui.getAllChild(layer)) {
                    if (ele.getID().equals(id)) {
                        return ele;
                    }
                }
            }
        }
        return res;
    }

    /**
     * Add an Element in a specific position
     *
     * @param e element to add
     * @param mouse mouse position or screen position
     *
     */
    public void addElement(GElement e, Point2D mouse) {
        if (e.getType().equals(GUITypes.SCREEN)) {
            this.currentS = (GScreen) e;
            this.currentLayers.clear();
            for (GElement lay : currentS.getElements()) {
                this.currentLayers.add((GLayer) lay);
            }
            this.getGui().addScreen(currentS);
            this.getGui().goTo(currentS);
        } else if (e.getType().equals(GUITypes.LAYER)) {
            Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "It's Layer");
            if (this.currentS != null) {
                this.getGui().addElement(e, this.currentS);
            } else {
                Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "No screen");
                throw new IllegalDropException("No screen!");
            }
            GLayer temp = (GLayer) e;
            this.currentL = temp;
            this.currentLayers.add(temp);
        } else {
            Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "It's Element" + e.toString());
            if (this.currentLayers.isEmpty()) {
                Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "No layer to drop in");
                throw new IllegalDropException("No layer to drop in");
            }

            if (currentL.contains(mouse)) {
                GElement result = findElement(mouse);

                this.getGui().addElement(e, result);

                String layout = result.getAttribute("childLayout");

                if (layout.equals("absolute")) {
                    int parentX = result.getNiftyElement().getX();
                    int parentY = result.getNiftyElement().getY();
                    e.addAttribute("x", "" + (int) (mouse.getX() - parentX));
                    e.addAttribute("y", "" + (int) (mouse.getY() - parentY));

                    //FIXME: Trigger the refresh operartion in the tree
                    e.refresh();

                } else {
                    Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "Layout should be Absolute instead of " + layout);
                }


            } else {
                Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "No layer contained Mouse to drop in");
            }
        }
        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.ADD, e));
        this.clearChanged();
        e.getParent().getNiftyElement().layoutElements();

        rootContext.refresh();
    }

    public void addElement(GUITypes t) {
        addElement(t, new Point(0, 0));
    }

    public void addElement(GUITypes t, Point2D mouse) {
        GElement newGElement = NiftyGUIFactory.getInstance().newGElement(t);
        this.addElement(newGElement, mouse);
    }

    public GScreen getCurrentScreen() {
        return this.currentS;
    }

    public GElement getSelected() {
        return this.selectedElement;
    }

    public void removeSelected() {
        this.getGui().removeElement(selectedElement);
        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.DEL, selectedElement));
        this.clearChanged();

    }

    public void reloadAfterFresh() {
        this.getGui().reloadAfterFresh();
    }

    public ElementEditor getElementEditor() {
        elementEditor.setEdited(selectedElement);
        return elementEditor;
    }

    public ElementEditor getElementEditor(GElement toEdit) {
        elementEditor.setEdited(toEdit);
        return elementEditor;
    }

    /**
     * move an element in points coordinates.
     *
     * @param to
     * @param from
     */
    public void move(Point2D to, GElement from) {
        if (from.getType().equals("" + GUITypes.LAYER)) {
            return;
        }
        GElement ele = findElement(to);
        if (ele.equals(from)) {
            getGui().move(to, ele.getParent(), from);
        } else {
            getGui().move(to, ele, from);
        }
        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.MOV, from));
        this.clearChanged();
    }

    /**
     * @return the gui
     */
    public AbstractGUI getGui() {
        return gui;
    }

    /**
     * Find the upper visible element in screen coordinates
     *
     * @param point screen coordinate
     * @return the upper element or if there's no one the upper layer visible
     */
    public GElement findElement(Point2D point) {
        LinkedList<GElement> res = new LinkedList<GElement>();

        for (GElement ele : this.gui.getAllChild(currentL)) {
            if (ele.contains(point)) {
                res.add(ele);
            }
        }

        GElement result = currentL;
        Rectangle minArea = currentS.getBounds();

        while (!res.isEmpty()) {
            GElement temp = res.pop();
            Rectangle area = temp.getBounds();
            if (area.width <= minArea.width && area.height <= minArea.height) {
                result = temp;
                minArea = area;
            }
        }
        return result;
    }

    /**
     * Notify all observers that an element it's been updated
     *
     * @param sel updated gui element
     */
    public void fireUpdate(GElement sel) {
        this.setChanged();
        this.notifyObservers(new SimpleNiftyEditorEvent(SimpleNiftyEditorEvent.UPDATE, sel));
        this.clearChanged();
    }

    public Node getRootContext() {
        return rootContext;
    }

    /*
     public void loadNodes(NiftyGuiDataObject niftyObject) {
        
     InputStream stream = null;
     try {
     stream = niftyObject.getPrimaryFile().getInputStream();
     //document = XMLUtil.parse(new InputSource(stream), false, false, this, null);
     //rootContext = new NiftyNode(document.getDocumentElement());

     //manager.setRootContext(rootContext);
     } catch (Exception ex) {
     //            Message msg = new NotifyDescriptor.Message(
     //                    "Error parsing File:" + ex,
     //                    NotifyDescriptor.ERROR_MESSAGE);
     //  DialogDisplayer.getDefault().notifyLater(msg);
     Exceptions.printStackTrace(ex);
     // return;
     } finally {
     try {
     if (stream != null) {
     stream.close();
     }
     } catch (IOException ex) {
     Exceptions.printStackTrace(ex);
     }
     }
     }
     */
    public void loadNodes(AbstractGUI gui) {
        //FIXME: Determinate real rootNode
        //rootContext = new NiftyDocNode(gui);

    }

    public void buildNodeTree() {
    }

    public void setNodeContext() {
        this.content.add(this.rootContext);
    }

    public void bindNodeLookup(InstanceContent ic) {
        this.content = (InstanceContent) ic;
    }
    /* File managemnet */

    public void loadFile(FileObject fileObj, Nifty nifty) {
        try {
            createNewGui(nifty, FileUtil.toFile(fileObj));
            loadNodes(gui);
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoProductException ex) {
            Exceptions.printStackTrace(ex);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void loadFile(Object filePath) {

        String fileName = null;
        InputStream openStream = null;
        try {
            if (filePath instanceof String) {

                java.net.URL fileURL = getClass().getClassLoader().getResource((String) filePath);
                if (fileURL == null) {
                    fileURL = getClass().getClassLoader().getResource((String) defaultFilePath);
                }
                fileName = fileURL.getFile();
                openStream = fileURL.openStream();
            } else if (filePath instanceof File) {
                fileName = ((File) filePath).getName();
                openStream = new FileInputStream((File) filePath);
            } else if (filePath instanceof FileObject) {
                fileName = ((FileObject) filePath).getName();
                openStream = ((FileObject) filePath).getInputStream();
            }

        } catch (IOException ex) {
            Logger.getLogger(J2DNiftyView.class.getName()).severe("Load " + filePath + " failed !");
            Logger.getLogger(J2DNiftyView.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
        }
        /*
         nifty.loadStyleFile("nifty-default-styles.xml");
         nifty.loadControlFile("nifty-default-controls.xml");

         //nifty.fromXmlWithoutStartScreen(fileName, openStream);
         nifty.fromXml(fileName, openStream, "start");
         nifty.resolutionChanged();
         */


    }

//    public void setNiftyDebug(boolean b) {
//        gui.getNifty().setDebugOptionPanelColors(b);
//    }
//
//    public void toogleNiftyDebug() {
//        gui.getNifty().setDebugOptionPanelColors(!gui.getNifty().isDebugOptionPanelColors());
//
//        //FIXME: Remove this debug 
//        // Dumb nifty tree
//
//        // Dumb GTree
//
//        // Dumb 
//    }

    /* Thread management */
    public void startRenderThread() {
        if (renderThread == null) {
            renderThread = new Thread() {
                public void run() {
                    Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "Start render thread");
                    viewContainer.start();
                }
            };
            renderThread.start();
        } else {
            renderThread.resume();
        }
    }

    public void pauseRenderThread() {
        renderThread.suspend();
    }

    public void stopRenderThread() {
        try {
            //FIXME: The thread will be automaticly stop because Nifty stop to render.
            //viewPanel.stop();
            renderThread.stop();

        } catch (ThreadDeath ex) {
        }
        renderThread = null;
    }

    // SETTER & GETTER
    public GUISelectionListener getGuiSelectionListener() {
        return guiSelectionListener;
    }

    public void setViewContainer(J2DNiftyViewContainer viewPanel) {
        this.viewContainer = viewPanel;
    }

    public J2DNiftyViewContainer getViewContainer() {
        return viewContainer;
    }

    public J2DNiftyView getNiftyView() {
        return viewContainer.getNiftyView();
    }

    public void bindSelectionView(J2DNiftyView view, JPopupMenu popUp) {
        guiSelectionListener = new GUISelectionListener(this, popUp, view);
    }
}
