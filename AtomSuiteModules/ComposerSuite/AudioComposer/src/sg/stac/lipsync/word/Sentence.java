package sg.stac.lipsync.word;

//Sentence.java
//A collection of Words
//
//(c) 2005 David Cuny
//http://jlipsync.sourceforge.net
//Released under Qt Public License
import sg.stac.lipsync.ui.DopeSheet;
import sg.stac.utils.SimpleXmlReader;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;
import sg.stac.audio.wav.WaveManager;
import sg.stac.lipsync.Export;
import sg.stac.lipsync.LipsyncManager;

/**
 * @author david
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Sentence extends TextChunk {

    public final static Color FORE_COLOR = new Color(121, 198, 162);
    public final static Color BACK_COLOR = new Color(205, 242, 162);
    public final static int BASE_ROW = 12;
    // hold children
    public Word word[];
    public Part parent;

    public Sentence(Part parent, int index, String text, int startFrame) {
        this.parent = parent;
        this.index = index;
        this.startFrame = startFrame;
        this.endFrame = startFrame;
        this.text = text;
        this.row = Sentence.BASE_ROW;
        this.foreColor = Sentence.FORE_COLOR;
        this.backColor = Sentence.BACK_COLOR;

        // clean the text and split into words
        String s[] = clean(text).split(" ", 0);

        // allocate enough words
        this.word = new Word[s.length];

        int rowOffset = 0;

        // create each word
        for (int i = 0; i < s.length; i++) {
            // create a new word
            word[i] = new Word(this, i, s[i], endFrame, rowOffset);
            // increment end frame position by the size of the word
            this.endFrame = word[i].endFrame + 1;
            rowOffset = (rowOffset == 0 ? 1 : 0);
        }
        this.endFrame--;
    }

    public String clean(String sentance) {

        System.out.println(sentance);

        // holds accumulated string
        StringBuffer buffer = new StringBuffer();
        String lastChar = "";

        // iterate through each character
        for (int i = 0; i < sentance.length(); i++) {
            // get a letter
            String letter = sentance.substring(i, i + 1);

            // parse the character
            if (letter.toUpperCase() != letter.toLowerCase()) {
                // letter, append upper case version
                buffer.append(letter.toUpperCase());
                lastChar = letter;

            } else if (letter.equals("'")) {
                // keep apostrophe
                buffer.append(letter);
                lastChar = letter;

            } else if (letter.equals(" ")) {
                // prior character not a space?
                if (!lastChar.equals(" ")) {
                    // add space to string
                    buffer.append(letter);
                    lastChar = letter;
                }
            }
        }

        return buffer.toString();
    }

    public void writeIndexesToTimeline(int[] timeline) {
        // iterate through each word in the sentace
        for (int i = 0; i < this.word.length; i++) {
            // wach word writes it's phonemes to the timeline
            this.word[i].writeIndexesToTimeline(timeline);
        }
    }

    public void writeNamesToTimeline(String[] timeline) {
        // iterate through each word in the sentance
        for (int i = 0; i < this.word.length; i++) {
            // wach word writes it's phonemes to the timeline
            this.word[i].writeNamesToTimeline(timeline);
        }
    }

    public void writeWordsToTimeline(String[] timeline) {
        // iterate through each word in the sentance
        for (int i = 0; i < this.word.length; i++) {
            // set the word in the timeline at it's start position
            timeline[this.word[i].startFrame] = this.word[i].text;
        }
    }

    public void render(Graphics g) {

        // render the box for the sentance
        this.renderHex(g);

        // render the child words
        for (int i = 0; i < this.word.length; i++) {
            this.word[i].render(g);
        }
    }

    public TextChunk hit(int frame, int y) {
        // in frame?
        if (!this.inFrame(frame)) {
            return null;
        }

        // in sentance?
        if (this.inY(y)) {
            LipsyncManager.dopeSheet.activeSentence = this;
            return this;
        }

        // check children
        for (int i = 0; i < this.word.length; i++) {
            TextChunk hitChunk = this.word[i].hit(frame, y);
            if (hitChunk != null) {
                return hitChunk;
            }
        }

        // no matches
        return null;

    }

    public void move(int newStart, int newEnd, int hitPart) {

        // check start range
        if (this.index == 0) {
            // first sentance
            if (newStart < 1) {
                // don't go past first frame
                newStart = 1;
            }
        } else if (newStart <= this.parent.sentence[this.index - 1].endFrame) {
            // don't go past prior sentance
            newStart = this.parent.sentence[this.index - 1].endFrame + 1;
        }

        // check end range
        if (this.index + 1 == this.parent.sentence.length) {
            // last sentance
            int lastFrame = LipsyncManager.dopeSheet.waveFile.frameCount;
            if (newEnd > lastFrame) {
                // don't go past last frame
                newEnd = lastFrame;
            }
        } else if (newEnd >= this.parent.sentence[this.index + 1].startFrame) {
            // don't go past prior sentance
            newEnd = this.parent.sentence[this.index + 1].startFrame - 1;
        }

        // check against prior limits
        if (newStart > newEnd) {
            newStart = this.startFrame;
        }
        if (newEnd < newStart) {
            newEnd = this.endFrame;
        }

        // no change?
        if (this.startFrame == newStart && this.endFrame == newEnd) {
            // don't do anything
            return;
        }

        // resized or just shifted?
        boolean resized = (this.endFrame - this.startFrame != newEnd - newStart);
        int delta = newStart - this.startFrame;

        // adjust
        this.startFrame = newStart;
        this.endFrame = newEnd;

        // play the frame
        switch (hitPart) {
            case DopeSheet.HIT_FRONT:
            case DopeSheet.HIT_MIDDLE:
                // play the wave from the front
                waveManager.stopPlayingWave();
                LipsyncManager.dopeSheet.waveFile.playFrames(newStart, newStart, newStart);
                break;

            case DopeSheet.HIT_BACK:
                // play the wave from the front
                waveManager.stopPlayingWave();
                LipsyncManager.dopeSheet.waveFile.playFrames(newEnd, newEnd, newEnd);
                break;
        }

        // adjust children?
        if (resized) {
            // readjust all the children
            this.recalcChildren();
        } else {
            // just shift the children
            this.shiftChildren(delta);
        }

    }

    public int calcFramesNeeded() {
        int needed = 0;

        // iterate through each word
        for (int i = 0; i < this.word.length; i++) {
            needed += this.word[i].calcFramesNeeded();
        }
        return needed;
    }

    public void shiftChildren(int delta) {
        // reallocate children		
        for (float i = 0; i < this.word.length; i++) {
            // get a word
            Word child = this.word[(int) i];

            // adjust start and end
            child.startFrame += delta;
            child.endFrame += delta;

            // adjust the phonemes for the words
            child.shiftChildren(delta);
        }

    }

    public void recalcChildren() {
        // calculate amount of padding to distribute
        float scale = (1 + this.endFrame - this.startFrame) / (float) calcFramesNeeded();

        float accum = 0;

        // reallocate children		
        for (float i = 0; i < this.word.length; i++) {
            // get a word
            Word child = this.word[(int) i];

            // adjust start
            child.startFrame = this.startFrame + (int) (accum * scale);

            // end of word
            accum += child.calcFramesNeeded();
            child.endFrame = this.startFrame + (int) (accum * scale) - 1;

            // adjust the phonemes for the words
            child.recalcChildren();
        }

    }

    public void writeXml(FileWriter stream)
            throws IOException {

        stream.write("<sentence>\n");

        // sentance information
        Export.writeTag(stream, "index", this.index);
        Export.writeTag(stream, "startFrame", this.startFrame);
        Export.writeTag(stream, "endFrame", this.endFrame);
        Export.writeTag(stream, "row", this.row);
        Export.writeTag(stream, "text", this.text);

        // the number of words
        stream.write("<words>\n");
        int count = this.word.length;
        Export.writeTag(stream, "count", count);
        for (int i = 0; i < count; i++) {
            // write the XML for each word
            this.word[i].writeXml(stream);
        }
        stream.write("</words>\n");
        stream.write("</sentence>\n");
    }

    public void readXml(SimpleXmlReader xmlReader)
            throws IOException {

        // <sentence>
        xmlReader.getTag("<sentence>");

        // sentance information
        this.index = xmlReader.getInt("index");
        this.startFrame = xmlReader.getInt("startFrame");
        this.endFrame = xmlReader.getInt("endFrame");
        this.row = xmlReader.getInt("row");
        this.text = xmlReader.getString("text");

        // <words>
        xmlReader.getTag("<words>");
        int count = xmlReader.getInt("count");
        // allocate the words
        this.word = new Word[count];
        for (int i = 0; i < count; i++) {
            // create a default word
            this.word[i] = new Word(this, i, "", 0, 0);
            // fill in with the stored values
            this.word[i].readXml(xmlReader);
        }
        xmlReader.getTag("</words>");
        xmlReader.getTag("</sentence>");
    }
}
