package com.klinker.platformer2d.scenes;


import com.klinker.engine2d.draw.Scene;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.sprite.backgrounds.LevelBackgroundBack;
import com.klinker.platformer2d.sprite.players.Player;
import com.klinker.platformer2d.sprite.tiles.Tile;
import com.klinker.platformer2d.utils.Map;
import com.klinker.platformer2d.utils.MapReader;

import java.io.File;



public class Level extends Scene {

    /**
     * A projection matrix that sets up a classical cartisian coordinate system that is in units of tiles.
     */
    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            0f, Platformer2D.tileCounts.x,
            Platformer2D.tileCounts.y, 0f,
            -1f, 1f
    );


    /**
     * The file with the level data.
     */
    private File level;

    /**
     * The map loaded form the level's data.
     */
    private Map map;

    /**
     *
     */
    private Sprite background;


    /**
     * Creates a new level based on  the path to it's data file.
     *
     * @param levelPath The path to the data file.
     */
    public Level(String levelPath) {
        super();
        this.level = new File(levelPath);
    }

    @Override
    public void init() {
        super.init();
        map = MapReader.read(this, this.level);
        background = new LevelBackgroundBack(new Vector2f(0f, 0f), map.getWorld());
        background.setPosition(0f, -(background.getSize().height - Platformer2D.tileCounts.y) / 2f);
    }

    @Override
    public Shader[] loadAllShaders() {
        return new Shader[]{ LevelBackgroundBack.SHADER, Tile.SHADER, Player.SHADER };
    }

    @Override
    public Matrix4f getProjMatrix() {
        return PROJ_MATRIX;
    }

    @Override
    public void update() {
        background.update();
        map.update();
    }

    @Override
    public void render() {
        background.render();
        map.render();
    }
}
