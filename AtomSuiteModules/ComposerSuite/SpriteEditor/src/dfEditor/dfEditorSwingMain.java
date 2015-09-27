/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dfEditor;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * EditorSwingMain does the jobs for Swing enviroment. It connects Graphics,
 * Components, LAF, Event Dispatching and suchs under the hood for us...
 *
 * @author cuong.nguyenmanh2
 */
public class dfEditorSwingMain {

    private JFileChooser fileChooser;
    private JFrame app;

    public dfEditorSwingMain(dfEditorApp app) {
        this.app = app;
        this.fileChooser = new JFileChooser();
    }

    public JFrame getApp() {
        return app;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}
