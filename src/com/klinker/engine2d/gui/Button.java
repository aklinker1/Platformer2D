package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.sprite.SimpleSprite;

public class Button extends TextView {

    public Button(String text, float height, Vector2f position, String fontDir) {
        this(text, new Size<Float>(null, height), position, fontDir);
    }

    public Button(String text, Size<Float> size, Vector2f position, String fontDir) {
        super(text, size, position, fontDir);
        setInnerHorAlign(Alignment.CENTER);
        setInnerVertAlign(Alignment.CENTER);
        setHorAlignment(Alignment.LEFT);
        setVertAlignment(Alignment.CENTER);

        loadCharacters();

        background = new StateObject<Sprite>();
        background.put(State.DEFAULT, new SimpleSprite(getAlignedPosition(), depth - 0.001f, size, R.textures.tiles.T01_01));
        background.put(State.SELECTED, new SimpleSprite(getAlignedPosition(), depth - 0.001f, size, R.textures.tiles.T01_02));
    }

    @Override
    public void render() {
        super.render();
    }
}
