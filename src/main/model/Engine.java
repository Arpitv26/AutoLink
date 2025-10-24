package model;

/**
 * Represents an engine component in the build.
 * Immutable once constructed.
 */

public class Engine extends Part {
    private final String type;
    private final int horsepower;
    private final double displacement;

    // REQUIRES: name != null && non-empty; type != null && non-empty;
    //           cost >= 0; horsepower > 0; displacement > 0
    // MODIFIES: this
    // EFFECTS:  constructs an engine with the given specs
    public Engine(String name, int cost, String type, int horsepower, double displacement) {
        super(name, cost);
        validateString(type, "type");

        if (horsepower <= 0) {
            throw new IllegalArgumentException("horsepower has to be more than 0!");
        }
        if (displacement <= 0) {
            throw new IllegalArgumentException("displacement has to be more than 0!");
        }

        this.type = type;
        this.horsepower = horsepower;
        this.displacement = displacement;
    }

    // EFFECTS: returns the engine type
    public String getType() {
        return type; 
    }

    // EFFECTS: returns engine horsepower
    public int getHorsepower() {
        return horsepower;  
    }

    // EFFECTS: returns engine displacement in litres
    public double getDisplacement() {
        return displacement; 
    }

    // EFFECTS: returns this engine as a JSON object including all its fields
    @Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject json = super.toJson();
        json.put("type", type);
        json.put("horsepower", horsepower);
        json.put("displacement", displacement);
        return json;
    }

}
