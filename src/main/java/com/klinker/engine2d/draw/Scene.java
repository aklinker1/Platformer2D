package com.klinker.engine2d.draw;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.opengl.Shader;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Scene implements Drawable {

    protected HashSet<Shader> shaders = new HashSet<>();
    private Engine engine;
    private Camera camera;

    public abstract Shader[] loadAllShaders();

    public Scene(Engine engine, Camera camera) {
        this.engine = engine;
        this.camera = camera;
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
        render(camera);
    }

    public void update() {
        update(camera);
        scrollCamera(camera);
    }


    public void transitionScenes(Scene scene/*, Transition transition*/) {
        scene.init();
        this.engine.setScene(scene);
    }

    public Engine getEngine() {
        return this.engine;
    }

    protected void scrollCamera(Camera camera) {}

}