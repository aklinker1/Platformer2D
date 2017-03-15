package com.klinker.platformer2d.physics;

/**
 * Smaller Depth = first to be drawn.
 * This means low depths get drawn behind higher depths.
 * <p>
 * The range is [-1, 1].
 */
public class Depth {

    public static final float BACKGROUND_BOTTOM = -1f;
    public static final float BACKGROUND_MIDDLE = -0.9f;
    public static final float BACKGROUND_TOP = -0.8f;
    public static final float ENEMY = -0.1f;
    public static final float PLAYER = 0.0f;

}
