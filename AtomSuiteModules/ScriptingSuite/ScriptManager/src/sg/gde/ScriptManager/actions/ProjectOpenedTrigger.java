package sg.gde.ScriptManager.actions;

import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.LookupProvider;
import org.netbeans.spi.project.ui.ProjectOpenedHook;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

@org.netbeans.spi.project.LookupProvider.Registration(projectType = "org-netbeans-modules-java-j2seproject")
public class ProjectOpenedTrigger extends ProjectOpenedHook implements LookupProvider {

    Project project;

    ProjectOpenedTrigger() {
    }

    @Override
    public Lookup createAdditionalLookup(Lookup lkp) {
        this.setProject(lkp.lookup(Project.class));
        return Lookups.fixed(this);
    }

    @Override
    protected void projectOpened() {
        InputOutput io = IOProvider.getDefault().getIO("Script Engine", true);
        io.getOut().println("Open project " + ProjectUtils.getInformation(project).getDisplayName());
    }

    @Override
    protected void projectClosed() {
        InputOutput io = IOProvider.getDefault().getIO("Script Engine", true);
        io.getOut().println("Close project " + ProjectUtils.getInformation(project).getDisplayName());
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
