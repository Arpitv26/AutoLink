package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the SideSkirts class
@ExcludeFromJacocoGeneratedReport
public class SideSkirtsTest {

    private SideSkirts sideSkirts;

    @BeforeEach
    public void runBefore() {
        sideSkirts = new SideSkirts("TRD Side Skirts", 650, "fiberglass", "TRD");
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("TRD Side Skirts", sideSkirts.getName());
        assertEquals(650, sideSkirts.getCost());
        assertEquals("fiberglass", sideSkirts.getMaterial());
        assertEquals("TRD", sideSkirts.getBrand());
    }

    @Test
    public void testInvalidConstructorNullMaterial() {
        assertThrows(IllegalArgumentException.class, () ->
                new SideSkirts("OEM Side Skirts", 400, null, "OEM"));
    }

    @Test
    public void testInvalidConstructorEmptyBrand() {
        assertThrows(IllegalArgumentException.class, () ->
                new SideSkirts("Aftermarket Side Skirts", 500, "plastic", ""));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new SideSkirts("Carbon Skirts", -100, "carbon fiber", "APR"));
    }
}
