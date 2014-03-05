package jme3timeline.components;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTree;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EffectComponent extends JPanel {

    public EffectComponent() {
        setLayout(new BorderLayout());
        add(new JXTree(), BorderLayout.CENTER);

        //add(new JXSearchField(), BorderLayout.NORTH);
    }
}
