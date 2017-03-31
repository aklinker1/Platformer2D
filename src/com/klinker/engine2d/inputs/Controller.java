package com.klinker.engine2d.inputs;


import com.klinker.engine2d.utils.Log;
import net.java.games.input.Component;

public class Controller implements InputSource {

    private net.java.games.input.Controller controller;
    private Component[] components;
    private float[] defaults;
    private Button[] buttons;

    public Controller(net.java.games.input.Controller controller) {
        this.controller = controller;
        this.components = controller.getComponents();
        this.defaults = new float[this.components.length];
        for (int i = 0; i < this.defaults.length; i++) {
            this.defaults[i] = components[i].getPollData();
        }
    }

    @Override
    public Object getSourceId() {
        return null;
    }

    @Override
    public void update() {
        controller.poll();
    }

    public float[] waitForInput(String forWhat) {
        System.out.print(forWhat + "... ");
        int i = -1;
        while (i == -1) {
            controller.poll();
            for (int j = 0; j < components.length; j++) {
                Component component = components[j];
                if (component.getName().toLowerCase().contains("axis")
                        || component.getName().toLowerCase().contains("rotation")) {
                    if (Math.abs(component.getPollData() - defaults[j]) > 0.5f) i = j;
                } else {
                    if (component.getPollData() != 0) i = j;
                }
            }
        }
        Log.d(components[i].getName() + " -> " + components[i].getPollData());
        return new float[]{ i, components[i].getPollData() };
    }

    public void setControls(float[] left, float[] right, float[] up, float[] down, float[] jump, float[] run) {
        buttons = new Button[InputManager.BUTTON_RUN + 1];
        buttons[InputManager.BUTTON_LEFT] = new Button(components[(int) left[0]], left[1]);
        buttons[InputManager.BUTTON_RIGHT] = new Button(components[(int) right[0]], right[1]);
        buttons[InputManager.BUTTON_UP] = new Button(components[(int) up[0]], up[1]);
        buttons[InputManager.BUTTON_DOWN] = new Button(components[(int) down[0]], down[1]);
        buttons[InputManager.BUTTON_JUMP] = new Button(components[(int) jump[0]], jump[1]);
        buttons[InputManager.BUTTON_RUN] = new Button(components[(int) run[0]], run[1]);
    }

    private class Button {
        public Component component;
        public float deadZone;
        public Button(Component component, float deadZone) {
            this.component = component;
            this.deadZone = deadZone;
        }
        public boolean isPressed() {
            return component.getPollData() >= deadZone;
        }
    }

    @Override
    public boolean isPressed(int button) {
        return buttons[button].isPressed();
    }

}
