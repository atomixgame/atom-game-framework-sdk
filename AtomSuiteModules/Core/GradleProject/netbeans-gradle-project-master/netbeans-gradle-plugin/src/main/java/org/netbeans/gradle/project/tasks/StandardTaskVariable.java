package org.netbeans.gradle.project.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.gradle.model.util.CollectionUtils;
import org.netbeans.gradle.project.NbGradleProject;
import org.netbeans.gradle.project.api.task.TaskVariable;
import org.netbeans.gradle.project.api.task.TaskVariableMap;
import org.netbeans.gradle.project.java.test.SpecificTestClass;
import org.netbeans.gradle.project.java.test.SpecificTestcase;
import org.netbeans.gradle.project.java.test.TestTaskName;
import org.netbeans.gradle.project.tasks.CachingVariableMap.ValueGetter;
import org.netbeans.gradle.project.tasks.CachingVariableMap.VariableDef;
import org.netbeans.gradle.project.tasks.CachingVariableMap.VariableDefMap;
import org.netbeans.gradle.project.tasks.CachingVariableMap.VariableValue;
import org.netbeans.gradle.project.util.StringUtils;
import org.netbeans.spi.project.SingleMethod;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;

public enum StandardTaskVariable {
    PROJECT_NAME("project", new ValueGetter<NbGradleProject>() {
        @Override
        public VariableValue getValue(TaskVariableMap variables, NbGradleProject project, Lookup actionContext) {
            String uniqueName = project.getAvailableModel().getMainProject().getProjectFullName();
            if (":".equals(uniqueName)) { // This is the root project.
                uniqueName = "";
            }
            return new VariableValue(uniqueName);
        }
    }),
    SELECTED_CLASS("selected-class", new ValueGetter<NbGradleProject>() {
        @Override
        public VariableValue getValue(TaskVariableMap variables, NbGradleProject project, Lookup actionContext) {
            SpecificTestClass testClass = actionContext.lookup(SpecificTestClass.class);
            if (testClass != null) {
                return new VariableValue(testClass.getTestClassName());
            }

            FileObject file = getFileOfContext(actionContext);
            if (file == null) {
                return VariableValue.NULL_VALUE;
            }

            return getClassNameForFile(project, file);
        }
    }),
    TEST_FILE_PATH("test-file-path", new ValueGetter<NbGradleProject>() {
        @Override
        public VariableValue getValue(TaskVariableMap variables, NbGradleProject project, Lookup actionContext) {
            String selectedClass = variables.tryGetValueForVariable(SELECTED_CLASS.getVariable());
            return new VariableValue(deduceFromClass(selectedClass));
        }

        private String deduceFromClass(String selectedClass) {
            return selectedClass != null
                    ? selectedClass.replace('.', '/')
                    : null;
        }
    }),
    TEST_METHOD("test-method", new ValueGetter<NbGradleProject>() {
        @Override
        public VariableValue getValue(TaskVariableMap variables, NbGradleProject project, Lookup actionContext) {
            return getMethodReplaceVariable(variables, project, actionContext);
        }
    }),
    PLATFORM_DIR("platform-dir", new ValueGetter<NbGradleProject>() {
        @Override
        public VariableValue getValue(TaskVariableMap variables, NbGradleProject project, Lookup actionContext) {
            FileObject rootFolder = project.getProperties().getPlatform().getValue().getRootFolder();
            return new VariableValue(rootFolder != null
                    ? FileUtil.getFileDisplayName(rootFolder)
                    : null);
        }
    }),
    TEST_TASK_NAME("test-task-name", new ValueGetter<NbGradleProject>() {
        @Override
        public VariableValue getValue(TaskVariableMap variables, NbGradleProject project, Lookup actionContext) {
            return new VariableValue(TestTaskName.getTaskName(actionContext));
        }
    }),
    TEST_TASK_NAME_CAPITAL("test-task-name-capital", new ValueGetter<NbGradleProject>() {
        @Override
        public VariableValue getValue(TaskVariableMap variables, NbGradleProject project, Lookup actionContext) {
            String value = variables.tryGetValueForVariable(TEST_TASK_NAME.getVariable());
            return new VariableValue(value != null
                    ? StringUtils.capitalizeFirstCharacter(value)
                    : null);
        }
    });

    private static VariableValue getClassNameForFile(NbGradleProject project, FileObject file) {
        SourceGroup[] sourceGroups = ProjectUtils.getSources(project)
                .getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);

        String relFileName = null;
        for (SourceGroup group: sourceGroups) {
            FileObject sourceRoot = group.getRootFolder();
            String relPath = FileUtil.getRelativePath(sourceRoot, file);
            if (relPath != null) {
                // Remove the ".java" or ".groovy" from the end of
                // the file name
                relFileName = removeExtension(relPath);
                break;
            }
        }

