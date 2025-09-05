package com.csc205.projects.project1;

package com.csc205.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a cube in 3D space defined by eight vertices.
 * This class provides methods for rotating the cube around its center, translating it,
 * calculating the total edge length (perimeter), volume, surface area, and other operations
 * useful for 3D graphics programs, such as retrieving vertices and edges.
 *
 * <p>Like in the Spring Framework getting started guides, this class demonstrates
 * basic object-oriented principles through encapsulation of the cube's vertices
 * and provision of well-defined methods for interaction. The class uses the Singleton
 * pattern for the logger instance to ensure a single logging point, promoting
 * resource efficiency and consistency in logging across instances.</p>
 *
 * <h2>Object-Oriented Design Patterns Used</h2>
 * <ul>
 *   <li><b>Encapsulation</b>: The vertices are stored in a private array, accessible only through
 *   getter methods or computed properties. This hides the internal representation and allows control over
 *   how the data is accessed or modified, demonstrating a foundational principle of data structures by
 *   protecting invariants (e.g., ensuring eight vertices) and ensuring data integrity.</li>
 *   <li><b>Singleton (for Logger)</b>: The logger is obtained via Logger.getLogger, which follows
 *   a singleton-like approach in java.util.logging, ensuring only one logger instance per class name.
 *   This pattern optimizes resource usage, a key consideration in algorithms dealing with large-scale
 *   data processing where logging overhead must be minimized.</li>
 *   <li><b>Composition</b>: The Cube3D class is composed of Point3D objects for vertices and can generate
 *   Line3D objects for edges, reusing the functionality of Point3D and Line3D. This promotes code reuse
 *   and modularity, essential in data structures like meshes or graphs where complex shapes are built from
 *   simpler primitives like points and lines.</li>
 *   <li><b>Immutable Operations (where applicable)</b>: Transformation methods like rotateX() and translate()
 *   return new Cube3D instances rather than mutating the existing one, promoting immutability. This aligns
 *   with functional programming principles integrated into OO design, reducing side effects and making the
 *   class thread-safe for concurrent algorithms in graphics rendering or simulations.</li>
 * </ul>
 *
 * <p>These patterns demonstrate foundational principles for data structures and algorithms by
 * emphasizing modularity (easy to extend or compose with other classes like spheres or scenes in 3D graphics),
 * efficiency (composition avoids redundant code in geometric algorithms), and reliability (logging helps
 * in debugging complex transformations like animations or collision detection in 3D space).</p>
 */
public class Cube3D {

    private static final Logger LOGGER = Logger.getLogger(Cube3D.class.getName());

