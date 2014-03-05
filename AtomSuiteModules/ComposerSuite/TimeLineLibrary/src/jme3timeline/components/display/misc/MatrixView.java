package jme3timeline.components.display.misc;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MatrixView extends JPanel {

    int i;
    private MCode nu[] = new MCode[80];//("0", 50,50,  100  );

    public MatrixView() {
        initMatrix();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintMatrix(g);
    }

    //private int dly = 0;
    public void initMatrix() {
        ///init

        setBackground(Color.black);

        //Timer clock = new Timer(1, this);
        //clock.start();

        int i;
        for (i = 0; i < nu.length; i++) {
            /*if (i == 0)
             nu[i] = new Mcode("0", 50 + i * 10, 50, 50);
             else if (i == 1)
             nu[i] = new Mcode("0", 50 + i * 10, 50, 20);
             else if (i == 2)
             nu[i] = new Mcode("0", 50 + i * 10, 50, 150);
             else if (i == 3)
             nu[i] = new Mcode("0", 50 + i * 10, 50, 100);
             else if (i == 4)
             nu[i] = new Mcode("0", 50 + i * 10, 50, 10);
             else */
            nu[i] = new MCode("0", i * 10, 10, ((int) (Math.random() * Math.random() * 100)) + 20);

        }

    }

    public void paintMatrix(Graphics g) {

        /* private Mcode bu = new Mcode("0", 60,50,  20  );
         private Mcode qu = new Mcode("0", 70,50,  50  );
         private Mcode wu = new Mcode("0", 80,50,  70  );
         private Mcode eu = new Mcode("0", 90,50, 200  );
         private Mcode ru = new Mcode("0", 100,50, 10  );
         */

        for (i = 0; i < nu.length; i++) {
            if (nu[i].delay < nu[i].dly) {
                nu[i].paint(g);
                nu[i].dly = 0;
            } else {
                nu[i].dly += 10;
            }
        }

        repaint();
        /*
         try {
         Thread.sleep(10);
         } catch (InterruptedException e) {
         }
         */
    }
}
