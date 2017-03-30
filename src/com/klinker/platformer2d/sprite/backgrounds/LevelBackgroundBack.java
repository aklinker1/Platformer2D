package com.klinker.platformer2d.sprite.backgrounds;

import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

public class LevelBackgroundBack extends Sprite {

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);

    private int world;

    public LevelBackgroundBack(Vector2f position, int world) {
        super(
                new Vector3f(position.x, position.y, Depth.BACKGROUND_BACK),
                new Size<>(Platformer2D.tileCounts.x, 3f / 4f * Platformer2D.tileCounts.x),
                new Texture("res/textures/bg/" + String.format("%02X", world) + "-00.png"),
                SHADER
        );
        this.world = world;
    }

    @Override
    public void update() {

    }

}
