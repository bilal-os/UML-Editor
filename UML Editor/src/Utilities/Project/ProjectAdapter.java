package Utilities.Project;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ProjectAdapter implements JsonSerializer<Project>, JsonDeserializer<Project> {

    @Override
    public JsonElement serialize(Project project, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        // Add only serializable fields
        jsonObject.addProperty("name", project.getName());
        jsonObject.addProperty("id", project.getId());
        // Add other serializable fields as needed
        return jsonObject;
    }

    @Override
    public Project deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        // Extract the fields
        String name = jsonObject.get("name").getAsString();
        int id = jsonObject.get("id").getAsInt();

        // Create a new Project object
        Project project = new Project(id, name);
        // Set any additional fields if necessary
        return project;
    }
}
