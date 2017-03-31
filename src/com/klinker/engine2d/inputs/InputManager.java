package com.klinker.engine2d.inputs;

public class InputManager {

    public static final int BUTTON_LEFT = 0;
    public static final int BUTTON_RIGHT = 1;
    public static final int BUTTON_DOWN = 2;
    public static final int BUTTON_UP = 3;
    public static final int BUTTON_JUMP = 4;
    public static final int BUTTON_SELECT = BUTTON_JUMP;
    public static final int BUTTON_RUN = 5;
    public static final int BUTTON_BACK = BUTTON_RUN;


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
        else return false;
    }

    public boolean isClicked(int button) {
        if (controller != null) return controller.isPressed(button);
        else return false;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

}
