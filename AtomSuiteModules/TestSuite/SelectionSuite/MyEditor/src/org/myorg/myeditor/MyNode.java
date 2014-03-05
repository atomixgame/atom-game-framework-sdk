/*
 * MyNode.java
 *
 * Created on Jul 25, 2007, 6:03:41 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.myeditor;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.myorg.myapi.APIObject;
import org.openide.ErrorManager;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;
import org.openide.util.actions.Presenter;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author gw152771
 */
public class MyNode extends AbstractNode implements PropertyChangeListener {

    public MyNode(APIObject obj) {
        super(new MyChildren(), Lookups.singleton(obj));
        setDisplayName("APIObject " + obj.getIndex());
        obj.addPropertyChangeListener(WeakListeners.propertyChange(this, obj));
    }

    public MyNode() {
        super(new MyChildren());
        setDisplayName("Root");
    }

    @Override
    public String getHtmlDisplayName() {
        APIObject obj = getLookup().lookup(APIObject.class);
        if (obj != null) {
            return "<font color='!textText'>APIObject " + obj.getIndex() + "</font>" + "<font color='!controlShadow'><i>" + obj.getDate() + "</i></font>";
        } else {
            return null;
        }
    }

    @Override
    public Image getIcon(int type) {
        return Utilities.loadImage("org/myorg/myeditor/icon.png");
    }

    @Override
    public Image getOpenedIcon(int i) {
        return getIcon(i);
    }

    @Override
    public Action[] getActions(boolean popup) {
        return new Action[]{new MyAction()};
    }

    private class MyAction extends AbstractAction implements Presenter.Popup {

        public MyAction() {
            putValue(NAME, "Do Something");
        }

        public void actionPerformed(ActionEvent e) {
            APIObject obj = getLookup().lookup(APIObject.class);
            JOptionPane.showMessageDialog(null, "Hello from " + obj);
        }

        public JMenuItem getPopupPresenter() {
            JMenu result = new JMenu("Submenu"); //remember JMenu is a subclass of JMenuItem
            result.add(new JMenuItem(this));
            result.add(new JMenuItem(this));
            return result;
        }
    }

        @Override
    @SuppressWarnings("unchecked")
    protected Sheet createSheet() {

        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();

        Sheet.Set set2 = sheet.createPropertiesSet();
        set2.setDisplayName("Other");
        set2.setName("other");

        APIObject obj = getLookup().lookup(APIObject.class);

        try {

            @SuppressWarnings(value = "unchecked")
            Property indexProp = new PropertySupport.Reflection(obj, Integer.class, "getIndex", null);
            @SuppressWarnings(value = "unchecked")
            PropertySupport.Reflection dateProp = new PropertySupport.Reflection(obj, Date.class, "date");
            dateProp.setPropertyEditorClass(DatePropertyEditor.class);

            indexProp.setName("index");
            dateProp.setName("date");

            set.put(indexProp);
            set.put(dateProp);

            set2.put(dateProp);
            set2.setValue("tabName", "Other Tab");
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        sheet.put(set);
        sheet.put(set2);
        return sheet;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            this.fireDisplayNameChange(null, getDisplayName());
        }
    }
}
