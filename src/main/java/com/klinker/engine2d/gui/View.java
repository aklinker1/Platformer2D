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

    private int id = -1;
    protected Vector3f position;
    private Vector3f alignment;
    protected Size<Float> size;
    private Alignment hAlignment;
    private Alignment vAlignment;
    private State state;
    private OnClickListener onClickListener;
    private OnSelectedListener onSelectedListener;
    private boolean isVisible;
    protected StateObject<Sprite> background;
    private float alpha = 1f;


    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnSelectedListener {
        void onSelect(View view);
    }


    public enum Alignment {
        CENTER, LEFT, RIGHT, TOP, BOTTOM
    }

    public enum State {
        DEFAULT, SELECTED, CLICKED, DISABLED
    }


    public View(int id, Vector3f position, Size<Float> size) {
        this.id = id;
        this.alignment = position;
        this.position = new Vector3f();
        this.position.setRelative(alignment);
        this.size = size;
        this.state = DEFAULT;
        this.hAlignment = DEFAULT_H_ALIGNMENT;
        this.vAlignment = DEFAULT_V_ALIGNMENT;
        this.isVisible = true;
        this.background = new StateObject<>(null);
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

    public void setBackground(String textureRes) {
        if (background == null) background = new StateObject<>(null);
        background.put(DEFAULT, new SimpleSprite(position, size, textureRes));
    }

    public void setBackground(State state, String textureRes) {
        this.background.put(state, new SimpleSprite(position, size, textureRes));
    }

    @Override
    public void render(Camera camera) {
        Sprite bg = background.get(state);
        if (bg != null) bg.render(camera);
    }

    @Override
    public void update(Camera camera) {
        if (isVisible) {
            camera.addToLayers(this, position.globalZ());
            if (state == State.SELECTED && onClickListener != null) {
                if (Platformer2D.getInputManager().isClicked(InputManager.BUTTON_SELECT)) {
                    onClickListener.onClick(this);
                } /*else {
                Mouse mouse = Platformer2D.getInputManager().getMouse();
                if (mouse.isClicked() && mouse.inRegion()) {
                    onClickListener.onClick(this);
                }*/
            }
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
        private T _default;

        public StateObject(T _default) {
            this.data = new HashMap<State, T>(10);
            this._default = _default;
        }

        public void put(State state, T t) {
            if (state == State.DEFAULT) _default = t;
            else data.put(state, t);
        }

        public T get(State state) {
            return data.getOrDefault(state, _default);
        }

        @Override
        public String toString() {
            return data.toString();
        }

        @Override
        public Iterator<T> iterator() {
            return new StateIterator();
        }

        public void setDefault(T _default) {
            this._default = _default;
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
        return this.getClass().getSimpleName() + " " + id;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public void setRelativeTo(Vector3f location) {
        this.alignment.setRelative(location);
    }

    public State getState() {
        return state;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        for (Sprite bg : background) {
            bg.setAlpha(alpha);
        }
    }

    public int getId() {
        return id;
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.onSelectedListener = listener;
    }

    public void selected() {
        if (onSelectedListener != null) onSelectedListener.onSelect(this);
    }

}
