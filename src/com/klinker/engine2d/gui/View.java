package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.inputs.KeyboardInput;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.utils.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import static com.klinker.engine2d.gui.View.State.*;

public class View {

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
        this.position = position;
        this.size = size;
        this.state = DEFAULT;
        this.hAlignment = DEFAULT_H_ALIGNMENT;
        this.vAlignment = DEFAULT_V_ALIGNMENT;
    }

    public void setBackgroundTexture(String textureRes) {
        if (background == null) background = new StateObject<>();
        background.put(DEFAULT, new SimpleSprite(getAlignedPosition(), size, textureRes));
    }

    public void setBackground(StateObject<Sprite> spriteStateObject) {
        this.background = spriteStateObject;
    }

    public void render() {
        if (background != null) background.get(state).render();
    }

    public void update() {
        if (background != null) background.get(state).update();
        if (state == State.SELECTED && KeyboardInput.isClicked(KeyboardInput.JUMP) && onClickListener != null) {
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
    }

    public void setVertAlignment(Alignment alignment) {
        this.vAlignment = alignment;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    protected float getHorAlignmentOffset() {
        if (hAlignment == Alignment.CENTER) return -size.width / 2f;
        else if (hAlignment == Alignment.RIGHT) return -size.width;
        else return 0f;
    }

    protected float getVerAlignmentOffset() {
        if (vAlignment == Alignment.CENTER) return -size.height / 2f;
        else if (vAlignment == Alignment.TOP) return -size.height;
        else return 0f;
    }

    protected Vector3f getAlignedPosition() {
        return new Vector3f(
                position.x + getHorAlignmentOffset(),
                position.y + getVerAlignmentOffset(),
                position.z
        );
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

}
