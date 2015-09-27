/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.gamebase.story.dialogue;

import java.util.ArrayList;
import org.jgrapht.DirectedGraph;
import sg.gde.rpg.editor.visual.dialoge.DialogueGraphSceneImpl;
import sg.gde.rpg.gamebase.RPGGraphNode;
import sg.gde.rpg.gamebase.character.GameCharacter;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Dialogue {

    public static final int DIALOGE_TYPE_SEQUENCE = 0;
    public static final int DIALOGE_TYPE_GRAPH = 1;
    public static final int DIALOGE_TYPE_MULTICHOICE = 1;
    
    ArrayList<GameCharacter> characters;
    RPGGraphNode rootNode = new RPGGraphNode(DialogueGraphSceneImpl.NO_IMAGE);
    private DirectedGraph<DialogueNode, DialogueEdge> dialogueGraph;

    int type;
    public RPGGraphNode getRootNode() {
        return rootNode;
    }

    public ArrayList<GameCharacter> getCharacters() {
        return characters;
    }

    public DirectedGraph<DialogueNode, DialogueEdge> getDialogueGraph() {
        return dialogueGraph;
    }
    
    
}
