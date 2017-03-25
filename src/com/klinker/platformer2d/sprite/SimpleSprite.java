package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

public class SimpleSprite extends Sprite {

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);
    private Size<Float> size;
    private Texture texture;

    /**
     * @see Sprite#Sprite(Vector2f, Size)
     * @param textRes The resource file for the image to be loaded.
     */
    public SimpleSprite(Vector2f position, Size<Float> size, String textRes) {
        super(position, size);
        texture = new Texture(textRes);
        initTextureAndShader();
    }

    @Override
    public float getDepth() {
        return Depth.BACKGROUND_BACK;
    }

    @Override
    public CollisionBox getCollision() {
        return null;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Shader getShader() {
        return null;
    }

    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
    }

    @Override
    public void update() {

    }
}
