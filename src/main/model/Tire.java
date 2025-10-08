package model;

/**
 * Represents a tire specification.
 * Immutable once constructed.
 */

public class Tire extends Part {
    
    private final int widthMm;
    private final int aspectPercent; 
    private final double rimDiameterIn; 

    /**
     * REQUIRES: name != null && non-empty; cost >= 0;
     *           widthMm > 0; aspectPercent > 0; rimDiameterIn > 0
     * MODIFIES: this
     * EFFECTS:  constructs a tire with the given specs
     */
    public Tire(String name, int cost, int widthMm, int aspectPercent, double rimDiameterIn) {
        super(name, cost);
         // stub
    }


    //EFFECTS:  returns section width (mm)
    public int getWidthMm() {
        return 0;  // stub
    }

    //EFFECTS:  returns aspect ratio (percent)
    public int getAspectPercent() {
        return 0;  // stub
    }

    //EFFECTS:  returns matching rim diameter (inches)
    public double getRimDiameterIn() {
        return 0.0;  // stub
    }
}
