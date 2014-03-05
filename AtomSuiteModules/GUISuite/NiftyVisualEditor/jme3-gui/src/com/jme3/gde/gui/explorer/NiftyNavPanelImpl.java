package com.jme3.gde.gui.explorer;

import com.jme3.gde.gui.nbeditor.nodes.NiftyDocNode;
import com.jme3.gde.gui.file.niftyfile.NiftyGuiDataObject;
import java.awt.BorderLayout;
import java.util.Collection;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.w3c.dom.Document;

/**
 * Basic dummy implementation of NavigatorPanel interface.
 */
public class NiftyNavPanelImpl extends JPanel implements NavigatorPanel, ExplorerManager.Provider {

    /**
     * holds UI of this panel
     */
    private JComponent panelUI;
    /**
     * template for finding data in given context. Object used as example,
     * replace with your own data source, for example JavaDataObject etc
     */
    //private static final Lookup.Template MY_DATA = new Lookup.Template(NiftyGuiDataObject.class);
    private static final Lookup.Template MY_DATA = new Lookup.Template(NiftyDocNode.class);
    /**
     * current context to work on
     */
    private Lookup.Result curContext;
    /**
     * listener to context changes
     */
    private LookupListener contextL;
    private ExplorerManager manager;
    private BeanTreeView treeView;
    private Lookup lookup;
    private NiftyGuiDataObject niftyObject;
    //private Nifty nifty;
    JToolBar toolbar;
    private Document doc;
    private NiftyDocNode rootContext;

    /**
     * public no arg constructor needed for system to instantiate provider well
     */
    public NiftyNavPanelImpl() {
        manager = new ExplorerManager();
        ActionMap map = getActionMap();

        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(manager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(manager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(manager));
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false

        lookup = ExplorerUtils.createLookup(manager, map);

        treeView = new BeanTreeView();
        setLayout(new BorderLayout());
        add(treeView, BorderLayout.CENTER);
        //Logger.getLogger(this.getClass().getName()).info("Nav NiftyNavPanelImpl");
    }

    public String getDisplayHint() {
        return " Nifty Navigator Panel ";
    }

    public String getDisplayName() {
        return "Nifty Outline View";
    }

    public JComponent getComponent() {
        if (panelUI == null) {
            //panelUI = new JLabel("Hello World");
            panelUI = this;
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

    public void loadNodes(NiftyGuiDataObject niftyObject) {
        manager.setRootContext(niftyObject.getGUIEditor().getRootContext());
    }

    public void loadNodes(NiftyDocNode niftyObject) {
        manager.setRootContext(niftyObject);
    }

    public void panelDeactivated() {
        curContext.removeLookupListener(getContextListener());
        curContext = null;
    }

    public Lookup getLookup() {
        // go with default activated Node strategy
        return lookup;
    }

    /**
     * *********** non - public part ***********
     */
    private void setNewContent(final Collection newData) {
        // put your code here that grabs information you need from given
        // collection of data, recompute UI of your panel and show it.
        // Note - be sure to compute the content OUTSIDE event dispatch thread,
        // just final repainting of UI should be done in event dispatch thread.
        // Please use RequestProcessor and Swing.invokeLater to achieve this.
        if (!newData.isEmpty()) {
            //niftyObject = (NiftyGuiDataObject) newData.iterator().next();
            rootContext = (NiftyDocNode) newData.iterator().next();
            Logger.getLogger(this.getClass().getName()).info("Nav panelActivated");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    loadNodes(rootContext);
                }
            });

        } else {
            Logger.getLogger(this.getClass().getName()).info("Nav Data is empty!");
        }


    }

    /**
     * Accessor for listener to context
     */
    private LookupListener getContextListener() {
        if (contextL == null) {
            contextL = new ContextListener();
        }
        return contextL;
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }

    /**
     * Listens to changes of context and triggers proper action
     */
    private class ContextListener implements LookupListener {

        public void resultChanged(LookupEvent ev) {
            Collection data = ((Lookup.Result) ev.getSource()).allInstances();
            setNewContent(data);
        }
    } // end of ContextListener
}