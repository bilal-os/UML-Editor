package Utilities.Component;

import Utilities.Diagram.Diagram;
import Utilities.Property.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RelationComponentTest {
    private RelationComponent relationComponent;
    private Component mockSource;
    private Component mockTarget;
    private Diagram mockDiagram;

    @BeforeEach
    void setUp() {
        mockDiagram = mock(Diagram.class);

        // Anonymous subclass for abstract class RelationComponent
        relationComponent = new RelationComponent(mockDiagram) {
            @Override
            public Property createProperty(String type, String value) throws IllegalArgumentException {
                return new Property(type, value) {
                    @Override
                    public void validateInput(String type, String value) throws IllegalArgumentException {
                        if (type == null || type.isEmpty()) {
                            throw new IllegalArgumentException("Type cannot be null or empty");
                        }
                        if (value == null) {
                            throw new IllegalArgumentException("Value cannot be null");
                        }
                    }

                    @Override
                    public void addValue(String value) throws IllegalArgumentException {
                        if (value == null || value.isEmpty()) {
                            throw new IllegalArgumentException("Value cannot be null or empty");
                        }
                        this.value = value;
                    }
                };
            }

            @Override
            public void renderComponent(Graphics g) {
                // Empty implementation for abstract method
            }
        };

        mockSource = mock(Component.class);
        mockTarget = mock(Component.class);
    }

    @Test
    void testSetSource() {
        relationComponent.setSource(mockSource);
        assertEquals(mockSource, relationComponent.getSource());
    }

    @Test
    void testSetTarget() {
        relationComponent.setTarget(mockTarget);
        assertEquals(mockTarget, relationComponent.getTarget());
    }

    @Test
    void testGetSource() {
        relationComponent.setSource(mockSource);
        assertEquals(mockSource, relationComponent.getSource());
    }

    @Test
    void testGetTarget() {
        relationComponent.setTarget(mockTarget);
        assertEquals(mockTarget, relationComponent.getTarget());
    }

    @Test
    void testGetPropertyValue() {
        relationComponent.createProperty("TestType", "TestValue");
        String value = relationComponent.getPropertyValue("TestType");
        assertEquals("TestValue", value);
    }

    @Test
    void testGetPropertyValueForNonExistentProperty() {
        String value = relationComponent.getPropertyValue("NonExistentProperty");
        assertNull(value);
    }

    @Test
    void testCalculateIntersectionPoint() {
        Point result = relationComponent.calculateIntersectionPoint(0, 0, 100, 100, 200, 200, 100, 100);
        assertNotNull(result);

        // Assert the intersection point is correct
        Point expected = new Point(100, 50); // Hypothetical expected point
        assertEquals(expected.x, result.x);
        assertEquals(expected.y, result.y);
    }

    @Test
    void testCalculateIntersectionPointWithNegativeCoordinates() {
        Point result = relationComponent.calculateIntersectionPoint(-50, -50, 100, 100, 50, 50, 50, 50);
        assertNotNull(result);
    }

    @Test
    void testRenderNameWithValidData() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        relationComponent.renderName(mockGraphics, new Point(0, 0), new Point(100, 100), Math.PI / 4, "Test Name");

        verify(mockGraphics, atLeastOnce()).drawString(eq("Test Name"), anyInt(), anyInt());
    }

    @Test
    void testRenderNameWithEmptyString() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        relationComponent.renderName(mockGraphics, new Point(0, 0), new Point(100, 100), Math.PI / 4, "");

        verify(mockGraphics, never()).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    void testRenderNameWithNullString() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        relationComponent.renderName(mockGraphics, new Point(0, 0), new Point(100, 100), Math.PI / 4, null);

        verify(mockGraphics, never()).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    void testRenderMultiplicityWithValidData() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        relationComponent.renderMultiplicity(mockGraphics, new Point(50, 50), Math.PI / 4, "1..*");

        verify(mockGraphics, atLeastOnce()).drawString(eq("1..*"), anyInt(), anyInt());
    }

    @Test
    void testRenderMultiplicityWithEmptyString() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        relationComponent.renderMultiplicity(mockGraphics, new Point(50, 50), Math.PI / 4, "");

        verify(mockGraphics, never()).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    void testRenderMultiplicityWithNullString() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        relationComponent.renderMultiplicity(mockGraphics, new Point(50, 50), Math.PI / 4, null);

        verify(mockGraphics, never()).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    void testCreatePropertyWithValidData() {
        Property property = relationComponent.createProperty("Type", "Value");
        assertNotNull(property);
        assertEquals("Type", property.gettype());
        assertEquals("Value", property.getValue());
    }

    @Test
    void testCreatePropertyWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> relationComponent.createProperty("", "Value"));
    }

    @Test
    void testCreatePropertyWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> relationComponent.createProperty("Type", null));
    }
}
