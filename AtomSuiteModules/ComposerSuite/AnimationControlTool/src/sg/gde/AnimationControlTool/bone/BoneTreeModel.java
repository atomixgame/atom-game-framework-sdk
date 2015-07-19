/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.AnimationControlTool.bone;

import com.jme3.animation.AnimControl;
import com.jme3.animation.Bone;
import java.util.Arrays;
import javax.swing.tree.TreeModel;

/**
 *
 * @author hungcuong
 */
public class BoneTreeModel implements TreeModel {

    private Bone root;

    public BoneTreeModel() {
        this.root = new Bone("Null Bone");
    }

    public BoneTreeModel(Bone rootBone) {
        if (rootBone != null) {
            this.root = rootBone;
        } else {
            this.root = new Bone("Null Bone");
        }
    }

    public void setRoot(Bone rootBone) {
        this.root = rootBone;
    }

    @Override
    public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
        //do nothing
    }

    @Override
    public Object getChild(Object parent, int index) {
        Bone f = (Bone) parent;

        if (f.getChildren() == null) {
            return null;
        }
        return f.getChildren().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        Bone f = (Bone) parent;

        if (f.getChildren() == null) {
            return 0;
        }
        return f.getChildren().size();

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        Bone par = (Bone) parent;
        Bone ch = (Bone) child;

        if (par.getChildren() == null) {
            return 0;
        }
        return par.getChildren().indexOf(ch);
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        Bone f = (Bone) node;
        if (f.getChildren() == null) {
            return true;
        }
        return f.getChildren().isEmpty();
    }

    @Override
    public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
        //do nothing
    }

    @Override
    public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue) {
        //do nothing
    }
}
