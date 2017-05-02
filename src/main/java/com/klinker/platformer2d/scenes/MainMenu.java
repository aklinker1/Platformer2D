package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.gui.Button;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;
import com.klinker.engine2d.utils.ViewNavigation;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

import java.util.LinkedList;

public class MainMenu extends Menu {

    public MainMenu(Engine engine) {
        super(engine);
    }

    @Override
    public Shader[] loadAllShaders() {
        return new Shader[]{ SimpleSprite.SHADER, TextView.FONT_SHADER };
    }

    @Override
    protected void initializeViews(LinkedList<View> views, ViewNavigation navigation) {
        // region Root: View Creation
        ViewGroup root = new ViewGroup(this, 0);
        root.setPosition(0, 0, 0);
        root.setSize(new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height)); {
            View background = new View(this, 1)
                    .setPosition(-PROJ_SIZE.width / 2f, -PROJ_SIZE.height / 2f, Depth.BACKGROUND_BACK)
                    .setSize(new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height))
                    .setBackground(R.textures.bg.MAIN_MENU);
            root.addView(background);

            TextView version = new TextView(this, 2);
            version.setPosition(PROJ_SIZE.width / 2f - 3f, -PROJ_SIZE.height / 2f + 2f, Depth.HUD)
                    .setSize(30, 6)
                    .setHorAlignment(TextView.Alignment.RIGHT)
                    .setVertAlignment(TextView.Alignment.BOTTOM);
            version.setText(R.strings.VERSION)
                    .setTextSize(3f)
                    .setTextColor(0x6b000000)
                    .setInnerHorAlign(TextView.Alignment.RIGHT)
                    .setInnerVertAlign(TextView.Alignment.BOTTOM);
            root.addView(version);

            Button storyMode = new Button(this, 3);
            storyMode.setPosition(25f, 25f, Depth.HUD)
                    .setSize(35f, 8f);
            storyMode.setText(R.strings.STORY_MODE)
                    .setTextSize(4.5f);
            storyMode.setOnClickListener((View view) -> {
                transitionScenes(new Level(getEngine(), R.levels.W02_LXX));
            });
            root.addView(storyMode);

            Button muliplayer = new Button(this, 4);
            muliplayer.setPosition(25, 9, Depth.HUD)
                    .setSize(35f, 8f);
            muliplayer.setText(R.strings.MULTIPLAYER)
                    .setTextSize(4.5f);
            muliplayer.setOnClickListener((View view) -> {
                Log.d("Clicked multiplayer!");
            });
            root.addView(muliplayer);

            Button settings = new Button(this, 5);
            settings.setPosition(25, -7, Depth.HUD)
                    .setSize(35f, 8f);
            settings.setText(R.strings.SETTINGS)
                    .setTextSize(4.5f);
            settings.setOnClickListener((View view) -> {
                transitionScenes(new SettingsMenu(getEngine()));
            });
            root.addView(settings);

            navigation.insertNode(storyMode, null, settings, null, muliplayer);
            navigation.insertNode(muliplayer, null, storyMode, null, settings);
            navigation.insertNode(settings, null, muliplayer, null, storyMode);
            navigation.select(storyMode);

            // TODO: 3/26/2017 Add grid?
            if (true) {
                View vertDivider = new View(this, 6)
                        .setPosition(0, 0, Depth.BACKGROUND_FRONT)
                        .setSize(0.2f, PROJ_SIZE.height)
                        .setHorAlignment(View.Alignment.CENTER)
                        .setVertAlignment(View.Alignment.CENTER)
                        .setBackground(R.textures.bg.W03_00);
                root.addView(vertDivider);

                View horDivider = new View(this, 7)
                        .setPosition(0, 0, Depth.BACKGROUND_FRONT)
                        .setSize(PROJ_SIZE.width, 0.2f)
                        .setHorAlignment(View.Alignment.CENTER)
                        .setVertAlignment(View.Alignment.CENTER)
                        .setBackground(R.textures.bg.W03_00);
                root.addView(horDivider);
            }
        }
        views.add(root);
        // endregion
    }

    @Override
    public String description() {
        return "MainMenu";
    }
}