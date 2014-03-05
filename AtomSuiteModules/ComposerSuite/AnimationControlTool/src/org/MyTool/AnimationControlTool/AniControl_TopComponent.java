/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.MyTool.AnimationControlTool;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.gde.core.scene.SceneApplication;
import com.jme3.gde.core.sceneexplorer.nodes.JmeAnimControl;
import com.jme3.gde.core.sceneexplorer.nodes.JmeSpatial;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import java.awt.Component;
import java.util.Collection;
import java.util.concurrent.Callable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupListener;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.MyTool.AnimationControlTool//AniControl_//EN",
autostore = false)
@TopComponent.Description(preferredID = "AniControl_TopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "org.MyTool.AnimationControlTool.AniControl_TopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_AniControl_Action",
preferredID = "AniControl_TopComponent")
public final class AniControl_TopComponent extends TopComponent implements LookupListener, AppState {

    private DefaultComboBoxModel comboModel;
    private BoneListPanel boneListPanel;

    public AniControl_TopComponent() {
        initComponents();
        setName(NbBundle.getMessage(AniControl_TopComponent.class, "CTL_AniControl_TopComponent"));
        setToolTipText(NbBundle.getMessage(AniControl_TopComponent.class, "HINT_AniControl_TopComponent"));
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
        //initTestPanel();
        boneListPanel.initBonePanel();
        //enableLogger();
    }

