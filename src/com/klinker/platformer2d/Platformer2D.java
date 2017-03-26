package com.klinker.platformer2d;


import com.klinker.engine2d.Engine;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.constants.Defaults;
import com.klinker.platformer2d.scenes.Level;
import com.klinker.platformer2d.scenes.MainMenu;
import com.klinker.platformer2d.utils.Settings;


public class Platformer2D extends Engine {

    private static final String SETTINGS_PATH = "settings.pref";

    public static Vector2f tileCounts;

    private Settings settings;

    public static void main(String[] args) {
        Platformer2D platformer = new Platformer2D();
        platformer.setStyle(Style.SMOOTH);
        platformer.start();
    }

    public Platformer2D() {
        settings = initializeSettings();
        if (true) setScene(new MainMenu());
        else setScene(new Level(R.levels.W02_LXX));
    }

    @Override
    protected void onFinish() {
        // Save settings
        if (settings != null) settings.outputToFile(SETTINGS_PATH);
    }

    @Override
    public String getWindowTitle() {
        return "Platformer 2D";
    }

    private Settings initializeSettings() {
        Settings settings = new Settings(SETTINGS_PATH);

        // check to see if a settings file exists, and initialize the defaults because it's the first open
        if (!settings.hasData()) {
            settings = new Settings();
            settings.put(Settings.KEY_FRAME_RATE, Defaults.FRAME_RATE);
            settings.put(Settings.KEY_ASPECT_RATIO, Defaults.ASPECT_RATIO);
            settings.put(Settings.KEY_TILE_WIDTH, Defaults.TILE_WIDTH);
            settings.put(Settings.KEY_WINDOW_WIDTH, Defaults.WINDOW_WIDTH);
        }

        frameRate = settings.getInt(Settings.KEY_FRAME_RATE);
        float aspectRatio = settings.getFloat(Settings.KEY_ASPECT_RATIO);
        int windowWidth = settings.getInt(Settings.KEY_WINDOW_WIDTH);
        windowSize = new Size<>(windowWidth, (int) (windowWidth * aspectRatio));
        int tileWidth = settings.getInt(Settings.KEY_TILE_WIDTH);
        tileCounts = new Vector2f(tileWidth, aspectRatio * tileWidth);

        return settings;
    }

}
