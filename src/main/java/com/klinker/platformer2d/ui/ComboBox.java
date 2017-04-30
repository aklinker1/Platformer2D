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

    public ComboBox(int id, Vector3f position, Size<Float> size, String label) {
        super(id, position, size);

        this.label = new TextView(43, label, size, new Vector3f());
        this.label.setTextSize(20);
        this.label.setTextColor(0xFFFFFFFF);
        this.label.setInnerVertAlign(Alignment.CENTER);
        this.label.setInnerHorAlign(Alignment.LEFT);

        Size<Float> underlineSize = new Size<Float>(size.width, 2f);
        this.underline = new View(45,
                new Vector3f(0, underlineSize.height - size.height, 0),
                underlineSize
        );
        this.underline.setBackground(R.textures.ui.DIVIDER_DARK);
    }

}
