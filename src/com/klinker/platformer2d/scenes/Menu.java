package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Scene;
import com.klinker.engine2d.gui.Button;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.inputs.KeyboardInput;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.utils.MenuNavigation;

import java.util.LinkedList;

public abstract class Menu extends Scene {

    public static Size<Float> PROJ_SIZE = new Size<Float>(
            100f * Platformer2D.tileCounts.ratioXoY(),
            100f
    );


    private LinkedList<View> views;

    private MenuNavigation<Button> navigation;


    public Menu(Engine engine) {
        super(engine, new Camera(PROJ_SIZE, new Vector3f(PROJ_SIZE.width / 2f, PROJ_SIZE.height / 2f, 0f)));
    }

    @Override
    public void init() {
        views = new LinkedList<>();
        this.navigation = new MenuNavigation<>();
        navigation.setOnItemSelectedListener((Button oldButton, Button newButton) -> {
            if (oldButton != null) oldButton.setState(View.State.DEFAULT);
            if (newButton != null) newButton.setState(View.State.SELECTED);
        });
        initializeViews(views, navigation);
        super.init();

    }

    protected abstract void initializeViews(LinkedList<View> views, MenuNavigation<Button> navigation);

    @Override
    public void update(Camera camera) {
        boolean left = KeyboardInput.isClicked(KeyboardInput.LEFT);
        boolean up = KeyboardInput.isClicked(KeyboardInput.UP);
        boolean right = KeyboardInput.isClicked(KeyboardInput.RIGHT);
        boolean down = KeyboardInput.isClicked(KeyboardInput.DOWN);
        if (left && !right) onLeftPress();
        if (right && !left) onRightPress();
        if (up && !down) onUpPress();
        if (down && !up) onDownPress();
        for (View view : views) view.update(camera);
    }

    @Override
    public void render(Camera camera) {
        for (View view : views) view.render(camera);
    }

    private void onLeftPress() {
        Button left = navigation.getLeft();
        if (left != null) navigation.select(left);
    }

    private void onUpPress() {
        Button up = navigation.getUp();
        if (up != null) navigation.select(up);
    }

    private void onRightPress() {
        Button right = navigation.getRight();
        if (right != null) navigation.select(right);
    }

    private void onDownPress() {
        Button down = navigation.getDown();
        if(down !=null)navigation.select(down);
    }

}
