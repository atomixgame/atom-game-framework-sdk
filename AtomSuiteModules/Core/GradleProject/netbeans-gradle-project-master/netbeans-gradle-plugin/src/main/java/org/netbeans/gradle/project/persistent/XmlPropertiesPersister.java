package org.netbeans.gradle.project.persistent;

import java.io.File;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jtrim.cancel.Cancellation;
import org.jtrim.cancel.CancellationToken;
import org.jtrim.concurrent.CancelableTask;
import org.jtrim.concurrent.CleanupTask;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.gradle.project.NbGradleProject;
import org.netbeans.gradle.project.NbTaskExecutors;
import org.netbeans.gradle.project.api.entry.ProjectPlatform;
import org.netbeans.gradle.project.properties.AuxConfig;
import org.netbeans.gradle.project.properties.AuxConfigSource;
import org.netbeans.gradle.project.properties.GradleLocation;
import org.netbeans.gradle.project.properties.LicenseHeaderInfo;
import org.netbeans.gradle.project.properties.MutableProperty;
import org.netbeans.gradle.project.properties.PredefinedTask;
import org.netbeans.gradle.project.properties.ProjectProperties;
import org.netbeans.gradle.project.properties.PropertiesSnapshot;
import org.netbeans.gradle.project.properties.PropertySource;

public final class XmlPropertiesPersister implements PropertiesPersister {
    private static final Logger LOGGER = Logger.getLogger(XmlPropertiesPersister.class.getName());

    private final File propertiesFile;

    public XmlPropertiesPersister(File propertiesFile) {
        ExceptionHelper.checkNotNullArgument(propertiesFile, "propertiesFile");

        this.propertiesFile = propertiesFile;
    }

    private void checkCallingThread() {
        if (!PropertiesPersister.PERSISTER_PROCESSOR.isExecutingInThis()) {
            throw new IllegalStateException("This method may only be called from PropertiesPersister.PERSISTER_PROCESSOR.");
        }
    }

