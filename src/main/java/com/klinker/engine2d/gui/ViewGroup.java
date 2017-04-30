package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.utils.ViewNavigation;

import java.util.LinkedList;

// TODO: 4/30/2017 Backgrounds don't show up
public class ViewGroup extends View {

    public LinkedList<View> views;
    private boolean isVisible;
    private ViewNavigation subNavigation;
    private boolean hasSubNavigation = false;

    public ViewGroup(int id, Vector2f position, Size<Float> size) {
        this(id, new Vector3f(position.x, position.y, 0), size);
    }

    public ViewGroup(int id, Vector3f position, Size<Float> size) {
        super(id, position, size);
        this.views = new LinkedList<>();
        this.isVisible = true;
        this.subNavigation = new ViewNavigation();
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
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        for (View view : views) {
            view.setAlpha(alpha);
        }
    }

    public ViewNavigation getSubNavigation() {
        return subNavigation;
    }

    public boolean hasSubNavigation() {
        return hasSubNavigation;
    }

    public void setSubNavigation(ViewNavigation navigation) {
        this.subNavigation = navigation;
    }

    public void clearSubNavigation() {
        this.subNavigation = new ViewNavigation();
        this.hasSubNavigation = false;
    }
}
