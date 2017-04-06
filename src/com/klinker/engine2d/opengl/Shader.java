package com.klinker.engine2d.opengl;

import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.utils.ShaderUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

/**
 * Shaders are the tapestry of the windows, they're what gets painted on. Vertices define the shape of
 * the object, in triangles, while the fragments define the color and their textures.
 */
public class Shader {

    /**
     * This is a constant id for OpenGL's binding of vertex arrays.
     */
    public static final int VERTEX_ATTR = 0;

    /**
     * This is a constant id for OpenGL's binding of where textures are located.
     */
    public static final int TEXT_COORD_ATTR = 1;


    /**
     * The id is the shader objects program reference to OpenGL.
     */
    private final int id;

    private final String vertex;
    private final String fragment;

    /**
     * This is a cache of all the locations of the shaders that have been created in OpenGL.
     */
    private Map<String, Integer> locationCache = new HashMap<>();

    /**
     * Whether or not this shader object is enabled (binded).
     */
    private boolean enabled = false;


    /**
     * Constructor for a shader.
     * @param vertex The path to the .vert file, ex: "shaders/background.vert".
     * @param fragment The path to the .frag file, ex: "shaders/background.frag".
     */
    public Shader(String vertex, String fragment) {
        this.id = ShaderUtils.load(vertex, fragment);
        this.vertex = vertex;
        this.fragment = fragment;
    }

    /**
     * Gets the OpenGL location for a given shader name.
     * @param name The name of a shader to get (uniform variable in the .vert file).
     * @return The OpenGL location of the shader with {@param name}.
     */
    private int getUniform(String name) {
        if (locationCache.containsKey(name)) {
            return locationCache.get(name);
        }
        int result = glGetUniformLocation(id, name);
        if (result == -1) {
            System.err.println("Could not find uniform variable '" + name + "'.");
        } else {
            locationCache.put(name, result);
        }
        return result;
    }

    /**
     * Sets a texture with a given name and active value.
     * @param name The name of the texture. This matches the uniform sampler2D name in the .frag file.
     * @param value The value matching the active texture in OpenGL.
     */
    public void setUniform1i(String name, int value) {
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        if (!enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        if (!enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vector) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector.globalX(), vector.globalY(), vector.globalZ());
    }

    /**
     * Sets a projection matrix for the shader.
     * @param name The name of the location in OpenGL, matches the uniform mat4 object name
     *             in the .vert file.
     * @param matrix The projection matrix representing how the frag looks
     */
    public void setUniformMatrix4f(String name, Matrix4f matrix) {
        if (!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    /**
     * Sets a color for the shader.
     * @param name The name of the location in OpenGL, matches the uniform vec4 object name
     *             in the .vert file.
     * @param color the color to set.
     */
    public void setUniformColorRGBA(String name, Color color) {
        if (!enabled) enable();
        glUniform4f(getUniform(name),
                color.getRed() / 255f, color.getGreen() / 255f,
                color.getBlue() / 255f, color.getAlpha() / 255f
        );
    }

    public void setUniformVector4f(String name, float x, float y, float z, float w) {
        if (!enabled) enable();
        glUniform4f(getUniform(name), x, y, z, w);
    }

    /**
     * Enables the texture by starting the OpenGL program for rendering this shader.
     */
    public void enable() {
        if (id == -1) {
            // TODO: 2/26/2017 Handle error in loading shader
        }
        glUseProgram(id);
        enabled = true;
    }

    /**
     * Disables the texture by starting a different OpenGL program to render nothing.
     */
    public void disable() {
        glUseProgram(0);
        enabled = false;
    }


    @Override
    public String toString() {
        return "Shader{" +
                "vertex='" + vertex + '\'' +
                ", fragment='" + fragment + '\'' +
                '}';
    }
}