    @Override
    public void save(final NbGradleProject project, final ProjectProperties properties, final Runnable onDone) {
        checkCallingThread();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final PropertiesSnapshot snapshot = new PropertiesSnapshot(properties);
                NbGradleProject.PROJECT_PROCESSOR.execute(Cancellation.UNCANCELABLE_TOKEN, new CancelableTask() {
                    @Override
                    public void execute(CancellationToken cancelToken) {
                        XmlPropertyFormat.saveToXml(project, propertiesFile, snapshot);
                    }
                }, new CleanupTask() {
                    @Override
                    public void cleanup(boolean canceled, Throwable error) throws Exception {
                        NbTaskExecutors.defaultCleanup(canceled, error);
                        if (onDone != null) {
                            onDone.run();
                        }
                    }
                });
            }
        });
    }

    private static <ValueType> PropertySetter<ValueType> newPropertySetter(
            MutableProperty<ValueType> property,
            PropertyGetter<ValueType> getter) {
        return new PropertySetter<>(property, getter);
    }

    @Override
    public void load(final ProjectProperties properties, final boolean usedConcurrently, final Runnable onDone) {
        checkCallingThread();

        // We must listen for changes, so that we do not overwrite properties
        // modified later.

        final List<PropertySetter<?>> setters = new LinkedList<>();

        // Just add a new element to the list when a new property needs to be
        // saved.
        setters.add(newPropertySetter(properties.getPlatform(), new PropertyGetter<ProjectPlatform>() {
            @Override
            public PropertySource<ProjectPlatform> get(PropertiesSnapshot snapshot) {
                return snapshot.getPlatform();
            }
        }));
        setters.add(newPropertySetter(properties.getSourceEncoding(), new PropertyGetter<Charset>() {
            @Override
            public PropertySource<Charset> get(PropertiesSnapshot snapshot) {
                return snapshot.getSourceEncoding();
            }
        }));
        setters.add(newPropertySetter(properties.getSourceLevel(), new PropertyGetter<String>() {
            @Override
            public PropertySource<String> get(PropertiesSnapshot snapshot) {
                return snapshot.getSourceLevel();
            }
        }));
        setters.add(newPropertySetter(properties.getCommonTasks(), new PropertyGetter<List<PredefinedTask>>() {
            @Override
            public PropertySource<List<PredefinedTask>> get(PropertiesSnapshot snapshot) {
                return snapshot.getCommonTasks();
            }
        }));
        setters.add(newPropertySetter(properties.getScriptPlatform(), new PropertyGetter<JavaPlatform>() {
            @Override
            public PropertySource<JavaPlatform> get(PropertiesSnapshot snapshot) {
                return snapshot.getScriptPlatform();
            }
        }));
        setters.add(newPropertySetter(properties.getGradleLocation(), new PropertyGetter<GradleLocation>() {
            @Override
            public PropertySource<GradleLocation> get(PropertiesSnapshot snapshot) {
                return snapshot.getGradleHome();
            }
        }));
        setters.add(newPropertySetter(properties.getLicenseHeader(), new PropertyGetter<LicenseHeaderInfo>() {
            @Override
            public PropertySource<LicenseHeaderInfo> get(PropertiesSnapshot snapshot) {
                return snapshot.getLicenseHeader();
            }
        }));
        for (final String command: properties.getKnownBuiltInCommands()) {
            MutableProperty<PredefinedTask> taskProperty = properties.tryGetBuiltInTask(command);
            if (taskProperty == null) {
                LOGGER.log(Level.WARNING, "tryGetBuiltInTask returned null for command: {0}", command);
                continue;
            }
            setters.add(newPropertySetter(taskProperty, new PropertyGetter<PredefinedTask>() {
                @Override
                public PropertySource<PredefinedTask> get(PropertiesSnapshot snapshot) {
                    return snapshot.tryGetBuiltInTask(command);
                }
            }));
        }

        for (PropertySetter<?> setter: setters) {
            setter.start();
        }

        final Executor setterExecutor = usedConcurrently
                ? SwingExecutor.INSTANCE
                : RecursiveExecutor.INSTANCE;

        NbGradleProject.PROJECT_PROCESSOR.execute(Cancellation.UNCANCELABLE_TOKEN, new CancelableTask() {
            @Override
            public void execute(CancellationToken cancelToken) {
                final PropertiesSnapshot snapshot = XmlPropertyFormat.readFromXml(propertiesFile);

                setterExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (PropertySetter<?> setter: setters) {
                            setter.set(snapshot);
                        }

                        // TODO: This might overwrite concurrently set
                        //  properties which is unexpected by the user. This
                        //  is unlikely to happen but should be fixed anyway.

                        Set<Map.Entry<String, PropertySource<PredefinedTask>>> builtInTasks
                                = snapshot.getBuiltInTasks().entrySet();
                        for (Map.Entry<String, PropertySource<PredefinedTask>> taskEntry: builtInTasks) {
                            MutableProperty<PredefinedTask> property
                                    = properties.tryGetBuiltInTask(taskEntry.getKey());
                            if (property == null) {
                                LOGGER.log(Level.SEVERE, "Cannot set property for built-in task: {0}", taskEntry.getKey());
                            }
                            else {
                                property.setValueFromSource(taskEntry.getValue());
                            }
                        }

                        List<AuxConfig> newAuxConfigs = new LinkedList<>();
                        for (AuxConfigSource config: snapshot.getAuxProperties()) {
                            newAuxConfigs.add(new AuxConfig(config.getKey(), config.getSource().getValue()));
                        }
                        properties.setAllAuxConfigs(newAuxConfigs);

                        if (onDone != null) {
                            onDone.run();
                        }
                    }
                });
            }
        }, new CleanupTask() {
            @Override
            public void cleanup(boolean canceled, Throwable error) throws Exception {
                NbTaskExecutors.defaultCleanup(canceled, error);

                // required, so that the listeners will not
                // be removed before setting the properties.
                setterExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (PropertySetter<?> setter: setters) {
                            setter.done();
                        }
                    }
                });
            }
        });
    }

    private interface PropertyGetter<ValueType> {
        public PropertySource<ValueType> get(PropertiesSnapshot snapshot);
    }

    private static class PropertySetter<ValueType> {
        private final MutableProperty<ValueType> property;
        private final PropertyGetter<? extends ValueType> getter;
        private final AtomicReference<ChangeDetector> detectorRef;

        public PropertySetter(MutableProperty<ValueType> property, PropertyGetter<? extends ValueType> getter) {
            assert property != null;
            this.property = property;
            this.getter = getter;
            this.detectorRef = new AtomicReference<>(new ChangeDetector());
        }

        private ChangeDetector getDectector() {
            ChangeDetector detector = detectorRef.get();
            if (detector == null) {
                throw new IllegalStateException();
            }
            return detector;
        }

        public void start() {
            property.addChangeListener(getDectector());
        }

        public void set(PropertiesSnapshot snapshot) {
            PropertySource<? extends ValueType> source = getter.get(snapshot);
            if (source != null) {
                property.setValueFromSource(source);
            }
            else {
                LOGGER.warning("null property source.");
            }
        }

        public void done() {
            ChangeDetector detector = detectorRef.getAndSet(null);
            if (detector != null) {
                property.removeChangeListener(detector);
            }
        }
    }

    private static class ChangeDetector implements ChangeListener {
        private volatile boolean changed;

        public boolean hasChanged() {
            return changed;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            changed = true;
        }
    }

    private enum RecursiveExecutor implements Executor {
        INSTANCE;

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private enum SwingExecutor implements Executor {
        INSTANCE;

        @Override
        public void execute(Runnable command) {
            SwingUtilities.invokeLater(command);
        }
    }
}
