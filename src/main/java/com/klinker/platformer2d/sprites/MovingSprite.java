package com.klinker.platformer2d.sprites;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.sprites.tiles.Tile;
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
        position.increment(vel.globalX(), vel.globalY(), vel.globalZ());
    }

    /**
     * Gets expected changes to globalX velocity
     */
    protected void accelX() {
    }

    /**
     * Gets expected changes to globalY velocity
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
            int xStart = (int) (1000 * this.collision.position.globalX());
            int yStart = (int) (1000 * this.collision.position.globalY());
            int xEnd = xStart + (int) (1000 * this.collision.size.width);
            int yEnd = yStart + (int) (1000 * this.collision.size.height);
            int xEndI = xEnd / 1000;
            int yEndI = yEnd / 1000;

            int curXMin = xStart / 1000;
            int curYMin = yStart / 1000;
            int curXMax = xEnd / 1000;
            int curYMax = yEnd / 1000;
            int futXMin = (xStart + (int) (vel.globalX() * 1000)) / 1000;
            int futYMin = (yStart + (int) (vel.globalY() * 1000)) / 1000;
            int futXMax = (xEnd + (int) (vel.globalX() * 1000)) / 1000;
            int futYMax = (yEnd + (int) (vel.globalY() * 1000)) / 1000;

            // handles how if the collision ends at a whole number, we want to exclude that from the loop
            if (xEnd - xEndI * 1000 == 0) curXMax--;
            if (yEnd - yEndI * 1000 == 0) curYMax--;

            if (dir == TOP && vel.globalY() > 0) { // moving upward, check tiles above me.
                for (int x = curXMin; x <= curXMax; x++) {
                    Tile tile = tiles.get(x, futYMax); // at the future globalY pos
                    if (tile != null && tile.initializeCollision().intersects(this.collision, vel.globalX(), vel.globalY())) {
                        onCollideTop(tile);
                        collided = true;
                        break;
                    }
                }
            } else if (dir == LEFT && vel.globalX() < 0) { // Moving left, check to the left of me.
                for (int y = curYMin; y <= curYMax; y++) {
                    Tile tile = tiles.get(futXMin, y); // at the future globalX pos
                    if (tile != null && tile.initializeCollision().intersects(this.collision, vel.globalX(), vel.globalY())) {
                        onCollideLeft(tile);
                        collided = true;
                        break;
                    }
                }
            } else if (dir == RIGHT && vel.globalX() > 0) { // moving right, check the right of me.
                for (int y = curYMin; y <= curYMax; y++) {
                    Tile tile = tiles.get(futXMax, y); // at the future globalX pos
                    if (tile != null && tile.initializeCollision().intersects(this.collision, vel.globalX(), vel.globalY())) {
                        onCollideRight(tile);
                        collided = true;
                        break;
                    }
                }
            } else if (dir == BOTTOM && vel.globalY() < 0) { // moving downward/walking, check for collisions beneath me.
                for (int x = curXMin; x <= curXMax; x++) {
                    Tile tile = tiles.get(x, futYMin); // at the future globalY pos
                    if (tile != null && tile.initializeCollision().intersects(this.collision, vel.globalX(), vel.globalY())) {
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
                if (this.collision.intersects(frenemy.initializeCollision(), vel.globalX(), vel.globalY())) {
                    float angle = (float) Math.abs(180 / 3.14159 * Math.atan(
                            (this.position.globalY() - frenemy.position.globalY()) /
                                    (this.position.globalX() - frenemy.position.globalX())
                    ));
                    if (Math.abs(angle) >= 45) { // there was a collision on top or bottom
                        if (position.globalY() > frenemy.position.globalY()) {
                            this.onCollideBottom(frenemy);
                            frenemy.onCollideTop(this);
                        } else if (position.globalY() < frenemy.position.globalY()) {
                            this.onCollideTop(frenemy);
                            frenemy.onCollideBottom(this);
                        }
                    } else {
                        if (position.globalX() < frenemy.position.globalX()) {
                            this.onCollideLeft(frenemy);
                            frenemy.onCollideRight(this);
                        } else if (position.globalX() > frenemy.position.globalX()) {
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
