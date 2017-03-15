package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.scenes.Level;
import com.klinker.platformer2d.utils.SparseArray2D;

import java.util.LinkedList;

public abstract class MovingSprite extends Sprite {

    /**
     * The shader used by this {@link Sprite}
     */
    private static Shader SHADER = new Shader(R.shaders.vert.MOVE, R.shaders.frag.MOVE);

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
     * @see Sprite#getShader()
     */
    @Override
    public Shader getShader() {
        return Level.PLAYER;
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

        // 3. Actually move the player.
        position.x += xVel;
        position.y += yVel;
    }

    @Override
    @Deprecated
    public void update() {
    }

    /**
     * Gets expected changes to x velocity
     */
    protected abstract void accelX();

    /**
     * Gets expected changes to y velocity
     */
    protected abstract void accelY();

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
     * Checks the collisions on the maps and notifies the sprites that collide.
     *
     * @param tiles     The tiles on the map.
     * @param frenemies The frenemies on the map.
     */
    private void checkCollisions(SparseArray2D<Tile> tiles, LinkedList<MovingSprite> frenemies) {
        // region Vertical Collision
        {
            int xf = (int) Math.floor(position.x);  // the left of the one I'm on.
            int xc = (int) Math.ceil(position.x); // the right of the one I'm on.
            int yf = (int) (position.y - 1); // the y beneath me.
            int yc = (int) (position.y + 1); // the y beneath me.

            if (yVel <= 0) { // moving downward/walking, check for collisions beneath me.
                Tile bl = tiles.get(xf, yf); // bottom left tile
                Tile br = tiles.get(xc, yf); // bottom right tile
                if (bl != null && bl.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideBottom(bl);
                } else if (br != null && br.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideBottom(br);
                } else {
                    onCollideNone();
                }
            } else { // moving upward, check tiles above me.
                Tile tl = tiles.get(xf, yc); // bottom left tile
                Tile tr = tiles.get(xc, yc); // bottom right tile
                if (tl != null && tl.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideTop(tl);
                } else if (tr != null && tr.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideTop(tr);
                }
            }
        }
        // endregion
        // region Horizontal Collisions
        {
            int xf = (int) Math.floor(position.x - 1);  // the left of the one I'm on.
            int xc = (int) Math.ceil(position.x + 1); // the right of the one I'm on.
            int yf = (int) Math.floor(position.y); // the y beneath me.
            int yc = (int) Math.ceil(position.y); // the y beneath me.

            if (xVel < 0) { // Moving left, check to the left of me.
                Tile lt = tiles.get(xf, yc); // left top tile
                Tile lb = tiles.get(xf, yf); // left bottom tile
                if (lt != null && lt.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideLeft(lt);
                } else if (lb != null && lb.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideLeft(lb);
                }
            } else if (xVel > 0) { // moving right check the right of me.
                Tile rt = tiles.get(xc, yc); // bottom left tile
                Tile rb = tiles.get(xc, yf); // bottom right tile
                if (rt != null && rt.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideRight(rt);
                } else if (rb != null && rb.getCollision().intersects(collision, xVel, yVel)) {
                    onCollideRight(rb);
                }
            } else { // you aren't moving and something hit you.
                // TODO: 3/15/2017 Figure out how to handle this and when it occurs
                System.err.println("Not moving, got hit!");
            }
        }
        // endregion
    }

}
