package model;

/**
 * Represents a lighting component (headlights or taillights).
 * Immutable once constructed.
 */

public class Lights extends Part {
    private final String type;   
    private final String brand;    
    private final String lightType; 
    private final String detail;   

    // REQUIRES: name/type/brand/lightType/detail non-null && non-empty; cost >= 0
    // MODIFIES: this
    // EFFECTS:  constructs a lighting component with given specs
    public Lights(String name, int cost, String type, String brand, String lightType, String detail) {
        super(name, cost);
        // stub
    }

    // EFFECTS: returns lighting component type ("headlights" or "taillights")
    public String getType() {
        return null;  // stub
    }

    // EFFECTS: returns manufacturer brand
    public String getBrand() {
        return null;  // stub
    } 

    // EFFECTS: returns light type (e.g., LED, HID)
    public String getLightType() {
        return null;   // stub
    }

    // EFFECTS: returns extra detail (colour temp or tint)
    public String getDetail() {
        return null;   // stub
    }
}
