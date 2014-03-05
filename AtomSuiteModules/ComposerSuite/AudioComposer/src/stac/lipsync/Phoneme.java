package stac.lipsync;

//DopeSheet.java
//Lowest level of dialog information
//
//(c) 2005 David Cuny
//http://jlipsync.sourceforge.net
//Released under Qt Public License

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;

public class Phoneme extends TextChunk {
	
	static final Color FORE_COLOR1 = new Color( 173, 114, 146 );
	static final Color BACK_COLOR1 = new Color( 231, 185, 210 );	
	static final Color FORE_COLOR2 = new Color( 196, 148, 148 );
	static final Color BACK_COLOR2 = new Color( 255, 192, 192 );


	static final int BASE_ROW = 70;
	
	Word parent;
	
	Phoneme( Word parent, int index, String text, int startFrame, int rowOffset ) {
		this.parent = parent;
		this.index = index;
		this.startFrame = startFrame;
		this.endFrame = startFrame;
		this.text = text;
		this.row = (int)(Phoneme.BASE_ROW + ((rowOffset/2f)*DopeSheet.boxHeight));
		
		// get index of parent
		int wordIndex = parent.index;
		
		// even or odd?
		if (wordIndex%2 ==0) {
			this.foreColor = Phoneme.FORE_COLOR1;
			this.backColor = Phoneme.BACK_COLOR1;
		} else {
			this.foreColor = Phoneme.FORE_COLOR2;
			this.backColor = Phoneme.BACK_COLOR2;
		}
	}
	

	public void writeIndexesToTimeline( int[] timeline ) {
		// get the index of the phoneme
		int index = FacePanel.getFaceIndex(this.text);
		
		// set each frame to that value
		for (int i = this.startFrame; i <= this.endFrame; i++) {
			timeline[i] = index;
		}
	}
	
	public void writeNamesToTimeline( String[] timeline )  {		
		// set each frame to that phoneme's text value
		for (int i = this.startFrame; i <= this.endFrame; i++) {
			timeline[i] = this.text;
		}
		
	}
	
	public void render(Graphics g) {
		// render the box for the phoneme
		this.renderHex(g);		
	}

	TextChunk hit(int frame, int y) {
		// in frame range?
		if (!this.inFrame(frame)) {
			return null;
		}
	
		// in phoneme?
		if (this.inY(y)) {
			LipsyncManager.dopeSheet.activePhoneme = this;
			return this;
		}
		
		return null;
	}
	
	
	void move(int newStart, int newEnd, int hitPart) {

		// check start range
		if (index == 0) {
			// first item
			if (newStart < parent.startFrame) {
				// don't go past parent start
				newStart = parent.startFrame;
			}
		} else if (newStart <= parent.phoneme[index-1].endFrame) {
			Phoneme prior = parent.phoneme[index-1];
			// prior phoneme can't shrink?
			if (prior.endFrame - prior.startFrame == 0) {
				// don't got past prior phoneme's end
				newStart = prior.endFrame+1;
			}
		}
		
		// check end range
		if (index == parent.phoneme.length-1) {
			// last item
			if (newEnd > parent.endFrame) {
				// don't go past parent's end
				newEnd = parent.endFrame;
			}
		} else if (newEnd >= parent.phoneme[index+1].startFrame) {
			Phoneme follows = parent.phoneme[index+1];
			// following phoneme can't shrink?
			if (follows.endFrame - follows.startFrame == 0) {
				// don't go past next sibling's start
				newEnd = follows.startFrame-1;
			}
		}
		
		// check against prior limits
		if (newStart > newEnd) newStart = this.startFrame;
		if (newEnd < newStart) newEnd = this.endFrame;

		// no change?
		if (this.startFrame == newStart && this.endFrame == newEnd) {
			// do nothing
			return;
		}
		
		// move
		this.startFrame = newStart;
		this.endFrame = newEnd;
		
		// adjust the neighbor to fill the gap
		if (hitPart == DopeSheet.HIT_FRONT || hitPart == DopeSheet.HIT_MIDDLE ) {
			if (index > 0) {
				// find neighbor in front
				Phoneme neighbor = parent.phoneme[index-1];
				neighbor.endFrame = this.startFrame-1;
			}
		}
			
		if (hitPart == DopeSheet.HIT_BACK || hitPart == DopeSheet.HIT_MIDDLE ) {
			if (index < parent.phoneme.length) {
				// find neighbor following
				Phoneme neighbor = parent.phoneme[index+1];
				neighbor.startFrame = this.endFrame+1;
			}
		}
		
		// play the frame
		switch (hitPart) {
		case DopeSheet.HIT_FRONT:
		case DopeSheet.HIT_MIDDLE:
			// play the wave from the front
			WaveFile.stopPlayingWave = true;
			Main.mainWindow.dopeSheet.waveFile.playFrames(newStart, newStart, newStart);
			break;

		case DopeSheet.HIT_BACK:
			// play the wave from the front
			WaveFile.stopPlayingWave = true;
			Main.mainWindow.dopeSheet.waveFile.playFrames(newEnd, newEnd, newEnd);
			break;
		}
		
	}
	
	public void writeXml( FileWriter stream ) 
	throws IOException {
		stream.write("<phoneme>\n");
		Export.writeTag(stream, "index", this.index);			
		Export.writeTag(stream, "startFrame", this.startFrame);
		Export.writeTag(stream, "endFrame", this.endFrame);
		Export.writeTag(stream, "row", this.row);
		Export.writeTag(stream, "text", this.text);
		stream.write("</phoneme>\n");
	}


	public void readXml( SimpleXmlReader xmlReader ) 
	throws IOException {
		xmlReader.getTag("<phoneme>");
		this.index = xmlReader.getInt("index");			
		this.startFrame = xmlReader.getInt("startFrame");
		this.endFrame = xmlReader.getInt("endFrame");
		this.row = xmlReader.getInt("row");
		this.text = xmlReader.getString("text");
		xmlReader.getTag("</phoneme>");
	}

	
}
