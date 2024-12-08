package BusinessLogic.ClassDiagram.Properties;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import Utilities.Component.Component;

import static org.mockito.Mockito.*;

class ClassPropertyTest {

    private Component mockComponent;

    @BeforeEach
    void setUp() {
        mockComponent = mock(Component.class); // Mock the Component dependency
    }

    @Test
    void testConstructorValidClassName() {
        String type = "Class Name";
        String value = "Person";

        assertDoesNotThrow(() -> new ClassProperty(type, value, mockComponent),
                "Valid class name should not throw an exception");
    }

    @Test
    void testConstructorInvalidClassName() {
        String type = "Class Name";
        String invalidValue = "person"; // Starts with lowercase

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ClassProperty(type, invalidValue, mockComponent));

        assertTrue(exception.getMessage().contains("Invalid syntax for Name"),
                "Exception message should indicate invalid class name syntax");
    }

    @Test
    void testAddValueValidAttribute() {
        ClassProperty property = new ClassProperty("Attribute", "+ age : int", mockComponent);
        String newValue = "- name : String";

        assertDoesNotThrow(() -> property.addValue(newValue),
                "Valid attribute syntax should not throw an exception");
        assertEquals(newValue, property.getValue(), "Value should be updated with the new attribute");
    }

    @Test
    void testAddValueInvalidAttribute() {
        ClassProperty property = new ClassProperty("Attribute", "+ age : int", mockComponent);
        String invalidValue = "invalidSyntax";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> property.addValue(invalidValue));

        assertTrue(exception.getMessage().contains("Invalid syntax for Attribute"),
                "Exception message should indicate invalid attribute syntax");
    }

    @Test
    void testAddValueValidMethod() {
        ClassProperty property = new ClassProperty("Method", "+ getName() : String", mockComponent);
        String newValue = "- setName(String name) : void";

        assertDoesNotThrow(() -> property.addValue(newValue),
                "Valid method syntax should not throw an exception");
        assertEquals(newValue, property.getValue(), "Value should be updated with the new method");
    }

    @Test
    void testAddValueInvalidMethod() {
        ClassProperty property = new ClassProperty("Method", "+ getName() : String", mockComponent);
        String invalidValue = "invalidSyntax";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> property.addValue(invalidValue));

        assertTrue(exception.getMessage().contains("Invalid syntax for Method"),
                "Exception message should indicate invalid method syntax");
    }

    @Test
    void testIsValidNameValid() {
        ClassProperty property = new ClassProperty();
        assertTrue(property.isValidName("Person"), "Valid class name should return true");
        assertTrue(property.isValidName("Person123"), "Class name with numbers should return true");
        assertTrue(property.isValidName("Person_Name"), "Class name with underscore should return true");
    }

    @Test
    void testIsValidNameInvalid() {
        ClassProperty property = new ClassProperty();
        assertFalse(property.isValidName("person"), "Class name starting with lowercase should return false");
        assertFalse(property.isValidName("123Person"), "Class name starting with a number should return false");
        assertFalse(property.isValidName(""), "Empty class name should return false");
        assertFalse(property.isValidName(null), "Null class name should return false");
    }

    @Test
    void testIsValidAttributeValid() {
        ClassProperty property = new ClassProperty();
        assertTrue(property.isValidAttribute("+ age : int"), "Valid attribute syntax should return true");
        assertTrue(property.isValidAttribute("- name : String"), "Valid attribute syntax with different visibility should return true");
    }

    @Test
    void testIsValidAttributeInvalid() {
        ClassProperty property = new ClassProperty();
        assertFalse(property.isValidAttribute("age : int"), "Missing visibility modifier should return false");
        assertFalse(property.isValidAttribute("+ age int"), "Missing colon should return false");
        assertFalse(property.isValidAttribute("+ : int"), "Missing name should return false");
    }

    @Test
    void testIsValidMethodValid() {
        ClassProperty property = new ClassProperty();
        assertTrue(property.isValidMethod("+ getName() : String"), "Valid method syntax should return true");
        assertTrue(property.isValidMethod("- setName(String name) : void"), "Valid method syntax with parameters should return true");
    }

    @Test
    void testIsValidMethodInvalid() {
        ClassProperty property = new ClassProperty();
        assertFalse(property.isValidMethod("getName() : String"), "Missing visibility modifier should return false");
        assertFalse(property.isValidMethod("+ getName() String"), "Missing colon should return false");
        assertFalse(property.isValidMethod("+ getName : String"), "Missing parentheses should return false");
    }
}
