package BusinessLogic;

import Utilities.Component;

import java.util.List;

public class Class extends Component {
    private String name;
    private List<String> attributes;
    private List<String> methods;

    public Class(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }

    public void removeAttribute(String attribute) {
        this.attributes.remove(attribute);
    }

    public List<String> getMethods() {
        return methods;
    }

    public void addMethod(String method) {
        this.methods.add(method);
    }

    public void removeMethod(String method) {
        this.methods.remove(method);
    }

}
