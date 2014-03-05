/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jme3.gde.gui.multiview.event.input;

/**
 *
 * @author normenhansen
 */
public class MouseInputEvent {
    public int x;
    public int y;
    public int button;
    public int huh;
    public boolean pressed;

    public MouseInputEvent(int x, int y, int button, int huh, boolean pressed) {
        this.x = x;
        this.y = y;
        this.button = button;
        this.huh = huh;
        this.pressed = pressed;
    }

}
