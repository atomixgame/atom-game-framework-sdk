package jme3timeline;

import bibliothek.extension.gui.dock.theme.EclipseTheme;
import jme3timeline.components.TimeLineComponent;

import bibliothek.gui.DockController;
import bibliothek.gui.DockFrontend;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.DefaultDockable;
import bibliothek.gui.dock.ScreenDockStation;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.station.split.SplitDockGrid;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import jme3timeline.components.EffectComponent;
import jme3timeline.components.EffectControlComponent;
import jme3timeline.components.FontComponent;
import jme3timeline.components.MainMenuBar;
import jme3timeline.components.MainToolBar;
import jme3timeline.components.MainViewComponent;
import jme3timeline.components.ProjectComponent;

/**
 *
 * @author cuong.nguyenmanh2
 *
 * Main Application for animation handling.
 *
 * UI Design based on UDK Matinee and After Effect,
 *
 * Using DockFrame library
 */
public class TestAtomTimeLine extends JFrame {

    /**
     * main access to the docking framework
     */
    private DockFrontend frontend;
    /**
     * dockable screen
     */
    private ScreenDockStation screen;
    /**
     * central docking station
     */
    private SplitDockStation station;
    
    NimRODLookAndFeel createNimRODLAF() {
        NimRODTheme nt = new NimRODTheme();
        
        nt.setPrimary1(new Color(10, 10, 10));
        nt.setPrimary2(new Color(20, 20, 20));
        nt.setPrimary3(new Color(30, 30, 30));
        nt.setPrimary(new Color(0, 150, 250));        
        nt.setWhite(new Color(200, 200, 200));
        nt.setSecondary(new Color(250, 250, 250));
        
        
        NimRODLookAndFeel NimRODLF = new NimRODLookAndFeel();
        NimRODLookAndFeel.setCurrentTheme(nt);

        //lookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel")
        return NimRODLF;
    }
    
    void setupLAF() {
        try {
            //UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
            //UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
            UIManager.setLookAndFeel(createNimRODLAF());
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Substance Graphite failed to initialize");
        }
    }
    
    public TestAtomTimeLine() {
        super("Test Timeline");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
    
    public void buildUI() {
        //setupLAF();
        setLayout(new BorderLayout());
        setJMenuBar(new MainMenuBar(this));
        add(new MainToolBar(), BorderLayout.NORTH);
        dock2();
        setVisible(true);
        
    }
    /* This method creates a new Dockable with title "title" and a single JPanel with
     * its background color set to "color". */
    
    private static Dockable createDockableColorPanel(String title, Color color) {
        DefaultDockable dockable = new DefaultDockable();
        dockable.setTitleText(title);
        
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(color);
        dockable.add(panel);
        
        return dockable;
    }
    
    private static Dockable createDockable(String title, JPanel panel) {
        DefaultDockable dockable = new DefaultDockable();
        dockable.setTitleText(title);
        dockable.add(panel);
        
        return dockable;
    }
    
    public void dock2() {
        DockController controller = new DockController();
        controller.setRootWindow(this);
        controller.setTheme(new EclipseTheme());
        station = new SplitDockStation();
        controller.add(station);
        
        Dockable mainView = createDockable("Main view", new MainViewComponent());
        Dockable timeLine = createDockable("Timeline", new TimeLineComponent());
        Dockable project = createDockable("Project", new ProjectComponent());
        Dockable font = createDockable("Font", new FontComponent());
        Dockable effect = createDockable("Effects & Presets", new EffectComponent());
        Dockable effectControl = createDockable("Effects Controls", new EffectControlComponent());
        
        SplitDockGrid grid = new SplitDockGrid();
        
        station.setDividerSize(2);
        grid.addDockable(0, 0, 1, 1, project);
        grid.addDockable(1, 0, 1, 1, mainView);
        grid.addDockable(2, 0, 1, 1, font, effect);
        grid.addDockable(0, 1, 3, 1, timeLine);
        grid.addDockable(0, 0, 1, 1, effectControl);
        station.dropTree(grid.toTree());
        
        add(station, BorderLayout.CENTER);
    }
    
    public static void main(String args[]) {
        final TestAtomTimeLine app = new TestAtomTimeLine();
        
        app.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.buildUI();
                
            }
        });
        
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    public static ImageIcon createImageIcon(String path) {
        String rootPath = "../images/";
        URL imgURL = TestAtomTimeLine.class.getResource(rootPath + path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + rootPath + path);
            return null;
        }
    }
}
