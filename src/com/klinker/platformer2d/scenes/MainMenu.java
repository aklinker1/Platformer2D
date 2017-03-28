package com.klinker.platformer2d.scenes;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.WrapWidthSprite;
import com.klinker.engine2d.gui.Button;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.gui.TextView;
import com.klinker.engine2d.gui.View;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;
import com.klinker.engine2d.draw.SimpleSprite;
import com.klinker.platformer2d.utils.MenuNavigation;

import java.util.LinkedList;

public class MainMenu extends Menu {

    private MenuNavigation<Button> navigation;

    public MainMenu(Engine engine) {
        super(engine);
        this.navigation = new MenuNavigation<>();
        navigation.setOnItemSelectedListener((Button oldButton, Button newButton) -> {
            if (oldButton != null) oldButton.setState(View.State.DEFAULT);
            if (newButton != null) newButton.setState(View.State.SELECTED);
        });
    }

    @Override
    public Shader[] loadAllShaders() {
        return new Shader[]{ SimpleSprite.SHADER, WrapWidthSprite.SHADER, TextView.FONT_SHADER };
    }

    @Override
    protected void initializeViews(LinkedList<View> views) {
        View background = new View(
                new Vector2f(-PROJ_SIZE.width / 2f, -PROJ_SIZE.height / 2f),
                Depth.BACKGROUND_BACK,
                new Size<Float>(PROJ_SIZE.width, PROJ_SIZE.height)
        );
        background.setBackgroundTexture(R.textures.bg.MAIN_MENU);
        views.add(background);

        TextView version = new TextView(
                "Version 0.0.1 Alpha",
                3f,
                new Vector2f(PROJ_SIZE.width / 2f - 2f,-PROJ_SIZE.height / 2f + 2f),
                R.fonts.ROBOTO
        );
        version.setTextSize(3f);
        version.setTextColor(0x6b000000);
        version.setHorAlignment(TextView.Alignment.RIGHT);
        views.add(version);

        Button storyMode = new Button(R.strings.STORY_MODE,
                new Size<Float>(35f, 8f),
                new Vector2f(25f, 25f),
                R.fonts.ROBOTO
        );
        storyMode.setTextSize(4.5f);
        storyMode.setState(View.State.SELECTED);
        storyMode.setOnClickListener((View view) -> {
            //transitionScenes(???);
            Log.d("Clicked story mode!");
        });
        views.add(storyMode);

        Button muliplayer = new Button(R.strings.MULTIPLAYER,
                new Size<Float>(35f, 8f),
                storyMode.getPosition().translate(0f, -12f),
                R.fonts.ROBOTO
        );
        muliplayer.setTextSize(4.5f);
        muliplayer.setOnClickListener((View view) -> {
            //transitionScenes(???);
            Log.d("Clicked multiplayer!");
        });
        views.add(muliplayer);

        Button settings = new Button(R.strings.SETTINGS,
                new Size<Float>(35f, 8f),
                muliplayer.getPosition().translate(0f, -12f),
                R.fonts.ROBOTO
        );
        settings.setTextSize(4.5f);
        settings.setOnClickListener((View view) -> {
            //transitionScenes(???);
            Log.d("Clicked Settings!");
        });
        views.add(settings);

        navigation.insertNode(storyMode, null, settings, null, muliplayer);
        navigation.insertNode(muliplayer, null, storyMode, null, settings);
        navigation.insertNode(settings, null, muliplayer, null, storyMode);
        navigation.select(storyMode);

        // TODO: 3/26/2017 Add grid?

        View vertDivider = new View(new Vector2f(0, 0), Depth.BACKGROUND_FRONT, new Size<Float>(0.2f, PROJ_SIZE.height));
        vertDivider.setHorAlignment(View.Alignment.CENTER);
        vertDivider.setVertAlignment(View.Alignment.CENTER);
        vertDivider.setBackgroundTexture(R.textures.bg.B03_00);
        views.add(vertDivider);

        View horDivider = new View(new Vector2f(0, 0), Depth.BACKGROUND_FRONT, new Size<Float>(PROJ_SIZE.width, 0.2f));
        horDivider.setHorAlignment(View.Alignment.CENTER);
        horDivider.setVertAlignment(View.Alignment.CENTER);
        horDivider.setBackgroundTexture(R.textures.bg.B03_00);
        views.add(horDivider);

    }

    @Override
    public void onLeftPress() {
        Button left = navigation.getLeft();
        if (left != null) navigation.select(left);
    }

    @Override
    public void onUpPress() {
        Button up = navigation.getUp();
        if (up != null) navigation.select(up);
    }

    @Override
    public void onRightPress() {
        Button right = navigation.getRight();
        if (right != null) navigation.select(right);
    }

    @Override
    public void onDownPress() {
        Button down = navigation.getDown();
        if (down != null) navigation.select(down);
    }

}
