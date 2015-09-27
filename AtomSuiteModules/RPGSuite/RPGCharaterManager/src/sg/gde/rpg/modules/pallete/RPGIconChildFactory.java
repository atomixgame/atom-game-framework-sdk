package sg.gde.rpg.modules.pallete;

import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class RPGIconChildFactory extends ChildFactory<RPGPaletteIcon> {

    private final Category category;

    RPGIconChildFactory(Category category) {
        this.category = category;
    }

    @Override
    protected boolean createKeys(List<RPGPaletteIcon> list) {
        list.add(new RPGPaletteIcon(0, category.getName(), "sg/gde/rpg/icons/x32/game1/King-Ikthusius-in-Armour-icon.png"));
        list.add(new RPGPaletteIcon(1, category.getName(), "sg/gde/rpg/icons/x32/game1/King-Ikthusius-in-Armour-icon.png"));
        list.add(new RPGPaletteIcon(2, category.getName(), "sg/gde/rpg/icons/x32/game1/King-Ikthusius-in-Armour-icon.png"));
        return true;
    }

    @Override
    protected Node createNodeForKey(RPGPaletteIcon key) {
        return new ShapeNode(key);
    }

    public class ShapeNode extends AbstractNode {

        public ShapeNode(RPGPaletteIcon key) {
            super(Children.LEAF);
            setIconBaseWithExtension(key.getImage());
        }
    }
}