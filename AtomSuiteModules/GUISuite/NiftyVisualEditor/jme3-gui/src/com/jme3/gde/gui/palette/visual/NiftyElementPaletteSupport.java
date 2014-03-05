
package com.jme3.gde.gui.palette.visual;

import com.jme3.gde.gui.palette.visual.dnd.NiftyElementDnDHandler;
import javax.swing.Action;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

public class NiftyElementPaletteSupport {
    
    public static PaletteController createPalette() {
        AbstractNode paletteRoot = new AbstractNode(Children.create(new NiftyElementCategoryChildFactory(), true));
        paletteRoot.setName("Palette Root");
        
        // FIXME: Declare the cross functional Swing & Netbean DnD mechanism!
        return PaletteFactory.createPalette( paletteRoot, new NiftyVisualElementPaletteActions(), null, new NiftyElementDnDHandler() );
    }
    
    private static class NiftyVisualElementPaletteActions extends PaletteActions {
        @Override
        public Action[] getImportActions() {
            return null;
        }
        @Override
        public Action[] getCustomPaletteActions() {
            return null;
        }
        @Override
        public Action[] getCustomCategoryActions(Lookup lookup) {
            return null;
        }
        @Override
        public Action[] getCustomItemActions(Lookup lookup) {
            return null;
        }
        @Override
        public Action getPreferredAction(Lookup lookup) {
            return null;
        }
    }

}
