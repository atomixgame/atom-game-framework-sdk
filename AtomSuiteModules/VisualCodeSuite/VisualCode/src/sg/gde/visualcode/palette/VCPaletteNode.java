/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.visualcode.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * VCPalleteNode is the node in the palette.
 * @author cuong.nguyenmanh2
 */
public class VCPaletteNode extends AbstractNode {

    public VCPaletteNode(VCNodeIcon key) {
        super(Children.LEAF);
        setIconBaseWithExtension(key.getImage());
    }
}
