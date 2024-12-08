package Utilities.Project;

import Utilities.Diagram.Diagram;
import Utilities.GenerateId;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project {

    private ArrayList<Diagram> diagrams;
    ArrayList<ProjectObserver> observers;

    private int id;
    private String name;
    private String description; // New field
    private String version;     // New field

    // Default constructor
    public Project() {
        this.diagrams = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // Constructor with id and name
    public Project(int id, String name) {
        this();
        this.id = GenerateId.generateId();
        this.name = name;
    }
    ////////////
    // Constructor with name, description, and version
    public Project(String name, String description, String version) {
        this();
        this.id = GenerateId.generateId();
        this.name = name;
        this.description = description;
        this.version = version;
    }

    // Getters and setters for diagrams
    public ArrayList<Diagram> getDiagrams() {
        return diagrams;
    }

    public void addDiagram(Diagram diagram) {
        diagrams.add(diagram);
        notifyObservers();
    }

    // Getters and setters for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters and setters for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getters and setters for version
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    // Observer methods
    public void addObserver(ProjectObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProjectObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ProjectObserver observer : observers) {
            observer.updateFromProject();
        }
    }
}
