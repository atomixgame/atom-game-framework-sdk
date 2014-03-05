package org.myorg.myeditor;

import java.beans.PropertyEditorManager;
import java.util.Date;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    public void restored() {
        PropertyEditorManager.registerEditor(Date.class, DatePropertyEditor.class);
    }
}
