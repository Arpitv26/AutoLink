package model;

/**
 * Represents a generic part that can be added to a build.
 * Each part has a user-given unique name and a cost in CAD.
 * This class is immutable.
 */

public abstract class Part {

    private final String name;
    private final int cost;     

    // REQUIRES: name != null && name is non-empty; cost >= 0
    // MODIFIES: this
    // EFFECTS:  constructs a part with the given name and cost
    public Part(String name, int cost) {
        // stub
    }

    // EFFECTS: returns the user-given unique name of this part
    public String getName() {
        return null; // stub
    }

    //EFFECTS:  returns the cost of this part in CAD (whole dollars)
    public int getCost() {
        return 0; // stub
    }
}
