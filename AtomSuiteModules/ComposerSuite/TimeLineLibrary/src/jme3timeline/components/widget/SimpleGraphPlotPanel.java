/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3timeline.components.widget;

/**
 *
 * @author cuong.nguyenmanh2
 */
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


public class SimpleGraphPlotPanel extends JPanel {
    // Plot one curve only
    static JFrame frame;
    
    // Attributes
    public static int glueSpace = 3;                    // Generic spacing between lines, text.
    public static int axisLabelFontSize = 10;           // Font size for axis labels.
    public static int tickFontSize = 8;                 // Same for tickmark labels.
    public static int legendFontSize = 16;              // Legend.
    public static int tickSize = 6;                     // Length of each tick mark.
    public static int titleFontSize = 12;               // Length of each tick mark.
    public static int pointBlobRadius = 4;              // Size of circle to mark a point.
    // Fonts and associated FontMetrics references:
    Font legendFont, tickFont, axisLabelFont, titleFont;
    FontMetrics tickFontMetrics, axisLabelFontMetrics, legendFontMetrics, titleFontMetrics;
    // Various box boundaries:
    // The panel:
    int panelHeight, panelWidth;
    // If an external bounding box is given:
    int bboxLeft, bboxRight, bboxTop, bboxBottom, bboxHeight, bboxWidth;
    // The inner box in which the curves are plotted:
    int plotBoxTopInset, plotBoxBottomInset, plotBoxLeftInset, plotBoxRightInset;
    int plotBoxHeight, plotBoxWidth, plotBoxLeft, plotBoxRight, plotBoxTop, plotBoxBottom;
    // The outerbox that contains the plotbox and the labels:
    int graphBoxHeight, graphBoxWidth, graphBoxLeft, graphBoxRight, graphBoxTop, graphBoxBottom;
    // The box below the outerbox that contains the legend:
    int legendBoxHeight, legendBoxWidth;
    int maxNumCurves;                              // Max allowable curves: for storage.
    int numCurves;                                 // Actual number of curves.
    int maxNumValues;
    SimplePlotCurve[] curves;                      // The curves themselves.
    double Xmax, Xmin, Xrange;                     // Lowest, highest, and range of X values.
    double Ymax, Ymin, Yrange;                     // Same for Y.
    int numXTicks, numYTicks;                      // Number of tick marks.
    double[] XTicks, YTicks;                       // The actual points for the ticks.
    String[] XTickLabels, YTickLabels;             // Labels.
    int XTickWidth, XTickHeight;                   // Font heights and widths.
    int YTickWidth, YTickHeight;
    String XaxisLabelString = "", YaxisLabelString = "";     // Axis labels.
    DecimalFormat Xformat, Yformat;                // Format labels/ticks.
    String titleString = "";                       // Title to appear center-top.
    // A set of colors to use.
    static Color colors[] = {Color.red, Color.blue, Color.green, Color.magenta};

    // Parameters to constructor: total number of curves. Over all curves,
    // the largest number of X values, the largest number of Y values.
    public SimpleGraphPlotPanel(int maxNumCurves, int maxNumValues) {
        this.setBackground(Color.white);

        // Curve data:
        this.maxNumCurves = maxNumCurves;
        this.maxNumValues = maxNumValues;
        numCurves = 0;
        curves = new SimplePlotCurve[maxNumCurves];
        for (int i = 0; i < maxNumCurves; i++) {
            curves[i] = new SimplePlotCurve(maxNumValues);
        }

        // Range stuff:
        Ymax = Xmax = Double.NEGATIVE_INFINITY;
        Ymin = Xmin = Double.POSITIVE_INFINITY;
        Xrange = Yrange = 0;
        // Tick marks:
        numXTicks = numYTicks = -1;
        XTicks = new double[maxNumValues + 1];
        YTicks = new double[maxNumValues + 1];
        XTickLabels = new String[maxNumValues + 1];
        YTickLabels = new String[maxNumValues + 1];

        // Font stuff:
        legendFont = new Font("Serif", Font.BOLD | Font.ITALIC, legendFontSize);
        axisLabelFont = new Font("SansSerif", Font.BOLD, axisLabelFontSize);
        tickFont = new Font("SansSerif", Font.PLAIN, tickFontSize);
        titleFont = new Font("Serif", Font.BOLD, titleFontSize);

    }


