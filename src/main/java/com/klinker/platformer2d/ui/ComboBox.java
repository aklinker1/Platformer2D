package com.klinker.platformer2d.ui;

import com.klinker.engine2d.gui.Scene;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;

import java.util.ArrayList;

public class ComboBox<T extends ComboBox.Option> extends ViewGroup {

    private TextView label;
    private TextView selection;
    private View underline;
    private View arrow;
    private ArrayList<T> options;
    private float textSize = 25;
    private int selectedOption = 0;


    public interface Option {
        String getOptionName();
    }

    // region Construction
    public ComboBox(Scene scene, Size<Float> size) {
        this(scene, View.DEFAULT_ID, size);
    }

    public ComboBox(Scene scene, int id, Size<Float> size) {
        super(scene, id);
        setSize(size);
        options = new ArrayList<>();

        this.label = new TextView(getScene(), 43);
        this.label.setPosition(8, 0, 0)
                .setSize(size);
        this.label.setTextSize(textSize)
                .setTextColor(0xFFFFFFFF)
                .setInnerVertAlign(Alignment.CENTER);
        addView(this.label);

        this.selection = new TextView(getScene(),44);
        this.selection.setPosition(-30, 0, 0)
                .setSize(size);
        this.selection.setText("Select an Option")
                .setTextSize(textSize)
                .setTextColor(0xFFFFFFFF)
                .setInnerVertAlign(Alignment.CENTER)
                .setInnerHorAlign(Alignment.RIGHT);
        addView(this.selection);

        this.underline = new View(getScene(), 45)
                .setPosition(0, 0, 0)
                .setSize(size.width, 2f)
                .setBackground(R.textures.ui.COMBO_BOX_UNDERLINE);
        addView(this.underline);

        this.arrow = new View(getScene(), 41)
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

    public ComboBox setOptions(ArrayList<T> options, int _default) {
        this.options = options;
        this.selectedOption = _default;
        selection.setText(options.get(_default).getOptionName());
        return this;
    }

}
