package sg.stac.ui;

import sg.stac.audio.AudioException;

/**
 * Interface for communicating audio state changed events.
 *
 * @author stefie10
 *
 */
public interface AudioCtrlListener {

    public void play() throws AudioException;

    public void pause() throws AudioException;

    public void stop() throws AudioException;

    public void record() throws AudioException;
}