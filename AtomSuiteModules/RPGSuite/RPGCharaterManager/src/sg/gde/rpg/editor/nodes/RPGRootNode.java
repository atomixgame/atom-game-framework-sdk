/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.editor.nodes;

import java.io.Serializable;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import sg.gde.rpg.gamebase.RPGModel;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RPGRootNode extends AbstractNode implements Serializable, Lookup.Provider {

    public RPGRootNode(RPGModel gameRoot) {
        super(Children.LEAF, Lookups.singleton(gameRoot));
    }
}
