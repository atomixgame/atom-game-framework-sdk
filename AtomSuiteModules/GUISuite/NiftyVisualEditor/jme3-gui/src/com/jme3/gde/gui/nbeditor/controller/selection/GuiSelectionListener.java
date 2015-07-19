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
package com.jme3.gde.gui.nbeditor.controller.selection;

import com.jme3.gde.gui.base.model.AbstractGUI;
import com.jme3.gde.gui.services.niftygui.java2d.J2DNiftyView;
import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import com.jme3.gde.gui.services.niftygui.events.SimpleNiftyEditorEvent;
import com.jme3.gde.gui.services.niftygui.NiftyGUI;
import de.lessvoid.nifty.layout.align.HorizontalAlign;
import de.lessvoid.nifty.layout.align.VerticalAlign;
import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.base.model.elements.GElement;
import com.jme3.gde.gui.base.model.elements.GScreen;
import de.lessvoid.nifty.elements.Element;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 * GuiSelectionListener handle all the Selection related
 *
 * @author cris
 */
public class GUISelectionListener extends MouseAdapter implements ActionListener, Observer, KeyListener {

    private final byte DIR_N = 0;
    private final byte DIR_E = 1;
    private final byte DIR_S = 2;
    private final byte DIR_W = 3;
    private final byte DIR_SE = 4;
    private final byte NOP = -1;
    private byte curDir;
    private GUIEditor guiEditor;
    private J2DNiftyView view;
    // Timing
    private Timer hold;
    private MouseEvent toServe;
    // Popup
    private JPopupMenu p;
    //State
    private boolean enable;
    private Rectangle selectedRect;
    private boolean selecting;
    private boolean dragging;
    // Netbean bridge 
    private Lookup.Result globalLookUpResult = Utilities.actionsGlobalContext().lookupResult(GElement.class);
    //Suggest & Snap
    ArrayList<Integer> suggestedValue;
    Element dummyElement;
    Rectangle dummyRect;

    public GUISelectionListener(GUIEditor currentGUI) {
        this.guiEditor = currentGUI;
        enable = true;
        setupNetbeanSelection();
    }

    public GUISelectionListener(GUIEditor currentGUI, JPopupMenu po, J2DNiftyView view) {
        this.guiEditor = currentGUI;
        enable = true;
        this.p = po;
        this.view = view;
        this.selectedRect = new Rectangle();
        setupNetbeanSelection();
    }

