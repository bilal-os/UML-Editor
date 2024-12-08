package Utilities.Project;

import Utilities.GsonFactory;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectFileHandler {

    // Method to save the project to a file
    public static void saveProject(Project project, String directoryPath) throws IOException {
        Gson gson = GsonFactory.createGson(); // Use the custom Gson instance
        File file = new File(directoryPath, project.getName() + ".json");

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(project, writer);
        }
    }

    // Method to open the project from a file
    public static Project openProject(String filePath) throws IOException {
        Gson gson = GsonFactory.createGson(); // Use the custom Gson instance
        File file = new File(filePath);

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Project.class);
        }
    }
}
