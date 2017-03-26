package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.draw.WrapWidthSprite;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprite.SimpleSprite;

import java.util.LinkedList;

public class MainMenu extends Menu {

    private View background;

    @Override
    public void init() {
        super.init();
        background = new View(
                new Vector3f(-PROJ_SIZE.width / 2f, -PROJ_SIZE.height / 2f, Depth.BACKGROUND_BACK),
                new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height)
        );
        background.setBackgroundTexture(R.textures.bg.MAIN_MENU);
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
        return new Shader[]{ SimpleSprite.SHADER, WrapWidthSprite.SHADER, TextView.FONT_SHADER };
    }

    @Override
    protected void initializeViews(LinkedList<View> views) {
        views.add(new TextView(
                "Hello World!",
                10f,
                new Vector3f(0f, 0f, Depth.TILE_FRONT),
                R.fonts.ROBOTO
        ));
    }

}
