package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.scenes.Level;

public class Tile extends Sprite {

    private String world;
    private String tileNum;

    public Tile(Vector2f position, int world, int tile) {
        super(position);
        this.world = String.format("%02X", world);
        this.tileNum = String.format("%02X", tile);
        initTextureAndShader();
    }

    @Override
    public Size<Float> getSize() {
        return new Size<>(1f, 1f);
    }

    @Override
    public float getDepth() {
        return 0f;
    }

    @Override
    public CollisionBox getCollision() {
        return new CollisionBox(CollisionBox.Shape.RECTANGLE, size, new Vector2f(), position);
    }

    @Override
    public Texture getTexture() {
        return new Texture("res/textures/tiles/" + world + "-" + tileNum + ".png");
    }

    @Override
    public Shader getShader() {
        return Level.BASIC_ALPHA;
    }

    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
    }

    @Override
    public void update() {

    }

}
