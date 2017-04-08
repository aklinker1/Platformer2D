package com.klinker.platformer2d.sprite.abstracts;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.sprite.tiles.Tile;
import com.klinker.platformer2d.utils.SparseArray2D;

import java.util.LinkedList;

public abstract class MovingSprite extends Sprite {

    public static Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.MOVE);

    /**
     * This draw velocity. Positive is upward/right, negative is downward/left.
     * The {@link Sprite#position} is incremented by this value every frame.
     */
    public Vector3f vel = new Vector3f();

    /**
     * @see Sprite#Sprite(Vector3f, Size, Texture, Shader)
     */
    public MovingSprite(Vector3f position, Size<Float> size, Texture texture, Shader shader) {
        super(position, size, texture, shader);
    }

    /**
     * A custom implementation of update that updates this sprite based on it's surroundings.
     *
     * @param tiles     The tiles around it.
     * @param frenemies The frenemies spawned on the screen.
     */
    public void update(Camera camera, SparseArray2D<Tile> tiles, LinkedList<Frenemy> frenemies) {
        // 1. Modify velocities
        accelX();
        accelY();

        // 2. Check for collisions and modify velocities
        checkCollisions(tiles, frenemies);

        // 3. Call the regular update
        update(camera);

        // 4. Actually move the player.
        position.increment(vel.x(), vel.y(), vel.z());
    }

    /**
     * Gets expected changes to x velocity
     */
    protected void accelX() {
    }

    /**
     * Gets expected changes to y velocity
     */
    protected void accelY() {
    }

    /**
     * @see MovingSprite#onCollideLeft(Sprite)
     */
    protected abstract void onCollideLeft(Sprite sprite);

    /**
     * @see MovingSprite#onCollideLeft(Sprite)
     */
    protected abstract void onCollideTop(Sprite sprite);

    /**
     * @see MovingSprite#onCollideLeft(Sprite)
     */
    protected abstract void onCollideRight(Sprite sprite);

    /**
     * @see MovingSprite#onCollideLeft(Sprite)
     */
    protected abstract void onCollideBottom(Sprite sprite);

    /**
     * Called when a sprite hits nothing as it moves
     */
    protected abstract void onCollideNone();

    /**
     * Checks the collisions on the maps and notifies the draw that collide.
     *
     * @param tiles     The tiles on the map.
     * @param frenemies The frenemies on the map.
     */
    private void checkCollisions(SparseArray2D<Tile> tiles, LinkedList<Frenemy> frenemies) {
        int TOP = 0, LEFT = 1, RIGHT = 2, BOTTOM = 3;
        boolean collided = false;

        // region Checking Tiles
        for (int dir = 0; dir <= BOTTOM; dir++) {
            // get the range in tiles to check
            // to prevent inaccurate floating point arithmetic, convert everything to int for * 1000 and rounding
            int xStart = (int) (1000 * this.collision.position.x()) + (int) (1000 * this.collision.origin.x);
            int yStart = (int) (1000 * this.collision.position.y()) + (int) (1000 * this.collision.origin.y);
            int xEnd = xStart + (int) (1000 * this.collision.size.width);
            int yEnd = yStart + (int) (1000 * this.collision.size.height);
            int xEndI = xEnd / 1000;
            int yEndI = yEnd / 1000;

            int curXMin = xStart / 1000;
            int curYMin = yStart / 1000;
            int curXMax = xEnd / 1000;
            int curYMax = yEnd / 1000;
            int futXMin = (xStart + (int) (vel.x() * 1000)) / 1000;
            int futYMin = (yStart + (int) (vel.y() * 1000)) / 1000;
            int futXMax = (xEnd + (int) (vel.x() * 1000)) / 1000;
            int futYMax = (yEnd + (int) (vel.y() * 1000)) / 1000;

            // handles how if the collision ends at a whole number, we want to exclude that from the loop
            if (xEnd - xEndI * 1000 == 0) curXMax--;
            if (yEnd - yEndI * 1000 == 0) curYMax--;

            if (dir == TOP && vel.y() > 0) { // moving upward, check tiles above me.
                for (int x = curXMin; x <= curXMax; x++) {
                    Tile tile = tiles.get(x, futYMax); // at the future y pos
                    if (tile != null && tile.getCollisionBox().intersects(this.collision, vel.x(), vel.y())) {
                        onCollideTop(tile);
                        collided = true;
                        break;
                    }
                }
            } else if (dir == LEFT && vel.x() < 0) { // Moving left, check to the left of me.
                for (int y = curYMin; y <= curYMax; y++) {
                    Tile tile = tiles.get(futXMin, y); // at the future x pos
                    if (tile != null && tile.getCollisionBox().intersects(this.collision, vel.x(), vel.y())) {
                        onCollideLeft(tile);
                        collided = true;
                        break;
                    }
                }
            } else if (dir == RIGHT && vel.x() > 0) { // moving right, check the right of me.
                for (int y = curYMin; y <= curYMax; y++) {
                    Tile tile = tiles.get(futXMax, y); // at the future x pos
                    if (tile != null && tile.getCollisionBox().intersects(this.collision, vel.x(), vel.y())) {
                        onCollideRight(tile);
                        collided = true;
                        break;
                    }
                }
            } else if (dir == BOTTOM && vel.y() < 0) { // moving downward/walking, check for collisions beneath me.
                for (int x = curXMin; x <= curXMax; x++) {
                    Tile tile = tiles.get(x, futYMin); // at the future y pos
                    if (tile != null && tile.getCollisionBox().intersects(this.collision, vel.x(), vel.y())) {
                        onCollideBottom(tile);
                        collided = true;
                        break;
                    }
                }
            }
        }
        // endregion

        // region Checking Frenemies
        for (MovingSprite frenemy : frenemies)
            if (this != frenemy) {
                if (this.collision.intersects(frenemy.getCollisionBox(), vel.x(), vel.y())) {
                    float angle = (float) Math.abs(180 / 3.14159 * Math.atan(
                            (this.position.y() - frenemy.position.y()) /
                                    (this.position.x() - frenemy.position.x())
                    ));
                    if (Math.abs(angle) >= 45) { // there was a collision on top or bottom
                        if (position.y() > frenemy.position.y()) {
                            this.onCollideBottom(frenemy);
                            frenemy.onCollideTop(this);
                        } else if (position.y() < frenemy.position.y()) {
                            this.onCollideTop(frenemy);
                            frenemy.onCollideBottom(this);
                        }
                    } else {
                        if (position.x() < frenemy.position.x()) {
                            this.onCollideLeft(frenemy);
                            frenemy.onCollideRight(this);
                        } else if (position.x() > frenemy.position.x()) {
                            this.onCollideRight(frenemy);
                            frenemy.onCollideLeft(this);
                        }
                    }
                }
            }
        // endregion

        // No collision detected
        if (!collided) onCollideNone();
    }

}
