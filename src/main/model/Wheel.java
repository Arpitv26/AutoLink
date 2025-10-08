package model;

/**
 * Represents a wheel specification.
 * Immutable once constructed.
 */

public class Wheel extends Part {

    private final double diameterIn; 
    private final double widthIn;   
    private final int offsetMm;      

    // REQUIRES: name != null && non-empty; cost >= 0;
    //          diameterIn > 0; widthIn > 0
    // MODIFIES: this
    // EFFECTS:  constructs a wheel with the given specs
    public Wheel(String name, int cost, double diameterIn, double widthIn, int offsetMm) {
        super(name, cost);
        // stub
    }

     // EFFECTS:  returns rim diameter (inches)
    public double getDiameterIn() {
        return 0.0; // stub
    }


    //EFFECTS:  returns rim width (inches)
    public double getWidthIn() {
        return 0.0; // stub
    }


    //EFFECTS:  returns offset (mm)
    public int getOffsetMm() {
        return 0; // stub
    }
}