    public static void makePlotFrame(Vector points, String name, int frameWidth, int frameHeight) {
        SimpleGraphPlotPanel panel = new SimpleGraphPlotPanel(1, points.size());
        panel.setNumXTicks(10);
        panel.setNumYTicks(10);
        int ID = panel.createNewCurve(name, Color.blue);
        panel.setXYPoints(ID, points);
        frame = new JFrame();
        frame.setSize(frameWidth, frameHeight);
        frame.getContentPane().add(panel);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }

    public static void makePlotFrame(Vector points) {
        makePlotFrame(points, "", 600, 480);
    }

    public static void makePlotFrame(Vector[] pointSets, String[] names, String xLabel, int frameWidth, int frameHeight) {
        // Find largest set.
        int N = pointSets[0].size();
        for (int i = 1; i < pointSets.length; i++) {
            if (N < pointSets[i].size()) {
                N = pointSets[i].size();
            }
        }


        SimpleGraphPlotPanel panel = new SimpleGraphPlotPanel(pointSets.length, N);
        panel.setXaxisLabel(xLabel);
        panel.setNumXTicks(10);
        panel.setNumYTicks(10);
        for (int i = 0; i < pointSets.length; i++) {
            //System.out.println ("Simple.makeNew(): i=" + i + " names[i]=" + names[i]);
            int ID = panel.createNewCurve(names[i], colors[i % colors.length]);
            panel.setXYPoints(ID, pointSets[i]);
        }
        frame = new JFrame();
        frame.setSize(frameWidth, frameHeight);
        frame.getContentPane().add(panel);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }

    /**
     * <code>paintComponent</code> overrides the
     * <code>JPanel</code>'s method of the same name.
     *
     * @param g a <code>Graphics</code> value
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBounds();

        // Now plot:
        makePlot(g);
    }

    private void setBounds() {
        // Set the bounding box to be the panel:
        Dimension D = this.getSize();
        panelHeight = D.height;
        panelWidth = D.width;
        bboxLeft = bboxTop = 0;
        bboxRight = panelWidth;
        bboxBottom = panelHeight;
    }

    ////////////////////////////////////////////////////////////////////
    // For public use:
    /**
     * Call
     * <code>setTitle</code> with the plot title to be displayed.
     *
     * @param titleString a <code>String</code> value
     */
    public void setTitle(String titleString) {
        this.titleString = titleString;
    }

    /**
     * Call
     * <code>createNewCurve</code> each time a new curve is to be started,
     * before adding data for the curve.
     *
     * @param legendString a <code>String</code> value
     * @return an <code>int</code> value: a curveID that is to be used later.
     */
    public int createNewCurve(String legendString, Color color) {
        numCurves++;
        curves[numCurves - 1].legendString = legendString;
        curves[numCurves - 1].color = color;
        return numCurves - 1;
    }

    /**
     * Set the X-axis label.
     *
     * @param XaxisLabelString a <code>String</code> value
     */
    public void setXaxisLabel(String XaxisLabelString) {
        this.XaxisLabelString = XaxisLabelString;
    }

    /**
     * Set the Y-axis label.
     *
     * @param YaxisLabelString a <code>String</code> value
     */
    public void setYaxisLabel(String YaxisLabelString) {
        this.YaxisLabelString = YaxisLabelString;
    }

    /**
     * Set the number of X-axis tick marks.
     *
     * @param numXTicks an <code>int</code> value
     */
    public void setNumXTicks(int numXTicks) {
        if (numXTicks <= maxNumValues) {
            this.numXTicks = numXTicks;
        }
    }

    /**
     * Set the number of Y-axis tick marks.
     *
     * @param numYTicks an <code>int</code> value
     */
    public void setNumYTicks(int numYTicks) {
        if (numYTicks <= maxNumValues) {
            this.numYTicks = numYTicks;
        }
    }

    public void setPointRadius(int numPixels) {
        if (numPixels > 0) {
            pointBlobRadius = numPixels;
        }
    }

