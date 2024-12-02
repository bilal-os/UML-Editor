package Utilities;

import java.util.ArrayList;

public class Project {
    ArrayList<Diagram> diagrams;
    int id;
    String name;
    ArrayList<ProjectObserver> observers;

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
