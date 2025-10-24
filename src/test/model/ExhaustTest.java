package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Exhaust class
@ExcludeFromJacocoGeneratedReport
public class ExhaustTest {

    private Exhaust exhaust;

    @BeforeEach
    public void runBefore() {
        exhaust = new Exhaust("HKS Hi-Power", 950, "Stainless steel performance exhaust");
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("HKS Hi-Power", exhaust.getName());
        assertEquals(950, exhaust.getCost());
        assertEquals("Stainless steel performance exhaust", exhaust.getSpec());
    }

    @Test
    public void testInvalidConstructorNullSpec() {
        assertThrows(IllegalArgumentException.class, () ->
                new Exhaust("Greddy EVO", 875, null));
    }

    @Test
    public void testInvalidConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Exhaust("", 600, "Titanium street spec"));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Exhaust("Tomei Type-R", -300, "Track edition"));
    }

    @Test
    public void testToJson() {
        org.json.JSONObject json = exhaust.toJson();
        assertEquals("Exhaust", json.getString("category"));
        assertEquals("HKS Hi-Power", json.getString("name"));
        assertEquals(950, json.getInt("cost"));
        assertEquals("Stainless steel performance exhaust", json.getString("spec"));
    }

}
