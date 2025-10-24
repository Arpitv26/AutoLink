package model;

import java.util.*;



// Represents the user's single active build.
// Holds an inventory of parts, active selections, and fitment defaults and baseline
// This class is mutable

public class Build {
    //inventory of all parts and lists
    private final List<Part> inventory;
    private final Set<String> nameIndex;
    private String name; //phase 2 addition for data persistence

    //active subParts we can have
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

    //private Double referenceOdMm;                  // user-set default for rolling diameter
    //private final double rollingDeltaThresholdPct; // in this case will be set to a default of 3% for the program

    // EFFECTS: constructs an empty build with default name "MyBuild"
    public Build() {
        this("MyBuild");
    }
    

    // MODIFIES: this
    // EFFECTS:  constructs an empty build with no baseline; threshold set to 3.0
    public Build(String name) {
        inventory = new ArrayList<>();
        nameIndex = new HashSet<>();
        this.name = name;

        //referenceOdMm = null;
        //rollingDeltaThresholdPct = 3.0;

    }


    // EFFECTS: returns the name of this build
    public String getName() {
        return name;
    }
    

    //                                                 Inventory management
    // -----------------------------------------------------------------------------------------------------------------

    // REQUIRES: p != null; p.getName() unique (not already in inventory)
    // MODIFIES: this
    // EFFECTS:  adds part p to inventory and returns true; otherwise returns false
    public boolean addPart(Part p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (nameIndex.contains(p.getName())) {
            return false;
        } else {
            inventory.add(p);
            nameIndex.add(p.getName());
            return true;
        }
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS:  removes the part with given name from inventory (if present) and returns true;
    //           if it was active in its category, clears that active reference; returns false if not found
    public boolean removePartByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }

        Part target = null;
        for (Part p : inventory) {
            if (p.getName().equals(name)) {
                target = p;
                break;
            }
        }
        if (target == null) {
            return false;
        }

