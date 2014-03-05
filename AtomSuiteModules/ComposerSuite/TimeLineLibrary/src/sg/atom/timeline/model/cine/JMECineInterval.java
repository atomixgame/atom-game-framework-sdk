/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.timeline.model.cine;

import de.jaret.util.date.Interval;
import de.jaret.util.date.JaretDate;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class JMECineInterval implements Interval{

    @Override
    public void setBegin(JaretDate jd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JaretDate getBegin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEnd(JaretDate jd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JaretDate getEnd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(JaretDate jd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Interval intrvl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSeconds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean intersects(Interval intrvl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
