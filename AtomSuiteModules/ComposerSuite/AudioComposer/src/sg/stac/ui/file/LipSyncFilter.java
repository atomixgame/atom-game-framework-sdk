package sg.stac.ui.file;

// LipsyncFileFilter.java
// Filter for selecting LipSync files
//
// (c) 2005 David Cuny
// http://jlipsync.sourceforge.net
// Released under Qt Public License

import java.io.File;

import javax.swing.filechooser.FileFilter;

// accept files ending with .jls
public class LipSyncFilter extends FileFilter {

	public static final String EXTENTION = new String(".ls");
	
    // Accept all directories and all .jls files
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String fileName = f.getName().toLowerCase();
		if (fileName.endsWith( EXTENTION )) {
			return true;
		}			
        return false;
    }
    
    // The description of this filter
    public String getDescription() {
        return "LipSync Files";
    }
}

