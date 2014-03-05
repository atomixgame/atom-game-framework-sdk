package jme3timeline.components;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import jme3timeline.TestAtomTimeLine;
import jme3timeline.components.display.misc.MatrixView;
import org.jdesktop.swingx.JXImageView;
//import processing.core.PApplet;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MainViewComponent extends JPanel {

    ImageIcon image;
    private JXImageView imageView;
    private MatrixView matrix;
    //private PApplet sketch;

    public MainViewComponent() {

        image = TestAtomTimeLine.createImageIcon("movie.png");
        setLayout(new BorderLayout());

        /*
         imageView = new JXImageView();
         imageView.setImage(image.getImage());
         imageView.setPreferredSize(new Dimension(120, 80));
         imageView.setScale(0.3);
         
         add(imageView, BorderLayout.CENTER);
         */
        //matrix = new MatrixView();
        //add(matrix, BorderLayout.CENTER);
        /*
        sketch = new ProcessingCircleSketch();
        sketch.init();
        add(sketch, BorderLayout.CENTER);
*/
        /*
        JPanel viewOptions = new JPanel();
        viewOptions.add(new JToggleButton("100%"));
        viewOptions.add(new JComboBox(new String[]{"Full", "Half"}));
        viewOptions.add(new JToggleButton("100%"));
        viewOptions.add(new JComboBox(new String[]{"Full", "Half"}));
        viewOptions.add(new JToggleButton("100%"));
        viewOptions.add(new JComboBox(new String[]{"Active Camera", "Top", "Right"}));
        viewOptions.add(new JToggleButton("100%"));
        viewOptions.add(new JComboBox(new String[]{"1 view", "2 views"}));
        viewOptions.add(new JToggleButton("100%"));
        viewOptions.add(new JXHyperlink(new AbstractAction("+0.0") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(" Do any thing");
            }
        }));


        add(viewOptions, BorderLayout.SOUTH);
*/
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
