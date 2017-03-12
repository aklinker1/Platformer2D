package com.klinker.platformer2d.sprite;


import com.klinker.engine2d.graphics.CollisionBox;
import com.klinker.engine2d.graphics.Texture;
import com.klinker.engine2d.inputs.Input;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.physics.Physics;

import java.util.LinkedList;


public class Player extends MovingSprite {

    public static final int HERO_BLUE_TRIANGLE = 0;

    private static Size<Float> SIZE = new Size<>(1f, 1f);

    private int hero;
    public boolean isGrounded;
    public boolean isRunning;

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
            else if (xVel < Physics.Player.DECELERATE / 2f) accel = Physics.Player.DECELERATE;
            else accel = -xVel;
        }

        // change velocity
        xVel += accel;
    }

    @Override
    protected void accelY() {

    }

    @Override
    protected void checkCollisions(Tile bl, Tile br, Tile tl, Tile tr, LinkedList<MovingSprite> frenemies) {

    }

    public void setY(float y) {
        position.y = y;
    }

}
