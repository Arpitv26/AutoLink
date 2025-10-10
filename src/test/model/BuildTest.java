package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Build class

public class BuildTest {

    private Build build;

    private Wheel w18;
    private Wheel w19;
    private Tire t18;
    private Tire t19;
    private Suspension sus;
    private Exhaust exh;
    private Engine eng;
    private Transmission trans;
    private Bumper bumpFront;
    private SideSkirts skirts;
    private Diffuser dif;
    private Spoiler spl;
    private Lights head;
    private Lights tail;

    @BeforeEach
    public void runBefore() {
        build = new Build();

        w18 = new Wheel("TE37-18x95-ET22", 3200, 18.0, 9.5, 22);
        w19 = new Wheel("LM-19x10-ET25", 4000, 19.0, 10.0, 25);

        t18 = new Tire("PS4S-245-40R18", 1400, 245, 40, 18.0);
        t19 = new Tire("PS4S-245-35R19", 1450, 245, 35, 19.0);

        sus = new Suspension("BC Coilovers", 1200, "coilover", 35);
        exh = new Exhaust("HKS Hi-Power", 950, "Stainless");

        eng = new Engine("2JZ-GTE", 12000, "Inline-6", 320, 3.0);
        trans = new Transmission("GR6", 8000, "Dual-Clutch", 6, "AWD");

        bumpFront = new Bumper("Mugen Front", 1200, "front", "carbon fiber", "Mugen", "aggressive");
        skirts = new SideSkirts("TRD Skirts", 650, "fiberglass", "TRD");
        dif = new Diffuser("APR Diffuser", 950, "carbon fiber", "APR", true);
        spl = new Spoiler("Voltex GT", 1800, "carbon fiber", "GT", 320.5);

        head = new Lights("OEM Heads", 600, "headlights", "OEM", "Halogen", "4300K");
        tail = new Lights("Valenti Tails", 800, "taillights", "Valenti", "LED", "smoked");
    }

    //                                                Inventory management
    // --------------------------------------------------------------------------------------------------------------


    @Test
    public void testAddPart() {
        assertTrue(build.addPart(w18));
        assertTrue(build.addPart(t18));

        List<Part> all = build.listAllParts();
        assertNotNull(all);
        assertEquals(2, all.size());

        // copy check
        all.clear();
        assertEquals(2, build.listAllParts().size());
    }

    @Test
    public void testAddPartDuplicateName() {
        assertTrue(build.addPart(w18));
        Wheel sameNameDifferentObject = new Wheel("TE37-18x95-ET22", 9999, 18.0, 8.0, 30);
        assertFalse(build.addPart(sameNameDifferentObject));
        assertEquals(1, build.listAllParts().size());
    }

    @Test
    public void testAddPartNull() {
        assertThrows(IllegalArgumentException.class, () -> build.addPart(null));
    }

    @Test
    public void testRemovePartByName() {
        assertTrue(build.addPart(w18));
        assertTrue(build.setActiveWheel(w18.getName()));

        assertTrue(build.removePartByName(w18.getName()));
        assertNull(build.getPartByName(w18.getName()));
        assertFalse(build.setActiveWheel(w18.getName())); 
        assertEquals(0, build.listAllParts().size());   
    }

    @Test
    public void testRemovePartByUnknownName() {
        assertFalse(build.removePartByName("does not exist"));
    }

