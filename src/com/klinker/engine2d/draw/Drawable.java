package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Vector3f;

public interface Drawable {

    void render(Camera camera);

    void update(Camera camera);

}
