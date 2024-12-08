package BusinessLogic.ClassDiagram.Components;

import Utilities.Diagram.Diagram;
import Utilities.Property.Property;
import Utilities.Property.CoordinateProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterfaceTest {

    private Interface testInterface;
    private Diagram mockDiagram;

    @BeforeEach
    void setUp() {
        // Mock the Diagram dependency
        mockDiagram = mock(Diagram.class);

        // Initialize the Interface object
        testInterface = new Interface("TestInterface", mockDiagram);
    }

    @Test
    void testConstructor_InitializesProperties() {
        // Verify name property
        Property nameProperty = testInterface.getProperties().stream()
                .filter(prop -> prop.gettype().equals("Interface Name"))
                .findFirst()
                .orElse(null);
        assertNotNull(nameProperty);
        assertEquals("TestInterface", nameProperty.getValue());

        // Verify width and height properties
        CoordinateProperty width = testInterface.getWidth();
        CoordinateProperty height = testInterface.getHeight();

        assertNotNull(width);
        assertNotNull(height);
        assertEquals(180, Integer.parseInt(width.getValue()));
        assertEquals(215, Integer.parseInt(height.getValue()));
    }

    @Test
    void testCreateProperty_ValidProperty() {
        // Add a valid "Method" property
        Property methodProperty = testInterface.createProperty("Method", "methodName()");

        // Verify the property was added
        assertTrue(testInterface.getProperties().contains(methodProperty));
        assertTrue(testInterface.getMethods().contains("methodName()"));
    }

    @Test
    void testCreateProperty_InvalidProperty() {
        // Attempt to create an invalid property type
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            testInterface.createProperty("InvalidType", "value");
        });

        // Verify exception message
        assertEquals("Invalid property type for Interface: InvalidType", exception.getMessage());
    }

    @Test
    void testRenderComponent_CallsGraphicsMethods() {
        // Mock the Graphics object
        Graphics mockGraphics = mock(Graphics.class);
        Graphics2D mockGraphics2D = mock(Graphics2D.class);

        // Ensure the Graphics object can be cast to Graphics2D
        when(mockGraphics.create()).thenReturn(mockGraphics2D);

        // Render the component
        testInterface.renderComponent(mockGraphics);

        // Verify rendering methods were called
        verify(mockGraphics2D).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        verify(mockGraphics2D).setColor(new Color(240, 240, 255));
        verify(mockGraphics2D).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(mockGraphics2D).drawRect(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testGetWidthAndHeight() {
        // Retrieve width and height properties
        CoordinateProperty width = testInterface.getWidth();
        CoordinateProperty height = testInterface.getHeight();

        // Verify their values
        assertEquals(180, Integer.parseInt(width.getValue()));
        assertEquals(215, Integer.parseInt(height.getValue()));
    }

    @Test
    void testAddMethod_ReflectsInProperties() {
        // Add a new method
        testInterface.createProperty("Method", "newMethod()");

        // Verify the method is present in the methods list
        List<String> methods = testInterface.getMethods();
        assertEquals(1, methods.size());
        assertTrue(methods.contains("newMethod()"));
    }

    @Test
    void testRenderHeader_CallsGraphicsMethods() {
        // Mock the Graphics object
        Graphics2D mockGraphics2D = mock(Graphics2D.class);

        // Call the private method using reflection
        try {
            var method = Interface.class.getDeclaredMethod("drawHeader", Graphics2D.class, int.class, int.class, int.class);
            method.setAccessible(true);
            method.invoke(testInterface, mockGraphics2D, 10, 10, 200);

            // Verify drawing methods were called
            verify(mockGraphics2D).setColor(new Color(220, 220, 250));
            verify(mockGraphics2D).fillRect(10, 10, 200, 35);
        } catch (Exception e) {
            fail("Reflection failed to access drawHeader method");
        }
    }

    @Test
    void testRenderMethods_CallsGraphicsMethods() {
        // Mock the Graphics object
        Graphics2D mockGraphics2D = mock(Graphics2D.class);

        // Add a method to render
        testInterface.createProperty("Method", "testMethod()");

        // Call the private method using reflection
        try {
            var method = Interface.class.getDeclaredMethod("drawMethods", Graphics2D.class, int.class, int.class, int.class, int.class);
            method.setAccessible(true);
            method.invoke(testInterface, mockGraphics2D, 10, 50, 200, 215);

            // Verify drawing methods were called
            verify(mockGraphics2D).setColor(Color.WHITE);
            verify(mockGraphics2D).fillRect(10, 50, 200, 215 - 35);
            verify(mockGraphics2D).setColor(Color.BLACK);
        } catch (Exception e) {
            fail("Reflection failed to access drawMethods method");
        }
    }
}
