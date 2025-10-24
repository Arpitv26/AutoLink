package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Part abstract class
@ExcludeFromJacocoGeneratedReport
public class PartTest {

    private Part testPart;

    // A simple subclass of Part for testing purposes
    private static class RandomPart extends Part {
        public RandomPart(String name, int cost) {
            super(name, cost);
        }
    }

    @BeforeEach
    public void runBefore() {
        testPart = new RandomPart("Generic Part", 500);
    }

    @Test
    public void testValidConstructorAndGetters() {
        assertEquals("Generic Part", testPart.getName());
        assertEquals(500, testPart.getCost());
    }

    @Test
    public void testInvalidConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () ->
                new RandomPart(null, 200));
    }

    @Test
    public void testInvalidConstructorEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
                new RandomPart("", 200));
    }

    @Test
    public void testInvalidConstructorNegativeCost() {
        assertThrows(IllegalArgumentException.class, () ->
                new RandomPart("Invalid Part", -100));
    }
}
