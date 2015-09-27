package sg.gde.visualcode.palette;

import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class VCCategoryChildFactory extends ChildFactory<VCCategory> {

    @Override
    protected boolean createKeys(List<VCCategory> list) {
        //FIXME: Add the categories here. Load from XML?

        list.add(new VCCategory("Actor"));
        list.add(new VCCategory("Animation"));

        loadNodesFromXML();
        return true;
    }

    @Override
    protected Node createNodeForKey(VCCategory category) {
        return new CategoryNode(category);
    }

    public class CategoryNode extends AbstractNode {

        public CategoryNode(VCCategory category) {
            super(Children.create(new VCNodeChildFactory(category), true));
            setDisplayName(category.getName());
        }
    }

    public void loadNodes() {
    }

    public void loadNodesFromContext() {
        //((org.openide.text.CloneableEditorSupport) context)
    }

    /**
     * FIXME: Use Disgester instead.
     * 
     */
    public void loadNodesFromXML() {

    }
}