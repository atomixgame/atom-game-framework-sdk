package de.jaret.timebars.linechart;

import de.jaret.timebars.linechart.renderer.LineChartHeaderRenderer;
import de.jaret.timebars.linechart.renderer.LineChartRenderer;
import de.jaret.util.ui.timebars.TimeBarViewerDelegate;
import de.jaret.util.ui.timebars.model.IRowHeightStrategy;
import de.jaret.util.ui.timebars.model.ITimeBarViewState;
import de.jaret.util.ui.timebars.model.TimeBarModel;
import de.jaret.util.ui.timebars.model.TimeBarRow;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.BoxTimeScaleRenderer;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JFrame;
import sg.atom.swing.components.jaret.JaretLineChartInterval;
import sg.atom.swing.components.jaret.creator.JaretDataPointModelCreator;

/**
 * Example showing how to draw a simple line chart with the time bars. This is showing one line chart only, but mixing
 * intervals and line chart intervals is possible.
 * 
 * @author Peter Kliem
 * @version $Id: LineChartExample.java 801 2008-12-27 22:44:54Z kliem $
 */
public class LineChartExample extends JFrame {

    /** timebar viewer. */
    private static TimeBarViewer _tbv;

    public LineChartExample() {
        super(LineChartExample.class.getName());
        this.setSize(1200, 700);
        //this.getContentPane().setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createContents(this);
    }

    protected TimeBarViewer createContents(JFrame parent) {
        BorderLayout layout = new BorderLayout();
        parent.setLayout(layout);

        TimeBarModel model = JaretDataPointModelCreator.createModel();

        // create the time bar viewer
        // horizontal scroll bar only! (since we only assume on erow, height of the
        // row is done by a row height strategy)
        _tbv = new TimeBarViewer(model, true, true);

        _tbv.setTimeScalePosition(TimeBarViewer.TIMESCALE_POSITION_TOP);
        _tbv.setModel(model);

        // choose a nice scale to begin with
        _tbv.setPixelPerSecond(10);

        // do some configurations on the viewer to make it more suitable for chart rendering

        // no selections
        _tbv.getSelectionModel().setRowSelectionAllowed(false);
        _tbv.getSelectionModel().setIntervalSelectionAllowed(true);
        _tbv.getSelectionModel().setMultipleSelectionAllowed(false); // also disable rect selection

        // setup the y axis
        _tbv.setYAxisWidth(20);
        _tbv.setHeaderRenderer(new LineChartHeaderRenderer());

        // disable grid rendering since it is not needed
        _tbv.setGridRenderer(null);

        // use the box timescale renderer
        _tbv.setTimeScaleRenderer(new BoxTimeScaleRenderer());

        // we will only render one row. This row should always be scaled to match the height of the diagram rectangle.
        // the row height strategy will ensure that
        _tbv.getTimeBarViewState().setUseVariableRowHeights(true);
        _tbv.getTimeBarViewState().setRowHeightStrategy(new IRowHeightStrategy() {

            public int calculateRowHeight(TimeBarViewerDelegate delegate, ITimeBarViewState timeBarViewState,
                    TimeBarRow row) {
                return delegate.getDiagramRect().height;
            }

            public boolean overrideDefault() {
                return true;
            }
        });


        // register the renderer for the line chart itself
        _tbv.registerTimeBarRenderer(JaretLineChartInterval.class, new LineChartRenderer());

        // add the control panel for a scale slider
        LineChartControlPanel ctrlPanel = new LineChartControlPanel(_tbv);
        parent.getContentPane().add(_tbv, BorderLayout.CENTER);
        parent.getContentPane().add(ctrlPanel, BorderLayout.SOUTH);

        ((JComponent)_tbv._diagram).addMouseMotionListener(mouseHandler);
        
        return _tbv;
        
        
    }
    MouseMotionAdapter mouseHandler = new MouseMotionAdapter() {

        @Override
        public void mouseMoved(MouseEvent e) {
            System.out.println("Mouse : X " + e.getX() + " Y " + e.getY());
        }
    };
    public static void main(String[] args) {
        LineChartExample test = new LineChartExample();
        test.setVisible(true);
    }
}