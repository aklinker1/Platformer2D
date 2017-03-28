package com.klinker.platformer2d.sprite.players;


import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.inputs.KeyboardInput;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.constants.Physics;
import com.klinker.platformer2d.scenes.Level;
import com.klinker.platformer2d.sprite.abstracts.MovingSprite;


public class Player extends MovingSprite {

    public static final int HERO_BLUE_TRIANGLE = 0;
    public static final Shader SHADER = new Shader(R.shaders.vert.MOVE, R.shaders.frag.MOVE);


    private int hero;
    public boolean isGrounded;
    private Level level;
    private float launchVelX = 0;
    private boolean releasedJump = false;

    public Player(Vector2f position, int hero, Level level) {
        super(position, new Size<>(1f, 1f));
        this.hero = hero;
        this.level = level;
        initTextureAndShader();
    }

    @Override
    public float getDepth() {
        return Depth.PLAYER;
    }

    @Override
    public CollisionBox getCollision() {
        return new CollisionBox(
                CollisionBox.Shape.RECTANGLE,
                new Size<>(0.90f, 0.95f),
                new Vector2f(0.05f, 0f),
                position
        );
    }

    @Override
    public Texture getTexture() {
        return new Texture(String.format("res/textures/character/hero%02X/body.png", hero));
    }

    @Override
    public Shader getShader() {
        return SHADER;
    }

    /**
     * Gets expected changes to x velocity
     */
    @Override
    protected void accelX() {
        boolean left = KeyboardInput.isPressed(KeyboardInput.LEFT);
        boolean right = KeyboardInput.isPressed(KeyboardInput.RIGHT);
        boolean run = KeyboardInput.isPressed(KeyboardInput.RUN);

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

    private int jumpFrames = 0;
    @Override
    protected void accelY() {
        boolean jump = KeyboardInput.isPressed(KeyboardInput.JUMP);
        boolean running = Math.abs(launchVelX) > Physics.Player.MAX_VEL_X;

        if (jump && jumpFrames == 0 && isGrounded) { // pressing jump for the first time
            Log.d("If1: " + jumpFrames);
            launchVelX = xVel;
            releasedJump = false;
            jumpFrames = 1;
            running = Math.abs(launchVelX) > Physics.Player.MAX_VEL_X;
            yVel = running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED;
        } else if (jump && jumpFrames < Physics.Player.JUMP_HOLD_MAX && !releasedJump) { // We are holding the jump button
            Log.d("If2: " + jumpFrames);
            jumpFrames++;
            yVel = running ? Physics.Player.JUMP_SPEED_RUN : Physics.Player.JUMP_SPEED;
        } else {
            Log.d("If3: " + jumpFrames);
            yVel -= Physics.Player.GRAVITY;
            releasedJump = true;
        }
    }

    public void setGrounded() {
        isGrounded = true;
        jumpFrames = 0;
    }

    @Override
    protected void onCollideLeft(Sprite sprite) {
        xVel = 0;
        CollisionBox otherCollision = sprite.getCollision();
        position.x = otherCollision.position.x + otherCollision.size.width - this.collision.origin.x;
    }

    @Override
    protected void onCollideTop(Sprite sprite) {
        yVel = 0;
        jumpFrames = Physics.Player.JUMP_HOLD_MAX; // sets this so that you cannot hover against the ceiling
        CollisionBox otherCollision = sprite.getCollision();
        position.y = otherCollision.position.y - this.collision.size.height - this.collision.origin.y;
    }

    @Override
    protected void onCollideRight(Sprite sprite) {
        xVel = 0;
        CollisionBox otherCollision = sprite.getCollision();
        position.x = otherCollision.position.x - this.size.width + this.collision.origin.x;
    }

    @Override
    protected void onCollideBottom(Sprite sprite) {
        setGrounded();
        yVel = 0;
        CollisionBox otherCollision = sprite.getCollision();
        position.y = otherCollision.position.y + otherCollision.origin.y + otherCollision.size.height - this.collision.origin.y;
    }

    @Override
    protected void onCollideNone() {
    }

    @Override
    public void update() {
        // Limit y Velocity: Max fall speed.
        if (yVel < Physics.Player.MAX_FALL_SPEED) yVel = Physics.Player.MAX_FALL_SPEED;
        // TODO: Notifies when the player dies off the bottom.
        // Moves the player to the top of the screen when falling off the bottom.
        if (position.y <= -2) position.y = Platformer2D.tileCounts.y;

        // Limit x positions: prevent from going off the sides of the map.
        if (position.x < 0.25f) position.x = 0.25f;
        else if (position.x > Platformer2D.tileCounts.x - 1.25f) position.x = Platformer2D.tileCounts.x - 1.25f;
    }
}
