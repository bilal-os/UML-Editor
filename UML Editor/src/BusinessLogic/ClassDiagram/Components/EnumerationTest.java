package BusinessLogic.ClassDiagram.Components;

import Utilities.Diagram.Diagram;
import Utilities.Property.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnumerationTest {
    private Enumeration enumeration;
    private Diagram mockDiagram;

    @BeforeEach
    void setUp() {
        // Mock the Diagram dependency
        mockDiagram = mock(Diagram.class);

        // Create an instance of Enumeration
        enumeration = new Enumeration("TestEnumeration", mockDiagram);
    }

    @Test
    void testConstructorInitialization() {
        // Verify default properties
        assertEquals("TestEnumeration", enumeration.getName(), "Enumeration name should match the constructor input");
        assertNotNull(enumeration.getWidth(), "Width property should be initialized");
        assertNotNull(enumeration.getHeight(), "Height property should be initialized");

        // Verify default dimensions
        assertEquals("180", enumeration.getWidth().getValue(), "Default width should be 180");
        assertEquals("215", enumeration.getHeight().getValue(), "Default height should be 215");
    }

    @Test
    void testCreateValidProperty() {
        // Create a valid property of type "Value"
        Property valueProperty = enumeration.createProperty("Value", "TestValue");

        // Verify property is added and correctly set
        assertNotNull(valueProperty, "Property should not be null");
        assertEquals("Value", valueProperty.gettype(), "Property type should match");
        assertEquals("TestValue", valueProperty.getValue(), "Property value should match");

        // Verify the property is part of the enumeration
        assertTrue(enumeration.getProperties().contains(valueProperty), "Property should be added to the enumeration");
    }

    @Test
    void testCreateInvalidProperty() {
        // Try creating an invalid property
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            enumeration.createProperty("InvalidType", "TestValue");
        });

        // Verify the exception message
        assertEquals("Invalid property type for Enumeration: InvalidType", exception.getMessage());
    }

    @Test
    void testRenderComponent() {
        // Prepare a mock Graphics object
        Graphics2D mockGraphics = mock(Graphics2D.class);

        // Call the render method
        enumeration.renderComponent(mockGraphics);

        // Verify key rendering operations
        verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
        verify(mockGraphics, atLeastOnce()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(mockGraphics, atLeastOnce()).drawRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(mockGraphics, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    void testDrawHeader() {
        // Use a real BufferedImage to test drawing without mocks
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        // Call the render method to draw the header
        enumeration.renderComponent(graphics);

        // No exceptions should be thrown
        graphics.dispose();
    }

    @Test
    void testSetAndGetDimensions() {
        // Modify width and height properties
        enumeration.getWidth().setValue("300");
        enumeration.getHeight().setValue("400");

        // Verify the updated dimensions
        assertEquals("300", enumeration.getWidth().getValue(), "Width should be updated to 300");
        assertEquals("400", enumeration.getHeight().getValue(), "Height should be updated to 400");
    }
}
