/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.timeline.model.cine;

import de.jaret.util.ui.timebars.model.AbstractTimeBarModel;
import de.jaret.util.ui.timebars.model.TimeBarRow;
import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class CinematicModel extends AbstractTimeBarModel {
    
    ArrayList<TimeLineTrackRow> mainTracks = new ArrayList<TimeLineTrackRow>();
    ArrayList<TimeLineTrackRow> eventTracks = new ArrayList<TimeLineTrackRow>();

    @Override
    public TimeBarRow getRow(int row) {
        return mainTracks.get(row);
    }
    
    @Override
    public int getRowCount() {
        return mainTracks.size();
    }
    
    public void addTrack(TimeLineTrackRow mainTrack) {
        mainTracks.add(mainTrack);
    }

    public ArrayList<TimeLineTrackRow> getMainTracks() {
        return mainTracks;
    }

    public ArrayList<TimeLineTrackRow> getEventTracks() {
        return eventTracks;
    }
    
    
}
