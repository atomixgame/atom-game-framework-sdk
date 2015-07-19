package com.lightdev.demo.gantt;

import java.util.Date;

/**
 * Classes interested to provide a time frame to render gantt bars in need to
 * implement this interface.
 *
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License, for
 * details see file license.txt in the distribution package of this software
 *
 * @version 1, 30.03.2006
 */
public interface TimeFrameProvider {

    /**
     * get the start date of this time frame
     *
     * @return the start date
     */
    public Date getFrameStart();

    /**
     * get the end date of this time frame
     *
     * @return the end date
     */
    public Date getFrameEnd();
    /**
     * action command to change start date
     */
    public static final String CMD_CHANGE_FRAME_START_DATE = "changeFrameStartDate";
    /**
     * action command to change end date
     */
    public static final String CMD_CHANGE_FRAME_END_DATE = "changeFrameEndDate";
}
