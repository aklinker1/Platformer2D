package com.klinker.platformer2d.sprite.tiles;

import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Tile extends SimpleSprite {

    public static final String[] tileMapping = new String[]{

    };

    private int world;
    private int tile;

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);

    public Tile(Vector3f position, Size<Float> size, String textRes) {
        super(position, size, textRes);
    }

    public static Tile newInstance(Vector2f position, int world, int tile) {
        String klassName = "com.klinker.platformer2d.tile." + tileMapping[tile];
        try {
            Class<?> klass = Class.forName(klassName);
            Constructor<?> constructor = klass.getConstructor(Vector2f.class, int.class);
            return (Tile) constructor.newInstance(position, world);
        } catch (ClassNotFoundException e) {
            Log.d("Tile not created: " + tile + ", " + tileMapping[tile]);
        } catch (NoSuchMethodException e) {
            Log.e("Could not find constructor for " + tileMapping[tile], e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Log.e("Error calling constructor for " + tileMapping[tile], e);
        }
        return null;
    }

}