    @Test
    public void testRemovePartByNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> build.removePartByName(null));
        assertThrows(IllegalArgumentException.class, () -> build.removePartByName("   "));
    }

    @Test
    public void testRemovePartByNameNotFound() {
        assertFalse(build.removePartByName("does not exist"));
    }

    @Test
    public void testRemovePartByNameClearsActive() {
        Wheel w1 = new Wheel("W", 1000, 18.0, 8.0, 35);
        build.addPart(w1);
        assertTrue(build.setActiveWheel("W"));
        assertTrue(build.removePartByName("W"));
        assertNull(build.getActive("wheel"));
    }

    //throws on null or empty
    @Test
    public void testGetPartByNameThrowsOnNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> build.getPartByName(null));
        assertThrows(IllegalArgumentException.class, () -> build.getPartByName("  "));
    }

    @Test
    public void testGetPartByNameWhenMissing() {
        assertNull(build.getPartByName("nope"));
    }

    @Test
    public void testListAllPartsReturnsCopy() {
        Wheel w = new Wheel("W", 1000, 18.0, 8.0, 35);
        build.addPart(w);
        List<Part> copy = build.listAllParts();
        assertEquals(1, copy.size());
        copy.clear(); 
        assertEquals(1, build.listAllParts().size()); //to see for not affecting inventory
    }


    //                                                  Active selections
    // --------------------------------------------------------------------------------------------------------------

    @Test
    public void testSetActivePart() {
        List<Part> parts = List.of(w18, t18, sus, exh, eng, trans, bumpFront, skirts, dif, spl, head);

        for (Part p : parts) {
            assertTrue(build.addPart(p));
        }

        assertTrue(build.setActiveWheel(w18.getName()));
        assertTrue(build.setActiveTire(t18.getName()));
        assertTrue(build.setActiveSuspension(sus.getName()));
        assertTrue(build.setActiveExhaust(exh.getName()));
        assertTrue(build.setActiveEngine(eng.getName()));
        assertTrue(build.setActiveTransmission(trans.getName()));
        assertTrue(build.setActiveBumper(bumpFront.getName()));
        assertTrue(build.setActiveSideSkirts(skirts.getName()));
        assertTrue(build.setActiveDiffuser(dif.getName()));
        assertTrue(build.setActiveSpoiler(spl.getName()));
        assertTrue(build.setActiveLights(head.getName()));
    }

    @Test
    public void testSetActiveWrongType() {
        assertTrue(build.addPart(t18));
        assertFalse(build.setActiveWheel(t18.getName())); 
    }

    @Test
    public void testReplaceActivePart() {
        assertTrue(build.addPart(w18));
        assertTrue(build.addPart(w19));
        assertTrue(build.setActiveWheel(w18.getName()));

        assertTrue(build.replaceActivePart("wheel", w19.getName()));
        assertNull(build.getPartByName(w18.getName())); 
        assertFalse(build.setActiveWheel(w18.getName()));
    }

    @Test
    public void testReplaceActivePartWrongCategory() {
        assertTrue(build.addPart(w18));
        assertFalse(build.replaceActivePart("tire", w18.getName())); 
    }


    @Test
public void testGetActiveVariants() {

    Wheel w = new Wheel("W", 1000, 18.0, 8.0, 35);
    Tire t = new Tire("T", 700, 245, 40, 18.0);
    Bumper b = new Bumper("B", 500, "front", "cf", "X", "style");
    SideSkirts s = new SideSkirts("SS", 300, "fg", "Y");

    build.addPart(w); build.addPart(t); build.addPart(b); build.addPart(s);
    assertTrue(build.setActiveWheel("W"));
    assertTrue(build.setActiveTire("T"));
    assertTrue(build.setActiveBumper("B"));
    assertTrue(build.setActiveSideSkirts("SS"));


    assertEquals(w, build.getActive("wheels"));
    assertEquals(t, build.getActive("TIRES"));
    assertEquals(b, build.getActive("bumpers"));
    assertEquals(s, build.getActive("side skirts"));


    assertNull(build.getActive("no-such-category"));
}

