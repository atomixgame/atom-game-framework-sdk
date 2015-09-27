package org.netbeans.gradle.project.others;

import org.jtrim.utils.ExceptionHelper;

public final class PluginEnum implements ClassFinder {
    private final ClassFinder pluginClass;

    public PluginEnum(ClassFinder pluginClass) {
        ExceptionHelper.checkNotNullArgument(pluginClass, "pluginClass");

        this.pluginClass = pluginClass;
    }

    public PluginEnum(PluginClassFactory classFactory, String className) {
        this(new PluginClass(classFactory, className));
    }

    @Override
    public Class<?> tryGetClass() {
        return pluginClass.tryGetClass();
    }

    public Object tryGetEnumConst(String name) {
        ExceptionHelper.checkNotNullArgument(name, "name");

        Class<?> type = pluginClass.tryGetClass();
        if (type == null) {
            return null;
        }

        Object[] enumTypes = type.getEnumConstants();
        if (enumTypes == null) {
            return null;
        }

        for (Object enumType: enumTypes) {
            if (enumType instanceof Enum) {
                if (name.equals(((Enum<?>)enumType).name())) {
                    return enumType;
                }
            }
        }

        return null;
    }
}
