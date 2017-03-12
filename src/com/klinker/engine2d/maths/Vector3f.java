package com.klinker.engine2d.maths;



/**
 * A vector of 3 floats (x, y, z). We use z as the render order in case
 * anything should appear behind something else, even though we are working
 * with no depth.
 */
public class Vector3f {



    /**
     * The x coordinate.
     */
    public float x;

    /**
     * The y coordinate.
     */
    public float y;

    /**
     * The z coordinate.
     */
    public float z;



    /**
     * Constructor initializing the vector to (0, 0, 0).
     */
    public Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * @param x The initial x coordinate.
     * @param y The initial y coordinate.
     * @param z The initial z coordinate.
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public Vector2f get2D() {
        return new Vector2f(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