        inventory.remove(target);
        nameIndex.remove(name);
        clearIfActive(target);
        return true;
    }

    // REQUIRES: category != null && non-empty; newName != null && non-empty
    // MODIFIES: this
    // EFFECTS:  if a part with name newName exists and matches the given category,
    //           removes the currently active part of that category,
    //           sets the new part active, and returns true.
    //           returns false if the newName part does not exist or is of a different category
    public boolean replaceActivePart(String category, String newName) {
        if (category == null || category.trim().isEmpty() || newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        Part candidate = getPartByName(newName);
        if (candidate == null) {
            return false;
        }
        String cat = normalizeCategory(category);

        return switch (cat) {
            case "wheel"        -> (candidate instanceof Wheel)        && assignWheel((Wheel) candidate);
            case "tire"         -> (candidate instanceof Tire)         && assignTire((Tire) candidate);
            case "suspension"   -> (candidate instanceof Suspension)   && assignSuspension((Suspension) candidate);
            case "exhaust"      -> (candidate instanceof Exhaust)      && assignExhaust((Exhaust) candidate);
            case "engine"       -> (candidate instanceof Engine)       && assignEngine((Engine) candidate);
            case "transmission" -> (candidate instanceof Transmission) && assignTransmission((Transmission) candidate);
            case "bumper"       -> (candidate instanceof Bumper)       && assignBumper((Bumper) candidate);
            case "sideskirts"   -> (candidate instanceof SideSkirts)   && assignSideSkirts((SideSkirts) candidate);
            case "diffuser"     -> (candidate instanceof Diffuser)     && assignDiffuser((Diffuser) candidate);
            case "spoiler"      -> (candidate instanceof Spoiler)      && assignSpoiler((Spoiler) candidate);
            case "lights"       -> (candidate instanceof Lights)       && assignLights((Lights) candidate);
            default             -> false;
        };
    }

    // EFFECTS: returns a copy of all parts in inventory
    public List<Part> listAllParts() {
        return new ArrayList<>(inventory);
    }

    // Active selections
    // -----------------------------------------------------------------------------------------------------------------

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if a Wheel with the given name exists, sets it active and returns
    // true; otherwise returns false
    public boolean setActiveWheel(String name) {
        Part p = getPartByName(name);
        if (p instanceof Wheel) {
            activeWheel = (Wheel) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if a Tire with the given name exists, sets it active and returns
    // true; otherwise returns false
    public boolean setActiveTire(String name) {
        Part p = getPartByName(name);
        if (p instanceof Tire) {
            activeTire = (Tire) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if a Suspension with the given name exists, sets it active and
    // returns true; otherwise returns false
    public boolean setActiveSuspension(String name) {
        Part p = getPartByName(name);
        if (p instanceof Suspension) {
            activeSuspension = (Suspension) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if an Exhaust with the given name exists, sets it active and returns
    // true; otherwise returns false
    public boolean setActiveExhaust(String name) {
        Part p = getPartByName(name);
        if (p instanceof Exhaust) {
            activeExhaust = (Exhaust) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if an Engine with the given name exists, sets it active and returns
    // true; otherwise returns false
    public boolean setActiveEngine(String name) {
        Part p = getPartByName(name);
        if (p instanceof Engine) {
            activeEngine = (Engine) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if a Transmission with the given name exists, sets it active and
    //          returns true; otherwise returns false
    public boolean setActiveTransmission(String name) {
        Part p = getPartByName(name);
        if (p instanceof Transmission) {
            activeTransmission = (Transmission) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if a Bumper with the given name exists, sets it active (front or
    //          rear, stored in type) and returns true
    //          otherwise returns false
    public boolean setActiveBumper(String name) {
        Part p = getPartByName(name);
        if (p instanceof Bumper) {
            activeBumper = (Bumper) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if a SideSkirts with the given name exists, sets it active and
    //          returns true; otherwise returns false
    public boolean setActiveSideSkirts(String name) {
        Part p = getPartByName(name);
        if (p instanceof SideSkirts) {
            activeSideSkirts = (SideSkirts) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null and non-empty
    // MODIFIES: this
    // EFFECTS: if a Diffuser with the given name exists it sets it active and returns
    //          true otherwise returns false
    public boolean setActiveDiffuser(String name) {
        Part p = getPartByName(name);
        if (p instanceof Diffuser) {
            activeDiffuser = (Diffuser) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null && non-empty
    // MODIFIES: this
    // EFFECTS: if a spoiler with the given name exists it sets it active and returns
    //          true otherwise returns false
    public boolean setActiveSpoiler(String name) {
        Part p = getPartByName(name);
        if (p instanceof Spoiler) {
            activeSpoiler = (Spoiler) p;
            return true;
        }
        return false;
    }

    // REQUIRES: name != null and non-empty
    // MODIFIES: this
    // EFFECTS: if a Lights with the given name exists then it sets it active
    //          and returns true otherwise returns false
    public boolean setActiveLights(String name) {
        Part p = getPartByName(name);
        if (p instanceof Lights) {
            activeLights = (Lights) p;
            return true;
        }
        return false;
    }

    // REQUIRES: category != null && non-empty
    // EFFECTS: returns the active Part for the given category key or null if none
    public Part getActive(String category) {
        String c = normalizeCategory(category);
        return switch (c) {
            case "wheel"        -> activeWheel;
            case "tire"         -> activeTire;
            case "suspension"   -> activeSuspension;
            case "exhaust"      -> activeExhaust;
            case "engine"       -> activeEngine;
            case "transmission" -> activeTransmission;
            case "bumper"       -> activeBumper;
            case "sideskirts"   -> activeSideSkirts;
            case "diffuser"     -> activeDiffuser;
            case "spoiler"      -> activeSpoiler;
            case "lights"       -> activeLights;
            default             -> null;
        };
    }


    // REQUIRES: category != null && non-empty
    // MODIFIES: this
    // EFFECTS: clears the active selection for the given category or returns true if category is known
    public boolean clearActive(String category) {
        String c = normalizeCategory(category);
        switch (c) {
            case "wheel" -> activeWheel = null;
            case "tire" -> activeTire = null;
            case "suspension" -> activeSuspension = null;
            case "exhaust" -> activeExhaust = null;
            case "engine" -> activeEngine = null;
            case "transmission" -> activeTransmission = null;
            case "bumper" -> activeBumper = null;
            case "sideskirts" -> activeSideSkirts = null;
            case "diffuser" -> activeDiffuser = null;
            case "spoiler" -> activeSpoiler = null;
            case "lights" -> activeLights = null;
            default -> {
                return false;
            }
        }
        return true;
    }


    //                                              Baseline + fitment
    // -----------------------------------------------------------------------------------------------------------------

/** will implement this later on 
    // REQUIRES: widthMm > 0 and aspectPct > 0 and rimIn > 0
    // MODIFIES: this
    // EFFECTS: sets the baseline rolling diameter in millimeters using the given tire size
    public void setBaselineTire(int widthMm, int aspectPct, double rimIn) {
        if (widthMm <= 0 || aspectPct <= 0 || rimIn <= 0) {
            throw new IllegalArgumentException();
        }
        referenceOdMm = computeRollingDiameterMm(widthMm, aspectPct, rimIn);
    }

    // EFFECTS: returns a list of warnings for the current active selections:
    //          - warns "Set baseline tire before running fitment check" if baseline not set
    //          - warns "Set active wheel and tire to run fitment check" if either
    //            activeWheel or activeTire is null
    //          - warns "Wheel and tire diameters do not match" if tire.rimDiameterIn !=
    //            wheel.diameterIn
    //          - warns "Rolling diameter difference exceeds ±3%" if delta vs. baseline >
    //            threshold
        ListString warnings = new ArrayList();
        if (referenceOdMm == null) {
            warnings.add("Set baseline tire before running fitment check");
        }
        if (activeWheel == null || activeTire == null) {
            warnings.add("Set active wheel and tire to run fitment check");
            return warnings;
        }
        double wheelDiaIn = activeWheel.getDiameterIn();
        double tireRimIn = activeTire.getRimDiameterIn();
        if (Math.abs(wheelDiaIn - tireRimIn) > 1e-6) {
            warnings.add("Wheel and tire diameters do not match");
        }
        if (referenceOdMm != null) {
            int w = activeTire.getWidthMm();
            int a = activeTire.getAspectPercent();
            double r = activeTire.getRimDiameterIn();
            double currentOd = computeRollingDiameterMm(w, a, r);
            double deltaPct = Math.abs((currentOd - referenceOdMm) / referenceOdMm) * 100.0;
            if (deltaPct > rollingDeltaThresholdPct) {
                warnings.add("Rolling diameter difference exceeds ±3%");
            }
        }
        return warnings;
    }
**/


    //                                                  Summary
    // -----------------------------------------------------------------------------------------------------------------

    // EFFECTS: returns the total cost of active parts only
    @SuppressWarnings("methodlength")
    public int totalCost() {
        int sum = 0;
        if (activeWheel != null) {
            sum += activeWheel.getCost();
        }
        if (activeTire != null) {
            sum += activeTire.getCost();
        }
        if (activeSuspension != null) {
            sum += activeSuspension.getCost();
        }
        if (activeExhaust != null) {
            sum += activeExhaust.getCost();
        }
        if (activeEngine != null) {
            sum += activeEngine.getCost();
        }
        if (activeTransmission != null) {
            sum += activeTransmission.getCost();
        }
        if (activeBumper != null) {
            sum += activeBumper.getCost();
        }
        if (activeSideSkirts != null) {
            sum += activeSideSkirts.getCost();
        }
        if (activeDiffuser != null) {
            sum += activeDiffuser.getCost();
        }
        if (activeSpoiler != null) {
            sum += activeSpoiler.getCost();
        }
        if (activeLights != null) {
            sum += activeLights.getCost();
        }
        return sum;
    }
    // EFFECTS:  returns counts of parts by category name
    //           result is a copy

    public Map<String, Integer> countsByCategory() {
        Map<String, Integer> counts = new HashMap<>();
        for (Part p : inventory) {
            String key = p.getClass().getSimpleName();
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }
        return counts;
    }

    //                                              Helper Methods
    // -----------------------------------------------------------------------------------------------------------------

    // REQUIRES: name != null && non-empty
    // EFFECTS:  returns the part in inventory with the given name;
    //           returns null if no such part exists
    public Part getPartByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        } 
       
        for (Part p : inventory) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;   
    }

/** 
    // REQUIRES: widthMm > 0; aspectPct > 0; rimIn > 0
    // MODIFIES: nothing
    // EFFECTS:  returns rolling outer diameter in millimeters using standard tire formula
    private static double computeRollingDiameterMm(int widthMm, int aspectPct, double rimIn) {
        double sidewall = widthMm * (aspectPct / 100.0);
        return rimIn * 25.4 + 2.0 * sidewall;
    }
**/
    @SuppressWarnings("methodlength")
    private void clearIfActive(Part target) {
        if (target == activeWheel) {
            activeWheel = null;
        }
        if (target == activeTire) {
            activeTire = null;
        }
        if (target == activeSuspension) {
            activeSuspension = null;
        }
        if (target == activeExhaust) {
            activeExhaust = null;
        }
        if (target == activeEngine) {
            activeEngine = null;
        }
        if (target == activeTransmission) {
            activeTransmission = null;
        }
        if (target == activeBumper) {
            activeBumper = null;
        }
        if (target == activeSideSkirts) {
            activeSideSkirts = null;
        }
        if (target == activeDiffuser) {
            activeDiffuser = null;
        }
        if (target == activeSpoiler) {
            activeSpoiler = null;
        }
        if (target == activeLights) {
            activeLights = null;
        }
    }

    // REQUIRES: s != null
    // MODIFIES: nothing
    // EFFECTS:  returns category key
    private static String normalizeCategory(String s) {
        String c = s.trim().toLowerCase();
        if (c.equals("wheels")) {
            c = "wheel";
        }
        if (c.equals("tires")) {
            c = "tire";
        }
        if (c.equals("bumpers")) {
            c = "bumper";
        }
        if (c.equals("side skirts")) {
            c = "sideskirts";
        }
        return c;
    }

    // REQUIRES: w != null
    // MODIFIES: this
    // EFFECTS:  removes previous active wheel from inventory/index (if any), sets new active, returns true
    private boolean assignWheel(Wheel w) {
        if (activeWheel != null) { 
            inventory.remove(activeWheel); 
            nameIndex.remove(activeWheel.getName()); 
        }
        activeWheel = w; 
        return true;
    }

    // REQUIRES: t != null
    // MODIFIES: this
    // EFFECTS:  removes previous active tire, sets new, returns true
    private boolean assignTire(Tire t) {
        if (activeTire != null) { 
            inventory.remove(activeTire); 
            nameIndex.remove(activeTire.getName()); 
        }
        activeTire = t; 
        return true;
    }

    // REQUIRES: s != null
    // MODIFIES: this
    // EFFECTS:  removes previous active suspension (if any), sets new, returns true
    private boolean assignSuspension(Suspension s) {
        if (activeSuspension != null) { 
            inventory.remove(activeSuspension);
            nameIndex.remove(activeSuspension.getName()); 
        }
        activeSuspension = s; 
        return true;
    }

    // REQUIRES: e != null
    // MODIFIES: this
    // EFFECTS:  removes previous active exhaust (if any), sets new, returns true
    private boolean assignExhaust(Exhaust e) {
        if (activeExhaust != null) {
            inventory.remove(activeExhaust); 
            nameIndex.remove(activeExhaust.getName());
        }
        activeExhaust = e; 
        return true;
    }

    // REQUIRES: e != null
    // MODIFIES: this
    // EFFECTS:  removes previous active engine (if any), sets new, returns true
    private boolean assignEngine(Engine e) {
        if (activeEngine != null) {
            inventory.remove(activeEngine);
            nameIndex.remove(activeEngine.getName());
        }
        activeEngine = e;
        return true;
    }

    // REQUIRES: t != null
    // MODIFIES: this
    // EFFECTS:  removes previous active transmission (if any), sets new, returns true
    private boolean assignTransmission(Transmission t) {
        if (activeTransmission != null) {
            inventory.remove(activeTransmission);
            nameIndex.remove(activeTransmission.getName());
        }
        activeTransmission = t;
        return true;
    }

    // REQUIRES: b != null
    // MODIFIES: this
    // EFFECTS:  removes previous active bumper (if any), sets new, returns true
    private boolean assignBumper(Bumper b) {
        if (activeBumper != null) {
            inventory.remove(activeBumper);
            nameIndex.remove(activeBumper.getName());
        }
        activeBumper = b; 
        return true;
    }

    // REQUIRES: s != null
    // MODIFIES: this
    // EFFECTS:  removes previous active side skirts (if any), sets new, returns true
    private boolean assignSideSkirts(SideSkirts s) {
        if (activeSideSkirts != null) {
            inventory.remove(activeSideSkirts); 
            nameIndex.remove(activeSideSkirts.getName());
        }
        activeSideSkirts = s; 
        return true;
    }

    // REQUIRES: d != null
    // MODIFIES: this
    // EFFECTS:  removes previous active diffuser and sets new, returns true
    private boolean assignDiffuser(Diffuser d) {
        if (activeDiffuser != null) {
            inventory.remove(activeDiffuser);
            nameIndex.remove(activeDiffuser.getName());
        }
        activeDiffuser = d;
        return true;
    }

    // REQUIRES: s != null
    // MODIFIES: this
    // EFFECTS:  removes previous active spoiler (if any), sets new, returns true
    private boolean assignSpoiler(Spoiler s) {
        if (activeSpoiler != null) {
            inventory.remove(activeSpoiler);
            nameIndex.remove(activeSpoiler.getName());
        }
        activeSpoiler = s;
        return true;
    }

    // REQUIRES: l != null
    // MODIFIES: this
    // EFFECTS:  removes previous active lights (if any), sets new, returns true
    private boolean assignLights(Lights l) {
        if (activeLights != null) {
            inventory.remove(activeLights);
            nameIndex.remove(activeLights.getName());
        }
        activeLights = l; 
        return true;
    }

    // EFFECTS: returns all parts in the build's inventory
    public List<Part> getParts() {
        return new ArrayList<>(inventory);
    }


}