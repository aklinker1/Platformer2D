package com.klinker.engine2d.inputs;


import com.klinker.engine2d.utils.Log;
import net.java.games.input.Component;
import net.java.games.input.ControllerEnvironment;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller implements InputSource {

    private net.java.games.input.Controller controller;
    private Component[] components;

    private float[] defaults;
    private Button[] buttons;

    public Controller(net.java.games.input.Controller controller) {
        this.controller = controller;
        this.components = controller.getComponents();
        setupDefaults();
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
        try { Thread.sleep(250); } catch (Exception e) {}
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

    @Override
    public boolean isPressed(int button) {
        return buttons[button].isPressed();
    }

    public boolean outputToFile(String filePath) {
        try (FileWriter stream = new FileWriter(filePath);
             BufferedWriter out = new BufferedWriter(stream)) {
            String content = controller.getName() + '\n';
            ArrayList<Component> components = new ArrayList<>();
            for (Component c : this.components) components.add(c);
            for (Button button : buttons) {
                content += components.indexOf(button.component) + " " + button.deadZone + '\n';
            }
            out.write(content);
            out.close();
            stream.close();
            return true;
        } catch (Exception e) {
            Log.e("Error outputting bindings", e);
            return false;
        }
    }

    public static Controller readFromFile(String filePath) {
        try (FileReader stream = new FileReader(filePath);
             BufferedReader input = new BufferedReader(stream)) {
            String name = input.readLine();
            net.java.games.input.Controller ctrl = null;
            for (net.java.games.input.Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
                if (c.getName().equals(name)) ctrl = c;
            }

            Controller controller;
            if (ctrl != null) {
                controller = new Controller(ctrl);
            } else {
                Log.d("\nPrevious controller not plugged in, using defaults");
                Log.d("[ arrow-keys ] = Move");
                Log.d("[ space ] = Select/Jump");
                Log.d("[ X ] = Back/Run\n");
                return null;
            }
            Component[] components = ctrl.getComponents();
            Button[] buttons = new Button[InputManager.INPUT_COUNT];
            for (int i = 0; i < buttons.length; i++) {
                String line = input.readLine();
                int compIndex = Integer.parseInt(line.split(" ")[0]);
                float deadZone = Float.parseFloat(line.split(" ")[1]);
                buttons[i] = new Button(components[compIndex], deadZone);
            }
            controller.setButtons(buttons);
            controller.setComponents(components);
            return controller;
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                Log.d("No previous key bindings found.");
            } else {
                Log.e("Error reading bindings", e);
            }
            return null;
        }
    }

    private void setupDefaults() {
        this.defaults = new float[this.components.length];
        for (int i = 0; i < this.defaults.length; i++) {
            this.defaults[i] = components[i].getPollData();
        }
    }

    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    public void setComponents(Component[] components) {
        this.components = components;
    }

}
