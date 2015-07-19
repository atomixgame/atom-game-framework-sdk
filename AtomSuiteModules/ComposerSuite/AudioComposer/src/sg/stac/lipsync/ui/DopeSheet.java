package sg.stac.lipsync.ui;

import sg.stac.utils.graphics.ScrollablePicture;
import sg.stac.audio.wav.WaveFile;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.Scrollable;
import sg.stac.audio.wav.WaveManager;
import sg.stac.lipsync.word.Part;
import sg.stac.lipsync.word.Phoneme;
import sg.stac.lipsync.word.Sentence;
import sg.stac.lipsync.word.TextChunk;
import sg.stac.lipsync.word.Word;

/**
 * @author David Cuny
 *
 * Graphically display dialog
 */
public class DopeSheet extends ScrollablePicture implements MouseListener,
        MouseMotionListener, Scrollable {

    private static final long serialVersionUID = 1L;
    public static final Color foreColor = new Color(192, 192, 192);
    public static final Color backColor = new Color(255, 255, 255);
    public static final Color markerForeColor = new Color(128, 0, 0);
    public static final Color markerBackColor = new Color(255, 127, 127);
    // what part was hit?
    public static final int HIT_NOTHING = 0;
    public static final int HIT_FRONT = 1;
    public static final int HIT_BACK = 2;
    public static final int HIT_MIDDLE = 3;
    public WaveFile waveFile;
    public int faceIndexTimeline[];
    public Part part = null;
    // object being dragged
    public Sentence activeSentence = null;
    public Word activeWord = null;
    public Phoneme activePhoneme = null;
    public int activeHitPart = HIT_NOTHING;
    public int mouseAnchor = 0;
    public int activeStartFrame = 0;
    public int activeEndFrame = 0;
    public boolean activeTimeMarker = false;
    public int timeMarkerAt = 1;
    // width of a single frame, in pixels
    public static int frameWidth = 8;
    // height of a box
    public static final int boxHeight = 20;
    // the current cursor
    public int currentCursor = Cursor.DEFAULT_CURSOR;
    private WaveManager waveManager;

    public DopeSheet(int frameCount) {

        // initialize
        this.initialize(frameCount);

        // add a mouse listener
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void initialize(int frameCount) {

        // create a wave file
        this.waveFile = new WaveFile(frameCount);

        // clear the part
        this.part = null;

        // reset all state information
        this.activeSentence = null;
        this.activeWord = null;
        this.activePhoneme = null;
        this.activeHitPart = HIT_NOTHING;
        this.mouseAnchor = 0;
        this.activeStartFrame = 0;
        this.activeEndFrame = 0;
        this.activeTimeMarker = false;
        this.timeMarkerAt = 1;

        // rebuild the phoneme timeline
        rebuildFaceIndexTimeline();

        // allocate an image
        super.setImage(frameCount * DopeSheet.frameWidth, 240);

    }

    public void loadWave(File file) {
        // load the file into the WaveFile
        this.waveFile.loadWave(file.getPath());

        // get the frame count
        int frames = this.waveFile.frameCount;

        // rebuild the phoneme timeline
        rebuildFaceIndexTimeline();

        // resize drawing area
        super.setImage(frames * DopeSheet.frameWidth, 240);

    }

    public void setDialog(String text) {
        this.part = new Part(text);
        rebuildFaceIndexTimeline();
    }

    public void rebuildFaceIndexTimeline() {

        // get frame count
        int frameCount = this.waveFile.frameCount;

        // allocate space for faces at each frame
        this.faceIndexTimeline = new int[frameCount + 1];

        // iterate through each element
        for (int i = 1; i < frameCount + 1; i++) {
            // set to zero
            this.faceIndexTimeline[i] = 0;
        }

        // update the faces for each part
        if (this.part != null) {
            this.part.writeIndexesToTimeline(this.faceIndexTimeline);
        }

    }

    public final String[] buildFaceNameTimeline() {

        int frameCount = this.waveFile.frameCount;

        // allocate space
        String[] faceNameTimeline = new String[frameCount + 1];

        // iterate through the 
        for (int i = 1; i <= frameCount; i++) {
            faceNameTimeline[i] = new String("Closed");
        }

        // update the faces for each part
        if (this.part != null) {
            this.part.writeNamesToTimeline(faceNameTimeline);
        }

        return faceNameTimeline;
    }

    public final String[] buildWordTimeline() {

        int frameCount = this.waveFile.frameCount;

        // allocate space
        String[] wordTimeline = new String[frameCount + 1];

        // initialize to blanks
        for (int i = 1; i <= frameCount; i++) {
            wordTimeline[i] = new String("");
        }

        // update the words for each part
        if (this.part != null) {
            this.part.writeWordsToTimeline(wordTimeline);
        }

        return wordTimeline;
    }

    public void paintComponent(Graphics g) {

        int points = 12;

        // name is one of: Dialog, DialogInput, Monospaced, Serif, or SansSerif
        g.setFont(new Font("System", Font.BOLD, points));

        // repaint
        super.paintComponent(g);

        // get size of drawable area
        int height = getHeight();
        int width = getWidth();

        // clear the background?
        g.setColor(DopeSheet.backColor);
        g.fillRect(0, 0, width, height);

        // use actual frame width
        width = this.waveFile.frameCount * DopeSheet.frameWidth;

        int frames = this.waveFile.frameCount;
        int fps = this.waveFile.framesPerSecond;

        // position of the time marker
        int markerAtPixel = (this.timeMarkerAt - 1) * DopeSheet.frameWidth;

        // draw lines
        g.setColor(DopeSheet.foreColor);
        for (int i = 0; i < frames; i++) {
            // position of line
            int x = i * frameWidth;
            if (i > 0 && i % fps == 0) {
                g.drawLine(x, 0, x, height);
                g.drawString(i + "", x + 1, points - 2);
            } else {
                g.drawLine(x, points, x, height);
            }
        }

        // time marker
        g.setColor(DopeSheet.markerBackColor);
        g.fillRect(markerAtPixel, 0, DopeSheet.frameWidth, height);

        // box around the time marker
        g.setColor(DopeSheet.markerForeColor);
        g.drawString("" + timeMarkerAt, markerAtPixel + DopeSheet.frameWidth + 2, points - 2);
        g.drawRect(markerAtPixel, 0, DopeSheet.frameWidth, height);

        // draw the wave
        this.waveManager.render(g, width, 16, height);

        // draw the dialog
        if (this.part != null) {
            this.part.render(g);
        }

        // need to create face index timeline?
        if (this.faceIndexTimeline == null) {
            this.rebuildFaceIndexTimeline();
        }

        // marker in range of frames?
        if (this.timeMarkerAt <= frames) {
            // get the face to display
            int faceIndex = this.faceIndexTimeline[this.timeMarkerAt];
            // update the face panel
            //Main.mainWindow.facePanel.setFace(faceIndex);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        // right click?
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (activeWord != null) {
                // ask use for new phoneme breakdown of word
                activeWord.getNewBreakdown();

                // update faces on timeline
                //LipSyncMainApp.mainWindow.dopeSheet.rebuildFaceIndexTimeline();
            }
            return;
        }

        // doubleclick?
        if (e.getClickCount() == 2) {
            // any active objects?
            if (activeSentence != null || activeWord != null
                    || activePhoneme != null) {
                // play the sentance, leave pointer at end of frame
                waveManager.stopPlayingWave();
                this.waveFile.playFrames(activeStartFrame, activeEndFrame, activeEndFrame);
            }
        }
    }

    public int xToFrame(int x) {
        // get the number of frames
        int frames = this.waveFile.frameCount;

        // convert x to frame number
        int frame = (x / DopeSheet.frameWidth) + 1;
        if (frame < 1) {
            frame = 1;
        }
        if (frame > frames) {
            frame = frames;
        }

        return frame;
    }

    public int hitSection(int x, int y, boolean isShiftDown, TextChunk textChunk, int frame) {

        int result = HIT_NOTHING;

        // drag or resize?
        if (isShiftDown) {
            // dragging entire part
            result = HIT_MIDDLE;

        } else {
            // if hit part in first half of the object?
            int pixelStart = (textChunk.startFrame - 1) * DopeSheet.frameWidth;
            int pixelEnd = (textChunk.endFrame) * DopeSheet.frameWidth;
            int midPoint = pixelStart + ((pixelEnd - pixelStart) / 2);

            // on left or right side?
            if (x <= midPoint) {
                // hit the front
                result = HIT_FRONT;
            } else {
                // hit the back
                result = HIT_BACK;
            }

            // inside?
            if (frame > textChunk.startFrame + 2 && frame < textChunk.endFrame - 2) {
                result = HIT_MIDDLE;
            }
        }

        return result;

    }

    public void mousePressed(MouseEvent e) {
        // no dialog yet?
        if (this.part == null) {
            return;
        }

        // what frame did the click occur in?
        int x = e.getX();
        int y = e.getY();
        int frame = xToFrame(x);

        // deactivate prior object
        activeSentence = null;
        activeWord = null;
        activePhoneme = null;
        activeTimeMarker = false;

        // what chunk got hit?
        TextChunk hitChunk = this.part.hit(frame, y);
        if (hitChunk == null) {
            // hit time marker
            activeTimeMarker = true;
            timeMarkerAt = frame;

            // update the dopesheet
            this.repaint();

            return;
        }

        // determine which part was hit
        this.activeHitPart = hitSection(e.getX(), e.getY(), e.isShiftDown(), hitChunk, frame);

        // set anchor, start and end
        this.mouseAnchor = frame;
        this.activeStartFrame = hitChunk.startFrame;
        this.activeEndFrame = hitChunk.endFrame;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void mouseEntered(MouseEvent e) {
        // restore the default cursor
        this.currentCursor = Cursor.DEFAULT_CURSOR;
        this.setCursor(new Cursor(this.currentCursor));
    }

    public void mouseExited(MouseEvent e) {
        // restore the default cursor?
        if (this.currentCursor != Cursor.DEFAULT_CURSOR) {
            this.currentCursor = Cursor.DEFAULT_CURSOR;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent arg0) {
        // no dialog yet?
        if (this.part == null) {
            return;
        }

        // what frame did the click occur in?
        int frame = xToFrame(arg0.getX());

        // get change
        int delta = frame - this.mouseAnchor;

        int newStartPos = activeStartFrame;
        int newEndPos = activeEndFrame;

        // adjust the ends
        if (this.activeHitPart != HIT_BACK) {
            newStartPos += delta;
        }
        if (this.activeHitPart != HIT_FRONT) {
            newEndPos += delta;
        }

        // is anything active?
        // time marker?
        if (activeTimeMarker) {
            // move marker?
            if (frame != timeMarkerAt) {
                // set new position
                timeMarkerAt = frame;

                // play the frame
                waveManager.stopPlayingWave();
                this.waveFile.playFrames(frame, frame, timeMarkerAt);
            }

        } else if (this.activeSentence != null) {
            // handle drag for sentance
            this.activeSentence.move(newStartPos, newEndPos, this.activeHitPart);

        } else if (this.activeWord != null) {
            System.out.println("Active word");
            // handle drag for word
            this.activeWord.move(newStartPos, newEndPos, this.activeHitPart);

        } else if (this.activePhoneme != null) {
            System.out.println("Active phoneme");
            // handle drag for phoneme
            this.activePhoneme.move(newStartPos, newEndPos, this.activeHitPart);
        }

        // update faces on timeline
        this.rebuildFaceIndexTimeline();

        // update the panel
        this.repaint();

    }

    private final void useCursor(int id) {

        // not the current cursor?
        if (this.currentCursor != id) {
            // make it current
            this.currentCursor = id;
            // use it
            this.setCursor(new Cursor(id));
        }
    }

    void adjustCursor(int x, int y, boolean isShiftDown) {
        // skip if not initialized
        if (this.part == null) {
            return;
        }

        // get the frame number
        int frame = this.xToFrame(x);

        // what chunk is the mouse over?
        TextChunk overChunk = this.part.hit(frame, y);

        // not over anything?
        if (overChunk == null) {
            // set cursor to default
            useCursor(Cursor.DEFAULT_CURSOR);
            return;
        }

        // what part is the cursor over?
        int overPart = hitSection(x, y, isShiftDown, overChunk, frame);
        switch (overPart) {
            case HIT_BACK:
                useCursor(Cursor.E_RESIZE_CURSOR);
                break;

            case HIT_FRONT:
                useCursor(Cursor.W_RESIZE_CURSOR);
                break;

            case HIT_MIDDLE:
                useCursor(Cursor.HAND_CURSOR);
                break;

            case HIT_NOTHING:
                useCursor(Cursor.DEFAULT_CURSOR);
                break;
        }

    }

    public void mouseMoved(MouseEvent e) {
        adjustCursor(e.getX(), e.getY(), e.isShiftDown());

    }

    public void setWaveManager(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public WaveFile getWaveFile() {
        return waveFile;
    }
    
    
}
