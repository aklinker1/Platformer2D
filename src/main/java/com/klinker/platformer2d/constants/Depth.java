package com.klinker.platformer2d.constants;

/**
 * Smaller Depth = first to be drawn.
 * This means low depths get drawn behind higher depths.
 * <p>
 * The range is (-1, 1].
 */
public class Depth {

    public static final float BACKGROUND_BACK = 0.00f;
    public static final float BACKGROUND_MIDDLE = 0.01f;
    public static final float BACKGROUND_FRONT = 0.02f;

    public static final float ENEMY = 0.60f;
    public static final float ENEMY_FRONT = 0.61f;

    public static final float PLAYER_GHOST = 0.49f;
    public static final float PLAYER = 0.50f;

    public static final float TILE_BACK = 0.40f;
    public static final float TILE = 0.62f;
    public static final float TILE_FRONT = 0.70f;

    public static final float HUD_LOW = 0.98f;
    public static final float HUD = 0.99f;
    public static final float HUD_HIGH = 1.00f;

}
