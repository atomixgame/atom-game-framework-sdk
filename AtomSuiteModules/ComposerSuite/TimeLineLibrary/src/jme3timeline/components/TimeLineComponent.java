package jme3timeline.components;

import sg.atom.timeline.ui.AtomTimeLineViewer;
import com.lightdev.demo.gantt.DateCellEditor;
import com.lightdev.demo.gantt.GanttTableCellRenderer;
import com.lightdev.demo.gantt.GanttTreeTable;
import com.lightdev.demo.gantt.TimeFrameProvider;
import com.lightdev.demo.gantt.data.ActivityTreeTableModel;
import com.lightdev.demo.gantt.dnd.TreeTableDropTarget;
import com.lightdev.demo.gantt.dnd.TreeTableMouseAdapter;
import com.lightdev.demo.gantt.dnd.TreeTableNodeMover;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import jme3timeline.TestAtomTimeLine;
import jme3timeline.TimeLinePanel;
import jme3timeline.components.renderer.ScaleRenderer;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXHyperlink;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TimeLineComponent extends JPanel implements TimeFrameProvider {

    private AtomTimeLineViewer timeLinePanel;
    private JButton addButton, removeButton;
    private JPanel buttonPanel;

    public TimeLineComponent() {
        setLayout(new BorderLayout());

        add(createAtomTimeLineViewer(), BorderLayout.CENTER);
    }

    public JPanel createJaret() {
        return new TimeLinePanel();
    }
    
    public JPanel createAtomTimeLineViewer(){
        return new AtomTimeLineViewer();
    }

    public JPanel createGantt() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        // create tree table and add it to frame
        JPanel tablePanel = new JPanel(new BorderLayout());
        GanttTreeTable treeTable = new GanttTreeTable(
                new ActivityTreeTableModel(),
                new GanttTableCellRenderer(this),
                new DateCellEditor());
        tablePanel.add(new JScrollPane(treeTable), BorderLayout.CENTER);
        tablePanel.setBorder(new EmptyBorder(3, 6, 3, 6)); // some aesthetics
        contentPane.add(tablePanel, BorderLayout.CENTER);

        // let the tree table show its root node but without expand/collapse handle
        treeTable.setRootVisible(true);
        treeTable.setShowsRootHandles(false);

        // set some initial column widths
        treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_TREE).setMaxWidth(100);
        treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_START).setMinWidth(60);
        treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_START).setMaxWidth(60);
        treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_END).setMinWidth(60);
        treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_END).setMaxWidth(60);
        treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_DAYS).setMinWidth(40);
        treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_DAYS).setMaxWidth(40);

        // swtich on drag and drop in the tree table
        TreeTableNodeMover handler = new TreeTableNodeMover();
        treeTable.setTransferHandler(handler);
        treeTable.setDropTarget(new TreeTableDropTarget(handler));
        treeTable.setDragEnabled(true);

        /*
         * add a custom mouse listener to compensate a tree expand/collapse 
         * deficit with drag enabled (see TreeTableMouseAdapter for details)
         */
        treeTable.addMouseListener(new TreeTableMouseAdapter());


        // Add: ScaleHeader
        JTableHeader header = treeTable.getTableHeader();
        TableColumn col = treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_GANTT);
        col.setHeaderRenderer(new ScaleRenderer());
        header.setBackground(Color.yellow);


        // create time frame selector and add to frame
        JPanel timeFrameSelector = new JPanel();
        JLabel lb = new JLabel("Start:");
        timeFrameSelector.add(lb);
        timeFrameStart = new JXDatePicker();
        timeFrameSelector.add(timeFrameStart);
        lb = new JLabel("End:");
        timeFrameSelector.add(lb);
        timeFrameEnd = new JXDatePicker();
        timeFrameSelector.add(timeFrameEnd);

        //contentPane.add(timeFrameSelector, BorderLayout.SOUTH);

        // initialize date selector with the current month
        GregorianCalendar now = new GregorianCalendar();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        timeFrameStart.setDate(new GregorianCalendar(year, month, 1).getTime());
        timeFrameEnd.setDate(new GregorianCalendar(year, month,
                now.getActualMaximum(Calendar.DAY_OF_MONTH)).getTime());

        // set local date format
        DateFormat[] formats = new DateFormat[]{DateFormat.getDateInstance(DateFormat.SHORT)};
        timeFrameStart.setFormats(formats);
        timeFrameEnd.setFormats(formats);

        // register tree table as listener to actions that influence tree table display
        timeFrameStart.addActionListener(treeTable);
        timeFrameEnd.addActionListener(treeTable);


        // Add more components
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JXHyperlink(new AbstractAction("0:00:00:29") {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }));
        bottomPanel.add(new JLabel("(29.97fps"));

        JToolBar toolbar = new JToolBar();
        ImageIcon imgCopy = TestAtomTimeLine.createImageIcon("retina/mimi/Copy16.png");
        JButton btnCopy = new JButton(imgCopy);
        toolbar.add(btnCopy);

        ImageIcon imgCut = TestAtomTimeLine.createImageIcon("retina/mimi/Cut16.png");
        JButton btnCut = new JButton(imgCut);
        toolbar.add(btnCut);

        ImageIcon imgDelete = TestAtomTimeLine.createImageIcon("retina/mimi/Delete16.png");
        JButton btnDelete = new JButton(imgDelete);
        toolbar.add(btnDelete);

        bottomPanel.add(toolbar);
        contentPane.add(bottomPanel,BorderLayout.SOUTH);
        return contentPane;
    }

    public JPanel createTimeLineUI() {
        JPanel timeLineCont = new JPanel();
        timeLinePanel = new AtomTimeLineViewer();
        timeLineCont.setLayout(new BorderLayout());
        addButton = new JButton("Add");
        addButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    }
                });

        removeButton = new JButton("Remove");
        removeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    }
                });

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        timeLineCont.add(timeLinePanel, BorderLayout.CENTER);
        timeLineCont.add(buttonPanel, BorderLayout.SOUTH);

        return timeLineCont;
    }

    /* -------------------- TimeFrameProvider implementation ----------------- */
    /**
     * get start date of time frame
     *
     * @return the start date of the time frame
     */
    public Date getFrameStart() {
        return timeFrameStart.getDate();
    }

    /**
     * get end date of time frame
     *
     * @return the end date of the time frame
     */
    public Date getFrameEnd() {
        return timeFrameEnd.getDate();
    }
    /* --------------- class fields -------------------------- */
    /**
     * component to manipulate start date of visible region of gantt chart
     */
    private JXDatePicker timeFrameStart;
    /**
     * component to manipulate end date of visible region of gantt chart
     */
    private JXDatePicker timeFrameEnd;
}
