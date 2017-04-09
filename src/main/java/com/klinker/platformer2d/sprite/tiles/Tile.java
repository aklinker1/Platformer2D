package com.klinker.platformer2d.sprite.tiles;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Tile extends Sprite {

    private int world;
    private int tile;

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);

    public static Tile newInstance(Vector2f position, int world, int tile) {
        String klassName = String.format("com.klinker.platformer2d.tile.T%02X", tile);
        try {
            Class<?> klass = Class.forName(klassName);
            Constructor<?> constructor = klass.getConstructor(String.class);
            return (Tile) constructor.newInstance(new Object[]{ world });
        } catch (ClassNotFoundException e) {
            return new Tile(new Vector3f(position.x, position.y, Depth.TILE), world, tile);
        } catch (NoSuchMethodException e) {
            Log.e("Could not find ", e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Log.e("Error calling new instance", e);
        }
        return null;
    }

    protected Tile(Vector3f position, int world, int tile) {
        super(
                position,
                new Size<>(1f, 1f),
                new Texture(String.format("src/main/resources/textures/tiles/%02X-%02X.png", world, tile)),
                SHADER
        );
        this.world = world;
        this.tile = tile;
        setCollision(new CollisionBox(CollisionBox.Shape.RECTANGLE, size, new Vector2f(), position));
    }

    @Override
    public void update(Camera camera) {

    }

}
