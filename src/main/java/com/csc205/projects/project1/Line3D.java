package com.csc205.projects.project1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a line in 3D space defined by two distinct points.
 * This class provides methods for calculating the length between the defining points,
 * the shortest distance to another line, and other operations useful for manipulating 3D lines.
 * The line is considered infinite, extending in both directions beyond the defining points.
 *
 * <p>Like in the Spring Framework getting started guides, this class demonstrates
 * basic object-oriented principles through encapsulation of the line's defining points
 * and provision of well-defined methods for interaction. The class uses the Singleton
 * pattern for the logger instance to ensure a single logging point, promoting
 * resource efficiency and consistency in logging across instances.</p>
 *
 * <h2>Object-Oriented Design Patterns Used</h2>
 * <ul>
 *   <li><b>Encapsulation</b>: The defining points (start, end) are private fields, accessible only through
 *   getter and setter methods. This hides the internal representation and allows control over
 *   how the data is modified, demonstrating a foundational principle of data structures by
 *   protecting invariants (e.g., ensuring start and end are distinct) and ensuring data integrity.</li>
 *   <li><b>Singleton (for Logger)</b>: The logger is obtained via Logger.getLogger, which follows
 *   a singleton-like approach in java.util.logging, ensuring only one logger instance per class name.
 *   This pattern optimizes resource usage, a key consideration in algorithms dealing with large-scale
 *   data processing where logging overhead must be minimized.</li>
 *   <li><b>Composition</b>: The Line3D class is composed of Point3D objects, reusing the functionality
 *   of Point3D for vector operations. This promotes code reuse and modularity, essential in data
 *   structures like graphs or trees where complex objects are built from simpler ones.</li>
 *   <li><b>Immutable Operations (where applicable)</b>: Methods like direction() and shortestDistanceTo()
 *   return new values or computations without mutating the existing line, promoting immutability.
 *   This aligns with functional programming principles integrated into OO design, reducing side effects
 *   and making the class thread-safe for concurrent algorithms.</li>
 * </ul>
 *
 * <p>These patterns demonstrate foundational principles for data structures and algorithms by
 * emphasizing modularity (easy to extend or compose with other classes like planes or volumes),
 * efficiency (composition avoids redundant code in spatial algorithms), and reliability (logging helps
 * in debugging complex geometric algorithms like ray tracing or collision detection in 3D space).</p>
 */
public class Line3D {

    private static final Logger LOGGER = Logger.getLogger(Line3D.class.getName());

    private Point3D start;
    private Point3D end;

    /**
     * Constructs a new Line3D defined by the specified start and end points.
     *
     * <p>This constructor initializes the line's defining points. It ensures the points are not null
     * and are distinct to properly define a line. It logs an INFO message upon creation to track
     * object instantiation, which can be useful for debugging in applications involving many lines,
     * such as 3D modeling or pathfinding algorithms.</p>
     *
     * @param start the starting point defining the line
     * @param end the ending point defining the line
     * @throws IllegalArgumentException if start or end is null, or if start equals end
     */
    public Line3D(Point3D start, Point3D end) {
        if (start == null || end == null) {
            LOGGER.log(Level.SEVERE, "Null point provided for Line3D construction");
            throw new IllegalArgumentException("Start and end points cannot be null");
        }
        if (start.equals(end)) {
            LOGGER.log(Level.SEVERE, "Start and end points are identical, cannot define a line");
            throw new IllegalArgumentException("Start and end points must be distinct");
        }
        this.start = start;
        this.end = end;
        LOGGER.log(Level.INFO, "Created Line3D from {0} to {1}", new Object[]{start, end});
    }

    /**
     * Returns the starting point of this line.
     *
     * <p>This getter method provides read access to the private start field, enforcing encapsulation.</p>
     *
     * @return the starting point
     */
    public Point3D getStart() {
        return start;
    }

    /**
     * Sets the starting point of this line.
     *
     * <p>This setter method updates the start field, ensuring it is not null and distinct from end.
     * It logs an INFO message for the change. If the new value is null or equals end, it logs a SEVERE
     * error and throws an exception.</p>
     *
     * @param start the new starting point
     * @throws IllegalArgumentException if start is null or equals end
     */
    public void setStart(Point3D start) {
        if (start == null) {
            LOGGER.log(Level.SEVERE, "Attempted to set start to null");
            throw new IllegalArgumentException("Start point cannot be null");
        }
        if (start.equals(this.end)) {
            LOGGER.log(Level.SEVERE, "New start point equals end, cannot define a line");
            throw new IllegalArgumentException("Start and end points must be distinct");
        }
        this.start = start;
        LOGGER.log(Level.INFO, "Updated start to {0}", start);
    }

    /**
     * Returns the ending point of this line.
     *
     * <p>This getter method provides read access to the private end field, enforcing encapsulation.</p>
     *
     * @return the ending point
     */
    public Point3D getEnd() {
        return end;
    }

    /**
     * Sets the ending point of this line.
     *
     * <p>This setter method updates the end field, ensuring it is not null and distinct from start.
     * It logs an INFO message for the change. If the new value is null or equals start, it logs a SEVERE
     * error and throws an exception.</p>
     *
     * @param end the new ending point
     * @throws IllegalArgumentException if end is null or equals start
     */
    public void setEnd(Point3D end) {
        if (end == null) {
            LOGGER.log(Level.SEVERE, "Attempted to set end to null");
            throw new IllegalArgumentException("End point cannot be null");
        }
        if (end.equals(this.start)) {
            LOGGER.log(Level.SEVERE, "New end point equals start, cannot define a line");
            throw new IllegalArgumentException("Start and end points must be distinct");
        }
        this.end = end;
        LOGGER.log(Level.INFO, "Updated end to {0}", end);
    }

