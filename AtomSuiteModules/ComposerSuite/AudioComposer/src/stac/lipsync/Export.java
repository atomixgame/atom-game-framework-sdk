package stac.lipsync;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class Export {

	// export formats
	static final int FORMAT_MAGPIE = 0;
	static final int FORMAT_MOHO = 1;
	
	public static String dataToMagpieFormat( String faceTimeline[], String wordTimeline[], int frames, int framesPerSecond ) {
		StringBuffer s = new StringBuffer(
				"Frame   Timecode   Key   Mouth name    Comments\n");

		int theHour = 0;
		int theMinute = 0;
		int theSecond = 0;
		int theTick = 0;
		
		for (int i = 1; i <= frames; i++) {

			// leading spaces
			s.append("  ");

			// the frame number
			int theFrame = i;
			if (theFrame < 100)
				s.append(" ");
			if (theFrame < 10)
				s.append(" ");
			s.append(theFrame);
			s.append("  ");

			// caculate timecode
			s.append(calcTimeCode(i, framesPerSecond, 0, 0, 0 ));

			// key if different from prior frame
			if ( i == 1 || !faceTimeline[i].equals( faceTimeline[i-1])) {
				s.append("   X   ");
			} else {
				s.append("       ");
			}

			// 15 characters for the mouth shape
			s.append(padString( faceTimeline[i], 15));

			// place the word in the comment fiels
			s.append(wordTimeline[i]);
			s.append("\n");
		}

		// convert to a string
		return s.toString();
	}
	
	public static String dataToMohoFormat( String faceName[], int frames) {
		StringBuffer s = new StringBuffer("MohoSwitch1\n");

		for (int i = 1; i <= frames; i++) {
			String mouthShape = faceName[i];
			if (mouthShape.length() > 0) {
				s.append(i + " " + mouthShape + "\n");
			}
		}

		// convert to a string
		return s.toString();
	}


	public static String calcTimeCode(int frame, int framesPerSecond, int hourOffset, int minuteOffset, int secondOffset) {
		StringBuffer s = new StringBuffer("");

		// calculate number of seconds
		int seconds = (frame - 1) / framesPerSecond;

		// add offsets
		seconds += (hourOffset * 3600) + (minuteOffset * 60) + secondOffset;

		int hours = seconds / 3600;
		seconds -= hours * 3600;

		int minutes = seconds / 60;
		seconds -= minutes * 60;

		// build the time
		if (hours < 10)
			s.append("0");
		s.append(hours);
		s.append(":");
		if (minutes < 10)
			s.append("0");
		s.append(minutes);
		s.append(":");
		if (seconds < 10)
			s.append("0");
		s.append(seconds);
		s.append(".");
		if (frame < 10)
			s.append("0");
		s.append(frame);

		// convert to string
		return padString(s.toString(), 12);
	}

	
	public static final String padString(String s, int padSize) {
		String padded = new String(s
				+ "                                              ");
		return padded.substring(0, padSize - 1);
	}

	
	// converts the timeline into the requested file format
	public static final String createContents( int fileFormat ) {
		// create file contents
		String contents = new String();

		// get frame count and frames per second
		int frameCount = Main.mainWindow.dopeSheet.waveFile.frameCount;
		int framesPerSecond = Main.mainWindow.dopeSheet.waveFile.framesPerSecond;
		
		// create a timeline of phonemes
		String[] phonemeTimeline = Main.mainWindow.dopeSheet.buildFaceNameTimeline();
		
		// create a timeline of words
		String[] wordTimeline = Main.mainWindow.dopeSheet.buildWordTimeline();
		
		// create the contents
		switch (fileFormat) {
		case FORMAT_MAGPIE:
			contents = Export.dataToMagpieFormat( phonemeTimeline, wordTimeline, frameCount, framesPerSecond);
			break;
		case FORMAT_MOHO:
			contents = Export.dataToMohoFormat( phonemeTimeline, frameCount );
			break;
		}

		return contents;

	}

	public static final void exportFile(int fileType) {
		JFileChooser fc = Utilities.fileChooser(null);
		int returnVal = Utilities.showSaveDialog(fc);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// get the selected file
			File file = fc.getSelectedFile();

			// make sure it ends with the proper extention
			String fileName = file.getPath();
			fileName = Utilities.addFileExtention(fileName, ".txt");


			try {
				// create a file writer
				FileWriter stream;
				stream = new FileWriter(file);

				// create file contents
				String contents = Export.createContents( fileType );
			
				// write the contents
				stream.write(contents);
				stream.close();

			} catch (Exception e) {
				Utilities.errMessage("Error writing file:" + e);
				return;
			}
		}
	}

	public void copyToClipboard() {
		// create a clipboard
		Clipboard clipboard = clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();

		// create the contents
		StringSelection contents = new StringSelection(this.createContents(FORMAT_MAGPIE));

		// copy to clipboard
		clipboard.setContents(contents, null);
	}

	public static final void writeTag( FileWriter stream, String tagName, Object value ) 
	throws IOException {
		stream.write("<" + tagName + ">" + value + "</" + tagName + ">\n");
	}

	public static final void writeTag( FileWriter stream, String tagName, int value ) 
	throws IOException {
		stream.write("<" + tagName + ">" + value + "</" + tagName + ">\n");
	}
	
}
