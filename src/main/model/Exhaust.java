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
        if (spec == null || spec.trim().isEmpty()) {
            throw new IllegalArgumentException("spec can not be null or empty!");
        }
        this.spec = spec;
    }

    // EFFECTS:  returns the spec (brand/model/notes)
    public String getSpec() {
        return spec; 
    }
}
