package org.netbeans.gradle.project.api.entry;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.Sources;
import org.netbeans.gradle.project.NbGradleProject;
import org.netbeans.gradle.project.ProjectInfoManager;
import org.netbeans.gradle.project.api.property.GradleProperty;
import org.netbeans.gradle.project.api.task.GradleCommandExecutor;
import org.netbeans.gradle.project.java.nodes.JavaDependenciesNode;
import org.netbeans.gradle.project.java.nodes.JavaExtensionNodes;
import org.netbeans.gradle.project.java.nodes.JavaProjectContextActions;
import org.netbeans.gradle.project.java.query.GradleClassPathProvider;
import org.netbeans.gradle.project.java.tasks.GradleJavaBuiltInCommands;
import org.netbeans.gradle.project.properties.GlobalGradleSettings;
import org.netbeans.gradle.project.view.BuildScriptsNode;
import org.netbeans.junit.MockServices;
import org.netbeans.spi.java.classpath.ClassPathProvider;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.AuxiliaryConfiguration;
import org.netbeans.spi.project.AuxiliaryProperties;
import org.netbeans.spi.project.ProjectConfigurationProvider;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.CustomizerProvider;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.PrivilegedTemplates;
import org.netbeans.spi.project.ui.RecommendedTemplates;
import org.netbeans.spi.queries.SharabilityQueryImplementation2;
import org.openide.filesystems.FileObject;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

import static org.junit.Assert.*;
import static org.netbeans.spi.project.ActionProvider.*;

/**
 *
 * @author radim
 */
public class SimpleJavaProjectTest {
    private static SampleGradleProject sampleProject;
    private NbGradleProject rootProject;

    @BeforeClass
    public static void setUpClass() throws Exception {
        MockServices.setServices();

        GlobalGradleSettings.setCleanMemoryPreference();
        GlobalGradleSettings.getGradleHome().setValue(SampleGradleProject.DEFAULT_GRADLE_TARGET);
        GlobalGradleSettings.getGradleJdk().setValue(JavaPlatform.getDefault());

        sampleProject = SampleGradleProject.createProject("gradle-sample.zip");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        GlobalGradleSettings.setDefaultPreference();
        SampleGradleProject toClose = sampleProject;
        sampleProject = null;

        if (toClose != null) {
            toClose.close();
        }
    }

