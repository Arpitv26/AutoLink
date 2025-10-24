package persistence;

import model.*;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

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
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of the given BuildData object to file
    public void write(BuildData data) {
        JSONObject json = new JSONObject();
        json.put("activeBuild", buildToJson(data.getBuild()));
        json.put("inventory", inventoryToJson(data.getInventory()));
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // EFFECTS: saves given JSON string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

        // EFFECTS: converts Build to JSON object
    private JSONObject buildToJson(Build build) {
        JSONObject json = new JSONObject();
        json.put("name", build.getName());

        JSONArray partsArray = new JSONArray();
        for (Part p : build.getParts()) {
            JSONObject partJson = new JSONObject();
            partJson.put("name", p.getName());
            partJson.put("cost", p.getCost());
            partsArray.put(partJson);
        }
        json.put("parts", partsArray);

        return json;
    }

    // EFFECTS: converts list of parts (inventory) to JSON array
    private JSONArray inventoryToJson(List<Part> inventory) {
        JSONArray array = new JSONArray();

        for (Part p : inventory) {
            JSONObject partJson = new JSONObject();
            partJson.put("name", p.getName());
            partJson.put("cost", p.getCost());
            array.put(partJson);
        }

        return array;
    }
}
