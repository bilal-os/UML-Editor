package DataLayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import Utilities.Project.Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONDataLayer implements DataLayer {
    private final ObjectMapper objectMapper;

    public JSONDataLayer() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing JSON
    }

    @Override
    public void saveProject(Project project, String filepath) throws Exception {
        // Ensure filename uses the project's name with .json extension
        String fileName = project.getName() + ".json";
        File file = new File(filepath, fileName); // Create a File object with the specified directory and filename

        // Convert the Project object into JSON
        String jsonContent;
        try {
            jsonContent = objectMapper.writeValueAsString(project);
        } catch (IOException e) {
           System.out.println(e.getMessage());
            throw new Exception("Error serializing the project", e);
        }

        // Write the JSON string to the specified file
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonContent);
        } catch (IOException e) {
            throw new Exception("Error writing to file", e);
        }
    }

    @Override
    public Project loadProject(String filepath) throws Exception {
        // Ensure the file exists at the given filepath
        File file = new File(filepath);
        if (!file.exists()) {
            throw new Exception("File not found: " + filepath);
        }

        // Read the JSON file and map it to a Project object
        try {
            // Deserialize the JSON content into a Project object
            return objectMapper.readValue(file, Project.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new Exception("Error reading or deserializing the project file", e);
        }
    }

}