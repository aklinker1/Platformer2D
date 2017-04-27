package com.klinker.platformer2d.scenes;


import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.gui.Scene;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprites.Map;
import com.klinker.platformer2d.sprites.backgrounds.LevelBackground;
import com.klinker.platformer2d.sprites.players.Player;
import com.klinker.platformer2d.sprites.tiles.Tile;
import com.klinker.platformer2d.utils.MapReader;

import java.io.File;



public class Level extends Scene {

    /**
     * The file with the level data.
     */
    private File level;

    /**
     * The map loaded form the level's data.
     */
    private Map map;

    /**
     * The background of the level.
     */
    private LevelBackground background;


    /**
     * Creates a new level based on  the path to it's data file.
     *
     * @param levelPath The path to the data file.
     */
    public Level(Engine engine, String levelPath) {
        super(engine);
        this.level = new File(levelPath);
    }

    @Override
    public Camera initializeCamera() {
        return new Camera(new Size<Float>(Platformer2D.tileCounts.x, Platformer2D.tileCounts.y), new Vector2f());
    }

    @Override
    public void init() {
        Log.d("Loading times: ");
        long start = System.currentTimeMillis();
        long prevTime = start;
        super.init();
        Log.d("- Super(): " + (System.currentTimeMillis() - prevTime) / 1000f + " s");
        prevTime = System.currentTimeMillis();

        map = MapReader.read(this, this.level);
        Log.d("- Map creation: " + (System.currentTimeMillis() - prevTime) / 1000f + " s");
        prevTime = System.currentTimeMillis();

        background = new LevelBackground(new Vector3f(0f, 0f, Depth.BACKGROUND_MIDDLE), map.getWorld());
        Log.d("- Background: " + (System.currentTimeMillis() - prevTime) / 1000f + " s");
        Log.d("Total: " + (System.currentTimeMillis() - start) / 1000f + " s");

    }

    @Override
    public Shader[] loadAllShaders() {
        return new Shader[]{SimpleSprite.SHADER, LevelBackground.SHADER, Tile.SHADER, Player.SHADER};
    }

    @Override
    public void update(Camera camera) {
        background.update(camera);
        map.update(camera);
    }

    @Override
    public String description() {
        return this.level.getName();
    }

    @Override
    public void render(Camera camera) {
        background.render(camera);
        map.render(camera);
    }

    @Override
    protected void scrollCamera(Camera camera) {
        Player player = map.getPlayer();
        camera.centerXY(player.getPosition());

        // limiting the camera to stay withing the bounds of the map.
        if (camera.getSize().width < map.getSize().width) {
            if (camera.getPosition().globalX() > 0) camera.getPosition().setLocalX(0f);
            else if (camera.getPosition().globalX() < camera.getSize().width - map.getSize().width)
                camera.getPosition().setLocalX(camera.getSize().width - map.getSize().width);
        } else {
            camera.getPosition().setLocalX(0f);
        }

        if (camera.getSize().height < map.getSize().height) {
            if (camera.getPosition().globalY() > 0) camera.getPosition().setLocalY(0f);
            else if (camera.getPosition().globalY() < camera.getSize().height - map.getSize().height)
                camera.getPosition().setLocalY(camera.getSize().height - map.getSize().height);
        } else {
            camera.getPosition().setLocalY(0f);
        }
    }

}
