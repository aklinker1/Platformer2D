package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.sprite.SimpleSprite;

import java.util.HashMap;

import static com.klinker.engine2d.gui.View.State.*;

public class View {

    public static final Alignment DEFAULT_ALIGNMENT = Alignment.LEFT;

    protected Vector2f position;
    protected Size<Float> size;
    private Alignment alignment;
    protected State state;

    protected StateObject<Sprite> background;


    public enum Alignment {
        CENTER, LEFT, RIGHT
    }

    public enum State {
        DEFAULT, SELECTED, CLICKED, DISABLED
    }


    public View(Vector2f position, Size<Float> size) {
        this.position = position;
        this.size = size;
        this.state = DEFAULT;
        this.alignment = DEFAULT_ALIGNMENT;
    }

    public void setBackgroundTexture(String textureRes) {
        if (background == null) background = new StateObject<>();
        background.put(DEFAULT, new SimpleSprite(position, size, textureRes));
    }

    public void setBackground(StateObject<Sprite> spriteStateObject) {
        this.background = spriteStateObject;
    }

    public void render() {
        if (background != null) background.get(state).render();
    }

    public void update() {
        if (background != null) background.get(state).update();
    }

    public void setState(State state) {
        this.state = state;
    }

    public Size<Float> getSize() {
        return size;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    protected float getAlignmentOffset() {
        if (alignment == Alignment.LEFT) return 0f;
        else if (alignment == Alignment.RIGHT) return -size.width;
        else return -size.width / 2f;
    }

    public static class StateObject<T> {
        private HashMap<State, T> data;
        public StateObject() {
            data = new HashMap<State, T>(10);
        }
        public void put(State state, T t) {
            data.put(state, t);
        }
        public T get(State state) {
            return data.get(state);
        }
    }

}
