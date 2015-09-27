package sg.gde.rpg.modules.pallete;

import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class CategoryChildFactory extends ChildFactory<Category> {

    @Override
    protected boolean createKeys(List<Category> list) {
        list.add(new Category("Characters"));
        list.add(new Category("Locations"));
        list.add(new Category("Items"));
        list.add(new Category("Variables"));
        return true;
    }

    @Override
    protected Node createNodeForKey(Category category) {
        return new CategoryNode(category);
    }

    public class CategoryNode extends AbstractNode {

        public CategoryNode(Category category) {
            super(Children.create(new RPGIconChildFactory(category), true));
            setDisplayName(category.getName());
        }
    }
}