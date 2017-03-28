package com.klinker.engine2d.draw;

import com.klinker.engine2d.Engine;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.platformer2d.Platformer2D;

import java.util.Arrays;
import java.util.HashSet;

import static org.lwjgl.opengl.GL11.*;

public abstract class Scene implements Drawable {

    protected HashSet<Shader> shaders = new HashSet<>();
    private Engine engine;

    public abstract void update();
    public abstract void render();

    public abstract Shader[] loadAllShaders();
    public abstract Matrix4f getProjMatrix();

    public Scene(Engine engine) {
        this.engine = engine;
    }

    public void init() {
        Shader[] shaders = loadAllShaders(); // fills the set of shaders to then initialize
        this.shaders.addAll(Arrays.asList(shaders));
        initializeShaders();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void initializeShaders() {
        for (Shader shader : shaders) {
            shader.setUniformMatrix4f("proj_matrix", getProjMatrix());
            // The 1 is used to match GL_TEXTURE1 in Engine
            shader.setUniform1i("tex", 1);
        }
    }

    public void transitionScenes(Scene scene/*, Transition transition*/) {
        scene.init();
        this.engine.setScene(scene);
    }

    public Engine getEngine() {
        return this.engine;
    }

}