    void enableLogger() {
        //Logger.getLogger("").setLevel(Level.FINE);
        //Logger.getLogger("").info("Hehe is good");
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aniControlManageBean1 = new org.MyTool.AnimationControlTool.AniControlManageBean();
        jPanel2 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelAnimation = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblTimePos = new javax.swing.JLabel();
        sliderAnimPos = new javax.swing.JSlider();
        jComboBox1 = new javax.swing.JComboBox();
        lblMaxTime = new javax.swing.JLabel();
        btnForward = new javax.swing.JButton();
        btnBackward = new javax.swing.JButton();
        btnPlayPause = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jSpinner1 = new javax.swing.JSpinner();
        sliderAnimSpeed = new javax.swing.JSlider();
        jCheckBox1 = new javax.swing.JCheckBox();
        lblSpeedValue = new javax.swing.JLabel();
        btnSpeedDown = new javax.swing.JButton();
        btnSpeedUp = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        animList = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        comboAnimList = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelTest = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jPanel2.border.title"))); // NOI18N
        jPanel2.setMinimumSize(new java.awt.Dimension(53, 35));
        jPanel2.setPreferredSize(new java.awt.Dimension(53, 35));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblName, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.lblName.text")); // NOI18N
        lblName.setName("lblName"); // NOI18N
        jPanel2.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));
        lblName.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jLabel4.AccessibleContext.accessibleName")); // NOI18N

        add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel5.setPreferredSize(new java.awt.Dimension(100, 80));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblTimePos, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.lblTimePos.text")); // NOI18N
        jPanel7.add(lblTimePos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, -1, -1));

        sliderAnimPos.setMinorTickSpacing(10);
        sliderAnimPos.setPaintLabels(true);
        sliderAnimPos.setPaintTicks(true);
        sliderAnimPos.setToolTipText(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.sliderAnimPos.toolTipText")); // NOI18N
        sliderAnimPos.setValue(0);
        sliderAnimPos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderAnimPosStateChanged(evt);
            }
        });
        jPanel7.add(sliderAnimPos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel7.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        org.openide.awt.Mnemonics.setLocalizedText(lblMaxTime, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.lblMaxTime.text")); // NOI18N
        jPanel7.add(lblMaxTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, -1, -1));

        org.openide.awt.Mnemonics.setLocalizedText(btnForward, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.btnForward.text")); // NOI18N
        btnForward.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnForward.setBorderPainted(false);
        jPanel7.add(btnForward, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 20, 20));

        org.openide.awt.Mnemonics.setLocalizedText(btnBackward, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.btnBackward.text")); // NOI18N
        btnBackward.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnBackward.setBorderPainted(false);
        btnBackward.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackwardMouseClicked(evt);
            }
        });
        jPanel7.add(btnBackward, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 20, 20));

        org.openide.awt.Mnemonics.setLocalizedText(btnPlayPause, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.btnPlayPause.text")); // NOI18N
        btnPlayPause.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPlayPause.setBorderPainted(false);
        btnPlayPause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlayPauseMouseClicked(evt);
            }
        });
        jPanel7.add(btnPlayPause, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 20, 20));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jPanel8.border.title"))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        sliderAnimSpeed.setMaximum(20);
        sliderAnimSpeed.setMinimum(-20);
        sliderAnimSpeed.setValue(0);
        sliderAnimSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderAnimSpeedStateChanged(evt);
            }
        });
        jPanel8.add(sliderAnimSpeed, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jCheckBox1.text")); // NOI18N
        jPanel8.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        org.openide.awt.Mnemonics.setLocalizedText(lblSpeedValue, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.lblSpeedValue.text")); // NOI18N
        jPanel8.add(lblSpeedValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        org.openide.awt.Mnemonics.setLocalizedText(btnSpeedDown, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.btnSpeedDown.text")); // NOI18N
        btnSpeedDown.setActionCommand(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.btnSpeedDown.actionCommand")); // NOI18N
        btnSpeedDown.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSpeedDown.setBorderPainted(false);
        btnSpeedDown.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSpeedDownMouseClicked(evt);
            }
        });
        jPanel8.add(btnSpeedDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 20, 20));

        org.openide.awt.Mnemonics.setLocalizedText(btnSpeedUp, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.btnSpeedUp.text")); // NOI18N
        btnSpeedUp.setActionCommand(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.btnSpeedUp.actionCommand")); // NOI18N
        btnSpeedUp.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSpeedUp.setBorderPainted(false);
        btnSpeedUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSpeedUpMouseClicked(evt);
            }
        });
        jPanel8.add(btnSpeedUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 20, 20));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        animList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(animList);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jLabel4.text")); // NOI18N

        comboAnimList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jLabel5.text")); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboAnimList, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(579, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboAnimList, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelAnimationLayout = new javax.swing.GroupLayout(panelAnimation);
        panelAnimation.setLayout(panelAnimationLayout);
        panelAnimationLayout.setHorizontalGroup(
            panelAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelAnimationLayout.setVerticalGroup(
            panelAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnimationLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.panelAnimation.TabConstraints.tabTitle"), panelAnimation); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        panelTest.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.panelTest.TabConstraints.tabTitle"), panelTest); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AniControl_TopComponent.class, "AniControl_TopComponent.jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void sliderAnimPosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderAnimPosStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sliderAnimPosStateChanged

    private void sliderAnimSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderAnimSpeedStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sliderAnimSpeedStateChanged

    private void btnSpeedDownMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSpeedDownMouseClicked
        // TODO add your handling code here:
        SceneApplication.getApplication().enqueue(new Callable() {

            public Object call() throws Exception {
                if (playState != 0) {
                    if (channel.getSpeed() > 0.1f) {
                        channel.setSpeed(channel.getSpeed() / 2);
                        currentSpeed = channel.getSpeed();
                    } else {
                        currentSpeed = channel.getSpeed();
                    }
                }
                return null;
            }
        });
        updateSliderState();
    }//GEN-LAST:event_btnSpeedDownMouseClicked

    private void btnSpeedUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSpeedUpMouseClicked
        // TODO add your handling code here:
        SceneApplication.getApplication().enqueue(new Callable() {

            public Object call() throws Exception {
                if (playState != 0) {
                    if (channel.getSpeed() < 10) {
                        channel.setSpeed(channel.getSpeed() * 2);
                        currentSpeed = channel.getSpeed();
                    } else {
                        currentSpeed = channel.getSpeed();
                    }
                }
                return null;
            }
        });
        updateSliderState();
    }//GEN-LAST:event_btnSpeedUpMouseClicked

    private void btnBackwardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackwardMouseClicked
        // TODO add your handling code here:
        SceneApplication.getApplication().enqueue(new Callable() {

            public Object call() throws Exception {
                float spf = (float) 1 / 24;
                currentTime = channel.getTime();
                float maxTime = channel.getAnimMaxTime();
                if (currentTime - spf >= 0) {
                    currentTime = currentTime - spf;
                } else {
                    currentTime = maxTime;
                }
                System.out.println("Time : " + currentTime + "  _ " + spf);
                channel.setTime(currentTime);
                channel.setSpeed(0);

                playState = 1;
                return null;
            }
        });

    }//GEN-LAST:event_btnBackwardMouseClicked

    private void btnPlayPauseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlayPauseMouseClicked
        // TODO add your handling code here:
        SceneApplication.getApplication().enqueue(new Callable() {

            public Object call() throws Exception {
                if (playState == 0) {
                    //currentTime = channel.getTime();
                    currentSpeed = channel.getSpeed();
                    //channel.setTime(currentTime);
                    channel.setSpeed(0);
                    playState = 1;
                } else {
                    channel.setSpeed(currentSpeed);
                    playState = 0;
                }
                return null;
            }
        });
    }//GEN-LAST:event_btnPlayPauseMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.MyTool.AnimationControlTool.AniControlManageBean aniControlManageBean1;
    private javax.swing.JList animList;
    private javax.swing.JButton btnBackward;
    private javax.swing.JButton btnForward;
    private javax.swing.JButton btnPlayPause;
    private javax.swing.JButton btnSpeedDown;
    private javax.swing.JButton btnSpeedUp;
    private javax.swing.JComboBox comboAnimList;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMaxTime;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSpeedValue;
    private javax.swing.JLabel lblTimePos;
    private javax.swing.JPanel panelAnimation;
    private javax.swing.JPanel panelTest;
    private javax.swing.JSlider sliderAnimPos;
    private javax.swing.JSlider sliderAnimSpeed;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        registerListener();
        setActive(false);
        disableAll();
        clearAllData();
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
        endJob();
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
    private Result<JmeAnimControl> result;

