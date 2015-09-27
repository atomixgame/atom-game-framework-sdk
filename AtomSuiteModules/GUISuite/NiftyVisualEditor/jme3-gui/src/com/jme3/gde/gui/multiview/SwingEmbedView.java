/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.multiview;

import com.jme3.gde.gui.base.model.GUITypes;
import com.jme3.gde.gui.nbeditor.controller.GUIEditor;
import com.jme3.gde.gui.nbeditor.controller.selection.GUISelectionListener;
import com.jme3.gde.gui.services.niftygui.events.SimpleNiftyEditorEvent;
import com.jme3.gde.gui.services.niftygui.java2d.J2DNiftyView;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

/**
 *
 * @author cuong.nguyenmanh2
 */
public abstract class SwingEmbedView extends JPanel implements Observer {

    protected final byte DIR_E = 1;
    protected final byte DIR_N = 0;
    protected final byte DIR_S = 2;
    protected final byte DIR_SE = 4;
    protected final byte DIR_W = 3;
    protected final byte NOP = -1;
    // For testing
    //private Robot mouseBot;
    protected long NORMAL_RMS = 15;
    public float ZOOM_AMOUNT = 1.2f;
    /* For viewing */
    // For drawing in Java2D canvas
    protected Canvas canvas;
    protected byte curDir;
    // For element selection
    protected boolean dragging;
    // timing
    protected long fps = 0;
    long frames = 0;
    protected Graphics2D graphics2D;
    protected GUISelectionListener guiSelectionHandler;
    protected JScrollBar jScrollBar1;
    protected JScrollBar jScrollBar2;
    //  shortcut
    protected GUIEditor manager;
    protected JPopupMenu popUp;
    // End of variables declaration
    protected Rectangle selectedRect;
    protected boolean selecting;
    BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 30, new float[]{10.0f, 8.0f}, 0);
    long time;
    // For handle transform of element
    AffineTransform transformer = new AffineTransform();
    // For viewing
    double zoomX = 1;
    double zoomY = 1;

    public SwingEmbedView() {
    }

    public void calculateFPS() {
        /*
         frames++;
         long diff = System.currentTimeMillis() - time;
         if (diff >= 1000) {
         time += diff;
         String fpsText = "Fps: " + frames;
         frames = 0;
         }
         */
    }

    public void cancelRect() {
        this.selecting = false;
    }

    public void createBuffer() {
    }

    public void createEventListeners() {
        //canvas.addMouseMotionListener(inputSystem);
        //canvas.addMouseListener(inputSystem);
        //canvas.addKeyListener(inputSystem);
        // canvas.createBufferStrategy(2);
        //  canvas.addMouseMotionListener(this);
        //  canvas.addMouseListener(this);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                //canvas.setSize(J2DNiftyView.this.getSize());
            }
        });
    }

    public void displayRect(int x, int y, int h, int w) {
        this.selectedRect.setBounds(x, y, w, h);
        this.selecting = true;
    }

    public Graphics2D getGraphics2d() {
        return this.graphics2D;
    }

    @Override
    public int getHeight() {
        return this.canvas.getHeight();
    }

    @Override
    public int getWidth() {
        return this.canvas.getWidth();
    }

    public void moveRect(int x, int y) {
        if (selectedRect != null) {
            int dx = (int) (x - this.selectedRect.getCenterX());
            int dy = (int) (y - this.selectedRect.getCenterY());
            this.selectedRect.translate(dx, dy);
        }
    }

    public void newGui(GUIEditor guiEditor) {
        this.manager = guiEditor;
        this.setClickListener(guiEditor.getGuiSelectionListener());
        this.selecting = false;
    }

    protected boolean render() {
        boolean done = false;
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        if (bufferStrategy == null) {
            // FIXME: Potentional Dangerous! Continue with thread confusion even buffer Strategy and other AWT related stuff were not completed
            return false;
        }
        graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphics2D.scale(zoomX, zoomY);
        graphics2D.setBackground(Color.BLACK);

        // Draw selecting rect
        if (selecting) {
            drawSelectingRect();
        }
        graphics2D.setTransform(transformer);
        bufferStrategy.show();
        graphics2D.dispose();
        graphics2D = null;
        return done;
    }

    protected boolean nativeRender() {
        return false;
    }

    protected void drawSelectingRect() {
        graphics2D.drawLine((int) selectedRect.getCenterX() - 10, (int) selectedRect.getCenterY(), (int) selectedRect.getCenterX() + 10, (int) selectedRect.getCenterY());
        graphics2D.drawLine((int) selectedRect.getCenterX(), (int) selectedRect.getCenterY() - 10, (int) selectedRect.getCenterX(), (int) selectedRect.getCenterY() + 10);
        graphics2D.setColor(Color.red);
        graphics2D.setStroke(stroke);
        graphics2D.draw(selectedRect);
        graphics2D.setColor(Color.black);
        graphics2D.setStroke(new BasicStroke());
        graphics2D.drawRect((int) selectedRect.getMaxX() - 6, (int) selectedRect.getMaxY() - 6, 11, 11);

    }

    protected void setClickListener(GUISelectionListener guiSelectionListener) {
        //this.canvas.removeMouseListener(guiSelectionHandler);
        //this.canvas.removeMouseMotionListener(guiSelectionHandler);
        this.canvas.addMouseListener(guiSelectionListener);
        this.canvas.addMouseMotionListener(guiSelectionListener);
        this.canvas.addKeyListener(guiSelectionListener);
        guiSelectionHandler = guiSelectionListener;
    }

    public void setManager(GUIEditor manager) {
        this.manager = manager;

    }

    public void setViewPort(Rectangle viewRect) {
    }

    public void start() {
        boolean done = false;
        time = System.currentTimeMillis();
        // this.canvas.setSize((int)(this.getHeight()*1), (int)(this.getWidth()*1));
        /*
         Font fpsFont = new Font("arial", Font.BOLD, 14);
         String fps = "Fps: 0";
         */
        // FIXME: Bind this NiftyView as an obverser of GUIEditor
        this.manager.addObserver(this);
        while (!done) {
            try {
                done = render();
                try {
                    Thread.sleep(NORMAL_RMS);
                } catch (InterruptedException ex) {
                    Logger.getLogger(J2DNiftyView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        SimpleNiftyEditorEvent act = (SimpleNiftyEditorEvent) arg;
        if (act.getType() == SimpleNiftyEditorEvent.SEL && !act.getGUIElement().getType().equals(GUITypes.LAYER)) {
            this.selectedRect.setBounds(act.getGUIElement().getBounds());
            this.selecting = true;
            Logger.getLogger(J2DNiftyView.class.getName()).log(Level.INFO, "Get notified by selection");
        } else if (act.getType() == SimpleNiftyEditorEvent.NEW) {
            this.newGui((GUIEditor) o);
            o.addObserver(this.guiSelectionHandler);
        } else {
            this.selecting = false;
        }
    }

    public void zoomIn() {
        zoomX *= ZOOM_AMOUNT;
        zoomY *= ZOOM_AMOUNT;
    }

    public void zoomIn(float amount) {
        zoomX *= amount;
        zoomY *= amount;
    }

    public void zoomOut() {
        zoomX /= ZOOM_AMOUNT;
        zoomY /= ZOOM_AMOUNT;
    }

    public void zoomOut(float amount) {
        zoomX *= amount;
        zoomY *= amount;
    }

    public void zoomReset() {
        zoomX = 1;
        zoomY = 1;
    }
}
