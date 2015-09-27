package org.netbeans.gradle.project.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gradle.tooling.ModelBuilder;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.UnknownModelException;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.GradleProject;
import org.gradle.tooling.model.GradleTask;
import org.gradle.tooling.model.idea.IdeaModule;
import org.gradle.tooling.model.idea.IdeaProject;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.gradle.model.GenericProjectProperties;
import org.netbeans.gradle.model.GradleTaskID;
import org.netbeans.gradle.model.OperationInitializer;
import org.netbeans.gradle.model.util.CollectionUtils;
import org.netbeans.gradle.project.NbGradleExtensionRef;
import org.netbeans.gradle.project.NbGradleProject;
import org.netbeans.gradle.project.NbStrings;
import org.netbeans.gradle.project.api.entry.ModelLoadResult;
import org.netbeans.gradle.project.api.entry.ParsedModel;
import org.netbeans.gradle.project.api.modelquery.GradleModelDefQuery1;
import org.netbeans.gradle.project.api.modelquery.GradleTarget;
import org.netbeans.gradle.project.java.model.idea.IdeaJavaModelUtils;
import org.openide.util.lookup.Lookups;

public final class NbCompatibleModelLoader implements NbModelLoader {
    private static final Logger LOGGER = Logger.getLogger(NbCompatibleModelLoader.class.getName());

    private final NbGradleModel baseModels;
    private final OperationInitializer setup;
    private final GradleTarget gradleTarget;

    public NbCompatibleModelLoader(
            NbGradleModel baseModels,
            OperationInitializer setup,
            GradleTarget gradleTarget) {
        ExceptionHelper.checkNotNullArgument(setup, "setup");
        ExceptionHelper.checkNotNullArgument(gradleTarget, "gradleTarget");

        this.gradleTarget = gradleTarget;
        this.baseModels = baseModels;
        this.setup = setup;
    }

    @Override
    public Result loadModels(
            NbGradleProject project,
            ProjectConnection connection,
            ProgressHandle progress) throws IOException {

        List<NbGradleModel.Builder> otherModels = new LinkedList<>();

        NbGradleModel.Builder mainModel;
        if (baseModels == null) {
            mainModel = loadMainModel(project, connection, otherModels);
        }
        else {
            mainModel = new NbGradleModel.Builder(baseModels);
        }

        Map<File, NbGradleModel.Builder> otherModelsMap = CollectionUtils.newHashMap(otherModels.size());
        for (NbGradleModel.Builder model: otherModels) {
            otherModelsMap.put(model.getProjectDir(), model);
        }
        otherModelsMap.remove(mainModel.getProjectDir());

        getExtensionModels(project, connection, progress, mainModel, otherModelsMap);

        return new Result(mainModel.create(), NbGradleModel.createAll(otherModels));
    }

    private <T> T getModelWithProgress(
            ProjectConnection projectConnection,
            Class<T> model) {
        ModelBuilder<T> builder = projectConnection.model(model);
        GradleModelLoader.setupLongRunningOP(setup, builder);

        return builder.get();
    }

    private void getExtensionModels(
            NbGradleProject project,
            ProjectConnection projectConnection,
            ProgressHandle progress,
            NbGradleModel.Builder mainModel,
            Map<File, NbGradleModel.Builder> otherModels) {

        Map<Class<?>, Object> found = new HashMap<>();

        NbGradleModel initialMainModel = mainModel.create();
        for (NbGradleExtensionRef extensionRef: GradleModelLoader.getUnloadedExtensions(project, initialMainModel)) {
            List<Object> extensionModels = new LinkedList<>();

            GradleModelDefQuery1 query1 = extensionRef.getModelNeeds().getQuery1();
            for (Class<?> modelClass: query1.getToolingModels(gradleTarget)) {
                try {
                    Object model = found.get(modelClass);
                    if (model == null) {
                        progress.progress(NbStrings.getFetchingToolingModel(modelClass));
                        model = getModelWithProgress(projectConnection, modelClass);
                    }

                    found.put(modelClass, model);
                    extensionModels.add(model);
                } catch (UnknownModelException ex) {
                    Throwable loggedException = LOGGER.isLoggable(Level.FINE)
                            ? ex
                            : null;
                    LOGGER.log(Level.INFO, "Cannot find model " + modelClass.getName(), loggedException);
                }
            }

            progress.progress(NbStrings.getParsingModel());

            ModelLoadResult modelLoadResult = new ModelLoadResult(
                    project.getProjectDirectoryAsFile(),
                    Lookups.fixed(extensionModels.toArray()));

            ParsedModel<?> parsedModel = extensionRef.parseModel(modelLoadResult);
            mainModel.setModelForExtension(extensionRef, parsedModel.getMainModel());

            for (Map.Entry<File, ?> otherEntry: parsedModel.getOtherProjectsModel().entrySet()) {
                NbGradleModel.Builder otherBuilder = otherModels.get(otherEntry.getKey());
                if (otherBuilder != null) {
                    otherBuilder.setModelForExtension(extensionRef, otherEntry.getValue());
                }
            }
        }
    }

    private static List<GradleTaskID> getTasksOfModule(IdeaModule module) {
        DomainObjectSet<? extends GradleTask> modelTasks = module.getGradleProject().getTasks();
        List<GradleTaskID> result = new ArrayList<>(modelTasks.size());

        for (GradleTask modelTask: modelTasks) {
            result.add(new GradleTaskID(modelTask.getName(), modelTask.getPath()));
        }
        return result;
    }

