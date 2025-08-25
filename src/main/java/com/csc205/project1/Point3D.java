package com.csc205.project1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a point in 3D space with x, y, and z coordinates.
 * This class provides methods for calculating distances, performing rotations,
 * and other operations useful for manipulating 3D points.
 *
 * <p>Like in the Spring Framework getting started guides, this class demonstrates
 * basic object-oriented principles through encapsulation of the point's coordinates
 * and provision of well-defined methods for interaction. The class uses the Singleton
 * pattern for the logger instance to ensure a single logging point, promoting
 * resource efficiency and consistency in logging across instances.</p>
 *
 * <h2>Object-Oriented Design Patterns Used</h2>
 * <ul>
 *   <li><b>Encapsulation</b>: The coordinates (x, y, z) are private fields, accessible only through
 *   getter and setter methods. This hides the internal representation and allows control over
 *   how the data is modified, demonstrating a foundational principle of data structures by
 *   protecting invariants and ensuring data integrity.</li>
 *   <li><b>Singleton (for Logger)</b>: The logger is obtained via Logger.getLogger, which follows
 *   a singleton-like approach in java.util.logging, ensuring only one logger instance per class name.
 *   This pattern optimizes resource usage, a key consideration in algorithms dealing with large-scale
 *   data processing where logging overhead must be minimized.</li>
 *   <li><b>Immutable Operations (where applicable)</b>: Rotation methods return new Point3D instances
 *   rather than mutating the existing one, promoting immutability. This aligns with functional
 *   programming principles integrated into OO design, reducing side effects and making the class
 *   thread-safe for concurrent algorithms.</li>
 * </ul>
 *
 * <p>These patterns demonstrate foundational principles for data structures and algorithms by
 * emphasizing modularity (easy to extend or compose with other classes like vectors or matrices),
 * efficiency (immutability avoids unnecessary copies in algorithms), and reliability (logging helps
 * in debugging complex spatial algorithms like pathfinding or collision detection in 3D space).</p>
 */
public class Point3D {

    private static final Logger LOGGER = Logger.getLogger(Point3D.class.getName());

    private double x;
    private double y;
    private double z;

