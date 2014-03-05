/*
 * APIObject.java
 *
 * Created on Jul 16, 2007, 8:30:53 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.myorg.myapi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gw152771
 */
public final class APIObject {

    private Date date = new Date();
    private static int count = 0;
    private final int index;

    public APIObject() {
        index = count++;
    }

    public Date getDate() {
        return date;
    }

public void setDate(Date d) {
    Date oldDate = date;
    date = d;
    fire("date", oldDate, date);
}
    
    public int getIndex() {
        return index;
    }

    public String toString() {
        return index + " - " + date;
    }

    @SuppressWarnings(value = "unchecked")
    private List listeners = Collections.synchronizedList(new LinkedList());

    @SuppressWarnings(value = "unchecked")
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    private void fire(String propertyName, Object old, Object nue) {
        //Passing 0 below on purpose, so you only synchronize for one atomic call
        @SuppressWarnings(value = "unchecked")
        PropertyChangeListener[] pcls = (PropertyChangeListener[]) listeners.toArray(new PropertyChangeListener[0]);
        for (int i = 0; i < pcls.length; i++) {
            pcls[i].propertyChange(new PropertyChangeEvent(this, propertyName, old, nue));
        }
    }
}
