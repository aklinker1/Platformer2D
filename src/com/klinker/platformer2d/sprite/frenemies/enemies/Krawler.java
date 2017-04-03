package com.klinker.platformer2d.sprite.frenemies.enemies;


import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Physics;
import com.klinker.platformer2d.sprite.abstracts.Enemy;
import com.klinker.platformer2d.sprite.abstracts.MovingSprite;


public class Krawler extends Enemy {

    public static Texture texture = new Texture(R.textures.enemies.KRAWLER);

    public Krawler(Vector3f position) {
        super(
                position,
                new Size<Float>(1f, 1f),
                texture,
                MovingSprite.SHADER
        );
        vel.x = -Physics.Krawler.VEL_X;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(
                CollisionBox.Shape.RECTANGLE,
                new Size<Float>(50f / 80f, 65f / 80f),
                new Vector2f(30f / 2f / 80, 0f),
                position
        );
    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        super.onCollideLeft(sprite);
        vel.x *= -1;
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        super.onCollideTop(sprite);
        Log.d("Bopped");
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        super.onCollideRight(sprite);
        vel.x *= -1;
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {
        super.onCollideBottom(sprite);
        vel.y = 0;
        CollisionBox otherCollision = sprite.getCollisionBox();
        position.y = otherCollision.position.y + otherCollision.origin.y + otherCollision.size.height - this.collision.origin.y;

    }

    @Override
    protected void onCollideNone() {
        Log.d("Krawler in freefall");
    }

    @Override
    protected void accelY() {
        vel.y -= Physics.GRAVITY;
    }

    @Override
    public boolean isSafeLeft() {
        return false;
    }

    @Override
    public boolean isSafeTop() {
        return true;
    }

    @Override
    public boolean isSafeRight() {
        return false;
    }

    @Override
    public boolean isSafeBottom() {
        return false;
    }
}
