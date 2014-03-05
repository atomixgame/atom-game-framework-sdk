package sg.atom.curve.manager;

// File:    CurveManager.java
import sg.atom.curve.BezierCurve;
import sg.atom.curve.ControlPoint;
import java.awt.*;
import java.util.*;
import sg.atom.curve.BezierCurveRenderer;

/**
 * Manages all the curves. *
 */
public class CurveManager {

    protected Vector curves = new Vector();
    protected PointManager pointManager;
    protected CurverEditingState state;

    public CurveManager(PointManager pointManager, CurverEditingState state) {
        this.pointManager = pointManager;
        this.state = state;
    }

    /**
     * Returns the curves that contain cp as a control point. *
     */
    public Vector getCurves(ControlPoint cp) {
        Vector temp = new Vector();
        BezierCurve bc;
        Enumeration e = curves.elements();
        while (e.hasMoreElements()) {
            bc = (BezierCurve) e.nextElement();
            if (bc.contains(cp)) {
                temp.addElement(bc);
            }
        }
        return temp;
    }

    /**
     * Create and add a new curve. Also perform any linking with other curves. *
     */
    public void addCurve(Point p0,
            Point p1,
            Point p2,
            Point p3) {

        ControlPoint cp0, cp1, cp2, cp3;

        // The middle two are always new:
        cp1 = new ControlPoint(p1);
        cp2 = new ControlPoint(p2);

        // Potentially link ends with other curves:
        // *** This code has become pretty "hairy"... ***
        // *** Big bug-potential; should be rewritten ***

        // First end point:

        // Check if the point exists:
        if (pointManager.isControlPoint(p0)) {
            ControlPoint cp = pointManager.getControlPoint(p0);

            // Check if this is an endpoint an not a link between two
            // existing curves:
            // (Without this check we could get "forks")
            Vector curveList = getCurves(cp);
            if (curveList.size() == 1) {
                BezierCurve theCurve = (BezierCurve) curveList.elementAt(0);
                int pointIndex = theCurve.getNumber(cp);
                switch (pointIndex) {
                    case 0: {
                        cp0 = cp;
                        // Ensure continuity:
                        ControlPoint temp = theCurve.getPoint(1);
                        cp1.setController(temp);
                        temp.setController(cp1);
                        // Update the continuity:
                        cp1.setPosition(cp1.getPosition());
                        break;
                    }
                    case 1:
                    case 2: {
                        cp0 = new ControlPoint(p0);
                        break;
                    }
                    case 3: {
                        cp0 = cp;
                        // Ensure continuity:
                        ControlPoint temp = theCurve.getPoint(2);
                        cp1.setController(temp);
                        temp.setController(cp1);
                        // Update the continuity:
                        cp1.setPosition(cp1.getPosition());
                        break;
                    }
                    default: {
                        cp0 = new ControlPoint(p0);
                    }
                } // End switch
            } else {
                cp0 = new ControlPoint(p0);
            }
        } else {
            cp0 = new ControlPoint(p0);
        }

        // Same thing with the second end point:

        // Check if the point exists:
        if (pointManager.isControlPoint(p3)) {
            ControlPoint cp = pointManager.getControlPoint(p3);

            // Check if this is an endpoint an not a link between two
            // existing curves:
            // (Without this check we could get "forks")
            Vector curveList = getCurves(cp);
            if (curveList.size() == 1) {
                BezierCurve theCurve = (BezierCurve) curveList.elementAt(0);
                int pointIndex = theCurve.getNumber(cp);
                switch (pointIndex) {
                    case 0: {
                        cp3 = cp;
                        // Ensure continuity:
                        ControlPoint temp = theCurve.getPoint(1);
                        cp2.setController(temp);
                        temp.setController(cp2);
                        // Update the continuity:
                        cp2.setPosition(cp2.getPosition());
                        break;
                    }
                    case 1:
                    case 2: {
                        cp3 = new ControlPoint(p3);
                        break;
                    }
                    case 3: {
                        cp3 = cp;
                        // Ensure continuity:
                        ControlPoint temp = theCurve.getPoint(2);
                        cp2.setController(temp);
                        temp.setController(cp2);
                        // Update the continuity:
                        cp2.setPosition(cp2.getPosition());
                        break;
                    }
                    default: {
                        cp3 = new ControlPoint(p3);
                    }
                } // End switch
            } else {
                cp3 = new ControlPoint(p3);
            }
        } else {
            cp3 = new ControlPoint(p3);
        }


        // Set the anchors:

        cp1.setAnchor(cp0); // Link with corresponding end point

        cp2.setAnchor(cp3); // Link with corresponding end point

        pointManager.addPoint(cp0);
        pointManager.addPoint(cp1);
        pointManager.addPoint(cp2);
        pointManager.addPoint(cp3);

        BezierCurve bc = new BezierCurve(cp0, cp1, cp2, cp3);
        curves.addElement(bc);

    }

    public void paint(Graphics g, int samples) {
        Enumeration list = curves.elements();
        BezierCurveRenderer renderer = new BezierCurveRenderer();
        while (list.hasMoreElements()) {
            BezierCurve curve = (BezierCurve) list.nextElement();
            renderer.paint(g, curve, samples);
        }
    }
}
