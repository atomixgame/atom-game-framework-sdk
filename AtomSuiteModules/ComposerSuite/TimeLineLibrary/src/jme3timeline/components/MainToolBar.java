package jme3timeline.components;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import jme3timeline.TestAtomTimeLine;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MainToolBar extends JToolBar {

    public MainToolBar() {
        super();

        JToolBar toolbar = this;
        ImageIcon imgCopy = TestAtomTimeLine.createImageIcon("retina/mimi/Copy16.png");
        JButton btnCopy = new JButton(imgCopy);
        toolbar.add(btnCopy);

        ImageIcon imgCut = TestAtomTimeLine.createImageIcon("retina/mimi/Cut16.png");
        JButton btnCut = new JButton(imgCut);
        toolbar.add(btnCut);

        ImageIcon imgDelete = TestAtomTimeLine.createImageIcon("retina/mimi/Delete16.png");
        JButton btnDelete = new JButton(imgDelete);
        toolbar.add(btnDelete);

        //toolbar.add(new JSeparator());
        ImageIcon imgBack = TestAtomTimeLine.createImageIcon("retina/Minicons/png24/arrow 31.png");
        JButton btnBack = new JButton(imgBack);
        toolbar.add(btnBack);

        ImageIcon imgPlay = TestAtomTimeLine.createImageIcon("retina/Minicons/png24/arrow 24.png");
        JButton btnPlay = new JButton(imgPlay);
        toolbar.add(btnPlay);
        
        ImageIcon imgRec = TestAtomTimeLine.createImageIcon("retina/Minicons/png24/settings 2.png");
        JButton btnRec = new JButton(imgRec);
        toolbar.add(btnRec);

        ImageIcon imgStop = TestAtomTimeLine.createImageIcon("retina/Minicons/png24/stop 1.png");
        JButton btnStop = new JButton(imgStop);
        toolbar.add(btnStop);

        ImageIcon imgPause = TestAtomTimeLine.createImageIcon("retina/Minicons/png24/pause 1.png");
        JButton btnPause = new JButton(imgPause);
        toolbar.add(btnPause);

        ImageIcon imgNext = TestAtomTimeLine.createImageIcon("retina/Minicons/png24/arrow 32.png");
        JButton btnNext = new JButton(imgNext);
        toolbar.add(btnNext);




    }
}
