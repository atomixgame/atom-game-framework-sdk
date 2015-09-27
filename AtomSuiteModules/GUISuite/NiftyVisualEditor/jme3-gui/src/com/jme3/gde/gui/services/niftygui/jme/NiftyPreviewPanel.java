/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.services.niftygui.jme;

import com.jme3.gde.gui.multiview.NativeEmbedView;
import com.jme3.audio.AudioRenderer;
import com.jme3.gde.core.assets.ProjectAssetManager;
import com.jme3.gde.core.scene.OffScenePanel;
import com.jme3.gde.core.scene.SceneApplication;
import com.jme3.gde.gui.file.niftyfile.NiftyGuiDataObject;
import com.jme3.gde.gui.services.niftygui.nodes.simplenodes.SimpleNiftyFileNode;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.xml.XMLUtil;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 *
 * @author normenhansen
 */
public class NiftyPreviewPanel extends NativeEmbedView {

    private NiftyGuiDataObject niftyObject;
    private Nifty nifty;
    private NiftyPreviewInputHandler inputHandler;
    private NiftyJmeDisplay niftyDisplay;

    public NiftyPreviewPanel(NiftyGuiDataObject niftyObject, ToolBarDesignEditor comp) {
        super();
        setRoot(Node.EMPTY);
        this.niftyObject = niftyObject;
        this.comp = comp;
        comp.setContentView(this);
        preparePreview();
        updatePreView();
    }

    public void updatePreView() {
        updatePreView(screen);
    }

    @Override
    public void initComponents() {
        super.initComponents();
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));
        createToolbar();
        scrollPanel = new JScrollPane();
        offPanel = new OffScenePanel(640, 480);
        scrollPanel.getViewport().add(offPanel);
        add(scrollPanel);
        offPanel.startPreview();
        prepareInputHandler();
    }

    @Override
    protected void viewResized(final int width, final int height) {
        super.viewResized(width, height); //To change body of generated methods, choose Tools | Templates.
        SceneApplication.getApplication().enqueue(new Callable<Object>() {
            public Object call() throws Exception {
                niftyDisplay.reshape(offPanel.getViewPort(), width, height);
                return null;
            }
        });
    }

    @Override
    protected void prepareInputHandler() {
        super.prepareInputHandler();

        inputHandler = new NiftyPreviewInputHandler();
        offPanel.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                inputHandler.addMouseEvent(e.getX(), e.getY(), e.getButton() == MouseEvent.NOBUTTON ? false : true);
            }

            public void mouseMoved(MouseEvent e) {
                inputHandler.addMouseEvent(e.getX(), e.getY(), e.getButton() == MouseEvent.NOBUTTON ? false : true);
            }
        });
        offPanel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                inputHandler.addMouseEvent(e.getX(), e.getY(), e.getButton() == MouseEvent.NOBUTTON ? false : true);
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        offPanel.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                inputHandler.addKeyEvent(e.getKeyCode(), e.getKeyChar(), true, e.isShiftDown(), e.isControlDown());
            }

            public void keyReleased(KeyEvent e) {
            }
        });
    }

    protected void parseGUIFile() {

        InputStream stream = null;
        try {
            stream = niftyObject.getPrimaryFile().getInputStream();
            doc = XMLUtil.parse(new InputSource(stream), false, false, this, null);
            SimpleNiftyFileNode rootContext = new SimpleNiftyFileNode(doc.getDocumentElement());
            setRoot(rootContext);
            comp.setRootContext(rootContext);
        } catch (Exception ex) {
            //            Message msg = new NotifyDescriptor.Message(
            //                    "Error parsing File:" + ex,
            //                    NotifyDescriptor.ERROR_MESSAGE);
            //  DialogDisplayer.getDefault().notifyLater(msg);
            Exceptions.printStackTrace(ex);
            // return;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    protected void validateGUIFile() {

        try {
            if (nifty != null) {
                nifty.validateXml(niftyObject.getPrimaryFile().getPath());
            }
        } catch (Exception e) {
            if (e instanceof SAXParseException) {
                SAXParseException spe = (SAXParseException) e;
                errors.addError("Line " + spe.getLineNumber() + " col :" + spe.getColumnNumber() + " : " + spe.getMessage());
            } else {
                errors.addError(e.getMessage());
            }
            Exceptions.printStackTrace(e);
        }
    }

    @Override
    protected ProjectAssetManager getProjectAssetManager() {
        final ProjectAssetManager pm = niftyObject.getLookup().lookup(ProjectAssetManager.class);
        if (pm == null) {
            Logger.getLogger(NiftyPreviewPanel.class.getName()).log(Level.WARNING, "No Project AssetManager found!");
        }

        return pm;
    }

    protected void heavyRefresh() {

        SceneApplication.getApplication().enqueue(new Callable<Object>() {
            public Object call() throws Exception {
                try {
                    nifty.fromXml(getProjectAssetManager().getRelativeAssetPath(niftyObject.getPrimaryFile().getPath()), screen);
                    if (screen == null || screen.length() == 0) {
                        Collection<String> screens = nifty.getAllScreensName();
                        for (Iterator<String> it = screens.iterator(); it.hasNext();) {
                            String string = it.next();
                            nifty.gotoScreen(string);
                            return null;
                        }
                    }
                } catch (Exception ex) {
                    NotifyDescriptor.Message msg = new NotifyDescriptor.Message("Error opening File:" + ex, NotifyDescriptor.ERROR_MESSAGE);
                    //  DialogDisplayer.getDefault().notifyLater(msg);
                    Exceptions.printStackTrace(ex);
                    errors.addError(ex.getMessage());
                }
                return null;
            }
        });
    }

    protected void preparePreview() {
        SceneApplication.getApplication().enqueue(new Callable<Object>() {
            public Object call() throws Exception {
                ViewPort guiViewPort = offPanel.getViewPort();
                ProjectAssetManager pm = niftyObject.getLookup().lookup(ProjectAssetManager.class);
                if (pm == null) {
                    Logger.getLogger(NiftyPreviewPanel.class.getName()).log(Level.WARNING, "No Project AssetManager found!");
                    return null;
                }
                AudioRenderer audioRenderer = SceneApplication.getApplication().getAudioRenderer();
                niftyDisplay = new NiftyJmeDisplay(pm,
                        inputHandler,
                        audioRenderer,
                        guiViewPort);
                nifty = niftyDisplay.getNifty();

                // attach the nifty display to the gui view port as a processor
                guiViewPort.addProcessor(niftyDisplay);
                return null;
            }
        });
    }

    protected void changeScreen(final String screen) {
        SceneApplication.getApplication().enqueue(new Callable<Object>() {
            public Object call() throws Exception {
                nifty.gotoScreen(screen);
                return null;
            }
        });
    }

    public void cleanup() {
        offPanel.stopPreview();
        SceneApplication.getApplication().enqueue(new Callable<Object>() {
            public Object call() throws Exception {
                ViewPort guiViewPort = offPanel.getViewPort();
                guiViewPort.removeProcessor(niftyDisplay);
                return null;
            }
        });
    }
}
