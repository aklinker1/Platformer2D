package com.klinker.platformer2d.constants;

public class Physics {

    public static class Player {
        public static final float DECELERATE = 0.006f;
        public static final float ACCELERATE_TURN = 0.008f;
        public static final float ACCELERATE_GROUND = 0.002f;
        public static final float ACCELERATE_GROUND_RUN = 0.004f;
        public static final float ACCELERATE_AIR = 0.001f;
        public static final float ACCELERATE_AIR_RUN = 0.003f;
        public static final float GRAVITY = 0.010f;
        public static final float MAX_FALL_SPEED = -0.300f;
        public static final float JUMP_SPEED = 0.200f;
        public static final float JUMP_SPEED_RUN = 0.250f;
        public static final float MAX_VEL_X = 0.075f; // blocks/s/frame
        public static final float MAX_VEL_X_RUN = 0.150f; // blocks/s/frame
        public static final long JUMP_HOLD_MAX = 200L;  // ms
    }

}