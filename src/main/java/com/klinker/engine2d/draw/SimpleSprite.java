package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.platformer2d.R;

public class SimpleSprite extends Sprite {

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);
    private String textRes;

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
        this.textRes = textRes;
    }

    @Override
    protected void setShaderProperties(Shader shader, Camera camera) {
        super.setShaderProperties(shader, camera);
    }

    @Override
    public void update(Camera camera) {
        super.update(camera);
        camera.addToLayers(this, position.globalZ());
    }

    @Override
    public String description() {
        return "Texture: " + textRes;
    }
}
