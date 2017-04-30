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
        ViewGroup tabGroup = new ViewGroup(0, new Vector2f(), new Size<Float>(1194f, 77f));
        // region TabGroup: View Creation
        View background = new View(1,
                new Vector3f(0, -PROJ_SIZE.height, Depth.BACKGROUND_BACK),
                new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height)
        );
        background.setBackground(R.textures.bg.SETTINGS);
        tabGroup.addView(background);

        View tabDivider = new View(2,
                new Vector3f(554f, -120f, Depth.BACKGROUND_MIDDLE),
                new Size<Float>(1194f, 4f)
        );
        tabDivider.setBackground(R.textures.ui.DIVIDER_DARK);
        tabDivider.setHorAlignment(View.Alignment.LEFT);
        tabDivider.setVertAlignment(View.Alignment.TOP);
        tabGroup.addView(tabDivider);

        String[] tabStrings = new String[]{
                R.strings.SETTINGS_TAB_VIDEO, R.strings.SETTINGS_TAB_CONTROLS,
                R.strings.SETTINGS_TAB_AUDIO, R.strings.SETTINGS_TAB_ONLINE
        };
        TextView[] tabs = new TextView[tabStrings.length];
        Size<Float> tabSize = new Size<Float>(221f, 77f);
        float startDistance = (tabDivider.getSize().width - tabs.length * tabSize.width) / 2f + tabDivider.getPosition().globalX();
        for (int i = 0; i < tabs.length; i++) {
            TextView tab = new TextView(3,
                    tabStrings[i], tabSize,
                    new Vector3f(startDistance + tabSize.width * i, -49, Depth.BACKGROUND_FRONT)
            );
            tab.setTextColor(View.State.SELECTED, 0xFFFFFFFF);
            tab.setTextColor(View.State.DEFAULT, 0xFF2E224C);
            tab.setTextSize(35);
            tab.setHorAlignment(View.Alignment.LEFT);
            tab.setVertAlignment(View.Alignment.TOP);
            tab.setInnerHorAlign(TextView.Alignment.CENTER);
            tab.setInnerVertAlign(TextView.Alignment.CENTER);
            tab.setBackground(View.State.SELECTED, R.textures.ui.SETTINGS_TAB_UNDERSCORE);
            tabs[i] = tab;
            tabGroup.addView(tab);
        }
        navigation.select(tabs[0]);
        // endregion
        views.add(tabGroup);

        // region Creating Tab Contents
        Vector2f tabContentPos = new Vector2f(554, 0);
        ViewGroup[] tabContents = new ViewGroup[tabs.length];
        tabContents[1] =  new ControlsTab(12, tabContentPos);
        for (ViewGroup tabContent : tabContents) {
            if (tabContent != null) views.add(tabContent);
        }
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