package com.klinker.engine2d.draw;

public interface Drawable {

    void render(Camera camera);

    void update(Camera camera);

    String description();

}
