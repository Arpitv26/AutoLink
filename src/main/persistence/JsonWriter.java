package persistence;

import model.*;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Citation: Modelled after the JsonWriter class in the WorkRoom example provided in the CPSC 210 Term Project Task 3

// Represents a writer that writes AutoLink build and inventory data to JSON file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // REQUIRES: destination is a valid path inside ./data directory
    // MODIFIES: this
    // EFFECTS: constructs a writer to write to given destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer for writing to file at destination
    //          throws FileNotFoundException if destination file cannot be opened
    public void open() throws FileNotFoundException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of the given BuildData object to file
    public void write(BuildData data) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        // stub
    }

    // EFFECTS: saves given JSON string to file
    private void saveToFile(String json) {
        // stub
    }
}
