package BusinessLogic.CodeGeneration;

import BusinessLogic.ClassDiagram.Components.*;
import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Components.Enumeration;
import BusinessLogic.ClassDiagram.Components.Package;
import BusinessLogic.ClassDiagram.Components.Relations.Aggregation;
import BusinessLogic.ClassDiagram.Components.Relations.Association;
import BusinessLogic.ClassDiagram.Components.Relations.Composition;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClassDiagramCodeGenerator implements DiagramCodeGenerator {
    // Regular expressions for parsing attributes and methods
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("^([-+#])\\s*(\\w+)\\s*:\\s*(\\w+)$");
    private static final Pattern METHOD_PATTERN = Pattern.compile("^([-+#])\\s*(\\w+)\\s*\\(([^)]*)\\)\\s*:\\s*(\\w+)(?:\\s*<<abstract>>)?$");

    // Code generation entry point
    public void generateDiagramCode(String fileLocation, Diagram diagram) {
        validateInputs(fileLocation, diagram);

        for (Component component : diagram.getComponents()) {
            generateCodeForComponent(component, fileLocation);
        }
    }

    // Dispatch method for different component types
    private void generateCodeForComponent(Component component, String fileLocation) {
        if (component instanceof AbstractClass abstractClass) {
            generateAbstractClassCode(abstractClass, fileLocation);
        } else if (component instanceof Class classComponent) {
            generateClassCode(classComponent, fileLocation);
        } else if (component instanceof Interface interfaceComponent) {
            generateInterfaceCode(interfaceComponent, fileLocation);
        } else if (component instanceof Enumeration enumeration) {
            generateEnumerationCode(enumeration, fileLocation);
        }
    }

    // Package name determination
    private String determinePackageName(Component component) {
        for (Component pkg : component.getDiagram().getComponents()) {
            if (pkg instanceof Package packageComponent) {
                List<Component> containedComponents = packageComponent.getContainedComponents();
                if (containedComponents.contains(component)) {
                    return getPackageNameProperty(packageComponent);
                }
            }
        }
        return "";
    }

    // Extract package name from package component
    private String getPackageNameProperty(Package packageComponent) {
        return packageComponent.getProperties().stream()
                .filter(prop -> "Package Name".equals(prop.gettype()))
                .findFirst()
                .map(Property::getValue)
                .orElse("");
    }

    // Enumeration code generation
    private void generateEnumerationCode(Enumeration enumeration, String baseFileLocation) {
        String enumName = getComponentName(enumeration, "Enumeration Name", "UnknownEnumeration");
        String packageName = determinePackageName(enumeration);

        StringBuilder enumCode = new StringBuilder();
        appendPackageDeclaration(enumCode, packageName);
        appendEnumDeclaration(enumCode, enumName, enumeration);

        writeToFile(baseFileLocation, enumName + ".java", enumCode.toString());
    }

    // Append package declaration to code
    private void appendPackageDeclaration(StringBuilder code, String packageName) {
        if (!packageName.isEmpty()) {
            code.append("package ").append(packageName).append(";\n\n");
        } else {
            code.append("// TODO: Add package declaration\n");
        }
    }

    // Append enum declaration and values
    private void appendEnumDeclaration(StringBuilder enumCode, String enumName, Enumeration enumeration) {
        enumCode.append("public enum ").append(enumName).append(" {\n");

        List<String> enumValues = getEnumValues(enumeration);
        for (int i = 0; i < enumValues.size(); i++) {
            enumCode.append("    ").append(enumValues.get(i));
            if (i < enumValues.size() - 1) {
                enumCode.append(",");
            }
            enumCode.append("\n");
        }

        enumCode.append("}\n");
    }

    // Get enum values from properties
    private List<String> getEnumValues(Enumeration enumeration) {
        return enumeration.getProperties().stream()
                .filter(prop -> "Value".equals(prop.gettype()))
                .map(Property::getValue)
                .toList();
    }

    // Interface code generation
    private void generateInterfaceCode(Interface interfaceComponent, String baseFileLocation) {
        String interfaceName = getComponentName(interfaceComponent, "Interface Name", "UnknownInterface");
        String packageName = determinePackageName(interfaceComponent);

        StringBuilder interfaceCode = new StringBuilder();
        appendPackageDeclaration(interfaceCode, packageName);
        appendInterfaceDeclaration(interfaceCode, interfaceName, interfaceComponent);

        writeToFile(baseFileLocation, interfaceName + ".java", interfaceCode.toString());
    }

    // Append interface declaration and methods
    private void appendInterfaceDeclaration(StringBuilder interfaceCode, String interfaceName, Interface interfaceComponent) {
        interfaceCode.append("public interface ").append(interfaceName).append(" {\n");

        List<String> methods = getMethods(interfaceComponent);
        for (String methodSignature : methods) {
            generateInterfaceMethod(interfaceCode, methodSignature);
        }

        interfaceCode.append("}\n");
    }

    // Get methods from interface properties
    private List<String> getMethods(Interface interfaceComponent) {
        return interfaceComponent.getProperties().stream()
                .filter(prop -> "Method".equals(prop.gettype()))
                .map(Property::getValue)
                .toList();
    }

    // Generate interface method signature
    private void generateInterfaceMethod(StringBuilder interfaceCode, String methodSignature) {
        Matcher matcher = METHOD_PATTERN.matcher(methodSignature.trim());
        if (!matcher.matches()) {
            System.err.println("Warning: Invalid method format - " + methodSignature);
            return;
        }

        String methodName = matcher.group(2);
        String parameters = matcher.group(3);
        String returnType = matcher.group(4);

        interfaceCode.append("    ")
                .append(returnType)
                .append(" ")
                .append(methodName)
                .append("(")
                .append(parameters)
                .append(");\n");
    }

    // Class code generation (regular and abstract)
    private void generateClassCode(Class classComponent, String baseFileLocation) {
        generateClassCodeBase(classComponent, baseFileLocation, false);
    }

    private void generateAbstractClassCode(AbstractClass abstractClass, String baseFileLocation) {
        generateClassCodeBase(abstractClass, baseFileLocation, true);
    }

    // Base method for generating class code
    private void generateClassCodeBase(Component classComponent, String baseFileLocation, boolean isAbstract) {
        String className = getComponentName(classComponent, "Class Name", "UnknownClass");
        String packageName = determinePackageName(classComponent);

        StringBuilder classCode = new StringBuilder();
        appendPackageDeclaration(classCode, packageName);
        appendClassDeclaration(classCode, className, classComponent, isAbstract);

        writeToFile(baseFileLocation, className + ".java", classCode.toString());
    }


    // Collect and categorize attributes and methods
    private AttributeMethodCollections collectAttributesAndMethods(Component classComponent) {
        AttributeMethodCollections collections = new AttributeMethodCollections();

        for (Property prop : classComponent.getProperties()) {
            switch (prop.gettype()) {
                case "Attribute" -> parseAttribute(prop.getValue(),
                        collections.privateAttributes,
                        collections.protectedAttributes,
                        collections.publicAttributes);
                case "Method" -> parseMethod(prop.getValue(),
                        collections.privateMethods,
                        collections.protectedMethods,
                        collections.publicMethods,
                        false);
                case "Abstract Method" -> parseMethod(prop.getValue(),
                        collections.privateMethods,
                        collections.protectedMethods,
                        collections.publicMethods,
                        true);
            }
        }

        return collections;
    }


    // Existing helper methods for parsing and generating methods/attributes
    // (parseAttribute, parseMethod, generateAttributes, generateMethods remain the same)

    // Write code to file
    private void writeToFile(String baseLocation, String fileName, String content) {
        try {
            Path directory = Paths.get(baseLocation);
            Files.createDirectories(directory);

            Path filePath = directory.resolve(fileName);
            Files.write(filePath, content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Generated file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing file: " + fileName);
            e.printStackTrace();
            throw new RuntimeException("Failed to write file", e);
        }
    }

    // Utility methods for input validation and name extraction
    private void validateInputs(String fileLocation, Diagram diagram) {
        if (fileLocation == null || fileLocation.isEmpty()) {
            throw new IllegalArgumentException("File location must be specified");
        }

        if (diagram == null) {
            throw new IllegalArgumentException("Diagram cannot be null");
        }
    }

    // Generic method to get component name based on property type
    private String getComponentName(Component component, String propertyType, String defaultName) {
        return component.getProperties().stream()
                .filter(prop -> propertyType.equals(prop.gettype()))
                .findFirst()
                .map(Property::getValue)
                .orElse(defaultName);
    }

    // Helper class to collect and organize attributes and methods
    private static class AttributeMethodCollections {
        Map<String, String> privateAttributes = new HashMap<>();
        Map<String, String> protectedAttributes = new HashMap<>();
        Map<String, String> publicAttributes = new HashMap<>();

        Map<String, MethodInfo> privateMethods = new HashMap<>();
        Map<String, MethodInfo> protectedMethods = new HashMap<>();
        Map<String, MethodInfo> publicMethods = new HashMap<>();
    }

    // Existing inner class for method information
    private static class MethodInfo {
        String name;
        String parameters;
        String returnType;
        boolean isAbstract;

        MethodInfo(String name, String parameters, String returnType, boolean isAbstract) {
            this.name = name;
            this.parameters = parameters;
            this.returnType = returnType;
            this.isAbstract = isAbstract;
        }
    }


    // Parse attribute and add to appropriate visibility map
    private void parseAttribute(String attributeValue,
                                Map<String, String> privateAttributes,
                                Map<String, String> protectedAttributes,
                                Map<String, String> publicAttributes) {
        // Validate attribute format using predefined pattern
        Matcher matcher = ATTRIBUTE_PATTERN.matcher(attributeValue.trim());
        if (!matcher.matches()) {
            System.err.println("Warning: Invalid attribute format - " + attributeValue);
            return;
        }

        // Extract visibility, name, and type
        String visibility = matcher.group(1);
        String name = matcher.group(2);
        String type = matcher.group(3);

        // Add to appropriate visibility map
        switch (visibility) {
            case "-" -> privateAttributes.put(name, type);
            case "#" -> protectedAttributes.put(name, type);
            case "+" -> publicAttributes.put(name, type);
            default -> System.err.println("Unknown visibility: " + visibility);
        }
    }

    // Parse method and add to appropriate visibility map
    private void parseMethod(String methodValue,
                             Map<String, MethodInfo> privateMethods,
                             Map<String, MethodInfo> protectedMethods,
                             Map<String, MethodInfo> publicMethods,
                             boolean isAbstract) {
        // Validate method format using predefined pattern
        Matcher matcher = METHOD_PATTERN.matcher(methodValue.trim());
        if (!matcher.matches()) {
            System.err.println("Warning: Invalid method format - " + methodValue);
            return;
        }

        // Extract method details
        String visibility = matcher.group(1);
        String methodName = matcher.group(2);
        String parameters = matcher.group(3);
        String returnType = matcher.group(4);

        // Create method info
        MethodInfo methodInfo = new MethodInfo(methodName, parameters, returnType, isAbstract);

        // Add to appropriate visibility map
        switch (visibility) {
            case "-" -> privateMethods.put(methodName, methodInfo);
            case "#" -> protectedMethods.put(methodName, methodInfo);
            case "+" -> publicMethods.put(methodName, methodInfo);
            default -> System.err.println("Unknown visibility: " + visibility);
        }
    }

    private void generateAggregationReferences(StringBuilder classCode, Component classComponent) {
        // Find all aggregations where this component is the source
        List<Aggregation> aggregations = classComponent.getDiagram().getComponents().stream()
                .filter(c -> c instanceof Aggregation)
                .map(c -> (Aggregation) c)
                .filter(agg -> {
                    Component source = agg.getSource();
                    return source != null && source.equals(classComponent);
                })
                .collect(Collectors.toList());

        for (Aggregation aggregation : aggregations) {
            // Get target component and name
            Component target = aggregation.getTarget();
            String targetName = getComponentName(target,
                    target instanceof Class ? "Class Name" :
                            target instanceof Interface ? "Interface Name" :
                                    target instanceof AbstractClass ? "Class Name" : "UnknownComponent",
                    "UnknownTarget");

            // Get target multiplicity
            String targetMultiplicity = getMultiplicityFromAggregation(aggregation, "Target Multiplicity");

            // Determine reference type for aggregation
            String referenceType = determineAggregationReferenceType(targetMultiplicity, targetName);

            // Generate attribute for aggregated target
            classCode.append("    // Aggregated ")
                    .append(targetName)
                    .append("\n    private ")
                    .append(referenceType)
                    .append(" ")
                    .append(targetName.toLowerCase())
                    .append(";\n");
        }
    }

    // Helper method to get multiplicity from aggregation
    private String getMultiplicityFromAggregation(Aggregation aggregation, String multiplicityType) {
        return aggregation.getProperties().stream()
                .filter(prop -> multiplicityType.equals(prop.gettype()))
                .findFirst()
                .map(Property::getValue)
                .orElse("1"); // Default to single reference
    }

    // Determine reference type for aggregation based on multiplicity
    private String determineAggregationReferenceType(String multiplicity, String targetName) {
        // Check for list-based multiplicity
        if (multiplicity.contains("*") ||
                (multiplicity.contains("..") && multiplicity.endsWith("*"))) {
            return "List<" + targetName + ">";
        }

        // Parse numeric ranges
        if (multiplicity.contains("..")) {
            String[] parts = multiplicity.split("\\.\\.");
            try {
                int min = Integer.parseInt(parts[0]);
                int max = parts.length > 1 ?
                        (parts[1].equals("*") ? Integer.MAX_VALUE : Integer.parseInt(parts[1])) :
                        Integer.parseInt(parts[0]);

                return max > 1 ? "List<" + targetName + ">" : targetName;
            } catch (NumberFormatException e) {
                // Fallback to single reference
                return targetName;
            }
        }

        // Single numeric value
        try {
            int count = Integer.parseInt(multiplicity);
            return count > 1 ? "List<" + targetName + ">" : targetName;
        } catch (NumberFormatException e) {
            // Default to single reference
            return targetName;
        }
    }

    // Generate attributes for a specific visibility
    private void generateAttributes(StringBuilder classCode,
                                    Map<String, String> attributes,
                                    String defaultVisibility) {
        // Iterate through attributes and add to class code
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            classCode.append("    ")
                    .append(defaultVisibility)
                    .append(" ")
                    .append(entry.getValue())  // type
                    .append(" ")
                    .append(entry.getKey())    // name
                    .append(";\n");
        }
    }

    // Generate methods for a specific visibility
    private void generateMethods(StringBuilder classCode,
                                 Map<String, MethodInfo> methods,
                                 String defaultVisibility) {
        // Iterate through methods and add to class code
        for (MethodInfo methodInfo : methods.values()) {
            classCode.append("\n    ");

            // Add abstract modifier if needed
            if (methodInfo.isAbstract) {
                classCode.append("abstract ");
            }

            // Method signature
            classCode.append(defaultVisibility)
                    .append(" ")
                    .append(methodInfo.returnType)
                    .append(" ")
                    .append(methodInfo.name)
                    .append("(")
                    .append(methodInfo.parameters)
                    .append(")");

            // Method body or abstract declaration
            if (methodInfo.isAbstract) {
                classCode.append(";\n");
            } else {
                classCode.append(" {\n");
                classCode.append("        // TODO: Implement method body\n");
                classCode.append("    }\n");
            }
        }
    }

    private List<Association> findAssociationsForComponent(Component component) {
        return component.getDiagram().getComponents().stream()
                .filter(c -> c instanceof Association)
                .map(c -> (Association) c)
                .filter(assoc -> {
                    Component source = assoc.getSource();
                    return source != null && source.equals(component);
                })
                .collect(Collectors.toList());
    }

    private void generateAssociationReferences(StringBuilder classCode, Component classComponent) {
        List<Association> associations = findAssociationsForComponent(classComponent);

        for (Association association : associations) {
            // Get target component and name
            Component target = association.getTarget();
            String targetName = getComponentName(target,
                    target instanceof Class ? "Class Name" :
                            target instanceof Interface ? "Interface Name" :
                                    target instanceof AbstractClass ? "Class Name" : "UnknownComponent",
                    "UnknownTarget");

            // Get multiplicity
            String sourceMultiplicity = getMultiplicityFromAssociation(association);

            // Determine reference type
            String referenceType = determineReferenceType(sourceMultiplicity, targetName);

            // Generate attribute
            classCode.append("    private ")
                    .append(referenceType)
                    .append(" ")
                    .append(targetName.toLowerCase())
                    .append(";\n");
        }
    }

    // Determine appropriate reference type based on multiplicity
    private String determineReferenceType(String multiplicity, String targetName) {
        // Check for list-based multiplicity
        if (multiplicity.contains("*") ||
                (multiplicity.contains("..") && multiplicity.endsWith("*"))) {
            return "List<" + targetName + ">";
        }

        // Parse numeric ranges
        if (multiplicity.contains("..")) {
            String[] parts = multiplicity.split("\\.\\.");
            try {
                int min = Integer.parseInt(parts[0]);
                int max = parts.length > 1 ?
                        (parts[1].equals("*") ? Integer.MAX_VALUE : Integer.parseInt(parts[1])) :
                        Integer.parseInt(parts[0]);

                return max > 1 ? "List<" + targetName + ">" : targetName;
            } catch (NumberFormatException e) {
                // Fallback to single reference
                return targetName;
            }
        }

        // Single numeric value
        try {
            int count = Integer.parseInt(multiplicity);
            return count > 1 ? "List<" + targetName + ">" : targetName;
        } catch (NumberFormatException e) {
            // Default to single reference
            return targetName;
        }
    }
    private void appendClassDeclaration(StringBuilder classCode, String className,
                                        Component classComponent, boolean isAbstract) {
        // Class declaration
        String classType = isAbstract ? "abstract class" : "class";
        classCode.append("public ").append(classType).append(" ").append(className).append(" {\n");

        // Generate association references first
        generateAssociationReferences(classCode, classComponent);

        // Generate aggregation references
        generateAggregationReferences(classCode, classComponent);

        generateCompositionReferences(classCode, classComponent);


        // Prepare collections for attributes and methods
        AttributeMethodCollections collections = collectAttributesAndMethods(classComponent);

        // Generate attributes
        generateAttributes(classCode, collections.privateAttributes, "private");
        generateAttributes(classCode, collections.protectedAttributes, "protected");
        generateAttributes(classCode, collections.publicAttributes, "public");

        // Generate constructor
        generateConstructor(classCode, className, isAbstract);

        // Generate methods
        generateMethods(classCode, collections.privateMethods, "private");
        generateMethods(classCode, collections.protectedMethods, "protected");
        generateMethods(classCode, collections.publicMethods, "public");

        classCode.append("}\n");
    }
    private String getMultiplicityFromAssociation(Association association) {
        for (Property prop : association.getProperties()) {
            if ("Source Multiplicity".equals(prop.gettype())) {
                return prop.getValue();
            }
        }
        return "1"; // Default to single reference
    }
    // You might also want to update the constructor generation
    private void generateConstructor(StringBuilder classCode, String className, boolean isAbstract) {
        String constructorVisibility = isAbstract ? "protected" : "public";
        classCode.append("\n    ")
                .append(constructorVisibility)
                .append(" ")
                .append(className)
                .append("() {\n");
        classCode.append("        // ")
                .append(isAbstract ? "Protected" : "Default")
                .append(" constructor\n");

        // Initialize collection references for compositions and aggregations
        classCode.append("        // Initialize collection references for compositions and aggregations\n");
        classCode.append("        // Example: this.targetList = new ArrayList<>();\n");

        classCode.append("    }\n");
    }

    private void generateCompositionReferences(StringBuilder classCode, Component classComponent) {
        // Find all compositions where this component is the source
        List<Composition> compositions = classComponent.getDiagram().getComponents().stream()
                .filter(c -> c instanceof Composition)
                .map(c -> (Composition) c)
                .filter(comp -> {
                    Component source = comp.getSource();
                    return source != null && source.equals(classComponent);
                })
                .collect(Collectors.toList());

        for (Composition composition : compositions) {
            // Get target component and name
            Component target = composition.getTarget();
            String targetName = getComponentName(target,
                    target instanceof Class ? "Class Name" :
                            target instanceof Interface ? "Interface Name" :
                                    target instanceof AbstractClass ? "Class Name" : "UnknownComponent",
                    "UnknownTarget");

            // Get target multiplicity
            String targetMultiplicity = getMultiplicityFromComposition(composition, "Target Multiplicity");

            // Determine reference type for composition
            String referenceType = determineCompositionReferenceType(targetMultiplicity, targetName);

            // Generate attribute for composed target
            classCode.append("    // Composed ")
                    .append(targetName)
                    .append("\n    private ")
                    .append(referenceType)
                    .append(" ")
                    .append(targetName.toLowerCase())
                    .append(";\n");
        }
    }

    // Helper method to get multiplicity from composition
    private String getMultiplicityFromComposition(Composition composition, String multiplicityType) {
        return composition.getProperties().stream()
                .filter(prop -> multiplicityType.equals(prop.gettype()))
                .findFirst()
                .map(Property::getValue)
                .orElse("1"); // Default to single reference
    }

    // Determine reference type for composition based on multiplicity
    private String determineCompositionReferenceType(String multiplicity, String targetName) {
        // Check for list-based multiplicity
        if (multiplicity.contains("*") ||
                (multiplicity.contains("..") && multiplicity.endsWith("*"))) {
            return "List<" + targetName + ">";
        }

        // Parse numeric ranges
        if (multiplicity.contains("..")) {
            String[] parts = multiplicity.split("\\.\\.");
            try {
                int min = Integer.parseInt(parts[0]);
                int max = parts.length > 1 ?
                        (parts[1].equals("*") ? Integer.MAX_VALUE : Integer.parseInt(parts[1])) :
                        Integer.parseInt(parts[0]);

                return max > 1 ? "List<" + targetName + ">" : targetName;
            } catch (NumberFormatException e) {
                // Fallback to single reference
                return targetName;
            }
        }

        // Single numeric value
        try {
            int count = Integer.parseInt(multiplicity);
            return count > 1 ? "List<" + targetName + ">" : targetName;
        } catch (NumberFormatException e) {
            // Default to single reference
            return targetName;
        }
    }




}