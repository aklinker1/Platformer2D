package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.gui.Scene;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.inputs.InputManager;
import com.klinker.engine2d.utils.ViewNavigation;
import com.klinker.platformer2d.Platformer2D;

import java.util.LinkedList;

public abstract class Menu extends Scene {

    private LinkedList<View> viewGroups;

    private ViewNavigation navigation;


    public Menu(Engine engine, String layoutRes) {
        super(engine, layoutRes);
    }

    @Override
    public void init() {
        super.init();
        viewGroups = new LinkedList<>();
        this.navigation = new ViewNavigation();
        //initializeViews(viewGroups, navigation);
        super.init();

    }

    protected abstract void initializeViews(LinkedList<View> views, ViewNavigation navigation);

    @Override
    public void update(Camera camera) {
        boolean left = Platformer2D.getInputManager().isClicked(InputManager.BUTTON_LEFT);
        boolean up = Platformer2D.getInputManager().isClicked(InputManager.BUTTON_UP);
        boolean right = Platformer2D.getInputManager().isClicked(InputManager.BUTTON_RIGHT);
        boolean down = Platformer2D.getInputManager().isClicked(InputManager.BUTTON_DOWN);
        if (left && !right) onLeftPress();
        if (right && !left) onRightPress();
        if (up && !down) onUpPress();
        if (down && !up) onDownPress();
        for (View view : viewGroups) view.update(camera);
    }

    @Override
    public void render(Camera camera) {
        for (View view : viewGroups) view.render(camera);
    }

    private void onLeftPress() {
        navigation.left();
    }

    private void onUpPress() {
        navigation.up();
    }

    private void onRightPress() {
        navigation.right();
    }

    private void onDownPress() {
        navigation.down();
    }

}
