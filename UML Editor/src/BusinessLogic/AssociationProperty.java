package BusinessLogic;

import Utilities.Component;
import Utilities.Property;
import Utilities.RelationComponent;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class AssociationProperty extends Property {
    // Interface for component validation strategy
    private interface ComponentValidationStrategy {
        boolean isValid(Component component);
    }

    // Strategy for non-relation component validation
    private static final ComponentValidationStrategy NON_RELATION_COMPONENT_STRATEGY =
            component -> component != null && !(component instanceof RelationComponent);

    /**
     * Constructor for AssociationProperty
     *
     * @param type Property type (Source or Target)
     * @param value Component name
     * @param component Associated relation component
     */
    public AssociationProperty(String type, String value, Component component) {
        super(type, value, component);
        processAssociation(type, value);
    }

    /**
     * Process association for Source or Target types
     *
     * @param type Property type
     * @param value Component name
     */
    private void processAssociation(String type, String value) {
        if ("Source".equals(type) || "Target".equals(type)) {
            RelationComponent relationComponent = (RelationComponent) associatedComponent;
            Component foundComponent = findComponentByName(value);

            validateComponentExists(type, foundComponent, value);
            updateRelationComponent(relationComponent, type, foundComponent);
        }
    }

    /**
     * Validate that the component exists
     *
     * @param type Property type
     * @param foundComponent Found component
     * @param value Component name
     */
    private void validateComponentExists(String type, Component foundComponent, String value) {
        if (foundComponent == null) {
            throw new IllegalStateException(type + " component not found: " + value);
        }
    }

    /**
     * Update the relation component based on type
     *
     * @param relationComponent Relation component to update
     * @param type Property type
     * @param foundComponent Component to set
     */
    private void updateRelationComponent(RelationComponent relationComponent, String type, Component foundComponent) {
        if ("Source".equals(type)) {
            relationComponent.setSource(foundComponent);
            removeSourcePropertyType(relationComponent);
        } else if ("Target".equals(type)) {
            relationComponent.setTarget(foundComponent);
        }
    }

    /**
     * Remove Source property type from relation component
     *
     * @param relationComponent Relation component to modify
     */
    private void removeSourcePropertyType(RelationComponent relationComponent) {
        relationComponent.removePropertyType("Source");
        System.out.println(relationComponent.getPropertiesTypes());
    }

    @Override
    public void validateInput(String type, String value) {
        // Only validate for Source and Target types
        if (("Source".equals(type) || "Target".equals(type))
                && !isValidComponentForAssociation(value)) {
            throw new IllegalArgumentException(
                    "Invalid component: " + value + " cannot be associated."
            );
        }
    }

    /**
     * Checks if a component is valid for association using strategy
     *
     * @param componentName Name of the component to validate
     * @return true if the component is valid
     */
    private boolean isValidComponentForAssociation(String componentName) {
        Component foundComponent = findComponentByName(componentName);
        return NON_RELATION_COMPONENT_STRATEGY.isValid(foundComponent);
    }

    /**
     * Finds a component by its name in the diagram
     *
     * @param componentName Name of the component to find
     * @return Component with matching name, or null if not found
     */
    private Component findComponentByName(String componentName) {
        return associatedComponent.getDiagram().getComponents().stream()
                .filter(component -> Objects.equals(component.getName(), componentName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addValue(String value) {
        // Validate input first
        validateInput(this.type, value);

        // Update the value
        this.value = value;

        // Process association
        processAssociation(type, value);
    }
}