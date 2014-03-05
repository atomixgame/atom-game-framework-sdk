package com.jme3.gde.preview;

import com.jme3.gde.psdhelper.PSDReader;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.loaders.DataObject;
import org.openide.util.RequestProcessor;

/**
 * Basic dummy implementation of NavigatorPanel interface.
 */
public class BasicNavPanelImpl implements NavigatorPanel {

    /**
     * holds UI of this panel
     */
    private JComponent panelUI;
    /**
     * template for finding data in given context. Object used as example,
     * replace with your own data source, for example JavaDataObject etc
     */
    private static final Lookup.Template MY_DATA = new Lookup.Template(DataObject.class);
    /**
     * current context to work on
     */
    private Lookup.Result curContext;
    /**
     * listener to context changes
     */
    private LookupListener contextL;
    private ImageDrawPanel imagePanel = new ImageDrawPanel();

    /** public no arg constructor needed for system to instantiate provider well */
    public BasicNavPanelImpl() {
    }

    public String getDisplayHint() {
        return "Basic dummy implementation of NavigatorPanel interface";
    }

    public String getDisplayName() {
        return "Dummy View";
    }

    public JComponent getComponent() {
        if (panelUI == null) {
            //panelUI = new JLabel("Do something please !");
            panelUI = new JPanel();
            panelUI.setLayout(new BorderLayout());
            panelUI.add(imagePanel, BorderLayout.CENTER);

            // You can override requestFocusInWindow() on the component if desired.
        }
        return panelUI;
    }

    public void panelActivated(Lookup context) {

        // lookup context and listen to result to get notified about context changes
        curContext = context.lookup(MY_DATA);
        curContext.addLookupListener(getContextListener());
        // get actual data and recompute content
        Collection data = curContext.allInstances();
        setNewContent(data);
    }

    public void panelDeactivated() {
        curContext.removeLookupListener(getContextListener());
        curContext = null;
    }

    public Lookup getLookup() {
        // go with default activated Node strategy
        return null;
    }
    private static final RequestProcessor RP = new RequestProcessor(BasicNavPanelImpl.class);
    String type = "";
    BufferedImage image;
    
    /************* non - public part ************/
    private void setNewContent(Collection newData) {
        // put your code here that grabs information you need from given
        // collection of data, recompute UI of your panel and show it.
        // Note - be sure to compute the content OUTSIDE event dispatch thread,
        // just final repainting of UI should be done in event dispatch thread.
        // Please use RequestProcessor and Swing.invokeLater to achieve this.

        for (Object data : newData) {

            final FileObject file = ((DataObject) data).getPrimaryFile();



            String fileExt = file.getExt();
            

            /*
             * Make reasonably sure we have an image format that AWT can
             * handle so we don't try to draw something silly.
             */
            if (fileExt != null) {
                if (fileExt.toLowerCase().endsWith("jpg")
                        || fileExt.toLowerCase().endsWith("jpeg")
                        || fileExt.toLowerCase().endsWith("gif")
                        || fileExt.toLowerCase().endsWith("png")) {
                    type = "normal";
                } else if (fileExt.toLowerCase().endsWith("psd")) {
                    type = "psd";
                }

            }

            if (file != null) {
                RP.post(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if (type.equals("normal")){
                                image = ImageIO.read(file.getInputStream());
                                System.out.println("normal case !");
                            } else if (type.equals("psd")){
                                PSDReader r = new PSDReader();
                                r.read(file.getInputStream());
                                image = r.allFrame();
                            }
                            imagePanel.setImage(image);
                        } catch (IOException ex) {
                            Logger.getLogger(BasicNavPanelImpl.class.getName()).log(Level.WARNING, null, ex);
                        }
                    }
                });


            } else {
            }

        }

    }

    /** Accessor for listener to context */
    private LookupListener getContextListener() {
        if (contextL == null) {
            contextL = new ContextListener();
        }
        return contextL;
    }

    /** Listens to changes of context and triggers proper action */
    private class ContextListener implements LookupListener {

        public void resultChanged(LookupEvent ev) {
            Collection data = ((Lookup.Result) ev.getSource()).allInstances();
            setNewContent(data);
        }
    } // end of ContextListener
}
