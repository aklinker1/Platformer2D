package com.klinker.engine2d.inputs;



import com.klinker.engine2d.Engine;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;



/**
 * This class handles input from the keyboeard.
 */
public class Input extends GLFWKeyCallback {

    /**
     * An array of booleans that contains the keys that are pressed.
     */
    public static boolean[] keys = new boolean[65536];

    public static final int LEFT = GLFW.GLFW_KEY_LEFT;
    public static final int UP = GLFW.GLFW_KEY_UP;
    public static final int RIGHT = GLFW.GLFW_KEY_RIGHT;
    public static final int DOWN = GLFW.GLFW_KEY_DOWN;
    public static final int JUMP = GLFW.GLFW_KEY_SPACE;
    public static final int RUN = GLFW.GLFW_KEY_X;



    /**
     * This is called from {@link Engine#update()} when it
     * @param window The OpenGl window id.
     * @param key The key that an action was made on.
     * @param scanCode Unknown.
     * @param action Unknown.
     * @param mods Unknown.
     */
    @Override
    public void invoke(long window, int key, int scanCode, int action, int mods) {
        keys[key] = action != GLFW.GLFW_RELEASE;    // when we release it, make the key true
    }

    public static boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

}
