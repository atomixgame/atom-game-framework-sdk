package sg.gde.visualcode.palette;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.BeanInfo;
import java.io.IOException;
import javax.swing.Action;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;

/**
 * FIXME: Integrate
 * http://bits.netbeans.org/dev/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html
 *
 * @author cuong.nguyenmanh2
 */
public class VCPaletteSupport {

    public static PaletteController createPalette() {
        AbstractNode paletteRoot = new AbstractNode(Children.create(new VCCategoryChildFactory(), true));
        paletteRoot.setName("Palette Root");
        return PaletteFactory.createPalette(paletteRoot, new MyActions(), null, new NodeDnDHandler());
    }

    private static class MyActions extends PaletteActions {

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

    private static class NodeDnDHandler extends DragAndDropHandler {

        @Override
        public void customize(ExTransferable exTransferable, Lookup lookup) {
            Node node = lookup.lookup(Node.class);
            final Image image = (Image) node.getIcon(BeanInfo.ICON_COLOR_16x16);
            exTransferable.put(new ExTransferable.Single(DataFlavor.imageFlavor) {
                @Override
                protected Object getData() throws IOException, UnsupportedFlavorException {
                    return image;
                }
            });
        }
    }
}
