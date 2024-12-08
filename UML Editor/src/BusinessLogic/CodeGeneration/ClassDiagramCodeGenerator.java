package BusinessLogic.CodeGeneration;

import BusinessLogic.ClassDiagram.Components.AbstractClass;
import BusinessLogic.ClassDiagram.Components.Class;
import Utilities.*;
import Utilities.Component.Component;
import Utilities.Diagram.Diagram;
import Utilities.Property.Property;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassDiagramCodeGenerator implements DiagramCodeGenerator {

    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("^([-+#])\\s*(\\w+)\\s*:\\s*(\\w+)$");
    private static final Pattern METHOD_PATTERN = Pattern.compile("^([-+#])\\s*(\\w+)\\s*\\(([^)]*)\\)\\s*:\\s*(\\w+)(?:\\s*<<abstract>>)?$");

    @Override
    public void generateDiagramCode(String fileLocation, Diagram diagram) {
        validateInputs(fileLocation, diagram);

        for (Component component : diagram.getComponents()) {
            if (component instanceof AbstractClass) {
                generateAbstractClassCode((AbstractClass) component, fileLocation);
            } else if (component instanceof Class) {
                generateClassCode((Class) component, fileLocation);
            }
        }
    }

    private void validateInputs(String fileLocation, Diagram diagram) {
        if (fileLocation == null || fileLocation.isEmpty()) {
            throw new IllegalArgumentException("File location must be specified");
        }

        if (diagram == null) {
            throw new IllegalArgumentException("Diagram cannot be null");
        }
    }

    private void generateClassCode(Class classComponent, String baseFileLocation) {
        String className = getClassName(classComponent);

        StringBuilder classCode = new StringBuilder();
        // Dynamically handle package if provided in class name
        if (className.contains(".")) {
            int lastDotIndex = className.lastIndexOf('.');
            String packageName = className.substring(0, lastDotIndex);
            className = className.substring(lastDotIndex + 1);
            classCode.append("package ").append(packageName).append(";\n\n");
        } else {
            classCode.append("// TODO: Add package declaration\n");
        }

        // Regular class declaration
        classCode.append("public class ").append(className).append(" {\n");

        // Temporary collections to organize attributes and methods
        Map<String, String> privateAttributes = new HashMap<>();
        Map<String, String> protectedAttributes = new HashMap<>();
        Map<String, String> publicAttributes = new HashMap<>();

        Map<String, MethodInfo> privateMethods = new HashMap<>();
        Map<String, MethodInfo> protectedMethods = new HashMap<>();
        Map<String, MethodInfo> publicMethods = new HashMap<>();

        // Categorize and parse properties
        for (Property prop : classComponent.getProperties()) {
            if ("Attribute".equals(prop.gettype())) {
                parseAttribute(prop.getValue(), privateAttributes, protectedAttributes, publicAttributes);
            } else if ("Method".equals(prop.gettype())) {
                parseMethod(prop.getValue(), privateMethods, protectedMethods, publicMethods, false);
            } else if ("Abstract Method".equals(prop.gettype())) {
                parseMethod(prop.getValue(), privateMethods, protectedMethods, publicMethods, true);
            }
        }

        // Generate attributes
        generateAttributes(classCode, privateAttributes, "private");
        generateAttributes(classCode, protectedAttributes, "protected");
        generateAttributes(classCode, publicAttributes, "public");

        // Generate constructor
        classCode.append("\n    public ").append(className).append("() {\n");
        classCode.append("        // Default constructor\n");
        classCode.append("    }\n");

        // Generate methods
        generateMethods(classCode, privateMethods, "private");
        generateMethods(classCode, protectedMethods, "protected");
        generateMethods(classCode, publicMethods, "public");

        classCode.append("}\n");

        // Write to file
        writeToFile(baseFileLocation, className + ".java", classCode.toString());
    }

    private void generateAbstractClassCode(AbstractClass abstractClass, String baseFileLocation) {
        String className = getClassName(abstractClass);

        StringBuilder classCode = new StringBuilder();
        // Dynamically handle package if provided in class name
        if (className.contains(".")) {
            int lastDotIndex = className.lastIndexOf('.');
            String packageName = className.substring(0, lastDotIndex);
            className = className.substring(lastDotIndex + 1);
            classCode.append("package ").append(packageName).append(";\n\n");
        } else {
            classCode.append("// TODO: Add package declaration\n");
        }

        // Abstract class declaration
        classCode.append("public abstract class ").append(className).append(" {\n");

        // Temporary collections to organize attributes and methods
        Map<String, String> privateAttributes = new HashMap<>();
        Map<String, String> protectedAttributes = new HashMap<>();
        Map<String, String> publicAttributes = new HashMap<>();

        Map<String, MethodInfo> privateMethods = new HashMap<>();
        Map<String, MethodInfo> protectedMethods = new HashMap<>();
        Map<String, MethodInfo> publicMethods = new HashMap<>();

        // Categorize and parse properties
        for (Property prop : abstractClass.getProperties()) {
            if ("Attribute".equals(prop.gettype())) {
                parseAttribute(prop.getValue(), privateAttributes, protectedAttributes, publicAttributes);
            } else if ("Method".equals(prop.gettype())) {
                parseMethod(prop.getValue(), privateMethods, protectedMethods, publicMethods, false);
            } else if ("Abstract Method".equals(prop.gettype())) {
                parseMethod(prop.getValue(), privateMethods, protectedMethods, publicMethods, true);
            }
        }

        // Generate attributes
        generateAttributes(classCode, privateAttributes, "private");
        generateAttributes(classCode, protectedAttributes, "protected");
        generateAttributes(classCode, publicAttributes, "public");

        // Generate protected constructor
        classCode.append("\n    protected ").append(className).append("() {\n");
        classCode.append("        // Protected constructor for abstract class\n");
        classCode.append("    }\n");

        // Generate methods
        generateMethods(classCode, privateMethods, "private");
        generateMethods(classCode, protectedMethods, "protected");
        generateMethods(classCode, publicMethods, "public");

        classCode.append("}\n");

        // Write to file
        writeToFile(baseFileLocation, className + ".java", classCode.toString());
    }

    private void parseAttribute(String attributeValue,
                                Map<String, String> privateAttributes,
                                Map<String, String> protectedAttributes,
                                Map<String, String> publicAttributes) {
        Matcher matcher = ATTRIBUTE_PATTERN.matcher(attributeValue.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid attribute format: " + attributeValue);
        }

        String visibility = matcher.group(1);
        String name = matcher.group(2);
        String type = matcher.group(3);

        switch (visibility) {
            case "-":
                privateAttributes.put(name, type);
                break;
            case "#":
                protectedAttributes.put(name, type);
                break;
            case "+":
                publicAttributes.put(name, type);
                break;
        }
    }

    private void parseMethod(String methodValue,
                             Map<String, MethodInfo> privateMethods,
                             Map<String, MethodInfo> protectedMethods,
                             Map<String, MethodInfo> publicMethods,
                             boolean isAbstract) {
        Matcher matcher = METHOD_PATTERN.matcher(methodValue.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid method format: " + methodValue);
        }

        String visibility = matcher.group(1);
        String methodName = matcher.group(2);
        String parameters = matcher.group(3);
        String returnType = matcher.group(4);

        MethodInfo methodInfo = new MethodInfo(methodName, parameters, returnType, isAbstract);

        switch (visibility) {
            case "-":
                privateMethods.put(methodName, methodInfo);
                break;
            case "#":
                protectedMethods.put(methodName, methodInfo);
                break;
            case "+":
                publicMethods.put(methodName, methodInfo);
                break;
        }
    }

    private void generateAttributes(StringBuilder classCode, Map<String, String> attributes, String defaultVisibility) {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            classCode.append("    ")
                    .append(defaultVisibility)
                    .append(" ")
                    .append(entry.getValue())
                    .append(" ")
                    .append(entry.getKey())
                    .append(";\n");
        }
    }

    private void generateMethods(StringBuilder classCode, Map<String, MethodInfo> methods, String defaultVisibility) {
        for (MethodInfo methodInfo : methods.values()) {
            classCode.append("\n    ");

            // Method modifier
            if (methodInfo.isAbstract) {
                classCode.append("abstract ");
            }

            classCode.append(defaultVisibility)
                    .append(" ")
                    .append(methodInfo.returnType)
                    .append(" ")
                    .append(methodInfo.name)
                    .append("(")
                    .append(methodInfo.parameters)
                    .append(")");

            if (methodInfo.isAbstract) {
                classCode.append(";\n");
            } else {
                classCode.append(" {\n");
                classCode.append("        // TODO: Implement method body\n");
                classCode.append("    }\n");
            }
        }
    }

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

    private String getClassName(Component classComponent) {
        for (Property prop : classComponent.getProperties()) {
            if ("Class Name".equals(prop.gettype())) {
                return prop.getValue();
            }
        }
        return "UnknownClass";
    }

    // Alternatively, if you want to keep the original method and add a new one:
    private String getClassName(Class classComponent) {
        for (Property prop : classComponent.getProperties()) {
            if ("Class Name".equals(prop.gettype())) {
                return prop.getValue();
            }
        }
        return "UnknownClass";
    }

    private String getClassName(AbstractClass abstractClass) {
        for (Property prop : abstractClass.getProperties()) {
            if ("Class Name".equals(prop.gettype())) {
                return prop.getValue();
            }
        }
        return "UnknownClass";
    }
    // Inner class to store method information
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
}