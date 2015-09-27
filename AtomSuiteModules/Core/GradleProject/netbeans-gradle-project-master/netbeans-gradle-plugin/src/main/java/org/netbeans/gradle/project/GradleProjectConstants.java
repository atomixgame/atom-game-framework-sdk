package org.netbeans.gradle.project;

public final class GradleProjectConstants {
    public static final String BUILD_SRC_NAME = "buildSrc";
    public static final String BUILD_FILE_NAME = "build.gradle";
    public static final String SETTINGS_FILE_NAME = "settings.gradle";
    public static final String GRADLE_PROPERTIES_NAME = "gradle.properties";
    public static final String DEFAULT_GRADLE_EXTENSION_WITHOUT_DOT = "gradle";
    public static final String DEFAULT_GRADLE_EXTENSION = "." + DEFAULT_GRADLE_EXTENSION_WITHOUT_DOT;

    public static final String NB_SETTINGS_DIR = ".nbsettings";
    public static final String NB_SETTINGS_FILE = "project.settings";

    private GradleProjectConstants() {
        throw new AssertionError();
    }
}
