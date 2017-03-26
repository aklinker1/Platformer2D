package com.klinker.platformer2d.sprite.abstracts;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.constants.Depth;

public abstract class Enemy extends MovingSprite {

    /**
     * @see Sprite#Sprite(Vector2f, Size)
     */
    public Enemy(Vector2f position, Size<Float> size) {
        super(position, size);
    }

    @Override
    public float getDepth() {
        return Depth.ENEMY;
    }

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
