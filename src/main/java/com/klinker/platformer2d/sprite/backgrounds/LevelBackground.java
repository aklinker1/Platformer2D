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
import com.klinker.platformer2d.sprite.frenemies.players.Player;

public class LevelBackground extends SpriteCluster {

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);


    private int world;
    private Player player;

    private SimpleSprite[] color;
    private SimpleSprite[] diamonds;
    private SimpleSprite[] bars;


    public LevelBackground(Vector3f position, int world) {
        super(position, new Size<Float>(Platformer2D.tileCounts.x, 3f / 4f * Platformer2D.tileCounts.x));
        this.world = world;

        createSprites();
    }

    private void createSprites() {
        this.color = new SimpleSprite[2];
        this.diamonds = new SimpleSprite[2];
        this.bars = new SimpleSprite[2];

        for (int x = 0; x < 2; x++) {
            Log.d(R.textures.bg.W01_00.replace("w01", String.format("w%02X", world)));
            color[x] = new SimpleSprite(
                    position, size, R.textures.bg.W01_00.replace("w01", String.format("w%02X", world)), SHADER
            );
            color[x].setTranslation(0, 0, 0);
            diamonds[x] = new SimpleSprite(
                    position, size, R.textures.bg.W01_01.replace("w01", String.format("w%02X", world)), SHADER
            );
            diamonds[x].setTranslation(0, 0, 0.001f);
            bars[x] = new SimpleSprite(
                    position, size, R.textures.bg.W01_02.replace("w01", String.format("w%02X", world)), SHADER
            );
            bars[x].setTranslation(0, 0, 0.002f);
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
        float xDif = player.getPosition().x();
        float yOffset = camera.getPosition().y();

        for (int x = 0; x < 2; x++) {
            color[x].setTranslation(getXOffset(x, xDif, 0.1f), yOffset * 0.1f, 0);
            diamonds[x].setTranslation(getXOffset(x, xDif, 0.1f), yOffset * 0.1f, 0.001f);
            bars[x].setTranslation(getXOffset(x, xDif, 0.15f), yOffset * 0.2f, 0.002f);
        }
    }

    private float getXOffset(int x, float xDif, float multiplier) {
        return x * size.width - (xDif * multiplier) % (size.width);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
