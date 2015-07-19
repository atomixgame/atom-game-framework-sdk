package sg.stac.lipsync;

//Main.java
//Launcher for lipsync program
//
//(c) 2005 David Cuny
//http://jlipsync.sourceforge.net
//Released under Qt Public License
import javax.swing.UIManager;

public class LipSyncMainApp {

    public static MainWindow mainWindow;
    public static final String VERSION = "1.0 beta";

    // create and launch the application
    public static void main(String[] args) {
        // use native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            // don't really care
        }

        MainWindow app = new MainWindow();
        mainWindow = app;
        app.repaint();

    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }
}
