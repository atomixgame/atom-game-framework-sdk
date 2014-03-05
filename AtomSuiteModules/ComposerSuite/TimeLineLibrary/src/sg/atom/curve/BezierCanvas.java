package sg.atom.curve;

import sg.atom.curve.manager.CurverEditingState;
import sg.atom.curve.manager.CurveManager;
import sg.atom.curve.manager.PointManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * Draws the curves and processes the mouse input. *
 */
public class BezierCanvas extends JPanel {

    protected CurverEditingState state;
    protected CurveManager curveManager;
    protected PointManager pointManager;
    protected Point[] temporaryCurve = new Point[4];
    protected int temporaryLength = 0;

    public BezierCanvas(CurverEditingState state,
            CurveManager curveManager,
            PointManager pointManager) {
        this.state = state;
        this.curveManager = curveManager;
        this.pointManager = pointManager;

        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseDown(e, e.getX(), e.getY());
                }
            }

            public void mouseEntered(MouseEvent arg0) {
            }

            public void mouseExited(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
    }

    public boolean mouseDown(MouseEvent e, int x, int y) {
        Point p = new Point(x, y);
        if (state.getState() == CurverEditingState.PASSIVE) {
            if (pointManager.isControlPoint(p)) {
                ControlPoint cp = pointManager.getControlPoint(p);
                pointManager.activatePoint(cp);
                state.setState(CurverEditingState.EDITING);
            }
        } else if (state.getState() == CurverEditingState.CREATING) {
            temporaryCurve[temporaryLength] = p;
            temporaryLength++;
            repaint();
        }
        System.out.println("Mouse down");

        return true;
    }

    public boolean mouseUp(MouseEvent e, int x, int y) {
        if (state.getState() == CurverEditingState.CREATING) {
            if (temporaryLength == 4) {
                Point[] p = temporaryCurve;
                curveManager.addCurve(p[0], p[1], p[2], p[3]);
                temporaryLength = 0;
                state.setState(CurverEditingState.PASSIVE);
                repaint();
            }
        } else if (state.getState() == CurverEditingState.EDITING) {
            state.setState(CurverEditingState.PASSIVE);
        }
        return true;
    }

    public boolean mouseDrag(MouseEvent e, int x, int y) {
        int s = state.getState();
        if (s == CurverEditingState.EDITING) {
            pointManager.setActivePoint(new Point(x, y));
            repaint();
        } else if (s == CurverEditingState.CREATING) {
            temporaryCurve[temporaryLength - 1] = new Point(x, y);
            repaint();
        }
        return true;
    }

    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Possible improvement: Separate static and dynamic layer; only update
     * curves bein edited and store the rest as an Image. *
     */
    public void paint(Graphics g) {
        // Draw grid

        Image offscreen = createImage(size().width, size().height);
        Graphics og = offscreen.getGraphics();

        // Draw all the curves
        curveManager.paint(og, state.getSamples());

        // If a curve is under creation, draw a simple approximation of it:
        if (temporaryLength != 0) {
            Point[] p = temporaryCurve;
            og.setColor(Color.red);
            for (int i = 1; i < temporaryLength; i++) {
                og.drawLine(p[i - 1].x - 1, p[i - 1].y - 1,
                        p[i].x - 1, p[i].y - 1);
            }
            for (int i = 0; i < temporaryLength; i++) {
                og.fillRect(p[i].x - 3, p[i].y - 3, 5, 5);
            }
            og.setColor(Color.black);
        }

        // If we are not in preview mode, draw all the points

        //if (!state.isPreview()) {
        pointManager.paint(og);
        //}

        g.drawImage(offscreen, 0, 0, this);
        drawGrid(g);
        drawTimeIndicator(g, 100);
    }

    public void drawTimeIndicator(Graphics og, float t) {

        Graphics2D g = (Graphics2D) og;
        int w = getSize().width;
        int h = getSize().height;
        float timeZoom = 1;
        int dx = (int) Math.ceil(t / timeZoom);

        g.setColor(new Color(1f, 0.0f, 0.0f, 1f));
        //System.out.println(" " + dx);
        g.drawLine(dx, 0, dx, h);
        g.drawLine(dx - 5, 0, dx + 5, 0);
        g.drawLine(dx - 5, 0, dx, 10);
        g.drawLine(dx, 10, dx + 5, 0);
    }

    public void drawGrid(Graphics og) {

        Graphics2D g = (Graphics2D) og;
        int w = getSize().width;
        int h = getSize().height;
        int dx = 0, dy = 0;
        int gapx = 200, gapy = 50;

        g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.5f));

        while (dx < w) {
            dx += gapx;
            g.drawLine(dx, 0, dx, h);
            if (dx / gapx % 2 == 0) {
                g.drawString(" +" + dx, dx, 10);
            }
        }
        while (dy < h) {
            dy += gapy;
            g.drawLine(0, dy, w, dy);
            if (dy / gapy % 2 == 1) {
                g.drawString(" +" + dy, 10, dy);
            }
        }
    }
}
