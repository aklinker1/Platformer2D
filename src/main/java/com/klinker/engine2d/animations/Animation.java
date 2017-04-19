package com.klinker.engine2d.animations;

import com.klinker.engine2d.draw.Sprite;

public abstract class Animation<T extends Sprite> {

    private int frame;
    private boolean loop;

    protected Animation(boolean loop) {
        this.loop = loop;
        this.frame = 0;
    }

    public void update(T sprite) {
        update(sprite, frame);
        frame++;
    }

    protected abstract void update(T sprite, final int frame);

    protected void resetFrameCounter() {
        frame = 0;
    }

    public boolean isLooping() {
        return loop;
    }

}
