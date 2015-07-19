package de.jaret.timebars.hierarchy;

import com.jme3.timeline.components.renderer.JMECineHierarchyRenderer;
import com.jme3.timeline.components.renderer.JMECineTitleRenderer;
import com.jme3.timeline.components.renderer.JMEHeaderRenderer;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.jaret.timebars.pdi.ControlPanel1;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.TimeBarMarkerImpl;
import de.jaret.util.ui.timebars.mod.DefaultIntervalModificator;
import de.jaret.util.ui.timebars.model.AddingTimeBarRowModel;
import de.jaret.util.ui.timebars.model.HierarchicalTimeBarModel;
import de.jaret.util.ui.timebars.model.TimeBarNode;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.DefaultRelationRenderer;
import sg.atom.swing.components.jaret.creator.RandomDemoModelCreator;

/**
 * Simple hierarchical view Swing version. Scaling, manipulating the intervals, tree structure, draggable marker.
 * 
 * @author Peter Kliem
 * @version $Id: SwingHierarchy.java 798 2008-12-27 21:51:27Z kliem $
 */
public class SwingHierarchy {
    
    public static void main(String[] args) {
        // set up the frame
        JFrame f = new JFrame(SwingHierarchy.class.getName());
        f.setSize(1000, 600);
        f.getContentPane().setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // create the model
        HierarchicalTimeBarModel model = RandomDemoModelCreator.createModel(2, 5);
        
        TimeBarViewer _tbv = new TimeBarViewer();
        _tbv.setName("Hierarchy");
        _tbv.setModel(model);
        _tbv.setTimeScalePosition(TimeBarViewer.TIMESCALE_POSITION_TOP);
        
        _tbv.setHierarchyRenderer(new JMECineHierarchyRenderer());
        _tbv.setHeaderRenderer(new JMEHeaderRenderer());
        _tbv.setHierarchyWidth(100);
        _tbv.setYAxisWidth(100);
        // allow all interval modifications
        _tbv.addIntervalModificator(new DefaultIntervalModificator());

        // register additional renderer for the erged intervals of the addind row model
        _tbv.registerTimeBarRenderer(AddingTimeBarRowModel.MergedInterval.class, new SumRenderer());

        // set the default title renderer
        //FIXME: Include icons
        _tbv.setTitleRenderer(new JMECineTitleRenderer());

        // setup relation rendering
        _tbv.setRelationRenderer(new DefaultRelationRenderer());
        //_tbv.setHeaderRenderer(null);


        // add a marker
        TimeBarMarkerImpl tm = new TimeBarMarkerImpl(true, new JaretDate().advanceDays(1));
        tm.setDescription("Timebarmarker");
        _tbv.addMarker(tm);
        
        f.getContentPane().add(_tbv, BorderLayout.CENTER);

        // expand all nodes
        TimeBarNode root = model.getRootNode();
        _tbv.getHierarchicalViewState().setExpandedRecursive(root, true);



        // add the control panel
        ControlPanel1 cp = new ControlPanel1(_tbv, null, true);
        f.getContentPane().add(cp, BorderLayout.NORTH);
        
        f.setVisible(true);
    }
}
