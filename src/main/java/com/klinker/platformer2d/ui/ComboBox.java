package com.klinker.platformer2d.ui;

import com.klinker.engine2d.gui.Scene;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;

public class ComboBox<T> extends ViewGroup {

    private TextView label;
    private TextView selection;
    private View underline;
    private View arrow;
    private ComboOptions<T> options;
    private float textSize = 25;
    private int selectedOption = 0;


    // region Construction
    public ComboBox(Scene scene, Size<Float> size) {
        super(scene);
        setSize(size);
        options = null;

        this.label = new TextView(getScene());
        this.label.setPosition(8, 0, 0)
                .setSize(size);
        this.label.setTextSize(textSize)
                .setTextColor(0xFFFFFFFF)
                .setInnerVertAlign(Alignment.CENTER);
        addView(this.label);

        this.selection = new TextView(getScene());
        this.selection.setPosition(-30, 0, 0)
                .setSize(size);
        this.selection.setText("Select an Option")
                .setTextSize(textSize)
                .setTextColor(0xFFFFFFFF)
                .setInnerVertAlign(Alignment.CENTER)
                .setInnerHorAlign(Alignment.RIGHT);
        addView(this.selection);

        this.underline = new View(getScene())
                .setPosition(0, 0, 0)
                .setSize(size.width, 2f)
                .setBackground(R.textures.ui.COMBO_BOX_UNDERLINE);
        addView(this.underline);

        this.arrow = new View(getScene())
                .setPosition(size.width - 8, size.height / 2, 0)
                .setSize(14f, 14f)
                .setBackground(R.textures.ui.COMBO_BOX_ARROW)
                .setHorAlignment(Alignment.RIGHT)
                .setVertAlignment(Alignment.CENTER);
        addView(this.arrow);
        Log.d("Label: " + this.label.getSize());
        Log.d("Selection: " + this.selection.getSize());
        Log.d("Arrow: " + this.arrow.getSize());
    }
    // endregion

    @Override
    public void select() {
        super.select();
        label.setState(State.SELECTED);
        selection.setState(State.SELECTED);
        underline.setState(State.SELECTED);
        arrow.setState(State.SELECTED);
    }

    @Override
    public void deselect() {
        super.deselect();
        label.setState(State.DEFAULT);
        selection.setState(State.DEFAULT);
        underline.setState(State.DEFAULT);
        arrow.setState(State.DEFAULT);
    }


    @Override
    public void updateComponents() {
        super.updateComponents();
        if (label != null) label.updateComponents();
        if (selection != null) selection.updateComponents();
        if (underline != null) underline.updateComponents();
        if (arrow != null) arrow.updateComponents();
    }

    public ComboBox setTextSize(float textSize) {
        this.textSize = textSize;
        this.label.setTextSize(textSize);
        this.selection.setTextSize(textSize);
        updateComponents();
        return this;
    }

    public ComboBox setLabelText(String text) {
        this.label.setText(text);
        updateComponents();
        return this;
    }

    public ComboBox setOptions(ComboOptions<T> options, int _default) {
        this.options = options;
        this.selectedOption = _default;
        selection.setText(options.getLabel(_default));
        return this;
    }

    public static class ComboOptions<T> {

        private String[] labels;
        private T[] options;

        public ComboOptions(String[] labels, T[] options) {
            this.labels = labels;
            this.options = options;
        }

        public T getOption(int index) {
            return options[index];
        }

        public String getLabel(int index) {
            return labels[index];
        }

    }

}
