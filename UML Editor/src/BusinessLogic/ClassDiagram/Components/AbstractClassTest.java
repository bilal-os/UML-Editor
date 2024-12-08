package BusinessLogic.ClassDiagram.Components;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Properties.AbstractClassProperty;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;
import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractClassTest {

    @Test
    void testAbstractClassConstructor() {
        // Test the default constructor
        AbstractClass abstractClass = new AbstractClass();
        assertNotNull(abstractClass);
    }

    @Test
    void testAbstractClassWithName() {
        Diagram mockDiagram = mock(Diagram.class); // Mock the Diagram dependency
        AbstractClass abstractClass = new AbstractClass("TestClass", mockDiagram);
        assertNotNull(abstractClass);
        assertEquals("TestClass", abstractClass.getName());  // Assuming getName() is inherited from Component
    }

    @Test
    void testCreateProperty() {
        Diagram mockDiagram = mock(Diagram.class);
        AbstractClass abstractClass = new AbstractClass("TestClass", mockDiagram);

        Property property = abstractClass.createProperty("Attribute", "name: String");
        assertNotNull(property);
        assertTrue(property instanceof AbstractClassProperty);
        assertEquals("Attribute", property.gettype());
        assertEquals("name: String", property.getValue());
    }

    @Test
    void testCreatePropertyWithInvalidType() {
        Diagram mockDiagram = mock(Diagram.class);
        AbstractClass abstractClass = new AbstractClass("TestClass", mockDiagram);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            abstractClass.createProperty("InvalidType", "SomeValue");
        });
        assertEquals("Invalid property type for Abstract Class: InvalidType", thrown.getMessage());
    }

    @Test
    void testRenderComponent() {
        AbstractClass abstractClass = new AbstractClass("TestClass", mock(Diagram.class));

        // Mocking Graphics2D
        Graphics2D graphics = mock(Graphics2D.class);

        // Call renderComponent to check that it doesn't throw any exceptions
        assertDoesNotThrow(() -> {
            abstractClass.renderComponent(graphics);
        });

        // Verify that the graphics methods were called during rendering
        verify(graphics, atLeastOnce()).setRenderingHint(any(), any());
        verify(graphics, atLeastOnce()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testAddAttributesAndMethods() {
        Diagram mockDiagram = mock(Diagram.class);
        AbstractClass abstractClass = new AbstractClass("TestClass", mockDiagram);

        abstractClass.createProperty("Attribute", "name: String");
        abstractClass.createProperty("Method", "getName() : String");

        assertEquals(2, abstractClass.getProperties().size());
        assertTrue(abstractClass.getProperties().get(0) instanceof AbstractClassProperty);
        assertTrue(abstractClass.getProperties().get(1) instanceof AbstractClassProperty);
    }

    @Test
    void testGetWidthAndHeight() {
        Diagram mockDiagram = mock(Diagram.class);
        AbstractClass abstractClass = new AbstractClass("TestClass", mockDiagram);

        // Test that width and height properties are correctly initialized
        assertEquals(180, abstractClass.getWidth().getValue()); // Assuming the default value is 180
        assertEquals(215, abstractClass.getHeight().getValue()); // Assuming the default value is 215
    }
}
