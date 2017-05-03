package com.klinker.engine2d.inputs;

import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import net.java.games.input.ControllerEnvironment;

import java.util.ArrayList;
import java.util.Arrays;

public class InputManager {

    public static final int BUTTON_LEFT = 0;
    public static final int BUTTON_RIGHT = 1;
    public static final int BUTTON_DOWN = 2;
    public static final int BUTTON_UP = 3;
    public static final int BUTTON_JUMP = 4;
    public static final int BUTTON_SELECT = BUTTON_JUMP;
    public static final int BUTTON_RUN = 5;
    public static final int BUTTON_BACK = BUTTON_RUN;
    public static final int INPUT_COUNT = 6;


    private Controller controller;
    private static Keyboard keyboard = new Keyboard();


    public InputManager(Controller controller) {
        this.controller = controller;
    }


    public void updateInputs() {
        if (controller != null) controller.update();
        keyboard.update();
    }

    public Controller getController() {
        return controller;
    }

    public boolean isPressed(int button) {
        if (controller != null) return controller.isPressed(button);
        else return keyboard.isPressed(button);
    }

    public boolean isClicked(int button) {
        if (controller != null) return controller.isClicked(button);
        else return keyboard.isClicked(button);
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public static InputSource[] getControllers(boolean filter) {
        net.java.games.input.Controller[] fullList = ControllerEnvironment.getDefaultEnvironment().getControllers();
        ArrayList<net.java.games.input.Controller> list = new ArrayList<>();
        for (net.java.games.input.Controller controller : fullList) {
            if (!filter
                    || !controller.getType().equals(net.java.games.input.Controller.Type.KEYBOARD)
                    && !controller.getType().equals(net.java.games.input.Controller.Type.MOUSE)
                    && !controller.getType().equals(net.java.games.input.Controller.Type.UNKNOWN)) {
                list.add(controller);
            }
        }

        InputSource[] controllers = new InputSource[list.size() + 1];
        controllers[0] = Platformer2D.getInputManager().getKeyboard();
        for (int i = 0; i < list.size(); i++) {
            controllers[i + 1] = new Controller(list.get(i));
        }
        Log.d("\nInputs:\n" + Arrays.toString(controllers));
        return controllers;
    }

}
