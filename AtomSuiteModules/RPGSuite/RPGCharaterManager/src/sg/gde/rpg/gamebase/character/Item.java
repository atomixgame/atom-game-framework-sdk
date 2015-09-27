/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.gamebase.character;

/**
 *
 * @author CuongNguyen
 */
public class Item {

    int id;
    String name;
    int cost = 100;
    int gem = 100;
    int[] requireItems;
    int version = 0;
    int rank = 1;
    String updateState;
    String versionState;
    String description = "";
    GameCharacter owner;
    String icon;
    int width = 10;
    int height = 1;

    public Item(int id, String name) {
        this.name = name;
    }

    public Item(int id, String name, int cost, String description, String icon) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.icon = icon;
    }

    public void pick() {
    }

    public void drop() {
    }

    public void onUseItem(GameCharacter character) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int[] getRequireItems() {
        return requireItems;
    }

    public void setRequireItems(int[] requireItems) {
        this.requireItems = requireItems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GameCharacter getOwner() {
        return owner;
    }

    public void setOwner(GameCharacter owner) {
        this.owner = owner;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
