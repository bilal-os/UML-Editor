package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Properties.ClassProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;
import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassTest {

    @Test
    void testClassConstructor() {
        // Test the default constructor
        Class cls = new Class();
        assertNotNull(cls);
    }

    @Test
    void testClassConstructorWithName() {
        Diagram mockDiagram = mock(Diagram.class); // Mock the Diagram dependency
        Class cls = new Class("TestClass", mockDiagram);
        assertNotNull(cls);
        assertEquals("TestClass", cls.getName());  // Assuming getName() is inherited from Component
    }

    @Test
    void testCreateProperty() {
        Diagram mockDiagram = mock(Diagram.class);
        Class cls = new Class("TestClass", mockDiagram);

        Property property = cls.createProperty("Attribute", "name: String");
        assertNotNull(property);
        assertTrue(property instanceof ClassProperty);
        assertEquals("Attribute", property.gettype());
        assertEquals("name: String", property.getValue());
    }

    @Test
    void testCreatePropertyWithInvalidType() {
        Diagram mockDiagram = mock(Diagram.class);
        Class cls = new Class("TestClass", mockDiagram);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cls.createProperty("InvalidType", "SomeValue");
        });
        assertEquals("Invalid property type: InvalidType", thrown.getMessage());
    }

    @Test
    void testRenderComponent() {
        Class cls = new Class("TestClass", mock(Diagram.class));

        // Mocking Graphics2D
        Graphics2D graphics = mock(Graphics2D.class);

        // Call renderComponent to check that it doesn't throw any exceptions
        assertDoesNotThrow(() -> {
            cls.renderComponent(graphics);
        });

        // Verify that the graphics methods were called during rendering
        verify(graphics, atLeastOnce()).setRenderingHint(any(), any());
        verify(graphics, atLeastOnce()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testAddAttributesAndMethods() {
        Diagram mockDiagram = mock(Diagram.class);
        Class cls = new Class("TestClass", mockDiagram);

        cls.createProperty("Attribute", "name: String");
        cls.createProperty("Method", "getName() : String");

        assertEquals(2, cls.getProperties().size());
        assertTrue(cls.getProperties().get(0) instanceof ClassProperty);
        assertTrue(cls.getProperties().get(1) instanceof ClassProperty);
    }

    @Test
    void testGetWidthAndHeight() {
        Diagram mockDiagram = mock(Diagram.class);
        Class cls = new Class("TestClass", mockDiagram);

        // Test that width and height properties are correctly initialized
        assertEquals(180, cls.getWidth().getValue()); // Assuming the default value is 180
        assertEquals(215, cls.getHeight().getValue()); // Assuming the default value is 215
    }
}
