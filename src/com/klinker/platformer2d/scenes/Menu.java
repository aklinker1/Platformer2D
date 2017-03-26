package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.draw.Scene;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.platformer2d.Platformer2D;

import java.util.LinkedList;

public abstract class Menu extends Scene {

    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            -50f * Platformer2D.tileCounts.ratioXoY(), 50f * Platformer2D.tileCounts.ratioXoY(),
            50f, -50f,
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



}
