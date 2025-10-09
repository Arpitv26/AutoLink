package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Engine class

public class TestEngine {

    private Engine engine;

    @BeforeEach
    public void runBefore() {
        engine = new Engine("2JZ-GTE", 12000, "Inline-6", 320, 3.0);
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("2JZ-GTE", engine.getName());
        assertEquals(12000, engine.getCost());
        assertEquals("Inline-6", engine.getType());
        assertEquals(320, engine.getHorsepower());
        assertEquals(3.0, engine.getDisplacement());
    }

    @Test
    public void testInvalidConstructorNullType() {
        assertThrows(IllegalArgumentException.class, () ->
                new Engine("RB26DETT", 15000, null, 280, 2.6));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new Engine("K20A", -5000, "Inline-4", 220, 2.0));
    }

    @Test
    public void testInvalidConstructorZeroHorsepower() {
        assertThrows(IllegalArgumentException.class, () ->
                new Engine("SR20DET", 7000, "Inline-4", 0, 2.0));
    }

    @Test
    public void testInvalidConstructorNegativeDisplacement() {
        assertThrows(IllegalArgumentException.class, () ->
                new Engine("LS3", 9000, "V8", 430, -6.2));
    }
}
