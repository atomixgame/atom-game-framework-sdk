/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.timeline.model.cine;

import de.jaret.util.date.Interval;
import de.jaret.util.ui.timebars.model.AbstractTimeBarRowModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TimeLineTrackRow extends AbstractTimeBarRowModel {

    String name;
    private ArrayList<MainTrack> mainTracks = new ArrayList<MainTrack>();

    public TimeLineTrackRow(String name) {
        this.name = name;
    }

    @Override
    public List<Interval> getIntervals() {
        ArrayList<Interval> result = new ArrayList<Interval>();
        for (MainTrack i : mainTracks) {
            result.add(i);
        }
        return result;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public ArrayList<MainTrack> getMainTracks() {
        return mainTracks;
    }
    
    
}
