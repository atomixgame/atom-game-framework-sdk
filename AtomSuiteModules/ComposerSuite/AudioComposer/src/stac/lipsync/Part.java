package stac.lipsync;

//Part.java
//Collection of Sentances
//
//(c) 2005 David Cuny
//http://jlipsync.sourceforge.net
//Released under Qt Public License


import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;

public class Part {

	public Sentence sentence[];
	
	Part( String text ) {		
		// split text up into sentences
		String[] s = text.split("\n", 0);
		
		// start position of sentence
		int startFrame = 1;
		
		// create sentences
		this.sentence = new Sentence[s.length];
		for (int i = 0; i < s.length; i++) {
			// create a sentence
			this.sentence[i] = new Sentence(this, i, s[i], startFrame );
			
			// start next after this
			startFrame = this.sentence[i].endFrame+1;
		}
	}
	

	public void writeIndexesToTimeline(int [] timeline) {		
		// set value for each sentance
		for (int i = 0; i < this.sentence.length; i++) {
			this.sentence[i].writeIndexesToTimeline( timeline );
		}
	}

	public void writeNamesToTimeline( String[] timeline) {		
		// set value for each sentance
		for (int i = 0; i < this.sentence.length; i++) {
			this.sentence[i].writeNamesToTimeline( timeline );
		}
	}
	
	public void writeWordsToTimeline( String[] timeline) {		
		// set value for each sentance
		for (int i = 0; i < this.sentence.length; i++) {
			this.sentence[i].writeWordsToTimeline( timeline );
		}
	}

	
	void render(Graphics g) {
		// render the child sentances
		for (int i = 0; i < this.sentence.length; i++) {
			this.sentence[i].render(g);
		}
	}
	
	TextChunk hit(int frame, int y) {
		// check children
		for (int i = 0; i < this.sentence.length; i++) {
			TextChunk hitChunk = this.sentence[i].hit(frame,y);
			if (hitChunk != null) {
				return hitChunk;
			}
		}
		
		// no matches
		return null;
	}

	
	public void writeXml( FileWriter stream ) 
	throws IOException {
		stream.write("<sentences>\n");
		int count = this.sentence.length;
		Export.writeTag( stream, "count", count);
		for (int i = 0; i < count; i++) {
			// write the XML for the sentance
			this.sentence[i].writeXml( stream );
		}
		stream.write("</sentences>\n");
	}
	
	public void readXml( SimpleXmlReader xmlReader ) 
	throws Exception {

		// <part>
		xmlReader.getTag("<sentences>");
		int count = xmlReader.getInt("count");
		this.sentence = new Sentence[count];
		
		// build each sentance
		for (int i = 0; i < count; i++) {
			// build a default sentence
			this.sentence[i] = new Sentence(this, i, "", 0);
			// set to the stored values
			this.sentence[i].readXml( xmlReader );
		}
		xmlReader.getTag("</sentences>");

	}
	
}
