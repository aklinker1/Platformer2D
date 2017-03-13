package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.graphics.CollisionBox;
import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.maths.Vector3f;
import com.klinker.platformer2d.scenes.Level;

import java.util.ArrayList;
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

    public void update(Tile bl, Tile br, Tile tl, Tile tr, LinkedList<MovingSprite> frenemies) {
        // 1. Modify velocities
        accelX();
        accelY();

        // 2. Check for collisions and modify velocities
        checkCollisions(bl, br, tl, tr, frenemies);

        // 3. Actually move the player.
        position.x += xVel;
        position.y += yVel;
    }

    @Override
    @Deprecated
    public void update() { }

    protected abstract void accelX();
    protected abstract void accelY();
    protected abstract void checkCollisions(Tile bl, Tile br, Tile tl, Tile tr, LinkedList<MovingSprite> frenemies);

}
