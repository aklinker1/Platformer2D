package com.klinker.platformer2d.ui;

import com.klinker.engine2d.gui.Scene;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.platformer2d.R;

import java.util.ArrayList;

public class ComboBox<T> extends ViewGroup {

    private TextView label;
    private TextView selection;
    private View underline;
    private View arrow;
    private ArrayList<T> options;
    private float textSize = 25;
    private int selectedOption = 0;

    // region Construction
    public ComboBox(Scene scene) {
        this(scene, View.DEFAULT_ID);
    }

    public ComboBox(Scene scene, int id) {
        super(scene, id);
        Size<Float> size = getSize();
        options = new ArrayList<>();

        this.label = new TextView(getScene(), 43);
        this.label.setPosition(8, 0, 0)
                .setSize(size);
        this.label.setTextSize(textSize)
                .setTextColor(0xFFFFFFFF)
                .setInnerVertAlign(Alignment.CENTER);
        addView(this.label);

        this.selection = new TextView(getScene(),44);
        this.selection.setPosition(0, 0, 0)
                .setSize(size)
                .setBackground(R.textures.tiles.collectables.X0E_00);
        this.selection.setText("Select an Option")
                .setTextSize(textSize)
                .setTextColor(0xFFFFFFFF)
                .setInnerVertAlign(Alignment.CENTER)
                .setInnerHorAlign(Alignment.LEFT);
        addView(this.selection);

        Size<Float> underlineSize = new Size<Float>(size.width, 2f);
        this.underline = new View(getScene(), 45)
                .setPosition(0, 0, 0)
                .setSize(underlineSize)
                .setBackground(R.textures.ui.COMBO_BOX_UNDERLINE);
        addView(this.underline);

        this.arrow = new View(getScene(), 41)
                .setPosition(size.width - 8, size.height / 2, 0)
                .setSize(14f, 14f)
                .setBackground(R.textures.ui.COMBO_BOX_ARROW)
                .setHorAlignment(Alignment.RIGHT)
                .setVertAlignment(Alignment.CENTER);
        addView(this.arrow);
    }
    // endregion

    public ComboBox setTextSize(float textSize) {
        this.textSize = textSize;
        this.label.setTextSize(textSize);
        this.selection.setTextSize(textSize);
        return this;
    }

    public ComboBox setLabelText(String text) {
        this.label.setText(text);
        return this;
    }

}
