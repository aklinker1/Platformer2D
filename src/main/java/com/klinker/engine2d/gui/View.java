package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.inputs.InputManager;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.Platformer2D;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import static com.klinker.engine2d.gui.View.State.DEFAULT;

public class View implements Drawable {

    public static final Alignment DEFAULT_H_ALIGNMENT = Alignment.LEFT;
    public static final Alignment DEFAULT_V_ALIGNMENT = Alignment.LEFT;
    public static final State DEFAULT_STATE = State.DEFAULT;

    protected Vector3f position;
    protected Size<Float> size;
    private Alignment hAlignment;
    private Alignment vAlignment;
    protected State state;
    private OnClickListener onClickListener;

    protected StateObject<Sprite> background;


    public interface OnClickListener {
        void onClick(View view);
    }


    public enum Alignment {
        CENTER, LEFT, RIGHT, TOP, BOTTOM
    }

    public enum State {
        DEFAULT, SELECTED, CLICKED, DISABLED
    }


    public View(Vector3f position, Size<Float> size) {
        this.position = new Vector3f();
        this.position.setRelative(position);
        this.size = size;
        this.state = DEFAULT;
        this.hAlignment = DEFAULT_H_ALIGNMENT;
        this.vAlignment = DEFAULT_V_ALIGNMENT;
        updatePosition();
    }

    private void updatePosition() {
        float hor;
        if (hAlignment == Alignment.RIGHT) hor = -size.width;
        else if (hAlignment == Alignment.CENTER) hor = -size.width / 2f;
        else hor = 0;

        float vert;
        if (vAlignment == Alignment.TOP) vert = -size.height;
        else if (vAlignment == Alignment.CENTER) vert = -size.height / 2f;
        else vert = 0;

        this.position.setLocalX(hor);
        this.position.setLocalY(vert);
    }

    public void setBackgroundTexture(String textureRes) {
        if (background == null) background = new StateObject<>();
        background.put(DEFAULT, new SimpleSprite(this.position, size, textureRes));
    }

    public void setBackground(StateObject<Sprite> spriteStateObject) {
        this.background = spriteStateObject;
    }

    @Override
    public void render(Camera camera) {
        if (background != null) background.get(state).render(camera);
    }

    @Override
    public void update(Camera camera) {
        camera.addToLayers(this, position.globalZ());
        if (background != null) background.get(state).update(camera);
        if (state == State.SELECTED && Platformer2D.getInputManager().isClicked(InputManager.BUTTON_SELECT) && onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public Size<Float> getSize() {
        return size;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setHorAlignment(Alignment alignment) {
        this.hAlignment = alignment;
        updatePosition();
    }

    public void setVertAlignment(Alignment alignment) {
        this.vAlignment = alignment;
        updatePosition();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public static class StateObject<T> implements Iterable<T> {
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
        @Override
        public String toString() {
            return data.toString();
        }
        @Override
        public Iterator<T> iterator() {
            return new StateIterator();
        }
        private class StateIterator implements Iterator<T> {

            private LinkedList<T> queue;

            public StateIterator() {
                queue = new LinkedList<T>();
                queue.addAll(data.values());
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                return queue.remove();
            }
        }
    }

    @Override
    public String description() {
        return "View";
    }
}
