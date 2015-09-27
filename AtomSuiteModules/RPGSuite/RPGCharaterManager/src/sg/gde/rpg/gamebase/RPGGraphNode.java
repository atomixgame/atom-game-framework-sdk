/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.gamebase;

import java.awt.Image;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RPGGraphNode {

    private Image image;
    String characterName;

    public RPGGraphNode(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public String getCharacterName() {
        return "Hero";
    }
}
