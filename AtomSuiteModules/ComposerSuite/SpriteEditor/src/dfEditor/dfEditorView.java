/* 
 *  Copyright 2012 Samuel Taylor
 * 
 *  This file is part of darkFunction Editor
 *
 *  darkFunction Editor is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  darkFunction Editor is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with darkFunction Editor.  If not, see <http://www.gnu.org/licenses/>.
 */
package dfEditor;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import javax.swing.JOptionPane;
import dfEditor.command.CommandManager;
import dfEditor.events.TaskChangeListener;
import dfEditor.animation.AnimationController;
import dfEditor.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * The application's main frame.
 *
 * FIXME: Morph this to Netbean platform architecture.
 */
public class dfEditorView implements TaskChangeListener {

    private final dfEditorSwingMain main;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItemSave;
    private javax.swing.JMenuItem menuItemSaveAs;
    private javax.swing.JMenuItem newAnimationItem;
    private javax.swing.JMenuItem newSpritesheetItem;
    private javax.swing.JMenuItem redoMenuItem;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JMenuItem undoMenuItem;
    // End of variables declaration//GEN-END:variables
    private JDialog aboutBox;

    public dfEditorView(dfEditorSwingMain main) {
        this.main = main;

        initComponents();

        //FIXME: Remove initial application infos
//        helpLabel.setText("http://www.darkfunction.com");
//        java.net.URL imgURL = this.getClass().getResource("resources/main_icons/Star.png");
//        ImageIcon ii = new ImageIcon(imgURL);
//        this.getFrame().setIconImage(ii.getImage());
//        this.getFrame().addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
////                dfEditorApp.getApplication().exit();
//            }
//        });
    }

    public void willExit(java.util.EventObject aObj) {
    }

    public boolean canExit(java.util.EventObject aObj) {
        for (int i = 0; i < tabbedPane.getTabCount(); ++i) {
            dfEditorTask tab = (dfEditorTask) (tabbedPane.getComponentAt(i));

            if (!tab.hasBeenModified()) {
                continue;
            }

            String[] choices = {" Save ", " Discard ", " Cancel "};

            String msg = "You have not saved " + tab.getName() + ". Would you like to save it now?";
            int choice = JOptionPane.showOptionDialog(
                    this.getFrame() // Center in window.
                    , msg // Message
                    , "Save changes?" // Title in titlebar
                    , JOptionPane.YES_NO_OPTION // Option type
                    , JOptionPane.WARNING_MESSAGE // messageType
                    , null // Icon (none)
                    , choices // Button text as above.
                    , " Save " // Default button's label
            );
            switch (choice) {
                case 0:
                    if (tab.save()) {
                        tabbedPane.remove(i);
                    } else {
                        return false;
                    }
                    break;
                case 1:
                    tabbedPane.remove(i);
                    i--;
                    break;
                case 2:
                    return false;
                default:
                    return false;
            }
        }
        return true;
    }

//    public void showAboutBox() {
//        if (aboutBox == null) {
//            JFrame mainFrame = dfEditorApp.getApplication().getMainFrame();
//            aboutBox = new dfEditorAboutBoxFree(mainFrame);
//            aboutBox.setLocationRelativeTo(mainFrame);
//        }
//        dfEditorApp.getApplication().show(aboutBox);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    //setComponent(mainPanel);
    //setMenuBar(menuBar);
    //setStatusBar(statusPanel);

    private void menuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSaveAsActionPerformed
        dfEditorTask currentTab = (dfEditorTask) tabbedPane.getSelectedComponent();