    private static final int[][] EDGE_INDICES = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0}, // bottom face
            {4, 5}, {5, 6}, {6, 7}, {7, 4}, // top face
            {0, 4}, {1, 5}, {2, 6}, {3, 7}  // vertical edges
    };

    private Point3D[] vertices;

    /**
     * Constructs a new Cube3D centered at the specified point with the given side length.
     *
     * <p>This constructor initializes the cube's eight vertices based on the center and side length,
     * assuming an axis-aligned orientation initially. It ensures the side length is positive.
     * Logs an INFO message upon creation to track object instantiation, useful for debugging in
     * 3D graphics applications involving multiple shapes.</p>
     *
     * @param center the center point of the cube
     * @param sideLength the length of each side of the cube
     * @throws IllegalArgumentException if center is null or sideLength is non-positive
     */
    public Cube3D(Point3D center, double sideLength) {
        if (center == null) {
            LOGGER.log(Level.SEVERE, "Null center provided for Cube3D construction");
            throw new IllegalArgumentException("Center cannot be null");
        }
        if (sideLength <= 0) {
            LOGGER.log(Level.SEVERE, "Non-positive side length provided: {0}", sideLength);
            throw new IllegalArgumentException("Side length must be positive");
        }
        double h = sideLength / 2.0;
        vertices = new Point3D[8];
        vertices[0] = new Point3D(center.getX() - h, center.getY() - h, center.getZ() - h);
        vertices[1] = new Point3D(center.getX() + h, center.getY() - h, center.getZ() - h);
        vertices[2] = new Point3D(center.getX() + h, center.getY() + h, center.getZ() - h);
        vertices[3] = new Point3D(center.getX() - h, center.getY() + h, center.getZ() - h);
        vertices[4] = new Point3D(center.getX() - h, center.getY() - h, center.getZ() + h);
        vertices[5] = new Point3D(center.getX() + h, center.getY() - h, center.getZ() + h);
        vertices[6] = new Point3D(center.getX() + h, center.getY() + h, center.getZ() + h);
        vertices[7] = new Point3D(center.getX() - h, center.getY() + h, center.getZ() + h);
        LOGGER.log(Level.INFO, "Created Cube3D centered at {0} with side length {1}", new Object[]{center, sideLength});
    }

    /**
     * Constructs a new Cube3D from an array of eight vertices.
     *
     * <p>This constructor allows creating a cube from pre-defined vertices, useful for transformed cubes.
     * It checks for exactly eight vertices but does not validate if they form a perfect cube.
     * Logs an INFO message upon creation.</p>
     *
     * @param vertices an array of eight Point3D vertices
     * @throws IllegalArgumentException if vertices is null or not exactly eight points
     */
    public Cube3D(Point3D[] vertices) {
        if (vertices == null || vertices.length != 8) {
            LOGGER.log(Level.SEVERE, "Invalid vertices array provided for Cube3D construction");
            throw new IllegalArgumentException("Exactly eight vertices must be provided");
        }
        for (Point3D v : vertices) {
            if (v == null) {
                LOGGER.log(Level.SEVERE, "Null vertex in array for Cube3D construction");
                throw new IllegalArgumentException("Vertices cannot be null");
            }
        }
        this.vertices = new Point3D[8];
        for (int i = 0; i < 8; i++) {
            this.vertices[i] = new Point3D(vertices[i].getX(), vertices[i].getY(), vertices[i].getZ());
        }
        LOGGER.log(Level.INFO, "Created Cube3D from provided vertices");
    }

    /**
     * Returns a copy of the vertices of this cube.
     *
     * <p>This getter method provides read access to the vertices, returning a deep copy to prevent external mutation.</p>
     *
     * @return an array of the cube's vertices
     */
    public Point3D[] getVertices() {
        Point3D[] copy = new Point3D[8];
        for (int i = 0; i < 8; i++) {
            copy[i] = new Point3D(vertices[i].getX(), vertices[i].getY(), vertices[i].getZ());
        }
        return copy;
    }

    /**
     * Calculates the center of the cube.
     *
     * <p>This method computes the average of all vertices to find the center point.
     * Logs an INFO message before calculation.</p>
     *
     * @return the center point
     */
    public Point3D getCenter() {
        LOGGER.log(Level.INFO, "Calculating center of cube");
        double sumX = 0, sumY = 0, sumZ = 0;
        for (Point3D v : vertices) {
            sumX += v.getX();
            sumY += v.getY();
            sumZ += v.getZ();
        }
        return new Point3D(sumX / 8, sumY / 8, sumZ / 8);
    }

    /**
     * Calculates the side length of the cube.
     *
     * <p>This method computes the distance between two adjacent vertices (e.g., vertices 0 and 1).
     * Assumes all edges are equal; in practice, for a true cube, they should be.
     * Logs an INFO message before calculation. If NaN detected, logs WARNING.</p>
     *
     * @return the side length
     */
    public double getSideLength() {
        LOGGER.log(Level.INFO, "Calculating side length of cube");
        double length = vertices[0].distanceTo(vertices[1]);
        if (Double.isNaN(length)) {
            LOGGER.log(Level.WARNING, "NaN side length calculated");
        }
        return length;
    }

    /**
     * Calculates the total edge length (perimeter) of the cube.
     *
     * <p>This method returns 12 times the side length, as a cube has 12 edges.
     * Useful for wireframe rendering in 3D graphics. Logs an INFO message.</p>
     *
     * @return the total edge length
     */
    public double getTotalEdgeLength() {
        LOGGER.log(Level.INFO, "Calculating total edge length of cube");
        return 12 * getSideLength();
    }

    /**
     * Calculates the volume of the cube.
     *
     * <p>This method computes the side length cubed. For verification, it could use the scalar triple product
     * of three edge vectors from a vertex, but uses the simpler method assuming cube properties.
     * Logs an INFO message before calculation.</p>
     *
     * @return the volume
     */
    public double getVolume() {
        LOGGER.log(Level.INFO, "Calculating volume of cube");
        double side = getSideLength();
        return side * side * side;
    }

    /**
     * Calculates the surface area of the cube.
     *
     * <p>This method returns 6 times the side length squared, as a cube has 6 faces.
     * Useful for texturing or lighting calculations in 3D graphics. Logs an INFO message.</p>
     *
     * @return the surface area
     */
    public double getSurfaceArea() {
        LOGGER.log(Level.INFO, "Calculating surface area of cube");
        double side = getSideLength();
        return 6 * side * side;
    }

    /**
     * Returns the edges of the cube as Line3D objects.
     *
     * <p>This method generates a list of 12 Line3D instances representing the cube's edges.
     * Useful for rendering or intersection tests in 3D graphics. Logs an INFO message.</p>
     *
     * @return a list of edges
     */
    public List<Line3D> getEdges() {
        LOGGER.log(Level.INFO, "Generating edges for cube");
        List<Line3D> edges = new ArrayList<>();
        for (int[] idx : EDGE_INDICES) {
            edges.add(new Line3D(vertices[idx[0]], vertices[idx[1]]));
        }
        return edges;
    }

    /**
     * Returns a new cube rotated around the x-axis passing through its center by the given angle.
     *
     * <p>This method rotates each vertex around the center using translate-rotate-translate back.
     * It returns a new Cube3D to maintain immutability. Logs an INFO message with the angle.
     * If the angle is NaN, logs a WARNING.</p>
     *
     * @param angle the rotation angle in radians
     * @return a new rotated cube
     */
    public Cube3D rotateX(double angle) {
        if (Double.isNaN(angle)) {
            LOGGER.log(Level.WARNING, "NaN angle provided for x-rotation");
        }
        LOGGER.log(Level.INFO, "Rotating cube around x-axis by {0} radians", angle);
        Point3D center = getCenter();
        Point3D[] newVertices = new Point3D[8];
        for (int i = 0; i < 8; i++) {
            Point3D temp = vertices[i].subtract(center);
            Point3D rotated = temp.rotateX(angle);
            newVertices[i] = center.add(rotated);
        }
        return new Cube3D(newVertices);
    }

    /**
     * Returns a new cube rotated around the y-axis passing through its center by the given angle.
     *
     * <p>This method rotates each vertex around the center using translate-rotate-translate back.
     * It returns a new Cube3D to maintain immutability. Logs an INFO message with the angle.
     * If the angle is NaN, logs a WARNING.</p>
     *
     * @param angle the rotation angle in radians
     * @return a new rotated cube
     */
    public Cube3D rotateY(double angle) {
        if (Double.isNaN(angle)) {
            LOGGER.log(Level.WARNING, "NaN angle provided for y-rotation");
        }
        LOGGER.log(Level.INFO, "Rotating cube around y-axis by {0} radians", angle);
        Point3D center = getCenter();
        Point3D[] newVertices = new Point3D[8];
        for (int i = 0; i < 8; i++) {
            Point3D temp = vertices[i].subtract(center);
            Point3D rotated = temp.rotateY(angle);
            newVertices[i] = center.add(rotated);
        }
        return new Cube3D(newVertices);
    }

    /**
     * Returns a new cube rotated around the z-axis passing through its center by the given angle.
     *
     * <p>This method rotates each vertex around the center using translate-rotate-translate back.
     * It returns a new Cube3D to maintain immutability. Logs an INFO message with the angle.
     * If the angle is NaN, logs a WARNING.</p>
     *
     * @param angle the rotation angle in radians
     * @return a new rotated cube
     */
    public Cube3D rotateZ(double angle) {
        if (Double.isNaN(angle)) {
            LOGGER.log(Level.WARNING, "NaN angle provided for z-rotation");
        }
        LOGGER.log(Level.INFO, "Rotating cube around z-axis by {0} radians", angle);
        Point3D center = getCenter();
        Point3D[] newVertices = new Point3D[8];
        for (int i = 0; i < 8; i++) {
            Point3D temp = vertices[i].subtract(center);
            Point3D rotated = temp.rotateZ(angle);
            newVertices[i] = center.add(rotated);
        }
        return new Cube3D(newVertices);
    }

    /**
     * Returns a new cube translated by the given vector.
     *
     * <p>This method adds the translation vector to each vertex.
     * It returns a new Cube3D to maintain immutability. Logs an INFO message with the vector.
     * If the vector is null, logs SEVERE and throws exception.</p>
     *
     * @param vector the translation vector
     * @return a new translated cube
     * @throws IllegalArgumentException if vector is null
     */
    public Cube3D translate(Point3D vector) {
        if (vector == null) {
            LOGGER.log(Level.SEVERE, "Null vector provided for translation");
            throw new IllegalArgumentException("Translation vector cannot be null");
        }
        LOGGER.log(Level.INFO, "Translating cube by vector {0}", vector);
        Point3D[] newVertices = new Point3D[8];
        for (int i = 0; i < 8; i++) {
            newVertices[i] = vertices[i].add(vector);
        }
        return new Cube3D(newVertices);
    }

    /**
     * Returns a string representation of this cube.
     *
     * <p>Overrides Object.toString() to provide a human-readable format including center and side length.</p>
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Cube3D[center=" + getCenter() + ", sideLength=" + getSideLength() + "]";
    }

    /**
     * Checks if this cube is equal to another object.
     *
     * <p>Overrides Object.equals() to compare vertices (order-insensitive, but assumes same ordering).
     * Logs an INFO message during comparison.</p>
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cube3D other = (Cube3D) obj;
        LOGGER.log(Level.INFO, "Comparing cubes for equality");
        Point3D[] otherVertices = other.getVertices();
        for (int i = 0; i < 8; i++) {
            if (!vertices[i].equals(otherVertices[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a hash code for this cube.
     *
     * <p>Overrides Object.hashCode() based on vertices' hash codes.</p>
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (Point3D v : vertices) {
            hash ^= v.hashCode();
        }
        return hash;
    }
}
