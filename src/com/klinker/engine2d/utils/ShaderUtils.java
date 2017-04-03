package com.klinker.engine2d.utils;


import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;



/**
 * A class that provides helper functions for creating shaders.
 */
public class ShaderUtils {



    /**
     * Private constructor to prevent external instantiation.
     */
    private ShaderUtils() { }



    /**
     * Loads the contents of the vertex and fragment files.
     * @param vertPath The path to the .vert file.
     * @param fragPath The path to the .frag file.
     * @return The OpenGL id for the shader.
     */
    public static int load(String vertPath, String fragPath) {
        String vert = FileUtils.loadAsString(vertPath);
        String frag = FileUtils.loadAsString(fragPath);
        return create(vert, frag);
    }

    /**
     * Creates a OpenGL program to load a shader using a .vert and .frag file.
     * @param vert the contents of a .vert file.
     * @param frag The contents of a .frag file.
     * @return The OpenGL program id for the inputted vertex and fragment file.
     */
    public static int create(String vert, String frag) {
        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, vert);
        glShaderSource(fragID, frag);

        // compile shaders
        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader.");
            System.err.println(glGetShaderInfoLog(vertID));
            return -1;
        }
        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader.");
            System.err.println(glGetShaderInfoLog(fragID));
            return -1;
        }

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertID);
        glDeleteShader(fragID);

        return program;
    }



}
