package persistence;

import model.Build;
import model.Part;
import java.util.List;

// Helper class
// Represents combined AutoLink data containing the active build and parts inventory

public class BuildData {
    private Build build;
    private List<Part> inventory;

    // REQUIRES: build and inventory not null
    // MODIFIES: this
    // EFFECTS: constructs BuildData object with given build and inventory list
    public BuildData(Build build, List<Part> inventory) {
        this.build = build;
        this.inventory = inventory;
    }

    // EFFECTS: returns the active build
    public Build getBuild() {
        return build;
    }

    // EFFECTS: returns the list of parts representing inventory
    public List<Part> getInventory() {
        return inventory;
    }
}