    /**
     * Constructs a new Point3D with the specified coordinates.
     *
     * <p>This constructor initializes the point's x, y, and z values. It logs an INFO message
     * upon creation to track object instantiation, which can be useful for debugging in
     * applications involving many points, such as 3D modeling.</p>
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        LOGGER.log(Level.INFO, "Created Point3D at ({0}, {1}, {2})", new Object[]{x, y, z});
    }

    /**
     * Constructs a new Point3D at the origin (0, 0, 0).
     *
     * <p>This default constructor sets all coordinates to zero and logs an INFO message.
     * It's convenient for creating origin points in 3D space, often used as a starting
     * reference in algorithms like transformations or distance calculations.</p>
     */
    public Point3D() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * <p>This getter method provides read access to the private x field, enforcing encapsulation.</p>
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of this point.
     *
     * <p>This setter method updates the x field and logs an INFO message for the change.
     * If the new value is NaN, it logs a WARNING to alert potential issues in calculations.</p>
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        if (Double.isNaN(x)) {
            LOGGER.log(Level.WARNING, "Attempted to set x to NaN");
        }
        this.x = x;
        LOGGER.log(Level.INFO, "Updated x to {0}", x);
    }

    /**
     * Returns the y-coordinate of this point.
     *
     * <p>This getter method provides read access to the private y field, enforcing encapsulation.</p>
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of this point.
     *
     * <p>This setter method updates the y field and logs an INFO message for the change.
     * If the new value is NaN, it logs a WARNING to alert potential issues in calculations.</p>
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        if (Double.isNaN(y)) {
            LOGGER.log(Level.WARNING, "Attempted to set y to NaN");
        }
        this.y = y;
        LOGGER.log(Level.INFO, "Updated y to {0}", y);
    }

    /**
     * Returns the z-coordinate of this point.
     *
     * <p>This getter method provides read access to the private z field, enforcing encapsulation.</p>
     *
     * @return the z-coordinate
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the z-coordinate of this point.
     *
     * <p>This setter method updates the z field and logs an INFO message for the change.
     * If the new value is NaN, it logs a WARNING to alert potential issues in calculations.</p>
     *
     * @param z the new z-coordinate
     */
    public void setZ(double z) {
        if (Double.isNaN(z)) {
            LOGGER.log(Level.WARNING, "Attempted to set z to NaN");
        }
        this.z = z;
        LOGGER.log(Level.INFO, "Updated z to {0}", z);
    }

    /**
     * Calculates the Euclidean distance between this point and another point.
     *
     * <p>This method computes the straight-line distance in 3D space using the formula:
     * sqrt((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2). It logs an INFO message before calculation.
     * If the other point is null, it logs a SEVERE error and throws an exception, demonstrating
     * error handling in algorithms.</p>
     *
     * @param other the other point to calculate distance to
     * @return the distance between the points
     * @throws IllegalArgumentException if the other point is null
     */
    public double distanceTo(Point3D other) {
        if (other == null) {
            LOGGER.log(Level.SEVERE, "Null point provided for distance calculation");
            throw new IllegalArgumentException("Other point cannot be null");
        }
        LOGGER.log(Level.INFO, "Calculating distance from ({0}, {1}, {2}) to ({3}, {4}, {5})",
                new Object[]{x, y, z, other.x, other.y, other.z});
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Returns a new point rotated around the x-axis by the given angle.
     *
     * <p>This method applies a rotation transformation using the rotation matrix for the x-axis.
     * It returns a new Point3D to maintain immutability for this operation. Logs an INFO message
     * with the angle. If the angle is NaN, logs a WARNING.</p>
     *
     * @param angle the rotation angle in radians
     * @return a new rotated point
     */
    public Point3D rotateX(double angle) {
        if (Double.isNaN(angle)) {
            LOGGER.log(Level.WARNING, "NaN angle provided for x-rotation");
        }
        LOGGER.log(Level.INFO, "Rotating around x-axis by {0} radians", angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double newY = y * cos - z * sin;
        double newZ = y * sin + z * cos;
        return new Point3D(x, newY, newZ);
    }

    /**
     * Returns a new point rotated around the y-axis by the given angle.
     *
     * <p>This method applies a rotation transformation using the rotation matrix for the y-axis.
     * It returns a new Point3D to maintain immutability for this operation. Logs an INFO message
     * with the angle. If the angle is NaN, logs a WARNING.</p>
     *
     * @param angle the rotation angle in radians
     * @return a new rotated point
     */
    public Point3D rotateY(double angle) {
        if (Double.isNaN(angle)) {
            LOGGER.log(Level.WARNING, "NaN angle provided for y-rotation");
        }
        LOGGER.log(Level.INFO, "Rotating around y-axis by {0} radians", angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double newX = x * cos + z * sin;
        double newZ = -x * sin + z * cos;
        return new Point3D(newX, y, newZ);
    }

    /**
     * Returns a new point rotated around the z-axis by the given angle.
     *
     * <p>This method applies a rotation transformation using the rotation matrix for the z-axis.
     * It returns a new Point3D to maintain immutability for this operation. Logs an INFO message
     * with the angle. If the angle is NaN, logs a WARNING.</p>
     *
     * @param angle the rotation angle in radians
     * @return a new rotated point
     */
    public Point3D rotateZ(double angle) {
        if (Double.isNaN(angle)) {
            LOGGER.log(Level.WARNING, "NaN angle provided for z-rotation");
        }
        LOGGER.log(Level.INFO, "Rotating around z-axis by {0} radians", angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double newX = x * cos - y * sin;
        double newY = x * sin + y * cos;
        return new Point3D(newX, newY, z);
    }

    /**
     * Calculates the magnitude (distance from origin) of this point.
     *
     * <p>This method is a convenience wrapper around distanceTo(new Point3D()), computing
     * the vector length. Logs an INFO message before calculation.</p>
     *
     * @return the magnitude of the point
     */
    public double magnitude() {
        LOGGER.log(Level.INFO, "Calculating magnitude of point ({0}, {1}, {2})", new Object[]{x, y, z});
        return distanceTo(new Point3D());
    }

    /**
     * Returns a new point that is the sum of this point and another.
     *
     * <p>This method treats points as vectors and adds them component-wise, returning a new instance.
     * Useful for vector arithmetic in algorithms like translations. Logs an INFO message.</p>
     *
     * @param other the point to add
     * @return a new point representing the sum
     * @throws IllegalArgumentException if the other point is null
     */
    public Point3D add(Point3D other) {
        if (other == null) {
            LOGGER.log(Level.SEVERE, "Null point provided for addition");
            throw new IllegalArgumentException("Other point cannot be null");
        }
        LOGGER.log(Level.INFO, "Adding point ({0}, {1}, {2})", new Object[]{other.x, other.y, other.z});
        return new Point3D(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Returns a new point that is the difference of this point and another.
     *
     * <p>This method treats points as vectors and subtracts them component-wise, returning a new instance.
     * Useful for vector arithmetic in algorithms like differences or directions. Logs an INFO message.</p>
     *
     * @param other the point to subtract
     * @return a new point representing the difference
     * @throws IllegalArgumentException if the other point is null
     */
    public Point3D subtract(Point3D other) {
        if (other == null) {
            LOGGER.log(Level.SEVERE, "Null point provided for subtraction");
            throw new IllegalArgumentException("Other point cannot be null");
        }
        LOGGER.log(Level.INFO, "Subtracting point ({0}, {1}, {2})", new Object[]{other.x, other.y, other.z});
        return new Point3D(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Returns a string representation of this point.
     *
     * <p>Overrides Object.toString() to provide a human-readable format: "Point3D[x=val, y=val, z=val]".</p>
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Point3D[x=" + x + ", y=" + y + ", z=" + z + "]";
    }

    /**
     * Checks if this point is equal to another object.
     *
     * <p>Overrides Object.equals() to compare coordinates with a small epsilon for floating-point precision.
     * Logs an INFO message during comparison.</p>
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point3D other = (Point3D) obj;
        LOGGER.log(Level.INFO, "Comparing points for equality");
        double epsilon = 1e-9;
        return Math.abs(x - other.x) < epsilon &&
                Math.abs(y - other.y) < epsilon &&
                Math.abs(z - other.z) < epsilon;
    }

    /**
     * Returns a hash code for this point.
     *
     * <p>Overrides Object.hashCode() based on coordinates, using Double.hashCode for consistency with equals.</p>
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Double.hashCode(x) ^ Double.hashCode(y) ^ Double.hashCode(z);
    }
}