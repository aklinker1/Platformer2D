package com.klinker.engine2d.inputs;



import com.klinker.engine2d.Engine;
import com.klinker.engine2d.utils.Log;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;



/**
 * This class handles input from the keyboeard.
 */
public class Input extends GLFWKeyCallback {

    /**
     * An array of booleans that contains the key bindings that are pressed.
     */
    public static int[] bindings = new int[65536];
    private static boolean[] clicked = new boolean[65536];

    public static final int LEFT = GLFW.GLFW_KEY_LEFT;
    public static final int UP = GLFW.GLFW_KEY_UP;
    public static final int RIGHT = GLFW.GLFW_KEY_RIGHT;
    public static final int DOWN = GLFW.GLFW_KEY_DOWN;
    public static final int JUMP = GLFW.GLFW_KEY_SPACE;
    public static final int RUN = GLFW.GLFW_KEY_X;

    public static final int ACTION_RELEASE = GLFW.GLFW_RELEASE;
    public static final int ACTION_CLICK = GLFW.GLFW_PRESS;
    public static final int ACTION_HOLD = GLFW.GLFW_REPEAT;

    public static final int MAX_CLICK_LENGTH = 20; // value / 2 + 1 = frames max, so 20 = 11 frames, 6 = 4 frames



    /**
     * This is called from {@link Engine#update()}. Updates an array that contains how the inputs are being pressed.
     * @param window The OpenGl window id.
     * @param key The key that an action was made on.
     * @param scanCode Unknown.
     * @param action Unknown.
     * @param mods Unknown.
     */
    @Override
    public void invoke(long window, int key, int scanCode, int action, int mods) {
        Log.d("action: " + action);
        if (action == ACTION_RELEASE) {
            clicked[key] = bindings[key] <= MAX_CLICK_LENGTH;
            bindings[key] = ACTION_RELEASE; // clear the binding, as in it is not being pressed.
        } else {
            bindings[key] += action; // we either clicked it or are holding it, so increment the value.
        }
    }

    public static boolean isPressed(int keyCode) {
        return bindings[keyCode] != 0;
    }

    /**
     * A function that returns whether or not a
     * @param keyCode The key to check.
     * @return true when the array's value is less than a click value.
     */
    public static boolean isClicked(int keyCode) {
        boolean click = clicked[keyCode];
        clicked[keyCode] = false;
        return click;
    }
}
