package com.klinker.engine2d.math;



/**
 * A vector of 3 floats (globalX, globalY, globalZ). We use globalZ as the render order in case
 * anything should appear behind something else, even though we are working
 * with no depth.
 */
public class Vector3f {



    /**
     * The globalX coordinate.
     */
    private float x;

    /**
     * The globalY coordinate.
     */
    private float y;

    /**
     * The globalZ coordinate.
     */
    private float z;

    private Vector3f relative = null;



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
     * @param x The initial globalX coordinate.
     * @param y The initial globalY coordinate.
     * @param z The initial globalZ coordinate.
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public float globalX() {
        if (relative == null) return x;
        else return relative.globalX() + x;
    }

    public float globalY() {
        if (relative == null) return y;
        else return relative.globalY() + y;
    }

    public float globalZ() {
        if (relative == null) return z;
        else return relative.globalZ() + z;
    }


    public float localX() {
        return x;
    }

    public float localY() {
        return y;
    }

    public float localZ() {
        return z;
    }

    public Vector3f getRelative() {
        return relative;
    }

    public void setLocalX(float x) {
        this.x = x;
    }

    public void setLocalY(float y) {
        this.y = y;
    }

    public void setLocalZ(float z) {
        this.z = z;
    }

    public void setRelative(Vector3f relative) {
        this.relative = relative;
    }

    public void increment(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")" + (relative == null ? "" : " relative to " + relative.toString());
    }
}
