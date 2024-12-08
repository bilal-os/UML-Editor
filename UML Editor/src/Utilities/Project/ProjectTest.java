package Utilities.Project;

import Utilities.Diagram.Diagram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class ProjectTest {

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(project.getDiagrams(), "Diagrams list should not be null");
        assertTrue(project.getDiagrams().isEmpty(), "Diagrams list should be empty initially");
        assertNotNull(project.observers, "Observers list should not be null");
        assertTrue(project.observers.isEmpty(), "Observers list should be empty initially");
    }

    @Test
    void testParameterizedConstructor() {
        Project project = new Project(1, "Test Project");
        assertEquals("Test Project", project.getName(), "Name should be set to 'Test Project'");
        assertEquals(1, project.getId(), "ID should be set to 1");
    }


    @Test
    void testGetName() {
        Project project = new Project(1, "Sample Project");
        assertEquals("Sample Project", project.getName(), "Name should match the parameter passed to the constructor");
    }

    @Test
    void testSetName() {
        project.setName("New Project Name");
        assertEquals("New Project Name", project.getName(), "Project name should be updated to 'New Project Name'");
    }

    @Test
    void testNotifyObservers_NoObservers() {
        project.notifyObservers();
        // No exception should be thrown, and there is no observer to verify
    }
}
