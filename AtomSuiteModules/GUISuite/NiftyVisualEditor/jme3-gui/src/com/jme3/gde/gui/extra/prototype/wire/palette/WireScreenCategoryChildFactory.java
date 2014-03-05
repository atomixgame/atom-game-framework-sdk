package com.jme3.gde.gui.extra.prototype.wire.palette;

import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class WireScreenCategoryChildFactory extends ChildFactory<WireScreenCategory> {

    @Override
    protected boolean createKeys(List<WireScreenCategory> list) {
        list.add(new WireScreenCategory("Shapes"));
        return true;
    }

    @Override
    protected Node createNodeForKey(WireScreenCategory category) {
        return new CategoryNode(category);
    }

    public class CategoryNode extends AbstractNode {
        public CategoryNode(WireScreenCategory category) {
            super(Children.create(new WireScreenChildFactory(category), true));
            setDisplayName(category.getName());
        }
    }
    
}