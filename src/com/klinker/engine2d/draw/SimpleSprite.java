package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

public class SimpleSprite extends Sprite {

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC_ALPHA);

    /**
     * Constructor for a simple sprite that supports alpha.
     *
     * @param position The position of the sprite.
     * @param size The size of the sprite.
     * @param textRes The texture resource. A simple image only.
     */
    public SimpleSprite(Vector3f position, Size<Float> size, String textRes) {
        this(position, size, textRes, SHADER);
    }

    public SimpleSprite(Vector3f position, Size<Float> size, String textRes, Shader shader) {
        super(position, size, new Texture(textRes), shader);
    }

    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
    }


    @Override
    public void update() {

    }

}
