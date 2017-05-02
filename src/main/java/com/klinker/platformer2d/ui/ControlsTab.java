package com.klinker.platformer2d.ui;

import com.klinker.engine2d.gui.Scene;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.platformer2d.R;

public class ControlsTab extends ViewGroup {

    public ControlsTab(Scene scene, int id) {
        super(scene, id);
        setSize(1194f, 884f);
        setVertAlignment(Alignment.TOP);
        setHorAlignment(Alignment.LEFT);

        Size<Float> size = getSize();

        TextView title = new TextView(getScene(), 30);
        title.setPosition(0, size.height, 0)
                .setSize(590f, 35f)
                .setVertAlignment(Alignment.TOP);
        title.setText(R.strings.SETTINGS_TAB_CONTROLS_TITLE)
                .setTextColor(0xFFFFFFFF)
                .setInnerHorAlign(Alignment.LEFT)
                .setInnerVertAlign(Alignment.CENTER)
                .setTextSize(35);
        addView(title);

        TextView subtitle = new TextView(getScene(), 44);
        subtitle.setPosition(0, size.height - 32, 0)
                .setSize(590f, 30f)
                .setVertAlignment(Alignment.TOP);
        subtitle.setText(R.strings.SETTINGS_TAB_CONTROLS_SUBTITLE)
                .setTextColor(0xFFFFFFFF)
                .setInnerHorAlign(View.Alignment.LEFT)
                .setInnerVertAlign(View.Alignment.CENTER)
                .setTextSize(25)
                .setAlpha(0.5f);
        addView(subtitle);

        ComboBox inputDevice = new ComboBox(getScene(), 47, new Size<Float>(size.width / 2f, 40f));
        inputDevice.setPosition(size.width / 2f, size.height - 22, 0)
                .setVertAlignment(Alignment.TOP);
        inputDevice.setLabelText(R.strings.SETTINGS_TAB_CONTROLS_INPUT_DEVICE)
                .setTextSize(30);
        addView(inputDevice);

        TextView movement = new TextView(getScene(), 51);
        movement.setPosition(0, size.height - 90, 0)
                .setSize(75f, 26f)
                .setVertAlignment(Alignment.TOP);
        movement.setText(R.strings.SETTINGS_TAB_CONTROLS_MOVEMENT)
                .setTextColor(0xFFFFFFFF)
                .setTextSize(25);
        addView(movement);

        Size<Float> moveDivSize = new Size<Float>(size.width - 133f, 4f);
        View movementDivider = new View(getScene(), 76)
                .setPosition(size.width - moveDivSize.width, size.height - 103, 0)
                .setSize(moveDivSize)
                .setVertAlignment(Alignment.TOP)
                .setAlpha(0.2f);
        movementDivider.setBackground(R.textures.ui.DIVIDER_WHITE);
        addView(movementDivider);
    }

    @Override
    public boolean hasSubNavigation() {
        return true;
    }
}
