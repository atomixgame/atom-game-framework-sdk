package sg.gde.rpg.gamebase.character;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;
import sg.gde.rpg.gamebase.ItemManager;
import sg.gde.rpg.gamebase.Player;
import sg.gde.rpg.gamebase.SkillManager;

/**
 *
 * @author CuongNguyen
 */
public class GameCharacter {

    //Constant
    public static final int STATUS_NONE = 0;
    public static final int STATUS_JOINED = 1;
    public static final int STATUS_BANNED = 2;
    // Attributes
    public String name;
    public Player owner;
    public int type;
    public int sex;
    public boolean isNpc = true;
    public int health = 800;
    public int maxHP = 1000;
    public int mana = 100;
    public int maxMana = 100;
    public int attack = 100;
    public int defend = 100;
    public int turn = 1;
    public int speed = 1;
    public int intelligent = 100;
    public int status = 0;
    public int rank = 1;
    public int skillStatus = 0;
    public int skillEffected = 0;
    public float range = 40;
    //Turn
    public float turnDelay = 0;
    public float turnDelayMax = 5;
    public int turnOrder;
    public int turnNum;
    public float turnRefreshRate = 1;
    
    //Gameplay
    protected ArrayList<Skill> skills;
    protected ArrayList<Item> items;
    protected ArrayList<GameAction> actions;
    protected Inventory inventory;
    public Skill mainSkill;
    public Item currentItem;
    
    // Visualization
    protected boolean staged = false;
    protected Spatial model;
    

    public GameCharacter(String name) {
        this.name = name;
        skills = new ArrayList<Skill>();
    }

    public GameCharacter(String name, Spatial model) {
        this.name = name;
        this.model = model;
        this.skills = new ArrayList<Skill>();
        this.isNpc = true;
        this.turnDelayMax = 8;
        createSkills();
    }

    public GameCharacter(String name, Player owner, Spatial model) {
        this(name, model);
        this.isNpc = false;
        this.owner = owner;
        this.owner.getTeam().add(this);
        this.turnDelayMax = 2;
        createInventory();
    }

    public void createSkills() {
        SkillManager skillManager = SkillManager.getInstance();
    }

    public void createInventory() {
        inventory = new Inventory(5, 5);
        items = new ArrayList<Item>();
    }
    // Events -------------------------------------

    //Actions-----------------------------------------------------------------------
    public void addItem(Item item){
//        inventory.add(item);
    }

    // For Collections of Characters!-----------------------------------------
    public static List<GameCharacter> getBy(List<GameCharacter> characters, Predicate<GameCharacter> predicate) {
        ArrayList<GameCharacter> result = new ArrayList<GameCharacter>();
        for (GameCharacter gc : characters) {
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public static List<GameCharacter> getBy(List<GameCharacter> characters, Predicate<GameCharacter>... filters) {
        ArrayList<GameCharacter> result = new ArrayList<GameCharacter>();
        for (GameCharacter gc : characters) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public static float distance(GameCharacter owner, GameCharacter target) {
        return owner.getLocation().distance(target.getLocation());
    }

    public static Predicate<GameCharacter> byRange(final GameCharacter centerCharacter, final float checkRange) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                Vector3f center = centerCharacter.getLocation();
                boolean inRange = gameCharacter.getLocation().distance(center) < checkRange;
                return inRange;
            }
        };
    }

    public static Predicate<GameCharacter> byAlive(final boolean isAlive) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                return gameCharacter.isDead() != isAlive;
            }
        };
    }

    public static Predicate<GameCharacter> byRank(final int rank) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                return gameCharacter.rank == rank;
            }
        };
    }

    public static Predicate<GameCharacter> byEnemy(final GameCharacter centerCharacter) {
        return new Predicate<GameCharacter>() {
            public boolean apply(GameCharacter gameCharacter) {
                return centerCharacter.isNpc != gameCharacter.isNpc;
            }
        };
    }

    public static class GetByRankPredicate implements Predicate<GameCharacter> {

        int rank;

        public GetByRankPredicate(int rank) {
            this.rank = rank;
        }

        public boolean apply(GameCharacter character) {
            return character.rank == rank;
        }
    }

    public static class GetByRangePredicate implements Predicate<GameCharacter> {

        Vector3f center;
        float checkRange;

        public GetByRangePredicate(Vector3f center, float range) {
            this.center = center;
            this.checkRange = range;
        }

        public GetByRangePredicate(GameCharacter centerCharacter, float range) {
            this(centerCharacter.getLocation(), range);
        }

        public boolean apply(GameCharacter gameCharacter) {
            boolean inRange = gameCharacter.getLocation().distance(center) < checkRange;
            //System.out.println("InRange " + checkRange + " : " + inRange);
            return inRange;
        }
    }
    
    
    public Node getModelNode() {
        return (Node) model;
    }

    public Spatial getModel() {
        return model;
    }

    public void setPlayer(Player owner) {
        this.owner = owner;
    }

    public void setModel(Spatial playerModel) {
        this.model = playerModel;
    }

    public Vector3f getLocation() {
        return this.getModel().getLocalTranslation();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }


    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }


}
