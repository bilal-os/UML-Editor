package DataLayer;

import Utilities.Diagram.Diagram;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import Utilities.Project.Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public void savePng(Diagram diagram, String directoryPath) throws Exception {
        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique filename using timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        String filename = "Diagram_" + timestamp + ".png";

        // Create full file path
        File outputFile = new File(directory, filename);

        // Create a buffered image with the diagram's dimensions
        int width = 1200; // Default width
        int height = 800; // Default height

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Create a graphics context from the buffered image
        Graphics2D graphics = bufferedImage.createGraphics();

        // Set a white background
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        try {
            // Render the diagram onto the graphics context
            diagram.renderDiagram(graphics);

            // Write the buffered image to a PNG file
            ImageIO.write(bufferedImage, "png", outputFile);

            System.out.println("Diagram saved successfully to: " + outputFile.getAbsolutePath());
        } finally {
            // Dispose of the graphics context to free up resources
            graphics.dispose();
        }
    }

    @Override
    public void saveJpg(Diagram diagram, String directoryPath) throws Exception {
        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique filename using timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        String filename = "Diagram_" + timestamp + ".jpg";

        // Create full file path
        File outputFile = new File(directory, filename);

        // Create a buffered image with the diagram's dimensions
        int width = 1200; // Default width
        int height = 800; // Default height

        // Create a buffered image without transparency (for JPEG)
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create a graphics context from the buffered image
        Graphics2D graphics = bufferedImage.createGraphics();

        // Set a white background
        graphics.setColor(java.awt.Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        try {
            // Render the diagram onto the graphics context
            diagram.renderDiagram(graphics);

            // Write the buffered image to a JPEG file
            ImageIO.write(bufferedImage, "jpg", outputFile);

            System.out.println("Diagram saved successfully to: " + outputFile.getAbsolutePath());
        } finally {
            // Dispose of the graphics context to free up resources
            graphics.dispose();
        }
    }

}