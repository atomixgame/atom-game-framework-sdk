
package com.lightdev.demo.gantt;

import java.util.Date;

/**
 * Classes interested to provide activity data to a gantt bar need to implement
 * this interface.
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
public interface Activity {
  
  /**
   * get the name of this activity
   * @return  the activity name
   */
  public String getName();
  
  /**
   * get the start date of this activity
   * @return  the start date
   */
  public Date getStart();
  
  /**
   * get the end date of this activity
   * @return  the end date
   */
  public Date getEnd();
  
  /**
   * get the type of this activity
   * @return  the activity type, one of ACTIVITY_TYPE_BAR and ACTIVITY_TYPE_GROUP
   */
  public int getType();
  
  /**
   * set the name of this activity
   * @param name the name
   */
  public void setName(String name);
  
  /**
   * set the start date of tis activity
   * @param start the start date
   */
  public void setStart(Date start);
  
  /**
   * set the end date of this activity
   * @param end  the end date
   */
  public void setEnd(Date end);
  
  /**
   * set the type of this activity
   * @param type the activity type, one of ACTIVITY_TYPE_BAR and ACTIVITY_TYPE_GROUP
   * @param type
   */
  public void setType(int type);
  
  /** activity type indicator constant */
  public static final int ACTIVITY_TYPE_BAR = 1;
  /** activity type indicator constant */
  public static final int ACTIVITY_TYPE_GROUP = 2;
}
