package com.klinker.engine2d.gui;

import com.klinker.engine2d.graphics.Sprite;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector3f;

public class View {

    private Vector3f position;
    private Size<Float> size;

    private Sprite background;

    public View(Vector3f position, Size<Float> size) {
        this.position = position;
        this.size = size;
    }

    public void render() {
        if (background != null) background.render();
    }

    public void update() {
        if (background != null) background.update();
    }

}
