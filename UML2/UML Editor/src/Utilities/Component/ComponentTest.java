package Utilities.Component;

import Utilities.Diagram.Diagram;
import Utilities.Property.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentTest {
    private Component component;
    private Diagram mockDiagram;
    private ComponentObserver mockObserver;

    @BeforeEach
    void setUp() {
        mockDiagram = mock(Diagram.class);

        // Anonymous subclass of Component
        component = new Component(mockDiagram) {
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

        mockObserver = mock(ComponentObserver.class);
    }

    @Test
    void testAddObserver() {
        component.addObserver(mockObserver);
        component.notifyObservers();
        verify(mockObserver, atLeastOnce()).updateFromComponent(component);
    }

    @Test
    void testRemoveObserver() {
        component.addObserver(mockObserver);
        component.removeObserver(mockObserver);
        component.notifyObservers();
        verify(mockObserver, never()).updateFromComponent(component);
    }

    @Test
    void testAddProperty() {
        Property property = component.addProperty("TestType", "TestValue");
        assertNotNull(property);
        assertEquals("TestType", property.gettype());
        assertEquals("TestValue", property.getValue());
    }

    @Test
    void testAddPropertyWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> component.addProperty("", "TestValue"));
    }

    @Test
    void testAddPropertyWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> component.addProperty("TestType", null));
    }

    @Test
    void testRemoveProperty() {
        Property property = component.addProperty("TestType", "TestValue");
        component.removeProperty(property);
        assertFalse(component.getProperties().contains(property));
    }

    @Test
    void testRemovePropertyType() {
        component.getPropertiesTypes().add("TestType");
        component.removePropertyType("TestType");
        assertFalse(component.getPropertiesTypes().contains("TestType"));
    }

    @Test
    void testSetDiagram() {
        Diagram newDiagram = mock(Diagram.class);
        component.setDiagram(newDiagram);
        assertEquals(newDiagram, component.getDiagram());
    }

    @Test
    void testGetName() {
        Property nameProperty = component.createProperty("Name", "TestComponent");
        component.getProperties().add(nameProperty);
        assertEquals("TestComponent", component.getName());
    }

    @Test
    void testGetNameWhenNamePropertyDoesNotExist() {
        // No "Name" property is added
        assertEquals("", component.getName());
    }

    @Test
    void testGetXCoordinate() {
        Property xCoordinate = component.getXCoordinate();
        assertNotNull(xCoordinate);
        assertEquals("X Coordinate", xCoordinate.gettype());
    }

    @Test
    void testGetYCoordinate() {
        Property yCoordinate = component.getYCoordinate();
        assertNotNull(yCoordinate);
        assertEquals("Y Coordinate", yCoordinate.gettype());
    }

    @Test
    void testCreateProperty() {
        Property property = component.createProperty("CustomType", "CustomValue");
        assertNotNull(property);
        assertEquals("CustomType", property.gettype());
        assertEquals("CustomValue", property.getValue());
    }

    @Test
    void testCreatePropertyWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> component.createProperty("", "CustomValue"));
    }

    @Test
    void testCreatePropertyWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> component.createProperty("CustomType", null));
    }

    @Test
    void testAddObserverAndNotify() {
        ComponentObserver observer1 = mock(ComponentObserver.class);
        ComponentObserver observer2 = mock(ComponentObserver.class);

        component.addObserver(observer1);
        component.addObserver(observer2);

        component.notifyObservers();

        verify(observer1, times(1)).updateFromComponent(component);
        verify(observer2, times(1)).updateFromComponent(component);
    }

    @Test
    void testRemoveObserverAndNotify() {
        ComponentObserver observer1 = mock(ComponentObserver.class);
        ComponentObserver observer2 = mock(ComponentObserver.class);

        component.addObserver(observer1);
        component.addObserver(observer2);
        component.removeObserver(observer1);

        component.notifyObservers();

        verify(observer1, never()).updateFromComponent(component);
        verify(observer2, times(1)).updateFromComponent(component);
    }
}
