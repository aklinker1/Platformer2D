package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.graphics.Label;
import com.klinker.engine2d.maths.Vector3f;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

import java.util.LinkedList;

public class MainMenu extends Menu {

    @Override
    protected void initializeNavigation(LinkedList<Label> labels) {
        labels.add(new Label(
                "Hello World!",
                new Vector3f(50f, 50f, Depth.TILE_FRONT),
                R.fonts.ROBOTO
        ));
    }

}
