package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Wheel class
public class TestWheel {

    private Wheel wheel;

    @BeforeEach
    public void runBefore() {
        wheel = new Wheel("Volk TE37", 3200, 18.0, 9.5, 22);
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("Volk TE37", wheel.getName());
        assertEquals(3200, wheel.getCost());
        assertEquals(18.0, wheel.getDiameterIn());
        assertEquals(9.5, wheel.getWidthIn());
        assertEquals(22, wheel.getOffsetMm());
    }

    @Test
    public void testInvalidConstructorZeroDiameter() {
        assertThrows(IllegalArgumentException.class, () ->
                new Wheel("Enkei RPF1", 1500, 0.0, 8.5, 35));
    }

    @Test
    public void testInvalidConstructorNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () ->
                new Wheel("Work Emotion", 2000, 18.0, -9.5, 30));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Wheel("BBS LM", -2500, 19.0, 10.0, 25));
    }

    @Test
    public void testInvalidConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Wheel("", 1800, 17.0, 8.0, 35));
    }
}