//individually testing all categories
    @Test
    public void testClearActiveAlln() {
        List<Part> parts = List.of(
            new Wheel("W", 1, 18.0, 8.0, 35),
            new Tire("T", 1, 245, 40, 18.0),
            new Suspension("SUS", 1, "coilover", 10),
            new Exhaust("EXH", 1, "ss"),
            new Engine("ENG", 1, "i4", 100, 2.0),
            new Transmission("TRN", 1, "auto", 6, "FWD"),
            new Bumper("B", 1, "front", "cf", "X", "style"),
            new SideSkirts("SS", 1, "fg", "Y"),
            new Diffuser("DIF", 1, "cf", "Z", true),
            new Spoiler("SPL", 1, "cf", "gt", 10.0),
            new Lights("L", 1, "headlights", "oem", "halogen", "4300K")
        );
        for (Part p : parts) { build.addPart(p); }
        assertTrue(build.setActiveWheel("W"));
        assertTrue(build.setActiveTire("T"));
        assertTrue(build.setActiveSuspension("SUS"));
        assertTrue(build.setActiveExhaust("EXH"));
        assertTrue(build.setActiveEngine("ENG"));
        assertTrue(build.setActiveTransmission("TRN"));
        assertTrue(build.setActiveBumper("B"));
        assertTrue(build.setActiveSideSkirts("SS"));
        assertTrue(build.setActiveDiffuser("DIF"));
        assertTrue(build.setActiveSpoiler("SPL"));
        assertTrue(build.setActiveLights("L"));


        assertTrue(build.clearActive("wheel"));
        assertTrue(build.clearActive("tire"));
        assertTrue(build.clearActive("suspension"));
        assertTrue(build.clearActive("exhaust"));
        assertTrue(build.clearActive("engine"));
        assertTrue(build.clearActive("transmission"));
        assertTrue(build.clearActive("bumper"));
        assertTrue(build.clearActive("sideskirts"));
        assertTrue(build.clearActive("diffuser"));
        assertTrue(build.clearActive("spoiler"));
        assertTrue(build.clearActive("lights"));

        assertFalse(build.clearActive("unknown"));
    }

    @Test
    public void testReplaceActivePartRemovesPreviousActiveForAllCategories() {
    
        build.addPart(w18); build.addPart(w19);
        assertTrue(build.replaceActivePart("wheels", w18.getName()));
        assertTrue(build.replaceActivePart("wheel", w19.getName()));
        assertNull(build.getPartByName(w18.getName()));


        build.addPart(t18); build.addPart(t19);
        assertTrue(build.replaceActivePart("tires", t18.getName()));
        assertTrue(build.replaceActivePart("tire", t19.getName()));
        assertNull(build.getPartByName(t18.getName()));

        build.addPart(sus);
        Suspension sus2 = new Suspension("SUS-2", 2, "coilover", 15);
        build.addPart(sus2);
        assertTrue(build.replaceActivePart("suspension", sus.getName()));
        assertTrue(build.replaceActivePart("suspension", sus2.getName()));
        assertNull(build.getPartByName(sus.getName()));

  
        build.addPart(exh);
        Exhaust exh2 = new Exhaust("EXH-2", 2, "stainless");
        build.addPart(exh2);
        assertTrue(build.replaceActivePart("exhaust", exh.getName()));
        assertTrue(build.replaceActivePart("exhaust", exh2.getName()));
        assertNull(build.getPartByName(exh.getName()));


        build.addPart(eng);
        Engine eng2 = new Engine("ENG-2", 2, "Inline-4", 180, 2.0);
        build.addPart(eng2);
        assertTrue(build.replaceActivePart("engine", eng.getName()));
        assertTrue(build.replaceActivePart("engine", eng2.getName()));
        assertNull(build.getPartByName(eng.getName()));

        build.addPart(trans);
        Transmission trans2 = new Transmission("TRN-2", 2, "Manual", 6, "RWD");
        build.addPart(trans2);
        assertTrue(build.replaceActivePart("transmission", trans.getName()));
        assertTrue(build.replaceActivePart("transmission", trans2.getName()));
        assertNull(build.getPartByName(trans.getName()));


        build.addPart(bumpFront);
        Bumper bumpRear = new Bumper("Mugen Rear", 1100, "rear", "carbon fiber", "Mugen", "aggressive");
        build.addPart(bumpRear);
        assertTrue(build.replaceActivePart("bumpers", bumpFront.getName())); 
        assertTrue(build.replaceActivePart("bumper", bumpRear.getName()));
        assertNull(build.getPartByName(bumpFront.getName()));

        build.addPart(skirts);
        SideSkirts skirts2 = new SideSkirts("TRD Skirts V2", 700, "fiberglass", "TRD");
        build.addPart(skirts2);
        assertTrue(build.replaceActivePart("side skirts", skirts.getName()));
        assertTrue(build.replaceActivePart("sideskirts", skirts2.getName()));
        assertNull(build.getPartByName(skirts.getName()));

   
        build.addPart(dif);
        Diffuser dif2 = new Diffuser("APR Diffuser V2", 999, "carbon fiber", "APR", false);
        build.addPart(dif2);
        assertTrue(build.replaceActivePart("diffuser", dif.getName()));
        assertTrue(build.replaceActivePart("diffuser", dif2.getName()));
        assertNull(build.getPartByName(dif.getName()));

      
        build.addPart(spl);
        Spoiler spl2 = new Spoiler("Voltex GT Type-2", 1900, "carbon fiber", "GT", 300.0);
        build.addPart(spl2);
        assertTrue(build.replaceActivePart("spoiler", spl.getName()));
        assertTrue(build.replaceActivePart("spoiler", spl2.getName()));
        assertNull(build.getPartByName(spl.getName()));


        build.addPart(head); build.addPart(tail);
        assertTrue(build.replaceActivePart("lights", head.getName()));
        assertTrue(build.replaceActivePart("lights", tail.getName()));
        assertNull(build.getPartByName(head.getName()));
    }



    //                                                  Baseline & fitment
    // --------------------------------------------------------------------------------------------------------------



    /** --------------> these tests will be uncommented when fitment related methods will be re-implemented
    @Test
    public void testRunFitmentWarningNoBaseline() {
        assertTrue(build.addPart(w18));
        assertTrue(build.addPart(t18));
        assertTrue(build.setActiveWheel(w18.getName()));
        assertTrue(build.setActiveTire(t18.getName()));

        List<String> warnings = build.runFitmentCheck();
        assertNotNull(warnings);
        assertTrue(warnings.contains("Set baseline tire before running fitment check"));
    }

    @Test
    public void testRunFitmentWarns() {
        build.setBaselineTire(245, 35, 19.0); 
        List<String> warnings = build.runFitmentCheck();
        assertNotNull(warnings);
        assertTrue(warnings.contains("Set active wheel and tire to run fitment check"));
    }

    @Test
    public void testRunFitmentWarningsRimDiameter() {
        build.setBaselineTire(245, 35, 19.0);
        assertTrue(build.addPart(w18));
        assertTrue(build.addPart(t19));
        assertTrue(build.setActiveWheel(w18.getName())); 
        assertTrue(build.setActiveTire(t19.getName()));

        List<String> warnings = build.runFitmentCheck();
        assertNotNull(warnings);
        assertTrue(warnings.contains("Wheel and tire diameters do not match"));
    }

    @Test
    public void testRunFitmentWithinThreePercent() {
        build.setBaselineTire(245, 35, 19.0);
        assertTrue(build.addPart(w19));
        assertTrue(build.addPart(t19));
        assertTrue(build.setActiveWheel(w19.getName()));
        assertTrue(build.setActiveTire(t19.getName()));

        List<String> warnings = build.runFitmentCheck();
        assertNotNull(warnings);
        // Probably will include other warnings in future, but just to ensure the rolling diameter 
        // warning is not present
        assertFalse(warnings.contains("Rolling diameter difference exceeds ±3%"));
    }

    @Test
    public void testRunFitmentOverThreePercent() {
        build.setBaselineTire(245, 30, 19.0);
        assertTrue(build.addPart(w18));
        assertTrue(build.addPart(t18));
        assertTrue(build.setActiveWheel(w18.getName()));
        assertTrue(build.setActiveTire(t18.getName()));

        List<String> warnings = build.runFitmentCheck();
        assertNotNull(warnings);
        assertTrue(warnings.contains("Rolling diameter difference exceeds ±3%"));
    }

**/

    //                                                   Summary
    // --------------------------------------------------------------------------------------------------------------

    @Test
    public void testTotalCostOfActiveParts() {
        List<Part> parts = List.of(w18, t18, sus, exh, eng, trans, bumpFront, skirts, dif, spl, head);
        for (Part p : parts) {
            assertTrue(build.addPart(p));
        }

        build.setActiveWheel(w18.getName());
        build.setActiveTire(t18.getName());
        build.setActiveSuspension(sus.getName());
        build.setActiveExhaust(exh.getName());
        build.setActiveEngine(eng.getName());
        build.setActiveTransmission(trans.getName());
        build.setActiveBumper(bumpFront.getName());
        build.setActiveSideSkirts(skirts.getName());
        build.setActiveDiffuser(dif.getName());
        build.setActiveSpoiler(spl.getName());
        build.setActiveLights(head.getName());

        int expected = parts.stream().mapToInt(Part::getCost).sum();
        assertEquals(expected, build.totalCost());
    }

    @Test
    public void testCountsByCategory() {
        List<Part> parts = List.of(w18, w19, t18, t19, sus, exh, bumpFront, head, tail);
        for (Part p : parts) {
            assertTrue(build.addPart(p));
        }

        Map<String, Integer> counts = build.countsByCategory();
        assertNotNull(counts);

        Map<String, Integer> expected = Map.of(
                "Wheel", 2, "Tire", 2, "Suspension", 1,
                "Exhaust", 1, "Bumper", 1, "Lights", 2);

        for (String category : expected.keySet()) {
            assertEquals(expected.get(category), counts.getOrDefault(category, 0));
        }
    }

    //                                                  Helpers
    // --------------------------------------------------------------------------------------------------------------

    @Test
    public void testGetPartByName() {
        assertTrue(build.addPart(w18));
        Part found = build.getPartByName(w18.getName());
        assertNotNull(found);
        assertEquals(w18.getName(), found.getName());
    }

    @Test
    public void testGetPartByMissingName() {
        assertNull(build.getPartByName("nope"));
    }
}
