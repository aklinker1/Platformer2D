package com.klinker.platformer2d.scenes;



import com.klinker.engine2d.graphics.Scene;
import com.klinker.engine2d.graphics.Shader;
import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.maths.Matrix4f;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.sprite.Background;
import com.klinker.platformer2d.utils.Map;
import com.klinker.platformer2d.utils.MapCreator;
import org.lwjgl.opengl.GL11;

import java.io.File;



public class Level extends Scene {

    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            0f, Platformer2D.TILE_COUNT.x,
            Platformer2D.TILE_COUNT.y, 0f,
            -1f, 1f
    );

    private File level;
    private Map map;
    private Sprite background;

    public static Shader BASIC, BASIC_ALPHA, PLAYER;

    public Level(String levelName) {
        super();
        this.level = new File("res/levels", levelName);
    }

    @Override
    public void init() {
        super.init();
        map = MapCreator.read(this.level);
        background = new Background(new Vector2f(0f, -(Background.size.height - Platformer2D.TILE_COUNT.y) / 2f), map.getWorld());
    }

    @Override
    public void loadAllShaders() {
        BASIC = new Shader("res/shaders/basic.vert", "res/shaders/basic.frag");
        addShaderToScene(BASIC);
        BASIC_ALPHA = new Shader("res/shaders/basic.vert", "res/shaders/basic-alpha.frag");
        addShaderToScene(BASIC_ALPHA);
        PLAYER = new Shader("res/shaders/move.vert", "res/shaders/move.frag");
        addShaderToScene(PLAYER);
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
