package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.ViewNavigation;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.ui.ControlsTab;

import java.util.LinkedList;

public class SettingsMenu extends Menu {

    public static Size<Float> PROJ_SIZE = new Size<Float>(
            1080f * Platformer2D.tileCounts.ratioXoY(),
            1080f
    );

    public SettingsMenu(Engine engine) {
        super(engine);
    }

    @Override
    public Camera initializeCamera() {
        return new Camera(PROJ_SIZE, new Vector2f(0, PROJ_SIZE.height));
    }

    @Override
    public Shader[] loadAllShaders() {
        return new Shader[]{ SimpleSprite.SHADER, TextView.FONT_SHADER };
    }

    @Override
    protected void initializeViews(LinkedList<View> views, ViewNavigation navigation) {
        String[] tabStrings = new String[]{
                R.strings.SETTINGS_TAB_VIDEO, R.strings.SETTINGS_TAB_CONTROLS,
                R.strings.SETTINGS_TAB_AUDIO, R.strings.SETTINGS_TAB_ONLINE
        };

        // region Creating Tab Contents
        Vector3f tabContentPos = new Vector3f(554, -165, Depth.PLAYER);
        ViewGroup[] tabContents = new ViewGroup[tabStrings.length];
        tabContents[1] =  new ControlsTab(this, 12);
        tabContents[1].setPosition(tabContentPos);
        for (ViewGroup tabContent : tabContents) {
            if (tabContent != null) views.add(tabContent);
        }
        // endregion

        // region TabGroup: View Creation
        ViewGroup tabGroup = new ViewGroup(this, 0);
        tabGroup.setPosition(0, 0, 0)
                .setSize(1194f, 77f);

        View background = new View(this, 1)
                .setPosition(0, -PROJ_SIZE.height, Depth.BACKGROUND_BACK)
                .setSize(PROJ_SIZE.width, PROJ_SIZE.height)
                .setBackground(R.textures.bg.SETTINGS);
        tabGroup.addView(background);

        View tabDivider = new View(this, 2)
                .setPosition(554f, -120f, Depth.BACKGROUND_MIDDLE)
                .setSize(1194f, 4f)
                .setBackground(R.textures.ui.DIVIDER_DARK)
                .setHorAlignment(View.Alignment.LEFT)
                .setVertAlignment(View.Alignment.TOP);
        tabGroup.addView(tabDivider);

        TextView[] tabs = new TextView[tabStrings.length];
        Size<Float> tabSize = new Size<Float>(221f, 77f);
        float startDistance = (tabDivider.getSize().width - tabs.length * tabSize.width) / 2f + tabDivider.getAlignmentOffset().globalX();
        for (int i = 0; i < tabs.length; i++) {
            TextView tab = new TextView(this, i);
            tab.setPosition(startDistance + tabSize.width * i, -49, Depth.BACKGROUND_FRONT)
                    .setSize(tabSize)
                    .setHorAlignment(View.Alignment.LEFT)
                    .setVertAlignment(View.Alignment.TOP)
                    .setBackground(View.State.SELECTED, R.textures.ui.SETTINGS_TAB_UNDERSCORE);
            tab.setText(tabStrings[i])
                    .setTextColor(View.State.SELECTED, 0xFFFFFFFF)
                    .setTextColor(View.State.DEFAULT, 0xFF2E224C)
                    .setTextSize(35)
                    .setInnerHorAlign(TextView.Alignment.CENTER)
                    .setInnerVertAlign(TextView.Alignment.CENTER);
            tab.setOnSelectedListener((View view) -> {
                for (int j = 0; j < tabs.length; j++) {
                    if (tabContents[j] != null) tabContents[j].setVisible(j == view.getId());
                }
            });
            tabs[i] = tab;
            tabGroup.addView(tab);
        }
        // TODO: 4/30/2017 Change this back to 0 when testing is done
        navigation.select(tabs[1]);
        views.add(tabGroup);
        // endregion

        // region Setting Navigation
        for (int i = 0; i < tabs.length; i++) {
            if (i == 0) {
                navigation.insertNode(tabs[i], null/*todo */, null, tabs[i + 1], null);
            } else if (i == tabs.length - 1) {
                navigation.insertNode(tabs[i], tabs[i - 1], null, null/*todo Scrollbar*/, null);
            } else {
                navigation.insertNode(tabs[i], tabs[i - 1], null, tabs[i + 1], null);
            }
        }
        // endregion
    }

    @Override
    public String description() {
        return "SettingsMenu";
    }

}