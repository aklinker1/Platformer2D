package com.klinker.platformer2d.sprite.tiles;

import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

public class Tile extends Sprite {

    private String world;
    private String tileNum;

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC_ALPHA);

    public Tile(Vector2f position, int world, int tile) {
        super(position, new Size<>(1f, 1f));
        this.world = String.format("%02X", world);
        this.tileNum = String.format("%02X", tile);
        initTextureAndShader();
    }

    @Override
    public float getDepth() {
        return Depth.TILE;
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
