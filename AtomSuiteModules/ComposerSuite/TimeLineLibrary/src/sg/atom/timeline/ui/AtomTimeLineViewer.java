package sg.atom.timeline.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sg.atom.curve.manager.CurveManager;
import sg.atom.curve.manager.PointManager;
import sg.atom.curve.manager.CurverEditingState;
import sg.atom.curve.BezierCanvas;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class AtomTimeLineViewer extends JPanel implements ListSelectionListener {

    private JPanel drawingPanel;
    private JList list;
    private JSplitPane splitPane;
    private String[] imageNames = {"Red Movie", "movie"};

    public AtomTimeLineViewer() {
        //Create the list of images and put it in a scroll pane.

        list = new JList(imageNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        JScrollPane listScrollPane = new JScrollPane(list);
        drawingPanel = new JPanel();
        drawingPanel.setLayout(new BorderLayout());
        CurverEditingState state = new CurverEditingState();
        PointManager pointManager = new PointManager();
        CurveManager curveManager = new CurveManager(pointManager, state);
        BezierCanvas bezierCanvas = new BezierCanvas(state, curveManager, pointManager);

        drawingPanel.add("Center", bezierCanvas);

        JScrollPane drawingViewScrollPane = new JScrollPane(drawingPanel);

        setLayout(new BorderLayout());
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, drawingViewScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        
        

        add(splitPane, BorderLayout.CENTER);
//Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(100, 50);

        listScrollPane.setMinimumSize(minimumSize);
        drawingViewScrollPane.setMinimumSize(minimumSize);
        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
        updateView(imageNames[list.getSelectedIndex()]);

    }
    //Listens to the list

    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        updateView(imageNames[list.getSelectedIndex()]);
    }

    //Renders the selected image
    protected void updateView(String name) {
        /*
         ImageIcon icon = createImageIcon("../../../images/" + name + ".png");
         picture.setIcon(icon);
         if (icon != null) {
         picture.setText(null);
         } else {
         picture.setText("Image not found");
         }*/
        // Render the associated data points
    }

    //Used by SplitPaneDemo2
    public JList getImageList() {
        return list;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = AtomTimeLineViewer.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
