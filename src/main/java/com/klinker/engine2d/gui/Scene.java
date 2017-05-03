package com.klinker.engine2d.gui;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.platformer2d.Platformer2D;
import com.klinker.platformer2d.utils.Settings;
import org.xml.sax.Attributes;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Scene implements Drawable {

    protected HashSet<Shader> shaders = new HashSet<>();
    private Engine engine;
    private Camera camera;
    private ViewGroup root;

    public abstract Shader[] loadAllShaders();

    public Scene(Engine engine, String layoutRes) {
        this.engine = engine;
        this.root = LayoutInflater.inflate(this, layoutRes);
    }

    public void initializeCamera(Attributes attributes) {
        float height = Float.parseFloat(attributes.getValue("projection_height"));

        float ratio = Platformer2D.getSettings().getFloat(Settings.KEY_ASPECT_RATIO);
        float width = height / ratio;

        String originX = attributes.getValue("projection_origin_x");
        float x;
        if (originX.equals("left")) x = 0;
        else if (originX.equals("center")) x = width / 2f;
        else x = width;

        String originY = attributes.getValue("projection_origin_y");
        float y;
        if (originY.equals("bottom")) y = 0;
        else if (originY.equals("center")) y = height / 2f;
        else y = height;

        camera = new Camera(
                new Size<Float>(width, height),
                new Vector2f(x, y)
        );
    }

    public void init() {
        Shader[] shaders = loadAllShaders(); // fills the set of shaders to then initialize
        this.shaders.addAll(Arrays.asList(shaders));
        initializeShaders();
    }

    private void initializeShaders() {
        for (Shader shader : shaders) {
            shader.setUniformMatrix4f("proj_matrix", camera.getProjection());
            // The 1 is used to match GL_TEXTURE1 in Engine
            shader.setUniform1i("tex", 1);
        }
    }

    public void render() {
        camera.renderLayers();
    }

    public void update() {
        update(camera);
        scrollCamera(camera);
    }

    @Override
    public void update(Camera camera) {
        root.update(camera);
    }

    @Override
    public void render(Camera camera) {
        // do nothing, as there are no sprites in a basic scene to load.
    }

    public void transitionScenes(Scene scene/*, Transition transition*/) {
        scene.init();
        this.engine.setScene(scene);
    }

    public Engine getEngine() {
        return this.engine;
    }

    protected void scrollCamera(Camera camera) {}

    public Camera getCamera() {
        return camera;
    }
}
