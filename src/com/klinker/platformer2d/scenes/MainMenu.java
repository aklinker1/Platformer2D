package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.maths.Vector3f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprite.SimpleSprite;

import java.util.LinkedList;

public class MainMenu extends Menu {

    private SimpleSprite background;

    @Override
    public void init() {
        super.init();
        background = new SimpleSprite(
                new Vector2f(-50f, -50f),
                new Size<Float>(100f, 100f),
                R.textures.bg.B04_00
        );
    }

    @Override
    public void update() {
        background.update();
        super.update();
    }

    @Override
    public void render() {
        background.render();
        super.render();
    }

    @Override
    public Shader[] loadAllShaders() {
        return new Shader[]{ SimpleSprite.SHADER };
    }

    @Override
    protected void initializeNavigation(LinkedList<View> labels) {
        labels.add(new TextView(
                "H",
                new Vector3f(0f, 0f, Depth.TILE_FRONT),
                R.fonts.ROBOTO
        ));
    }

}
