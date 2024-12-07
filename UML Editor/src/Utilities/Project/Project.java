package Utilities.Project;

import Utilities.Diagram.Diagram;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Project.class)
public class Project {

    ArrayList<Diagram> diagrams;
    protected int id;
    String name;
    ArrayList<ProjectObserver> observers;

    public Project() {
        diagrams = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public Project(int id, String name) {
        diagrams = new ArrayList<>();
        observers = new ArrayList<>();
        this.id = id;
        this.name = name;
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
