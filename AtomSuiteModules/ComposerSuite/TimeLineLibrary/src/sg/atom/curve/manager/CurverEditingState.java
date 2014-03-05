package sg.atom.curve.manager;

import sg.atom.curve.BezierCanvas;

/**
 * Stores the state of the application and displays it on the status line. *
 */
public class CurverEditingState {

    // Hack solution for repainting when toggling preview and clearing
    // the drawing...
    protected BezierCanvas bezierCanvas;
    
    public static final int PASSIVE = 0;
    public static final int CREATING = 1;
    public static final int EDITING = 2;
    /*
    protected String[] messages = {"Press \"New\" to add a curve. "
        + "Drag the control points to edit the curves.",
        "Click to create control points.",
        "Move point to desired position."};
        */ 
    protected int state = CREATING;
    protected int samples = 100;
    protected boolean preview = true;

    public CurverEditingState() {
        setState(CREATING);
    }

    public void setState(int state) {
        this.state = state;
        //status.setText(messages[state]);
    }

    public int getState() {
        return state;
    }

    public void setSamples(int samples) {
        this.samples = samples;
        bezierCanvas.repaint();
    }

    public int getSamples() {
        return this.samples;
    }

    public void togglePreview() {
        preview = !preview;
        bezierCanvas.repaint();
    }

    public boolean isPreview() {
        return preview;
    }

    public void setCanvas(BezierCanvas bezierCanvas) {
        this.bezierCanvas = bezierCanvas;
    }
}
