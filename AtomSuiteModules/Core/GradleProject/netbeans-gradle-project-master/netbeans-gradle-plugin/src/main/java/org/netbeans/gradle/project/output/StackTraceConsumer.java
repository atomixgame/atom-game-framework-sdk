package org.netbeans.gradle.project.output;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.queries.SourceForBinaryQuery;
import org.netbeans.api.project.Project;
import org.netbeans.gradle.project.java.query.GradleClassPathProvider;
import org.netbeans.spi.java.queries.SourceForBinaryQueryImplementation;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.URLMapper;
import org.openide.windows.OutputListener;
import org.openide.windows.OutputWriter;

public final class StackTraceConsumer implements SmartOutputHandler.Consumer {
    private static final Logger LOGGER = Logger.getLogger(StackTraceConsumer.class.getName());

    private static final Pattern LINE_PATTERN = Pattern.compile("(?:\\[catch\\])?\\sat (.*)\\((.*)\\.java\\:(\\d+)\\)");

    private final Project project;
    private final ClassPath classPath;

    public StackTraceConsumer(Project project) {
        ExceptionHelper.checkNotNullArgument(project, "project");

        this.project = project;
        this.classPath = getClassPathFromProject(project);
    }

    private static ClassPath getClassPathFromProject(Project project) {
        GradleClassPathProvider classPaths = project.getLookup().lookup(GradleClassPathProvider.class);
        if (classPaths == null) {
            LOGGER.log(Level.WARNING, "No class path provider for project: {0}", project.getProjectDirectory());
            return ClassPath.EMPTY;
        }

        ClassPath classPath = classPaths.getAllRuntimeClassPaths();
        if (classPath == null) {
            LOGGER.log(Level.WARNING, "No runtime class path for project: {0}", project.getProjectDirectory());
            return ClassPath.EMPTY;
        }
        return classPath;
    }

    private OpenEditorOutputListener tryCreateLinkListener(
            SourceForBinaryQuery.Result sourceForBinary,
            String path,
            String lineNum) {

        FileObject[] roots = sourceForBinary.getRoots();
        for (FileObject root: roots) {
            FileObject javaFo = root.getFileObject(path);
            if (javaFo != null) {
                int lineInt = -1;
                try {
                    lineInt = Integer.parseInt(lineNum) - 1;
                } catch (NumberFormatException ex) {
                }
                return OpenEditorOutputListener.tryCreateListener(javaFo, lineInt);
            }
        }
        return null;
    }

    // This method is based on
    // org.netbeans.modules.maven.api.output.OutputUtils.matchStackTraceLine
    private OpenEditorOutputListener matchStackTraceLine(String line) {
        Matcher match = LINE_PATTERN.matcher(line);
        if (!match.matches()) {
            return null;
        }

        String method = match.group(1);
        String file = match.group(2);
        String lineNum = match.group(3);
        int index = method.indexOf(file);
        if (index < 0) {
            return null;
        }
        String packageName = method.substring(0, index).replace('.', '/');
        String resourceName = packageName + file + ".class";
        FileObject resource = classPath.findResource(resourceName);
        if (resource == null) {
            return null;
        }

        String path = packageName + file + ".java";
        FileObject root = classPath.findOwnerRoot(resource);
        if (root == null) {
            return null;
        }
        URL url = URLMapper.findURL(root, URLMapper.INTERNAL);

        for (SourceForBinaryQueryImplementation query: project.getLookup().lookupAll(SourceForBinaryQueryImplementation.class)) {
            SourceForBinaryQuery.Result sourceForBinary = query.findSourceRoots(url);
            if (sourceForBinary != null) {
                OpenEditorOutputListener result = tryCreateLinkListener(sourceForBinary, path, lineNum);
                if (result != null) {
                    return result;
                }
            }
        }

        SourceForBinaryQuery.Result sourceForBinary = SourceForBinaryQuery.findSourceRoots(url);
        if (sourceForBinary == null) {
            return null;
        }
        return tryCreateLinkListener(sourceForBinary, path, lineNum);
    }

    public ActionListener tryGetOpenEditorAction(String line) {
        return matchStackTraceLine(line);
    }

    @Override
    public boolean tryConsumeLine(String line, OutputWriter output) throws IOException {
        OutputListener listener = matchStackTraceLine(line);
        if (listener != null) {
            output.println(line, listener, false);
            return true;
        }
        else {
            return false;
        }
    }
}
