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
public class ProcessingCircleSketch extends PApplet {

    String message = "tickle";
    float x, y; // X and Y coordinates of text
    float hr, vr;  // horizontal and vertical radius of the text

    public void setup() {
        size(400, 400);
        background(0);
        setupText();
    }

    public void draw() {
        background(0);
        fill(200);
        ellipseMode(CENTER);
        ellipse(mouseX, mouseY, 40, 40);
        drawText();
    }

    void setupText() {
        //size(640, 360);

        // Create the font
        textFont(createFont("Georgia", 36));
        textAlign(CENTER, CENTER);

        hr = textWidth(message) / 2;
        vr = (textAscent() + textDescent()) / 2;
        noStroke();
        x = width / 2;
        y = height / 2;
    }

    void drawText() {
        // Instead of clearing the background, fade it by drawing
        // a semi-transparent rectangle on top
        fill(204, 120);
        rect(0, 0, width, height);

        // If the cursor is over the text, change the position
        if (abs(mouseX - x) < hr
                && abs(mouseY - y) < vr) {
            x += random(-5, 5);
            y += random(-5, 5);
        }
        fill(0);
        text("tickle", x, y);
    }
}
