package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.graphics.Label;
import com.klinker.engine2d.graphics.Scene;
import com.klinker.engine2d.maths.Matrix4f;

import java.util.LinkedList;

public abstract class Menu extends Scene {

    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            -50f, 50f,
            50f, -50f,
            -1f, 1f
    );

    private LinkedList<Label> labels;
    //private LinkedList<Buttons> buttons;

    @Override
    public void init() {
        labels = new LinkedList<>();
        initializeNavigation(labels);
        super.init();

    }

    protected abstract void initializeNavigation(LinkedList<Label> labels);

    @Override
    public void update() {

    }

    @Override
    public void render() {
        for (Label label : labels) {
            label.render();
        }
    }

    @Override
    public Matrix4f getProjMatrix() {
        return PROJ_MATRIX;
    }



}
