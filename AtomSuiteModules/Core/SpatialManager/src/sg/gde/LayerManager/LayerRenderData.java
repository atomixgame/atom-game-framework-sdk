/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.LayerManager;

import com.jme3.animation.Bone;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.swing.outline.RenderDataProvider;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 *
 * @author hungcuong
 */
class LayerRenderData implements RenderDataProvider {

    Icon smallIcon;
    Icon bigIcon;
    Icon openIcon;
    Icon closeIcon;
    LayerRenderData() {
        try {
            openIcon = createImageIcon("/IconImage/24/eyeopen24.png",
                    "smallBoneIcon");
            closeIcon = createImageIcon("/IconImage/24/eyeclose24.png",
                    "smallBoneIcon"); 
            
        } catch (Exception ex) {
            NotifyDescriptor des = new NotifyDescriptor.Message(ex.getLocalizedMessage());
            DialogDisplayer.getDefault().notifyLater(des);

        }

    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description) throws Exception {
        java.net.URL imgURL = LayerRenderData.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            throw new Exception("Couldn't find file: " + path);
            //return null;
        }
    }

    @Override
    public java.awt.Color getBackground(Object o) {
        return null;
    }

    @Override
    public String getDisplayName(Object o) {
        return ((Bone) o).getName();
    }

    @Override
    public java.awt.Color getForeground(Object o) {
        Bone f = (Bone) o;
        if (f.getChildren().isEmpty()) {
            return Color.BLACK;
        } else {
            return Color.BLUE;
        }
        //return null;
    }

    @Override
    public javax.swing.Icon getIcon(Object o) {

        Bone f = (Bone) o;
        if (f.getChildren().isEmpty()) {
            return closeIcon;
        } else {
            return openIcon;
        }


    }

    @Override
    public String getTooltipText(Object o) {
        Bone f = (Bone) o;
        return f.getName();
    }

    @Override
    public boolean isHtmlDisplayName(Object o) {
        return false;
    }
}
