package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Transmission class

public class TestTransmission {

    private Transmission transmission;

    @BeforeEach
    public void runBefore() {
        transmission = new Transmission("GR6 DCT", 8000, "Dual-Clutch", 6, "AWD");
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("GR6 DCT", transmission.getName());
        assertEquals(8000, transmission.getCost());
        assertEquals("Dual-Clutch", transmission.getType());
        assertEquals(6, transmission.getGears());
        assertEquals("AWD", transmission.getDrive());
    }

    @Test
    public void testInvalidConstructorNullType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transmission("Tremec TR6060", 7000, null, 6, "RWD"));
    }

    @Test
    public void testInvalidConstructorEmptyDrive() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transmission("ZF 8HP", 5000, "Automatic", 8, ""));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transmission("Aisin Manual", -3000, "Manual", 5, "FWD"));
    }

    @Test
    public void testInvalidConstructorZeroGears() {
        assertThrows(IllegalArgumentException.class, () ->
                new Transmission("Custom Sequential", 10000, "Sequential", 0, "RWD"));
    }
}
