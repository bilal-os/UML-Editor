package Utilities;

import java.util.ArrayList;

public class Project implements Subject{
    ArrayList<Diagram> diagrams;
    int id;
    String name;
    ArrayList<Observer> observers;

    public Project(int id, String name) {
        diagrams = new ArrayList<>();
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

    @Override
    public void addObserver(Observer observer) {
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
