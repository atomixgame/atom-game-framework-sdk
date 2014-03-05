package stac.lipsync;

// WaveFile.java
// Load, display and play wave files
//
// (c) 2005 David Cuny
// http://jlipsync.sourceforge.net
// Released under Qt Public License

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class WaveFile {
	
	// wave color...
	static final Color FORE_COLOR = new Color( 30, 121, 198 );
	static final Color BACK_COLOR = new Color( 162, 205, 242 );

	
	// buffer holding the wave data 
	byte[] bytes = null;
	
	// largest sample size in frame
	int[] atFrame = null;

	int byteCount = 0;
	int bytesPerSample = 1; // 1 = 8 bit, 2 = 16 bit
	int channels = 1; // mono or stereo
	boolean signed = false;

	int maxAmplitude = 0;

	// number of frames
	int frameCount = 48;
	int framesPerSecond = 24;
	int bytesPerFrame = 1;

	// start and end of marker play settings
	int anchorStart = 0;

	int anchorEnd = 0;
	int markerStart = 0;
	int markerEnd = 0;
	boolean selecting = false;

	// allow stopping of wave playback
	static boolean stopPlayingWave = false;
		
	// name of file
	String fileName = new String("");

	// output data line
	SourceDataLine sourceDataLine = null;
	
	// audio format
	AudioFormat sourceAudioFormat = null;

	public WaveFile( int defaultFrameCount ) {
		this.frameCount = defaultFrameCount;
	}
	
	public void render(Graphics g, int width, int y1, int y2) {

		// no waveform?
		if (byteCount == 0) {
			return;
		}

		// calculate height of drawing area
		int height = y2 - y1;
		
		int priorTop = y1 + (height/2);
		int priorBottom = priorTop;
				
		// draw the wave
		for (int i = 0; i < frameCount; i++ ) {

			// get sample value for frame
			int sample = atFrame[i];			
			
			// fit sample to the window
			sample = (int)(height*((float)sample/(float)maxAmplitude));
						
			// x position
			int x = i * DopeSheet.frameWidth;
			int nextX = x + DopeSheet.frameWidth;
			
			// center the sample
			int top = y1 + (height-sample) / 2;
			int bottom = top + sample;
			
			// draw the sample
			g.setColor(WaveFile.BACK_COLOR);				
			g.fillRect(x, top, DopeSheet.frameWidth, sample);
				
			// draw the border
			g.setColor(WaveFile.FORE_COLOR);
			g.drawLine(x, priorTop, x, top );
			g.drawLine(x, top, nextX, top );
			g.drawLine(x, priorBottom, x, bottom );
			g.drawLine(x, bottom, nextX, bottom );
						
			// save values as prior
			priorTop = top;
			priorBottom = bottom;
		}

	}

	public final int getSample( int byteIndex ) {
		// return the value of a sample at byte position

		int result = 0;
		
		if (byteIndex+bytesPerSample >= byteCount ) {
			return 0;
		}

		switch (bytesPerSample) {
		case 1:
			// 8 bit
			result = (int)bytes[byteIndex];
		case 2:
			// 16 bit, LSB			
			int lsb = (int)bytes[byteIndex];
			int msb = (int)bytes[byteIndex+1];
			if (lsb < 0) lsb = -lsb + 128;
			result = lsb + (msb * 256);
		}
					
		return result;
	}
	
	private final File askUserForFile(String fileName) {
		
		// prompt user
		int result = JOptionPane.showConfirmDialog(null,
				"Can't find the file '" + fileName
						+ "'\nDo you want to find it manually?",
				"Unable to find .wav file", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {					
			// open file dialog
			JFileChooser fc = Utilities.fileChooser(null);

			// limit to .wav files
			fc.addChoosableFileFilter(new WavFilter());
			
			// open the dialog			
			int returnVal = Utilities.showOpenDialog(fc);
			
			// check results
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				return fc.getSelectedFile();
			}
		}
		
		// no file
		return null;

	}


	// load a waveform from a file
	public final void loadWave(String fileName) {
				
		// FIXME! If the bytes per sample changes, need to open a new dataline!
		
		// try loading the wave file
		File file = null;		
		try {
			// open the file
			file = new File(fileName);
			if (!file.canRead()) {
				// throw an exception
				throw new Exception();
			}
			
			// set the filename
			this.fileName = file.getPath();
						
		} catch (Exception e) {
			// can't find or open the file; ask user for help
			file = askUserForFile(fileName);
			if (file == null) {
				// clear flags and exit
				byteCount = 0;
				this.fileName = "";
				return;
			}
		} 
		
		// try to open the input stream
		AudioInputStream sourceAudioInputStream = null;
		try {
			sourceAudioInputStream = AudioSystem.getAudioInputStream( file );
		} catch (Exception e) {
			Utilities.errMessage( "Error reading file '" + file.getName() + "'" );
			byteCount = 0;
			return;
		}
		
		// get the audio format
		sourceAudioFormat = sourceAudioInputStream.getFormat();
						
		// get the input stream
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sourceAudioFormat, sourceAudioInputStream );
		
		// number of channels
		channels = sourceAudioFormat.getChannels();
				
		// bytes per sample
		bytesPerSample = (channels * sourceAudioFormat.getSampleSizeInBits())/ 8;
		
		// signed?
		signed = sourceAudioFormat.getEncoding().equals(Encoding.PCM_SIGNED);
		
		// calculate the number of bytes in the stream
		byteCount = (int)(audioInputStream.getFrameLength() * sourceAudioFormat.getFrameSize());
		
		// calcuate the metrics of the wave
		calculateMetrics();
        
		// allocate and load the array
		bytes = new byte[byteCount]; 
		try {
		    audioInputStream.read(bytes, 0, byteCount);
		} catch (Exception e) {
			Utilities.errMessage( "Error reading the audio file" );
			byteCount = 0;
			return;
		}
		
		// find the maximum amplitude of the wave
		maxAmplitude = 0;
		int sampleSize = bytesPerSample * channels;
		for (int i = 0; i < byteCount; i += sampleSize) {
			// get a sample
			int value = getSample(i);
			// maximum value?
			if (value > maxAmplitude) {
				maxAmplitude = value;
			} else if (-value > maxAmplitude) {
				maxAmplitude = -value;
			}
		}		
		
		// holds sample value/frame
		atFrame = new int[frameCount];

		// iterate through wave
		for (int i = 0; i < byteCount ; i += sampleSize ) {

			int frame = (i / bytesPerFrame);
			if (frame >= frameCount) {
				frame = frameCount-1;
			}
			
			//System.out.println("frame:"+frame+" frameCount:"+frameCount);
			
			// range check
			if (i >= 0 && i < byteCount) {
				
				// get value of sample
				int sample = getSample((int)i);
				
				// invert?
				if (!signed) {
					if (sample > 0) {
						sample = maxAmplitude - sample;
					} else {
						sample = -(maxAmplitude + sample);
					}
				}
				
				// make positive
				sample = (int)Math.abs(sample);
					
				if (sample > atFrame[frame]) {
					atFrame[frame] = sample;
				}
			}
			
		}

		

		// save the name
		fileName = file.getPath();

		// need to close prior line?
		if (sourceDataLine != null) {
			sourceDataLine.flush();
			sourceDataLine.close();
			sourceDataLine = null;
		}
		
		// need to open a line?
		if (sourceDataLine == null) {
			// the data line
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, sourceAudioFormat);
			try {				
				// open the line
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
				sourceDataLine.open(sourceAudioFormat);
				
				// prepare the line for output
				sourceDataLine.start();

			} catch (Exception e) {
				Utilities.errMessage("Unable to open audio device");
				sourceDataLine = null;
			}
		} else {
			// is the line open?
			if (!(sourceDataLine.isOpen())) {
				Utilities.errMessage("Data line is not open");
				sourceDataLine = null;
			}

		}
		
	}

	// calcuate metrics
	public final void calculateMetrics() {

		int sampleCount = byteCount / (bytesPerSample * channels);

		// is a wave loaded?
		if (byteCount != 0) {
			// number of seconds is (frames / frames per second)
			float seconds = sampleCount / sourceAudioFormat.getSampleRate();

			// number of displayed frames per second
			frameCount = (int) Math.floor(framesPerSecond * seconds);

			// number of audio bytes in each animation frame
			bytesPerFrame = ((int)((float)sampleCount / (float)frameCount)) * (bytesPerSample * channels);
		}		
	}

	// play the wave file
	public final void playFrames(int startFrame, int endFrame, int returnToFrame) {
				
		// no start frame
		if (startFrame == 0) {
			startFrame = 1;
		}

		// no end frame or past end
		if (endFrame == 0 || endFrame > frameCount) {
			endFrame = frameCount;
		}

		// calculate start and end bytes
		int startByte = bytesPerFrame * (startFrame - 1);
		int endByte = bytesPerFrame * endFrame;

		// range check bytes
		if (startByte < 0) {
			startByte = 0;
		}
		if (endByte > byteCount) {
			endByte = byteCount;
		}

		// play the thread on a background thread
		try {
			new PlayThread(startByte, endByte).start();
			new MoveTimelineThread(startFrame, endFrame, returnToFrame).start();
		} catch (Exception e) {
			Utilities.errMessage("Error playing the wave file");
		}
	}

	// play the sound on a background thread
	class PlayThread extends Thread {

		int startPosition, endPosition;

		public PlayThread(int startAtByte, int endAtByte) {
			startPosition = startAtByte;
			endPosition = endAtByte;
			stopPlayingWave = false;
			
		}

		public final void run() {

			// make sure a waveform is loaded
			if (byteCount == 0) {
				return;
			}

			// make sure audio device connected
			if (sourceDataLine == null) {
				Utilities.errMessage("Audio device failed. Try reloading file");
				return;
			}

			// is it open?
			if (!(sourceDataLine.isOpen())) {
				Utilities.errMessage("Data line is not open");
				sourceDataLine = null;
			}
			
			// send out 1K at a time
			int bufferSize = (1024 * 1);			

			// calculate buffer
			int startOfBuffer = startPosition;

			while (!WaveFile.stopPlayingWave) {
				// check for buffer overflow
				if ((startOfBuffer + bufferSize) >= endPosition) {
					bufferSize = (endPosition) - startOfBuffer;
				}

				// start the line
				try {
					sourceDataLine.start();
				} catch (Exception e) {
					// oops
					System.out.println("Unable to start the line");
				}

				// fixme: throws illegal length if too large
				System.out.println("Buffer size is " + bufferSize);

				int nBytesWritten = sourceDataLine.write(bytes, startOfBuffer,
						bufferSize);
				System.out.println("wrote " + nBytesWritten + " bytes");

				// point to next starting point in the buffer
				startOfBuffer += bufferSize;
				if (startOfBuffer >= endPosition) {
					break;
				}

			}
			
			// play out the remainder of the line, but don't close it
			sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.flush();

		}

	}

	// play the sound on a background thread
	class MoveTimelineThread extends Thread {

		int startFrame;
		int endFrame;
		int returnToFrame;		
		
		public MoveTimelineThread(int startFrame, int endFrame, int returnToFrame) {
			
			this.startFrame = startFrame;
			this.endFrame = endFrame;
			this.returnToFrame = returnToFrame;
			
		}

		public final void run() {			
			
			try {
				// calculate wait time between frames
				double startTime = System.currentTimeMillis();
				double sleepTime = 1000f / (float)(framesPerSecond);

				// time next frame is to appear
				double nextFrame = startTime;

				// display the frames
				for (int i = startFrame; i <= endFrame; i++) {
					
					// thread stopped?
					if (WaveFile.stopPlayingWave) {
						break;
					}
					
					// in range?
					if (i > 0 && i <= frameCount) {

						// marker moved?
						if (LipsyncManager.dopeSheet.timeMarkerAt != i) {
							// move the time marker
							LipsyncManager.dopeSheet.timeMarkerAt = i;
							
							// force a repaint of the dope sheet
							//Main.mainWindow.dopeSheetScrollPane.repaint(0,0, 1000, 12);
							//LipsyncManager.mainWindow.dopeSheetScrollPane.repaint();
						}

						// wait for next frame
						nextFrame += sleepTime;
						while (System.currentTimeMillis() <= nextFrame) {
							// sleep a bit
							sleep(1);
						}
					}
				}
			} catch (Exception e) {
				// oops.
			}
			
			// reset marker?
			if (!WaveFile.stopPlayingWave) {
				// need to move it?
				if (this.returnToFrame != LipsyncManager.dopeSheet.timeMarkerAt) {
					// restore the marker
					LipsyncManager.dopeSheet.timeMarkerAt = returnToFrame;
					//LipsyncManager.mainWindow.dopeSheetScrollPane.repaint();
				}
			}

		}
		
	}
	
}