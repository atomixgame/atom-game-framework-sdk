package stac.lipsync;

// Word.java
// Collection of Phonemes
//
// (c) 2005 David Cuny
// http://jlipsync.sourceforge.net
// Released under Qt Public License
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import stac.ui.StacMainWindow;

/**
 * @author david
 *
 */
public class Word extends TextChunk {

    static final Color FORE_COLOR = new Color(198, 121, 30);
    static final Color BACK_COLOR = new Color(242, 205, 162);
    static final int BASE_ROW = 36;
    Sentence parent;
    String phonemeString;
    Phoneme phoneme[];

    Word(Sentence parent, int index, String text, int startFrame, int rowOffset) {
        this.startFrame = startFrame;
        this.endFrame = startFrame;
        this.row = (int) (Word.BASE_ROW + ((rowOffset / 2f) * DopeSheet.boxHeight));
        this.text = text;
        this.parent = parent;
        this.index = index;
        this.foreColor = Word.FORE_COLOR;
        this.backColor = Word.BACK_COLOR;

        // convert the word to phonemes
        //Lookup lookup = Main.mainWindow.lookup;
        Lookup lookup = LipsyncManager.getLookup();
        String dictionaryPhonemes[] = lookup.get(text).split(" ");

        // allocate the phonemes
        this.phoneme = new Phoneme[dictionaryPhonemes.length];

        // FIXME: if not found, should prompt for breakdown?

        // position of word		
        rowOffset = 0;

        // clear the result
        this.phonemeString = new String("");

        // iterate through each letter
        for (int i = 0; i < dictionaryPhonemes.length; i++) {
            // create a phoneme
            phoneme[i] = new Phoneme(this, i, FacePanel.convertPhoneme(dictionaryPhonemes[i]), this.endFrame, rowOffset);

            // concat
            this.phonemeString = this.phonemeString.concat(phoneme[i].text) + " ";

            // advance end frame
            this.endFrame = phoneme[i].endFrame + 1;

            // toggle every other row
            rowOffset = (rowOffset == 0 ? 1 : 0);
        }

        this.endFrame--;

    }

    public void getNewBreakdown() {
        // prompt the user for a breakdown
        String breakdown = JOptionPane.showInputDialog("Enter phonemes for " + this.text + "'", this.phonemeString);

        // cancelled?
        if (breakdown == null) {
            // don't change it
            return;
        }

        // convert to upper case
        this.phonemeString = breakdown.toUpperCase();

        // convert the word to phonemes
        String dictionaryPhonemes[] = phonemeString.split(" ");

        // allocate the phonemes
        this.phoneme = new Phoneme[dictionaryPhonemes.length];

        // position of word		
        int rowOffset = 0;

        // iterate through each letter
        for (int i = 0; i < dictionaryPhonemes.length; i++) {
            // create a phoneme
            phoneme[i] = new Phoneme(this, i, FacePanel.convertPhoneme(dictionaryPhonemes[i]), this.startFrame + i, rowOffset);

            // toggle every other row
            rowOffset = (rowOffset == 0 ? 1 : 0);
        }

        // adjust
        this.recalcChildren();

        // repaint the dopesheet
        Main.mainWindow.dopeSheet.repaint();
    }

    public void writeIndexesToTimeline(int[] timeline) {
        // iterate through the phonemes
        for (int i = 0; i < this.phoneme.length; i++) {
            // copy the phoneme index to the timeline
            this.phoneme[i].writeIndexesToTimeline(timeline);
        }

    }

    public void writeNamesToTimeline(String[] timeline) {
        // iterate through the phonemes
        for (int i = 0; i < this.phoneme.length; i++) {
            // copy the phoneme text to the timeline
            this.phoneme[i].writeNamesToTimeline(timeline);
        }

    }

    public void render(Graphics g) {

        // render the box for the sentance
        this.renderHex(g);

        // render the child phonemens
        for (int i = 0; i < this.phoneme.length; i++) {
            this.phoneme[i].render(g);
        }
    }

