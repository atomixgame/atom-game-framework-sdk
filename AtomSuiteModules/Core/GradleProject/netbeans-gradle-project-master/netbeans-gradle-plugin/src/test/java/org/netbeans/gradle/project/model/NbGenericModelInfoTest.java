package org.netbeans.gradle.project.model;

import java.io.File;
import org.junit.Test;
import org.netbeans.gradle.model.util.SerializationUtils;

import static org.junit.Assert.*;
import static org.netbeans.gradle.project.model.NbGradleMultiProjectDefTest.createTestMultiProject;

public class NbGenericModelInfoTest {
    @Test
    public void testSerialization() throws ClassNotFoundException {
        NbGradleMultiProjectDef projectDef = createTestMultiProject();
        NbGenericModelInfo source = new NbGenericModelInfo(projectDef, new File("settings.gradle"));

        byte[] serialized = SerializationUtils.serializeObject(source);
        NbGenericModelInfo deserialized = (NbGenericModelInfo)SerializationUtils.deserializeObject(serialized);

        assertEquals(source.getProjectDir().toString(), deserialized.getProjectDir().toString());
        assertEquals(
                source.getSettingsFile().toString(),
                deserialized.getSettingsFile().toString());
    }
}
