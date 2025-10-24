package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Citation: Modelled after the JsonWriterTest class in example provided in the CPSC 210 Term Project Task 3

// Tests for JsonWriter class
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:file.json");
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
        }
    }

    @Test
    void testWriterEmptyBuildData() {
        try {
            Build build = new Build("Empty Build");
            BuildData data = new BuildData(build, List.of());

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBuildData.json");
            writer.open();
            writer.write(data);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBuildData.json");
            BuildData readData = reader.read();

            assertEquals("Empty Build", readData.getBuild().getName());
            assertEquals(0, readData.getBuild().getParts().size());
            assertEquals(0, readData.getInventory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBuildData() {
        try {
            Build build = new Build("Supra Build");
            build.addPart(new PartStub("BBS RS", 800));
            build.addPart(new PartStub("TRD Spoiler", 300));

            List<Part> inventory = List.of(new PartStub("OEM Bumper", 400),new PartStub("Mishimoto Radiator", 250));

            BuildData data = new BuildData(build, inventory);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBuildData.json");
            writer.open();
            writer.write(data);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBuildData.json");
            BuildData readData = reader.read();

            Build readBuild = readData.getBuild();
            List<Part> readInventory = readData.getInventory();

            assertEquals("Supra Build", readBuild.getName());
            assertEquals(2, readBuild.getParts().size());
            assertEquals(2, readInventory.size());

            assertEquals("BBS RS", readBuild.getParts().get(0).getName());
            assertEquals(800, readBuild.getParts().get(0).getCost());
            assertEquals("TRD Spoiler", readBuild.getParts().get(1).getName());
            assertEquals(300, readBuild.getParts().get(1).getCost());

            assertEquals("OEM Bumper", readInventory.get(0).getName());
            assertEquals(400, readInventory.get(0).getCost());
            assertEquals("Mishimoto Radiator", readInventory.get(1).getName());
            assertEquals(250, readInventory.get(1).getCost());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // helper stub class for testing 
    private static class PartStub extends Part {
        public PartStub(String name, int cost) {
            super(name, cost);
        }
        
        @Override
        public org.json.JSONObject toJson() {
            return super.toJson();
        }
    }

    @Test
    void testReaderAllPartTypes() {
        try {
            String json = """
            {
            "activeBuild": {
                "name": "Full Build",
                "parts": [
                {"category":"Wheel","name":"TE37","cost":3000,"diameterIn":18,"widthIn":9,"offsetMm":22},
                {"category":"Tire","name":"PS4S","cost":1400,"widthMm":245,"aspectPercent":35,"rimDiameterIn":19},
                {"category":"Suspension","name":"KW","cost":2500,"type":"coilover","dropMm":30},
                {"category":"Exhaust","name":"HKS","cost":950,"spec":"stainless"},
                {"category":"Engine","name":"2JZ","cost":12000,"type":"I6","horsepower":320,"displacement":3.0},
                {"category":"Transmission","name":"GR6","cost":8000,"type":"DCT","gears":6,"drive":"AWD"},
                {"category":"Bumper","name":"Mugen","cost":1200,"type":"front","material":"cf","brand":"Mugen","style":"aggressive"},
                {"category":"SideSkirts","name":"TRD","cost":650,"material":"fg","brand":"TRD"},
                {"category":"Diffuser","name":"APR","cost":950,"material":"cf","brand":"APR","functional":true},
                {"category":"Spoiler","name":"Voltex","cost":1800,"material":"cf","style":"GT","heightMm":320.5},
                {"category":"Lights","name":"Valenti","cost":800,"type":"taillight","brand":"Valenti","lightType":"LED","detail":"smoked"}
                ]
            },
            "inventory": []
            }
            """;


            Path path = Paths.get("./data/testReaderAllPartTypes.json");
            Files.writeString(path, json);

            JsonReader reader = new JsonReader(path.toString());
            BuildData data = reader.read();

            Build build = data.getBuild();
            assertEquals("Full Build", build.getName());
            assertEquals(11, build.getParts().size());

            assertEquals("TE37", build.getParts().get(0).getName());
            assertEquals("2JZ", build.getParts().get(4).getName());
            assertEquals("Voltex", build.getParts().get(9).getName());
            assertEquals("Valenti", build.getParts().get(10).getName());

        } catch (IOException e) {
            fail("Should not throw IOException");
        }
    }

}
