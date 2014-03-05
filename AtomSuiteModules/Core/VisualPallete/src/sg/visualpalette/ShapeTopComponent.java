package sg.visualpalette;

import com.jme3.gde.scenecomposer.SceneComposerTopComponent;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;
import sg.visualpalette.palette.ShapePaletteSupport;

@ConvertAsProperties(dtd = "-//org.netbeans.shapesample//Shape//EN",
autostore = false)
@TopComponent.Description(preferredID = "ShapeTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "sg.visualpalette.ShapeTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_ShapeAction",
preferredID = "ShapeTopComponent")
@Messages({
    "CTL_ShapeAction=Shape",
    "CTL_ShapeTopComponent=Shape Window",
    "HINT_ShapeTopComponent=This is a Shape window"
})
public final class ShapeTopComponent extends TopComponent {

    public ShapeTopComponent() {
        initComponents();
        setName(Bundle.CTL_ShapeTopComponent());
        setToolTipText(Bundle.HINT_ShapeTopComponent());

        setLayout(new BorderLayout());

        GraphSceneImpl scene = new GraphSceneImpl();

        JScrollPane shapePane = new JScrollPane();

        shapePane.setViewportView(scene.createView());

        add(shapePane, BorderLayout.CENTER);
        add(scene.createSatelliteView(), BorderLayout.WEST);


        associateLookup(Lookups.singleton(ShapePaletteSupport.createPalette()));
        // Test if can be DnD in SceneViewer
        //SceneViewerTopComponent composer = SceneViewerTopComponent.findInstance();
        //composer.associateLookup(Lookups.singleton(ShapePaletteSupport.createPalette()));
                
                //Lookups.singleton(ShapePaletteSupport.createPalette()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
