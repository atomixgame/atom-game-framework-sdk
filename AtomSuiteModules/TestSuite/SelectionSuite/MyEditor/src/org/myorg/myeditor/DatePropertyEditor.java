/*
 * DataPropertyEditor.java
 *
 * Created on Jul 26, 2007, 9:05:44 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.myeditor;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import org.jdesktop.swingx.JXDatePicker;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

/**
 *
 * @author gw152771
 */
public class DatePropertyEditor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {

    public DatePropertyEditor() {
    }

    public String getAsText() {
        Date d = (Date) getValue();
        if (d == null) {
            return "No Date Set";
        }
        return new SimpleDateFormat("MM/dd/yy HH:mm:ss").format(d);
    }

    public void setAsText(String s) {
        try {
            setValue(new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(s));
        } catch (ParseException pe) {
            IllegalArgumentException iae = new IllegalArgumentException("Could not parse date");
            throw iae;
        }
    }

    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }

    private InplaceEditor ed = null;

    public InplaceEditor getInplaceEditor() {
        if (ed == null) {
            ed = new Inplace();
        }
        return ed;
    }

    private static class Inplace implements InplaceEditor {

        private final JXDatePicker picker = new JXDatePicker();
        private PropertyEditor editor = null;

        public void connect(PropertyEditor propertyEditor, PropertyEnv env) {
            editor = propertyEditor;
            reset();
        }

        public JComponent getComponent() {
            return picker;
        }

        public void clear() {
            //avoid memory leaks:
            editor = null;
            model = null;
        }

        public Object getValue() {
            return picker.getDate();
        }

        public void setValue(Object object) {
            picker.setDate((Date) object);
        }

        public boolean supportsTextEntry() {
            return true;
        }

        public void reset() {
            Date d = (Date) editor.getValue();
            if (d != null) {
                picker.setDate(d);
            }
        }

        public KeyStroke[] getKeyStrokes() {
            return new KeyStroke[0];
        }

        public PropertyEditor getPropertyEditor() {
            return editor;
        }

        public PropertyModel getPropertyModel() {
            return model;
        }

        private PropertyModel model;

        public void setPropertyModel(PropertyModel propertyModel) {
            this.model = propertyModel;
        }

        public boolean isKnownComponent(Component component) {
            return component == picker || picker.isAncestorOf(component);
        }

        public void addActionListener(ActionListener actionListener) {
            //do nothing - not needed for this component
        }

        public void removeActionListener(ActionListener actionListener) {
            //do nothing - not needed for this component
        }
    }
}
