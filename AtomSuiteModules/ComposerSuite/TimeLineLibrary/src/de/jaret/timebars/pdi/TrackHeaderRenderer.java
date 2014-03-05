package de.jaret.timebars.pdi;

import sg.atom.timeline.model.cine.TimeLineTrackRow;
import de.jaret.util.ui.timebars.model.TimeBarRowHeader;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.HeaderRenderer;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * @author Peter Kliem
 * @version $Id: PdiHeaderRenderer.java 237 2007-02-10 21:11:50Z olk $
 */
public class TrackHeaderRenderer implements HeaderRenderer {

    JLabel _component = new JLabel("", JLabel.RIGHT);

    @Override
    public JComponent getHeaderRendererComponent(TimeBarViewer tbv, TimeBarRowHeader value, boolean isSelected) {
        if (value != null) {
            _component.setText(((TimeLineTrackRow) value).getName());
        } else {
            _component.setText("No name");
        }

        if (isSelected) {
            _component.setOpaque(true);
            _component.setBackground(Color.BLUE);
        } else {
            _component.setBackground(Color.WHITE);
        }
        
        _component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(" Clicked !");
            }
            
        });
        
        //_component.
        return _component;
    }

    @Override
    public int getWidth() {
        return 100;
    }
}