    @Before
    public void setUp() throws Exception {
        Thread.interrupted();
        rootProject = sampleProject.loadProject("gradle-sample");

        GradleTestExtension ext = rootProject.getLookup().lookup(GradleTestExtension.class);
        assertNotNull(ext);

        if (!rootProject.tryWaitForLoadedProject(3, TimeUnit.MINUTES)) {
            throw new TimeoutException("Project was not loaded until the timeout elapsed.");
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testProjectServiceProvider() throws Exception {
        Project project = rootProject;

        MyCustomLookupEntry entry = project.getLookup().lookup(MyCustomLookupEntry.class);
        assertNotNull("Lookup must contain entry: MyCustomLookupEntry", entry);
        assertEquals("Must be registered with the currect project.",
                project.getProjectDirectory(),
                entry.getProject().getProjectDirectory());
    }

    @Test
    public void testClassPath() throws Exception {
        NbGradleProject project = rootProject;

        FileObject foProjectSrc = project.getProjectDirectory().getFileObject("src/main/java/org/netbeans/gradle/Sample.java");

        // get the classpath
        verifyClasspath(project, foProjectSrc, ClassPath.SOURCE, "gradle-sample/src/main/java");
        // need to add some test here
        // verifyClasspath(prj, foProjectSrc, ClassPath.BOOT, "android.jar", "annotations.jar");
    }

    private static String[] getSingleCommands() {
        return new String[] {
            COMMAND_RUN_SINGLE,
            COMMAND_DEBUG_SINGLE,
            COMMAND_TEST_SINGLE,
            COMMAND_DEBUG_TEST_SINGLE,
        };
    }

    @Test
    public void testSingleCommandsEnabledForJava() throws Exception {
        NbGradleProject project = rootProject;

        ActionProvider actionProvider = project.getLookup().lookup(ActionProvider.class);
        Set<String> supportedActions = new HashSet<>(Arrays.asList(actionProvider.getSupportedActions()));

        FileObject javaFile = project.getProjectDirectory().getFileObject(
                "src/main/java/org/netbeans/gradle/Sample.java");
        for (String command: getSingleCommands()) {
            Lookup lookup = Lookups.fixed(javaFile);
            boolean actionEnabled = actionProvider.isActionEnabled(command, lookup);
            assertTrue(actionEnabled);
            assertTrue(supportedActions.contains(command));
        }
    }

    private Set<String> getRootsOfClassPath(ClassPath classpath) {
        Set<String> roots = new HashSet<>();
        for (FileObject cpEntry: classpath.getRoots()) {
            roots.add(cpEntry.getPath());
        }
        return roots;
    }

    private void verifyClasspath(Project prj, FileObject fo, String cpType, String... entries) {
        ClassPathProvider cpp = prj.getLookup().lookup(ClassPathProvider.class);
        ClassPath classpath = cpp.findClassPath(fo, cpType);
        assertNotNull("classpath " + cpType + " found", classpath);

        Set<String> cpRoots = getRootsOfClassPath(classpath);
        for (final String entry: entries) {
            assertTrue(
                    "classpath " + classpath + " contains entry " + entry,
                    Iterables.any(cpRoots, new Predicate<String>() {
                @Override
                public boolean apply(String t) {
                    return t.endsWith(entry);
                }
            }));
        }
    }

    private static void checkNotOnLookup(Lookup lookup, Class<?> type) {
        Collection<?> objects = lookup.lookupAll(type);
        if (!objects.isEmpty()) {
            fail("Lookup must not contain an instance of " + type.getName());
        }
    }

    private static void checkExactlyOnce(Lookup lookup, Class<?> type) {
        Collection<?> objects = lookup.lookupAll(type);
        if (objects.size() != 1) {
            fail("Lookup must contain exactly one entry of " + type.getName() + " instead of " + objects.size() + " times.");
        }
    }

    @Test
    public void testQueriesNotIncludedMultipleTimes() {
        Lookup lookup = rootProject.getLookup();

        checkExactlyOnce(lookup, LogicalViewProvider.class);
        checkExactlyOnce(lookup, ProjectInformation.class);
        checkExactlyOnce(lookup, ActionProvider.class);
        checkExactlyOnce(lookup, SharabilityQueryImplementation2.class);
        checkExactlyOnce(lookup, CustomizerProvider.class);
        checkExactlyOnce(lookup, ProjectConfigurationProvider.class);
        checkExactlyOnce(lookup, ProjectState.class);
        checkExactlyOnce(lookup, AuxiliaryConfiguration.class);
        checkExactlyOnce(lookup, AuxiliaryProperties.class);
        checkExactlyOnce(lookup, GradleCommandExecutor.class);
        checkExactlyOnce(lookup, GradleProperty.SourceEncoding.class);
        checkExactlyOnce(lookup, GradleProperty.ScriptPlatform.class);
        checkExactlyOnce(lookup, GradleProperty.SourceLevel.class);
        checkExactlyOnce(lookup, GradleProperty.BuildPlatform.class);
        checkExactlyOnce(lookup, ProjectInfoManager.class);
        checkExactlyOnce(lookup, Sources.class);
        checkExactlyOnce(lookup, GradleClassPathProvider.class);
        checkExactlyOnce(lookup, PrivilegedTemplates.class);
        checkExactlyOnce(lookup, RecommendedTemplates.class);
        checkExactlyOnce(lookup, NbGradleProject.class);
    }

    @Test
    public void testExtensionQueriesAreNotOnLookup() {
        Lookup lookup = rootProject.getLookup();

        checkNotOnLookup(lookup, JavaExtensionNodes.class);
        checkNotOnLookup(lookup, JavaProjectContextActions.class);
        checkNotOnLookup(lookup, GradleJavaBuiltInCommands.class);
    }

    @Test
    public void testHasProperNodes() throws Exception {
        final NbGradleProject project = rootProject;

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                LogicalViewProvider view = project.getLookup().lookup(LogicalViewProvider.class);
                Node root = view.createLogicalView();

                Lookup children = Lookups.fixed((Object[])root.getChildren().getNodes());
                JavaDependenciesNode dependenciesNode = children.lookup(JavaDependenciesNode.class);
                BuildScriptsNode buildScriptsNode = children.lookup(BuildScriptsNode.class);

                assertNotNull("Must have a dependencies node", dependenciesNode);
                assertNotNull("Must have a build scripts node", buildScriptsNode);
            }
        });
    }

    private static void verifyJavaDocActionIsAdded(Action[] actions) {
        for (Action action: actions) {
            if (action == null) continue;

            Object name = action.getValue("Name");
            if (name == null) continue;

            if ("projectCommand:javadoc".equals(name.toString())) {
                return;
            }
        }

        fail("Could not find javadoc command.");
    }

    @Test
    public void testJavadocActionIsAdded() throws Exception {
        final NbGradleProject project = rootProject;

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                LogicalViewProvider view = project.getLookup().lookup(LogicalViewProvider.class);
                Node root = view.createLogicalView();

                verifyJavaDocActionIsAdded(root.getActions(false));
                verifyJavaDocActionIsAdded(root.getActions(true));
            }
        });
    }
}