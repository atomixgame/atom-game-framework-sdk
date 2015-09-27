package sg.gde.atomcore.project.properties;

import java.util.ResourceBundle;
import javax.swing.JComponent;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

public class AssetsPropertiesPanelProvider implements ProjectCustomizer.CompositeCategoryProvider {

    @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-java-j2seproject", category="BuildCategory", position = 90)
    public static AssetsPropertiesPanelProvider createAssetsPanel() {
        return new AssetsPropertiesPanelProvider();
    }
    private Project project;

    private AssetsPropertiesPanelProvider() {
    }

    @Override
    public Category createCategory(Lookup lkp) {
        ResourceBundle bundle = NbBundle.getBundle(AssetsPropertiesPanelProvider.class);
        ProjectCustomizer.Category toReturn = null;
        project = lkp.lookup(Project.class);
        if (project == null) {
            return toReturn;
        }
        toReturn = ProjectCustomizer.Category.create(
                "assets",
                bundle.getString("LBL_Config_assets"),
                null);
        return toReturn;
    }

    @Override
    public JComponent createComponent(Category category, Lookup lkp) {
        String nm = category.getName();
        AssetsPropertiesPanel panel = new AssetsPropertiesPanel(project);
        category.setStoreListener(panel);
        return panel;
    }
}
