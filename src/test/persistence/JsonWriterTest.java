package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Citation: Modelled after the JsonWriterTest class in the WorkRoom example provided in the CPSC 210 Term Project Task 3

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
    }
}
