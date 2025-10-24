package persistence;

import model.Build;
import model.Part;
import org.json.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


// Citation: Modelled after the JsonReader class in the WorkRoom example provided in the CPSC 210 Term Project Task 3


// Represents a reader that reads AutoLink build and parts data from JSON file
public class JsonReader {
    private String source;

    // REQUIRES: source is a valid path to a JSON file inside ./data directory
    // MODIFIES: this
    // EFFECTS: constructs a reader to read from given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // REQUIRES: file at source must exist and contain valid JSON data
    // EFFECTS: reads data from file and returns BuildData object containing
    //          active build and inventory list; throws IOException if an error occurs
    public BuildData read() throws IOException {
        return null; // stub
    }

    // REQUIRES: valid file path
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null; // stub
    }

    // REQUIRES: valid JSON structure with activeBuild and inventory fields
    // EFFECTS: parses AutoLink data from JSON object and returns BuildData object
    private BuildData parseAutoLink(JSONObject jsonObject) {
        return null; // stub
    }

    // REQUIRES: valid JSON object containing build info
    // EFFECTS: parses Build from JSON and returns it
    private Build parseBuild(JSONObject jsonObject) {
        return null; // stub
    }

    // REQUIRES: valid JSON array containing part info
    // EFFECTS: parses parts from JSON array and returns them as a list
    private List<Part> parseInventory(JSONArray jsonArray) {
        return new ArrayList<>(); // stub
    }
}
