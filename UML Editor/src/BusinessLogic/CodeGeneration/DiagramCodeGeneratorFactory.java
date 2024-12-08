package BusinessLogic.CodeGeneration;

import BusinessLogic.ClassDiagram.Components.Class;
import BusinessLogic.ClassDiagram.Diagram.ClassDiagram;
import Utilities.Diagram.Diagram;

public class DiagramCodeGeneratorFactory {
    public static DiagramCodeGenerator getDiagramCodeGenerator(Diagram diagram) {
        if(diagram instanceof ClassDiagram)
        {
            return new ClassDiagramCodeGenerator();
        }
        return null;
    }
}
