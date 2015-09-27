package org.netbeans.gradle.project.api.entry;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.openide.util.Lookup;

/**
 * @deprecated Use {@link GradleProjectExtension2} with
 *   {@link GradleProjectExtensionDef} instead.<P>
 *
 * Defines an extension of a particular Gradle project. Instances of this class
 * are expected to be created by a {@link GradleProjectExtensionQuery}.
 * <P>
 * Instances of this interface must be safe to be called by multiple threads
 * concurrently but they are not required to be
 * <I>synchronization transparent</I> unless otherwise noted.
 *
 * @see GradleProjectExtensionQuery
 */
@Deprecated
public interface GradleProjectExtension {
    /**
     * Returns the unique name of this extension. The name is used to reference
     * the extension when it conflicts with another extension. That is, the
     * {@link #modelsLoaded(Lookup) modelsLoaded} method may return the name of
     * an extension to suppress it. The recommended naming convention is to name
     * extensions by their fully-qualified class name.
     * <P>
     * Projects that are not Java projects usually need to suppress the Java
     * extension whose name is "org.netbeans.gradle.project.java.JavaExtension".
     *
     * @return the unique name of this extension. This method never returns
     *   {@code null}.
     */
    @Nonnull
    public String getExtensionName();

    /**
     * Returns the list of model types needed to be requested from the Tooling
     * API of Gradle. The actually loaded models will be made available for
     * this extension through the {@link #modelsLoaded(Lookup) modelsLoaded}
     * method call.
     * <P>
     * Each element of the returned list defines a single model which is
     * required by this extension. A single model is defined by a preference
     * order of models where only the first available model is loaded.
     * <P>
     * For example if you return [[ModelA.class], [ModelB1.class, ModelB2.class]],
     * then if ModelB1 is available, ModelB2 will not be loaded. It is also
     * possible that models are not available, and the extension must be able
     * to handle this case.
     *
     * @return the list of model types needed to be requested from the Tooling
     *   API of Gradle. This method never returns {@code null} and none of its
     *   elements can be {@code null} (not even the class instances).
     *
     * @see #modelsLoaded(Lookup)
     */
    @Nonnull
    public Iterable<List<Class<?>>> getGradleModels();

    /**
     * Returns the lookup which is to be added to the project's lookup. That is,
     * the objects contained in the returned lookup will be possible to be
     * queried through the project's lookup. Until the method
     * {@link #modelsLoaded(Lookup) modelsLoaded} is called,
     * {@code GradleProjectExtension} must take the same action as if
     * {@code modelsLoaded} has already been called with an empty lookup.
     * <P>
     * The following queries are known and used by the Gradle plugin itself:
     * <ul>
     *  <li>{@link org.netbeans.gradle.project.api.config.CustomProfileQuery}</li>
     *  <li>{@link org.netbeans.gradle.project.api.config.InitScriptQuery}</li>
     *  <li>{@link org.netbeans.gradle.project.api.nodes.GradleProjectContextActions}</li>
     *  <li>{@link org.netbeans.gradle.project.api.nodes.GradleProjectExtensionNodes}</li>
     *  <li>{@link org.netbeans.gradle.project.api.task.BuiltInGradleCommandQuery}</li>
     *  <li>{@link org.netbeans.gradle.project.api.task.GradleTaskVariableQuery}</li>
     *  <li>
     *   {@link org.netbeans.spi.project.ui.support.ProjectCustomizer.CompositeCategoryProvider}:
     *   You may add as many instances of this query as you want to the lookup,
     *   if you need more than one customizer.
     *  </li>
     * </ul>
     * <P>
     * Note: You may also return an implementation of
     * {@link org.netbeans.spi.project.ui.ProjectOpenedHook} but note that you
     * have to have this instance on the lookup at all times, otherwise it may
     * not get called.
     * <P>
     * <B>Implementation note</B>: If this method ever changes the
     * {@code Lookup} object it returns, then it must consider listeners
     * registered to the results of the lookup operation (see: {@code Lookup.Result}).
     * It is however recommended to always return the same lookup instance.
     * Since the objects on the lookup may change, implementations are
     * recommended to use a subclass of {@link org.openide.util.lookup.ProxyLookup}.
     *
     * @return the lookup which is to be added to the project's lookup. This
     *   method may never return {@code null}.
     *
     * @see org.openide.util.lookup.ProxyLookup
     */
    @Nonnull
    public Lookup getExtensionLookup();

