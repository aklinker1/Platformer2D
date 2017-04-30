package com.klinker.platformer2d;


import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.inputs.Controller;
import com.klinker.engine2d.inputs.InputManager;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.scenes.Level;
import com.klinker.platformer2d.scenes.SettingsMenu;
import com.klinker.platformer2d.utils.Settings;


public class Platformer2D extends Engine {

    public static final String SETTINGS_PATH = "settings.pref";

    public static Vector2f tileCounts;

    private Settings settings;

    public static void main(String[] args) {
        Platformer2D platformer = new Platformer2D();
        platformer.setStyle(Style.SMOOTH);
        platformer.setFullscreen(true);
        Sprite.showCollisions = true;
        platformer.start();
    }

    public Platformer2D() {
        settings = initializeSettings();
        setInputManager(setUpInputManager());

        if (true) setScene(new SettingsMenu(this));
        else setScene(new Level(this, R.levels.W02_LXX));
    }

    @Override
    protected void onFinish() {
        // Save settings
        if (settings != null) settings.outputToFile(SETTINGS_PATH);
    }

    @Override
    public String getWindowTitle() {
        return R.strings.GAME_TITLE;
    }

    private Settings initializeSettings() {
        Settings settings = new Settings(SETTINGS_PATH);

        // set the settings.
        frameRate = settings.getInt(Settings.KEY_FRAME_RATE);
        float aspectRatio = settings.getFloat(Settings.KEY_ASPECT_RATIO);
        int windowWidth = settings.getInt(Settings.KEY_WINDOW_WIDTH);
        //windowWidth = 1920;
        windowSize = new Size<>(windowWidth, (int) (windowWidth * aspectRatio));
        int tileWidth = settings.getInt(Settings.KEY_TILE_WIDTH);
        tileCounts = new Vector2f(tileWidth, aspectRatio * tileWidth);

        return settings;
    }

    private InputManager setUpInputManager() {
        Controller controller = settings.getController();
        return new InputManager(controller);
    }

}
