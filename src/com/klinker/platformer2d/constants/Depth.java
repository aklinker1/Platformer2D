package com.klinker.platformer2d.constants;

/**
 * Smaller Depth = first to be drawn.
 * This means low depths get drawn behind higher depths.
 * <p>
 * The range is (-1, 1].
 */
public class Depth {

    public static final float BACKGROUND_BACK = -0.99f;
    public static final float BACKGROUND_MIDDLE = -0.98f;
    public static final float BACKGROUND_FRONT = -0.97f;

    public static final float ENEMY = 0.1f;
    public static final float ENEMY_FRONT = 0.2f;

    public static final float PLAYER_GHOST = -0.1f;
    public static final float PLAYER = 0.0f;

    public static final float TILE_BACK = -0.2f;
    public static final float TILE = 0.0f;
    public static final float TILE_FRONT = 0.2f;

}
