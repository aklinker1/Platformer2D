package com.klinker.platformer2d.ui;

import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.R;

public class ComboBox extends ViewGroup {

    private TextView label;
    private View underline;
    private View arrow;
    private float textSize = 30;

    public ComboBox(int id, Vector3f position, Size<Float> size, String label) {
        super(id, position, size);

        this.label = new TextView(43, label, size, new Vector3f());
        this.label.setTextSize(textSize);
        this.label.setTextColor(0xFFFFFFFF);
        this.label.setVertAlignment(Alignment.CENTER);

        Size<Float> underlineSize = new Size<Float>(size.width, 2f);
        this.underline = new View(45,
                new Vector3f(0, underlineSize.height - size.height, 0),
                underlineSize
        );
        this.underline.setBackground(R.textures.ui.DIVIDER_DARK);


        addView(this.label);
        addView(this.underline);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.label.setTextSize(textSize);
    }
}
