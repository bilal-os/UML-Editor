package Utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Utilities.Project.ProjectAdapter;
import Utilities.Project.Project;

public class GsonFactory {
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Project.class, new ProjectAdapter())
                .create();
    }
}
