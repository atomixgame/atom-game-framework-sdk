
package com.lightdev.demo.gantt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import com.lightdev.demo.gantt.data.ActivityTreeTableModel;
import com.lightdev.demo.gantt.dnd.TreeTableDropTarget;
import com.lightdev.demo.gantt.dnd.TreeTableMouseAdapter;
import com.lightdev.demo.gantt.dnd.TreeTableNodeMover;


/**
 * An application to demonstrate the use of JXTreeTable and JXDatePicker to create 
 * a project planning tool implementing an editable gantt chart.
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 1, 30.03.2006
 */
public class GanttDemo implements WindowListener, TimeFrameProvider, ActionListener {

  /**
   * construct a new instance of a GanttDemo object
   */
  public GanttDemo() {
    super();
    buildUi();
  }

  /**
   * main method to bootstrap application
   * 
   * <p>This simply sets the system look and feel and then creates a 
   * new instance of this application class</p>
   * 
   * @param args  command line arguments
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new GanttDemo();
  }
  
  /**
   * create the user interface of this application
   */
  private void buildUi() {
    
    // create a frame for our demo application
    JFrame frame = new JFrame();
    
    // add this class as a window listener to terminate in case the frame is closed
    frame.addWindowListener(this);
    
    // get the frame's content pane to add components to and set the layout
    Container contentPane = frame.getContentPane();
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
    treeTable.getColumnModel().getColumn(ActivityTreeTableModel.COL_TREE).setWidth(180);
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
    contentPane.add(timeFrameSelector, BorderLayout.NORTH);
    
    // initialize date selector with the current month
    GregorianCalendar now = new GregorianCalendar();
    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH);
    timeFrameStart.setDate(new GregorianCalendar(year, month, 1).getTime());
    timeFrameEnd.setDate(new GregorianCalendar(year, month, 
            now.getActualMaximum(Calendar.DAY_OF_MONTH)).getTime());
    
    // set local date format
    DateFormat[] formats = new DateFormat[] { DateFormat.getDateInstance(DateFormat.SHORT) };
    timeFrameStart.setFormats(formats);
    timeFrameEnd.setFormats(formats);
    
    // create buttons
    JButton newBtn = new JButton("New Activity");
    JButton deleteBtn = new JButton("Delete Activity");
    JButton licenseBtn = new JButton("Copyright/License");
    
    // add this class to the license button as an action listener for display of license info
    licenseBtn.addActionListener(this);
    
    // create button panel and add to frame
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(newBtn);
    buttonPanel.add(deleteBtn);
    buttonPanel.add(licenseBtn);
    contentPane.add(buttonPanel, BorderLayout.SOUTH);
    
    // associate action commands with components that cause action events
    newBtn.setActionCommand(GanttTreeTable.CMD_NEW_ACTIVITY);
    deleteBtn.setActionCommand(GanttTreeTable.CMD_DELETE_ACTIVITY);
    licenseBtn.setActionCommand(CMD_SHOW_LICENSE);
    //timeFrameStart.setActionCommand(TimeFrameProvider.CMD_CHANGE_FRAME_START_DATE);
    //timeFrameEnd.setActionCommand(TimeFrameProvider.CMD_CHANGE_FRAME_END_DATE);
    
    // register tree table as listener to actions that influence tree table display
    newBtn.addActionListener(treeTable);
    deleteBtn.addActionListener(treeTable);
    timeFrameStart.addActionListener(treeTable);
    timeFrameEnd.addActionListener(treeTable);
    
    // adjust and show frame
    frame.setSize(700, 500);
    frame.setLocationRelativeTo(null);
    frame.setTitle("Gantt Demo");
    frame.setVisible(true);
  }
  
  /**
   * show the license of this application
   * 
   * <p>This assumes a file named 'license.txt' to exist in 
   * the package this class resides in.</p>
   */
  private void showLicense() {
    try {
      InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("license.txt"));
      BufferedReader br = new BufferedReader(isr);
      StringBuffer buf = new StringBuffer();
      String line;
      line = br.readLine();
      while(line != null) {
        buf.append(line);
        buf.append(System.getProperty("line.separator"));
        line = br.readLine();
      }
      isr.close();
      br.close();
      JDialog ld = new JDialog();
      ld.setLayout(new BorderLayout());
      Container contentPane = ld.getContentPane();
      JTextArea ta = new JTextArea(buf.toString());
      ta.setLineWrap(false);
      contentPane.add(new JScrollPane(ta));
      ld.setModal(true);
      ld.setTitle(TXT_LICENSE);
      ld.setSize(740,540);
      ld.setLocationRelativeTo(null);
      ld.setVisible(true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /* -------------------- TimeFrameProvider implementation ----------------- */
  
  /**
   * get start date of time frame
   * @return the start date of the time frame
   */
  public Date getFrameStart() {
    return timeFrameStart.getDate();
  }

  /**
   * get end date of time frame
   * @return the end date of the time frame
   */
  public Date getFrameEnd() {
    return timeFrameEnd.getDate();
  }
  
  /* ---------------- WindowListener implementation -------------- */
  
  /**
   * terminate application when window is closed
   */
  public void windowClosing(WindowEvent e) {
    System.exit(0);
  }
  
  // other methods of WindowListener interface are unused here
  public void windowClosed(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
  
  /* --------------- ActionListener implementation -------------------------- */

  /**
   * show a license info when respective button is pressed
   * @param e  event that led to this method call
   */
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if(cmd.equals(CMD_SHOW_LICENSE)) {
      showLicense();
    }
  }
  
  /* --------------- class fields -------------------------- */
  
  /** component to manipulate start date of visible region of gantt chart */
  private JXDatePicker timeFrameStart;
  
  /** component to manipulate end date of visible region of gantt chart */
  private JXDatePicker timeFrameEnd;
  
  /* -------------- class constants ----------------------- */
  
  public static final String CMD_SHOW_LICENSE = "showLicense";
  
  /** license text */
  private static final String TXT_LICENSE = "License";

}
