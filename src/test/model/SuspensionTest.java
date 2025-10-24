package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Suspension class
@ExcludeFromJacocoGeneratedReport
public class SuspensionTest {

    private Suspension suspension;

    @BeforeEach
    public void runBefore() {
        suspension = new Suspension("BC Racing Coilovers", 1200, "coilover", 35);
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("BC Racing Coilovers", suspension.getName());
        assertEquals(1200, suspension.getCost());
        assertEquals("coilover", suspension.getType());
        assertEquals(35, suspension.getDropMm());
    }

    @Test
    public void testInvalidConstructorNullType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Suspension("KW Coilovers", 1500, null, 30));
    }

    @Test
    public void testInvalidConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Suspension("", 1000, "air suspension", 50));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Suspension("AirLift Performance", -300, "air", 60));
    }

    @Test
    public void testInvalidConstructorNeg() {
        assertThrows(IllegalArgumentException.class, () ->
        new Suspension("Tein Flex Z", 1100, "coilover", -10));
    }

    @Test
    public void testToJson() {
        org.json.JSONObject json = suspension.toJson();
        assertEquals("Suspension", json.getString("category"));
        assertEquals("BC Racing Coilovers", json.getString("name"));
        assertEquals(1200, json.getInt("cost"));
        assertEquals("coilover", json.getString("type"));
        assertEquals(35, json.getInt("dropMm"));
    }

}