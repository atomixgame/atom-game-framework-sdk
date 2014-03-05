package jme3timeline.components;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EffectControlComponent extends JPanel {

    JXTaskPaneContainer inspectorPanel;
    String[] effects = {"Effect1", "Effect2", "Effect3"};
    
    public EffectControlComponent() {
        inspectorPanel = new JXTaskPaneContainer();
        //BoxLayout boxLayout = new BoxLayout(inspectorPanel, BoxLayout.Y_AXIS);
        setLayout(new BorderLayout());
        add(new JScrollPane(inspectorPanel), BorderLayout.CENTER);
        inspectorPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        createUI();
    }


    public void createUI() {
        inspectorPanel.removeAll();

        for (int i = 0; i < effects.length; i++) {
            String comp = effects[i];
            JPanel insidePanel = new JPanel();
            insidePanel.add(new JLabel("Description for" + comp));
            JXTaskPane taskPanel = new JXTaskPane();
            taskPanel.setTitle(comp);
            taskPanel.add(insidePanel);
            
            inspectorPanel.add(taskPanel);
        }
        //this.validate();
        //inspectorPanel.repaint();
        //this.repaint();
    }
}
