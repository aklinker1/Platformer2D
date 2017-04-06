package com.klinker.engine2d.math;



import com.klinker.engine2d.utils.BufferUtils;

import java.nio.FloatBuffer;



/**
 * A 4x4 Matrix of floats
 */
public class Matrix4f {

    /**
     * The size of the matrix, 4 by 4.
     */
    private static final int SIZE = 4;



    /**
     * The elements, arranged in a 16 float long array.
     */
    public float[] elements = new float[SIZE * SIZE];



    /**
     * @return The Identity matrix
     *
     *          [1 0 0 0]
     *          [0 1 0 0]
     *          [0 0 1 0]
     *          [0 0 0 1]
     */
    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0.0f;
        }
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;
        return result;
    }

    /**
     * @param left The left (globalX) of the projection.
     * @param right The right (globalX) of the projection.
     * @param top The top (globalY) of the projection.
     * @param bottom The bottom (globalY) of the projection.
     * @param near The near (globalZ) of the projection.
     * @param far The far (globalZ) of the projection.
     * @return An orthogonal matrix.
     */
    public static Matrix4f orthographic(float left, float right, float top, float bottom, float near, float far) {
        Matrix4f result = identity();
        // for some reason the range that OpenGL could draw was (near/2, far/2], so double both to get what I want!
        near *= 2;
        far *= 2;

        result.elements[0] = 2.0f / (right - left);
        result.elements[5] = 2.0f / (top - bottom);
        result.elements[10] = -2.0f / (far - near);

        result.elements[12] = -((right + left) / (right - left));
        result.elements[13] = -((top + bottom) / (top - bottom));
        result.elements[14] = -((far + near) / (far - near));

        return result;
    }

    /**
     * @param vectors The vectors to create a translation to multiply by.
     * @return A matrix that, when multiplied with another matrix, will translate the
     * other matrix by {@param vector}
     */
    public static Matrix4f translate(Vector3f ... vectors) {
        Matrix4f result = identity();
        for (Vector3f v : vectors) {
            result.elements[12] += v.globalX();
            result.elements[13] += v.globalY();
            result.elements[14] += v.globalZ();
        }
        return result;
    }

    /**
     * @param angle The angle (in degrees) to create a rotation to multiply by.
     * @return A matrix that, when multiplied with another matrix, will rotate the
     * other matrix by {@param angle}
     */
    public static Matrix4f rotate(float angle) {
        Matrix4f result = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        result.elements[0] = cos;
        result.elements[1] = sin;

        result.elements[4] = -sin;
        result.elements[5] = cos;

        return result;
    }

    /**
     * Perform matrix multiplication.
     * @param matrix A matrix to multiply this matrix by.
     * @return The resule of this times {@param matrix}
     */
    public Matrix4f multiply(Matrix4f matrix) {
        Matrix4f result = new Matrix4f();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
                }
                result.elements[x + y * 4] = sum;
            }
        }
        return result;
    }



    /**
     * Creates an empty (all 0) 4x4 matrix.
     */
    public Matrix4f() { }

    /**
     * Deep copy constructor.
     */
    public Matrix4f(Matrix4f m) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = m.elements[i];
        }
    }



    /**
     * @return A {@link FloatBuffer} from this matrix's elements that is used in OpenGL.
     */
    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }



}
