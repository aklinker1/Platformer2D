package com.klinker.engine2d.gui;

import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.sprite.SimpleSprite;

public class Button extends TextView {



    public Button(String text, float hieght, Vector2f position, String fontDir) {
        super(text, hieght, position, fontDir);
        StateObject<SimpleSprite> backgrounds = new StateObject<SimpleSprite>();
    }

}
