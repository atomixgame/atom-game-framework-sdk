/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3timeline.components;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import jme3timeline.TestAtomTimeLine;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TimeControlComponent extends JPanel {

    public TimeControlComponent() {
        super();
        ImageIcon imgPlay = TestAtomTimeLine.createImageIcon("retina/mimi/Copy16.png");
        JButton btnPlay = new JButton(imgPlay);
        add(btnPlay);

        ImageIcon imgStop = TestAtomTimeLine.createImageIcon("retina/mimi/Cut16.png");
        JButton btnStop = new JButton(imgStop);
        add(btnStop);

        ImageIcon imgDelete = TestAtomTimeLine.createImageIcon("retina/mimi/Delete16.png");
        JButton btnDelete = new JButton(imgDelete);
        add(btnDelete);
    }
}
