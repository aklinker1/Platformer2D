package com.klinker.platformer2d.ui;

import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

public class ControlsTab extends ViewGroup {

    public ControlsTab(Vector2f position) {
        super("ControlsTab", position);

        TextView title = new TextView(
                R.strings.SETTINGS_TAB_CONTROLS_TITLE,
                new Size<>(590f, 35f),
                new Vector3f(0, 0, Depth.HUD_LOW), R.fonts.ROBOTO
        );
        title.setTextColor(0xFFFFFFFF);
        title.setInnerHorAlign(View.Alignment.LEFT);
        title.setInnerVertAlign(View.Alignment.CENTER);
        title.setTextSize(35);
        addView(title);

        TextView subtitle = new TextView(
                R.strings.SETTINGS_TAB_CONTROLS_SUBTITLE,
                new Size<>(590f, 30f),
                new Vector3f(0, -32, Depth.HUD_LOW), R.fonts.ROBOTO
        );
        subtitle.setTextColor(0x80FFFFFF);
        subtitle.setInnerHorAlign(View.Alignment.LEFT);
        subtitle.setInnerVertAlign(View.Alignment.CENTER);
        subtitle.setTextSize(30);
        addView(subtitle);
    }

}