package model;

/**
 * Represents a spoiler or wing.
 * Immutable once constructed.
 */

public class Spoiler extends Part {
    private final String material;
    private final String style;
    private final double heightMm;

    // REQUIRES: name/material/style non-null && non-empty; cost >= 0; heightMm >= 0
    // MODIFIES: this
    // EFFECTS:  constructs a spoiler with given specs
    public Spoiler(String name, int cost, String material, String style, double heightMm) {
        super(name, cost);
        validateString(material, "material");
        validateString(style, "style");
        if (heightMm < 0) {
            throw new IllegalArgumentException("height should be more than 0!");
        }

        this.material = material;
        this.style = style;
        this.heightMm = heightMm;
    }

    // EFFECTS: returns material
    public String getMaterial() {
        return material;
    }

    // EFFECTS: returns style
    public String getStyle() {
        return style;
    }

    // EFFECTS: returns height in millimetres
    public double getHeightMm() {
        return heightMm;
    } 

    // EFFECTS: returns this spoiler as a JSON object including all its fields
    @Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject json = super.toJson();
        json.put("material", material);
        json.put("style", style);
        json.put("heightMm", heightMm);
        return json;
    }

}
