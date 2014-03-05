package com.jme3.gde.gui.palette.visual;

import com.jme3.gde.gui.nbeditor.model.Types;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

public class NiftyElementChildFactory extends ChildFactory<NiftyElementIcon> {

    private final NiftyElementCategory category;

    NiftyElementChildFactory(NiftyElementCategory category) {
        this.category = category;
    }

    @Override
    protected boolean createKeys(List<NiftyElementIcon> list) {
        /*
         list.add(new NiftyElementIcon(0, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/images/window.png"));
         list.add(new NiftyElementIcon(1, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/images/panel.png"));
         list.add(new NiftyElementIcon(2, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/images/screen.png"));
         */
        //FIXME: Create the real key with real icons
        list.add(new NiftyElementIcon(3, category.getName(), Types.WINDOW, "com/jme3/gde/gui/resources/icons/components/window.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.SCREEN, "com/jme3/gde/gui/resources/icons/components/screen.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.LAYER, "com/jme3/gde/gui/resources/icons/components/layer.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/icons/components/layout.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.PANEL, "com/jme3/gde/gui/resources/icons/components/panel.png"));

        list.add(new NiftyElementIcon(3, category.getName(), Types.IMAGE, "com/jme3/gde/gui/resources/icons/components/image.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.LABEL, "com/jme3/gde/gui/resources/icons/components/text.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/icons/components/scroller.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.LISTBOX, "com/jme3/gde/gui/resources/icons/components/list.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.TEXTFIELD, "com/jme3/gde/gui/resources/icons/components/textArea.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/icons/components/table.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/icons/components/control.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/icons/components/style.png"));
        list.add(new NiftyElementIcon(3, category.getName(), Types.BUTTON, "com/jme3/gde/gui/resources/icons/components/button.png"));
        return true;
    }

    @Override
    protected Node createNodeForKey(NiftyElementIcon key) {
        return new NiftyElementPaletteNode(key);
    }
}