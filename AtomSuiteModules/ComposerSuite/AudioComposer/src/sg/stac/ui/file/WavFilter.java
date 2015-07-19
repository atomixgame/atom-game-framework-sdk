package sg.stac.ui.file;

// WavFilter.java
// Filter for selecting .wav files
//
// (c) 2005 David Cuny
// http://jlipsync.sourceforge.net
// Released under Qt Public License
import java.io.File;

import javax.swing.filechooser.FileFilter;

// accept files ending with .wav
public class WavFilter extends FileFilter {

    public final static String EXTENTION = new String(".wav");

    // Accept all directories and all .wav files
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String fileName = f.getName().toLowerCase();
        if (fileName.endsWith(EXTENTION)) {
            return true;
        }
        return false;
    }

    // The description of this filter
    public String getDescription() {
        return "Wave Audio Files";
    }
}