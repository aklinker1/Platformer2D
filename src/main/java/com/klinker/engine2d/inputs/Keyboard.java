package com.klinker.engine2d.inputs;



import com.klinker.engine2d.Engine;
import com.klinker.engine2d.utils.Log;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;



/**
 * This class handles input from the keyboeard.
 */
public class Keyboard extends GLFWKeyCallback implements InputSource {

    /**
     * An array of booleans that contains the key bindings that are pressed.
     */
    public static int[] bindings = new int[65536];
    private static boolean[] clicked = new boolean[65536];

    public static int LEFT = GLFW.GLFW_KEY_LEFT;
    public static int UP = GLFW.GLFW_KEY_UP;
    public static int RIGHT = GLFW.GLFW_KEY_RIGHT;
    public static int DOWN = GLFW.GLFW_KEY_DOWN;
    public static int JUMP = GLFW.GLFW_KEY_SPACE;
    public static int RUN = GLFW.GLFW_KEY_X;
    public static int[] buttons = new int[] {
            LEFT,
            RIGHT,
            UP,
            DOWN,
            JUMP,
            RUN
    };

    public static final int ACTION_RELEASE = GLFW.GLFW_RELEASE;
    public static final int ACTION_CLICK = GLFW.GLFW_PRESS;
    public static final int ACTION_HOLD = GLFW.GLFW_REPEAT;

    public static final int MAX_CLICK_LENGTH = 20; // value / 2 + 1 = frames max, so 20 = 11 frames, 6 = 4 frames

    public Keyboard() {

    }



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
        if (action == ACTION_RELEASE) {
            clicked[key] = bindings[key] <= MAX_CLICK_LENGTH;
            bindings[key] = ACTION_RELEASE; // clear the binding, as in it is not being pressed.
        } else {
            bindings[key] += action; // we either clicked it or are holding it, so increment the value.
        }
    }

    public boolean isPressed(int keyCode) {
        return bindings[buttons[keyCode]] != ACTION_RELEASE;
    }

    /**
     * A function that returns whether or not a
     * @param keyCode The key to check.
     * @return true when the array's value is less than a click value.
     */
    @Override
    public boolean isClicked(int keyCode) {
        boolean click = clicked[buttons[keyCode]];
        if (click) clicked[buttons[keyCode]] = false;
        return click;
    }

    @Override
    public Object getSourceId() {
        return null;
    }

    @Override
    public void update() {
        // calls invoke (above)
        GLFW.glfwPollEvents();
    }

    public int waitForInput(String forWhat) {
        System.out.print(forWhat + "... ");
        int key = -1;
        while (key == -1) {
            update();
            for (int i = 0 ; i < bindings.length; i++) {
                if (bindings[i] != ACTION_RELEASE) key = i;
            }
        }
        Log.d("" + key);
        try { Thread.sleep(250); } catch (Exception e) {}
        return key;
    }

    public void setup() {
        buttons[InputManager.BUTTON_LEFT] = waitForInput("Left");
        buttons[InputManager.BUTTON_RIGHT] = waitForInput("Right");
        buttons[InputManager.BUTTON_UP] = waitForInput("Up");
        buttons[InputManager.BUTTON_DOWN] = waitForInput("Down");
        buttons[InputManager.BUTTON_JUMP] = waitForInput("Jump/Select");
        buttons[InputManager.BUTTON_RUN] = waitForInput("Run/Back");
    }


}
