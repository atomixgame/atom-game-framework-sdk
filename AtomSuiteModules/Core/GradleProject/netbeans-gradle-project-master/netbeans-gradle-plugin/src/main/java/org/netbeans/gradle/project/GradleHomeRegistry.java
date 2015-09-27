package org.netbeans.gradle.project;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jtrim.cancel.Cancellation;
import org.jtrim.cancel.CancellationToken;
import org.jtrim.concurrent.CancelableTask;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.classpath.GlobalPathRegistry;
import org.netbeans.gradle.project.properties.GlobalGradleSettings;
import org.netbeans.gradle.project.query.GradleHomeClassPathProvider;
import org.netbeans.spi.java.classpath.ClassPathFactory;
import org.netbeans.spi.java.classpath.ClassPathImplementation;
import org.netbeans.spi.java.classpath.PathResourceImplementation;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.filesystems.FileObject;

public final class GradleHomeRegistry {
    private static final AtomicReference<GradleHomePaths> GRADLE_HOME_BINARIES;
    private static final AtomicBoolean REGISTERED_GLOBAL_PATH;
    private static final AtomicBoolean USING_GLOBAL_PATHS;
    private static final PropertyChangeSupport CHANGES;

    static {
        EventSource eventSource = new EventSource();
        CHANGES = new PropertyChangeSupport(eventSource);
        eventSource.init(CHANGES);

        USING_GLOBAL_PATHS = new AtomicBoolean(false);
        REGISTERED_GLOBAL_PATH = new AtomicBoolean(false);
        GRADLE_HOME_BINARIES = new AtomicReference<>(new GradleHomePaths());
    }

    private static void doRegisterGlobalClassPath() {
        ClassPath classPath = ClassPathFactory.createClassPath(new ClassPathImplementation() {
            @Override
            public List<PathResourceImplementation> getResources() {
                return GRADLE_HOME_BINARIES.get().getPaths();
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {
                CHANGES.addPropertyChangeListener(listener);
            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {
                CHANGES.removePropertyChangeListener(listener);
            }
        });

        GlobalPathRegistry.getDefault().register(ClassPath.COMPILE, new ClassPath[]{classPath});
    }

    public static void requireGradlePaths() {
        if (USING_GLOBAL_PATHS.compareAndSet(false, true)) {
            GlobalGradleSettings.getGradleHome().addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    CHANGES.firePropertyChange(ClassPathImplementation.PROP_RESOURCES, null, null);
                }
            });

            FileObject gradleHome = GlobalGradleSettings.getGradleLocation();
            if (gradleHome != null) {
                setGradleHome(gradleHome);
            }
        }
    }

    public static void setGradleHome(final FileObject gradleHome) {
        ExceptionHelper.checkNotNullArgument(gradleHome, "gradleHome");

        NbGradleProject.PROJECT_PROCESSOR.execute(Cancellation.UNCANCELABLE_TOKEN, new CancelableTask() {
            @Override
            public void execute(CancellationToken cancelToken) {
                URL[] urls = GradleHomeClassPathProvider.getAllGradleLibs(gradleHome);
                if (urls.length == 0) {
                    // Keep the previous classpaths if there are non found.
                    return;
                }

                if (gradleHome.equals(GRADLE_HOME_BINARIES.get().getHomePath())) {
                    return;
                }

                GRADLE_HOME_BINARIES.set(new GradleHomePaths(gradleHome, urls));

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        CHANGES.firePropertyChange(ClassPathImplementation.PROP_RESOURCES, null, null);
                    }
                });

                if (REGISTERED_GLOBAL_PATH.compareAndSet(false, true)) {
                    doRegisterGlobalClassPath();
                }
            }
        }, null);
    }

    private static class GradleHomePaths {
        private final FileObject homePath;
        private final List<PathResourceImplementation> paths;

        public GradleHomePaths() {
            this(null, new URL[0]);
        }

        public GradleHomePaths(FileObject homePath, URL[] urls) {
            ExceptionHelper.checkNotNullElements(urls, "urls");

            this.homePath = homePath;

            List<PathResourceImplementation> resources = new ArrayList<>(urls.length);
            for (URL url: urls) {
                resources.add(ClassPathSupport.createResource(url));
            }
            this.paths = Collections.unmodifiableList(resources);
        }

        public FileObject getHomePath() {
            return homePath;
        }

        public List<PathResourceImplementation> getPaths() {
            return paths;
        }
    }

    private static final class EventSource implements ClassPathImplementation {
        private volatile PropertyChangeSupport changes;

        public void init(PropertyChangeSupport changes) {
            assert changes != null;
            this.changes = changes;
        }

        @Override
        public List<PathResourceImplementation> getResources() {
            return Collections.emptyList();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            changes.addPropertyChangeListener(listener);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            changes.removePropertyChangeListener(listener);
        }
    }
}
