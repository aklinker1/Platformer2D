package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;

import java.util.LinkedList;

public abstract class SpriteCluster implements Drawable {

    private LinkedList<Sprite> sprites;
    protected Vector3f position;
    protected Size<Float> size;

    protected SpriteCluster(Vector3f position, Size<Float> size) {
        this.position = position;
        this.size = size;
        this.sprites = new LinkedList<>();
    }

    public void addSprite(Sprite sprite) {
        sprites.addLast(sprite);
    }

    @Override
    public void render(Camera camera) {
        for (Sprite sprite : sprites) sprite.render(camera);
    }

    @Override
    public void update(Camera camera) {
        for (Sprite sprite : sprites) sprite.update(camera);
    }

    public Size<Float> getSize() {
        return size;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.setX(x);
        this.position.setY(y);
        this.position.setX(z);
    }

}
