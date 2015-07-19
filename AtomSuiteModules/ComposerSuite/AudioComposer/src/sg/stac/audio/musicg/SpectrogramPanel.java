/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.stac.audio.musicg;

import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SpectrogramPanel extends JPanel {

    SwingMusicCGRenderer render;
    BufferedImage image;

    public SpectrogramPanel(String fileName) {
        // create a wave object
        Wave wave = new Wave(fileName);
        Spectrogram spectrogram = new Spectrogram(wave);

        // Graphic render
        render = new SwingMusicCGRenderer();
        // change the spectrogram representation
        int fftSampleSize = 512;
        int overlapFactor = 2;
        spectrogram = new Spectrogram(wave, fftSampleSize, overlapFactor);
        image = render.renderSpectrogram(spectrogram);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null); // see javadoc for more info on the parameters            
    }
}
