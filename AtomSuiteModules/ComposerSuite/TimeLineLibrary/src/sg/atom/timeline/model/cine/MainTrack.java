/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.timeline.model.cine;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MainTrack extends TimeLineIntervalModel {

    String name;

    public MainTrack(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return "Track";
    }
}
