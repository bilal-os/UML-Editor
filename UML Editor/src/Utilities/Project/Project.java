package Utilities.Project;

import Utilities.Diagram.Diagram;
import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    // Ensure compatibility with serialization
    private static final long serialVersionUID = 1L;

    private ArrayList<Diagram> diagrams;
    private int id;
    private String name;

    // Mark observers as transient to avoid serialization issues
    private transient ArrayList<ProjectObserver> observers;

    public Project(int id, String name) {
        diagrams = new ArrayList<>();
        observers = new ArrayList<>();
        this.id = id;
        this.name = name;
    }

    public void setDiagrams(ArrayList<Diagram> diagrams) {
        this.diagrams = diagrams;
    }

    public ArrayList<Diagram> getDiagrams() {
        return diagrams;
    }

    public void addDiagram(Diagram diagram) {
        diagrams.add(diagram);
        notifyObservers();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Methods for managing observers
    public void addObserver(ProjectObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProjectObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers
    public void notifyObservers() {
        for (ProjectObserver observer : observers) {
            observer.updateFromProject();
        }
    }
}
