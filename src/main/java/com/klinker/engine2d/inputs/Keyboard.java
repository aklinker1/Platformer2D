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
    public static boolean[] bindings = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] prev = new boolean[bindings.length];

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
        prev[key] = bindings[key];
        bindings[key] = action != ACTION_RELEASE;
        Log.d("Updated: prev = " + prev[key] + ",\tcurrent = " + bindings[key]);
    }

    public boolean isPressed(int keyCode) {
        return bindings[buttons[keyCode]];
    }

    /**
     * A function that returns whether or not a
     * @param keyCode The key to check.
     * @return true when the array's value is less than a click value.
     */
    @Override
    public boolean isClicked(int keyCode) {
        boolean result = !prev[buttons[keyCode]] && bindings[buttons[keyCode]];
        prev[buttons[keyCode]] = true;
        return result;
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
                if (bindings[i]) key = i;
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
