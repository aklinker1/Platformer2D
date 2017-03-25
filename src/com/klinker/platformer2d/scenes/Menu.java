package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.graphics.Scene;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.maths.Matrix4f;

import java.util.LinkedList;

public abstract class Menu extends Scene {

    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            -50f, 50f,
            50f, -50f,
            -1f, 1f
    );

    private LinkedList<View> labels;
    //private LinkedList<Buttons> buttons;

    @Override
    public void init() {
        labels = new LinkedList<>();
        initializeNavigation(labels);
        super.init();

    }

    protected abstract void initializeNavigation(LinkedList<View> labels);

    @Override
    public void update() {

    }

    @Override
    public void render() {
        for (View view : labels) {
            view.render();
        }
    }

    @Override
    public Matrix4f getProjMatrix() {
        return PROJ_MATRIX;
    }



}