    TextChunk hit(int frame, int y) {
        // in word frame range?
        if (!this.inFrame(frame)) {
            return null;
        }

        // in word?
        if (this.inY(y)) {
            LipsyncManager.dopeSheet.activeWord = this;
            System.out.println("in word " + this.text);
            return this;
        }

        // check children
        for (int i = 0; i < this.phoneme.length; i++) {
            TextChunk hitChunk = this.phoneme[i].hit(frame, y);
            if (hitChunk != null) {
                return hitChunk;
            }
        }

        // no matches
        return null;

    }

    int calcFramesNeeded() {
        // size is equal to number of phonemes 
        return this.phoneme.length;
    }

    void move(int newStart, int newEnd, int hitPart) {

        // check start range
        if (index == 0) {
            // don't go past start of sentance
            if (newStart < parent.startFrame) {
                newStart = parent.startFrame;
            }
        } else if (newStart <= parent.word[index - 1].endFrame) {
            // don't go past end of prior word
            newStart = parent.word[index - 1].endFrame + 1;
        }


        // check end range
        if (index == parent.word.length - 1) {
            // last item
            if (newEnd > parent.endFrame) {
                // don't go past end of parent
                newEnd = parent.endFrame;
            }
        } else if (newEnd >= parent.word[index + 1].startFrame) {
            // don't go past sibling
            newEnd = parent.word[index + 1].startFrame - 1;
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
            // do nothing
            return;
        }

        // resized or just shifted?
        boolean resized = (this.endFrame - this.startFrame != newEnd - newStart);
        int delta = newStart - this.startFrame;

        // move
        this.startFrame = newStart;
        this.endFrame = newEnd;

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

        // adjust children?
        if (resized) {
            // readjust all the children
            this.recalcChildren();
        } else {
            // just shift the children
            this.shiftChildren(delta);
        }

    }

    void shiftChildren(int delta) {
        // reallocate children		
        for (float i = 0; i < this.phoneme.length; i++) {
            // get a phoneme
            Phoneme child = this.phoneme[(int) i];

            // adjust start and end
            child.startFrame += delta;
            child.endFrame += delta;
        }

    }

    void recalcChildren() {
        // calculate amount of padding to distribute
        float scale = (1 + this.endFrame - this.startFrame) / (float) this.phoneme.length;

        // reallocate children		
        for (float i = 0; i < this.phoneme.length; i++) {
            // get a phoneme
            Phoneme child = this.phoneme[(int) i];

            // adjust
            child.startFrame = this.startFrame + (int) (i * scale);
            child.endFrame = this.startFrame + (int) ((i + 1) * scale) - 1;
        }

    }

    public void writeXml(FileWriter stream)
            throws IOException {

        stream.write("<word>\n");

        // sentance information
        Export.writeTag(stream, "index", this.index);
        Export.writeTag(stream, "startFrame", this.startFrame);
        Export.writeTag(stream, "endFrame", this.endFrame);
        Export.writeTag(stream, "row", this.row);
        Export.writeTag(stream, "text", this.text);
        Export.writeTag(stream, "phonemeString", this.phonemeString);

        // the number of phonemes
        stream.write("<phonemes>\n");
        int count = this.phoneme.length;
        Export.writeTag(stream, "count", count);
        for (int i = 0; i < count; i++) {
            // write the XML for each word
            this.phoneme[i].writeXml(stream);
        }
        stream.write("</phonemes>\n");
        stream.write("</word>\n");
    }

    public void readXml(SimpleXmlReader xmlReader)
            throws IOException {

        xmlReader.getTag("<word>");

        // sentance information
        this.index = xmlReader.getInt("index");
        this.startFrame = xmlReader.getInt("startFrame");
        this.endFrame = xmlReader.getInt("endFrame");
        this.row = xmlReader.getInt("row");
        this.text = xmlReader.getString("text");
        this.phonemeString = xmlReader.getString("phonemeString");

        // the phonemes		
        xmlReader.getTag("<phonemes>");
        int count = xmlReader.getInt("count");

        // allocate the phonemes
        this.phoneme = new Phoneme[count];

        // read the phonemes
        for (int i = 0; i < count; i++) {
            // create a default phoneme
            this.phoneme[i] = new Phoneme(this, i, "", 0, 0);
            // set to the stored value			
            this.phoneme[i].readXml(xmlReader);
        }
        xmlReader.getTag("</phonemes>");
        xmlReader.getTag("</word>");
    }
}
