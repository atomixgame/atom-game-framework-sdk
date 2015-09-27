/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.spatial.layer;

import com.jme3.animation.Bone;
import com.jme3.math.Vector3f;
import org.netbeans.swing.outline.RowModel;

/**
 *
 * @author hungcuong
 */
public class LayerRowModel implements RowModel {

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return Vector3f.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;                
            default:
                assert false;
        }
        return null;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Pos";
            case 1:
                return "Child";
            case 2:
                return "Description";
            default:
                assert false;
        }
        return null;
    }

    @Override
    public Object getValueFor(Object node, int column) {
        Bone f = (Bone) node;
        
        if (f==null) return null;
        
        switch (column) {
            case 0:
                if (f.getLocalPosition()==null) 
                    return new Vector3f(0,0,0);
                else 
                    return new Vector3f(f.getLocalPosition());
            case 1:
                if (f.getChildren()==null) 
                    return new Integer(0);
                else 
                    return new Integer(f.getChildren().size());
            case 2:
                if (f.getName()==null) 
                    return "Null";
                else 
                    return f.getName();
            default:
                assert false;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        return false;
    }

    @Override
    public void setValueFor(Object node, int column, Object value) {
        //do nothing for now
    }
}
