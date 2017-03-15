package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.scenes.Level;
import com.klinker.platformer2d.utils.SparseArray2D;

import java.util.LinkedList;

public abstract class MovingSprite extends Sprite {

    private static Shader SHADER = new Shader("res/shaders/move.vert", "res/shaders/move.frag");
    public float xVel = 0f;
    public float yVel = 0f;

    public MovingSprite(Vector2f position) {
        super(position);
    }

    @Override
    public Shader getShader() {
        return Level.PLAYER;
    }

    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
    }

    public void update(SparseArray2D<Tile> tiles, LinkedList<MovingSprite> frenemies) {
        // 1. Modify velocities
        accelX();
        accelY();

        // 2. Check for collisions and modify velocities
        checkCollisions(tiles, frenemies);

        // 3. Actually move the player.
        position.x += xVel;
        position.y += yVel;
    }

    @Override
    @Deprecated
    public void update() { }

    protected abstract void accelX();
    protected abstract void accelY();

    protected abstract void checkCollisions(SparseArray2D<Tile> tiles, LinkedList<MovingSprite> frenemies);

}
