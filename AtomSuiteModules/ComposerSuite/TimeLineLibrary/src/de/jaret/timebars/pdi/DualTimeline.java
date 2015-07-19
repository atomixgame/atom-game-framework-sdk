package de.jaret.timebars.pdi;

import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.TimeBarMarkerImpl;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.DefaultGapRenderer;
import de.jaret.util.ui.timebars.swing.renderer.DefaultTitleRenderer;
import java.awt.BorderLayout;
import java.util.Random;
import javax.swing.JFrame;
import sg.atom.swing.components.jaret.JaretCinematicModel;
import sg.atom.swing.components.jaret.JaretMainTrack;
import sg.atom.swing.components.jaret.JaretTimeLineTrackRow;

/**
 */
public class DualTimeline extends JFrame {

    Random RND = new Random();
    JaretCinematicModel cinematicModel;
    JaretCinematicModel cinematicModel2;
    static String[] mainTrackNames = {"Director", "Cam", "CameraEffect", "Sound", "Effect"};
    static String[] evenTrackNames = {"Cam1", "Cam2", "Car", "MainCharacter", "Background", "Particles"};

    public DualTimeline() {
        super("Cinematic test");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        cinematicModel = new JaretCinematicModel();
        createModels(cinematicModel);

        cinematicModel2 = new JaretCinematicModel();
        createModels(cinematicModel2);

        TimeBarViewer tbv = new TimeBarViewer(cinematicModel, false, false);
        tbv.setName("Main");
        tbv.setTimeScalePosition(TimeBarViewer.TIMESCALE_POSITION_TOP);
        MiniTimeScaleRenderer timeScaleRenderer = new MiniTimeScaleRenderer();
        timeScaleRenderer.setHeight(20);
        tbv.setTimeScaleRenderer(timeScaleRenderer);

        //tbv.addIntervalModificator(new DefaultIntervalModificator());

        // set the default title renderer
        tbv.setTitleRenderer(new DefaultTitleRenderer());
        tbv.setHeaderRenderer(new TrackHeaderRenderer());
        tbv.setStartDate(new JaretDate(0));
        tbv.setDrawRowGrid(true);
        tbv.setTimeBarRenderer(new PdiRenderer());
        tbv.setPixelPerSecond(200.0 / 10);
        //tbv.setHeaderRenderer(null);
        tbv.setGapRenderer(new DefaultGapRenderer());
        tbv.getXScrollBar().setVisible(false);
        
        // add a marker
        TimeBarMarkerImpl tm = new TimeBarMarkerImpl(true, new JaretDate(0));
        tm.setDescription("Timebarmarker");
        tbv.addMarker(tm);

        // Dienste ohne dispo info
        TimeBarViewer tbv2 = new TimeBarViewer(cinematicModel2, false, false);
        tbv2.setTimeScalePosition(TimeBarViewer.TIMESCALE_POSITION_NONE);
        tbv2.setName("lower");
        //tbv2.setDrawRowGrid(true);
        tbv2.setTimeBarRenderer(new PdiRenderer());
        tbv2.setHeaderRenderer(null);

        tbv2.setPixelPerSecond(200.0 / 10);
        /*
         tbv2.setAdjustMinMaxDatesByModel(false);
         tbv2.setMinDate(tbv.getMinDate());
         tbv2.setMaxDate(tbv.getMaxDate());
         tbv2.setStartDate(tbv.getStartDate());
         tbv2.setYAxisWidth(tbv.getYAxisWidth());
        
         DragSource dragSource = DragSource.getDefaultDragSource();
         DragGestureListener dgl = new TimeBarViewerDragGestureListener();
         DragGestureRecognizer dgr =
         dragSource.createDefaultDragGestureRecognizer(tbv2._diagram,
         DnDConstants.ACTION_MOVE, dgl);
        
         DropTargetListener dtl = new TimeBarDropTargetListener(); DropTarget
         dt = new DropTarget(this, dtl);
         
         DropTargetListener dtl = new DragnDropTargetListener(tbv);
         DropTarget dt = new DropTarget(tbv, dtl);
         
         // synchronize the TimeBarViewers by a synchronizer
         TimeBarViewerSynchronizer synchronizer = new TimeBarViewerSynchronizer(false, true, true);
         synchronizer.addViewer(tbv);
         synchronizer.addViewer(tbv2);

         JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
         splitter.add(tbv);
         splitter.add(tbv2);
         getContentPane().add(splitter, BorderLayout.CENTER);
         // add the control panel
         ControlPanel1 cp = new ControlPanel1(tbv, null, false);
         getContentPane().add(cp, BorderLayout.NORTH);
         splitter.setDividerLocation(0.5);
         */
        getContentPane().add(tbv, BorderLayout.CENTER);
        setVisible(true);
    }

    void initComponents() {
    }

    public static void main(String[] args) {
        DualTimeline f = new DualTimeline();
    }

    /**
     *
     */
    private void createModels(JaretCinematicModel cinematicModel) {
        for (int i = 0; i < mainTrackNames.length; i++) {
            JaretTimeLineTrackRow row = new JaretTimeLineTrackRow(mainTrackNames[i]);
            int numOfTracks = 2 + RND.nextInt(8);
            for (int j = 0; j < numOfTracks; j++) {
                JaretMainTrack mainTrack = new JaretMainTrack("Track " + i + " " + j);
                JaretDate start = new JaretDate(0).advanceSeconds(5 * (1 + j));
                mainTrack.setBegin(start);
                //mainTrack.setEnd(start.copy().advanceSeconds(RND.nextInt(15 * (1+j))));
                mainTrack.setEnd(start.copy().advanceSeconds(3));
                row.getMainTracks().add(mainTrack);
            }
            cinematicModel.addTrack(row);
        }
    }

    /**
     * @return
     */
    private void createEventTracks() {
        for (int i = 0; i < evenTrackNames.length; i++) {
        }

    }
}
