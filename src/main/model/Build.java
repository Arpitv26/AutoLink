package model;

import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * Represents the user's single active build.
 * Holds an inventory of parts, active selections, and fitment baseline.
 * This class is mutable.
 */


public class Build {
    //inventory and lists
    private final List<Part> inventory;
    private final Set<String> nameIndex;

    //active parts on the build
    private Wheel activeWheel;
    private Tire activeTire;
    private Suspension activeSuspension;
    private Exhaust activeExhaust;
    private Engine activeEngine;
    private Transmission activeTransmission;
    private Bumper activeBumper;    
    private SideSkirts activeSideSkirts;
    private Diffuser activeDiffuser;
    private Spoiler activeSpoiler;
    private Lights activeLights;    


    //default values either user-set or not
    private Double referenceOdMm;              // user-set default for rolling diameter
    private final double rollingDeltaThresholdPct; // in this case will be setting it to a default of 3% for the program

    // MODIFIES: this
    // EFFECTS:  constructs an empty build with no baseline; threshold set to 3.0
    public Build() {
        // stub
    }

    //                                                 Inventory management
    // -----------------------------------------------------------------------------------------------------------------

    // REQUIRES: p != null; p.getName() unique (not already in inventory)
    // MODIFIES: this
    // EFFECTS:  adds part p to inventory and returns true; otherwise returns false
    public boolean addPart(Part p) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  removes the part with given name from inventory (if present) and returns true;
    //           if it was active in its category, clears that active reference; returns false if not found
    public boolean removePartByName(String name) {
        return false; // stub
    }

    // REQUIRES: category != null && non-empty; newName != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a part with name newName exists and matches the given category,
    //           removes the currently active part of that category,
    //           sets the new part active, and returns true;
    //           returns false if the newName part does not exist or is of a different category
    public boolean replaceActivePart(String category, String newName) {
        return false; // stub
    }

    // EFFECTS:  returns a defensive copy of all parts in inventory
    public List<Part> listAllParts() {
        return null; // stub
    }

    //                                                  Active selections
    // -----------------------------------------------------------------------------------------------------------------


    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Wheel with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveWheel(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Tire with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveTire(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Suspension with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveSuspension(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if an Exhaust with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveExhaust(String name) {
        return false; // stub
    }

        // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if an Engine with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveEngine(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Transmission with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveTransmission(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Bumper with the given name exists, sets it active (front or rear, stored in type) and returns true
    //           otherwise returns false
    public boolean setActiveBumper(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a SideSkirts with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveSideSkirts(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Diffuser with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveDiffuser(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Spoiler with the given name exists, sets it active and returns true; otherwise returns false
    public boolean setActiveSpoiler(String name) {
        return false; // stub
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a Lights with the given name exists, sets it active (headlights or taillights, stored in type)
    //           and returns true; otherwise returns false
    public boolean setActiveLights(String name) {
        return false; // stub
    }



    //                                                Baseline + fitment
    // -----------------------------------------------------------------------------------------------------------------

    // REQUIRES: widthMm > 0; aspectPct > 0; rimIn > 0
    // MODIFIES: this
    // EFFECTS:  sets the baseline rolling diameter (mm) using the given tire size
    public void setBaselineTire(int widthMm, int aspectPct, double rimIn) {
        // stub
    }

    // EFFECTS:  returns a list of warnings for the current active selections:
    //           - warns "Set baseline tire before running fitment check" if baseline not set
    //           - warns "Set active wheel and tire to run fitment check" if either activeWheel or activeTire is null
    //           - warns "Wheel and tire diameters do not match" if tire.rimDiameterIn != wheel.diameterIn
    //           - warns "Rolling diameter difference exceeds ±3%" if delta vs. baseline > threshold
    public List<String> runFitmentCheck() {
        return null; // stub
    }

    //                                                    Summary
    // -----------------------------------------------------------------------------------------------------------------

    // EFFECTS:  returns the total cost of active parts only
    public int totalCost() {
        return 0; // stub
    }
    // EFFECTS:  returns counts of parts by category name
    //           result is a defensive copy; ordering unspecified

    public Map<String, Integer> countsByCategory() {
        return null; // stub
    }


    //                                              Helper Methods
    // -----------------------------------------------------------------------------------------------------------------

    // REQUIRES: name != null && non-empty
    // EFFECTS:  returns the part in inventory with the given name;
    //           returns null if no such part exists
    public Part getPartByName(String name) {
        return null; // stub
    }

}
