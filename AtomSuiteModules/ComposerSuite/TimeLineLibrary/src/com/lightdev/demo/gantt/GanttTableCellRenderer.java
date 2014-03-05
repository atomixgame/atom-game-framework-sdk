
package com.lightdev.demo.gantt;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A table cell renderer for gantt bars
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
public class GanttTableCellRenderer extends DefaultTableCellRenderer {

  /**
   * create a new instance of a GanttTableRenderer object
   * @param timeFrameProvider  the time frame provider to use
   */
  public GanttTableCellRenderer(TimeFrameProvider timeFrameProvider) {
    super();
    bar = new GanttBar();
    bar.setTimeFrameProvider(timeFrameProvider);
  }

  /**
   * get the component to render with
   * @param table  table object this renderer serves
   * @param value  the value to be edited
   * @param isSelected  indicates whether or not the cell is selected
   * @param hasFocus  indicator for whether or not the currently rendered component has the focus
   * @param row  number of row to be rendered
   * @param column number of column to be rendered
   * @return  renderer component to use
   */
  public Component getTableCellRendererComponent(JTable table, Object value, 
      boolean isSelected, boolean hasFocus, int row, int column) 
  {
    setValue(value);
    return bar;
  }
  
  /**
   * set the value to render inside component
   */
  protected void setValue(Object value) {
    if(value != null && value instanceof Activity) {
      bar.setActivity((Activity) value); 
    }
  }
  
  /** reference to gantt bar object to use for rendering */
  private GanttBar bar;

}
