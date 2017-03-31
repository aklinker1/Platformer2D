package com.klinker.platformer2d.scenes;


import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Scene;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.sprite.backgrounds.LevelBackground;
import com.klinker.platformer2d.sprite.players.Player;
import com.klinker.platformer2d.sprite.tiles.Tile;
import com.klinker.platformer2d.utils.Map;
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
        super(engine, new Camera(new Size<Float>(Platformer2D.tileCounts.x, Platformer2D.tileCounts.y), new Vector3f()));
        this.level = new File(levelPath);
    }

    @Override
    public void init() {
        super.init();
        map = MapReader.read(this, this.level);
        background = new LevelBackground(new Vector3f(0f, 0f, Depth.BACKGROUND_FRONT), map.getWorld());
        background.setPlayer(map.getPlayer());
    }

    @Override
    public Shader[] loadAllShaders() {
        return new Shader[]{ LevelBackground.SHADER, Tile.SHADER, Player.SHADER };
    }

    @Override
    public void update(Camera camera) {
        background.update(camera);
        map.update(camera);
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
            if (camera.getPosition().x > 0) camera.getPosition().x = 0f;
            else if (camera.getPosition().x < camera.getSize().width - map.getSize().width) camera.getPosition().x = camera.getSize().width - map.getSize().width;
        } else {
            camera.getPosition().x = 0f;
        }

        if (camera.getSize().height < map.getSize().height) {
            if (camera.getPosition().y > 0) camera.getPosition().y = 0f;
            else if (camera.getPosition().y < camera.getSize().height - map.getSize().height) camera.getPosition().y = camera.getSize().height - map.getSize().height;
        } else {
            camera.getPosition().y = 0f;
        }
    }

}
