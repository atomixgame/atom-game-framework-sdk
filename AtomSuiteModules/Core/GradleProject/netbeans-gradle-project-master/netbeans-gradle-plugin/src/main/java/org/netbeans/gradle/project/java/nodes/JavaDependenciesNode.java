package org.netbeans.gradle.project.java.nodes;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jtrim.concurrent.TaskExecutor;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.api.project.Project;
import org.netbeans.gradle.model.java.JavaClassPaths;
import org.netbeans.gradle.model.java.JavaSourceSet;
import org.netbeans.gradle.model.util.CollectionUtils;
import org.netbeans.gradle.project.NbGradleProject;
import org.netbeans.gradle.project.NbIcons;
import org.netbeans.gradle.project.NbStrings;
import org.netbeans.gradle.project.NbTaskExecutors;
import org.netbeans.gradle.project.api.nodes.SingleNodeFactory;
import org.netbeans.gradle.project.api.task.CommandCompleteListener;
import org.netbeans.gradle.project.java.JavaExtension;
import org.netbeans.gradle.project.java.model.JavaProjectDependency;
import org.netbeans.gradle.project.java.model.NbJavaModel;
import org.netbeans.gradle.project.java.model.NbJavaModule;
import org.netbeans.gradle.project.tasks.DaemonTaskDef;
import org.netbeans.gradle.project.tasks.DownloadSourcesTask;
import org.netbeans.gradle.project.tasks.GradleDaemonManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

public final class JavaDependenciesNode extends AbstractNode {
    private static final Logger LOGGER = Logger.getLogger(JavaDependenciesNode.class.getName());
    private static final Collator STR_CMP = Collator.getInstance();

    private static final TaskExecutor SOURCES_DOWNLOADER
            = NbTaskExecutors.newExecutor("Sources-downloader", 1);

    private final JavaExtension javaExt;

    public JavaDependenciesNode(JavaExtension javaExt) {
        this(new DependenciesChildFactory(javaExt), javaExt);
    }

    private JavaDependenciesNode(DependenciesChildFactory childFactory, JavaExtension javaExt) {
        super(createChildren(childFactory));

        this.javaExt = javaExt;
    }

    private static Children createChildren(DependenciesChildFactory childFactory) {
        return Children.create(childFactory, true);
    }

