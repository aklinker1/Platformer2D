package com.klinker.platformer2d.sprites;

import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;


public abstract class Enemy extends Frenemy {

    /**
     * Creates an enemy with the specified qualities.
     *
     * @param position
     * @param size
     * @param texture
     * @param shader
     */
    public Enemy(Vector3f position, Size<Float> size, Texture texture, Shader shader) {
        super(position, size, texture, shader);
    }

    public abstract boolean isSafeLeft();

    public abstract boolean isSafeTop();

    public abstract boolean isSafeRight();

    public abstract boolean isSafeBottom();

}
