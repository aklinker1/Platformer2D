package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.gui.Scene;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.platformer2d.R;

public class TestMenu extends Scene {

    public TestMenu(Engine engine) {
        super(engine, R.layouts.MAIN_MENU);
    }

    @Override
    public String description() {
        return "Test Menu";
    }

    @Override
    public Shader[] loadAllShaders() {
        Shader[] array = new Shader[]{ SimpleSprite.SHADER, TextView.FONT_SHADER };
        return array;
    }

}
