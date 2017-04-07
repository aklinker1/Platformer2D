package com.klinker.platformer2d.sprite.backgrounds;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.draw.SpriteCluster;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.sprite.players.Player;

public class LevelBackground extends SpriteCluster {

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);


    private int world;
    private Player player;

    private SimpleSprite[] color;
    private SimpleSprite[] diamonds;
    private SimpleSprite[] bars;


    public LevelBackground(Vector3f position, int world, Player player) {
        super(position, new Size<Float>(Platformer2D.tileCounts.x, 3f / 4f * Platformer2D.tileCounts.x));
        this.world = world;
        this.player = player;

        createSprites();
    }

    private void createSprites() {
        this.color = new SimpleSprite[2];
        this.diamonds = new SimpleSprite[2];
        this.bars = new SimpleSprite[2];

        for (int x = 0; x < 2; x++) {
            Log.d(String.format("res/textures/bg/%02X-00.png", world));
            color[x] = new SimpleSprite(
                    position, size, String.format("res/textures/bg/%02X-00.png", world), SHADER
            );
            diamonds[x] = new SimpleSprite(
                    position, size, String.format("res/textures/bg/%02X-01.png", world), SHADER
            );
            diamonds[x].getPosition().increment(0, 0, 0.001f);
            bars[x] = new SimpleSprite(
                    position, size, String.format("res/textures/bg/%02X-02.png", world), SHADER
            );
            bars[x].getPosition().increment(0, 0, 0.002f);
            addSprite(color[x]);
            addSprite(diamonds[x]);
            addSprite(bars[x]);
        }
    }

    @Override
    public void render(Camera camera) {
        Camera stationary = new Camera(camera);
        stationary.setPosition(0, 0, 0);
        super.render(stationary);
    }

    @Override
    public void update(Camera camera) {
        // these are negative values
        float xDif = player.getPosition().globalX();
        float yOffset = camera.getPosition().globalY();

        for (int x = 0; x < 2; x++) {
            color[x].setPosition(getXOffset(x, xDif, 0.1f), yOffset * 0.1f, 0);
            diamonds[x].setPosition(getXOffset(x, xDif, 0.1f), yOffset * 0.1f, 0.001f);
            bars[x].setPosition(getXOffset(x, xDif, 0.15f), yOffset * 0.2f, 0.002f);
        }
    }

    private float getXOffset(int x, float xDif, float multiplier) {
        return x * size.width - (xDif * multiplier) % (size.width);
    }

}
