package sg.stac.utils;

// Utilities.java
// Various useful routines
//
// (c) 2005 David Cuny
// http://jlipsync.sourceforge.net
// Released under Qt Public License
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sg.stac.lipsync.LipSyncMainApp;

public class Utilities {
    // get the icon

    public final static ImageIcon createImageIcon(String path) {
        URL imgURL = LipSyncMainApp.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find Icon: " + path);
            return null;
        }
    }

    public static InputStream openFile(String fileName) throws IOException {

        // check for good filename
        if (fileName == null) {
            throw new IOException("Cannot open File: (null filename).");
        }

        // get the resource
        URL url = LipSyncMainApp.class.getResource(fileName);
        if (url == null) {
            throw new IOException("File not found: " + fileName);
        }

        return url.openStream();
    }

    // return a 32x32 version of the icon
    public final static ImageIcon createScaledImageIcon(ImageIcon icon) {
        // get the image
        Image image = icon.getImage();

        // create an icon to hold the image
        ImageIcon scaledIcon = new ImageIcon();

        // scale down the icon
        scaledIcon.setImage(
                image.getScaledInstance(32, 32,
                Image.SCALE_DEFAULT));

        return scaledIcon;
    }

    public final static void errMessage(String s) {
        JOptionPane.showMessageDialog(null, s, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public final static void setPreference(String key, String value) {
        // get the user preferences
        Preferences userPrefs = Preferences.userRoot().node("/jlipsync/preferences");

        try {
            // save preference
            userPrefs.put(key, value);
            userPrefs.flush();
        } catch (Exception e) {
            errMessage("Unable to store persistant settings.");
        }
    }

    public final static String getPreference(String key) {
        // get the user preferences
        Preferences userPrefs = Preferences.userRoot().node("/jlipsync/preferences");

        // return preference, default is empty string
        return userPrefs.get(key, "");
    }

    // return a JFileChooser that opens at the default directory
    public final static JFileChooser fileChooser(File file) {

        JFileChooser fc = new JFileChooser();

        // look for default directory?
        String dirName = Utilities.getPreference("defaultDirectory");

        if (dirName.length() > 0) {
            try {
                fc.setCurrentDirectory(new File(dirName));
            } catch (Exception e) {
                // oh, well...
            }
        }

        // was a file specified?
        if (file != null) {
            // set as selected file
            try {
                fc.setSelectedFile(file);
            } catch (Exception e) {
                // ignore
            }
        }

        // return the dialog
        return fc;
    }

    // showSaveDialog, but automatically saves the current directory
    public final static int showSaveDialog(JFileChooser fc) {
        int returnVal = fc.showSaveDialog(LipSyncMainApp.getMainWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // save as default directory
            String dirName = fc.getCurrentDirectory().getAbsolutePath();
            Utilities.setPreference("defaultDirectory", dirName);
        }

        return returnVal;
    }

    // showOpenDialog, but automatically saves the current directory
    public final static int showOpenDialog(JFileChooser fc) {
        int returnVal = fc.showOpenDialog(LipSyncMainApp.getMainWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // save as default directory
            String dirName = fc.getCurrentDirectory().getAbsolutePath();
            Utilities.setPreference("defaultDirectory", dirName);
        }

        return returnVal;
    }

    // ensure file name ends with given extention
    public final static String addFileExtention(String fileName, String extention) {

        String lowerFileName = fileName.toLowerCase();
        if (!(lowerFileName.endsWith(extention))) {
            return fileName + extention;
        }
        return fileName;
    }

    public final static void copyToClipboard(String data) {
        // create a clipboard
        Clipboard clipboard = clipboard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();

        // create the contents
        StringSelection contents = new StringSelection(data);

        // copy to clipboard
        clipboard.setContents(contents, null);
    }
}
