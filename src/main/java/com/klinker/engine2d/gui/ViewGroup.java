package com.klinker.engine2d.gui;


import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.utils.ViewNavigation;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.LinkedList;


// TODO: 4/30/2017 Backgrounds don't show up properly
public class ViewGroup extends View {

    public LinkedList<View> views;
    private ViewNavigation subNavigation;
    private boolean hasSubNavigation = false;

    public ViewGroup(Scene scene) {
        this(scene, null);
    }

    public ViewGroup(Scene scene, Attributes attrs) {
        super(scene, attrs);
        this.views = new LinkedList<>();
        this.subNavigation = new ViewNavigation();
    }

    @Override
    protected void initAttrs(HashMap<String, String> attrs) {
        super.initAttrs(attrs);

        String sElevation = attrs.get("bg_elevation");
        if (sElevation == null) getPosition().setLocalZ(0);
        else getPosition().setLocalZ(Float.parseFloat(sElevation));
    }

    /**
     * Adds the view to the view group, and set's it's position relative to the view groups.
     * @param view The view to add.
     */
    public void addView(View view) {
        this.views.addLast(view);
        view.setRelativeTo(getAlignmentOffset());
    }

    @Override
    public void update(Camera camera) {
        super.update(camera);
        if (isVisible()) {
            for (View view : views) {
                view.update(camera);
            }
        }
    }

    @Override
    public View setAlpha(float alpha) {
        super.setAlpha(alpha);
        for (View view : views) {
            view.setAlpha(alpha);
        }
        return this;
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
