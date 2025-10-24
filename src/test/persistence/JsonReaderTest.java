package persistence;

import model.Build;
import model.Part;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Citation: Modelled after the JsonReaderTest class in the example provided in the CPSC 210 Term Project Task 3

// Tests for JsonReader class
public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BuildData data = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    @Test
    void testReaderEmptyBuildData() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBuildData.json");
        try {
            BuildData data = reader.read();

            assertNotNull(data.getBuild());
            assertEquals("Empty Build", data.getBuild().getName());
            assertEquals(0, data.getBuild().getParts().size());
            assertEquals(0, data.getInventory().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBuildData() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBuildData.json");
        try {
            BuildData data = reader.read();
            Build build = data.getBuild();
            List<Part> inventory = data.getInventory();

            // build
            assertEquals("Supra Build", build.getName());
            assertEquals(2, build.getParts().size());

            Part firstBuildPart = build.getParts().get(0);
            assertEquals("BBS RS", firstBuildPart.getName());
            assertEquals(800, firstBuildPart.getCost());

            //parts inside build
            Part secondBuildPart = build.getParts().get(1);
            assertEquals("TRD Spoiler", secondBuildPart.getName());
            assertEquals(300, secondBuildPart.getCost());

            //inventory
            assertEquals(2, inventory.size());

            Part firstInventoryPart = inventory.get(0);
            assertEquals("OEM Bumper", firstInventoryPart.getName());
            assertEquals(400, firstInventoryPart.getCost());

            Part secondInventoryPart = inventory.get(1);
            assertEquals("Mishimoto Radiator", secondInventoryPart.getName());
            assertEquals(250, secondInventoryPart.getCost());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderUnknownCategory() {
        try {
            String json = """
            {
            "build": {
                "name": "Invalid Build",
                "parts": [
                {"category":"UnknownPart","name":"Mystery","cost":100}
                ]
            },
            "inventory": []
            }
            """;

            Path path = Paths.get("./data/testReaderUnknownCategory.json");
            Files.writeString(path, json);

            JsonReader reader = new JsonReader(path.toString());
            assertThrows(org.json.JSONException.class, reader::read);

        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

}