    /**
     * Calculates the length between the defining start and end points of this line.
     *
     * <p>This method computes the Euclidean distance between the two points defining the line.
     * Note that since the line is infinite, this is not the length of the line itself but the
     * segment between the defining points. It logs an INFO message before calculation.</p>
     *
     * @return the length between start and end
     */
    public double length() {
        LOGGER.log(Level.INFO, "Calculating length between defining points {0} and {1}", new Object[]{start, end});
        return start.distanceTo(end);
    }

    /**
     * Returns the direction vector of this line.
     *
     * <p>This method computes the vector from start to end, representing the direction.
     * It returns a new Point3D to avoid mutation. Logs an INFO message.</p>
     *
     * @return the direction vector
     */
    public Point3D direction() {
        LOGGER.log(Level.INFO, "Computing direction vector for line");
        return end.subtract(start);
    }

    /**
     * Calculates the shortest distance between this line and another line.
     *
     * <p>This method computes the shortest distance between two infinite lines in 3D space.
     * It uses the formula for skew lines: | (P - Q) • (u × v) | / ||u × v||, where u and v are
     * direction vectors, and P, Q are points on each line. For parallel lines, it uses
     * | (P - Q) × u | / ||u||. If lines are identical (collinear and overlapping), distance is 0.
     * Logs an INFO message before calculation. If the other line is null, logs SEVERE and throws exception.
     * If directions are zero (impossible due to constructor), logs WARNING.</p>
     *
     * @param other the other line
     * @return the shortest distance
     * @throws IllegalArgumentException if the other line is null
     */
    public double shortestDistanceTo(Line3D other) {
        if (other == null) {
            LOGGER.log(Level.SEVERE, "Null line provided for shortest distance calculation");
            throw new IllegalArgumentException("Other line cannot be null");
        }
        LOGGER.log(Level.INFO, "Calculating shortest distance to another line");
        Point3D u = this.direction();
        Point3D v = other.direction();
        Point3D w = this.start.subtract(other.start);

        Point3D crossUV = cross(u, v);
        double crossUVMag = crossUV.magnitude();

        if (crossUVMag == 0) {
            // Parallel or collinear
            Point3D crossWU = cross(w, u);
            double crossWUMag = crossWU.magnitude();
            double uMag = u.magnitude();
            if (uMag == 0) {
                LOGGER.log(Level.WARNING, "Zero magnitude direction vector encountered");
                return 0; // Degenerate case
            }
            return crossWUMag / uMag;
        } else {
            // Skew or intersecting
            double dotWCrossUV = dot(w, crossUV);
            return Math.abs(dotWCrossUV) / crossUVMag;
        }
    }

    /**
     * Computes the dot product of two vectors represented as Point3D.
     *
     * <p>This static utility method calculates the dot product: x1*x2 + y1*y2 + z1*z2.
     * Useful for vector operations in 3D algorithms.</p>
     *
     * @param a the first vector
     * @param b the second vector
     * @return the dot product
     */
    private static double dot(Point3D a, Point3D b) {
        return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
    }

    /**
     * Computes the cross product of two vectors represented as Point3D.
     *
     * <p>This static utility method calculates the cross product, returning a new Point3D.
     * The components are computed as: (ay*bz - az*by, az*bx - ax*bz, ax*by - ay*bx).
     * Useful for vector operations in 3D algorithms like finding normals or distances.</p>
     *
     * @param a the first vector
     * @param b the second vector
     * @return the cross product vector
     */
    private static Point3D cross(Point3D a, Point3D b) {
        double newX = a.getY() * b.getZ() - a.getZ() * b.getY();
        double newY = a.getZ() * b.getX() - a.getX() * b.getZ();
        double newZ = a.getX() * b.getY() - a.getY() * b.getX();
        return new Point3D(newX, newY, newZ);
    }

    /**
     * Checks if this line is parallel to another line.
     *
     * <p>This method determines if the direction vectors are parallel by checking if their
     * cross product magnitude is zero (within floating-point epsilon). Logs an INFO message.</p>
     *
     * @param other the other line
     * @return true if parallel, false otherwise
     * @throws IllegalArgumentException if the other line is null
     */
    public boolean isParallelTo(Line3D other) {
        if (other == null) {
            LOGGER.log(Level.SEVERE, "Null line provided for parallel check");
            throw new IllegalArgumentException("Other line cannot be null");
        }
        LOGGER.log(Level.INFO, "Checking if lines are parallel");
        Point3D u = this.direction();
        Point3D v = other.direction();
        Point3D crossUV = cross(u, v);
        double epsilon = 1e-9;
        return crossUV.magnitude() < epsilon;
    }

    /**
     * Returns a string representation of this line.
     *
     * <p>Overrides Object.toString() to provide a human-readable format: "Line3D[start=..., end=...]".</p>
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Line3D[start=" + start + ", end=" + end + "]";
    }

    /**
     * Checks if this line is equal to another object.
     *
     * <p>Overrides Object.equals() to compare defining points (order-insensitive, since lines are undirected).
     * It checks if start/end match other's start/end or end/start, using Point3D's equals.</p>
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Line3D other = (Line3D) obj;
        LOGGER.log(Level.INFO, "Comparing lines for equality");
        return (start.equals(other.start) && end.equals(other.end)) ||
                (start.equals(other.end) && end.equals(other.start));
    }

    /**
     * Returns a hash code for this line.
     *
     * <p>Overrides Object.hashCode() based on start and end hash codes, XORed for order-insensitivity.</p>
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return start.hashCode() ^ end.hashCode();
    }
}