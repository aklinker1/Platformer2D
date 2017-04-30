package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.gui.Button;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.gui.ViewGroup;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
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
        ViewGroup root = new ViewGroup(0, new Vector2f(), new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height)); {
            // region Root: View Creation
            View background = new View(1,
                    new Vector3f(-PROJ_SIZE.width / 2f, -PROJ_SIZE.height / 2f, Depth.BACKGROUND_BACK),
                    new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height)
            );
            background.setBackground(R.textures.bg.MAIN_MENU);
            root.addView(background);

            TextView version = new TextView(2,
                    R.strings.VERSION,
                    new Size<Float>(30f, 6f),
                    new Vector3f(PROJ_SIZE.width / 2f - 3f, -PROJ_SIZE.height / 2f + 2f, Depth.HUD)
            );
            version.setTextSize(3f);
            version.setTextColor(0x6b000000);
            version.setHorAlignment(TextView.Alignment.RIGHT);
            version.setVertAlignment(TextView.Alignment.BOTTOM);
            version.setInnerHorAlign(TextView.Alignment.RIGHT);
            version.setInnerVertAlign(TextView.Alignment.BOTTOM);
            root.addView(version);

            Button storyMode = new Button(3,
                    R.strings.STORY_MODE,
                    new Size<Float>(35f, 8f),
                    new Vector3f(25f, 25f, Depth.HUD)
            );
            storyMode.setTextSize(4.5f);
            storyMode.setOnClickListener((View view) -> {
                transitionScenes(new Level(getEngine(), R.levels.W02_LXX));
            });
            root.addView(storyMode);

            Button muliplayer = new Button(4,
                    R.strings.MULTIPLAYER,
                    new Size<Float>(35f, 8f),
                    new Vector3f(25, 9, Depth.HUD)
            );
            muliplayer.setTextSize(4.5f);
            muliplayer.setOnClickListener((View view) -> {
                Log.d("Clicked multiplayer!");
            });
            root.addView(muliplayer);

            Button settings = new Button(5,
                    R.strings.SETTINGS,
                    new Size<Float>(35f, 8f),
                    new Vector3f(25, -7, Depth.HUD)
            );
            settings.setTextSize(4.5f);
            settings.setOnClickListener((View view) -> {
                transitionScenes(new SettingsMenu(getEngine()));
            });
            root.addView(settings);

            navigation.insertNode(storyMode, null, settings, null, muliplayer);
            navigation.insertNode(muliplayer, null, storyMode, null, settings);
            navigation.insertNode(settings, null, muliplayer, null, storyMode);
            navigation.select(storyMode);

            // TODO: 3/26/2017 Add grid?

            View vertDivider = new View(6,
                    new Vector3f(0, 0, Depth.BACKGROUND_FRONT),
                    new Size<Float>(0.2f, PROJ_SIZE.height)
            );
            vertDivider.setHorAlignment(View.Alignment.CENTER);
            vertDivider.setVertAlignment(View.Alignment.CENTER);
            vertDivider.setBackground(R.textures.bg.W03_00);
            root.addView(vertDivider);

            View horDivider = new View(7,
                    new Vector3f(0, 0, Depth.BACKGROUND_FRONT),
                    new Size<Float>(PROJ_SIZE.width, 0.2f)
            );
            horDivider.setHorAlignment(View.Alignment.CENTER);
            horDivider.setVertAlignment(View.Alignment.CENTER);
            horDivider.setBackground(R.textures.bg.W03_00);
            root.addView(horDivider);
            // endregion
        }
        views.add(root);
    }

    @Override
    public String description() {
        return "MainMenu";
    }
}