    /**
     * Call
     * <code>setXVector</code> with a set of X-values for a particular curve,
     * AFTER the curve has been created. This MUST be called before the
     * corresponding Y-values are given. The Y-values can be given as a vector,
     * or one by one to allowing for incremental plotting.
     *
     * @param curveID an <code>int</code> value
     * @param X a <code>double[]</code> value
     * @exception SimPlotException if an error occurs
     */
    public void setXVector(int curveID, double[] X) {
        if (curveID >= maxNumCurves) {
            return;
        }

        if (X.length > maxNumValues) {
            return;
        }

        curves[curveID].setXValues(X);

        // Update range:
        double min = X[0], max = X[0];
        for (int i = 0; i < X.length; i++) {
            if (X[i] < min) {
                min = X[i];
            }
            if (X[i] > max) {
                max = X[i];
            }
        }
        if (min < Xmin) {
            Xmin = min;
        }
        if (max > Xmax) {
            Xmax = max;
        }
        Xrange = Xmax - Xmin;
    }

    /**
     * Call
     * <code>setYVector</code> to pass in a set of Y-values for a a particular
     * curve. Call this only after setting the X-values for the same curve.
     *
     * @param curveID an <code>int</code> value
     * @param Y a <code>double[]</code> value
     * @exception SimPlotException if an error occurs
     */
    public void setYVector(int curveID, double[] Y) {
        if (curveID >= maxNumCurves) {
            return;
        }

        if (Y.length != curves[curveID].getNumXValues()) {
            return;
        }

        curves[curveID].setYValues(Y);


        // Update range:
        double min = Y[0], max = Y[0];
        for (int i = 0; i < Y.length; i++) {
            if (Y[i] < min) {
                min = Y[i];
            }
            if (Y[i] > max) {
                max = Y[i];
            }
        }
        if (min < Ymin) {
            Ymin = min;
        }
        if (max > Ymax) {
            Ymax = max;
        }
        Yrange = Ymax - Ymin;
    }

    /**
     * You can call
     * <code>addNewYValue</code> for a curve, once for each Y-value for
     * incremental plotting. In this case, the caller needs to
     * <code>repaint</code> the panel.
     *
     * @param curveID an <code>int</code> value
     * @param y a <code>double</code> value
     * @exception SimPlotException if an error occurs
     */
    public void addNewYValue(int curveID, double y) {
        if (curveID >= maxNumCurves) {
            return;
        }

        if (curves[curveID].getNumYValues() >= curves[curveID].getNumXValues()) {
            return;
        }

        curves[curveID].addNewYValue(y);

        // Adjust range:
        if (y < Ymin) {
            Ymin = y;
        }
        if (y > Ymax) {
            Ymax = y;
        }
        Yrange = Ymax - Ymin;
    }

    public void setXYPoints(int curveID, Vector points) {
        if (points == null) {
            return;
        }
        if (curveID >= maxNumCurves) {
            return;
        }

        int numPoints = points.size();
        if (points.size() > maxNumValues) {
            numPoints = maxNumValues;
        }

        double[] X = new double[numPoints];
        double[] Y = new double[numPoints];
        for (int i = 0; i < numPoints; i++) {
            Object obj = points.get(i);
            if (!(obj instanceof Point2D.Double)) {
                return;
            }
            Point2D.Double p = (Point2D.Double) obj;
            X[i] = p.x;
            Y[i] = p.y;
        }

        setXVector(curveID, X);
        setYVector(curveID, Y);
    }

    /**
     * Call
     * <code>makePlot</code> to make the plotter plot on a
     * <code>Graphics</code> instance of your choice, with a specified bounding
     * box within which to draw.
     *
     * @param g a <code>Graphics</code> value
     * @param boundingBoxTopLeftX an <code>int</code> value - the top left
     * corner of the box (X)
     * @param boundingBoxTopLeftY an <code>int</code> value - top left Y value.
     * @param boundingBoxWidth an <code>int</code> value - width of the box.
     * @param boundingBoxHeight an <code>int</code> value - height of the box.
     */
    public void makePlot(Graphics g, int boundingBoxTopLeftX, int boundingBoxTopLeftY,
            int boundingBoxWidth, int boundingBoxHeight) {
        bboxLeft = boundingBoxTopLeftX;
        bboxRight = bboxLeft + boundingBoxWidth;
        bboxTop = boundingBoxTopLeftY;
        bboxBottom = bboxTop + boundingBoxHeight;
        makePlot(g);
    }

