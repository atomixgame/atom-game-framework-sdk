package com.jme3.gde.gui.palette.visual;


import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class NiftyElementCategoryChildFactory extends ChildFactory<NiftyElementCategory> {

    @Override
    protected boolean createKeys(List<NiftyElementCategory> list) {
        list.add(new NiftyElementCategory("Normal Style"));
        list.add(new NiftyElementCategory("Black Style"));
        list.add(new NiftyElementCategory("White Style"));
        list.add(new NiftyElementCategory("Scifi Style"));
        return true;
    }

    @Override
    protected Node createNodeForKey(NiftyElementCategory category) {
        return new CategoryNode(category);
    }

    public class CategoryNode extends AbstractNode {
        public CategoryNode(NiftyElementCategory category) {
            super(Children.create(new NiftyElementChildFactory(category), true));
            setDisplayName(category.getName());
        }
    }
    
}