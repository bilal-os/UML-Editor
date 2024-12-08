package Utilities.Project;

import Utilities.Diagram.Diagram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectTest {
    private Project project;
    private Diagram mockDiagram;
    private ProjectObserver mockObserver;

    @BeforeEach
    void setUp() {
        project = new Project(1, "Test Project");
        mockDiagram = mock(Diagram.class);
        mockObserver = mock(ProjectObserver.class);
    }

    // =======================
    // Test Constructor
    // =======================
    @Test
    void testConstructor() {
        assertEquals(1, project.getId());
        assertEquals("Test Project", project.getName());
        assertNotNull(project.getDiagrams());
        assertEquals(0, project.getDiagrams().size());
    }

    // =======================
    // Test Observer Management
    // =======================
    @Test
    void testAddObserver() {
        project.addObserver(mockObserver);
        project.notifyObservers();
        verify(mockObserver, times(1)).updateFromProject();
    }

    @Test
    void testRemoveObserver() {
        project.addObserver(mockObserver);
        project.removeObserver(mockObserver);
        project.notifyObservers();
        verify(mockObserver, never()).updateFromProject();
    }

    @Test
    void testNotifyObservers() {
        ProjectObserver observer1 = mock(ProjectObserver.class);
        ProjectObserver observer2 = mock(ProjectObserver.class);

        project.addObserver(observer1);
        project.addObserver(observer2);

        project.notifyObservers();

        verify(observer1, times(1)).updateFromProject();
        verify(observer2, times(1)).updateFromProject();
    }

    // =======================
    // Test Diagram Management
    // =======================
    @Test
    void testAddDiagram() {
        project.addDiagram(mockDiagram);
        assertEquals(1, project.getDiagrams().size());
    }

    @Test
    void testSetDiagrams() {
        ArrayList<Diagram> newDiagrams = new ArrayList<>();
        newDiagrams.add(mockDiagram);
        project.setDiagrams(newDiagrams);

        assertEquals(1, project.getDiagrams().size());
    }

    @Test
    void testGetDiagrams() {
        project.addDiagram(mockDiagram);
        ArrayList<Diagram> diagrams = project.getDiagrams();
        assertNotNull(diagrams);
        assertEquals(1, diagrams.size());
        assertEquals(mockDiagram, diagrams.get(0));
    }

    // =======================
    // Test Data Encapsulation
    // =======================
    @Test
    void testSetAndGetId() {
        project.setId(42);
        assertEquals(42, project.getId());
    }

    @Test
    void testSetAndGetName() {
        project.setName("Updated Project Name");
        assertEquals("Updated Project Name", project.getName());
    }

    // =======================
    // Test Serialization
    // =======================
    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        project.setId(42);
        project.setName("Serialized Project");
        project.addDiagram(mockDiagram);

        // Serialize the object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(project);

        // Deserialize the object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        Project deserializedProject = (Project) objectIn.readObject();

        assertNotNull(deserializedProject);
        assertEquals(42, deserializedProject.getId());
        assertEquals("Serialized Project", deserializedProject.getName());
        assertEquals(1, deserializedProject.getDiagrams().size());
    }

    @Test
    void testObserversAreTransient() throws IOException, ClassNotFoundException {
        project.addObserver(mockObserver);

        // Serialize the project
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(project);

        // Deserialize the project
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        Project deserializedProject = (Project) objectIn.readObject();

        // Since observers are transient, they should be empty after deserialization
        assertNotNull(deserializedProject);
        assertEquals(0, deserializedProject.observers.size());
    }
}