    private static NbGradleProjectTree tryCreateProjectTreeFromIdea(IdeaModule module) {
        File moduleDir = IdeaJavaModelUtils.tryGetModuleDir(module);
        if (moduleDir == null) {
            return null;
        }

        int expectedChildCount = module.getGradleProject().getChildren().size();
        List<NbGradleProjectTree> children = new ArrayList<>(expectedChildCount);
        for (IdeaModule child: getChildModules(module)) {
            NbGradleProjectTree childInfo = tryCreateProjectTreeFromIdea(child);
            if (childInfo != null) {
                children.add(childInfo);
            }
        }

        GradleProject gradleProject = module.getGradleProject();
        String projectName = gradleProject.getName();
        String projectFullName = gradleProject.getPath();
        GenericProjectProperties properties
                = new GenericProjectProperties(projectName, projectFullName, moduleDir);

        return new NbGradleProjectTree(properties, getTasksOfModule(module), children);
    }

    private static List<IdeaModule> getChildModules(IdeaModule module) {
        Collection<? extends GradleProject> children = module.getGradleProject().getChildren();
        Set<String> childrenPaths = CollectionUtils.newHashSet(children.size());
        for (GradleProject child: children) {
            childrenPaths.add(child.getPath());
        }

        List<IdeaModule> result = new LinkedList<>();
        for (IdeaModule candidateChild: module.getProject().getModules()) {
            if (childrenPaths.contains(candidateChild.getGradleProject().getPath())) {
                result.add(candidateChild);
            }
        }
        return result;
    }

    private static NbGradleModel loadMainModelFromIdeaModule(
            NbGradleProjectTree rootProject,
            IdeaModule ideaModule) throws IOException {
        ExceptionHelper.checkNotNullArgument(rootProject, "rootProject");
        ExceptionHelper.checkNotNullArgument(ideaModule, "ideaModule");

        NbGradleProjectTree projectTree = tryCreateProjectTreeFromIdea(ideaModule);
        if (projectTree == null) {
            throw new IOException("Failed to create project tree for project: " + ideaModule.getName());
        }

        return new NbGradleModel(new NbGradleMultiProjectDef(rootProject, projectTree));
    }

    private NbGradleModel.Builder loadMainModel(
            NbGradleProject project,
            ProjectConnection projectConnection,
            List<NbGradleModel.Builder> otherModels) throws IOException {

        IdeaProject ideaProject
                = getModelWithProgress(projectConnection, IdeaProject.class);

        return parseMainModel(project, ideaProject, otherModels);
    }

    private static NbGradleModel.Builder toBuilder(NbGradleMultiProjectDef projectDef) {
        return new NbGradleModel.Builder(new NbGenericModelInfo(projectDef));
    }

    private static NbGradleModel.Builder toBuilder(NbGradleModel model) {
        return new NbGradleModel.Builder(model);
    }

    private static NbGradleModel.Builder parseMainModel(
            NbGradleProject project,
            IdeaProject ideaProject,
            List<NbGradleModel.Builder> otherModels) throws IOException {
        ExceptionHelper.checkNotNullArgument(project, "project");
        ExceptionHelper.checkNotNullArgument(ideaProject, "ideaProject");
        ExceptionHelper.checkNotNullArgument(otherModels, "otherModels");

        File projectDir = project.getProjectDirectoryAsFile();
        IdeaModule mainModule = IdeaJavaModelUtils.tryFindMainModule(projectDir, ideaProject);
        if (mainModule == null) {
            throw new IOException("Failed to find idea module for project: " + project.getDisplayName());
        }

        IdeaModule rootModule = tryFindRootModule(ideaProject);
        if (rootModule == null) {
            throw new IOException("Failed to find root module for project: " + project.getDisplayName());
        }

        NbGradleProjectTree rootTree = tryCreateProjectTreeFromIdea(rootModule);
        if (rootTree == null) {
            throw new IOException("Failed to find root tree for project: " + rootModule.getName());
        }

        String rootPath = rootModule.getGradleProject().getPath();
        for (IdeaModule otherModule: ideaProject.getModules()) {
            // This comparison is not strictly necessary but there is no reason
            // to reparse the main project.
            if (otherModule != mainModule) {
                if (rootPath.equals(otherModule.getGradleProject().getPath())) {
                    otherModels.add(toBuilder(new NbGradleMultiProjectDef(rootTree, rootTree)));
                }
                else {
                    otherModels.add(toBuilder(loadMainModelFromIdeaModule(rootTree, otherModule)));
                }
            }
        }

        NbGradleProjectTree mainTree;
        if (rootPath.equals(mainModule.getGradleProject().getPath())) {
            mainTree = rootTree;
        }
        else {
            mainTree = tryCreateProjectTreeFromIdea(mainModule);
        }

        if (mainTree == null) {
            throw new IOException("Failed to find tree for project: " + mainModule.getName());
        }

        return toBuilder(new NbGradleMultiProjectDef(rootTree, mainTree));
    }

    private static IdeaModule tryFindRootModule(IdeaProject ideaModel) {
        DomainObjectSet<? extends IdeaModule> modules = ideaModel.getModules();
        if (modules.isEmpty()) {
            return null;
        }

        GradleProject rootProject = getRoot(modules.iterator().next().getGradleProject());
        String rootName = rootProject.getPath();

        for (IdeaModule module: ideaModel.getModules()) {
            if (rootName.equals(module.getGradleProject().getPath())) {
                return module;
            }
        }
        return null;
    }

    private static GradleProject getRoot(GradleProject project) {
        GradleProject prev = null;
        GradleProject current = project;
        do {
            prev = current;
            current = current.getParent();
        } while (current != null);
        return prev;
    }
}