    /**
     * Called whenever the models for the associated Gradle project has been
     * (re)loaded. An invocation of this method invalidates previous invocation
     * of this method and the {@code modelsLoaded} method may not be called
     * concurrently by multiple threads for the same project.
     * <P>
     * This method must return the {@link #getExtensionName() name} of the
     * extensions which must be suppressed in order for this extension to work
     * properly. It is recommended that the {@link GradleProjectExtensionQuery}
     * loaded this extension is positioned before any extension with which this
     * extension conflict. (The position of the Java extension is 1000). Note,
     * that implementations must only return conflict if they are active. That
     * is, their model was provided. If they cannot recognize any model on the
     * passed lookup they must not return conflicts (rather they must stop
     * providing their own interfaces on the lookup).
     * <P>
     * If this method returns a non-empty set, then all of those extensions will
     * be notified with {@code modelsLoaded(Lookup.EMPTY)}.
     * <P>
     * <B>Important note</B>: It is possible that the {@code Lookup} passed to
     * this method is retrieved from a previous call to the
     * {@link #deduceModelsForProjects(Lookup) deduceModelsForProjects} method.
     * <P>
     * <B>Implementation note</B>: If this implementations can't find the
     * models it needs, the expected action is to remove instances from the
     * {@link #getExtensionLookup() lookup} which might conflict with other
     * extensions. Probably the only instance which must remain on the project's
     * lookup is implementations of {@link org.netbeans.spi.project.ui.ProjectOpenedHook}.
     *
     * @param modelLookup the {@code Lookup} containing the available models
     *   loaded via the Tooling API of Gradle. If a model requested by this
     *   extension is not available on the lookup then it could not be loaded
     *   via the Tooling API. It is also possible that additional models are
     *   available on this lookup but this method must only rely on models, this
     *   extension explicitly requested. This argument cannot be {@code null}.
     * @return the {@link #getExtensionName() name} of the extensions which must
     *   be suppressed in order for this extension to work properly. This method
     *   may return {@code null} which is equivalent to returning an empty set.
     *
     * @see #getGradleModels()
     */
    @Nullable
    public Set<String> modelsLoaded(@Nonnull Lookup modelLookup);

    /**
     * Returns the lookups which might be used to fully load the given projects.
     * <P>
     * Often, an extension can deduce models it will need to load a project from
     * models loaded for another project. This might enable to greatly reduce
     * the loading time of projects because the Gradle plugin might cache the
     * returned lookups instead of asking Gradle itself.
     * <P>
     * Note that this method is allowed to associate empty lookups with project
     * directories, if it knows that for that particular project, attempting to
     * load models for this extension is futile.
     * <P>
     * This method may always safely return an empty map. However, it is
     * recommended to make a best effort to deduce whatever it can.
     *
     * @param modelLookup the {@code Lookup} from which this method should try
     *   to deduce the models it needs for projects. This argument cannot be
     *   {@code null}.
     * @return the {@code Map}, mapping project directories to lookups of
     *   models. If this method returns a {@code Lookup} for a specific
     *   project directory, the Gradle plugin may pass the returned lookup to
     *   the {@link #modelsLoaded(Lookup) modelsLoaded} instead of asking
     *   Gradle for the {@link #getGradleModels() models}. This method may never
     *   return {@code null} and must not contain a {@code null} key, or a
     *   {@code null} value.
     */
    @Nonnull
    public Map<File, Lookup> deduceModelsForProjects(@Nonnull Lookup modelLookup);
}
