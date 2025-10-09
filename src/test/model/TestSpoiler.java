package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Spoiler class

public class TestSpoiler {

    private Spoiler spoiler;

    @BeforeEach
    public void runBefore() {
        spoiler = new Spoiler("Voltex GT Wing", 1800, "carbon fiber", "GT", 320.5);
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("Voltex GT Wing", spoiler.getName());
        assertEquals(1800, spoiler.getCost());
        assertEquals("carbon fiber", spoiler.getMaterial());
        assertEquals("GT", spoiler.getStyle());
        assertEquals(320.5, spoiler.getHeightMm());
    }

    @Test
    public void testInvalidConstructorNullMaterial() {
        assertThrows(IllegalArgumentException.class, () ->
                new Spoiler("OEM Lip", 400, null, "Lip", 100.0));
    }

    @Test
    public void testInvalidConstructorEmptyStyle() {
        assertThrows(IllegalArgumentException.class, () ->
                new Spoiler("Ducktail Spoiler", 600, "fiberglass", "", 150.0));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Spoiler("Carbon Lip", -100, "carbon fiber", "Lip", 120.0));
    }

    @Test
    public void testInvalidConstructorNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () ->
                new Spoiler("APR Wing", 1300, "carbon fiber", "GT", -25.0));
    }
}
