package com.klinker.platformer2d.sprite.backgrounds;

import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.scenes.Level;

public class LevelBackgroundBack extends Sprite {

    private int world;
    public static Size<Float> size = new Size<>(Platformer2D.TILE_COUNT.x, 3f / 4f * Platformer2D.TILE_COUNT.x);

    public LevelBackgroundBack(Vector2f position, int world) {
        super(position);
        this.world = world;
        initTextureAndShader();
    }

    @Override
    public Size<Float> getSize() {
        return size;
    }

    @Override
    public float getDepth() {
        return Depth.BACKGROUND_BACK;
    }

    @Override
    public CollisionBox getCollision() {
        return new CollisionBox(CollisionBox.Shape.RECTANGLE, size, new Vector2f(0, 0), position);
    }

    @Override
    public Texture getTexture() {
        return new Texture("res/textures/bg/" + String.format("%02X", world) + "-00.png");
    }

    @Override
    public Shader getShader() {
        return Level.BASIC;
    }

    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
        //shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(new Vector3f(i * 10 + -scrollX * 0.03f, 0f, 0f)));
    }

    @Override
    public void update() {

    }

}
