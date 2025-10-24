package persistence;

import model.*;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


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
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAutoLink(jsonObject);
    }

    // REQUIRES: valid file path
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(Paths.get(source), StandardCharsets.UTF_8);
            String line;

            // read each line and append
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();
    }

    // REQUIRES: valid JSON structure with activeBuild and inventory fields
    // EFFECTS: parses AutoLink data from JSON object and returns BuildData object
    private BuildData parseAutoLink(JSONObject jsonObject) {
        JSONObject buildJson = jsonObject.getJSONObject("activeBuild");
        JSONArray inventoryJson = jsonObject.getJSONArray("inventory");

        Build build = parseBuild(buildJson);
        List<Part> inventory = parseInventory(inventoryJson);

        return new BuildData(build, inventory);
    }

    // REQUIRES: valid JSON object containing build info
    // EFFECTS: parses Build from JSON and returns it
    private Build parseBuild(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Build build = new Build(name);

        JSONArray partsArray = jsonObject.getJSONArray("parts");
        for (Object obj : partsArray) {
            JSONObject partJson = (JSONObject) obj;
            String partName = partJson.getString("name");
            int cost = partJson.getInt("cost");

            Part part = new GenericPart(partName, cost);
            build.addPart(part);
        }

        return build;

    }

    // REQUIRES: valid JSON array containing part info
    // EFFECTS: parses parts from JSON array and returns them as a list
    private List<Part> parseInventory(JSONArray jsonArray) {
        List<Part> inventory = new ArrayList<>();

        for (Object obj : jsonArray) {
            JSONObject nextPart = (JSONObject) obj;
            String name = nextPart.getString("name");
            int cost = nextPart.getInt("cost");

            Part p = new GenericPart(name, cost);
            inventory.add(p);
        }

        return inventory;
    }

    // Simple generic part used for reading JSON when the specific subclass is unknown
    private static class GenericPart extends Part {
        public GenericPart(String name, int cost) {
            super(name, cost);
        }
    }
}
