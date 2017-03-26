package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.sprite.SimpleSprite;

public class View {

    protected Vector2f position;
    protected Size<Float> size;

    protected Sprite background;

    public View(Vector2f position, Size<Float> size) {
        this.position = position;
        this.size = size;
    }

    public void setBackgroundTexture(String textureRes) {
        background = new SimpleSprite(position, size, textureRes);
    }

    public void render() {
        if (background != null) background.render();
    }

    public void update() {
        if (background != null) background.update();
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Size<Float> getSize() {
        return size;
    }

    public Vector2f getPosition() {
        return position;
    }
}
