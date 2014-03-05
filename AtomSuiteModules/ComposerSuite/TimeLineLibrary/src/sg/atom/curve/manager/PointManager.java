package sg.atom.curve.manager;

// File:    PointManager.java
import sg.atom.curve.ControlPoint;
import java.awt.*;
import java.util.*;

/**
 * Manages all the ControlPoints. *
 */
public class PointManager {

    protected Vector points = new Vector();
    protected ControlPoint activePoint;

    protected Rectangle zone(ControlPoint cp) {
        return new Rectangle(cp.getPosition().x - 7,
                cp.getPosition().y - 7,
                15, 15);
    }

    /**
     * Adds a point to the manager. *
     */
    public void addPoint(ControlPoint cp) {
        points.addElement(cp);
    }

    /**
     * Sets one point active. *
     */
    public void activatePoint(ControlPoint activePoint) {
        this.activePoint = activePoint;
    }

    /**
     * Sets the position of the active point. *
     */
    public void setActivePoint(Point p) {
        activePoint.setPosition(p);
    }

    /**
     * Checks whether a given point corresponds with a control point. *
     */
    public boolean isControlPoint(Point p) {
        Enumeration list = points.elements();
        while (list.hasMoreElements()) {
            if (zone((ControlPoint) list.nextElement()).inside(p.x, p.y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the ControlPoint correspondint to a point. *
     */
    public ControlPoint getControlPoint(Point p) {
        Enumeration list = points.elements();
        ControlPoint cp;
        while (list.hasMoreElements()) {
            cp = (ControlPoint) list.nextElement();
            if (zone(cp).inside(p.x, p.y)) {
                return cp;
            }
        }
        return null;
    }

    protected void drawPoint(Graphics g, ControlPoint cp) {
        int x = cp.getPosition().x;
        int y = cp.getPosition().y;
        g.setColor(Color.white);
        g.fillRect(x - 3, y - 3, 5, 5);
        g.setColor(Color.black);
        g.drawRect(x - 3, y - 3, 5, 5);
    }

    /**
     * Draws all the points. *
     */
    public void paint(Graphics g) {
        Enumeration list = points.elements();

        while (list.hasMoreElements()) {
            drawPoint(g, (ControlPoint) list.nextElement());
        }
    }
}
