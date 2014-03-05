/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.curve;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class BezierCurveRenderer {

    public void paint(Graphics g, BezierCurve curve, double steps) {

        //if (!state.isPreview()) {
        g.setColor(Color.red);
        Point[] p = new Point[4];
        for (int i = 0; i < 4; i++) {
            p[i] = curve.points[i].getPosition();
        }
        g.drawLine(p[0].x - 1, p[0].y - 1,
                p[1].x - 1, p[1].y - 1);
        g.drawLine(p[2].x - 1, p[2].y - 1,
                p[3].x - 1, p[3].y - 1);
        g.setColor(Color.black);
        //}

        double step = (1 / steps);
        double t0 = 0;
        for (double t1 = step; t1 < 1; t1 += step) {
            Point p1 = curve.bezierFunction(t0);
            Point p2 = curve.bezierFunction(t1);
            g.drawLine(p1.x - 1, p1.y - 1, p2.x - 1, p2.y - 1);
            t0 = t1;
        }
        Point p1 = curve.bezierFunction(t0);
        Point p2 = curve.bezierFunction(1);
        g.drawLine(p1.x - 1, p1.y - 1, p2.x - 1, p2.y - 1);

    }
}
