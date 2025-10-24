package model;

import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents a generic part that can be added to a build.
 * Each part has a user-given unique name and a cost in CAD.
 * This class is immutable.
 */

public abstract class Part implements Writable {

    private final String name;
    private final int cost;     

    // REQUIRES: name != null && name is non-empty; cost >= 0
    // MODIFIES: this
    // EFFECTS:  constructs a part with the given name and cost
    public Part(String name, int cost) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must be not null or empty!");
        }

        if (cost < 0) {
            throw new IllegalArgumentException("cost must be more than 0!");
        }

        this.name = name;
        this.cost = cost;
    }

    // EFFECTS: returns the user-given unique name of this part
    public String getName() {
        return name;
    }

    //EFFECTS:  returns the cost of this part in CAD (whole dollars)
    public int getCost() {
        return cost;
    }


    //helper method for exception handling and validation for each subPart constructor
    protected static void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty!");
        }
    }

    // EFFECTS: returns basic JSON with category, name, cost
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("category", getClass().getSimpleName());
        json.put("name", name);
        json.put("cost", cost);
        return json;
    }

}
