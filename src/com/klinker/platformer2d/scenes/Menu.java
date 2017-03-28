package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Scene;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.inputs.KeyboardInput;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.platformer2d.Platformer2D;

import java.util.LinkedList;

public abstract class Menu extends Scene {

    private static final int CHECK_INPUT_EVERY = 4;

    public static Size<Float> PROJ_SIZE = new Size<Float>(
            100f * Platformer2D.tileCounts.ratioXoY(),
            100f
    );
    public static Matrix4f PROJ_MATRIX = Matrix4f.orthographic(
            -PROJ_SIZE.width / 2f, PROJ_SIZE.width / 2f,
            PROJ_SIZE.height / 2f, -PROJ_SIZE.height / 2f,
            -1f, 1f
    );
    private int frameCount = 0;

    private LinkedList<View> views;
    //private LinkedList<Buttons> buttons;


    public Menu(Engine engine) {
        super(engine);
    }

    @Override
    public void init() {
        views = new LinkedList<>();
        initializeViews(views);
        super.init();

    }

    protected abstract void initializeViews(LinkedList<View> labels);

    @Override
    public void update() {
        if (frameCount >= CHECK_INPUT_EVERY) {
            frameCount = 0;
            boolean left = KeyboardInput.isPressed(KeyboardInput.LEFT);
            boolean up = KeyboardInput.isPressed(KeyboardInput.UP);
            boolean right = KeyboardInput.isPressed(KeyboardInput.RIGHT);
            boolean down = KeyboardInput.isPressed(KeyboardInput.DOWN);
            if (left && !right) onLeftPress();
            if (right && !left) onRightPress();
            if (up && !down) onUpPress();
            if (down && !up) onDownPress();
        } else {
            frameCount++;
        }
        for (View view : views) view.update();
    }

    @Override
    public void render() {
        for (View view : views) view.render();
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
