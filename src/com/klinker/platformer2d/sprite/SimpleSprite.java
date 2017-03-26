package com.klinker.platformer2d.sprite;

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
    private Texture _texture;
    private float _depth;

    /**
     * @see Sprite#Sprite(Vector2f, Size)
     * @param textRes The resource file for the image to be loaded.
     */
    public SimpleSprite(Vector2f position, float depth, Size<Float> size, String textRes) {
        super(position, size);
        _texture = new Texture(textRes);
        this.depth = depth;
        _depth = depth;
        initTextureAndShader();
        reinitializeMesh();
    }

    @Override
    public float getDepth() {
        return _depth;
    }

    @Override
    public CollisionBox getCollision() {
        return null;
    }

    @Override
    public Texture getTexture() {
        return _texture;
    }

    @Override
    public Shader getShader() {
        return SHADER;
    }

    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
    }


    @Override
    public void update() {

    }

}
