package Utilities.Component;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationTypeTest {

    @Test
    void testEnumValues() {
        assertEquals(RelationType.ASSOCIATION, RelationType.valueOf("ASSOCIATION"));
        assertEquals(RelationType.INHERITANCE, RelationType.valueOf("INHERITANCE"));
        assertEquals(RelationType.DEPENDENCY, RelationType.valueOf("DEPENDENCY"));
        assertEquals(RelationType.COMPOSITION, RelationType.valueOf("COMPOSITION"));
    }

    @Test
    void testEnumNames() {
        String[] expectedNames = {"ASSOCIATION", "INHERITANCE", "DEPENDENCY", "COMPOSITION"};
        String[] actualNames = new String[RelationType.values().length];
        for (int i = 0; i < RelationType.values().length; i++) {
            actualNames[i] = RelationType.values()[i].name();
        }
        assertArrayEquals(expectedNames, actualNames);
    }
}
