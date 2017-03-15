package com.klinker.platformer2d.scenes;


import com.klinker.engine2d.graphics.Scene;
import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.sprite.Background;
import com.klinker.platformer2d.utils.Map;
import com.klinker.platformer2d.utils.MapCreator;

import java.io.File;



public class Level extends Scene {

    /**
     * A projection matrix that sets up a classical cartisian coordinate system that is in units of tiles.
     */
    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            0f, Platformer2D.TILE_COUNT.x,
            Platformer2D.TILE_COUNT.y, 0f,
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
     * The shaders used by all the objects in the
     */
    public static Shader BASIC, BASIC_ALPHA, PLAYER;


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
        map = MapCreator.read(this.level);
        background = new Background(new Vector2f(0f, -(Background.size.height - Platformer2D.TILE_COUNT.y) / 2f), map.getWorld());
    }

    @Override
    public Shader[] loadAllShaders() {
        BASIC = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);
        BASIC_ALPHA = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC_ALPHA);
        PLAYER = new Shader(R.shaders.vert.MOVE, R.shaders.frag.MOVE);
        return new Shader[]{BASIC, BASIC_ALPHA, PLAYER };
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
