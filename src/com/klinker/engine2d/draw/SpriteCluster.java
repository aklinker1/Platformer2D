package com.klinker.engine2d.draw;

import java.util.LinkedList;

public abstract class SpriteCluster implements Drawable {

    private LinkedList<Sprite> sprites;

    protected SpriteCluster() {
        sprites = new LinkedList<>();
    }

    public void addSprite(Sprite sprite) {
        sprites.addLast(sprite);
    }

    @Override
    public void render(Camera camera) {
        for (Sprite sprite : sprites) sprite.render(camera);
    }

    @Override
    public void update() {
        for (Sprite sprite : sprites) sprite.update();
    }

}
