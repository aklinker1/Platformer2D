package com.klinker.engine2d.inputs;


import net.java.games.input.Component;


import java.io.Serializable;


public class Button implements Serializable {
    public Component component;
    public float deadZone;
    public Button(Component component, float deadZone) {
        this.component = component;
        this.deadZone = deadZone;
    }
    public boolean isPressed() {
        if (component.getIdentifier().getClass() == Component.Identifier.Button.class) {
            return component.getPollData() == 1.0f;
        } else if (component.getIdentifier().equals(Component.Identifier.Axis.POV)) {
            return component.getPollData() == deadZone;
        } else {
            return component.getPollData() >= deadZone;
        }
    }
}