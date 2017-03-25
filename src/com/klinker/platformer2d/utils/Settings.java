package com.klinker.platformer2d.utils;

import com.klinker.engine2d.utils.Preferences;

public class Settings extends Preferences {

    public static final String KEY_TILE_WIDTH = "tile_width";
    public static final String KEY_ASPECT_RATIO = "aspect_ratio";
    public static final String KEY_FRAME_RATE = "frame_rate";
    public static final String KEY_WINDOW_WIDTH = "window_width";
    //public static final String KEY_ = "";

    public Settings() {
    }

    public Settings(String path) {
        super(path);
    }

}
