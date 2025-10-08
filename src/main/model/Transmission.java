package model;

/**
 * Represents a transmission component in the build.
 * Immutable once constructed.
 */

public class Transmission extends Part {

    private final String type;
    private final int gears;
    private final String drive;

    // REQUIRES: name/type/drive non-null && non-empty; cost >= 0; gears > 0
    // MODIFIES: this
    // EFFECTS:  constructs a transmission with given specs
    public Transmission(String name, int cost, String type, int gears, String drive) {
        super(name, cost);
        // stub
    }

    // EFFECTS: returns transmission type
    public String getType() {
        return null;  // stub
    }

    // EFFECTS: returns number of gears
    public int getGears() {
        return 0;  // stub
    }

    // EFFECTS: returns drive layout (RWD, FWD, AWD)
    public String getDrive() {
        return null;  // stub
    }
}
