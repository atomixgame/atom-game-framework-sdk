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
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.nodes.PropertySupport;
import org.openide.util.Exceptions;

/**
 * KeyFrame represent a property's value in a given point int time.
 * It also provides functionality to edit itself via triggerEditor() method.
 * Subclasses should define it's type parameter, for most basic types a default
 * editor should be provided thanks to PropertySupport.Reflection, if a custom
 * editor is desired just set it in the 'property' field.
 * @author tomas
 */
public abstract class KeyFrame<T> implements ActionListener {

    // Color to represent KeyFrames in TimelinePanel
    protected static Color color = Color.red;
    // Shape to represent KeyFrames in TimelinePanel (Triangle)
    protected static final Polygon KEY_FRAME_SHAPE = new Polygon(new int[]{0, 10, -10}, new int[]{0, 10, 10}, 3);
    // Time value were this KeyFrame exists
    protected float time;
    // Utility to handle property value modification
    protected PropertySupport.Reflection<T> property;
    // Position in TimelinePanel canvas
    protected int x, y;
    // Wether this keyframe is currently selected or not
    protected boolean selected;

    public KeyFrame() {

        // Hardcore generic reflection stuff just to instantiate the PropertySupport
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Class<T> tClass = (Class<T>) paramType.getActualTypeArguments()[0];
            try {
                property = new PropertySupport.Reflection<T>(this, tClass, "value");
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            throw new InstantiationError("Attempt to create parameterless KeyFrame");
        }
    }

    public void paint(Graphics g) {
        // Draw in TimelinePanel canvas, shape is shared so it is always returned to position
        g.setColor(color);
        KEY_FRAME_SHAPE.translate(x, y);
        g.fillPolygon(KEY_FRAME_SHAPE);
        if (selected) {
            g.setColor(Color.black);
            g.drawPolygon(KEY_FRAME_SHAPE);
        }
        KEY_FRAME_SHAPE.translate(-x, -y);
    }

    public boolean isPointInside(Point point) {
        point.translate(-x, -y);
        return KEY_FRAME_SHAPE.contains(point);
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
    public abstract T getValue();

    public abstract void setValue(T value);

    @Override
    public String toString() {
        return "KeyFrame<" + property.getValueType().getName() + ">[" + getValue().toString() + "]";
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
        x = (int) (TimelineManager.BAR_SEPARATION * time);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
