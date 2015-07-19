package sg.stac.lipsync;

//MainWindow.java
//Main window for lipsync program
//
//(c) 2005 David Cuny
//http://jlipsync.sourceforge.net
//Released under Qt Public License
import sg.stac.lipsync.ui.DopeSheet;
import sg.stac.ui.file.LipSyncFilter;
import sg.stac.ui.file.WavFilter;
import sg.stac.lipsync.word.Part;
import sg.stac.lipsync.word.Lookup;
import sg.stac.utils.SimpleXmlReader;
import sg.stac.audio.wav.WaveFile;
import sg.stac.utils.Utilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class MainWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    DopeSheet dopeSheet;
    static File openFile = null;
    Lookup lookup = new Lookup();
    JScrollPane dopeSheetScrollPane;
    FacePanel facePanel;
    JTextArea script;
    JComboBox fpsCombo;

    public MainWindow() {

        // set the name
        super("LipSync");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the menu bar
        setJMenuBar(createMenuBar());

        // create the window content
        this.setContentPane(createContentPanel());

        // create the dopesheet
        dopeSheet = new DopeSheet(200);

        this.dopeSheetScrollPane = new JScrollPane(dopeSheet);
        this.getContentPane().add(dopeSheetScrollPane, BorderLayout.CENTER);

        //Display the window.
        setSize(450, 490);
        setVisible(true);

    }

    public void repaintDopesheet() {
        this.dopeSheetScrollPane.repaint();
    }


    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
    }

    // ensure file name ends with given extention
    public String addFileExtention(String fileName, String extention) {

        String lowerFileName = fileName.toLowerCase();
        if (!(lowerFileName.endsWith(extention))) {
            return fileName + extention;
        }
        return fileName;
    }

    // if returns false, user cancelled autosave action
    public boolean cancelledAutoSave() {
        // no work to save?
        if (this.openFile == null) {
            return false;
        }

        // ask user if they want to save their current work
        int result = JOptionPane.showConfirmDialog(null,
                "Save Current File First?", "Save File?",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION) {
            // cancelled
            return true;

        } else if (result == JOptionPane.NO_OPTION) {
            // didn't save, but didn't cancel
            return false;
        }

        // make sure it ends with the proper extention
        String openFileName = this.openFile.getPath();
        openFileName = addFileExtention(openFileName, ".ls");

        // write the timesheet out		
        writeTimesheet(new File(openFileName));

        return false;

    }

    // Menu actions
    public void fileNew() {
        // autosave?
        if (cancelledAutoSave()) {
            return;
        }

        // no open file
        MainWindow.openFile = null;

        // clear the script text
        this.script.setText("");

        // clear the data from the dopesheet		
        this.dopeSheet.initialize(200);
        this.dopeSheet.repaint();

    }

    public void fileOpen() {

        // autosave?
        if (cancelledAutoSave()) {
            return;
        }

        // open file dialog
        JFileChooser fc = Utilities.fileChooser(null);
        fc.addChoosableFileFilter(new WavFilter());
        fc.addChoosableFileFilter(new LipSyncFilter());
        int returnVal = Utilities.showOpenDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // get the file name
            File file = fc.getSelectedFile();
            String fileName = file.getPath();

            // end with ".ls" ?
            fileName = fileName.toLowerCase();
            if (fileName.endsWith(".ls")) {
                // save as the open file
                MainWindow.openFile = file;
                // pass the file and the path to load the timesheet
                readTimeSheet(MainWindow.openFile);
            } else {
                // assume a wave file of some sort
                LipSyncMainApp.mainWindow.dopeSheet.loadWave(file);
            }
        }
    }

    public void fileSave() {
        if (this.openFile == null) {
            // use save as instead
            fileSaveAs();
        } else {
            // make sure it ends with the proper extention
            String fileName = openFile.getPath();
            fileName = addFileExtention(fileName, LipSyncFilter.EXTENTION);

            // write the timesheet out
            writeTimesheet(new File(fileName));
        }
    }

    public void fileSaveAs() {
        JFileChooser fc = Utilities.fileChooser(openFile);
        fc.addChoosableFileFilter(new LipSyncFilter());
        int returnVal = Utilities.showSaveDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // get the selected file
            File file = fc.getSelectedFile();

            // make sure it ends with the proper extention
            String fileName = file.getPath();
            fileName = addFileExtention(fileName, LipSyncFilter.EXTENTION);

            // write the timesheet out
            writeTimesheet(new File(fileName));
        }
    }

    public void writeTimesheet(File file) {

        // file to write to
        FileWriter stream;

        // get the parts
        Part part = LipSyncMainApp.mainWindow.dopeSheet.part;

        try {
            // create the stream
            stream = new FileWriter(file);

            // xml header
            stream.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");

            // header
            stream.write("<lipsync>\n");
            Export.writeTag(stream, "version", LipSyncMainApp.VERSION);

            // wave file information
            Export.writeTag(stream, "waveFile", this.dopeSheet.getWaveManager().getCurrentWave().fileName);

            // non-timesheet information
            Export.writeTag(stream, "framesPerSecond", this.dopeSheet.getWaveManager().getCurrentWave().framesPerSecond);
            Export.writeTag(stream, "waveFileName", this.dopeSheet.getWaveManager().getCurrentWave().fileName);

            // script
            Export.writeTag(stream, "script", this.script.getText());

            // currently, only 1 part
            stream.write("<parts>\n");
            Export.writeTag(stream, "count", 1);
            part.writeXml(stream);
            stream.write("</parts>\n");

            // footer
            stream.write("</lipsync>\n");

            // close the stream
            stream.close();

        } catch (Exception e) {
            Utilities.errMessage("Error writing file " + e);

        }
    }


    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, subMenu;
        JMenuItem menuItem, subMenuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        // file menu
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        //file : new
        menuItem = new JMenuItem("New", KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Clear the timesheet");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileNew();
            }
        });


        //file : open
        menuItem = new JMenuItem("Open...", KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Open an existing timesheet or wave file");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileOpen();
            }
        });

        menu.addSeparator();

        //file : save
        menuItem = new JMenuItem("Save...", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Save the current timesheet");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileSave();
            }
        });

        //file : save as
        menuItem = new JMenuItem("Save As...", KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Save the current timesheet using a different file name");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileSave();
            }
        });


        menu.addSeparator();


        menuItem = new JMenuItem("Export As Magpie Timesheet...", KeyEvent.VK_M);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Export the timesheet in Magpie format");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Export.exportFile(Export.FORMAT_MAGPIE);
            }
        });


        menuItem = new JMenuItem("Export As Moho Timesheet...", KeyEvent.VK_H);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Export the timesheet in Moho format");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Export.exportFile(Export.FORMAT_MOHO);
            }
        });


        menu.addSeparator();

        menuItem = new JMenuItem("Copy to Clipboard", KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Copy the timesheet to the system clipboard in Magpie format");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // copy data in Magpie format to the clipboard
                Utilities.copyToClipboard(Export.createContents(Export.FORMAT_MAGPIE));
            }
        });


        menu.addSeparator();

        // file exit
        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Copy the exposure sheet to the clipboard");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // save current work?
                if (cancelledAutoSave()) {
                    return;
                }
                // exit application
                System.exit(0);
            }
        });


        // edit menu
        //menu = new JMenu("Edit");
        //menu.setMnemonic(KeyEvent.VK_E);
        //menuBar.add(menu);

        // help menu
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu);


        return menuBar;
    }

    public JPanel createContentPanel() {
        //Create the content-pane-to-be.
        JButton button;
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(200, 200));
        contentPanel.setOpaque(true);

        // create a panel to put buttons in
        JToolBar toolbar = new JToolBar();
        toolbar.setPreferredSize(new Dimension(200, 30));
        contentPanel.add("North", toolbar);

        // file open
        button = new JButton(createImageIcon("images/open.gif"));
        button.setToolTipText("Load a file from disk");
        toolbar.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileOpen();
            }
        });

        // file save
        button = new JButton(createImageIcon("images/save.gif"));
        button.setToolTipText("Save a file to disk");
        toolbar.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // fileOpen();
            }
        });

        toolbar.addSeparator();

        // play to
        button = new JButton(createImageIcon("images/play_to.gif"));
        button.setToolTipText("Play the wave from the start to the current position");
        toolbar.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // play the wave from the start to the current position
                int here = dopeSheet.timeMarkerAt;
                LipSyncMainApp.mainWindow.dopeSheet.waveFile.playFrames(1, here, here);
            }
        });

        // play
        button = new JButton(createImageIcon("images/play.gif"));
        button.setToolTipText("Play the wave");
        toolbar.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // number of frames
                int end = LipSyncMainApp.mainWindow.dopeSheet.waveFile.frameCount;
                // play the entire wave, return to current position
                LipSyncMainApp.mainWindow.dopeSheet.waveFile.playFrames(1, end, dopeSheet.timeMarkerAt);
            }
        });

        // play from
        button = new JButton(createImageIcon("images/play_from.gif"));
        button.setToolTipText("Play the wave from current position to the end");
        toolbar.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // current position, number of frames
                int here = dopeSheet.timeMarkerAt;
                int end = LipSyncMainApp.mainWindow.dopeSheet.waveFile.frameCount;
                // play the wave starting a the current position to the end
                LipSyncMainApp.mainWindow.dopeSheet.waveFile.playFrames(dopeSheet.timeMarkerAt, end, here);
            }
        });


        // stop
        button = new JButton(createImageIcon("images/stock_media-stop.png"));
        button.setToolTipText("Stop playing the wave");
        toolbar.add(button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WaveFile.stopPlayingWave = true;
            }
        });

        toolbar.addSeparator();

        // frames per second
        fpsCombo = new JComboBox();
        fpsCombo.setToolTipText("Set the frames per second of playback");
        toolbar.add(fpsCombo);
        fpsCombo.addItem("24 fps - Film");
        fpsCombo.addItem("25 fps - PAL");
        fpsCombo.addItem("30 fps - NTSC");
        fpsCombo.setMaximumSize(new Dimension(120, 24));
        fpsCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get fps
                String fps = fpsCombo.getSelectedItem().toString().substring(0, 2);
                int newFps = Integer.parseInt(fps);

                // save value
                dopeSheet.waveFile.framesPerSecond = newFps;

                // need to reload file? 
                if (LipSyncMainApp.mainWindow.openFile != null) {
                    // reload the file
                    LipSyncMainApp.mainWindow.dopeSheet.loadWave(LipSyncMainApp.mainWindow.openFile);
                }
            }
        });

        toolbar.addSeparator();

        // create a panel to hold the text and facePanel
        JPanel textAndFacePanel = new JPanel(new BorderLayout());
        contentPanel.add("South", textAndFacePanel);

        // create an area to draw the phoneme on
        facePanel = new FacePanel("default");
        textAndFacePanel.add("East", facePanel);

        // create an to enter the dialog into		
        script = new JTextArea("Enter your dialog here.");
        JScrollPane dialogScroll = new JScrollPane(script);
        textAndFacePanel.add("Center", dialogScroll);

        button = new JButton("Convert Dialog To Phonemes");
        textAndFacePanel.add("South", button);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dopeSheet.setDialog(script.getText());
                dopeSheet.repaint();
            }
        });





        return contentPanel;
    }

    // load a timesheet (along with the wave)
    void readTimeSheet(File file) {

        String tag;
        String waveFileName;
        Part newPart;

        try {

            SimpleXmlReader xmlReader = new SimpleXmlReader(file);

            // xml header
            String theHeader = xmlReader.getTag("");
            if (!theHeader.substring(0, 6).equals("<?xml ")) {
                throw new Exception("Not an XML file");
            }

            // header
            xmlReader.getTag("<lipsync>");

            // version number
            String version = xmlReader.getString("version");

            // wave file information
            waveFileName = xmlReader.getString("waveFile");

            // non-timesheet information
            int fps = xmlReader.getInt("framesPerSecond");

            // get wave file
            waveFileName = xmlReader.getString("waveFileName");
            if (waveFileName.trim().length() > 0) {
                // try loading the wave file
                File waveFile;
                try {
                    // open the file
                    waveFile = new File(waveFileName);

                    // load the wave file
                    this.dopeSheet.waveFile.loadWave(waveFile.getPath());

                } catch (Exception e2) {
                    // failed, show error but keep going
                    // display an error
                    Utilities.errMessage("Can't find the file " + waveFileName);
                }
            }


            // read the script
            this.script.setText(xmlReader.getString("script"));

            // create a new part
            newPart = new Part("");

            // read the parts (currently only one part is supported)
            xmlReader.getTag("<parts>");
            int count = xmlReader.getInt("count");
            newPart.readXml(xmlReader);
            xmlReader.getTag("</parts>");

            xmlReader.getTag("</lipsync>");


        } catch (Exception e) {

            // display an error
            Utilities.errMessage("Error reading file: " + e);

            return;

        }

        // success, replace
        this.dopeSheet.part = newPart;

        // update the faces on the timeline
        this.dopeSheet.part.writeIndexesToTimeline(this.dopeSheet.faceIndexTimeline);

        // try loading the wave file		
        this.dopeSheet.waveFile.loadWave(waveFileName);


        // repaint the dopesheet
        this.dopeSheet.repaint();
    }
    // return an icon
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainWindow.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            String message = new String("Couldn't find file: " + path);
            JOptionPane.showMessageDialog(null, message, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
