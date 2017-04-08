package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.engine2d.gui.Button;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.platformer2d.utils.MenuNavigation;

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
    protected void initializeViews(LinkedList<View> views, MenuNavigation<Button> navigation) {
        View background = new View(
                new Vector3f(-PROJ_SIZE.width / 2f, -PROJ_SIZE.height / 2f, Depth.BACKGROUND_MIDDLE),
                new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height)
        );
        background.setBackgroundTexture(R.textures.bg.MAIN_MENU);
        views.add(background);

        TextView version = new TextView(
                R.strings.VERSION,
                3f,
                new Vector3f(PROJ_SIZE.width / 2f - 2f,-PROJ_SIZE.height / 2f + 2f, Depth.HUD),
                R.fonts.ROBOTO
        );
        version.setTextSize(3f);
        version.setTextColor(0x6b000000);
        version.setHorAlignment(TextView.Alignment.RIGHT);
        views.add(version);

        Button storyMode = new Button(
                R.strings.STORY_MODE,
                new Size<Float>(35f, 8f),
                new Vector3f(25f, 25f, Depth.HUD),
                R.fonts.ROBOTO
        );
        storyMode.setTextSize(4.5f);
        storyMode.setOnClickListener((View view) -> {
            transitionScenes(new Level(getEngine(), R.levels.W02_LXX));
        });
        views.add(storyMode);

        Vector3f multiplayerPosition = new Vector3f(storyMode.getPosition());
        multiplayerPosition.increment(0, -12f, 0);
        Button muliplayer = new Button(
                R.strings.MULTIPLAYER,
                new Size<Float>(35f, 8f),
                multiplayerPosition,
                R.fonts.ROBOTO
        );
        muliplayer.setTextSize(4.5f);
        muliplayer.setOnClickListener((View view) -> {
            Log.d("Clicked multiplayer!");
        });
        views.add(muliplayer);

        Vector3f settingsPosition = new Vector3f(muliplayer.getPosition());
        settingsPosition.increment(0, -12f, 0);
        Button settings = new Button(R.strings.SETTINGS,
                new Size<Float>(35f, 8f),
                settingsPosition,
                R.fonts.ROBOTO
        );
        settings.setTextSize(4.5f);
        settings.setOnClickListener((View view) -> {
            Log.d("Clicked Settings!");
        });
        views.add(settings);

        navigation.insertNode(storyMode, null, settings, null, muliplayer);
        navigation.insertNode(muliplayer, null, storyMode, null, settings);
        navigation.insertNode(settings, null, muliplayer, null, storyMode);
        navigation.select(storyMode);

        // TODO: 3/26/2017 Add grid?

        View vertDivider = new View(new Vector3f(0, 0, Depth.BACKGROUND_FRONT), new Size<Float>(0.2f, PROJ_SIZE.height));
        vertDivider.setHorAlignment(View.Alignment.CENTER);
        vertDivider.setVertAlignment(View.Alignment.CENTER);
        vertDivider.setBackgroundTexture(R.textures.bg.B03_00);
        views.add(vertDivider);

        View horDivider = new View(new Vector3f(0, 0, Depth.BACKGROUND_FRONT), new Size<Float>(PROJ_SIZE.width, 0.2f));
        horDivider.setHorAlignment(View.Alignment.CENTER);
        horDivider.setVertAlignment(View.Alignment.CENTER);
        horDivider.setBackgroundTexture(R.textures.bg.B03_00);
        views.add(horDivider);

    }

}