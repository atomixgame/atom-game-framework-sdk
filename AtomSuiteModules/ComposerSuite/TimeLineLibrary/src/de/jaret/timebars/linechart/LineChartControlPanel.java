/*
 *  File: LineChartControlPanel.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package de.jaret.timebars.linechart;

import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Control panel for the line chart example.
 * 
 * @author Peter Kliem
 * @version $Id: LineChartControlPanel.java 766 2008-05-28 21:36:48Z kliem $
 */
public class LineChartControlPanel extends JPanel {

    private TimeBarViewer _tbv;

    public LineChartControlPanel(TimeBarViewer tbv) {
        _tbv = tbv;
        createControls(this);
    }

    /**
     * @param panel
     */
    private void createControls(LineChartControlPanel panel) {

        panel.setLayout(new BorderLayout());
        final JSlider pixPerSecondsScale = new JSlider(JSlider.HORIZONTAL);
        pixPerSecondsScale.setMaximum(700);
        pixPerSecondsScale.setMinimum(1);
        if (_tbv.getPixelPerSecond() * (24.0 * 60.0 * 60.0) > 700) {
            pixPerSecondsScale.setMaximum((int) (_tbv.getPixelPerSecond() * (24.0 * 60.0 * 60.0)));
        }
        pixPerSecondsScale.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event) {
                int val = pixPerSecondsScale.getValue();
                double pps = ((double) val) / (24.0 * 60.0 * 60.0);
                //System.out.println("scale " + val + "pps " + pps);
                _tbv.setPixelPerSecond(pps);
            }
        });
        pixPerSecondsScale.setValue((int) (_tbv.getPixelPerSecond() * (24.0 * 60.0 * 60.0)));


        panel.add(pixPerSecondsScale, BorderLayout.CENTER);

        final JCheckBox optScrollingCheck = new JCheckBox("check");
        optScrollingCheck.setText("Use optimized scrolling");
        optScrollingCheck.setSelected(_tbv.getOptimizeScrolling());
        optScrollingCheck.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                _tbv.setOptimizeScrolling(optScrollingCheck.isSelected());
            }
        });


        panel.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Focus");
            }
        });


        panel.add(optScrollingCheck, BorderLayout.EAST);
    }

}
