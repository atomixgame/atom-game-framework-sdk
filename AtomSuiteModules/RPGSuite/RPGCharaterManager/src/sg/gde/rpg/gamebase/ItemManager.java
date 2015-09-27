/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.gamebase;

import com.jme3.asset.AssetManager;
import java.util.ArrayList;
import sg.gde.rpg.gamebase.character.Item;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ItemManager {

    /**
     * Singleton reference of ItemManager.
     */
    private static ItemManager defaultInstance;
    private AssetManager assetManager;
    private ArrayList<Item> items;
    private int gid = 0;
    public static String IMAGE_DIR = "Interface/Images/Icons/Items/";

    /**
     * Constructs singleton instance of ItemManager.
     */
    private ItemManager() {
        loadItems();
    }

    /**
     * Provides reference to singleton object of ItemManager.
     *
     * @return Singleton instance of ItemManager.
     */
    public static synchronized final ItemManager getInstance() {
        if (defaultInstance == null) {
            defaultInstance = new ItemManager();
        }
        return defaultInstance;
    }

    public Item getAwardItem() {
        return getItem(gid - 1);
    }

    public void loadItems() {
        items = new ArrayList<Item>();
//        assetManager = CuteHeroesMain.getInstance().getAssetManager();
//        assetManager.registerLoader(TextLineLoader.class, "txt");
//        ArrayList<String> lines = (ArrayList<String>) assetManager.loadAsset("Data/Items/Sword.txt");
//        
//        for (String line:lines){
//            String[] elements = line.split(",");
//            Item item = new Item(gid++, elements[0]);
//            item.cost = elements[1];
//            items.add(item);
//            
//        }
        //FIXME: For testing purpose
    }

    public Item getItem(int i) {
        return items.get(i);
    }

    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
