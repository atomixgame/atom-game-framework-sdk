/*
 *  Copyright (c) 2009-2010 jMonkeyEngine
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.gde.cinematics.timeline;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.nodes.PropertySupport;
import org.openide.util.Exceptions;

/**
 *
 * @author tomas
 */
public abstract class Timeline<T> implements ActionListener {

    protected static Color SELECTED_COLOR = new Color(191, 105, 186, 180);
    protected String name;
    protected ArrayList<TimelineProperty> properties = new ArrayList<TimelineProperty>();
    protected boolean selected = false;
    protected ArrayList<TimelinePropertyListener> listeners = new ArrayList<TimelinePropertyListener>();
    // Data and datatype this Timline represents
    protected T object;
    protected PropertySupport.Reflection<T> property;

    public Timeline() {
        // Hardcore generic reflection stuff just to instantiate the PropertySupport
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            @SuppressWarnings("unchecked")
            Class<T> tClass = (Class<T>) paramType.getActualTypeArguments()[0];
            try {
                property = new PropertySupport.Reflection<T>(this, tClass, "object");
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            throw new InstantiationError("Attempt to create parameterless Timeline");
        }
    }

    public void triggerEditor() {
        PropertyEditor propertyEditor = property.getPropertyEditor();
        try {
            if (property.getValue() != null) {
                propertyEditor.setValue(property.getValue());
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

        DialogDescriptor dialogDescriptor = new DialogDescriptor(propertyEditor.getCustomEditor(), property.getDisplayName(), true, this);
        DialogDisplayer.getDefault().notifyLater(dialogDescriptor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != DialogDescriptor.CANCEL_OPTION) {
            try {
                property.setValue((T) property.getPropertyEditor().getValue());
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    // Had to make these abstract and implement in subclass because of a NoSuchMethodException,
    // perhaps because it is inside the constructor and the child doesn't have these methods yet?
    public abstract T getObject();

    public abstract void setObject(T object);

    public boolean addTimelinePropertyListener(TimelinePropertyListener listener) {
        return listeners.add(listener);
    }

    public boolean removeTimelinePropertyListener(TimelinePropertyListener listener) {
        return listeners.remove(listener);
    }

    public void clearTimelinePropertyListeners() {
        listeners.clear();
    }

    public void fireAddedProperty(TimelineProperty property) {
        for (TimelinePropertyListener timelinePropertyListener : listeners) {
            timelinePropertyListener.addedTimelineProperty(this, property);
        }
    }

    public void fireRemovedProperty(TimelineProperty property) {
        for (TimelinePropertyListener timelinePropertyListener : listeners) {
            timelinePropertyListener.removedTimelineProperty(this, property);
        }
    }

    public void paintTimelinePanel(Graphics g, Rectangle rect) {
        Graphics2D g2 = (Graphics2D) g;

        if (selected) {
            g2.setColor(SELECTED_COLOR);
            g2.fill(rect);
        }
        g2.setColor(Color.black);
        g2.draw(rect);
    }

    public boolean remove(TimelineProperty o) {
        return properties.remove(o);
    }

    public boolean add(TimelineProperty e) {
        return properties.add(e);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<TimelineProperty> getPropertiesList() {
        return Collections.unmodifiableList(properties);
    }

    List<TimelineProperty> getProperties() {
        return properties;
    }

    abstract void reloadControls();
}
