package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;

public class Button extends TextView {

    public Button(String text, float height, Vector3f position, String fontDir) {
        this(text, new Size<Float>(null, height), position, fontDir);
    }

    public Button(String text, Size<Float> size, Vector3f position, String fontDir) {
        super(text, size, position, fontDir);
        setInnerHorAlign(Alignment.CENTER);
        setInnerVertAlign(Alignment.CENTER);
        setHorAlignment(Alignment.LEFT);
        setVertAlignment(Alignment.BOTTOM);

        loadCharacters();

        background = new StateObject<Sprite>(null);
    }

}
