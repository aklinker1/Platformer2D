package com.klinker.platformer2d.sprite.abstracts;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.CollisionBox;

public abstract class Frenemy extends MovingSprite {

    /**
     * Creates an enemy with the specified qualities.
     *
     * @param position
     * @param size
     * @param texture
     * @param shader
     */
    public Frenemy(Vector3f position, Size<Float> size, Texture texture, Shader shader) {
        super(position, size, texture, shader);
        setCollision(initializeCollision());
    }

    public abstract CollisionBox initializeCollision();

    @Override
    public void update() {

    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        super.onCollideLeft(sprite);
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        super.onCollideTop(sprite);
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        super.onCollideRight(sprite);
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {
        super.onCollideBottom(sprite);
    }

    public void killPlayer() {
        // TODO: 3/15/2017 Implement how the player dies and how the scene is notified
        System.out.println("Kill Player");
    }

}
