package sg.gde.visualcode.palette;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

public class VCNodeChildFactory extends ChildFactory<VCNodeIcon> {

    private final VCCategory category;

    VCNodeChildFactory(VCCategory category) {
        this.category = category;
    }

    @Override
    protected boolean createKeys(List<VCNodeIcon> list) {
        list.add(new VCNodeIcon(0, category.getName(), "sg/atom/visualcode/resources/images/nodes/image1.png"));
        list.add(new VCNodeIcon(1, category.getName(), "sg/atom/visualcode/resources/images/nodes/image2.png"));
        list.add(new VCNodeIcon(2, category.getName(), "sg/atom/visualcode/resources/images/nodes/image3.png"));
        return true;
    }

    @Override
    protected Node createNodeForKey(VCNodeIcon key) {
        return new VCPaletteNode(key);
    }
}