/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.LayerManager;

import java.awt.BorderLayout;
import test.ui.node.EventChildFactory;
import test.ui.node.MyBeanTreeView;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//sg.gde.LayerManager//LayerManager_//EN",
autostore = false)
@TopComponent.Description(preferredID = "LayerManager_TopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "sg.gde.LayerManager.LayerManager_TopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_LayerManager_Action",
preferredID = "LayerManager_TopComponent")
public final class LayerManager_TopComponent extends TopComponent implements ExplorerManager.Provider {

    private MyBeanTreeView myBeanTree;

    public LayerManager_TopComponent() {
        initComponents();
        setName(NbBundle.getMessage(LayerManager_TopComponent.class, "CTL_LayerManager_TopComponent"));
        setToolTipText(NbBundle.getMessage(LayerManager_TopComponent.class, "HINT_LayerManager_TopComponent"));
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
        initLayers();

        associateLookup(ExplorerUtils.createLookup(mgr, getActionMap()));
        mgr.setRootContext(new AbstractNode(Children.create(new EventChildFactory(), true)));
        setDisplayName("My Editor");
    }

    void initLayers() {
        myBeanTree = new MyBeanTreeView();
        mgr.setRootContext(new AbstractNode(Children.create(new EventChildFactory(), true)));
        add(myBeanTree, BorderLayout.CENTER);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outline1 = new org.netbeans.swing.outline.Outline();

        setLayout(new java.awt.BorderLayout());
        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setViewportView(outline1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(441, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(LayerManager_TopComponent.class, "LayerManager_TopComponent.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.netbeans.swing.outline.Outline outline1;
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
    private final ExplorerManager mgr = new ExplorerManager();

    public ExplorerManager getExplorerManager() {
        return mgr;
    }
}