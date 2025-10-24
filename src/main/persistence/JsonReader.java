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
            Part part = parsePart(partJson);
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
            inventory.add(parsePart(nextPart));
        }
        return inventory;
    }


    // REQUIRES: valid JSON part object with category field
    // EFFECTS: parses and returns correct Part subclass based on category
    @SuppressWarnings("methodlength")
    private Part parsePart(JSONObject json) {
        String category = json.getString("category");
        String name = json.getString("name");
        int cost = json.getInt("cost");
        if (category.equalsIgnoreCase("Wheel")) {
            return parseWheelPart(json, name, cost);
        } else if (category.equalsIgnoreCase("Tire")) {
            return parseTirePart(json, name, cost);
        } else if (category.equalsIgnoreCase("Suspension")) {
            return parseSuspensionPart(json, name, cost);
        } else if (category.equalsIgnoreCase("Exhaust")) {
            return parseExhaustPart(json, name, cost);
        } else if (category.equalsIgnoreCase("Engine")) {
            return parseEnginePart(json, name, cost);
        } else if (category.equalsIgnoreCase("Transmission")) {
            return parseTransmissionPart(json, name, cost);
        } else if (category.equalsIgnoreCase("Bumper")) {
            return parseBumperPart(json, name, cost);
        } else if (category.equalsIgnoreCase("SideSkirts")) {
            return parseSideSkirtsPart(json, name, cost);
        } else if (category.equalsIgnoreCase("Diffuser")) {
            return parseDiffuserPart(json, name, cost);
        } else if (category.equalsIgnoreCase("Spoiler")) {
            return parseSpoilerPart(json, name, cost);
        } else if (category.equalsIgnoreCase("Lights")) {
            return parseLightsPart(json, name, cost);
        } else if (category.equalsIgnoreCase("PartStub")) {
            return new GenericPart(name, cost);
        } else {
            throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    // EFFECTS: parses wheel JSON into Wheel object
    private Part parseWheelPart(JSONObject json, String name, int cost) {
        return new Wheel(name, cost, json.getDouble("diameterIn"),
                json.getDouble("widthIn"), json.getInt("offsetMm"));
    }

    // EFFECTS: parses tire JSON into Tire object
    private Part parseTirePart(JSONObject json, String name, int cost) {
        return new Tire(name, cost, json.getInt("widthMm"),
                json.getInt("aspectPercent"), json.getDouble("rimDiameterIn"));
    }

    // EFFECTS: parses suspension JSON into Suspension object
    private Part parseSuspensionPart(JSONObject json, String name, int cost) {
        return new Suspension(name, cost, json.getString("type"),
                json.getInt("dropMm"));
    }

    // EFFECTS: parses exhaust JSON into Exhaust object
    private Part parseExhaustPart(JSONObject json, String name, int cost) {
        return new Exhaust(name, cost, json.getString("spec"));
    }

    // EFFECTS: parses engine JSON into Engine object
    private Part parseEnginePart(JSONObject json, String name, int cost) {
        return new Engine(name, cost, json.getString("type"),
                json.getInt("horsepower"), json.getDouble("displacement"));
    }

    // EFFECTS: parses transmission JSON into Transmission object
    private Part parseTransmissionPart(JSONObject json, String name, int cost) {
        return new Transmission(name, cost, json.getString("type"),
                json.getInt("gears"), json.getString("drive"));
    }

    // EFFECTS: parses bumper JSON into Bumper object
    private Part parseBumperPart(JSONObject json, String name, int cost) {
        return new Bumper(name, cost, json.getString("type"),
                json.getString("material"), json.getString("brand"),
                json.getString("style"));
    }

    // EFFECTS: parses side skirts JSON into SideSkirts object
    private Part parseSideSkirtsPart(JSONObject json, String name, int cost) {
        return new SideSkirts(name, cost, json.getString("material"),
                json.getString("brand"));
    }

    // EFFECTS: parses diffuser JSON into Diffuser object
    private Part parseDiffuserPart(JSONObject json, String name, int cost) {
        return new Diffuser(name, cost, json.getString("material"),
                json.getString("brand"), json.getBoolean("functional"));
    }

    // EFFECTS: parses spoiler JSON into Spoiler object
    private Part parseSpoilerPart(JSONObject json, String name, int cost) {
        return new Spoiler(name, cost, json.getString("material"),
                json.getString("style"), json.getDouble("heightMm"));
    }

    // EFFECTS: parses lights JSON into Lights object
    private Part parseLightsPart(JSONObject json, String name, int cost) {
        return new Lights(name, cost, json.getString("type"),
                json.getString("brand"), json.getString("lightType"),
                json.getString("detail"));
    }

     // simple fallback part type for testing
    private static class GenericPart extends Part {
        public GenericPart(String name, int cost) {
            super(name, cost);
        }
    }


}
