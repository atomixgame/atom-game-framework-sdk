package swingxTest;

/**
 *
 * @author cuong.nguyenmanh2
 */
import java.awt.Component;
import java.awt.Frame;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 *
 * @author max
 */
public class CheckBoxTest implements TableCellRenderer {

    private class CheckBoxTreeTableModel extends DefaultTreeTableModel {

        public CheckBoxTreeTableModel(TreeTableNode ttn) {
            super(ttn);
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return Boolean.class;
        }
    }

    public static void main(String args[]) {
        new CheckBoxTest();
    }

    private CheckBoxTest() {
        Frame f = new Frame();
        f.setSize(100, 100);
        TreeTableNode ttn = new DefaultMutableTreeTableNode();
//        ttn.setUserObject(Boolean.TRUE);
//        ttn.setValueAt(true, 0);
        DefaultTreeTableModel ttm = new CheckBoxTreeTableModel(ttn);
//        List columnIdentifiers = new ArrayList(1);
//        columnIdentifiers.add(Boolean.class);
//        ttm.setColumnIdentifiers(columnIdentifiers);
        JXTreeTable table = new JXTreeTable(ttm);
        //table.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, new Color(189, 189, 255), Color.WHITE));
        table.setDragEnabled(true);
//        table.createDefaultColumnsFromModel();
//        table.setDefaultRenderer(Boolean.class, new CheckBoxTest());
        table.setColumnControlVisible(true);
        table.setName("tree");
        table.setRootVisible(true);
        f.add(table);
        f.setVisible(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        JCheckBox cb = new JCheckBox();
        cb.setSelected(bln);
        return cb;
    }
}
