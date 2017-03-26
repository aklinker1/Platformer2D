package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.draw.Scene;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.inputs.Input;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.platformer2d.Platformer2D;

import java.util.LinkedList;

public abstract class Menu extends Scene {

    public static Size<Float> PROJ_SIZE = new Size<Float>(
            100f * Platformer2D.tileCounts.ratioXoY(),
            100f
    );
    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            -PROJ_SIZE.width / 2f, PROJ_SIZE.width / 2f,
            PROJ_SIZE.height / 2f, -PROJ_SIZE.height / 2f,
            -1f, 1f
    );

    private LinkedList<View> views;
    //private LinkedList<Buttons> buttons;

    @Override
    public void init() {
        views = new LinkedList<>();
        initializeViews(views);
        super.init();

    }

    protected abstract void initializeViews(LinkedList<View> labels);

    @Override
    public void update() {
        boolean left = Input.isKeyDown(Input.LEFT);
        boolean up = Input.isKeyDown(Input.UP);
        boolean right = Input.isKeyDown(Input.RIGHT);
        boolean down = Input.isKeyDown(Input.DOWN);
        if (left && !right) onLeftPress();
        if (right && !left) onRightPress();
        if (up && !down) onUpPress();
        if (down && !up) onDownPress();
    }

    @Override
    public void render() {
        for (View view : views) {
            view.render();
        }
    }

    @Override
    public Matrix4f getProjMatrix() {
        return PROJ_MATRIX;
    }

    public abstract void onLeftPress();
    public abstract void onUpPress();
    public abstract void onRightPress();
    public abstract void onDownPress();

}
