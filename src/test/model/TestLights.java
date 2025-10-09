package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Lights class

public class TestLights {

    private Lights lights;

    @BeforeEach
    public void runBefore() {
        lights = new Lights("Valenti LED Tails", 800, "taillights", "Valenti", "LED", "smoked finish");
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("Valenti LED Tails", lights.getName());
        assertEquals(800, lights.getCost());
        assertEquals("taillights", lights.getType());
        assertEquals("Valenti", lights.getBrand());
        assertEquals("LED", lights.getLightType());
        assertEquals("smoked finish", lights.getDetail());
    }

    @Test
    public void testInvalidConstructorNullBrand() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lights("OEM Headlights", 600, "headlights", null, "HID", "6000K"));
    }

    @Test
    public void testInvalidConstructorEmptyType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lights("Aftermarket Headlights", 700, "", "Spyder", "LED", "white tint"));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Lights("HID Retrofits", -100, "headlights", "Morimoto", "HID", "5000K"));
    }
}
