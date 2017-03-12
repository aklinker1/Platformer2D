package com.klinker.engine2d.graphics;

import com.klinker.engine2d.maths.Matrix4f;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashSet;

public abstract class Scene {

    protected HashSet<Shader> shaders = new HashSet<>();

    public abstract void update();
    public abstract void render();
    public abstract void loadAllShaders();
    public abstract Matrix4f getProjMatrix();

    public void addShaderToScene(Shader shader) {
        shaders.add(shader);
    }

    public void init() {
        loadAllShaders(); // fills the set of shaders to then initialize
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

}
