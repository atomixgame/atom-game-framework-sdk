package jme3timeline.components.renderer;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ScaleRenderer extends JLabel implements TableCellRenderer {
    // This method is called each time a column header
    // using this renderer needs to be rendered.

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
        // 'value' is column header value of column 'vColIndex'
        // rowIndex is always -1
        // isSelected is always false
        // hasFocus is always false

        // Configure the component with the specified value
        setText(value.toString());

        // Set tool tip if desired
        setToolTipText((String) value);

        // Since the renderer is a component, return itself
        return this;
    }
    private final double TICK_DIST = 20;

    void drawRuler(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double len = Math.sqrt(dx * dx + dy * dy);

        /*
         AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
         at.concatenate(AffineTransform.getRotateInstance(Math.atan2(dy, dx)));
         g.transform(at);
         */
        // Draw horizontal ruler starting in (0, 0)
        g.drawLine(0, 0, (int) len, 0);
        for (double i = 0; i < len; i += TICK_DIST) {
            g.drawLine((int) i, -3, (int) i, 3);
        }
    }

    public void paintComponent(Graphics g) {
        drawRuler(g, 10, 30, 300, 150);
        //drawRuler(g, 300, 150, 100, 100);
        //drawRuler(g, 100, 100, 120, 350);
        //drawRuler(g, 50, 350, 350, 50);
    }
    // The following methods override the defaults for performance reasons

    @Override
    public void validate() {
    }

    @Override
    public void revalidate() {
    }

    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }
}