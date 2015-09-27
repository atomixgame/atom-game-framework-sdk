/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.spriteeditor.explorer;

import dfEditor.SpriteTree;
import dfEditor.io.SpritesheetReader;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultTreeModel;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import sg.gde.spriteeditor.io.file.sprite.SpriteFileDataObject;

/**
 *
 * @author cuong.nguyenmanh2
 */
@NavigatorPanel.Registration(mimeType = "text/x-sprite", displayName = "Sprite File")
public class SpriteEditorNavPanelImpl extends JPanel implements NavigatorPanel, LookupListener {

    JTextArea textArea;
    private SpriteTree spriteTree;

    public SpriteEditorNavPanelImpl() {
        setLayout(new BorderLayout());
//        textArea =  = new JTextArea();
        spriteTree = new SpriteTree();
        JScrollPane pane = new JScrollPane(spriteTree);
//        textArea.setEditable(false);
        add(pane, BorderLayout.CENTER);
    }
    Result<SpriteFileDataObject> dataobjectsInLookup;

    @Override
    public void panelActivated(Lookup lkp) {
        dataobjectsInLookup = lkp.lookupResult(SpriteFileDataObject.class);
        dataobjectsInLookup.addLookupListener(this);
    }

    @Override
    public void panelDeactivated() {
        dataobjectsInLookup.removeLookupListener(this);
    }

    @Override
    public void resultChanged(LookupEvent le) {
        //textArea.setText("");

        if (dataobjectsInLookup.allInstances().iterator().hasNext()) {
            SpriteFileDataObject dobj = dataobjectsInLookup.allInstances().iterator().next();
//                for (String line : dobj.getPrimaryFile().asLines()) {
//
////                    if (!line.startsWith("#") && line.length() > 0) {
////                        textArea.append(line);
////                        textArea.append("\n");
////                    }
//                    
//                    
//                }
//                textArea.setText("File: " + dobj.getPrimaryFile().getPath());

            //bindUI(dobj);
        }
    }

    public void bindUI(SpriteFileDataObject dobj) {
        //FIXME: Can this also be a Node
        SpritesheetReader reader = new SpritesheetReader(FileUtil.toFile(dobj.getPrimaryFile()));
        String imagePath = reader.getImagePath();
        DefaultTreeModel model = reader.getTreeModel();
        spriteTree.setModel(model);
        spriteTree.setEnabled(true);
    }

    @Override
    public Lookup getLookup() {
        return Lookup.EMPTY;
    }

    @Override
    public String getDisplayName() {
        return "Sprite File";
    }

    @Override
    public String getDisplayHint() {
        return "Sprite File";
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}
