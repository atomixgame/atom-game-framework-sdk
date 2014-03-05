/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3timeline.components.display.processing;

import processing.core.PApplet;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ProcessingCircle2 extends PApplet {

    public void setup() {
        size(640, 360);
        background(102);
    }

    public void draw() {
        // Call the variableEllipse() method and send it the
        // parameters for the current mouse position
        // and the previous mouse position
        variableEllipse(mouseX, mouseY, pmouseX, pmouseY);
    }

// The simple method variableEllipse() was created specifically 
// for this program. It calculates the speed of the mouse
// and draws a small ellipse if the mouse is moving slowly
// and draws a large ellipse if the mouse is moving quickly 
    void variableEllipse(int x, int y, int px, int py) {
        float speed = abs(x - px) + abs(y - py);
        stroke(speed);
        ellipse(x, y, speed, speed);
    }
}
