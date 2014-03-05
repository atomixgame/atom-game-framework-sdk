package org.myorg.myviewer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows MyViewer component.
 */
public class MyViewerAction extends AbstractAction {
    
    public MyViewerAction() {
        super(NbBundle.getMessage(MyViewerAction.class, "CTL_MyViewerAction"));
        //        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(MyViewerTopComponent.ICON_PATH, true)));
    }
    
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = MyViewerTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
    
}
