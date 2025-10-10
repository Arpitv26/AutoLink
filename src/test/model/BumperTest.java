package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


// Unit tests for the Bumper class.

public class BumperTest {

    private Bumper bumper;

    @BeforeEach
    public void runBefore() {
        bumper = new Bumper("Mugen Front", 1200, "front", "carbon fiber", "Mugen", "aggressive");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Mugen Front", bumper.getName());
        assertEquals(1200, bumper.getCost());
        assertEquals("front", bumper.getType());
        assertEquals("carbon fiber", bumper.getMaterial());
        assertEquals("Mugen", bumper.getBrand());
        assertEquals("aggressive", bumper.getStyle());
    }

    @Test
    public void testInvalidConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bumper(null, 1000, "front", "plastic", "OEM", "sport"));
    }

    @Test
    public void testInvalidConstructorEmptyType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bumper("OEM Rear", 800, "", "plastic", "OEM", "simple"));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Bumper("OEM Rear", -200, "rear", "plastic", "OEM", "simple"));
    }
}
