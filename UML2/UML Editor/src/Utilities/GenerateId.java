package Utilities;

public class GenerateId {
    private static int counter = 0;

    // Thread-safe ID generation
    public synchronized static int generateId() {
        return counter++;
    }
}
