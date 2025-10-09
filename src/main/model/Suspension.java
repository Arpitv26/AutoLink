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
        validateString(type, "type");
        if (dropMm < 0) {
            throw new IllegalArgumentException("drop should be more than 0!");
        }

        this.type = type;
        this.dropMm = dropMm;
    }

    // EFFECTS:  returns the suspension type
    public String getType() {
        return type;
    }


    //EFFECTS:  returns ride height change (mm)
    public int getDropMm() {
        return dropMm;
    }
}
