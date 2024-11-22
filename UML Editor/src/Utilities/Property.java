package Utilities;

public class Property {
    String type;
    String value;

    public Property(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String gettype() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
