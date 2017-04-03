package com.klinker.platformer2d.sprite.abstracts;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;

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
        setCollision(getCollisionBox());
    }

    public abstract CollisionBox getCollisionBox();

    @Override
    public void update(Camera camera) {

    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        Log.d(this.getClass().getSimpleName() + " collided left with " + sprite.getClass().getSimpleName());
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        Log.d(this.getClass().getSimpleName() + " collided top with " + sprite.getClass().getSimpleName());
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        Log.d(this.getClass().getSimpleName() + " collided right with " + sprite.getClass().getSimpleName());
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {

    }

}
