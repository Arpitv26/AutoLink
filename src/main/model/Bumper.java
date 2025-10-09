package model;

/**
 * Represents a bumper part that can be either front or back
 * Immutable once constructed.
 */

public class Bumper extends Part {
    private final String type;   
    private final String material;  
    private final String brand;     
    private final String style;     

    // REQUIRES: name,type,material,brand,style non-null and non-empty; cost >= 0
    // MODIFIES: this
    // EFFECTS:  constructs a bumper with given specs
    public Bumper(String name, int cost, String type, String material, String brand, String style) {
        super(name, cost);
        validateString(type, "type");
        validateString(material, "material");
        validateString(brand, "brand");
        validateString(style, "style");

        this.type = type;
        this.material = material;
        this.brand = brand;
        this.style = style;
    }

    // EFFECTS: returns bumper type ("front" or "rear")
    public String getType() {
        return type; 
    } 

    // EFFECTS: returns material
    public String getMaterial() {
        return material;
    }

    // EFFECTS: returns brand
    public String getBrand() {
        return brand;
    }

    // EFFECTS: returns style
    public String getStyle() {
        return style;
    }
}
