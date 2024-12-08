package BusinessLogic.ClassDiagram.Properties;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Utilities.Component.Component;

import static org.mockito.Mockito.*;

class EnumerationPropertyTest {

    private Component mockComponent;

    @BeforeEach
    void setUp() {
        mockComponent = mock(Component.class); // Mock the Component dependency
    }

    @Test
    void testConstructorValidEnumerationName() {
        String type = "Enumeration Name";
        String validValue = "Color";

        assertDoesNotThrow(() -> new EnumerationProperty(type, validValue, mockComponent),
                "Valid enumeration name should not throw an exception");
    }

    @Test
    void testConstructorInvalidEnumerationName() {
        String type = "Enumeration Name";
        String invalidValue = "color"; // Starts with lowercase

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new EnumerationProperty(type, invalidValue, mockComponent));

        assertTrue(exception.getMessage().contains("Invalid Enumeration Name"),
                "Exception message should indicate invalid enumeration name syntax");
    }

    @Test
    void testConstructorInvalidEnumerationType() {
        String type = "Unknown Type";
        String value = "Color";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new EnumerationProperty(type, value, mockComponent));

        assertTrue(exception.getMessage().contains("Unknown property type for Enumeration"),
                "Exception message should indicate unknown property type");
    }

    @Test
    void testAddValueValidValue() {
        EnumerationProperty property = new EnumerationProperty("Value", "RED", mockComponent);
        String newValue = "BLUE";

        assertDoesNotThrow(() -> property.addValue(newValue),
                "Valid enumeration value should not throw an exception");
        assertEquals(newValue, property.getValue(), "Value should be updated with the new enumeration value");
    }

    @Test
    void testAddValueInvalidValue() {
        EnumerationProperty property = new EnumerationProperty("Value", "RED", mockComponent);
        String invalidValue = "red"; // Lowercase value

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> property.addValue(invalidValue));

        assertTrue(exception.getMessage().contains("Invalid Enumeration Value"),
                "Exception message should indicate invalid enumeration value syntax");
    }

    @Test
    void testIsValidNameValid() {
        EnumerationProperty property = new EnumerationProperty();
        assertTrue(property.isValidName("Color"), "Valid enumeration name should return true");
        assertTrue(property.isValidName("Color_123"), "Enumeration name with underscore and numbers should return true");
    }

    @Test
    void testIsValidNameInvalid() {
        EnumerationProperty property = new EnumerationProperty();
        assertFalse(property.isValidName("color"), "Enumeration name starting with lowercase should return false");
        assertFalse(property.isValidName("123Color"), "Enumeration name starting with a number should return false");
        assertFalse(property.isValidName(""), "Empty enumeration name should return false");
        assertFalse(property.isValidName(null), "Null enumeration name should return false");
    }

    @Test
    void testIsValidValueValid() {
        EnumerationProperty property = new EnumerationProperty();
        assertTrue(property.isValidValue("RED"), "Valid enumeration value should return true");
        assertTrue(property.isValidValue("VALUE_123"), "Enumeration value with underscore and numbers should return true");
    }

    @Test
    void testIsValidValueInvalid() {
        EnumerationProperty property = new EnumerationProperty();
        assertFalse(property.isValidValue("red"), "Enumeration value in lowercase should return false");
        assertFalse(property.isValidValue("123VALUE"), "Enumeration value starting with number should return false");
        assertFalse(property.isValidValue(""), "Empty enumeration value should return false");
        assertFalse(property.isValidValue(null), "Null enumeration value should return false");
    }
}