    ////////////////////////////////////////////////////////////////////
    // Drawing
    void makePlot(Graphics g) {
        // Compute sizes:
        computeDrawingConstants(g);

        // Draw the bounding box:
        g.setColor(Color.black);
        g.drawRect(bboxLeft, bboxTop, bboxWidth, bboxHeight);

        // Draw legend line:
        g.drawLine(bboxLeft, graphBoxBottom, bboxRight, graphBoxBottom);
        writeLegends(g);

        // Draw plot box:
        g.setColor(Color.black);
        g.drawRect(plotBoxLeft, plotBoxTop, plotBoxWidth, plotBoxHeight);

        // Write labels:
        g.setFont(axisLabelFont);
        int x = bboxLeft + glueSpace;
        int y = bboxTop + 2 * glueSpace + axisLabelFontMetrics.getHeight()
                + titleFontMetrics.getHeight();
        g.drawString(YaxisLabelString, x, y);
        x = graphBoxRight - glueSpace - axisLabelFontMetrics.stringWidth(XaxisLabelString);
        y = graphBoxBottom - glueSpace;
        g.drawString(XaxisLabelString, x, y);

        // Draw and write ticks:
        g.setFont(tickFont);
        drawTicks(g);

        // Write title.
        x = (bboxLeft + bboxRight) / 2;
        y = bboxTop + 2 * glueSpace + titleFontMetrics.getHeight();
        g.setColor(Color.black);
        g.setFont(titleFont);
        g.drawString(titleString, x, y);

        // Plot curves:
        for (int i = 0; i < numCurves; i++) {
            plotCurve(g, curves[i]);
        }

    }

    int dataXToPixelValue(double x) {
        // Take a graph coordinate and get the pixel value for drawing.
        // First, the pixel value with regular coordinates:
        int regularX = (int) Math.round(((x - Xmin) / Xrange) * plotBoxWidth);
        // Now shift:
        return regularX + bboxLeft + plotBoxLeftInset;
    }

    int dataYToPixelValue(double y) {
        // Take a graph coordinate and get the pixel value for drawing.
        // First, the pixel value with regular coordinates:
        int regularY = (int) Math.round(((y - Ymin) / Yrange) * plotBoxHeight);
        // Now shift:
        return bboxTop + plotBoxTopInset + (plotBoxHeight - regularY);
    }

    void computeDrawingConstants(Graphics g) {
        // Boundingbox limits:
        bboxHeight = bboxBottom - bboxTop;
        bboxWidth = bboxRight - bboxLeft;

        // Legend stuff:
        legendBoxWidth = bboxWidth;
        g.setFont(legendFont);
        legendFontMetrics = g.getFontMetrics();
        legendBoxHeight = legendFontMetrics.getHeight() * numCurves + 4 * glueSpace;

        // GraphBox stuff:
        graphBoxWidth = bboxWidth;
        graphBoxHeight = bboxHeight - legendBoxHeight;
        graphBoxLeft = bboxLeft;
        graphBoxRight = graphBoxLeft + graphBoxWidth;
        graphBoxTop = bboxTop;
        graphBoxBottom = graphBoxTop + graphBoxHeight;

        // Need to do ticks next, to get tick distances:
        g.setFont(tickFont);
        tickFontMetrics = g.getFontMetrics();
        makeTicks(g);

        // plotBox stuff:
        g.setFont(titleFont);
        titleFontMetrics = g.getFontMetrics();
        int titleFontHeight = titleFontMetrics.getHeight();
        g.setFont(axisLabelFont);
        axisLabelFontMetrics = g.getFontMetrics();
        int axisLabelHeight = axisLabelFontMetrics.getHeight();
        plotBoxTopInset = 6 * glueSpace + axisLabelHeight + titleFontHeight;
        plotBoxBottomInset = 4 * glueSpace + tickFontMetrics.getHeight() + axisLabelHeight;
        plotBoxRightInset = 4 * glueSpace + XTickWidth + tickSize / 2;
        plotBoxLeftInset = 4 * glueSpace + YTickWidth + tickSize / 2;
        plotBoxWidth = graphBoxWidth - (plotBoxLeftInset + plotBoxRightInset);
        plotBoxHeight = graphBoxHeight - (plotBoxTopInset + plotBoxBottomInset);
        plotBoxTop = graphBoxTop + plotBoxTopInset;
        plotBoxBottom = plotBoxTop + plotBoxHeight;
        plotBoxLeft = graphBoxLeft + plotBoxLeftInset;
        plotBoxRight = plotBoxLeft + plotBoxWidth;
    }

