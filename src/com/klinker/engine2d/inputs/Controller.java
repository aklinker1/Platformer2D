package com.klinker.engine2d.inputs;


import com.klinker.engine2d.utils.Log;
import net.java.games.input.Component;

public class Controller implements InputSource {

    private net.java.games.input.Controller controller;
    private Component[] components;
    private float[] defaults;
    private ButtonMapping buttonMapping;

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
        this.buttonMapping = new ButtonMapping(left, right, up, down, jump, run, components);
    }

    private class ButtonMapping {
        public float leftDeadzone;
        public Component left;
        public float rightDeadzone;
        public Component right;
        public float upDeadzone;
        public Component up;
        public float downDeadzone;
        public Component down;
        public float jumpDeadzone;
        public Component jump;
        public float runDeadzone;
        public Component run;
        public ButtonMapping(float[] left, float[] right, float[] up,
                             float[] down, float[] jump, float[] run,
                             Component[] components) {
            leftDeadzone = left[1];
            rightDeadzone = right[1];
            upDeadzone = up[1];
            downDeadzone = down[1];
            jumpDeadzone = jump[1];
            runDeadzone = run[1];
            this.left = components[(int) left[0]];
            this.right = components[(int) right[0]];
            this.up = components[(int) up[0]];
            this.down = components[(int) down[0]];
            this.jump = components[(int) jump[0]];
            this.run = components[(int) run[0]];
        }
    }

}
