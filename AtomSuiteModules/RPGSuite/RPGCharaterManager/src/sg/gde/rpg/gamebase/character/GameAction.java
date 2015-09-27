/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.gamebase.character;

import sg.gde.rpg.gamebase.character.Skill;
import java.util.concurrent.Callable;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GameAction implements Callable<Skill>, Runnable {

    int id;
    Skill skill;
    GameCharacter character;
    int status;
    boolean actived;

    public GameAction(int id, Skill skill, GameCharacter character) {
        this.id = id;
        this.skill = skill;
        this.character = character;
    }

    public void run() {
    }

    public Skill call() throws Exception {
        return skill;
    }
}
