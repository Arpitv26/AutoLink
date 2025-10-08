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
        // stub
    }

    // EFFECTS: returns the engine type
    public String getType() {
        return null;  // stub
    }

    // EFFECTS: returns engine horsepower
    public int getHorsepower() {
        return 0;  // stub
    }

    // EFFECTS: returns engine displacement in litres
    public double getDisplacement() {
        return 0.0;  // stub
    }
}
