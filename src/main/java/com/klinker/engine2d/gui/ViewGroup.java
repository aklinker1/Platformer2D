package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;

import java.util.LinkedList;

public class ViewGroup implements Drawable {

    private Vector3f position;
    private String description;
    public LinkedList<View> views;
    private boolean isVisible;

    public ViewGroup(String description, Vector2f position) {
        this.position = new Vector3f(position.x, position.y, 0);
        this.description = description;
        this.views = new LinkedList<>();
        this.isVisible = true;
    }

    /**
     * Adds the view to the view group, and set's it's position relative to the view groups.
     * @param view The view to add.
     */
    public void addView(View view) {
        this.views.addLast(view);
        view.setRelativeTo(this.position);
    }

    @Override
    public void render(Camera camera) {
        // shouldn't be called. Views should be added to the layers below in update(Camera)
        //for (View view : views) view.render(camera);
    }

    @Override
    public void update(Camera camera) {
        if (isVisible) {
            for (View view : views) view.update(camera);
        }
    }

    @Override
    public String description() {
        return "ViewGroup: " + description;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
