package com.klinker.platformer2d.sprites.players;


import com.klinker.engine2d.collisions.CollisionBox;
import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.inputs.InputManager;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.constants.Physics;
import com.klinker.platformer2d.sprites.Enemy;
import com.klinker.platformer2d.sprites.Frenemy;
import com.klinker.platformer2d.utils.MapReader;

import java.util.HashMap;


public class Player extends Frenemy {

    public static Player newInstance(HashMap<String, String> mapping) {
        int x = Integer.parseInt(mapping.get(MapReader.parameters.POS_X));
        int y = Integer.parseInt(mapping.get(MapReader.parameters.POS_Y));
        int hero = Integer.parseInt(mapping.get(MapReader.parameters.HERO), 16);
        return new Player(new Vector2f(x, y), hero);
    }

    public static final int HERO_BLUE_TRIANGLE = 0;


    private int hero;
    private float launchVelX = 0;
    private boolean isJumping = false;

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
    public CollisionBox initializeCollision() {
        return new CollisionBox(
                CollisionBox.Shape.RECTANGLE,
                new Size<>(0.90f, 0.95f),
                new Vector2f(0.05f, 0f),
                position
        );
    }

    /**
     * Gets expected changes to globalX velocity
     */
    @Override
    protected void accelX() {
        boolean left = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_LEFT);
        boolean right = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_RIGHT);
        boolean run = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_RUN);

        float accel;
        if (right && !left) { // trying to go right
            if (vel.globalX() >= 0) { // accelerate
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
            if (vel.globalX() <= 0) { // accelerate
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
            final float decel = isGrounded ? Physics.Player.DECELERATE_GROUND : Physics.Player.DECELERATE_AIR;
            if (vel.globalX() > -decel / 2f) accel = decel;
            else if (vel.globalX() < decel / 2f) accel = -decel;
            else accel = -vel.globalX();
        }

        // change velocity
        vel.increment(accel, 0, 0);

        if (run || !isGrounded) {
            if (vel.globalX() > Physics.Player.MAX_VEL_X_RUN) vel.setLocalX(Physics.Player.MAX_VEL_X_RUN);
            else if (vel.globalX() < -Physics.Player.MAX_VEL_X_RUN) vel.setLocalX(-Physics.Player.MAX_VEL_X_RUN);
        } else {
            if (vel.globalX() > Physics.Player.MAX_VEL_X) vel.setLocalX(Physics.Player.MAX_VEL_X);
            else if (vel.globalX() < -Physics.Player.MAX_VEL_X) vel.setLocalX(-Physics.Player.MAX_VEL_X);
        }
    }

    private int jumpFrames = 0;
    @Override
    protected void accelY() {
        boolean isPressingJump = Platformer2D.getInputManager().isPressed(InputManager.BUTTON_JUMP);
        boolean running = Math.abs(launchVelX) > Physics.Player.MAX_VEL_X;

        if (isPressingJump) {
            if (jumpFrames == 0 && isGrounded) {
                isJumping = true;
                launchVelX = vel.globalX();
                jumpFrames = 1;
                running = Math.abs(launchVelX) > Physics.Player.MAX_VEL_X;
                vel.setLocalY(running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED);
            } else if (jumpFrames > 0 && jumpFrames < Physics.Player.JUMP_HOLD_MAX && isJumping) {
                jumpFrames++;
                vel.setLocalY(running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED);
            } else {
                vel.increment(0, -Physics.GRAVITY, 0);
            }
        } else {
            isJumping = false;
            jumpFrames = 16;
            vel.increment(0, -Physics.GRAVITY, 0);
            if (isGrounded) {
                jumpFrames = 0;
            }
        }
    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        if (!(sprite instanceof Enemy)) {
            vel.setLocalX(0);
            CollisionBox otherCollision = sprite.initializeCollision();
            position.setLocalX(otherCollision.position.globalX() + otherCollision.size.width - this.collision.position.localX());
        }
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        if (!(sprite instanceof Enemy)) {
            vel.setLocalY(0);
            jumpFrames = Physics.Player.JUMP_HOLD_MAX; // sets this so that you cannot hover against the ceiling
            CollisionBox otherCollision = sprite.initializeCollision();
            position.setLocalY(otherCollision.position.globalY() - this.collision.size.height - this.collision.position.localY());
        }
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        if (!(sprite instanceof Enemy)) {
            vel.setLocalX(0);
            CollisionBox otherCollision = sprite.initializeCollision();
            position.setLocalX(otherCollision.position.globalX() - this.size.width + this.collision.position.localX());
        }
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {
        super.onCollideBottom(sprite);
        if (!(sprite instanceof Enemy)) {
            vel.setLocalY(0);
            CollisionBox otherCollision = sprite.initializeCollision();
            position.setLocalY(otherCollision.position.globalY() + otherCollision.size.height - this.collision.position.localY());
        }
    }

    @Override
    public void update(Camera camera) {
        super.update(camera);
        // Limit globalY Velocity: Max fall speed.
        if (vel.globalY() < Physics.Player.MAX_FALL_SPEED) vel.setLocalY(Physics.Player.MAX_FALL_SPEED);
        // TODO: Notifies when the player dies off the bottom.
        // Moves the player to the top of the screen when falling off the bottom.
        if (position.globalY() <= -2) position.setLocalY(Platformer2D.tileCounts.y + 1);
    }

    private void killPlayer() {
        Log.d("KILLED PLAYER");
    }

    @Override
    public String description() {
        return "Player";
    }
}
