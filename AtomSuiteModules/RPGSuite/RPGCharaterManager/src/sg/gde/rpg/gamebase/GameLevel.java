/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.gamebase;

import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.HashMap;

/**
 *
 * @author CuongNguyen
 */
public class GameLevel {

    int id;
    String name;
    int point;
    int status;
    int picture;
    String path;
    String description;
//    HashMap<String, Transform> camAngles;

    public GameLevel(String name) {
        this.name = name;
    }

    
    public Vector3f getStartPos() {
        return Vector3f.ZERO;
    }

    public Node getLevelNode() {
        return new Node("LevelNode");
    }

    public void load() {
    }

    public GameLevel nextLevel() {
        return null;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
    
}