    void makeTicks(Graphics g) {
        if ((numXTicks < 0) || (numYTicks < 0)) {
            numXTicks = numYTicks = estimateNumXTicks(g);
        }
        createFormats();
        makeXTicks();
        makeYTicks();
    }

    void drawTicks(Graphics g) {
        for (int i = 0; i < numXTicks; i++) {
            drawXTick(g, XTicks[i], XTickLabels[i]);
        }
        for (int i = 0; i < numYTicks; i++) {
            drawYTick(g, YTicks[i], YTickLabels[i]);
        }
    }

    int estimateNumXTicks(Graphics g) {
        // Do this by seeing how many ticks for Xmax will fit.
        int width = tickFontMetrics.stringWidth("" + Xmax);
        int n = graphBoxWidth / (width + 2 * glueSpace);
        // System.out.println ("estimateNumXTicks(): Xmax=" + Xmax + " w=" + width + " n=" + n);
        // Reduce by 1 to be safe.
        if (n > 4) {
            n--;
        }
        if (n > maxNumValues) {
            n = maxNumValues;
        }
        // System.out.println ("estimateNumXTicks=" + n);
        return n;
    }

    void makeXTicks() {
        // Force an odd number of ticks:
        if (numXTicks % 2 == 0) {
            numXTicks++;
        }
        double intervalSize = Xrange / (numXTicks - 1);
        XTicks[0] = Xmin;
        for (int i = 1; i <= numXTicks - 2; i++) {
            XTicks[i] = XTicks[i - 1] + intervalSize;
        }
        XTicks[numXTicks - 1] = Xmax;
        // Now for labels:
        int maxWidth = Integer.MIN_VALUE;
        for (int i = 0; i < numXTicks; i++) {
            XTickLabels[i] = makeDoubleFormatX(XTicks[i]);
            int width = tickFontMetrics.stringWidth(XTickLabels[i]);
            if (width > maxWidth) {
                maxWidth = width;
            }
            // System.out.println ("makeXTicks(): i=" + i + " XTick[i]=" + XTicks[i]);
        }
        // We'll need this for writing:
        XTickWidth = maxWidth;
    }

    void makeYTicks() {
        if (numYTicks % 2 == 0) {
            numYTicks++;
        }
        double intervalSize = Yrange / (numYTicks - 1);
        YTicks[0] = Ymin;
        for (int i = 1; i <= numYTicks - 2; i++) {
            YTicks[i] = YTicks[i - 1] + intervalSize;
        }
        YTicks[numYTicks - 1] = Ymax;
        // Now for labels:
        int maxWidth = Integer.MIN_VALUE;
        for (int i = 0; i < numYTicks; i++) {
            YTickLabels[i] = makeDoubleFormatY(YTicks[i]);
            int width = tickFontMetrics.stringWidth(YTickLabels[i]);
            if (width > maxWidth) {
                maxWidth = width;
            }
            // System.out.println ("makeYTicks(): i=" + i + " YTickLabels[i]=" + YTickLabels[i] + " w=" + width + " max=" + maxWidth);
        }
        YTickWidth = maxWidth;
    }

    void createFormats() {
        // Based on Xmin, Xmax: 
        Xformat = makeFormat(Xmin, Xmax);
        // Based on Ymin, Ymax:
        Yformat = makeFormat(Ymin, Ymax);
    }

    DecimalFormat makeFormat(double low, double high) {
        // Find out precision required:
        String formatStr = "#.##";
        double value = low;

        if (low == 0.0) {
            // Use high, possibly.
            if ((high == 0) || (high > 1)) {
                return new DecimalFormat(formatStr);
            }
            value = high;
        }
        // Make it positive:
        if (value < 0) {
            value = value * (-1);
        }
        while (value < 1) {
            value = value * 10;
            formatStr = formatStr + "#";
        }
        // System.out.println ("low=" + low + " high=" + high + " value=" + value + " formatStr=" + formatStr);
        return new DecimalFormat(formatStr);
    }

    String makeDoubleFormatX(double x) {
        String str = Xformat.format(x);
        // System.out.println ("makeX: x=" + x + " str=" + str);
        return str;
    }

    String makeDoubleFormatY(double y) {
        String str = Yformat.format(y);
        // System.out.println ("makeY: y=" + y + " str=" + str);
        return str;
    }

