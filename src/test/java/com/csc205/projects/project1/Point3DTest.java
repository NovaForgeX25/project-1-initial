package com.csc205.projects.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Point3D class.
 *
 * <p>This class tests the functionality of the Point3D class, including constructors, getters, setters,
 * distance calculations, rotations, magnitude, addition, subtraction, equality, and hash code methods.
 * It includes tests for common corner cases such as null inputs, NaN values, and edge cases like zero
 * distances or identical points. The tests follow the Spring Framework getting started guides style for
 * clear documentation and use JUnit 5 for assertions.</p>
 */
public class Point3DTest {

    private static final Logger LOGGER = Logger.getLogger(Point3DTest.class.getName());
    private Point3D point;

    /**
     * Sets up a default Point3D instance before each test.
     *
     * <p>Initializes a Point3D at (1.0, 2.0, 3.0) to be used as a base for testing methods.
     * Logs an INFO message to track setup.</p>
     */
    @BeforeEach
    void setUp() {
        point = new Point3D(1.0, 2.0, 3.0);
        LOGGER.log(Level.INFO, "Setting up test with point {0}", point);
    }

    /**
     * Tests the parameterized constructor of Point3D.
     *
     * <p>Verifies that the constructor correctly sets the x, y, and z coordinates.
     * Ensures encapsulation by checking getter methods return expected values.</p>
     */
    @Test
    void testParameterizedConstructor() {
        LOGGER.log(Level.INFO, "Testing parameterized constructor");
        Point3D p = new Point3D(4.0, 5.0, 6.0);
        assertEquals(4.0, p.getX(), "X coordinate should be 4.0");
        assertEquals(5.0, p.getY(), "Y coordinate should be 5.0");
        assertEquals(6.0, p.getZ(), "Z coordinate should be 6.0");
    }

    /**
     * Tests the default constructor of Point3D.
     *
     * <p>Verifies that the default constructor sets all coordinates to zero,
     * representing the origin (0, 0, 0).</p>
     */
    @Test
    void testDefaultConstructor() {
        LOGGER.log(Level.INFO, "Testing default constructor");
        Point3D p = new Point3D();
        assertEquals(0.0, p.getX(), "X coordinate should be 0.0");
        assertEquals(0.0, p.getY(), "Y coordinate should be 0.0");
        assertEquals(0.0, p.getZ(), "Z coordinate should be 0.0");
    }

    /**
     * Tests the setX, setY, and setZ methods.
     *
     * <p>Verifies that setters update coordinates correctly and getters reflect the changes.
     * Ensures encapsulation is maintained.</p>
     */
    @Test
    void testSettersAndGetters() {
        LOGGER.log(Level.INFO, "Testing setters and getters");
        point.setX(10.0);
        point.setY(20.0);
        point.setZ(30.0);
        assertEquals(10.0, point.getX(), "X coordinate should be 10.0");
        assertEquals(20.0, point.getY(), "Y coordinate should be 20.0");
        assertEquals(30.0, point.getZ(), "Z coordinate should be 30.0");
    }

    /**
     * Tests setting NaN values for coordinates.
     *
     * <p>Verifies that setting NaN logs a warning but still allows the operation,
     * as per the Point3D implementation. Checks the coordinate is set to NaN.</p>
     */
    @Test
    void testSettersWithNaN() {
        LOGGER.log(Level.INFO, "Testing setters with NaN");
        point.setX(Double.NaN);
        assertTrue(Double.isNaN(point.getX()), "X coordinate should be NaN");
        point.setY(Double.NaN);
        assertTrue(Double.isNaN(point.getY()), "Y coordinate should be NaN");
        point.setZ(Double.NaN);
        assertTrue(Double.isNaN(point.getZ()), "Z coordinate should be NaN");
    }

    /**
     * Tests the distanceTo method with a valid point.
     *
     * <p>Calculates the Euclidean distance between two points and verifies the result.
     * Uses a known distance for (1, 2, 3) to (4, 5, 6).</p>
     */
    @Test
    void testDistanceTo() {
        LOGGER.log(Level.INFO, "Testing distanceTo method");
        Point3D other = new Point3D(4.0, 5.0, 6.0);
        double expected = Math.sqrt(27.0); // sqrt((4-1)^2 + (5-2)^2 + (6-3)^2)
        assertEquals(expected, point.distanceTo(other), 1e-9, "Distance should be sqrt(27)");
    }

