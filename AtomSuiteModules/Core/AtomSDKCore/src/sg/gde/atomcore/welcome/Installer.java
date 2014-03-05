package sg.gde.atomcore.welcome;

import org.openide.modules.ModuleInstall;
import org.openide.windows.WindowManager;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        WindowManager.getDefault().invokeWhenUIReady(new Runnable(){

            public void run() {
                WelcomeScreenTopComponent.checkOpen();
            }
        });
    }
}