    @Override
    public Image getIcon(int type) {
        return NbIcons.getLibrariesIcon();
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

    @Override
    public String getDisplayName() {
        return NbStrings.getDependenciesNodeCaption();
    }

    @Override
    public Action[] getActions(boolean context) {
        NbGradleProject project = javaExt.getProject().getLookup().lookup(NbGradleProject.class);
        return new Action[]{
            new DownloadSourcesAction(project)
        };
    }

    private static class DependenciesChildFactory
    extends
            ChildFactory.Detachable<SingleNodeFactory>
    implements
            ChangeListener {

        private final JavaExtension javaExt;
        private final AtomicReference<NbJavaModule> lastModule;

        public DependenciesChildFactory(JavaExtension javaExt) {
            ExceptionHelper.checkNotNullArgument(javaExt, "javaExt");

            this.javaExt = javaExt;
            this.lastModule = new AtomicReference<>(null);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            NbJavaModule newModule = javaExt.getCurrentModel().getMainModule();
            NbJavaModule prevModule = lastModule.getAndSet(newModule);

            if (hasRelevantDifferences(newModule, prevModule)) {
                refresh(false);
            }
        }

        private static boolean hasRelevantDifferences(NbJavaModule module1, NbJavaModule module2) {
            if (module1 == module2) {
                // In practice this happens only when they are nulls.
                return false;
            }
            if (module1 == null || module2 == null) {
                return true;
            }

            if (module1.getSources().size() != module2.getSources().size()) {
                return true;
            }

            Map<String, JavaSourceSet> sources2 = getSourceSetMap(module2);
            for (JavaSourceSet sourceSet1: module1.getSources()) {
                JavaSourceSet sourceSet2 = sources2.get(sourceSet1.getName());
                if (sourceSet2 == null) {
                    return true;
                }

                if (!isClassPathSame(sourceSet1, sourceSet2)) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isClassPathSame(JavaSourceSet sourceSet1, JavaSourceSet sourceSet2) {
            JavaClassPaths classpaths1 = sourceSet1.getClasspaths();
            JavaClassPaths classpaths2 = sourceSet2.getClasspaths();

            if (!classpaths1.getCompileClasspaths().equals(classpaths2.getCompileClasspaths())) {
                return false;
            }

            return classpaths1.getRuntimeClasspaths().equals(classpaths2.getRuntimeClasspaths());
        }

        private static Map<String, JavaSourceSet> getSourceSetMap(NbJavaModule module) {
            Map<String, JavaSourceSet> result = CollectionUtils.newHashMap(module.getSources().size());
            for (JavaSourceSet sourceSet: module.getSources()) {
                result.put(sourceSet.getName(), sourceSet);
            }
            return result;
        }

        @Override
        protected void addNotify() {
            lastModule.set(javaExt.getCurrentModel().getMainModule());
            javaExt.addModelChangeListener(this);
        }

        @Override
        protected void removeNotify() {
            javaExt.removeModelChangeListener(this);
        }

        private void addDependencyGroup(
                final String groupName,
                final Collection<? extends SingleNodeFactory> dependencies,
                List<SingleNodeFactory> toPopulate) {

            if (dependencies.isEmpty()) {
                return;
            }

            toPopulate.add(new SingleNodeFactory() {
                @Override
                public Node createNode() {
                    return new AbstractNode(Children.create(new DependencyGroupChildFactory(dependencies), true)) {
                        @Override
                        public Image getIcon(int type) {
                            return NbIcons.getLibrariesIcon();
                        }

                        @Override
                        public Image getOpenedIcon(int type) {
                            return getIcon(type);
                        }

                        @Override
                        public String getDisplayName() {
                            return groupName;
                        }
                    };
                }
            });
        }

        private static List<SingleNodeFactory> filesToNodes(NbJavaModel currentModel, Collection<File> files) {
            List<SingleNodeFactory> result = new ArrayList<>(files.size());
            for (File file: files) {
                JavaProjectDependency projectDep = currentModel.tryGetDepedency(file);
                if (projectDep == null) {
                    result.add(new FileDependency(file));
                }
                else {
                    result.add(new ProjectDependencyFactory(projectDep));
                }
            }
            return result;
        }

        private static String listToString(Collection<?> list) {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Object element: list) {
                if (first) {
                    first = false;
                }
                else {
                    result.append(", ");
                }
                result.append(element != null ? element.toString() : "null");
            }
            return result.toString();
        }

        private static String getBaseDependencyGroupName(
                DependencyType dependencyType,
                JavaSourceSet sourceSet) {

            String sourceSetName = sourceSet.getName();

            switch (dependencyType) {
                case COMPILE:
                    return NbStrings.getCompileForSourceSet(sourceSetName);
                case RUNTIME:
                    return NbStrings.getRuntimeForSourceSet(sourceSetName);
                case PROVIDED:
                    return NbStrings.getProvidedForSourceSet(sourceSetName);
                default:
                    throw new AssertionError(dependencyType.name());
            }
        }

        private String getNameForDependencyGroup(
                DependencyType dependencyType,
                JavaSourceSet sourceSet,
                Map<String, Set<String>> sourceSetDependencyGraph) {

            String baseName = getBaseDependencyGroupName(dependencyType, sourceSet);
            Set<String> dependencies = sourceSetDependencyGraph.get(sourceSet.getName());
            if (dependencies == null || dependencies.isEmpty()) {
                return baseName;
            }
            else {
                return NbStrings.getSourceSetInherits(baseName, listToString(dependencies));
            }
        }

        private static int compareProjectDependencyNodes(ProjectDependencyFactory node1, ProjectDependencyFactory node2) {
            NbJavaModule module1 = node1.projectDep.tryGetModule();
            NbJavaModule module2 = node2.projectDep.tryGetModule();

            if (module1 == module2) {
                return 0;
            }

            if (module1 == null) {
                return 1;
            }
            if (module2 == null) {
                return -1;
            }

            return STR_CMP.compare(module1.getShortName(), module2.getShortName());
        }

        private static int compareFileDependencyNodes(FileDependency node1, FileDependency node2) {
            String name1 = node1.file.getName();
            String name2 = node2.file.getName();

            return STR_CMP.compare(name1, name2);
        }

        private static int compareDependencyNodes(SingleNodeFactory node1, SingleNodeFactory node2) {
            if (node1 instanceof ProjectDependencyFactory) {
                if (node2 instanceof ProjectDependencyFactory) {
                    return compareProjectDependencyNodes(
                            (ProjectDependencyFactory)node1,
                            (ProjectDependencyFactory)node2);
                }
                else {
                    return -1;
                }
            }
            else if (node2 instanceof ProjectDependencyFactory) {
                return 1;
            }
            else if (node1 instanceof FileDependency && node2 instanceof FileDependency) {
                return compareFileDependencyNodes((FileDependency)node1, (FileDependency)node2);
            }
            else {
                return 0;
            }
        }

        private static List<SingleNodeFactory> sortDependencyNodes(List<SingleNodeFactory> nodes) {
            SingleNodeFactory[] nodesArray = nodes.toArray(new SingleNodeFactory[nodes.size()]);

            Arrays.sort(nodesArray, new Comparator<SingleNodeFactory>() {
                @Override
                public int compare(SingleNodeFactory o1, SingleNodeFactory o2) {
                    return compareDependencyNodes(o1, o2);
                }
            });

            return Arrays.asList(nodesArray);
        }

        private void addSourceSetDependencyNodes(
                NbJavaModel currentModel,
                String nodeGroupName,
                Collection<String> sourceSetDependencies,
                Set<File> classpaths, // IN/OUT
                List<SingleNodeFactory> toPopulate) {

            NbJavaModule mainModule = currentModel.getMainModule();
            classpaths.removeAll(mainModule.getAllBuildOutputs());

            for (String inheritedName: sourceSetDependencies) {
                JavaSourceSet inherited = mainModule.tryGetSourceSetByName(inheritedName);
                if (inherited != null) {
                    classpaths.removeAll(inherited.getClasspaths().getCompileClasspaths());
                    classpaths.removeAll(inherited.getClasspaths().getRuntimeClasspaths());
                    classpaths.remove(inherited.getOutputDirs().getClassesDir());
                    classpaths.remove(inherited.getOutputDirs().getResourcesDir());
                    classpaths.removeAll(inherited.getOutputDirs().getOtherDirs());
                }
            }

            List<SingleNodeFactory> dependencyNodes = filesToNodes(currentModel, classpaths);
            dependencyNodes = sortDependencyNodes(dependencyNodes);

            addDependencyGroup(nodeGroupName, dependencyNodes, toPopulate);
        }

        private static <T> Set<T> splitSets(Set<T> set1, Set<T> set2) {
            Set<T> smallerSet;
            Set<T> largerSet;

            if (set1.size() < set2.size()) {
                smallerSet = set1;
                largerSet = set2;
            }
            else {
                smallerSet = set2;
                largerSet = set1;
            }

            Set<T> intersect = new HashSet<>();
            for (T element: smallerSet) {
                if (largerSet.remove(element)) {
                    intersect.add(element);
                }
            }
            smallerSet.removeAll(intersect);

            return intersect;
        }

        private static Map<String, Set<String>> sourceSetDependencyGraph(
                NbJavaModule mainModule) {

            Map<File, String> buildOutput = new HashMap<>();
            for (JavaSourceSet sourceSet: mainModule.getSources()) {
                buildOutput.put(sourceSet.getOutputDirs().getClassesDir(), sourceSet.getName());
            }

            Map<String, Set<String>> result = new HashMap<>();
            for (JavaSourceSet sourceSet: mainModule.getSources()) {
                String sourceSetName = sourceSet.getName();

                Set<File> runtimeClasspaths = sourceSet.getClasspaths().getRuntimeClasspaths();

                Set<String> dependencies = new HashSet<>();
                for (Map.Entry<File, String> entry: buildOutput.entrySet()) {
                    if (runtimeClasspaths.contains(entry.getKey())) {
                        String dependencyName = entry.getValue();
                        if (!sourceSetName.equals(dependencyName)) {
                            dependencies.add(entry.getValue());
                        }
                    }
                }

                result.put(sourceSetName, dependencies);
            }

            // TODO: Remove redundant edges

            return result;
        }

        private void readKeys(List<SingleNodeFactory> toPopulate) throws DataObjectNotFoundException {
            NbJavaModel currentModel = javaExt.getCurrentModel();
            NbJavaModule mainModule = currentModel.getMainModule();

            Map<String, Set<String>> dependencyGraph = sourceSetDependencyGraph(mainModule);

            for (JavaSourceSet sourceSet: mainModule.getSources()) {
                JavaClassPaths classpaths = sourceSet.getClasspaths();

                Set<File> providedClassPaths = new HashSet<>(classpaths.getCompileClasspaths());
                Set<File> runtimeClassPaths = new HashSet<>(classpaths.getRuntimeClasspaths());
                Set<File> compileClassPaths = splitSets(providedClassPaths, runtimeClassPaths);

                Set<String> sourceDependencies = dependencyGraph.get(sourceSet.getName());
                if (sourceDependencies == null) {
                    sourceDependencies = Collections.emptySet();
                }

                addSourceSetDependencyNodes(
                        currentModel,
                        getNameForDependencyGroup(DependencyType.COMPILE, sourceSet, dependencyGraph),
                        sourceDependencies,
                        compileClassPaths,
                        toPopulate);

                addSourceSetDependencyNodes(
                        currentModel,
                        getNameForDependencyGroup(DependencyType.PROVIDED, sourceSet, dependencyGraph),
                        sourceDependencies,
                        providedClassPaths,
                        toPopulate);

                addSourceSetDependencyNodes(
                        currentModel,
                        getNameForDependencyGroup(DependencyType.RUNTIME, sourceSet, dependencyGraph),
                        sourceDependencies,
                        runtimeClassPaths,
                        toPopulate);
            }

            LOGGER.fine("Dependencies for the Gradle project were found.");
        }

        @Override
        protected boolean createKeys(List<SingleNodeFactory> toPopulate) {
            try {
                readKeys(toPopulate);
            } catch (DataObjectNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(SingleNodeFactory key) {
            return key.createNode();
        }
    }

    private static class DependencyGroupChildFactory extends ChildFactory<SingleNodeFactory> {
        private final List<SingleNodeFactory> dependencies;

        public DependencyGroupChildFactory(Collection<? extends SingleNodeFactory> dependencies) {
            this.dependencies = new ArrayList<>(dependencies);
        }

        protected void readKeys(List<SingleNodeFactory> toPopulate) throws DataObjectNotFoundException {
            toPopulate.addAll(dependencies);
        }

        @Override
        protected boolean createKeys(List<SingleNodeFactory> toPopulate) {
            try {
                readKeys(toPopulate);
            } catch (DataObjectNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(SingleNodeFactory key) {
            return key.createNode();
        }
    }

    private static final class FileDependency implements SingleNodeFactory {
        private final File file;

        public FileDependency(File file) {
            assert file != null;

            this.file = file;
        }

        private Node createPlainNode() {
            return new FilterNode(Node.EMPTY) {
                @Override
                public String getDisplayName() {
                    return file.getName();
                }

                @Override
                public Image getIcon(int type) {
                    return NbIcons.getFolderIcon();
                }

                @Override
                public Image getOpenedIcon(int type) {
                    return getIcon(type);
                }
            };
        }

        @Override
        public Node createNode() {
            File normalizedFile = FileUtil.normalizeFile(file);
            FileObject fileObj = normalizedFile != null ? FileUtil.toFileObject(normalizedFile) : null;
            if (fileObj == null) {
                LOGGER.log(Level.WARNING, "Dependency is not available: {0}", file);
                return createPlainNode();
            }

            final DataObject dataObj;
            try {
                dataObj = DataObject.find(fileObj);
            } catch (DataObjectNotFoundException ex) {
                LOGGER.log(Level.INFO, "Unexpected DataObjectNotFoundException for file: " + file, ex);
                return createPlainNode();
            }
            return dataObj.getNodeDelegate().cloneNode();
        }
    }

    private static final class ProjectDependencyFactory implements SingleNodeFactory {
        private final JavaProjectDependency projectDep;

        public ProjectDependencyFactory(JavaProjectDependency projectDep) {
            this.projectDep = projectDep;
        }

        private DataObject getProjectDirObj() {
            Project project = projectDep.getProjectReference().tryGetProject();
            if (project == null) {
                return null;
            }
            try {
                return DataObject.find(project.getProjectDirectory());
            } catch (DataObjectNotFoundException ex) {
                LOGGER.log(Level.INFO, "Failed to find node for project directory: " + project.getProjectDirectory(), ex);
                return null;
            }
        }

        @Override
        public Node createNode() {
            DataObject fileObject = getProjectDirObj();

            Node baseNode = fileObject != null
                    ? fileObject.getNodeDelegate()
                    : Node.EMPTY;

            // TODO: Update the created node if the underlying project is reloaded.
            return new FilterNode(baseNode.cloneNode()) {
                @Override
                public Image getIcon(int type) {
                    return NbIcons.getGradleIcon();
                }

                @Override
                public Image getOpenedIcon(int type) {
                    return getIcon(type);
                }

                @Override
                public String getDisplayName() {
                    NbJavaModule module = projectDep.tryGetModule();
                    return module != null
                            ? module.getShortName()
                            : super.getDisplayName();
                }
            };
        }
    }

    private enum DependencyType {
        COMPILE,
        RUNTIME,
        PROVIDED
    }

    @SuppressWarnings("serial")
    private static final class DownloadSourcesAction extends AbstractAction {
        private final NbGradleProject project;

        public DownloadSourcesAction(NbGradleProject project) {
            super(NbStrings.getDownloadSources());
            this.project = project;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DaemonTaskDef taskDef = DownloadSourcesTask.createTaskDef(project);
            GradleDaemonManager.submitGradleTask(SOURCES_DOWNLOADER, taskDef, new CommandCompleteListener() {
                @Override
                public void onComplete(Throwable error) {
                    if (error != null) {
                        project.displayError(NbStrings.getDownloadSourcesFailure(), error);
                    }
                }
            });
        }
    }
}
