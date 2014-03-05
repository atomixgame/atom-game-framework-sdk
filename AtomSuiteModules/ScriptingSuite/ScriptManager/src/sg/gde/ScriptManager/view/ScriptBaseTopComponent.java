package sg.gde.ScriptManager.view;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openide.windows.TopComponent;
import sg.gde.ScriptManager.control.NetbeanGroovyScriptEngine;

/**
 *
 * @author hungcuong
 */
public class ScriptBaseTopComponent extends TopComponent {

    NetbeanGroovyScriptEngine scriptManager;
    JPanel childPanel;
    private JLabel lblTitle;
    private JPanel controlPanel;

    public ScriptBaseTopComponent(NetbeanGroovyScriptEngine scriptManager, String title) {
        super();
        setName(title);
        this.scriptManager = scriptManager;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        lblTitle = new JLabel("Script Panel");
        //
        controlPanel = new JPanel();
        controlPanel.add(lblTitle);
        add(controlPanel, BorderLayout.NORTH);
        Logger.getLogger("Script Engine").info("init Panel!");
    }

    public void open(String scriptName) {
        childPanel = scriptManager.runPanel(scriptName);
        add(childPanel, BorderLayout.CENTER);
        super.open();
    }
}
