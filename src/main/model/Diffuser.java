package model;

/**
 * Represents a rear diffuser component.
 * Immutable once constructed.
 */

public class Diffuser extends Part {
    private final String material;
    private final String brand;
    private final boolean functional;

    // REQUIRES: name/material/brand non-null && non-empty; cost >= 0
    // MODIFIES: this
    // EFFECTS:  constructs a diffuser with given specs
    public Diffuser(String name, int cost, String material, String brand, boolean functional) {
        super(name, cost);
        // stub
    }

    // EFFECTS: returns material
    public String getMaterial() {
        return null;  // stub
    }

    // EFFECTS: returns brand
    public String getBrand() {
        return null;  // stub
    } 

    // EFFECTS: returns whether diffuser is functional
    public boolean isFunctional() {
        return false;  // stub
    }
}
