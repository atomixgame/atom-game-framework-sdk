package com.jme3.gde.gui.extra.prototype.wire.palette;

import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class WireScreenChildFactory extends ChildFactory<WireNiftyScreen> {

    private final WireScreenCategory category;

    WireScreenChildFactory(WireScreenCategory category) {
        this.category = category;
    }

    @Override
    protected boolean createKeys(List<WireNiftyScreen> list) {
        list.add(new WireNiftyScreen(0, category.getName(), "sg/visualpalette/palette/image1.png"));
        list.add(new WireNiftyScreen(1, category.getName(), "sg/visualpalette/palette/image2.png"));
        list.add(new WireNiftyScreen(2, category.getName(), "sg/visualpalette/palette/image3.png"));
        return true;
    }

    @Override
    protected Node createNodeForKey(WireNiftyScreen key) {
        return new WireScreenNode(key);
    }

    public class WireScreenNode extends AbstractNode {
        public WireScreenNode(WireNiftyScreen key) {
            super(Children.LEAF);
            setIconBaseWithExtension(key.getImage());
        }
    }
    
}