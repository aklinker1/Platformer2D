package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.draw.WrapWidthSprite;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.sprite.SimpleSprite;

import java.util.LinkedList;

public class MainMenu extends Menu {

    private View background;

    @Override
    public void init() {
        super.init();
        background = new View(
                new Vector2f(-PROJ_SIZE.width / 2f, -PROJ_SIZE.height / 2f),
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
        TextView version = new TextView(
                "Version 0.0.1 Alpha",
                3f,
                new Vector2f(PROJ_SIZE.width / 2f - 2f,-PROJ_SIZE.height / 2f + 2f),
                R.fonts.ROBOTO
        );
        Log.d("position = " + version.getPosition());
        version.setTextColor(0x6b000000);
        version.setAlignment(TextView.Alignment.RIGHT);
        views.add(version);

        TextView test = new TextView(
                "Test of innerAlign",
                new Size<Float>(5f, 30f),
                new Vector2f(0, 0),
                R.fonts.ROBOTO
        );

    }

    @Override
    public void onLeftPress() {
        Log.d("Left");
    }

    @Override
    public void onUpPress() {
        Log.d("Up");
    }

    @Override
    public void onRightPress() {
        Log.d("Right");
    }

    @Override
    public void onDownPress() {
        Log.d("Down");
    }

}
