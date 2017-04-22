package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.math.Vector3f;

import java.util.LinkedList;

public class ViewGroup implements Drawable {

    private Vector3f position;
    private String description;
    public LinkedList<View> views;

    public ViewGroup(String description, Vector3f position) {
        this.position = position;
        this.description = description;
        this.views = new LinkedList<>();
    }

    public void addView(View view) {
        this.views.addLast(view);
    }

    @Override
    public void render(Camera camera) {
        // shouldn't be called. Views should be added to the layers below in update(Camera)
        //for (View view : views) view.render(camera);
    }

    @Override
    public void update(Camera camera) {
        for (View view : views) view.update(camera);
    }

    @Override
    public String description() {
        return "ViewGroup: " + description;
    }
}
