package BusinessLogic.ClassDiagram.Properties;

import static org.junit.jupiter.api.Assertions.*;

import Utilities.Component.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AbstractClassPropertyTest {

    private Component mockComponent;

    @BeforeEach
    void setUp() {
        mockComponent = mock(Component.class);
    }

    @Test
    void testConstructorValidAbstractMethod() {
        String type = "Abstract Method";
        String value = "+ myMethod() : void";

        AbstractClassProperty property = new AbstractClassProperty(type, value, mockComponent);

        assertNotNull(property.getValue(), "Value should not be null");
        assertTrue(property.getValue().contains("<<abstract>>"),
                "Abstract Method should append '<<abstract>>'");
    }

    @Test
    void testConstructorInvalidAbstractMethod() {
        String type = "Abstract Method";
        String invalidValue = "invalidSyntax";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new AbstractClassProperty(type, invalidValue, mockComponent));

        assertTrue(exception.getMessage().contains("Invalid Abstract Method syntax"),
                "Exception message should indicate invalid syntax");
    }

    @Test
    void testAddValueWithValidAbstractMethod() {
        AbstractClassProperty property = new AbstractClassProperty("Abstract Method", "+ method() : void", mockComponent);
        String newValue = "+ newMethod() : String";

        property.addValue(newValue);

        assertTrue(property.getValue().contains("<<abstract>>"),
                "Abstract Method should append '<<abstract>>'");
        assertEquals("+ newMethod() : String <<abstract>>", property.getValue(),
                "Value should match the expected format");
    }

    @Test
    void testAddValueWithInvalidAbstractMethod() {
        AbstractClassProperty property = new AbstractClassProperty("Abstract Method", "+ method() : void", mockComponent);

        String invalidValue = "invalidSyntax";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> property.addValue(invalidValue));

        assertTrue(exception.getMessage().contains("Invalid Abstract Method syntax"),
                "Exception message should indicate invalid syntax");
    }

    @Test
    void testValidateInputWithValidAbstractMethod() {
        AbstractClassProperty property = new AbstractClassProperty("Abstract Method", "+ method() : void", mockComponent);

        assertDoesNotThrow(() -> property.validateInput("Abstract Method", "+ myMethod() : int"),
                "Valid Abstract Method syntax should not throw exceptions");
    }

    @Test
    void testValidateInputWithInvalidAbstractMethod() {
        AbstractClassProperty property = new AbstractClassProperty("Abstract Method", "+ method() : void", mockComponent);

        assertThrows(IllegalArgumentException.class,
                () -> property.validateInput("Abstract Method", "invalidSyntax"),
                "Invalid syntax should throw IllegalArgumentException");
    }
}
