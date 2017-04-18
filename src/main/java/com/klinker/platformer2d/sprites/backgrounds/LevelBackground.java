package com.klinker.platformer2d.sprites.backgrounds;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.draw.SpriteCluster;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.sprites.players.Player;

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
            Vector3f cPos = new Vector3f();
            cPos.setRelative(position);
            color[x] = new SimpleSprite(
                    cPos, size, R.textures.bg.W01_00.replace("w01", String.format("w%02X", world)), SHADER
            );
            Vector3f dPos = new Vector3f(0, 0, 0.001f);
            dPos.setRelative(position);
            diamonds[x] = new SimpleSprite(
                    dPos, size, R.textures.bg.W01_01.replace("w01", String.format("w%02X", world)), SHADER
            );
            Vector3f bPos = new Vector3f(0, 0, 0.002f);
            bPos.setRelative(position);
            bars[x] = new SimpleSprite(
                    bPos, size, R.textures.bg.W01_02.replace("w01", String.format("w%02X", world)), SHADER
            );
        }
        for (int x = 0; x < 2; x++) addSprite(color[x]);
        for (int x = 0; x < 2; x++) addSprite(diamonds[x]);
        for (int x = 0; x < 2; x++) addSprite(bars[x]);
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
        float xDif = -camera.getPosition().globalX();
        float yOffset = camera.getPosition().globalY();

        for (int x = 0; x < 2; x++) {
            color[x].getPosition().setLocalX(0);
            color[x].getPosition().setLocalY(0);
            diamonds[x].getPosition().setLocalX(getXOffset(x, xDif, 0.15f)); // was 0.2f
            diamonds[x].getPosition().setLocalY(yOffset * 0.15f); // was 0.2f
            bars[x].getPosition().setLocalX(getXOffset(x, xDif, 0.3f)); // was 0.3f
            bars[x].getPosition().setLocalY(yOffset * 0.3f); // was 0.3f
            Log.d("color[" + x + "] = " + color[x].getPosition().localX());
            Log.d("diamonds[" + x + "] = " + diamonds[x].getPosition().localX());
            Log.d("bars[" + x + "] = " + bars[x].getPosition().localX());
        }
        Log.d("");
    }

    private float getXOffset(int x, float xDif, float multiplier) {
        // todo: When this whole value is < 0, add the size to it.
        return size.width * x - Math.abs(multiplier * xDif) % size.width;
    }

}
