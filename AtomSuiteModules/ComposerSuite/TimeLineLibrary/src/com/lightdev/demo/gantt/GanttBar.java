
package com.lightdev.demo.gantt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JComponent;

/**
 * A component to paint a gantt bar or gantt group indicator.
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
public class GanttBar extends JComponent {
  
  /**
   * create a new instance of a GanttBar object
   */
  public GanttBar() {
    super();
  }
  
  /**
   * draw a gantt bar
   */
  public void paint(Graphics g) {
    // paint parent(s) first
    super.paint(g);
    
    // only act when requirements are met
    if(activity != null && timeFrameProvider != null) {    
      int widthInPixel = getWidth();
      
      // get start and end of time frame to render in
      double visibleStartTime = (double) timeFrameProvider.getFrameStart().getTime();
      double visibleEndTime = (double) timeFrameProvider.getFrameEnd().getTime();
      
      // get start and end of activity to render
      double barStartTime = (double) activity.getStart().getTime();
      double barEndTime = (double) activity.getEnd().getTime();
      
      /*
       * compute the 'duration' of one pixel, i.e. which duration in current time frame 
       * is represented by one pixel
       */
      double pixelDuration = (visibleEndTime - visibleStartTime) / (double) widthInPixel;
      
      // compute left offset
      int leftOffset = 0;
      if(visibleStartTime <= barStartTime && visibleEndTime >= barStartTime) {
        leftOffset = (int) ((barStartTime - visibleStartTime) / pixelDuration);
      }
      
      // compute right offset
      int rightOffset = 0;
      if(visibleStartTime <= barEndTime && visibleEndTime >= barEndTime) 
      {
        rightOffset = (int) ((visibleEndTime - barEndTime) / pixelDuration);
      }
      
      if(activity.getType() == Activity.ACTIVITY_TYPE_BAR) {
        // draw gantt bar
        g.setColor(Color.BLUE);
        g.fillRect(leftOffset, 
            TOP_OFFSET, 
            widthInPixel - leftOffset - rightOffset, 
            getHeight() - TOP_OFFSET * 2);
      }
      else {
        // draw group polygon
        int doublePinSize = 2 * PIN_SIZE;
        int xRight = leftOffset + (widthInPixel - leftOffset - rightOffset);
        int yBottom = getHeight() - doublePinSize;
        int pinBottom = yBottom + PIN_SIZE;
        Polygon polygon = new Polygon();
        polygon.addPoint(leftOffset, TOP_OFFSET);
        polygon.addPoint(xRight, TOP_OFFSET);
        polygon.addPoint(xRight, yBottom);
        polygon.addPoint(xRight - PIN_SIZE, pinBottom);
        polygon.addPoint(xRight - doublePinSize, yBottom);
        polygon.addPoint(leftOffset + doublePinSize, yBottom);
        polygon.addPoint(leftOffset + PIN_SIZE, pinBottom);
        polygon.addPoint(leftOffset, yBottom);
        g.setColor(Color.BLACK);
        g.fillPolygon(polygon);
      }
    }
  }
  
  /**
   * set the activity this gantt bar should draw
   * @param activity  the activity to draw
   */
  public void setActivity(Activity activity) {
    this.activity = activity;
  }
  
  /**
   * set the time frame provider for this gantt bar
   * @param timeFrameProvider  the time frame provider to use
   */
  public void setTimeFrameProvider(TimeFrameProvider timeFrameProvider) {
    this.timeFrameProvider = timeFrameProvider;
  }
  
  /** reference to activity object to draw */
  private Activity activity;
  
  /** reference to time frame provider object to use */
  private TimeFrameProvider timeFrameProvider;
  
  /** size of pin of group polygon in pixel */
  private static final int PIN_SIZE = 4;
  
  /** number of pixel for top margin of bar / group polygon */
  private static final int TOP_OFFSET = 2;
  
}
