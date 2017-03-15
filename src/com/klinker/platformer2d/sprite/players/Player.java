package com.klinker.platformer2d.sprite.players;


import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.inputs.Input;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.constants.Physics;
import com.klinker.platformer2d.sprite.abstracts.MovingSprite;


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
        return Depth.PLAYER;
    }

    @Override
    public CollisionBox getCollision() {
        return new CollisionBox(CollisionBox.Shape.RECTANGLE, size, new Vector2f(0f, 0f), position);
    }

    @Override
    public Texture getTexture() {
        return new Texture(String.format("res/textures/character/hero%02X/body.png", hero));
    }

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
        boolean running = Math.abs(xVel) > Physics.Player.MAX_VEL_X;
        if (jump && jumpStart == 0 && isGrounded) { // pressing jump for the first time
            jumpStart = System.currentTimeMillis();
            yVel = running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED;
        } else if (jump && (System.currentTimeMillis() - jumpStart < Physics.Player.JUMP_HOLD_MAX)) { // We are holding the jump button
            yVel = running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED;
        } else {
            yVel -= Physics.Player.GRAVITY;
        }
    }

    public void setGrounded(boolean grounded) {
        isGrounded = grounded;
        if (grounded) {
            jumpStart = System.currentTimeMillis();
        } else {
            jumpStart = 0;
        }
    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        xVel = 0;
        CollisionBox collision = sprite.getCollision();
        position.x = collision.position.x + collision.origin.x + collision.size.width;
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        yVel = 0;
        CollisionBox collision = sprite.getCollision();
        position.y = collision.position.y + collision.origin.y;
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        xVel = 0;
        CollisionBox collision = sprite.getCollision();
        position.x = collision.position.x + collision.origin.x - size.width;
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {
        setGrounded(true);
        yVel = 0;
        CollisionBox otherCollision = sprite.getCollision();
        position.y = otherCollision.position.y + otherCollision.origin.y + otherCollision.size.height;
    }

    @Override
    protected void onCollideNone() {
        setGrounded(false);
    }

    @Override
    public void update() {
        if (yVel < Physics.Player.MAX_FALL_SPEED) yVel = Physics.Player.MAX_FALL_SPEED;
    }
}