        if (currentTab != null) {
            if (currentTab.saveAs()) {
                java.io.File file = currentTab.getSavedFile();
                if (file != null && file.exists()) {
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
                }
            }
        }
    }//GEN-LAST:event_menuItemSaveAsActionPerformed

    private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed
        ((dfEditorTask) tabbedPane.getSelectedComponent()).undo();
    }//GEN-LAST:event_undoMenuItemActionPerformed

    private void redoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoMenuItemActionPerformed
        ((dfEditorTask) tabbedPane.getSelectedComponent()).redo();
    }//GEN-LAST:event_redoMenuItemActionPerformed

    /**
     * FIXME: Integrate this to TopComponent.
     *
     * @param c
     */
    private void addTab(java.awt.Component c) {
        tabbedPane.add(c);
        tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(c), new TabComponent(tabbedPane));
        tabbedPane.setSelectedComponent(c);
    }

    private JFrame getFrame() {
        return main.getApp();
    }

    /**
     * TODO: This method create a Spritesheet Component wich bound to a file.
     *
     * @param evt
     */
    private void newSpritesheetItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSpritesheetItemActionPerformed
        //Custom button text

        JFrame frame = this.getFrame();
        //FIXME: Move this to a Wizard
        SingleOrMultiDialog dialog = new SingleOrMultiDialog(frame, true);
        dialog.setLocationRelativeTo(frame);

        int result = dialog.showDialog();

        dfEditorPanel panel = null;
        switch (result) {
            case 0: {
                panel = new SpritesheetController(new CommandManager(undoMenuItem, redoMenuItem), true, helpLabel, this, main.getFileChooser());
                break;
            }
            case 1: {
                panel = new SpriteImageController(new CommandManager(undoMenuItem, redoMenuItem), helpLabel, this, main.getFileChooser());
                break;
            }
        }

        if (panel != null) {
            addTab(panel);
        }

    }//GEN-LAST:event_newSpritesheetItemActionPerformed

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        load();
    }//GEN-LAST:event_loadMenuItemActionPerformed

    /**
     * This method open files via a new JFileChooser mechanism.
     *
     */
    public void load() {
        JFileChooser chooser = main.getFileChooser();

        CustomFilter filter = new CustomFilter();
        filter.addExtension(CustomFilter.EXT_ANIM);
        filter.addExtension(CustomFilter.EXT_SPRITE);
        chooser.resetChoosableFileFilters();
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Load a spritesheet / animation set");

        // FIXME: Make this abstract
        JFrame mainFrame = dfEditorApp.getApplication().getMainFrame();

        int returnVal = chooser.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = chooser.getSelectedFile();

            loadFile(selectedFile);
        }
    }

    public void loadFile(File selectedFile) {
        if (selectedFile != null && selectedFile.exists()) {
            try {
                boolean bLoaded = false;
                java.awt.Component task = null;
                //FIXME: This should be a separate method?
                if (IOUtils.getExtension(selectedFile).equals(CustomFilter.EXT_ANIM)) {
                    task = loadAnim(selectedFile);
                } else if (IOUtils.getExtension(selectedFile).equals(CustomFilter.EXT_SPRITE)) {
                    task = loadSprite(selectedFile);
                }

                if (task != null) {
                    addTask(task, selectedFile);
                }
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Could not load the file!" + selectedFile.getAbsolutePath(), "File error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No such file exists",
                    "File not found",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addTask(java.awt.Component task, File selectedFile) {
        boolean bLoaded = true;
        if (bLoaded && task != null) {
            addTab(task);
            ((dfEditorTask) task).setSavedFile(selectedFile);
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), selectedFile.getName());
        }
    }

    public java.awt.Component loadAnim(File selectedFile) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
        boolean bLoaded = false;
        java.awt.Component task = null;
        AnimationController animController = new AnimationController(new CommandManager(undoMenuItem, redoMenuItem), false, helpLabel, this, main.getFileChooser());

        AnimationSetReader reader = new AnimationSetReader(selectedFile);
        bLoaded = animController.load(reader);
        if (bLoaded) {
            task = animController;
        } else {
            throw new FileNotFoundException("Can not open file: " + selectedFile.getAbsolutePath());
        }
        // FIXME: Use output for this!
        if (helpLabel != null) {
            helpLabel.setText("Loaded animations " + selectedFile.toString());
        }

        return task;
    }

    /**
     * TODO: This method create a Spritesheet Component wich bound to a file
     *
     * @param selectedFile
     * @return
     * @throws FileNotFoundException
     */
    public java.awt.Component loadSprite(File selectedFile) throws FileNotFoundException {
        boolean bLoaded = false;
        java.awt.Component task = null;
        JFrame frame = this.getFrame();
        SingleOrMultiDialog dialog = new SingleOrMultiDialog(frame, true);
        dialog.setLocationRelativeTo(frame);

        //FIXME: Can this also be a Node
        SpritesheetReader reader = new SpritesheetReader(selectedFile);
        String imagePath = reader.getImagePath();
        DefaultTreeModel model = reader.getTreeModel();

        int result = dialog.showDialog();

        switch (result) {
            case 0: {
                SpritesheetController spriteSheet = new SpritesheetController(new CommandManager(undoMenuItem, redoMenuItem), false, helpLabel, this, main.getFileChooser());
                bLoaded = spriteSheet.load(imagePath, model);
                if (!bLoaded) {
                    throw new FileNotFoundException("Can not open file: " + selectedFile.getAbsolutePath());
                }
                task = spriteSheet;
                break;
            }
            case 1: {
                SpriteImageController spriteSheet = new SpriteImageController(new CommandManager(undoMenuItem, redoMenuItem), helpLabel, this, main.getFileChooser());
                bLoaded = spriteSheet.load(imagePath, model);
                if (!bLoaded) {
                    throw new FileNotFoundException("Can not open file: " + selectedFile.getAbsolutePath());
                }
                task = spriteSheet;
                break;
            }
        }

        // FIXME: Use output for this!
        if (helpLabel != null) {
            helpLabel.setText("Loaded spritesheet " + selectedFile.toString());
        }
        return task;

    }

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        updateMenuBar();
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void newAnimationItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAnimationItemActionPerformed
        AnimationController animationSet = new AnimationController(new CommandManager(undoMenuItem, redoMenuItem), true, this.helpLabel, this, main.getFileChooser());
        addTab(animationSet);
    }//GEN-LAST:event_newAnimationItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
//        showAboutBox();
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void menuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSaveActionPerformed
        dfEditorTask currentTab = (dfEditorTask) tabbedPane.getSelectedComponent();

        if (currentTab != null) {
            if (currentTab.save()) {
                java.io.File file = currentTab.getSavedFile();

                if (file != null && file.exists()) {
                    String saveName = file.getName();
                    if (saveName != null && saveName.length() > 0) {
                        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), saveName);
                    }
                }
            }
        }
    }//GEN-LAST:event_menuItemSaveActionPerformed

    private void updateMenuBar() {
        dfEditorTask selectedTask = (dfEditorTask) (tabbedPane.getSelectedComponent());

        if (selectedTask != null) {
            String savedName = null;
            if (selectedTask.getSavedFile() != null) {
                savedName = selectedTask.getSavedFile().getName();
            }

            if (selectedTask.hasBeenModified()) {
                if (savedName != null) {
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), "*" + savedName);
                }

                menuItemSaveAs.setEnabled(true);
                menuItemSave.setEnabled(true);
            } else {
                menuItemSaveAs.setEnabled(false);
                menuItemSave.setEnabled(false);
            }

            selectedTask.refreshCommandManagerButtons();
        }
    }

    public void taskChanged(dfEditorTask aTask) {
        // TODO: this ignores arg and uses current task
        updateMenuBar();
    }
}
