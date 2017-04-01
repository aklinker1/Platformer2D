package com.klinker.platformer2d.utils;

import com.klinker.engine2d.inputs.Controller;
import com.klinker.engine2d.utils.BufferUtils;
import com.klinker.engine2d.utils.Log;
import com.klinker.engine2d.Engine;
import com.klinker.engine2d.utils.Preferences;
import com.klinker.platformer2d.Platformer2D;
import net.java.games.input.Component;
import com.klinker.engine2d.inputs.Keyboard;
import net.java.games.input.ControllerEnvironment;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Settings extends Preferences {

    public static final String FILE_CONTROLLER = "bindings.pref";

    public static final String KEY_TILE_WIDTH = "tile_width";
    public static final String KEY_ASPECT_RATIO = "aspect_ratio";
    public static final String KEY_FRAME_RATE = "frame_rate";
    public static final String KEY_WINDOW_WIDTH = "window_width";
    public static final String KEY_WINDOW_FULL_SCREEN = "window_full_screen";
    //public static final String KEY_ = "";

    public static final int DEFAULT_TILE_WIDTH = 28; // tiles
    public static final float DEFAULT_ASPECT_RATIO = 9 / 16f; // height / width
    public static final int DEFAULT_FRAME_RATE = 60; // fps
    public static final int DEFAULT_WINDOW_WIDTH = 1280; // pixels
    public static final boolean DEFAULT_WINDOW_FULL_SCREEN = true; // is full screen?

    private Controller controller;


    public Settings() {
        // TODO: 3/27/2017 Set up custom inputs
    }

    public Settings(String path) {
        super(path);
    }

    @Override
    protected HashMap<String, Object> getInitialMap() {
        HashMap<String, Object> data = new HashMap<>();
        data.put(Settings.KEY_FRAME_RATE, Settings.DEFAULT_FRAME_RATE);
        data.put(Settings.KEY_ASPECT_RATIO, Settings.DEFAULT_ASPECT_RATIO);
        data.put(Settings.KEY_TILE_WIDTH, Settings.DEFAULT_TILE_WIDTH);
        data.put(Settings.KEY_WINDOW_WIDTH, Settings.DEFAULT_WINDOW_WIDTH);
        data.put(Settings.KEY_WINDOW_FULL_SCREEN, Settings.DEFAULT_WINDOW_FULL_SCREEN);
        setupController();
        return data;
    }

    private void setupController() {
        this.controller = Controller.setup();
    }

    @Override
    public boolean outputToFile(String filePath) {
        if (super.outputToFile(filePath) && controller != null) {
            return controller.outputToFile(FILE_CONTROLLER);
        } else return false;
    }

    @Override
    protected HashMap<String, Object> readFromFile(String path) {
        controller = Controller.readFromFile(FILE_CONTROLLER);
        return super.readFromFile(path);
    }

    public Controller getController() {
        return controller;
    }
}
