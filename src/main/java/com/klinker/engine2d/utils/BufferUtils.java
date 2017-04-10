package com.klinker.engine2d.utils;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;



/**
 * Helper functions for converting arrays to buffers.
 */
public class BufferUtils {



    /**
     * Private constructor to prevent external instantiation.
     */
    private BufferUtils() { }



    /**
     * @param array The array to be converted.
     * @return A byte buffer from {@param array}
     */
    public static ByteBuffer createByteBuffer(byte[] array) {
        ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
        result.put(array).flip();
        return result;
    }

    /**
     * @param array The array to be converted.
     * @return A byte buffer from {@param array}
     */
    public static FloatBuffer createFloatBuffer(float[] array) {
        // floats are 4 bytes
        FloatBuffer result = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).flip();
        return result;
    }

    /**
     * @param array The array to be converted.
     * @return A byte buffer from {@param array}
     */
    public static IntBuffer createIntBuffer(int[] array) {
        // floats are 4 bytes
        IntBuffer result = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).flip();
        return result;
    }



}
