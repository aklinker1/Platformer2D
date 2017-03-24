package com.klinker.platformer2d.sprite.abstracts;

import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.sprite.tiles.Tile;
import com.klinker.platformer2d.utils.SparseArray2D;

import java.util.LinkedList;

public abstract class MovingSprite extends Sprite {

    /**
     * This sprites y velocity. Positive is upward, negative is downward.
     * The {@link Sprite#position} is incremented by this value every frame.
     */
    public float xVel = 0f;

    /**
     * This sprites y velocity. Positive is upward, negative is downward.
     * The {@link Sprite#position} is incremented by this value every frame.
     */
    public float yVel = 0f;

    /**
     * @see Sprite#Sprite(Vector2f)
     */
    public MovingSprite(Vector2f position) {
        super(position);
    }

    /**
     * @see Sprite#setShaderProperties(Shader)
     */
    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
    }

    /**
     * A custom implementation of update that updates this sprite based on it's surroundings.
     *
     * @param tiles     The tiles around it.
     * @param frenemies The frenemies spawned on the screen.
     */
    public void update(SparseArray2D<Tile> tiles, LinkedList<MovingSprite> frenemies) {
        // 1. Modify velocities
        accelX();
        accelY();

        // 2. Check for collisions and modify velocities
        checkCollisions(tiles, frenemies);

        // 3. Call the regular update
        update();

        // 4. Actually move the player.
        position.x += xVel;
        position.y += yVel;
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
    protected void onCollideLeft(Sprite sprite) {
    }

    /**
     * @see MovingSprite#onCollideLeft(Sprite)
     */
    protected void onCollideTop(Sprite sprite) {
    }

    /**
     * @see MovingSprite#onCollideLeft(Sprite)
     */
    protected void onCollideRight(Sprite sprite) {
    }

    /**
     * @see MovingSprite#onCollideLeft(Sprite)
     */
    protected void onCollideBottom(Sprite sprite) {
    }

    /**
     * Called when a sprite hits nothing as it moves
     */
    protected void onCollideNone() {
    }

    /**
     * Checks the collisions on the maps and notifies the sprites that collide.
     *
     * @param tiles     The tiles on the map.
     * @param frenemies The frenemies on the map.
     */
    private void checkCollisions(SparseArray2D<Tile> tiles, LinkedList<MovingSprite> frenemies) {
        // to prevent inaccurate floating point arithmatic, convert everything to int for * 1000 and rounding
        // get the range in tiles to check
        int xStart = (int) (1000 * this.collision.position.x) + (int) (1000 * this.collision.origin.x);
        int yStart = (int) (1000 * this.collision.position.y) + (int) (1000 * this.collision.origin.y);
        int xEnd = xStart + (int) (1000 * this.collision.size.width);
        int yEnd = yStart + (int) (1000 * this.collision.size.height);
        int xEndI = xEnd / 1000;
        int yEndI = yEnd / 1000;

        int curXMin = xStart / 1000;
        int curYMin = yStart / 1000;
        int curXMax = xEnd / 1000;
        int curYMax = yEnd / 1000;
        int futXMin = (int) (xStart / 1000 + xVel);
        int futYMin = (int) (yStart / 1000 + yVel);
        int futXMax = (int) (xEnd / 1000 + xVel);
        int futYMax = (int) (yEnd / 1000 + yVel);

        // handles how if the collision ends at a whole number, we want to exclude that from the loop
        if (xEnd - xEndI * 1000 == 0) curXMax--;
        if (yEnd - yEndI * 1000 == 0) curYMax--;

        boolean collision = false;
        if (xVel < 0) { // Moving left, check to the left of me.
            for (int y = curYMin; y <= curYMax; y++) {
                Tile tile = tiles.get(futXMin, y); // at the future x pos
                if (tile != null && tile.getCollision().intersects(this.collision, xVel, yVel)) {
                    onCollideLeft(tile);
                    collision = true;
                    break;
                }
            }
        } else if (xVel > 0) { // moving right, check the right of me.
            for (int y = curYMin; y <= curYMax; y++) {
                Tile tile = tiles.get(futXMax, y); // at the future x pos
                if (tile != null && tile.getCollision().intersects(this.collision, xVel, yVel)) {
                    onCollideRight(tile);
                    collision = true;
                    break;
                }
            }
        }

        // re-initialize the positions if there was a collision
        if (collision) {
            xStart = (int) (1000 * this.collision.position.x) + (int) (1000 * this.collision.origin.x);
            yStart = (int) (1000 * this.collision.position.y) + (int) (1000 * this.collision.origin.y);
            xEnd = xStart + (int) (1000 * this.collision.size.width);
            yEnd = yStart + (int) (1000 * this.collision.size.height);
            xEndI = xEnd / 1000;
            yEndI = yEnd / 1000;

            curXMin = xStart / 1000;
            curYMin = yStart / 1000;
            curXMax = xEnd / 1000;
            curYMax = yEnd / 1000;
            futXMin = (int) (xStart / 1000 + xVel);
            futYMin = (int) (yStart / 1000 + yVel);
            futXMax = (int) (xEnd / 1000 + xVel);
            futYMax = (int) (yEnd / 1000 + yVel);

            if (xEnd - xEndI * 1000 == 0) curXMax--;
            if (yEnd - yEndI * 1000 == 0) curYMax--;
        }

        if (yVel < 0) { // moving downward/walking, check for collisions beneath me.
            for (int x = curXMin; x <= curXMax; x++) {
                Tile tile = tiles.get(x, futYMin); // at the future y pos
                if (tile != null && tile.getCollision().intersects(this.collision, xVel, yVel)) {
                    System.out.println("  Bottom @block(" + x + ", " + ((int) (yStart + yVel)) + "), pos:" + position.get2D().toString() + ", size:" + this.collision.size.toString());
                    System.out.println("    Before: xVel = " + xVel + ", yVel = " + yVel);
                    onCollideBottom(tile);
                    System.out.println("    After: xVel = " + xVel + ", yVel = " + yVel);
                    System.out.println("    curXMin = " + curXMin + ", curXMax = " + curXMax);
                    break;
                }
            }
        } else if (yVel > 0) { // moving upward, check tiles above me.
            for (int x = curXMin; x < curXMax; x++) {
                Tile tile = tiles.get(x, futYMax); // at the future y pos
                if (tile != null && tile.getCollision().intersects(this.collision, xVel, yVel)) {
                    System.out.println("  Top @block(" + x + ", " + ((int) (yEnd + yVel)) + "), pos:" + position.get2D().toString() + ", size:" + this.collision.size.toString());
                    System.out.println("    Before: xVel = " + xVel + ", yVel = " + yVel);
                    onCollideTop(tile);
                    System.out.println("    After: xVel = " + xVel + ", yVel = " + yVel);
                    break;
                }
            }
        }
    }

}
