package model;

/**
 * Represents a suspension component
 * Immutable once constructed
 */

public class Suspension extends Part {
  
    private final String type;
    private final int dropMm;  

    //REQUIRES: name != null && non-empty; type != null && non-empty;
    //          cost >= 0; dropMm >= 0
    //MODIFIES: this
    //EFFECTS:  constructs a suspension component with given specs
    public Suspension(String name, int cost, String type, int dropMm) {
        super(name, cost);
        //stub
    }

    // EFFECTS:  returns the suspension type
    public String getType() {
        return null;  // stub
    }


    //EFFECTS:  returns ride height change (mm)
    public int getDropMm() {
        return 0; // stub
    }
}
