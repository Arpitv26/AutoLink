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

    // EFFECTS: returns this side skirts component as a JSON object including all its fields
    @Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject json = super.toJson();
        json.put("material", material);
        json.put("brand", brand);
        return json;
    }


}
