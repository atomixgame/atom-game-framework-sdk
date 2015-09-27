package org.netbeans.gradle.project.properties;

import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

@OptionsPanelController.SubRegistration(
        location = "Advanced",
        displayName = "#AdvancedOption_DisplayName_Gradle",
        keywords = "#AdvancedOption_Keywords_Gradle",
        keywordsCategory = "Advanced/Gradle")
public final class GradleOptionsPanelController extends OptionsPanelController {
    private GradleSettingsPanel settingsPanel;

    private GradleSettingsPanel getPanel() {
        if (settingsPanel == null) {
            settingsPanel = new GradleSettingsPanel();
        }
        return settingsPanel;
    }

    @Override
    public void update() {
        getPanel().updateSettings();
    }

    @Override
    public void applyChanges() {
        getPanel().saveSettings();
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isChanged() {
        return true;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
    }
}
