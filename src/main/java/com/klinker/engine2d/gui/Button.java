package com.klinker.engine2d.gui;

import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;

public class Button extends TextView {

    public Button(int id, String text, float height, Vector3f position, String fontDir) {
        this(id, text, new Size<Float>(null, height), position, fontDir);
    }

    public Button(int id, String text, Size<Float> size, Vector3f position, String fontDir) {
        super(id, text, size, position, fontDir);
        setInnerHorAlign(Alignment.CENTER);
        setInnerVertAlign(Alignment.CENTER);
        setHorAlignment(Alignment.LEFT);
        setVertAlignment(Alignment.BOTTOM);

        loadCharacters();
    }

}