        return new VariableValue(relFileName != null ? relFileName.replace('/', '.') : null);
    }

    private static final Logger LOGGER = Logger.getLogger(StandardTaskVariable.class.getName());
    private static final VariableDefMap<NbGradleProject> TASK_VARIABLE_MAP
            = createStandardMap();

    private static VariableValue getMethodReplaceVariable(
            TaskVariableMap variables,
            NbGradleProject project,
            SingleMethod method) {

        String selectedClass = variables.tryGetValueForVariable(SELECTED_CLASS.getVariable());
        if (selectedClass == null) {
            selectedClass = getClassNameForFile(project, method.getFile()).value;
            if (selectedClass == null) {
                LOGGER.log(Level.INFO, "Could not find class file name for file {0}", method.getFile());
                return VariableValue.NULL_VALUE;
            }
        }

        return new VariableValue(selectedClass + "." + method.getMethodName());
    }

    private static VariableValue getMethodReplaceVariable(
            TaskVariableMap variables,
            NbGradleProject project,
            Lookup actionContext) {

        SingleMethod method = actionContext.lookup(SingleMethod.class);
        if (method != null) {
            return getMethodReplaceVariable(variables, project, method);
        }

        SpecificTestcase specificTestcase = actionContext.lookup(SpecificTestcase.class);
        if (specificTestcase != null) {
            return new VariableValue(specificTestcase.getTestIncludePattern());
        }

        return VariableValue.NULL_VALUE;
    }

    private static String removeExtension(String filePath) {
        int extSeparatorIndex = filePath.lastIndexOf('.');
        return extSeparatorIndex >= 0
                ? filePath.substring(0, extSeparatorIndex)
                : filePath;
    }

    private static List<FileObject> getFilesOfContext(Lookup context) {
        List<FileObject> files = new LinkedList<>();
        for (DataObject dataObj: context.lookupAll(DataObject.class)) {
            FileObject file = dataObj.getPrimaryFile();
            if (file != null) {
                files.add(file);
            }
        }
        return files;
    }

    private static FileObject getFileOfContext(Lookup context) {
        List<FileObject> files = getFilesOfContext(context);
        if (files.isEmpty()) {
            return null;
        }

        FileObject file = files.get(0);
        if (file == null) {
            return null;
        }

        return file.isFolder() ? null : file;
    }

    private static VariableDefMap<NbGradleProject> createStandardMap() {
        StandardTaskVariable[] variables = StandardTaskVariable.values();

        final Map<TaskVariable, VariableDef<NbGradleProject>> result
                = CollectionUtils.newHashMap(variables.length);

        for (StandardTaskVariable variable: variables) {
            result.put(variable.getVariable(), variable.asVariableDef());
        }

        return new VariableDefMap<NbGradleProject>() {
            @Override
            public VariableDef<NbGradleProject> tryGetDef(TaskVariable variable) {
                return result.get(variable);
            }
        };
    }

    public static TaskVariableMap createVarReplaceMap(
            NbGradleProject project, Lookup actionContext) {
        return new CachingVariableMap<>(TASK_VARIABLE_MAP, project, actionContext);
    }

    public static String replaceVars(String str, TaskVariableMap varReplaceMap) {
        return replaceVars(str, varReplaceMap, null);
    }

    public static String replaceVars(
            String str,
            TaskVariableMap varReplaceMap,
            List<? super DisplayedTaskVariable> collectedVariables) {

        StringBuilder result = new StringBuilder(str.length() * 2);

        int index = 0;
        while (index < str.length()) {
            char ch = str.charAt(index);
            if (ch == '$') {
                int varStart = str.indexOf('{', index + 1);
                int varEnd = varStart >= 0
                        ? StringUtils.unescapedIndexOf(str, varStart + 1, '}')
                        : -1;
                if (varStart >= 0 && varEnd >= varStart) {
                    String varDef = str.substring(varStart + 1, varEnd);
                    DisplayedTaskVariable taskVar = DisplayedTaskVariable.tryParseTaskVariable(varDef);

                    if (taskVar != null) {
                        if (collectedVariables != null) {
                            collectedVariables.add(taskVar);
                        }

                        String value = varReplaceMap.tryGetValueForVariable(taskVar.getVariable());
                        if (value != null) {
                            result.append(value);
                            index = varEnd + 1;
                            continue;
                        }
                    }
                }
            }

            result.append(ch);
            index++;
        }
        return result.toString();
    }

    private final TaskVariable variable;
    private final ValueGetter<NbGradleProject> valueGetter;

    private StandardTaskVariable(String variableName, ValueGetter<NbGradleProject> valueGetter) {
        this.variable = new TaskVariable(variableName);
        this.valueGetter = valueGetter;
    }

    private CachingVariableMap.VariableDef<NbGradleProject> asVariableDef() {
        return new CachingVariableMap.VariableDef<>(variable, valueGetter);
    }

    public TaskVariable getVariable() {
        return variable;
    }

    public String getVariableName() {
        return variable.getVariableName();
    }

    public String getScriptReplaceConstant() {
        return variable.getScriptReplaceConstant();
    }
}
