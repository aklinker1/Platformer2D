package com.klinker.platformer2d.sprite;


import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.inputs.Input;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.physics.Physics;
import com.klinker.platformer2d.utils.SparseArray2D;

import java.util.LinkedList;


public class Player extends MovingSprite {

    public static final int HERO_BLUE_TRIANGLE = 0;

    private static Size<Float> SIZE = new Size<>(1f, 1f);

    private int hero;
    public boolean isGrounded;

    public Player(Vector2f position, int hero) {
        super(position);
        this.hero = hero;
        initTextureAndShader();
    }

    @Override
    public Size<Float> getSize() {
        return SIZE;
    }

    @Override
    public float getDepth() {
        return 0.1f;
    }

    @Override
    public CollisionBox getCollision() {
        return new CollisionBox(CollisionBox.Shape.RECTANGLE, size, new Vector2f(0f, 0f), position);
    }

    @Override
    public Texture getTexture() {
        return new Texture(String.format("res/textures/char/hero%02X/body.png", hero));
    }

    /*@Override
    public Shader getShader() {
        return Level.PLAYER;
    }

    @Override
    protected void setShaderProperties(Shader shader) {
        shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(position));
    }

    @Override
    public void update() {

    }*/

    /**
     * Gets expected changes to x velocity
     */
    @Override
    protected void accelX() {
        boolean left = Input.isKeyDown(Input.LEFT);
        boolean right = Input.isKeyDown(Input.RIGHT);
        boolean run = Input.isKeyDown(Input.RUN);

        float accel;
        if (right && !left) { // trying to go right
            if (xVel >= 0) { // accelerate
                if (isGrounded) {
                    if (run) accel = Physics.Player.ACCELERATE_GROUND_RUN;
                    else accel = Physics.Player.ACCELERATE_GROUND;
                } else {
                    if (run) accel = Physics.Player.ACCELERATE_AIR_RUN;
                    else accel = Physics.Player.ACCELERATE_AIR;
                }
            } else { // turn around
                accel = Physics.Player.ACCELERATE_TURN;
            }
        } else if (left && !right) { // trying to go left
            if (xVel <= 0) { // accelerate
                if (isGrounded) {
                    if (run) accel = -Physics.Player.ACCELERATE_GROUND_RUN;
                    else accel = -Physics.Player.ACCELERATE_GROUND;
                } else {
                    if (run) accel = -Physics.Player.ACCELERATE_AIR_RUN;
                    else accel = -Physics.Player.ACCELERATE_AIR;
                }
            } else { // turn around
                accel = -Physics.Player.ACCELERATE_TURN;
            }
        } else { // not doing anything
            if (xVel > Physics.Player.DECELERATE / 2f) accel = -Physics.Player.DECELERATE;
            else if (xVel < -Physics.Player.DECELERATE / 2f) accel = Physics.Player.DECELERATE;
            else accel = -xVel;
        }

        // change velocity
        xVel += accel;

        if (run) {
            if (xVel > Physics.Player.MAX_VEL_X_RUN) xVel = Physics.Player.MAX_VEL_X_RUN;
            else if (xVel < -Physics.Player.MAX_VEL_X_RUN) xVel = -Physics.Player.MAX_VEL_X_RUN;
        } else {
            if (xVel > Physics.Player.MAX_VEL_X) xVel = Physics.Player.MAX_VEL_X;
            else if (xVel < -Physics.Player.MAX_VEL_X) xVel = -Physics.Player.MAX_VEL_X;
        }
    }

    private long jumpStart = 0;
    @Override
    protected void accelY() {
        boolean jump = Input.isKeyDown(Input.JUMP);
        if (jump && jumpStart == 0 && isGrounded) { // pressing jump for the first time
            jumpStart = System.currentTimeMillis();
            yVel = Physics.Player.JUMP_SPEED;
        } else if (jump && (System.currentTimeMillis() - jumpStart < Physics.Player.JUMP_HOLD_MAX)) { // We are holding the jump button
            yVel = Physics.Player.JUMP_SPEED;
        } else {
            yVel -= Physics.Player.GRAVITY;
        }
    }

    @Override
    protected void checkCollisions(SparseArray2D<Tile> tiles, LinkedList<MovingSprite> frenemies) {
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
                    setGrounded();
                    yVel = 0;
                    position.y = bl.getCollision().position.y + bl.getCollision().origin.y + bl.getCollision().size.height;
                } else if (br != null && br.getCollision().intersects(collision, xVel, yVel)) {
                    setGrounded();
                    yVel = 0;
                    position.y = br.getCollision().position.y + br.getCollision().origin.y + br.getCollision().size.height;
                } else {
                    isGrounded = false;
                }
            } else { // moving upward, check tiles above me.
                Tile tl = tiles.get(xf, yc); // bottom left tile
                Tile tr = tiles.get(xc, yc); // bottom right tile
                if (tl != null && tl.getCollision().intersects(collision, xVel, yVel)) {
                    yVel = 0;
                    position.y = tl.getCollision().position.y + tl.getCollision().origin.y;
                } else if (tr != null && tr.getCollision().intersects(collision, xVel, yVel)) {
                    yVel = 0;
                    position.y = tr.getCollision().position.y + tr.getCollision().origin.y;
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
                    setGrounded();
                    xVel = 0;
                    position.x = lt.getCollision().position.x + lt.getCollision().origin.x + lt.getCollision().size.width;
                } else if (lb != null && lb.getCollision().intersects(collision, xVel, yVel)) {
                    setGrounded();
                    xVel = 0;
                    position.x = lb.getCollision().position.x + lb.getCollision().origin.x + lb.getCollision().size.width;
                } else {
                    isGrounded = false;
                }
            } else { // moving right check the right of me.
                Tile rt = tiles.get(xc, yc); // bottom left tile
                Tile rb = tiles.get(xc, yf); // bottom right tile
                if (rt != null && rt.getCollision().intersects(collision, xVel, yVel)) {
                    xVel = 0;
                    position.x = rt.getCollision().position.x + rt.getCollision().origin.x - size.width;
                } else if (rb != null && rb.getCollision().intersects(collision, xVel, yVel)) {
                    xVel = 0;
                    position.x = rb.getCollision().position.x + rb.getCollision().origin.x - size.width;
                }
            }
        }
        // endregion
    }

    public void setY(float y) {
        position.y = y;
    }

    public void setGrounded() {
        isGrounded = true;
        jumpStart = System.currentTimeMillis();
    }

}
