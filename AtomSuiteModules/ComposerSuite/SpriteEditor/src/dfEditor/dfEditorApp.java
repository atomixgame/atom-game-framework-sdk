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

import com.google.inject.Inject;
import java.io.File;
import javax.swing.JFrame;

/**
 * Stand alone version of the Application.
 *
 * TODO: Configed to be a minimum settings of Netbean. TODO: Load config from
 * outside?
 *
 * @author cuong.nguyenmanh2
 */
public class dfEditorApp extends JFrame {

    /**
     * Singleton reference of dfEditorApp.
     */
    private static dfEditorApp defaultInstance;
    private dfEditorSwingMain main;

    /**
     * Constructs singleton instance of dfEditorApp.
     */
    private dfEditorApp() {
    }

    /**
     * Provides reference to singleton object of dfEditorApp.
     *
     * @return Singleton instance of dfEditorApp.
     */
    public static synchronized final dfEditorApp getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new dfEditorApp();
        }
        return defaultInstance;
    }
    private dfEditorView view;

    /**
     * At startup create and show the main frame of the application.
     */
    protected void startup() {
        final String userDir = dfEditorApp.getUserDataDirectory();
        final dfEditorApp self = this;
        main = new dfEditorSwingMain(this);
        view = new dfEditorView(main);
        //this.addExitListener(main);
    }

    //SET LAF
//        java.awt.EventQueue.invokeLater(new Runnable()
//        {
//            public void run()
//            {                                
//                Toolkit.getDefaultToolkit().setDynamicLayout(true);
//                System.setProperty("sun.awt.noerasebackground", "true");
//                
//                try {
//                    TinyLookAndFeel tiny = new TinyLookAndFeel();
//                    UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
//                } catch(Exception ex) {
//                    ex.printStackTrace();
//                }           
//                
//                self.showMainView();
//            }
//        });

    /**
     * Make a directory for user info.
     *
     * TODO: Align this to Netbean architecture
     *
     * @return
     */
    private static String getUserDataDirectory() {
        String dir = System.getProperty("user.home") + File.separator + ".dfEditor" + File.separator;
        File test = new File(dir);
        if (!test.exists()) {
            test.mkdir();
        }
        return dir;
    }

    @Inject
    public JFrame getMainFrame() {
        return this;
    }
//    
//    /**
//     * This method is to initialize the specified window by injecting resources.
//     * Windows shown in our application come fully initialized from the GUI
//     * builder, so this additional configuration is not needed.
//     */
//    protected void configureWindow(java.awt.Window root) {
//    }
//

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of dfEditorApp
     */
    public static dfEditorApp getApplication() {
        return getInstance();
    }
//

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
//        launch(dfEditorApp.class, args);
    }

    public void shutdown() {
    }
}
