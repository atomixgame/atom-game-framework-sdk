/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.gui.file.controlfile;

import com.jme3.gde.gui.file.niftyfile.*;
import com.jme3.gde.core.assets.ProjectAssetManager;
import com.jme3.gde.gui.multiview.jme.JMEPreviewViewDesc;
import java.io.IOException;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.Node;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;

public class NiftyGuiControlDataObject extends NiftyGuiDataObject {

    private static final int TYPE_TOOLBAR = 0;
    protected final Lookup lookup;
    protected final InstanceContent lookupContents = new InstanceContent();

    public NiftyGuiControlDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        lookup = new ProxyLookup(getCookieSet().getLookup(), new AbstractLookup(getLookupContents()));
        findAssetManager();
    }

     @Override
    public Lookup getLookup() {
        return lookup;
    }

    public InstanceContent getLookupContents() {
        return lookupContents;
    }

    @Override
    protected Node createNodeDelegate() {
        DataNode node = new DataNode(this, Children.LEAF, getLookup());
        node.setIconBaseWithExtension("com/jme3/gde/gui/Computer_File_043.gif");
        return node;
    }

    @Override
    protected DesignMultiViewDesc[] getMultiViewDesc() {
        if (getLookup().lookup(ProjectAssetManager.class) == null) {
            return new DesignMultiViewDesc[]{};
        } else {

            return new DesignMultiViewDesc[]{new JMEPreviewViewDesc(this, TYPE_TOOLBAR)};
        }
    }

    @Override
    protected String getPrefixMark() {
        return "Nifty Control file";
    }
}
