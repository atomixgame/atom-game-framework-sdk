package sg.stac.lipsync;

// FacePanel.java
// Display face for current phoneme
//
// (c) 2005 David Cuny
// http://jlipsync.sourceforge.net
// Released under Qt Public License
import sg.stac.utils.Utilities;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FacePanel
        extends JLabel {

    // list of phonemes
    Object phonemes[] = {"<none>"};
    // face images
    ImageIcon shapes[] = new ImageIcon[30];
    final static int shape_rest = 0,
            shape_a = 1,
            shape_b = 2,
            shape_c = 3,
            shape_ch = 4,
            shape_d = 5,
            shape_e = 6,
            shape_f = 7,
            shape_g = 8,
            shape_i = 9,
            shape_j = 10,
            shape_k = 11,
            shape_l = 12,
            shape_m = 13,
            shape_n = 14,
            shape_o = 15,
            shape_p = 16,
            shape_q = 17,
            shape_r = 18,
            shape_s = 19,
            shape_sh = 20,
            shape_t = 21,
            shape_th = 22,
            shape_u = 23,
            shape_v = 24,
            shape_w = 25,
            shape_y = 26,
            shape_z = 27;

    public FacePanel(String set) {
        useSet(set);
    }

    public static String convertPhoneme(String s) {
        // digit part of phoneme?
        if (s.length() > 2) {
            // drop the digit
            s = s.substring(1, 2);
        }

        // try to convert the phoneme
        if (s.equals("AA")) {
            return "O";	// odd
        }
        if (s.equals("AE")) {
            return "A"; // ate
        }
        if (s.equals("AH")) {
            return "O"; // hut
        }
        if (s.equals("AW")) {
            return "A"; // cow
        }
        if (s.equals("AI")) {
            return "I"; // hide
        }
        if (s.equals("B")) {
            return "B";  // be
        }
        if (s.equals("CH")) {
            return "CH"; // cheese
        }
        if (s.equals("D")) {
            return "D"; // d
        }
        if (s.equals("DH")) {
            return "TH"; // thee
        }
        if (s.equals("EH")) {
            return "E"; // Ed
        }
        if (s.equals("ER")) {
            return "E"; // hurt
        }
        if (s.equals("EY")) {
            return "A"; // ate
        }
        if (s.equals("F")) {
            return "F"; // fee
        }
        if (s.equals("G")) {
            return "G"; // green
        }
        if (s.equals("HH")) {
            return "A"; // he
        }
        if (s.equals("H")) {
            return "A"; // he
        }
        if (s.equals("IH")) {
            return "I"; // it
        }
        if (s.equals("IY")) {
            return "E"; // eat
        }
        if (s.equals("JH")) {
            return "J"; // gee
        }
        if (s.equals("K")) {
            return "K"; // key
        }
        if (s.equals("L")) {
            return "L"; // lee
        }
        if (s.equals("M")) {
            return "M"; // me
        }
        if (s.equals("N")) {
            return "N"; // knee
        }
        if (s.equals("NG")) {
            return "G"; // ping
        }
        if (s.equals("OW")) {
            return "O"; // oat
        }
        if (s.equals("OY")) {
            return "O"; // toy
        }
        if (s.equals("P")) {
            return "P"; // pee
        }
        if (s.equals("R")) {
            return "R"; // read
        }
        if (s.equals("S")) {
            return "S"; // sea
        }
        if (s.equals("SH")) {
            return "SH"; // she
        }
        if (s.equals("T")) {
            return "T"; // tea
        }
        if (s.equals("TH")) {
            return "TH"; // theta
        }
        if (s.equals("UH")) {
            return "O"; // hood
        }
        if (s.equals("UW")) {
            return "U"; // two
        }
        if (s.equals("V")) {
            return "V"; // vee
        }
        if (s.equals("W")) {
            return "W"; // we
        }
        if (s.equals("Y")) {
            return "E"; // yield
        }
        if (s.equals("Z")) {
            return "Z"; // zee
        }
        if (s.equals("ZH")) {
            return "SH"; // seizure
        }
        return s;
    }

    public void useSet(String faces) {
        // load the graphics

        // clear the poses
        shapes = new ImageIcon[30];

        // a, i
        shapes[shape_a] = Utilities.createImageIcon("mouths/ai.png");
        shapes[shape_i] = shapes[shape_a];

        // c, e, g, k, n, r, s, t
        shapes[shape_c] = Utilities.createImageIcon("mouths/cdgk.png");
        shapes[shape_e] = shapes[shape_c];
        shapes[shape_g] = shapes[shape_c];
        shapes[shape_j] = shapes[shape_c];
        shapes[shape_k] = shapes[shape_c];
        shapes[shape_n] = shapes[shape_c];
        shapes[shape_r] = shapes[shape_c];
        shapes[shape_s] = shapes[shape_c];
        shapes[shape_t] = shapes[shape_c];
        shapes[shape_y] = shapes[shape_c];
        shapes[shape_ch] = shapes[shape_c];

        // closed
        shapes[shape_rest] = Utilities.createImageIcon("mouths/closed.png");

        // d
        shapes[shape_d] = Utilities.createImageIcon("mouths/d.png");

        // e, z
        shapes[shape_e] = Utilities.createImageIcon("mouths/e.png");
        shapes[shape_z] = Utilities.createImageIcon("mouths/e.png");

        // f, v
        shapes[shape_f] = Utilities.createImageIcon("mouths/fv.png");
        shapes[shape_v] = shapes[shape_f];

        // l
        shapes[shape_l] = Utilities.createImageIcon("mouths/l.png");
        shapes[shape_th] = Utilities.createImageIcon("mouths/th.png");

        // m, b, p
        shapes[shape_m] = Utilities.createImageIcon("mouths/mbp.png");
        shapes[shape_b] = shapes[shape_m];
        shapes[shape_p] = shapes[shape_m];

        // o
        shapes[shape_o] = Utilities.createImageIcon("mouths/o.png");

        // u
        shapes[shape_u] = Utilities.createImageIcon("mouths/u.png");

        // q, w
        shapes[shape_w] = Utilities.createImageIcon("mouths/wq.png");
        shapes[shape_q] = shapes[shape_w];


        // create a border
        setBorder(BorderFactory.createLineBorder(Color.black));

        // set to empty face
        setFace("Closed");


    }

    public static int getFaceIndex(String s) {

        int faceIndex = -1;

        if (s.equals("<none>")) {
            faceIndex = shape_rest;
        } else if (s.equals("Closed")) {
            faceIndex = shape_rest;
        } else if (s.equals("A")) {
            faceIndex = shape_a;
        } else if (s.equals("B")) {
            faceIndex = shape_b;
        } else if (s.equals("C")) {
            faceIndex = shape_c;
        } else if (s.equals("CH")) {
            faceIndex = shape_ch;
        } else if (s.equals("D")) {
            faceIndex = shape_d;
        } else if (s.equals("E")) {
            faceIndex = shape_e;
        } else if (s.equals("F")) {
            faceIndex = shape_f;
        } else if (s.equals("G")) {
            faceIndex = shape_g;
        } else if (s.equals("I")) {
            faceIndex = shape_i;
        } else if (s.equals("J")) {
            faceIndex = shape_j;
        } else if (s.equals("K")) {
            faceIndex = shape_k;
        } else if (s.equals("L")) {
            faceIndex = shape_l;
        } else if (s.equals("M")) {
            faceIndex = shape_m;
        } else if (s.equals("N")) {
            faceIndex = shape_n;
        } else if (s.equals("O")) {
            faceIndex = shape_o;
        } else if (s.equals("P")) {
            faceIndex = shape_p;
        } else if (s.equals("Q")) {
            faceIndex = shape_q;
        } else if (s.equals("R")) {
            faceIndex = shape_r;
        } else if (s.equals("S")) {
            faceIndex = shape_s;
        } else if (s.equals("SH")) {
            faceIndex = shape_sh;
        } else if (s.equals("T")) {
            faceIndex = shape_t;
        } else if (s.equals("TH")) {
            faceIndex = shape_th;
        } else if (s.equals("U")) {
            faceIndex = shape_u;
        } else if (s.equals("V")) {
            faceIndex = shape_v;
        } else if (s.equals("W")) {
            faceIndex = shape_w;
        } else if (s.equals("Y")) {
            faceIndex = shape_y;
        } else if (s.equals("Z")) {
            faceIndex = shape_z;
        }

        return faceIndex;
    }

    // get the icon for a face
    public ImageIcon getFace(String s) {
        int faceIndex = getFaceIndex(s);

        // return a face
        if (faceIndex == -1) {
            return null;
        } else {
            return shapes[faceIndex];
        }

    }

    // set the proper face
    public void setFace(int index) {
        // set icon
        if (index != -1) {
            // get the icon
            ImageIcon icon = shapes[index];
            // set it
            setIcon(icon);
            // force a repaint
            repaint();
        }
    }

    // set the proper face
    public void setFace(String s) {
        // set icon
        ImageIcon icon = getFace(s);
        if (icon != null) {
            setIcon(icon);
            repaint();
        }
    }
}
