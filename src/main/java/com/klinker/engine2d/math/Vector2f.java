package com.klinker.engine2d.math;



/**
 * A vector of 2 floats (globalX, globalY).
 */
public class Vector2f {



    /**
     * The globalX coordinate.
     */
    public float x;

    /**
     * The globalY coordinate.
     */
    public float y;



    /**
     * Constructor initializing the vector to (0, 0).
     */
    public Vector2f() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Clone constructor.
     */
    public Vector2f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * @param x The initial globalX coordinate.
     * @param y The initial globalY coordinate.
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public Vector2f translate(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

    public float ratioXoY() {
        return x / y;
    }


    @Override
    public String toString() {
        return "Vector2F(" + x + ", " + y + ")";
    }
}
