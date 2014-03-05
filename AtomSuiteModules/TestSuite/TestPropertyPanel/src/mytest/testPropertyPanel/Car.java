/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package mytest.testPropertyPanel;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author hungcuong
 */
public class Car implements Serializable {

    private String type;
    private int year;
    private final PropertyChangeSupport propertySupport;

    public Car() {
        propertySupport = new PropertyChangeSupport(this);
    }

    Car(String type, int year) {
        this.type = type;
        this.year = year;
        propertySupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    /**
     * Get the value of year
     *
     * @return the value of year
     */
    public int getYear() {
        return year;
    }

    /**
     * Set the value of year
     *
     * @param year new value of year
     */
    public void setYear(int year) {
        int oldValue = year;
        propertySupport.firePropertyChange("type", oldValue, year);
        this.year = year;

    }

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {

        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        String oldValue = type;
        propertySupport.firePropertyChange("type", oldValue, type);
        this.type = type;
    }
}
