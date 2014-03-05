package jme3timeline.components.widget;

import sg.atom.timeline.ui.AtomTimeLineViewer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ImageViewer extends JPanel implements ListSelectionListener {

    private JLabel picture;
    private JList list;
    private JSplitPane splitPane;
    private String[] imageNames = {"Red Movie", "movie"};

    public ImageViewer() {
        //Create the list of images and put it in a scroll pane.

        list = new JList(imageNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        JScrollPane listScrollPane = new JScrollPane(list);
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane pictureScrollPane = new JScrollPane(picture);

        setLayout(new BorderLayout());
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, pictureScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        add(splitPane, BorderLayout.CENTER);
//Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(100, 50);

        listScrollPane.setMinimumSize(minimumSize);
        pictureScrollPane.setMinimumSize(minimumSize);
        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
        updateLabel(imageNames[list.getSelectedIndex()]);

    }
    //Listens to the list

    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        updateLabel(imageNames[list.getSelectedIndex()]);
    }

    //Renders the selected image
    protected void updateLabel(String name) {
        ImageIcon icon = createImageIcon("../../../images/" + name + ".png");
        picture.setIcon(icon);
        if (icon != null) {
            picture.setText(null);
        } else {
            picture.setText("Image not found");
        }
    }

    //Used by SplitPaneDemo2
    public JList getImageList() {
        return list;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = AtomTimeLineViewer.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
