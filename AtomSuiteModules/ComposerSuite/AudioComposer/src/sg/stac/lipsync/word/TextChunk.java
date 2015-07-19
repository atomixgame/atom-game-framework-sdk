package sg.stac.lipsync.word;

//TextChunk.java
//Base class for Sentance, Word and Phoneme
//
//(c) 2005 David Cuny
//http://jlipsync.sourceforge.net
//Released under Qt Public License
import sg.stac.lipsync.ui.DopeSheet;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;
import sg.stac.audio.wav.WaveManager;
import sg.stac.utils.SimpleXmlReader;

public abstract class TextChunk {
    // for drawing the text box

    public static int polyX[] = new int[6];
    public static int polyY[] = new int[6];
    public int startFrame;
    public int endFrame;
    public String text;
    public int row;
    public int index;
    public Color foreColor;
    public Color backColor;
    protected WaveManager waveManager;

    public final void renderHex(Graphics g) {

        // too small?
        if (this.endFrame < this.startFrame) {
            return;
        }

        int halfWide = DopeSheet.frameWidth / 2;
        int halfHigh = DopeSheet.boxHeight / 2;

        // position of the box
        int x1 = (this.startFrame - 1) * DopeSheet.frameWidth;
        int x2 = (this.endFrame) * DopeSheet.frameWidth;
        int y1 = this.row;
        int y2 = this.row + DopeSheet.boxHeight;

        // point in the polygon
        TextChunk.polyX[0] = x1;
        TextChunk.polyX[1] = x1 + halfWide;
        TextChunk.polyX[2] = x2 - halfWide;
        TextChunk.polyX[3] = x2;
        TextChunk.polyX[4] = x2 - halfWide;
        TextChunk.polyX[5] = x1 + halfWide;

        TextChunk.polyY[0] = y1 + halfHigh;
        TextChunk.polyY[1] = y1;
        TextChunk.polyY[2] = y1;
        TextChunk.polyY[3] = y1 + halfHigh;
        TextChunk.polyY[4] = y2;
        TextChunk.polyY[5] = y2;

        // draw the fill
        g.setColor(this.backColor);
        g.fillPolygon(TextChunk.polyX, TextChunk.polyY, 6);

        // draw the outline 
        g.setColor(this.foreColor);
        g.drawPolygon(TextChunk.polyX, TextChunk.polyY, 6);

        // draw the text inside
        g.setColor(Color.black);
        g.drawString(this.text, x1 + 4, y2 - 5);
    }

    public final void renderBox(Graphics g) {

        // position of the box
        int x1 = (this.startFrame - 1) * DopeSheet.frameWidth;
        int x2 = (this.endFrame) * DopeSheet.frameWidth;
        int y1 = this.row;
        int y2 = this.row + DopeSheet.boxHeight;

        // draw a box
        g.setColor(this.backColor);
        g.fillRect(x1, y1, x2 - x1, y2 - y1);
        g.setColor(this.foreColor);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);

        // draw the text inside
        g.setColor(Color.black);
        g.drawString(this.text, x1 + 2, y2 - 5);
    }

    public final boolean inFrame(int frame) {
        // return true of frame is in range of this object
        return (frame >= this.startFrame && frame <= this.endFrame);
    }

    public final boolean inY(int y) {
        // return true if y is in range of this object
        return (y >= row && y <= row + DopeSheet.boxHeight);
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public abstract TextChunk hit(int frame, int y);

    public abstract void move(int newStart, int newEnd, int hitPart);

    public abstract void readXml(SimpleXmlReader xmlReader) throws IOException;

    public abstract void recalcChildren();

    public abstract void render(Graphics g);

    public abstract void writeXml(FileWriter stream) throws IOException;
}
