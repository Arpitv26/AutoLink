package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Tire class
@ExcludeFromJacocoGeneratedReport
public class TireTest {

    private Tire tire;

    @BeforeEach
    public void runBefore() {
        tire = new Tire("Michelin Pilot Sport 4S", 1400, 245, 35, 19.0);
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("Michelin Pilot Sport 4S", tire.getName());
        assertEquals(1400, tire.getCost());
        assertEquals(245, tire.getWidthMm());
        assertEquals(35, tire.getAspectPercent());
        assertEquals(19.0, tire.getRimDiameterIn());
    }

    @Test
    public void testInvalidConstructorZeroWidth() {
        assertThrows(IllegalArgumentException.class, () ->
                new Tire("Pirelli P Zero", 1200, 0, 30, 18.0));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Tire("Bridgestone Potenza", -500, 255, 40, 19.0));
    }

    @Test
    public void testInvalidConstructorZeroAspectPercent() {
        assertThrows(IllegalArgumentException.class, () ->
                new Tire("Continental ExtremeContact", 1100, 235, 0, 18.0));
    }

    @Test
    public void testInvalidConstructorNegativeRimDiameter() {
        assertThrows(IllegalArgumentException.class, () ->
                new Tire("Goodyear Eagle F1", 1000, 255, 30, -17.0));
    }
}
