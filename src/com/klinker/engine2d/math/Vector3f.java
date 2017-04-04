package com.klinker.engine2d.math;



/**
 * A vector of 3 floats (x, y, z). We use z as the render order in case
 * anything should appear behind something else, even though we are working
 * with no depth.
 */
public class Vector3f {



    /**
     * The x coordinate.
     */
    private float x;

    /**
     * The y coordinate.
     */
    private float y;

    /**
     * The z coordinate.
     */
    private float z;

    private Vector3f translate = null;



    /**
     * Constructor initializing the vector to (0, 0, 0).
     */
    public Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Deep copy constructor.
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
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


    public float x() {
        if (translate == null) return x;
        else return translate.x() + x;
    }

    public float y() {
        if (translate == null) return y;
        else return translate.y() + y;
    }

    public float z() {
        if (translate == null) return z;
        else return translate.z() + z;
    }

    public Vector3f getTranslate() {
        return translate;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setTranslate(Vector3f translate) {
        this.translate = translate;
    }

    public void increment(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