//method to register the listener;
    private void registerListener() {
        TopComponent tc = WindowManager.getDefault().findTopComponent("SceneExplorerTopComponent");

        Lookup tcLookup = tc.getLookup();
        result = tcLookup.lookupResult(JmeAnimControl.class);
        result.addLookupListener(this);
    }
    
    public AnimChannel channel;
    public AnimControl animControl;
    private String currentAnimName = "";
    private float currentTime = 0;
    private float currentSpeed = 1;
    private int playState = 0;
    private float maxTime;
    Spatial realSpatial;
    boolean isActiveNow;
    DefaultListModel listModel;
//implements org.openide.util.LookupListener (called from AWT thread)

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<JmeAnimControl> items = (Collection<JmeAnimControl>) result.allInstances();
        for (JmeAnimControl node1 : items) {

            JmeSpatial parentNode = (JmeSpatial) node1.getParentNode();
            String des1 = parentNode.getDisplayName();
            lblName.setText(des1);
            animControl = node1.getLookup().lookup(AnimControl.class);
            realSpatial = parentNode.getLookup().lookup(Spatial.class);
            updateChannels();
            updateAnimList();
            boneListPanel.updateBoneTable();
            updateTimeValue();
            updateSliderState();
            boneListPanel.initBonePanel();
            if (!SceneApplication.getApplication().getStateManager().hasState(this)) {
                SceneApplication.getApplication().getStateManager().attach(this);
            }
            setActive(true);
            enableAll();
            return;
        }

        if (items.isEmpty()) {
            endJob();
        }

    }

    void endJob() {
        if (SceneApplication.getApplication().getStateManager().hasState(this)) {
            SceneApplication.getApplication().getStateManager().detach(this);
        }
        //lblName.setText("Out ->");
        clearAllData();
        disableAll();
    }

    void updateAnimList() {
        listModel = new DefaultListModel();
        comboModel = new DefaultComboBoxModel();

        for (String name : animControl.getAnimationNames()) {
            listModel.addElement(name);
            comboModel.addElement(name);
        }
        animList.setModel(listModel);
        comboAnimList.setModel(comboModel);

        animList.setSelectedValue(currentAnimName, true);
        comboAnimList.setSelectedItem(currentAnimName);
    }

    void updateChannels() {
        if (animControl.getNumChannels() > 0) {
            channel = animControl.getChannel(0);
            //lblName.setText("getChannel");
        } else {
            channel = animControl.createChannel();
            //lblName.setText("CreateChanel");
        }
    }

    void updateSliderState() {
        int max = sliderAnimPos.getMaximum();
        int min = sliderAnimPos.getMinimum();
        sliderAnimPos.setValue(min + Math.round(currentTime * max / maxTime));

        sliderAnimSpeed.setValue(Math.round(currentSpeed));
        lblSpeedValue.setText(currentSpeed + "X");
    }

    void updateTimeValue() {
        currentTime = channel.getTime();
        maxTime = channel.getAnimMaxTime();
        lblMaxTime.setText(new Float(maxTime).toString());
        lblTimePos.setText(new Float(currentTime).toString());
    }

    void clearAllData() {
        if (listModel != null) {

            listModel.clear();
        } else {
            animList.setListData(new String[]{});
        }
        if (comboModel != null) {
            comboModel.removeAllElements();
        } else {
            comboAnimList.removeAllItems();
        }
        sliderAnimPos.setValue(0);
        sliderAnimSpeed.setValue(1);
        lblSpeedValue.setText("1X");
        lblName.setText("Name");
        lblMaxTime.setText("00:00");
        lblTimePos.setText("00:00");

    }

    @Override
    public void update(float f) {

        if (isActive()) {
            if (animControl.getNumChannels() > 0 && animControl.getChannel(0) != null && channel != animControl.getChannel(0)) {
                updateChannels();
            }

            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    String currentAnimName = channel.getAnimationName();
                    if ((currentAnimName != null) && (!currentAnimName.equals("null"))) {
                        updateTimeValue();
                        updateSliderState();
                    }
                }
            });


            //lblName.setText(currentAnimName + count + "is Active & Update");
            count++;
        }


        //Logger.getLogger(AniControl_TopComponent.class.getName()).warning("update!");

    }
    private int count = 0;

    @Override
    public void initialize(AppStateManager asm, Application aplctn) {
        isActiveNow = false;
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    public void setActive(boolean bln) {
        isActiveNow = bln;
    }

    public boolean isActive() {
        return isActiveNow;
    }

    @Override
    public void stateAttached(AppStateManager asm) {
    }

    @Override
    public void stateDetached(AppStateManager asm) {
    }

    @Override
    public void render(RenderManager rm) {
    }

    @Override
    public void postRender() {
    }

    @Override
    public void cleanup() {
    }

    private void disableAll() {
        //jTabbedPane1.setEnabled(false);
        disablePanel(panelAnimation);
    }

    void disablePanel(JPanel panel) {
        for (Component component : panel.getComponents()) {
            component.setEnabled(false);
            component.setFocusable(false);
            if (component instanceof JPanel) {
                disablePanel((JPanel) component);
            }
        }
        return;
    }

    void enablePanel(JPanel panel) {
        for (Component component : panel.getComponents()) {
            component.setEnabled(true);
            component.setFocusable(true);
            if (component instanceof JPanel) {
                enablePanel((JPanel) component);
            }
        }
    }

    private void enableAll() {
        //jTabbedPane1.setEnabled(true);
        enablePanel(panelAnimation);
    }
}
