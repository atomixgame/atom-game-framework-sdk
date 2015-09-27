package org.netbeans.gradle.project.model.issue;

import org.jtrim.utils.ExceptionHelper;
import org.netbeans.gradle.project.NbStrings;

public final class DependencyResolutionIssue {
    public enum DependencyKind {
        RUNTIME,
        COMPILE;
    }

    private final String projectName;
    private final String sourceSetName;
    private final DependencyKind dependencyKind;
    private final Throwable stackTrace;

    public DependencyResolutionIssue(
            String projectName,
            String sourceSetName,
            DependencyKind dependencyKind,
            Throwable stackTrace) {

        ExceptionHelper.checkNotNullArgument(projectName, "projectName");
        ExceptionHelper.checkNotNullArgument(sourceSetName, "sourceSetName");
        ExceptionHelper.checkNotNullArgument(dependencyKind, "dependencyKind");
        ExceptionHelper.checkNotNullArgument(stackTrace, "stackTrace");

        this.projectName = projectName;
        this.sourceSetName = sourceSetName;
        this.dependencyKind = dependencyKind;
        this.stackTrace = stackTrace;
    }

    public static DependencyResolutionIssue compileIssue(String projectName, String sourceSetName, Throwable stackTrace) {
        return new DependencyResolutionIssue(projectName, sourceSetName, DependencyKind.COMPILE, stackTrace);
    }

    public static DependencyResolutionIssue runtimeIssue(String projectName, String sourceSetName, Throwable stackTrace) {
        return new DependencyResolutionIssue(projectName, sourceSetName, DependencyKind.RUNTIME, stackTrace);
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSourceSetName() {
        return sourceSetName;
    }

    public DependencyKind getDependencyKind() {
        return dependencyKind;
    }

    public Throwable getStackTrace() {
        return stackTrace;
    }

    public String getMessage() {
        switch (dependencyKind) {
            case RUNTIME:
                return NbStrings.getRuntimeDependencyResolutionFailure(projectName, sourceSetName);
            case COMPILE:
                return NbStrings.getCompileDependencyResolutionFailure(projectName, sourceSetName);
            default:
                throw new AssertionError(dependencyKind.name());
        }
    }
}
