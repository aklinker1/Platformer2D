package com.klinker.platformer2d.sprite;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.CollisionBox;
import com.klinker.engine2d.utils.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class Frenemy extends MovingSprite {

    public static Frenemy fromString(String simpleName, String[] data) {
        boolean found = false;
        Class klass;
        try {
            klass = Class.forName("com.klinker.platformer2d.sprite.enemies." + simpleName);
        } catch (ClassNotFoundException e1) {
            try {
                klass = Class.forName("com.klinker.platformer2d.sprite.friends." + simpleName);
            } catch (ClassNotFoundException e2) {
                try {
                    klass = Class.forName("com.klinker.platformer2d.sprite.players." + simpleName);
                } catch (ClassNotFoundException e3) {
                    Log.e("Error loading " + simpleName, e3);
                    return null;
                }
            }
        }
        try {
            Method newInstance = klass.getMethod("newInstance", HashMap.class);
            HashMap<String, String> mapping = new HashMap<>();
            for (String datum : data) {
                String[] parts = datum.split(":");
                String key = parts[0];
                String value = parts[1];
                mapping.put(key, value);
            }
            Log.d("Mapping: " + mapping.toString());
            return (Frenemy) newInstance.invoke(null, new Object[]{mapping});
        } catch (NoSuchMethodException e) {
            Log.e("No newInstance method in " + simpleName);
        } catch (InvocationTargetException | IllegalAccessException e) {
            Log.e("Other error while loading " + simpleName, e);
        }
        return null;
    }

    private boolean isSpawned = true;

    /**
     * Creates an enemy with the specified qualities.
     *
     * @param position
     * @param size
     * @param texture
     * @param shader
     */
    public Frenemy(Vector3f position, Size<Float> size, Texture texture, Shader shader) {
        super(position, size, texture, shader);
        setCollision(initializeCollision());
    }

    public abstract CollisionBox initializeCollision();

    @Override
    public void update(Camera camera) {

    }

    public boolean isSpawned() {
        return isSpawned;
    }

}
