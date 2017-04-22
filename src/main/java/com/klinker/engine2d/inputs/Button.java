package com.klinker.engine2d.inputs;


import net.java.games.input.Component;

import java.io.Serializable;


public class Button implements Serializable {

    public Component component;
    public float deadZone;
    float value = 0f;
    float prevValue = 0f;

    public Button(Component component, float deadZone) {
        this.component = component;
        this.deadZone = deadZone;
    }

    public void poll() {
        prevValue = value;
        value = component.getPollData();
    }

    public boolean isPressed() {
        // if it's a button, either 1 or 0
        if (component.getIdentifier().getClass() == Component.Identifier.Button.class) {
            return value == 1.0f;

        // if it's a POV (D-pad), then the deadzone represents the corrent value.
        } else if (component.getIdentifier().equals(Component.Identifier.Axis.POV)) {
            return value == deadZone;

        // If it's an axis, then the value has to be higher than the deadzone.
        } else {
            return value >= deadZone;
        }
    }

    public boolean isClicked() {
        boolean different = value != prevValue;

        // if they aren't different, it was not a click
        if (!different) return false;

        // Otherwise it is a click if it is pressed.
        else return isPressed();
    }

}