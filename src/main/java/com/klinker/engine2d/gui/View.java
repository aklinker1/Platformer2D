package com.klinker.engine2d.gui;


import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.inputs.InputManager;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.Platformer2D;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class View implements Drawable {

    // region Defaults
    public static final int DEFAULT_ID = -1;
    private final Vector3f DEFAULT_ALIGNMENT_OFFSET = new Vector3f();
    private final Size<Float> DEFAULT_SIZE = new Size<>(0f, 0f);
    public static final Alignment DEFAULT_H_ALIGNMENT = Alignment.LEFT;
    public static final Alignment DEFAULT_V_ALIGNMENT = Alignment.BOTTOM;
    public static final State DEFAULT_STATE = State.DEFAULT;
    private final Vector3f DEFAULT_POSITION = new Vector3f();
    public static final boolean DEFAULT_VISIBILITY = true;
    private final StateObject<Sprite> DEFAULT_BACKGROUND = new StateObject<>(null);
    public static final float DEFAULT_ALPHA = 1.0f;
    private final OnClickListener DEFAULT_CLICK_LISTENER = null;
    private final OnSelectedListener DEFAULT_SELECT_LISTENER = null;
    // endregion


    // region Member Variables
    private Scene scene = null;
    private int id = DEFAULT_ID;
    private Vector3f position = DEFAULT_POSITION;
    private Vector3f alignmentOffset = DEFAULT_ALIGNMENT_OFFSET;
    private Size<Float> size = DEFAULT_SIZE;
    private Alignment hAlignment = DEFAULT_H_ALIGNMENT;
    private Alignment vAlignment = DEFAULT_V_ALIGNMENT;
    private State state = DEFAULT_STATE;
    private boolean isVisible = DEFAULT_VISIBILITY;
    private StateObject<Sprite> background = DEFAULT_BACKGROUND;
    private float alpha = DEFAULT_ALPHA;
    private OnClickListener onClickListener = DEFAULT_CLICK_LISTENER;
    private OnSelectedListener onSelectedListener = DEFAULT_SELECT_LISTENER;
    // endregion


    // region Interfaces
    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnSelectedListener {
        void onSelect(View view);
    }
    // endregion


    // region Enums
    public enum Alignment {
        CENTER, LEFT, RIGHT, TOP, BOTTOM
    }

    public enum State {
        DEFAULT, SELECTED, CLICKED, DISABLED, CHECKED
    }
    // endregion


    // region Construction
    public View(Scene scene, Attributes attrs) {
        this.scene = scene;
        this.id = id;
        this.position = new Vector3f();
        this.alignmentOffset = new Vector3f();
        this.alignmentOffset.setRelative(this.position);

        if (attrs != null) {
            HashMap<String, String> properties = new HashMap<>();
            for (int i = 0; i < attrs.getLength(); i++)
                properties.put(attrs.getQName(i), attrs.getValue(i));
            initAttrs(properties);
        }
    }

    public View(Scene scene) {
        this(scene, null);
    }

    protected void initAttrs(HashMap<String, String> attrs) {
        String sId = attrs.get("id");
        if (sId == null) id = DEFAULT_ID;
        else id = Integer.parseInt(sId);

        position.setLocalX(Float.parseFloat(attrs.get("x_pos")));
        position.setLocalY(Float.parseFloat(attrs.get("y_pos")));

        String sElevation = attrs.get("elevation");
        if (sElevation == null) position.setLocalZ(0);
        else position.setLocalZ(Float.parseFloat(sElevation));

        String sVisible = attrs.get("visible");
        if (sVisible == null) isVisible = DEFAULT_VISIBILITY;
        else isVisible = Boolean.parseBoolean(sVisible);

        String sAlpha = attrs.get("alpha");
        if (sAlpha == null) alpha = DEFAULT_ALPHA;
        else alpha = Float.parseFloat(sAlpha);


        String sWidth = attrs.get("width");
        if (sWidth.equals("match")) {
            size.width = 100f;
        } else if (sWidth.equals("wrap")) {
            size.width = 100f;
        } else {
            size.width = Float.parseFloat(sWidth);
        }
        String sHeight = attrs.get("height");
        if (sHeight.equals("match")) {
            size.height = 100f;
        } else if (sHeight.equals("wrap")) {
            size.height = 100f;
        } else {
            size.height = Float.parseFloat(sHeight);
        }

        String hAlign = attrs.get("x_align");
        if (hAlign == null) {
            hAlignment = DEFAULT_H_ALIGNMENT;
        } else {
            if (hAlign.equals("left")) hAlignment = Alignment.LEFT;
            else if (hAlign.equals("center")) hAlignment = Alignment.CENTER;
            else hAlignment = Alignment.RIGHT;
            updateAlignmentHorOffset();
        }

        String vAlign = attrs.get("y_align");
        if (vAlign == null) {
            vAlignment = DEFAULT_V_ALIGNMENT;
        } else {
            if (vAlign.equals("top")) vAlignment = Alignment.TOP;
            else if (vAlign.equals("center")) vAlignment = Alignment.CENTER;
            else vAlignment = Alignment.BOTTOM;
            updateAlignmentVertOffset();
        }
    }
    // endregion


    // region Helper Functions
    private void updateAlignmentHorOffset() {
        float offset;
        if (hAlignment == Alignment.RIGHT) offset = -size.width;
        else if (hAlignment == Alignment.CENTER) offset = -size.width / 2f;
        else offset = 0;
        this.alignmentOffset.setLocalX(offset);
    }

    private void updateAlignmentVertOffset() {
        float offset;
        if (vAlignment == Alignment.TOP) offset = -size.height;
        else if (vAlignment == Alignment.CENTER) offset = -size.height / 2f;
        else offset = 0;
        this.alignmentOffset.setLocalY(offset);
    }
    // endregion


    // region Setters
    public View setPosition(float x, float y, float z) {
        this.position.setLocalX(x);
        this.position.setLocalY(y);
        this.position.setLocalZ(z);
        updateComponents();
        return this;
    }

    public View setPosition(Vector3f position) {
        return setPosition(
                position.localX(),
                position.localY(),
                position.localZ()
        );
    }

    public View setSize(Size<Float> size) {
        return setSize(size.width, size.height);
    }

    public View setSize(float width, float height) {
        this.size.width = width;
        this.size.height = height;
        updateComponents();
        return this;
    }

    public View setBackground(String textureRes) {
        return setBackground(State.DEFAULT, textureRes);
    }

    public View setBackground(State state, String textureRes) {
        return setBackground(state, new SimpleSprite(alignmentOffset, size, textureRes));
    }

    public View setBackground(State state, Sprite sprite) {
        this.background.put(state, sprite);
        this.background.get(state).setAlpha(alpha);
        return this;
    }

    public View setBackground(StateObject<Sprite> background) {
        this.background.put(State.DEFAULT, background.get(State.DEFAULT));
        this.background.put(State.SELECTED, background.get(State.SELECTED));
        this.background.put(State.CLICKED, background.get(State.CLICKED));
        this.background.put(State.CHECKED, background.get(State.CHECKED));
        this.background.put(State.DISABLED, background.get(State.DISABLED));
        return this;
    }

    public View setHorAlignment(Alignment alignment) {
        this.hAlignment = alignment;
        updateAlignmentHorOffset();
        updateComponents();
        return this;
    }

    public View setVertAlignment(Alignment alignment) {
        this.vAlignment = alignment;
        updateAlignmentVertOffset();
        updateComponents();
        return this;
    }

    public View setAlpha(float alpha) {
        this.alpha = alpha;
        for (Sprite bg : background) {
            bg.setAlpha(alpha);
        }
        return this;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.onSelectedListener = listener;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public void setRelativeTo(Vector3f location) {
        this.position.setRelative(location);
    }
    // endregion


    // region Getters
    public Size<Float> getSize() {
        return size;
    }

    public Vector3f getAlignmentOffset() {
        return alignmentOffset;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public State getState() {
        return state;
    }

    public float getAlpha() {
        return alpha;
    }

    public int getId() {
        return id;
    }

    public Scene getScene() {
        return scene;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Sprite getBackground() {
        return background.get(state);
    }
    // endregion


    // region Other Mutators
    public void select() {
        state = State.SELECTED;
        if (onSelectedListener != null) onSelectedListener.onSelect(this);
    }

    public void deselect() {
        state = State.DEFAULT;
    }

    public void updateComponents() {

    }
    // endregion


    // region Drawable Overrides
    @Override
    public void render(Camera camera) {
        if (isVisible) {
            Sprite bg = background.get(state);
            if (bg != null) bg.render(camera);
        }
    }

    @Override
    public void update(Camera camera) {
        if (isVisible) {
            //camera.addToLayers(this, position.globalZ());
            if (background.get(state) != null) background.get(state).update(camera);
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

    @Override
    public String description() {
        return this.getClass().getSimpleName() + " " + id;
    }
    // endregion


    // region State Object
    public static class StateObject<T> implements Iterable<T> {

        private HashMap<State, T> data;
        private T _default;

        public StateObject() {
            this(null);
        }

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

        public static void setBackground(Attributes attributes, View view) {
            Vector3f alignOffset = view.getAlignmentOffset();
            Size<Float> size = view.getSize();
            StateObject<Sprite> sprites = new StateObject<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                State state = State.DEFAULT;
                switch (name) {
                    default:
                    case "default":
                        state = State.DEFAULT;
                        break;
                    case "selected":
                        state = State.SELECTED;
                        break;
                    case "clicked":
                        state = State.CLICKED;
                        break;
                    case "checked":
                        state = State.CHECKED;
                        break;
                    case "disabled":
                        state = State.DISABLED;
                        break;
                }
                Sprite sprite = Sprite.from(value, state, alignOffset, size);
                view.setBackground(state, sprite);
            }
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
    // endregion

}