    public final void startDrag(MouseEvent e) {
        if (enable) {
            //JPanel c = (JPanel) e.getComponent().getParent();
            this.guiEditor.selectElement(e.getX(), e.getY());

            /*
             TransferHandler handler = c.getTransferHandler();
             handler.exportAsDrag(c, e, TransferHandler.MOVE);
             */
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (enable) {
            toServe = e;
            //   this.gui.selectElement(e.getX(), e.getY());
            hold = new Timer(225, this);
            hold.setRepeats(false);
            hold.start();

        }
        if (e.isPopupTrigger()) {
            this.guiEditor.selectElement(e.getX(), e.getY());
            this.p.show(e.getComponent(), e.getX(), e.getY());
        }

    }

    public void mouseReleased(MouseEvent e) {
        if (enable) {
            if (hold != null) {
                hold.stop();
            }
        }

        if (e.isPopupTrigger()) {
            this.guiEditor.selectElement(e.getX(), e.getY());
            this.p.show(e.getComponent(), e.getX(), e.getY());
        }

        if (dragging) {
            GElement sel = this.guiEditor.getSelected();
            if (sel != null && this.selectedRect != null) {
                sel.lightRefresh();
                this.selectedRect.setRect(sel.getBounds());
                this.enable();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.guiEditor.selectElement(e.getX(), e.getY());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.toServe != null) {
            this.startDrag(toServe);
        }
        toServe = null;

    }

    public void disable() {
        this.enable = false;
    }

    public void enable() {
        this.enable = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (this.selecting) {
            this.disable();

            if (e.getX() > selectedRect.getMaxX() - 5 && e.getX() < selectedRect.getMaxX() + 5
                    && e.getY() > selectedRect.getMaxY() - 5
                    && e.getY() < selectedRect.getMaxY() + 5) {
                setCursor(e.getComponent(), Cursor.SE_RESIZE_CURSOR);
                curDir = DIR_SE;

            } else if (e.getX() == selectedRect.getMinX() && (e.getY() < selectedRect.getMaxY() && e.getY() > selectedRect.getMinY())) {
                setCursor(e.getComponent(), Cursor.W_RESIZE_CURSOR);
                curDir = DIR_W;
            } else if (e.getX() == selectedRect.getMaxX() && (e.getY() < selectedRect.getMaxY() && e.getY() > selectedRect.getMinY())) {
                setCursor(e.getComponent(), Cursor.E_RESIZE_CURSOR);
                curDir = DIR_E;
            } else if (e.getY() < selectedRect.getMaxY() + 5 && e.getY() > selectedRect.getMaxY() - 5
                    && (e.getX() < selectedRect.getMaxX() && e.getX() > selectedRect.getMinX())) {
                setCursor(e.getComponent(), Cursor.S_RESIZE_CURSOR);
                curDir = DIR_S;
            } else if (e.getY() == selectedRect.getMinY() && (e.getX() < selectedRect.getMaxX() && e.getX() > selectedRect.getMinX())) {
                curDir = DIR_N;
                setCursor(e.getComponent(), Cursor.N_RESIZE_CURSOR);
            } else if (e.getY() < selectedRect.getCenterY() + 10
                    && e.getY() > selectedRect.getCenterY() - 10 && (e.getX() < (selectedRect.getCenterX() + 10) && e.getX() > selectedRect.getCenterX() - 10)) {
                setCursor(e.getComponent(), Cursor.MOVE_CURSOR);
                this.enable();
                curDir = NOP;
            } else {
                setCursor(e.getComponent(), Cursor.DEFAULT_CURSOR);
                curDir = NOP;
            }
        } else {
            setCursor(e.getComponent(), Cursor.DEFAULT_CURSOR);
            curDir = NOP;
        }

    }

    public void setCursor(Component c, int type) {
        c.setCursor(new Cursor(type));
    }

    public void directElementModification(MouseEvent e, String attribute) {//,int intValue,String value){
        GElement sel = guiEditor.getSelected();
        if (attribute.equals("width")) {
            sel.getNiftyElement().setWidth(selectedRect.width);
            sel.addAttribute(attribute, "" + selectedRect.width + "px");
        } else if (attribute.equals("height")) {
            sel.getNiftyElement().setHeight(selectedRect.height);
            sel.addAttribute("height", "" + selectedRect.height + "px");
        } else if (attribute.equals("widthx")) {
            sel.addAttribute("width", "" + selectedRect.width + "px");
            if (sel.getParent().getAttribute("childLayout").equals("absolute")) {
                int x = sel.getParent().getNiftyElement().getX();
                sel.addAttribute("x", "" + (e.getX() - x) + "px");
            }
        } else if (attribute.equals("widthheight")) {

            sel.getNiftyElement().setHeight(selectedRect.height);
            sel.addAttribute("height", "" + selectedRect.height + "px");
            sel.getNiftyElement().setWidth(selectedRect.width);
            sel.addAttribute("width", "" + selectedRect.width + "px");
        } else if (attribute.equals("heighty")) {
            sel.addAttribute("height", "" + selectedRect.height + "px");
            if (sel.getParent().getAttribute("childLayout").equals("absolute")) {
                int y = sel.getParent().getNiftyElement().getY();
                sel.addAttribute("y", "" + (e.getY() - y) + "px");
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.selecting) {
            int to;
            this.dragging = true;

            //suggest and snap
            collectSuggestionValues();
            switch (curDir) {
                case DIR_E:
                    to = (int) (e.getX() - this.selectedRect.getMaxX());
                    if ((this.selectedRect.width + to) > 0) {
                        this.selectedRect.width += to;

                        directElementModification(e, "width");
                    }
                    break;
                case DIR_W:
                    to = (int) (this.selectedRect.getMinX() - e.getX());
                    if ((this.selectedRect.width + to) > 0) {
                        this.selectedRect.x = e.getX();
                        this.selectedRect.width += to;

                        directElementModification(e, "widthx");
                    }
                    break;
                case DIR_S:
                    to = (int) (e.getY() - this.selectedRect.getMaxY());
                    if ((this.selectedRect.height + to) > 0) {
                        this.selectedRect.height += to;
                        directElementModification(e, "height");
                    }
                    break;
                case DIR_SE:
                    to = (int) (e.getX() - this.selectedRect.getMaxX());
                    int toy = (int) (e.getY() - this.selectedRect.getMaxY());
                    if (((this.selectedRect.width + to) > 0) && (this.selectedRect.height + to) > 0) {
                        if (e.isControlDown()) {
                            this.selectedRect.height += to;
                        } else {
                            this.selectedRect.height += toy;
                        }
                        this.selectedRect.width += to;
                        //FIXME: Changed!
                        /*
                         if (e.isControlDown()) {
                         Point gtry = new Point((int) selectedRect.getMaxX(), (int) selectedRect.getMaxY());
                         //  SwingUtilities.convertPointToScreen(gtry, this);
                         // mouseBot.mouseMove(gtry.x,gtry.y);
                         }
                         */
                        directElementModification(e, "widthheight");
                    }
                    break;
                case DIR_N:
                    to = (int) (this.selectedRect.getMinY() - e.getY());
                    if ((this.selectedRect.height + to) > 0) {
                        this.selectedRect.height += to;
                        this.selectedRect.y = e.getY();


                        directElementModification(e, "heighty");
                    }
                    break;
                default:
                    dragging = false;


            }
            view.displayRect(selectedRect.x, selectedRect.y, selectedRect.height, selectedRect.width);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        SimpleNiftyEditorEvent act = (SimpleNiftyEditorEvent) arg;
        if (act.getType() == SimpleNiftyEditorEvent.SEL && !act.getGUIElement().getType().equals(GUITypes.LAYER)) {
            this.selectedRect.setBounds(act.getGUIElement().getBounds());
            this.selecting = true;
        } else if (act.getType() == SimpleNiftyEditorEvent.NEW) {
            this.guiEditor = (((GUIEditor) o));
            this.selecting = false;
        } else {
            this.selecting = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        GElement sel = guiEditor.getSelected();
        String layout = sel.getParent().getAttribute("childLayout");

        if (layout.equals("horizontal")) {
            horizontalBehavior(sel, e.getKeyCode());
        } else if (layout.equals("vertical")) {
            verticalBehavior(sel, e.getKeyCode());
        } else {
            absoluteBehavior(sel, e.getKeyCode());
        }
        this.guiEditor.fireUpdate(sel);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    private void absoluteBehavior(GElement sel, int key) {
        int x = sel.getNiftyElement().getX();
        int y = sel.getNiftyElement().getY();

        if (key == KeyEvent.VK_DOWN) {
            y++;
            sel.addAttribute("y", "" + y);
            sel.lightRefresh();
        } else if (key == KeyEvent.VK_UP) {
            y--;
            sel.addAttribute("y", "" + y);
            sel.lightRefresh();
        } else if (key == KeyEvent.VK_LEFT) {
            x--;
            sel.addAttribute("x", "" + x);
            sel.lightRefresh();
        } else if (key == KeyEvent.VK_RIGHT) {
            x++;
            sel.addAttribute("x", "" + x);
            sel.lightRefresh();
        }
        view.displayRect(x, y, sel.getNiftyElement().getHeight(), sel.getNiftyElement().getWidth());
        this.selectedRect.setRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(), sel.getNiftyElement().getWidth(), sel.getNiftyElement().getHeight());
    }

    private void horizontalBehavior(GElement sel, int key) {
        VerticalAlign current = null;
        int index = 0;
        try {
            current = VerticalAlign.valueOf(sel.getAttribute("valign"));
            index = current.ordinal();
        } catch (IllegalArgumentException e) {
            current = VerticalAlign.center;
            index = 1;
        }

        if (key == KeyEvent.VK_UP && index > 1) {
            index--;
            VerticalAlign newAlign = VerticalAlign.values()[index];
            sel.addAttribute("valign", newAlign.name());
            sel.lightRefresh();
        } else if (key == KeyEvent.VK_DOWN && index < VerticalAlign.values().length) {
            index++;
            VerticalAlign newAlign = VerticalAlign.values()[index];
            sel.addAttribute("valign", newAlign.name());
            sel.lightRefresh();
        } else {
            index = sel.getParent().getNiftyElement().getElements().indexOf(sel.getNiftyElement());
            if (key == KeyEvent.VK_RIGHT && ++index < sel.getParent().getNiftyElement().getElements().size()) {

                sel.setIndex(index);
                sel.lightRefresh();
            } else if (key == KeyEvent.VK_LEFT && --index >= 0) {
                sel.setIndex(index);
                sel.lightRefresh();
            }
        }
        view.displayRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(), sel.getNiftyElement().getHeight(), sel.getNiftyElement().getWidth());
        this.selectedRect.setRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(), sel.getNiftyElement().getWidth(), sel.getNiftyElement().getHeight());
    }

    private void verticalBehavior(GElement sel, int key) {
        HorizontalAlign current = null;
        int index = 0;
        try {
            current = HorizontalAlign.valueOf(sel.getAttribute("align"));
            index = current.ordinal();
        } catch (IllegalArgumentException e) {
            current = HorizontalAlign.center;
            index = 1;
        }

        if (key == KeyEvent.VK_LEFT && index > 1) {
            index--;
            HorizontalAlign newAlign = HorizontalAlign.values()[index];
            sel.addAttribute("align", newAlign.name());
            sel.lightRefresh();
        } else if (key == KeyEvent.VK_RIGHT && index < HorizontalAlign.values().length) {
            index++;
            HorizontalAlign newAlign = HorizontalAlign.values()[index];
            sel.addAttribute("align", newAlign.name());
            sel.lightRefresh();
        } else {
            index = sel.getParent().getNiftyElement().getElements().indexOf(sel.getNiftyElement());
            if (key == KeyEvent.VK_DOWN && ++index < sel.getParent().getNiftyElement().getElements().size()) {
                sel.setIndex(index);
                sel.lightRefresh();
            } else if (key == KeyEvent.VK_UP && --index >= 0) {
                sel.setIndex(index);
                sel.lightRefresh();
            }
        }
        view.displayRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(), sel.getNiftyElement().getHeight(), sel.getNiftyElement().getWidth());
        this.selectedRect.setRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(), sel.getNiftyElement().getWidth(), sel.getNiftyElement().getHeight());
    }

    /*
     @Override
     public void valueChanged(TreeSelectionEvent e) {
     TreePath path = e.getNewLeadSelectionPath();
     if (path != null) {
     DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
     if (!node.isRoot()) {
     this.gui.selectElement((GElement) node.getUserObject());
     }
     }
     }
     */
    public void collectSuggestionValues() {
        GElement sel = guiEditor.getSelected();
        AbstractGUI gui = guiEditor.getGui();
        suggestedValue.clear();
        for (GScreen screen : gui.getScreens()) {
            for (GElement layer : screen.getElements()) {
                for (GElement ele : gui.getAllChild(layer)) {
                    Rectangle bounds = ele.getBounds();
                    suggestedValue.add(bounds.x);
                    suggestedValue.add(bounds.x + bounds.width);
                }
            }
        }
        /*
         if (true){//sel.getAttribute("")) {
            
         }
         */
    }

    public void setupNetbeanSelection() {
        //You must hold a reference to your Lookup.Result as long as you are interested 
        //in changes in it, or it will be garbage collected and you will stop getting 
        //notifications

        globalLookUpResult.addLookupListener(new LookupListener() {
            public void resultChanged(LookupEvent evt) {
                Collection c = ((Lookup.Result) evt.getSource()).allInstances();
                //do something with the collection of 0 or more instances - the collection has changed
                Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "Selection change" + c.size());

                // Select only the first one!
                if (c.iterator().hasNext()) {
                    Object obj = c.iterator().next();
                    if (obj instanceof GElement) {
                        final GElement el = (GElement) obj;

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                selectElement(el);
                            }
                        });

                    } else {
                    }
                }

            }
        });
    }

    public GElement getSelected() {
        return guiEditor.getSelected();
    }

    public void selectElement(GElement el) {
        guiEditor.selectElement(el);
        Logger.getLogger(GUIEditor.class.getName()).log(Level.INFO, "Selection change ACT");
    }

    public ArrayList<Integer> getSuggestedValue() {
        return suggestedValue;
    }
}
