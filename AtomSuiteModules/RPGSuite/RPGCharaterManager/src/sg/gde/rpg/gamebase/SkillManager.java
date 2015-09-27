/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.gamebase;

import com.jme3.asset.AssetManager;
import java.util.ArrayList;
import sg.gde.rpg.gamebase.character.GameCharacter;
import sg.gde.rpg.gamebase.character.Skill;

/**
 *
 * @author CuongNguyen
 */
public class SkillManager {

    private AssetManager assetManager;
    private ArrayList<Skill> skills;
    private int gid = 0;
    public static String IMAGE_DIR = "Interface/Images/Icons/Skills/";
    /**
     * Singleton reference of SkillManager.
     */
    private static SkillManager defaultInstance;

    /**
     * Constructs singleton instance of SkillManager.
     */
    private SkillManager() {

        loadSkills();
    }

    /**
     * Provides reference to singleton object of SkillManager.
     *
     * @return Singleton instance of SkillManager.
     */
    public static synchronized final SkillManager getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new SkillManager();
        }
        return defaultInstance;
    }

    public void loadSkills() {
        this.skills = new ArrayList<Skill>();


    }

    public Skill getSkillByName(String itemName) {
        for (Skill skill : skills) {
            if (skill.getName().equals(itemName)) {
                return skill;
            }
        }
        return null;
    }

    public void addSkill(Skill skill) {
    }

    public void upgrade(GameCharacter character, Skill skill) {
    }
}
