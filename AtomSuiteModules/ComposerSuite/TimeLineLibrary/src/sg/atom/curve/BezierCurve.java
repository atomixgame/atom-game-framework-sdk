package sg.atom.curve;

import java.awt.*;

public class BezierCurve {

    protected ControlPoint[] points = new ControlPoint[4];

    public boolean contains(ControlPoint cp) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == cp) {
                return true;
            }
        }
        return false;
    }

    public int getNumber(ControlPoint cp) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == cp) {
                return i;
            }
        }
        return -1;
    }

    public ControlPoint getPoint(int i) {
        if (i >= 0 && i < 4) {
            return points[i];
        } else {
            return null;
        }
    }

    public BezierCurve(ControlPoint p0,
            ControlPoint p1,
            ControlPoint p2,
            ControlPoint p3) {
        //State state) {
        points[0] = p0;
        points[1] = p1;
        points[2] = p2;
        points[3] = p3;
        //this.state = state;
    }

    public Point bezierFunction(double t) {
        Point p1, p2, p3, p4;
        p1 = points[0].getPosition();
        p2 = points[1].getPosition();
        p3 = points[2].getPosition();
        p4 = points[3].getPosition();
        double x, y;

        x = p1.x * (Math.pow(-t, 3) + 3 * Math.pow(t, 2) - 3 * t + 1)
                + 3 * p2.x * t * (Math.pow(t, 2) - 2 * t + 1)
                + 3 * p3.x * Math.pow(t, 2) * (1 - t) + p4.x * Math.pow(t, 3);


        y = p1.y * (Math.pow(-t, 3) + 3 * Math.pow(t, 2) - 3 * t + 1)
                + 3 * p2.y * t * (Math.pow(t, 2) - 2 * t + 1)
                + 3 * p3.y * Math.pow(t, 2) * (1 - t) + p4.y * Math.pow(t, 3);

        return new Point((int) Math.round(x), (int) Math.round(y));

    }


}
