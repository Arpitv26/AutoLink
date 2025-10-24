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
        validateString(type, "type");
        validateString(brand, "brand");
        validateString(lightType, "lightType");
        validateString(detail, "detail");

        this.type = type;
        this.brand = brand;
        this.lightType = lightType;
        this.detail = detail;
        
    }

    // EFFECTS: returns lighting component type ("headlights" or "taillights")
    public String getType() {
        return type;  
    }

    // EFFECTS: returns manufacturer brand
    public String getBrand() {
        return brand;
    } 

    // EFFECTS: returns light type (e.g., LED, HID)
    public String getLightType() {
        return lightType;  
    }

    // EFFECTS: returns extra detail (colour temp or tint)
    public String getDetail() {
        return detail;  
    }

    // EFFECTS: returns this lights component as a JSON object including all its fields
    @Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject json = super.toJson();
        json.put("type", type);
        json.put("brand", brand);
        json.put("lightType", lightType);
        json.put("detail", detail);
        return json;
    }

}
