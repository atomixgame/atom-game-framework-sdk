package jme3timeline.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.TreeNode;
import jme3timeline.TestAtomTimeLine;
import org.jdesktop.swingx.JXImageView;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ProjectComponent extends JPanel {
    
    String[] titles = {"Comps", "Solids", "Images", "Sounds"};
    private DefaultTreeTableModel model;
    private JXImageView imageView;
    
    public ProjectComponent() {
        setLayout(new BorderLayout());
        JPanel previewPanel = new JPanel();
        previewPanel.setLayout(new BorderLayout());
        
        imageView = new JXImageView();
        imageView.setImage(TestAtomTimeLine.createImageIcon("Red Movie.png").getImage());
        imageView.setPreferredSize(new Dimension(120, 80));
        imageView.setScale(0.3);
        
        previewPanel.add(imageView, BorderLayout.WEST);
        previewPanel.add(new JLabel("3D Twist & Color Character"), BorderLayout.CENTER);
        add(previewPanel, BorderLayout.NORTH);
        add(new JXTreeTable(createTree()), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        
        ImageIcon imgCopy = TestAtomTimeLine.createImageIcon("retina/mimi/Copy16.png");
        JButton btnCopy = new JButton(imgCopy);
        buttonPanel.add(btnCopy);
        
        ImageIcon imgCut = TestAtomTimeLine.createImageIcon("retina/mimi/Cut16.png");
        JButton btnCut = new JButton(imgCut);
        buttonPanel.add(btnCut);
        
        ImageIcon imgDelete = TestAtomTimeLine.createImageIcon("retina/mimi/Delete16.png");
        JButton btnDelete = new JButton(imgDelete);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    DefaultTreeTableModel createTree() {
        DefaultMutableTreeTableNode rootNode = new DefaultMutableTreeTableNode("Project");
        model = new DefaultTreeTableModel(rootNode);
        for (String title : titles) {
            rootNode.add(new DefaultMutableTreeTableNode(title));
        }
        return model;
    }
}
