package com.klinker.platformer2d.sprites.tiles;

import com.klinker.engine2d.collisions.CollisionBox;
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
        /* 0x00 */ null,
        /* 0x01 */ "ground.Fill",
        /* 0x02 */ "ground.FillShapeOutline",
        /* 0x03 */ "ground.FillShape",
        /* 0x04 */ "ground.EndLeft",
        /* 0x05 */ "ground.Ground",
        /* 0x06 */ "ground.EndRight",
        /* 0x07 */ "ground.Single",
        /* 0x08 */ "ground.EndRightCovered",
        /* 0x09 */ "ground.EndLeftCovered",

        /* 0x0A */ "blocks.EndLeft",
        /* 0x0B */ "blocks.Block",
        /* 0x0C */ "blocks.EndRight",
        /* 0x0D */ "blocks.EndBottom",
        /* 0x0E */ "blocks.EndTop",
        /* 0x0F */ "blocks.EndTopLeft",
        /* 0x10 */ "blocks.EndTopRight",
        /* 0x11 */ "blocks.EndBottomRight",
        /* 0x12 */ "blocks.EndBottomLeft",
        /* 0x13 */ "blocks.Single",
        /* 0x14 */ null,
        /* 0x15 */ null,
        /* 0x16 */ null,
        /* 0x17 */ null,
        /* 0x18 */ null,
        /* 0x19 */ null,
        /* 0x1A */ null,
        /* 0x1B */ null,
        /* 0x1C */ null,
        /* 0x1D */ null,
        /* 0x1E */ null,
        /* 0x1F */ null,
    };

    private int world;
    protected Vector2f position2d;

    public static final Shader SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.BASIC);

    /**
     * Constructor for a tile.
     *
     * @param position The 2D position on the map.
     * @param depth    The draw depth of sprite.
     * @param size     The size of the sprite, generally even numbers.
     * @param textRes  The W01 texture string, it will be changed to the needed world.
     * @param world    The world the tile should pull it's texture from.
     */
    public Tile(Vector2f position, float depth, Size<Float> size, String textRes, int world) {
        super(
                new Vector3f(position.x, position.y, depth),
                size,
                // replacing the world reference in the texture to the requested world
                textRes.replace("w01", String.format("w%02X", world))
        );
        this.world = world;
        this.position2d = position;
        initializeCollision();
    }

    public static Tile newInstance(Vector2f position, int world, int tile) {
        String klassName = null;
        try {
            klassName = "com.klinker.platformer2d.sprites.tiles." + tileMapping[tile];
            Class<?> klass = Class.forName(klassName);
            Constructor<?> constructor = klass.getConstructor(Vector2f.class, int.class);
            return (Tile) constructor.newInstance(position, world);
        } catch (ClassNotFoundException e) {
            Log.d("Tile class not found: " + tile + ", " + tileMapping[tile] + ", " + klassName);
        } catch (NoSuchMethodException e) {
            Log.e("Could not find constructor for " + klassName, e);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("tile #" + tile + " has not been added to the mapping.");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Log.e("Error calling constructor for " + klassName, e);
        }
        return null;
    }

    public int getWorld() {
        return world;
    }

    public abstract CollisionBox initializeCollision();
}
