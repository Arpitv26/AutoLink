package model;

/**
 * Represents a pair of side skirts.
 * Immutable once constructed.
 */

public class SideSkirts extends Part {
    private final String material;
    private final String brand;

    // REQUIRES: name/material/brand non-null && non-empty; cost >= 0
    // MODIFIES: this
    // EFFECTS:  constructs side skirts with given specs
    public SideSkirts(String name, int cost, String material, String brand) {
        super(name, cost);
        validateString(material, "material");
        validateString(brand, "brand");

        this.material = material;
        this.brand = brand;
    }

    // EFFECTS: returns material
    public String getMaterial() {
        return material;
    }

    // EFFECTS: returns brand
    public String getBrand() {
        return brand;
    }

}
