package com.klinker.engine2d.inputs;

public class InputManager {

    Controller controller;
    Keyboard keyboard;

    public InputManager(Controller controller) {
        this.controller = controller;
        this.keyboard = new Keyboard();
    }

    public void updateInputs() {
        if (controller != null) controller.update();
        keyboard.update();
    }

    public Controller getController() {
        return controller;
    }

}