    void drawXTick(Graphics g, double x, String tickLabel) {
        // System.out.println ("DrawXTick: numXTicks=" + numXTicks + " x=" + x + " label=" + tickLabel);

        // Draw a tick at data position x.
        int xTick = dataXToPixelValue(x);
        int y = plotBoxBottom;
        g.setColor(Color.darkGray);
        g.drawLine(xTick, y + tickSize / 2, xTick, y - tickSize / 2);
        if (tickLabel != null) {
            // Put a label centered at xTick.
            int xLabelPos = xTick - (tickFontMetrics.stringWidth(tickLabel) / 2);
            int yLabelPos = plotBoxBottom + glueSpace
                    + tickSize / 2 + tickFontMetrics.getHeight();
            g.drawString(tickLabel, xLabelPos, yLabelPos);
        }
    }

    void drawYTick(Graphics g, double y, String tickLabel) {
        // System.out.println ("DrawYTick: numYTicks=" + numYTicks + " y=" + y + " label=" + tickLabel);

        // Draw a tick at data position y.
        int yTick = dataYToPixelValue(y);
        int x = plotBoxLeft;
        g.setColor(Color.darkGray);
        g.drawLine(x - tickSize / 2, yTick, x + tickSize / 2, yTick);
        if (tickLabel != null) {
            // Put a label.
            int xLabelPos = plotBoxLeft - tickSize / 2 - 2 * glueSpace
                    - tickFontMetrics.stringWidth(tickLabel);
            int yLabelPos = yTick + tickFontMetrics.getHeight() / 2;
            g.drawString(tickLabel, xLabelPos, yLabelPos);
        }
    }

    void writeLegends(Graphics g) {
        for (int i = 0; i < numCurves; i++) {
            // Write the legend string for curve i. First, the font.
            g.setFont(legendFont);
            // Now, the location
            int x = graphBoxLeft + 4 * glueSpace;
            int legendFontHeight = g.getFontMetrics().getHeight();
            int y = graphBoxBottom + 2 * glueSpace + ((i + 1) * legendFontHeight);
            g.setColor(curves[i].color);
            g.drawString(curves[i].legendString, x, y);
        }
    }

    void plotCurve(Graphics g, SimplePlotCurve curve) {
        g.setColor(curve.color);
        int x = 0, y = 0, prev_x = 0, prev_y = 0;
        for (int i = 0; i < curve.getNumValues(); i++) {
            // Plot the point X[i],Y[i].
            if (i > 0) {
                prev_x = x;
                prev_y = y;
            }
            x = dataXToPixelValue(curve.getX(i));
            y = dataYToPixelValue(curve.getY(i));
            g.fillOval(x - pointBlobRadius, y - pointBlobRadius, 2 * pointBlobRadius, 2 * pointBlobRadius);
            //** Joint the dots? Assumes data is sorted.
            if (i > 0) {
                g.drawLine(prev_x, prev_y, x, y);
            }
        }

    }
}
class SimplePlotCurve {

    private int maxNumValues;          // Maximum number of points.
    private int numXValues;            // Actual number of points.
    private int numCurrentYValues;     // Number of Y values added so far.
    String legendString;               // String to print in legend.
    Color color;                       // Preferred color.
    private double[] X, Y;     // The points.

    public SimplePlotCurve(int maxNumValues) {
        X = new double[maxNumValues];
        Y = new double[maxNumValues];
        numXValues = 0;
        numCurrentYValues = 0;
    }

    public void setXValues(double[] X) {
        numXValues = 0;
        for (int i = 0; i < X.length; i++) {
            numXValues++;
            this.X[numXValues - 1] = X[i];
        }
    }

    public void setYValues(double[] Y) {
        numCurrentYValues = 0;
        for (int i = 0; i < Y.length; i++) {
            numCurrentYValues++;
            this.Y[numCurrentYValues - 1] = Y[i];
        }
    }

    public void addNewYValue(double y) {
        numCurrentYValues++;
        Y[numCurrentYValues - 1] = y;
    }

    public int getNumXValues() {
        return numXValues;
    }

    public int getNumValues() {
        if (numXValues > numCurrentYValues) {
            return numCurrentYValues;
        } else {
            return numXValues;
        }
    }

    public int getNumYValues() {
        return numCurrentYValues;
    }

    public double getX(int i) {
        return X[i];
    }

    public double getY(int i) {
        return Y[i];
    }
}
