package org.netbeans.gradle.project.properties;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.gradle.model.util.CollectionUtils;
import org.netbeans.gradle.project.api.entry.ProjectPlatform;
import org.w3c.dom.Element;

public final class FallbackProjectProperties
extends
        AbstractProjectProperties {
    private static final Logger LOGGER = Logger.getLogger(FallbackProjectProperties.class.getName());

    private final ProjectProperties mainProperties;
    private final ProjectProperties defaultProperties;

    private final MutableProperty<String> sourceLevel;
    private final MutableProperty<ProjectPlatform> platform;
    private final MutableProperty<JavaPlatform> scriptPlatform;
    private final MutableProperty<GradleLocation> gradleHome;
    private final MutableProperty<Charset> sourceEncoding;
    private final MutableProperty<LicenseHeaderInfo> licenseHeader;
    private final MutableProperty<Void> auxConfigListener;
    private final MutableProperty<List<PredefinedTask>> commonTasks;

    public FallbackProjectProperties(ProjectProperties mainProperties, ProjectProperties defaultProperties) {
        ExceptionHelper.checkNotNullArgument(mainProperties, "mainProperties");
        ExceptionHelper.checkNotNullArgument(defaultProperties, "defaultProperties");

        this.mainProperties = mainProperties;
        this.defaultProperties = defaultProperties;

        this.sourceLevel = new FallbackProperty<>(
                mainProperties.getSourceLevel(),
                defaultProperties.getSourceLevel());

        this.platform = new FallbackProperty<>(
                mainProperties.getPlatform(),
                defaultProperties.getPlatform());

        this.scriptPlatform = new FallbackProperty<>(
                mainProperties.getScriptPlatform(),
                defaultProperties.getScriptPlatform());

        this.gradleHome = new FallbackProperty<>(
                mainProperties.getGradleLocation(),
                defaultProperties.getGradleLocation());

        this.sourceEncoding = new FallbackProperty<>(
                mainProperties.getSourceEncoding(),
                defaultProperties.getSourceEncoding());

        this.licenseHeader = new FallbackProperty<>(
                mainProperties.getLicenseHeader(),
                defaultProperties.getLicenseHeader());

        this.auxConfigListener = new FallbackProperty<>(
                mainProperties.getAuxConfigListener(),
                defaultProperties.getAuxConfigListener());

        this.commonTasks = new ListMergerProperty<>(
                mainProperties.getCommonTasks(),
                defaultProperties.getCommonTasks());
    }

    @Override
    public MutableProperty<LicenseHeaderInfo> getLicenseHeader() {
        return licenseHeader;
    }

    @Override
    public MutableProperty<String> getSourceLevel() {
        return sourceLevel;
    }

    @Override
    public MutableProperty<ProjectPlatform> getPlatform() {
        return platform;
    }

    @Override
    public MutableProperty<Charset> getSourceEncoding() {
        return sourceEncoding;
    }

    @Override
    public MutableProperty<List<PredefinedTask>> getCommonTasks() {
        return commonTasks;
    }

    @Override
    public MutableProperty<JavaPlatform> getScriptPlatform() {
        return scriptPlatform;
    }

    @Override
    public MutableProperty<GradleLocation> getGradleLocation() {
        return gradleHome;
    }

    @Override
    public MutableProperty<PredefinedTask> tryGetBuiltInTask(String command) {
        MutableProperty<PredefinedTask> mainProperty = mainProperties.tryGetBuiltInTask(command);
        MutableProperty<PredefinedTask> defaultProperty = defaultProperties.tryGetBuiltInTask(command);
        if (mainProperty == null) {
            if (defaultProperty == null) {
                // We must ignore because otherwise someone trying to set the
                // property would set the defaultProperty which should not
                // happen.
                LOGGER.warning("Main property is null but the default is not, ignoring the default.");
            }
            return null;
        }
        if (defaultProperty == null) {
            return mainProperty;
        }

        return new FallbackProperty<>(mainProperty, defaultProperty);
    }

    @Override
    public Set<String> getKnownBuiltInCommands() {
        Set<String> known1 = mainProperties.getKnownBuiltInCommands();
        Set<String> known2 = defaultProperties.getKnownBuiltInCommands();

        Set<String> result = CollectionUtils.newHashSet(known1.size() + known2.size());
        result.addAll(known1);
        result.addAll(known2);
        return result;
    }

    @Override
    public MutableProperty<Void> getAuxConfigListener() {
        return auxConfigListener;
    }

    @Override
    public AuxConfigProperty getAuxConfig(String elementName, String namespace) {
        FallbackProperty<Element> property = new FallbackProperty<>(
                mainProperties.getAuxConfig(elementName, namespace).getProperty(),
                defaultProperties.getAuxConfig(elementName, namespace).getProperty());
        return new AuxConfigProperty(elementName, namespace, property);
    }

    @Override
    public void setAllAuxConfigs(Collection<AuxConfig> configs) {
        mainProperties.setAllAuxConfigs(configs);
    }

    @Override
    public Collection<AuxConfigProperty> getAllAuxConfigs() {
        return mainProperties.getAllAuxConfigs();
    }

    private static class ListMergerProperty<ElementType>
    implements
            MutableProperty<List<ElementType>> {

        private final MutableProperty<List<ElementType>> mainValue;
        private final MutableProperty<List<ElementType>> defaultValue;

        public ListMergerProperty(MutableProperty<List<ElementType>> mainValue, MutableProperty<List<ElementType>> defaultValue) {
            ExceptionHelper.checkNotNullArgument(mainValue, "mainValue");
            ExceptionHelper.checkNotNullArgument(defaultValue, "defaultValue");

            this.mainValue = mainValue;
            this.defaultValue = defaultValue;
        }

        @Override
        public void setValueFromSource(PropertySource<? extends List<ElementType>> source) {
            mainValue.setValueFromSource(source);
        }

        @Override
        public void setValue(List<ElementType> value) {
            mainValue.setValue(value);
        }

        @Override
        public List<ElementType> getValue() {
            List<ElementType> mainList = mainValue.getValue();
            List<ElementType> defaultList = defaultValue.getValue();

            List<ElementType> result = new ArrayList<>(mainList.size() + defaultList.size());
            result.addAll(mainList);
            result.addAll(defaultList);
            return result;
        }

        @Override
        public boolean isDefault() {
            return mainValue.isDefault() && defaultValue.isDefault();
        }

        @Override
        public void addChangeListener(ChangeListener listener) {
            ExceptionHelper.checkNotNullArgument(listener, "listener");

            mainValue.addChangeListener(listener);
            defaultValue.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ChangeListener listener) {
            ExceptionHelper.checkNotNullArgument(listener, "listener");

            defaultValue.removeChangeListener(listener);
            mainValue.removeChangeListener(listener);
        }
    }
}
