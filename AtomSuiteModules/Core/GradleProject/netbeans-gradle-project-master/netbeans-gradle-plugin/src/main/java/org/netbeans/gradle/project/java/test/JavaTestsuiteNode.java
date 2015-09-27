package org.netbeans.gradle.project.java.test;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import org.jtrim.cancel.Cancellation;
import org.jtrim.cancel.CancellationToken;
import org.jtrim.concurrent.CancelableTask;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.api.project.Project;
import org.netbeans.gradle.model.java.JavaSourceGroup;
import org.netbeans.gradle.model.java.JavaSourceSet;
import org.netbeans.gradle.project.NbStrings;
import org.netbeans.gradle.project.java.JavaExtension;
import org.netbeans.gradle.project.output.OpenEditorOutputListener;
import org.netbeans.gradle.project.util.StringUtils;
import org.netbeans.gradle.project.view.GradleActionProvider;
import org.netbeans.modules.gsf.testrunner.api.TestsuiteNode;
import org.netbeans.spi.project.ActionProvider;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public final class JavaTestsuiteNode extends TestsuiteNode {
    private static final String[] EXTENSIONS = {".java", ".groovy", ".scala"};

    private final JavaExtension javaExt;
    private final TestTaskName testTaskName;

    public JavaTestsuiteNode(
            String suiteName,
            boolean filtered,
            JavaExtension javaExt,
            TestTaskName testTaskName) {

        super(suiteName, filtered);

        ExceptionHelper.checkNotNullArgument(javaExt, "javaExt");
        ExceptionHelper.checkNotNullArgument(testTaskName, "testTaskName");

        this.javaExt = javaExt;
        this.testTaskName = testTaskName;
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[] {
            getJumpToSourcesAction(),
            getRerunTestAction(),
        };
    }

    @Override
    public Action getPreferredAction() {
        return getJumpToSourcesAction();
    }

    private String getTestClassName() {
        return StringUtils.getTopMostClassName(suiteName);
    }

    private static FileObject tryGetTestFile(File root, String relPath) {
        FileObject rootObj = FileUtil.toFileObject(root);
        if (rootObj == null) {
            return null;
        }

        for (String extension: EXTENSIONS) {
            FileObject sourceFile = rootObj.getFileObject(relPath + extension);
            if (sourceFile != null) {
                return sourceFile;
            }
        }
        return null;
    }

    private static FileObject tryGetTestFile(JavaSourceGroup sourceGroup, String relPath) {
        for (File root: sourceGroup.getSourceRoots()) {
            FileObject result = tryGetTestFile(root, relPath);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static FileObject tryGetTestFile(JavaSourceSet sourceSet, String relPath) {
        for (JavaSourceGroup sourceGroup: sourceSet.getSourceGroups()) {
            FileObject result = tryGetTestFile(sourceGroup, relPath);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public static FileObject tryGetTestFile(JavaExtension javaExt, String testClassName) {
        // Note that we always need '/' for FileObject (no matter the file system).
        String relPath = testClassName.replace('.', '/');

        for (JavaSourceSet sourceSet: javaExt.getCurrentModel().getMainModule().getSources()) {
            FileObject result = tryGetTestFile(sourceSet, relPath);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private FileObject tryGetTestFile() {
        return tryGetTestFile(javaExt, getTestClassName());
    }

    private Action getJumpToSourcesAction() {
        return new JumpToSourcesAction();
    }

    private Action getRerunTestAction() {
        return new RerunTestAction();
    }

    private void jumpToSourcesNow() {
        final FileObject testFile = tryGetTestFile();
        if (testFile == null) {
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OpenEditorOutputListener.tryOpenFile(testFile, -1);
            }
        });
    }

    @SuppressWarnings("serial")
    private class JumpToSourcesAction extends AbstractAction {
        public JumpToSourcesAction() {
            super(NbStrings.getJumpToSource());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ShowTestUtils.FILE_OPEN_PROCESSOR.execute(Cancellation.UNCANCELABLE_TOKEN, new CancelableTask() {
                @Override
                public void execute(CancellationToken cancelToken) {
                    jumpToSourcesNow();
                }
            }, null);
        }
    }

    @SuppressWarnings("serial")
    private class RerunTestAction extends AbstractAction {
        public RerunTestAction() {
            super(NbStrings.getTestClassAgain());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Project project = javaExt.getProject();
            Lookup context = Lookups.fixed(testTaskName, new SpecificTestClass(suiteName));

            GradleActionProvider.invokeAction(project, ActionProvider.COMMAND_TEST_SINGLE, context);
        }
    }
}
