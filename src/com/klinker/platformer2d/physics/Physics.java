package com.klinker.platformer2d.physics;

public class Physics {

    public static class Player {
        public static final float DECELERATE = 0.04f;
        public static final float ACCELERATE_TURN = 0.009f;
        public static final float ACCELERATE_GROUND = 0.002f;
        public static final float ACCELERATE_GROUND_RUN = 0.004f;
        public static final float ACCELERATE_AIR = 0.01f;
        public static final float ACCELERATE_AIR_RUN = 0.02f;
        public static final float GRAVITY = 0.04f;
        public static final float MAX_FALL_SPEED = 0.35f;
        public static final float JUMP_SPEED = 0.5f;
        public static final float MAX_CHANGE_X = 5; // blocks/s
        public static final float MAX_VEL_X = MAX_CHANGE_X / 60f; // blocks/s/frame
    }

}