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
    public void render() {
        for (Sprite sprite : sprites) sprite.render();
    }

    @Override
    public void update() {
        for (Sprite sprite : sprites) sprite.update();
    }

}
