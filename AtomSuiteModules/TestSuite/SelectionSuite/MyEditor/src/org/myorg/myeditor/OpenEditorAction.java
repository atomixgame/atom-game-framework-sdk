package org.myorg.myeditor;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

public final class OpenEditorAction extends CallableSystemAction {

    public void performAction() {
        MyEditor editor = new MyEditor();
        editor.open();
        editor.requestActive();
    }

    public String getName() {
        return NbBundle.getMessage(OpenEditorAction.class, "CTL_OpenEditorAction");
    }

    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    protected boolean asynchronous() {
        return false;
    }
}
