package Utilities.Property;

import Utilities.Component.Component;
import Utilities.Property.CoordinateProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoordinatePropertyTest {

    @Test
    void testDefaultConstructor() {
        CoordinateProperty coordinateProperty = new CoordinateProperty();

        assertNull(coordinateProperty.getType(), "Default type should be null");
        assertNull(coordinateProperty.getValue(), "Default value should be null");
        assertNull(coordinateProperty.getComponent(), "Default component should be null");
    }


    @Test
    void testValidateInputValidValue() {
        CoordinateProperty coordinateProperty = new CoordinateProperty();

        assertDoesNotThrow(() -> coordinateProperty.validateInput("coordinate", "10"), "Valid value should not throw an exception");
    }

    @Test
    void testValidateInputInvalidValue() {
        CoordinateProperty coordinateProperty = new CoordinateProperty();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> coordinateProperty.validateInput("coordinate", "-5"), "Negative value should throw an exception");
        assertEquals("Enter a valid coordinate", exception.getMessage(), "Exception message should match expected");
    }

    @Test
    void testAddValueValid() {
        CoordinateProperty coordinateProperty = new CoordinateProperty();
        coordinateProperty.type = "coordinate";

        assertDoesNotThrow(() -> coordinateProperty.addValue("15"), "Valid value should not throw an exception");
        assertEquals("15", coordinateProperty.getValue(), "Value should be updated to the valid input");
    }

    @Test
    void testAddValueInvalid() {
        CoordinateProperty coordinateProperty = new CoordinateProperty();
        coordinateProperty.type = "coordinate";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> coordinateProperty.addValue("-10"), "Negative value should throw an exception");
        assertEquals("Enter a valid coordinate", exception.getCause().getMessage(), "Exception message should match expected");
    }

}
