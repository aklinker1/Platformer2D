package com.klinker.platformer2d.sprite.enemies;


import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.constants.Physics;
import com.klinker.platformer2d.sprite.Enemy;
import com.klinker.platformer2d.sprite.Frenemy;
import com.klinker.platformer2d.sprite.players.Player;
import com.klinker.platformer2d.utils.MapReader;

import java.util.HashMap;


// TODO: 4/6/2017 Krawler Collision is not showing.
public class Krawler extends Enemy {

    public static Texture texture = new Texture(R.textures.enemies.KRAWLER);

    public static Krawler newInstance(HashMap<String, String> mapping) {
        int x = Integer.parseInt(mapping.get(MapReader.parameters.POS_X));
        int y = Integer.parseInt(mapping.get(MapReader.parameters.POS_Y));
        int dir = mapping.get(MapReader.parameters.DIR).equals("left") ? -1 : 1;
        Krawler krawler = new Krawler(new Vector2f(x, y));
        krawler.vel.setLocalX(krawler.vel.globalX() * dir);
        return krawler;
    }

    public Krawler(Vector2f position) {
        super(
                new Vector3f(position.x, position.y, Depth.ENEMY),
                new Size<Float>(1f, 1f),
                texture,
                Frenemy.SHADER
        );
        vel.setLocalX(Physics.Krawler.VEL_X);
    }


    @Override
    public CollisionBox initializeCollision() {
        return new CollisionBox(
                CollisionBox.Shape.RECTANGLE,
                new Size<Float>(50f / 80f, 65f / 80f),
                new Vector2f(30f / 2f / 80, 0f),
                position
        );
    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        if (sprite.getClass() == Player.class) {
            Log.d("Kill player");
        } else {
            vel.setLocalX(-1 * vel.globalX());
        }
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        if (sprite.getClass() == Player.class) {
            Log.d("Bopped");
        }
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        if (sprite.getClass() == Player.class) {
            Log.d("Kill player");
        } else {
            vel.setLocalX(-1 * vel.globalX());
        }
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {
        vel.setLocalY(0);
        CollisionBox otherCollision = sprite.initializeCollision();
        position.setLocalY(otherCollision.position.globalY() + otherCollision.size.height - this.collision.position.localY());
    }

    @Override
    protected void onCollideNone() {

    }

    @Override
    protected void accelY() {
        vel.setLocalY(vel.globalY() - Physics.GRAVITY);
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
