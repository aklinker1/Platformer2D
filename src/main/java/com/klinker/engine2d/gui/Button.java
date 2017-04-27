package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.R;

public class Button extends TextView {

    public Button(String text, float height, Vector3f position, String fontDir) {
        this(text, new Size<Float>(null, height), position, fontDir);
    }

    public Button(String text, Size<Float> size, Vector3f position, String fontDir) {
        super(text, size, position, fontDir);
        setInnerHorAlign(Alignment.CENTER);
        setInnerVertAlign(Alignment.CENTER);
        setHorAlignment(Alignment.LEFT);
        setVertAlignment(Alignment.CENTER);

        loadCharacters();

        background = new StateObject<Sprite>(null);
        background.put(State.DEFAULT, new SimpleSprite(this.position, size, R.textures.tiles.blocks.X0F_05));
        background.put(State.SELECTED, new SimpleSprite(this.position, size, R.textures.tiles.blocks.X0F_06));
    }

}
