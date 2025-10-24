package model;

/**
 * Represents an exhaust component.
 * Immutable once constructed.
 */

public class Exhaust extends Part {
    private final String spec; 

    // REQUIRES: name != null && non-empty; spec != null && non-empty; cost >= 0
    // MODIFIES: this
    // EFFECTS:  constructs an exhaust with the given spec
    public Exhaust(String name, int cost, String spec) {
        super(name, cost);
        validateString(spec, "spec");
        this.spec = spec;
    }

    // EFFECTS:  returns the spec (brand/model/notes)
    public String getSpec() {
        return spec; 
    }

    // EFFECTS: returns this exhaust as a JSON object including all its fields
    @Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject json = super.toJson();
        json.put("spec", spec);
        return json;
    }


}
