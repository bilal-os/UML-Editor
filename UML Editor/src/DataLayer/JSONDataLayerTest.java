package DataLayer;

import Utilities.Diagram.Diagram;
import Utilities.Project.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JSONDataLayerTest {

    private JSONDataLayer dataLayer;

    @BeforeEach
    void setUp() {
        dataLayer = new JSONDataLayer();
    }

    @Test
    void testSaveProject() throws Exception {
        Project mockProject = mock(Project.class);
        when(mockProject.getName()).thenReturn("TestProject");

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        dataLayer.saveProject(mockProject, tempDir.getAbsolutePath());

        File savedFile = new File(tempDir, "TestProject.json");
        assertTrue(savedFile.exists(), "JSON file should be saved");

        // Cleanup
        savedFile.delete();
    }

    @Test
    void testSaveProjectThrowsExceptionOnSerializationError() throws Exception {
        Project mockProject = mock(Project.class);
        when(mockProject.getName()).thenReturn("TestProject");

        JSONDataLayer mockDataLayer = spy(dataLayer);
        doThrow(new IOException("Serialization error"))
                .when(mockDataLayer).saveProject(Mockito.any(Project.class), Mockito.anyString());

        assertThrows(Exception.class, () -> mockDataLayer.saveProject(mockProject, "dummyPath"));
    }

    @Test
    void testLoadProject() throws Exception {
        // Create a temporary JSON file to test loading
        String jsonContent = "{\"name\":\"TestProject\"}";
        File tempFile = File.createTempFile("TestProject", ".json");
        org.apache.commons.io.FileUtils.writeStringToFile(tempFile, jsonContent, "UTF-8");

        Project loadedProject = dataLayer.loadProject(tempFile.getAbsolutePath());
        assertEquals("TestProject", loadedProject.getName(), "Loaded project name should match");

        // Cleanup
        tempFile.delete();
    }

    @Test
    void testLoadProjectThrowsExceptionForNonexistentFile() {
        assertThrows(Exception.class, () -> dataLayer.loadProject("nonexistent.json"));
    }

    @Test
    void testSavePng() throws Exception {
        Diagram mockDiagram = mock(Diagram.class);

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        dataLayer.savePng(mockDiagram, tempDir.getAbsolutePath());

        // Check if a PNG file was created
        File[] files = tempDir.listFiles((dir, name) -> name.startsWith("Diagram_") && name.endsWith(".png"));
        assertNotNull(files);
        assertTrue(files.length > 0, "PNG file should be created");

        // Cleanup
        for (File file : files) {
            file.delete();
        }
    }
    ////////////
    @Test
    void testSaveJpg() throws Exception {
        Diagram mockDiagram = mock(Diagram.class);

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        dataLayer.saveJpg(mockDiagram, tempDir.getAbsolutePath());

        // Check if a JPG file was created
        File[] files = tempDir.listFiles((dir, name) -> name.startsWith("Diagram_") && name.endsWith(".jpg"));
        assertNotNull(files);
        assertTrue(files.length > 0, "JPG file should be created");

        // Cleanup
        for (File file : files) {
            file.delete();
        }
    }

    @Test
    void testSavePngHandlesGraphicsRendering() throws Exception {
        Diagram mockDiagram = mock(Diagram.class);

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        dataLayer.savePng(mockDiagram, tempDir.getAbsolutePath());

        // Verify that `renderDiagram` was called
        verify(mockDiagram, times(1)).renderDiagram(any(Graphics2D.class));
    }

    @Test
    void testSaveJpgHandlesGraphicsRendering() throws Exception {
        Diagram mockDiagram = mock(Diagram.class);

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        dataLayer.saveJpg(mockDiagram, tempDir.getAbsolutePath());

        // Verify that `renderDiagram` was called
        verify(mockDiagram, times(1)).renderDiagram(any(Graphics2D.class));
    }
}
