/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.gde.rpg.editor.visual.dialoge;

import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class InplaceTextEditor implements TextFieldInplaceEditor {

    @Override
    public boolean isEnabled(Widget widget) {
        return true;
    }

    @Override
    public String getText(Widget widget) {
        return ((LabelWidget) widget).getLabel();
    }

    @Override
    public void setText(Widget widget, String text) {
        ((LabelWidget) widget).setLabel(text);
    }
}
