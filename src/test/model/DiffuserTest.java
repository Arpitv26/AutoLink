package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Diffuser class
@ExcludeFromJacocoGeneratedReport
public class DiffuserTest {

    private Diffuser diffuser;

    @BeforeEach
    public void runBefore() {
        diffuser = new Diffuser("APR Rear Diffuser", 950, "carbon fiber", "APR", true);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("APR Rear Diffuser", diffuser.getName());
        assertEquals(950, diffuser.getCost());
        assertEquals("carbon fiber", diffuser.getMaterial());
        assertEquals("APR", diffuser.getBrand());
        assertTrue(diffuser.isFunctional());
    }

    @Test
    public void testInvalidConstructorNullMaterial() {
        assertThrows(IllegalArgumentException.class, () ->
                new Diffuser("Generic Diffuser", 400, null, "Generic", false));
    }

    @Test
    public void testInvalidConstructorEmptyBrand() {
        assertThrows(IllegalArgumentException.class, () ->
                new Diffuser("Generic Diffuser", 400, "plastic", "", false));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Diffuser("Cheap Diffuser", -50, "plastic", "eBay", false));
    }
}
