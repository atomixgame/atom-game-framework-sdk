package sg.atom.curve;

// File:    ControlPoint.java
import java.awt.*;
import java.util.*;

/**
 * One of the control points of a curve. Can be made to change its position in
 * relation to other points, among other things to preserve continuity. *
 */
public class ControlPoint extends Observable implements Observer {

    protected Point position;
    // End point connected to this control point:
    protected ControlPoint anchor;
    // The relative position of this point to the anchor:
    protected Point deltaAnchor;
    // Nearest control point on the "other side" of the anchor:
    protected ControlPoint controller;

    public ControlPoint(Point p) {
        super();
        position = p;
    }
    
    public void setAnchor(ControlPoint anchor) {
        this.anchor = anchor;
        this.anchor.addObserver(this);
        deltaAnchor = new Point(position.x - anchor.getPosition().x,
                position.y - anchor.getPosition().y);
    }

    public void setController(ControlPoint controller) {
        this.controller = controller;
        this.controller.addObserver(this);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        if (anchor != null) {
            deltaAnchor = new Point(position.x - anchor.getPosition().x,
                    position.y - anchor.getPosition().y);
        }
        setChanged();
        notifyObservers(position);
    }


    /**
     * Change position relative to other points. *
     */
    public void update(Observable obs, Object arg) {
        Point newPos = ((Point) arg);
        if (obs == anchor) {
            position.x = newPos.x + deltaAnchor.x;
            position.y = newPos.y + deltaAnchor.y;
        } else if (obs == controller) {
            double radius1 = Math.sqrt(Math.pow(deltaAnchor.x, 2)
                    + Math.pow(deltaAnchor.y, 2));
            int x = newPos.x - anchor.getPosition().x;
            int y = newPos.y - anchor.getPosition().y;
            float radius2 = (float) Math.sqrt(Math.pow(x, 2)
                    + Math.pow(y, 2));
            float ratio = (float) radius1 / radius2;
            position.x = anchor.getPosition().x - Math.round(x * ratio);
            position.y = anchor.getPosition().y - Math.round(y * ratio);

            if (anchor != null) {
                deltaAnchor = new Point(position.x - anchor.getPosition().x,
                        position.y - anchor.getPosition().y);
            }

        }
        //setChanged();
        //notifyObservers(position);
    }
}
