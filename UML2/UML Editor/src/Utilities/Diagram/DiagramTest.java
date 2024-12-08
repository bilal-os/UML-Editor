package Utilities.Diagram;

import Utilities.Component.Component;
import Utilities.Property.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiagramTest {
    private Diagram diagram;
    private DiagramObserver mockObserver;

    // Concrete subclass of the abstract Diagram for testing
    private static class TestDiagram extends Diagram {
        public TestDiagram(String name) {
            super(name);
        }

        @Override
        public Component createComponent(String componentName) {
            // Anonymous subclass for Component
            return new Component(this) {
                @Override
                public Property createProperty(String type, String value) throws IllegalArgumentException {
                    return new Property(type, value) {
                        @Override
                        public void validateInput(String type, String value) throws IllegalArgumentException {}
                        @Override
                        public void addValue(String value) throws IllegalArgumentException {}
                    };
                }

                @Override
                public void renderComponent(Graphics g) {
                    // No-op for this test
                }
            };
        }

        @Override
        public void renderDiagram(Graphics2D g) {
            // No-op for this test
        }
    }

    @BeforeEach
    void setUp() {
        diagram = new TestDiagram("Test Diagram");
        mockObserver = mock(DiagramObserver.class);
    }

    // =======================
    // Test Observer Management
    // =======================

    @Test
    void testAddObserver() {
        diagram.addObserver(mockObserver);
        diagram.notifyObservers();
        verify(mockObserver, times(1)).updateFromDiagram();
    }

    @Test
    void testRemoveObserver() {
        diagram.addObserver(mockObserver);
        diagram.removeObserver(mockObserver);
        diagram.notifyObservers();
        verify(mockObserver, never()).updateFromDiagram();
    }

    @Test
    void testNotifyObservers() {
        DiagramObserver observer1 = mock(DiagramObserver.class);
        DiagramObserver observer2 = mock(DiagramObserver.class);
        diagram.addObserver(observer1);
        diagram.addObserver(observer2);

        diagram.notifyObservers();

        verify(observer1, times(1)).updateFromDiagram();
        verify(observer2, times(1)).updateFromDiagram();
    }

    // =======================
    // Test Component Management
    // =======================

    @Test
    void testCreateAndAddComponent() {
        Component component = diagram.addComponent("TestComponent");
        assertNotNull(component);
        assertEquals(1, diagram.getComponents().size());
    }

    @Test
    void testGetComponentNames() {
        diagram.getComponentNames().add("Component1");
        diagram.getComponentNames().add("Component2");
        List<String> componentNames = diagram.getComponentNames();

        assertEquals(2, componentNames.size());
        assertTrue(componentNames.contains("Component1"));
        assertTrue(componentNames.contains("Component2"));
    }

    @Test
    void testAddMultipleComponents() {
        diagram.addComponent("Component1");
        diagram.addComponent("Component2");
        diagram.addComponent("Component3");

        assertEquals(3, diagram.getComponents().size());
    }

    @Test
    void testAddComponentWithDuplicateName() {
        Component component1 = diagram.addComponent("DuplicateName");
        Component component2 = diagram.addComponent("DuplicateName");

        assertNotNull(component1);
        assertNotNull(component2);
        assertEquals(2, diagram.getComponents().size());
    }

    // =======================
    // Test Name and Constructor
    // =======================

    @Test
    void testGetName() {
        assertEquals("Test Diagram", diagram.getName());
    }

    // =======================
    // Test Abstract Methods
    // =======================

    @Test
    void testRenderDiagram() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        assertDoesNotThrow(() -> diagram.renderDiagram(mockGraphics));
    }

    // =======================
    // Test Serialization
    // =======================

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        diagram.addComponent("Component1");
        diagram.addComponent("Component2");

        // Serialize the object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(diagram);

        // Deserialize the object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        Diagram deserializedDiagram = (Diagram) objectIn.readObject();

        assertNotNull(deserializedDiagram);
        assertEquals("Test Diagram", deserializedDiagram.getName());
        assertEquals(2, deserializedDiagram.getComponents().size());
    }

    @Test
    void testObserversAreTransient() throws IOException, ClassNotFoundException {
        diagram.addObserver(mockObserver);

        // Serialize and deserialize
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(diagram);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        Diagram deserializedDiagram = (Diagram) objectIn.readObject();

        // Since observers are transient, they should be null after deserialization
        assertEquals(0, deserializedDiagram.getComponents().size());
    }
}