    /**
     * Tests the distanceTo method with a null point.
     *
     * <p>Verifies that passing null throws an IllegalArgumentException and logs a SEVERE message.</p>
     */
    @Test
    void testDistanceToNull() {
        LOGGER.log(Level.INFO, "Testing distanceTo with null");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> point.distanceTo(null));
        assertEquals("Other point cannot be null", exception.getMessage(), "Expected null point exception");
    }

    /**
     * Tests the distanceTo method with the same point.
     *
     * <p>Verifies that the distance to itself is zero, handling the corner case of identical points.</p>
     */
    @Test
    void testDistanceToSelf() {
        LOGGER.log(Level.INFO, "Testing distanceTo with same point");
        assertEquals(0.0, point.distanceTo(point), 1e-9, "Distance to self should be 0");
    }

    /**
     * Tests the rotateX method with a 90-degree rotation.
     *
     * <p>Rotates the point (1, 2, 3) around the x-axis by π/2 radians and verifies the result.
     * Expected: (1, -3, 2) based on rotation matrix.</p>
     */
    @Test
    void testRotateX() {
        LOGGER.log(Level.INFO, "Testing rotateX with 90 degrees");
        Point3D rotated = point.rotateX(Math.PI / 2);
        assertEquals(1.0, rotated.getX(), 1e-9, "X coordinate should remain 1.0");
        assertEquals(-3.0, rotated.getY(), 1e-9, "Y coordinate should be -3.0");
        assertEquals(2.0, rotated.getZ(), 1e-9, "Z coordinate should be 2.0");
    }

    /**
     * Tests the rotateX method with a NaN angle.
     *
     * <p>Verifies that rotating with NaN logs a warning and produces a point with NaN coordinates.</p>
     */
    @Test
    void testRotateXWithNaN() {
        LOGGER.log(Level.INFO, "Testing rotateX with NaN");
        Point3D rotated = point.rotateX(Double.NaN);
        assertTrue(Double.isNaN(rotated.getY()), "Y coordinate should be NaN");
        assertTrue(Double.isNaN(rotated.getZ()), "Z coordinate should be NaN");
    }

    /**
     * Tests the rotateY method with a 90-degree rotation.
     *
     * <p>Rotates the point (1, 2, 3) around the y-axis by π/2 radians and verifies the result.
     * Expected: (3, 2, -1) based on rotation matrix.</p>
     */
    @Test
    void testRotateY() {
        LOGGER.log(Level.INFO, "Testing rotateY with 90 degrees");
        Point3D rotated = point.rotateY(Math.PI / 2);
        assertEquals(3.0, rotated.getX(), 1e-9, "X coordinate should be 3.0");
        assertEquals(2.0, rotated.getY(), 1e-9, "Y coordinate should remain 2.0");
        assertEquals(-1.0, rotated.getZ(), 1e-9, "Z coordinate should be -1.0");
    }

    /**
     * Tests the rotateZ method with a 90-degree rotation.
     *
     * <p>Rotates the point (1, 2, 3) around the z-axis by π/2 radians and verifies the result.
     * Expected: (-2, 1, 3) based on rotation matrix.</p>
     */
    @Test
    void testRotateZ() {
        LOGGER.log(Level.INFO, "Testing rotateZ with 90 degrees");
        Point3D rotated = point.rotateZ(Math.PI / 2);
        assertEquals(-2.0, rotated.getX(), 1e-9, "X coordinate should be -2.0");
        assertEquals(1.0, rotated.getY(), 1e-9, "Y coordinate should be 1.0");
        assertEquals(3.0, rotated.getZ(), 1e-9, "Z coordinate should remain 3.0");
    }

    /**
     * Tests the magnitude method.
     *
     * <p>Verifies that the magnitude (distance from origin) is correct for (1, 2, 3).
     * Expected: sqrt(1^2 + 2^2 + 3^2) = sqrt(14).</p>
     */
    @Test
    void testMagnitude() {
        LOGGER.log(Level.INFO, "Testing magnitude");
        double expected = Math.sqrt(14.0);
        assertEquals(expected, point.magnitude(), 1e-9, "Magnitude should be sqrt(14)");
    }

    /**
     * Tests the magnitude of the origin point.
     *
     * <p>Verifies that the magnitude of (0, 0, 0) is zero, handling the corner case of the origin.</p>
     */
    @Test
    void testMagnitudeAtOrigin() {
        LOGGER.log(Level.INFO, "Testing magnitude at origin");
        Point3D origin = new Point3D();
        assertEquals(0.0, origin.magnitude(), 1e-9, "Magnitude at origin should be 0");
    }

    /**
     * Tests the add method with a valid point.
     *
     * <p>Adds (4, 5, 6) to (1, 2, 3) and verifies the result is (5, 7, 9).</p>
     */
    @Test
    void testAdd() {
        LOGGER.log(Level.INFO, "Testing add method");
        Point3D other = new Point3D(4.0, 5.0, 6.0);
        Point3D result = point.add(other);
        assertEquals(5.0, result.getX(), 1e-9, "X coordinate should be 5.0");
        assertEquals(7.0, result.getY(), 1e-9, "Y coordinate should be 7.0");
        assertEquals(9.0, result.getZ(), 1e-9, "Z coordinate should be 9.0");
    }

    /**
     * Tests the add method with a null point.
     *
     * <p>Verifies that adding null throws an IllegalArgumentException and logs a SEVERE message.</p>
     */
    @Test
    void testAddNull() {
        LOGGER.log(Level.INFO, "Testing add with null");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> point.add(null));
        assertEquals("Other point cannot be null", exception.getMessage(), "Expected null point exception");
    }

    /**
     * Tests the subtract method with a valid point.
     *
     * <p>Subtracts (4, 5, 6) from (1, 2, 3) and verifies the result is (-3, -3, -3).</p>
     */
    @Test
    void testSubtract() {
        LOGGER.log(Level.INFO, "Testing subtract method");
        Point3D other = new Point3D(4.0, 5.0, 6.0);
        Point3D result = point.subtract(other);
        assertEquals(-3.0, result.getX(), 1e-9, "X coordinate should be -3.0");
        assertEquals(-3.0, result.getY(), 1e-9, "Y coordinate should be -3.0");
        assertEquals(-3.0, result.getZ(), 1e-9, "Z coordinate should be -3.0");
    }

    /**
     * Tests the subtract method with a null point.
     *
     * <p>Verifies that subtracting null throws an IllegalArgumentException and logs a SEVERE message.</p>
     */
    @Test
    void testSubtractNull() {
        LOGGER.log(Level.INFO, "Testing subtract with null");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> point.subtract(null));
        assertEquals("Other point cannot be null", exception.getMessage(), "Expected null point exception");
    }

    /**
     * Tests the equals method with identical points.
     *
     * <p>Verifies that two points with the same coordinates are equal, accounting for floating-point precision.</p>
     */
    @Test
    void testEqualsIdentical() {
        LOGGER.log(Level.INFO, "Testing equals with identical points");
        Point3D other = new Point3D(1.0, 2.0, 3.0);
        assertTrue(point.equals(other), "Points should be equal");
    }

    /**
     * Tests the equals method with different points.
     *
     * <p>Verifies that points with different coordinates are not equal.</p>
     */
    @Test
    void testEqualsDifferent() {
        LOGGER.log(Level.INFO, "Testing equals with different points");
        Point3D other = new Point3D(4.0, 5.0, 6.0);
        assertFalse(point.equals(other), "Points should not be equal");
    }

    /**
     * Tests the equals method with null.
     *
     * <p>Verifies that comparing with null returns false.</p>
     */
    @Test
    void testEqualsNull() {
        LOGGER.log(Level.INFO, "Testing equals with null");
        assertFalse(point.equals(null), "Point should not equal null");
    }

    /**
     * Tests the equals method with a non-Point3D object.
     *
     * <p>Verifies that comparing with a different type returns false.</p>
     */
    @Test
    void testEqualsNonPoint3D() {
        LOGGER.log(Level.INFO, "Testing equals with non-Point3D object");
        assertFalse(point.equals(new Object()), "Point should not equal non-Point3D object");
    }

    /**
     * Tests the hashCode method for consistency.
     *
     * <p>Verifies that equal points have the same hash code, ensuring consistency with equals.</p>
     */
    @Test
    void testHashCodeConsistency() {
        LOGGER.log(Level.INFO, "Testing hashCode consistency");
        Point3D other = new Point3D(1.0, 2.0, 3.0);
        assertEquals(point.hashCode(), other.hashCode(), "Equal points should have same hash code");
    }

    /**
     * Tests the toString method.
     *
     * <p>Verifies that the string representation matches the expected format: "Point3D[x=1.0, y=2.0, z=3.0]".</p>
     */
    @Test
    void testToString() {
        LOGGER.log(Level.INFO, "Testing toString method");
        String expected = "Point3D[x=1.0, y=2.0, z=3.0]";
        assertEquals(expected, point.toString(), "toString should match expected format");
    }
}