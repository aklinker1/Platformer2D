package com.klinker.platformer2d.sprite.frenemies.players;


import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.inputs.InputManager;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.constants.Physics;
import com.klinker.platformer2d.sprite.abstracts.Enemy;
import com.klinker.platformer2d.sprite.abstracts.Frenemy;


public class Player extends Frenemy {

    public static final int HERO_BLUE_TRIANGLE = 0;


    private int hero;
    public boolean isGrounded;
    private float launchVelX = 0;
    private boolean releasedJump = false;

    public Player(Vector2f position, int hero) {
        super(
                new Vector3f(position.x, position.y, Depth.PLAYER),
                new Size<>(1f, 1f),
                new Texture(R.textures.players.hero01.BODY),
                Frenemy.SHADER
        );
        this.hero = hero;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return new CollisionBox(
                CollisionBox.Shape.RECTANGLE,
                new Size<>(0.90f, 0.95f),
                new Vector2f(0.05f, 0f),
                position
        );
    }

    /**
     * Gets expected changes to x velocity
     */
    @Override
    protected void accelX() {
        boolean left = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_LEFT);
        boolean right = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_RIGHT);
        boolean run = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_RUN);

        float accel;
        if (right && !left) { // trying to go right
            if (vel.x() >= 0) { // accelerate
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
            if (vel.x() <= 0) { // accelerate
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
            if (vel.x() > Physics.Player.DECELERATE / 2f) accel = -Physics.Player.DECELERATE;
            else if (vel.x() < -Physics.Player.DECELERATE / 2f) accel = Physics.Player.DECELERATE;
            else accel = -vel.x();
        }

        // change velocity
        vel.increment(accel, 0, 0);

        if (run) {
            if (vel.x() > Physics.Player.MAX_VEL_X_RUN) vel.setX(Physics.Player.MAX_VEL_X_RUN);
            else if (vel.x() < -Physics.Player.MAX_VEL_X_RUN) vel.setX(-Physics.Player.MAX_VEL_X_RUN);
        } else {
            if (vel.x() > Physics.Player.MAX_VEL_X) vel.setX(Physics.Player.MAX_VEL_X);
            else if (vel.x() < -Physics.Player.MAX_VEL_X) vel.setX(-Physics.Player.MAX_VEL_X);
        }
    }

    private int jumpFrames = 0;
    @Override
    protected void accelY() {
        boolean jump = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_JUMP);
        boolean running = Math.abs(launchVelX) > Physics.Player.MAX_VEL_X;

        if (jump && jumpFrames == 0 && isGrounded) { // pressing jump for the first time
            launchVelX = vel.x();
            releasedJump = false;
            jumpFrames = 1;
            running = Math.abs(launchVelX) > Physics.Player.MAX_VEL_X;
            vel.setY(running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED);
        } else if (jump && jumpFrames < Physics.Player.JUMP_HOLD_MAX && !releasedJump) { // We are holding the jump button
            jumpFrames++;
            vel.setY(running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED);
        } else {
            vel.increment(0, -Physics.GRAVITY, 0);
            releasedJump = true;
        }
    }

    public void setGrounded() {
        isGrounded = true;
        jumpFrames = 0;
    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        super.onCollideLeft(sprite);
        if (sprite instanceof Enemy && !((Enemy) sprite).isSafeRight()) {
            killPlayer();
        } else {
            vel.setX(0);
            CollisionBox otherCollision = sprite.getCollisionBox();
            position.setX(otherCollision.position.x() + otherCollision.size.width - this.collision.origin.x);
        }
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        super.onCollideTop(sprite);
        if (sprite instanceof Enemy && !((Enemy) sprite).isSafeBottom()) {
            killPlayer();
        } else {
            vel.setY(0);
            jumpFrames = Physics.Player.JUMP_HOLD_MAX; // sets this so that you cannot hover against the ceiling
            CollisionBox otherCollision = sprite.getCollisionBox();
            position.setY(otherCollision.position.y() - this.collision.size.height - this.collision.origin.y);
        }
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        super.onCollideRight(sprite);
        if (sprite instanceof Enemy && !((Enemy) sprite).isSafeLeft()) {
            killPlayer();
        } else {
            vel.setX(0);
            CollisionBox otherCollision = sprite.getCollisionBox();
            position.setX(otherCollision.position.x() - this.size.width + this.collision.origin.x);
        }
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {
        super.onCollideBottom(sprite);
        if (sprite instanceof Enemy && !((Enemy) sprite).isSafeTop()) {
            killPlayer();
        } else {
            setGrounded();
            vel.setY(0);
            CollisionBox otherCollision = sprite.getCollisionBox();
            position.setY(otherCollision.position.y() + otherCollision.origin.y + otherCollision.size.height - this.collision.origin.y);
        }
    }

    @Override
    protected void onCollideNone() {

    }

    @Override
    public void update(Camera camera) {
        // Limit y Velocity: Max fall speed.
        if (vel.y() < Physics.Player.MAX_FALL_SPEED) vel.setY(Physics.Player.MAX_FALL_SPEED);
        // TODO: Notifies when the player dies off the bottom.
        // Moves the player to the top of the screen when falling off the bottom.
        if (position.y() <= -2) position.setY(Platformer2D.tileCounts.y + 1);
    }

    private void killPlayer() {
        Log.d("KILLED PLAYER");
    }

}
