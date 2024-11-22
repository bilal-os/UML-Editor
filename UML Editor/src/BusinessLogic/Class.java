package BusinessLogic;

import Utilities.Component;
import Utilities.Property;

import java.util.ArrayList;
import java.util.List;

public class Class extends Component {
    private String name;
    private List<String> attributes;
    private List<String> methods;

    public Class(String name) {

        attributes = new ArrayList<>();
        methods = new ArrayList<>();

        propertiesTypes.add("Class Name");
        propertiesTypes.add("Attribute");
        propertiesTypes.add("Method");

        this.name = name;

        properties.add(new Property("Class Name", name));

        for(int i=0; i<attributes.size(); i++){
            properties.add(new Property("Attribute " + i, attributes.get(i)));
        }

        for(int i=0; i<methods.size(); i++){
            properties.add(new Property("Method " + i, methods.get(i)));
        }

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
