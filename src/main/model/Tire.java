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


        if (widthMm <= 0 || aspectPercent <= 0 || rimDiameterIn <= 0) {
            throw new IllegalArgumentException("width, aspectPercent, and rimDiameterIn must all be more than 0!");
        }

        this.widthMm = widthMm;
        this.aspectPercent = aspectPercent;
        this.rimDiameterIn = rimDiameterIn;
    }


    //EFFECTS:  returns section width (mm)
    public int getWidthMm() {
        return widthMm;
    }

    //EFFECTS:  returns aspect ratio (percent)
    public int getAspectPercent() {
        return aspectPercent;
    }

    //EFFECTS:  returns matching rim diameter (inches)
    public double getRimDiameterIn() {
        return rimDiameterIn;
    }
}
