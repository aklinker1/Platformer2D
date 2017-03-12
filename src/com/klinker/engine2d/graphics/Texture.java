package com.klinker.engine2d.graphics;


import com.klinker.engine2d.utils.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The class that handles loading textures from a png or jpg.
 */
public class Texture {

    /**
     * The render style of the textures, set from {@link com.klinker.engine2d.Engine}.
     * If the engine is set to {@link com.klinker.engine2d.Engine.Style#RETRO}, the style should be GL11.GL_NEAREST.
     * If the engine is set to {@link com.klinker.engine2d.Engine.Style#SMOOTH}, the style should be GL11.GL_LINEAR.
     * Defaults to GL_NEAREST
     */
    public static int RENDER_STYLE = GL_NEAREST;

    /**
     * The dimensions of the image.
     */
    private int width;
    private int height;

    /**
     * The OpenGL id for the texture being loaded.
     */
    private int texture;


    /**
     * Constructor for texture being created.
     * @param path The path to the texture file, ex: "res/background.jpeg"
     */
    public Texture(String path) {
        texture = load(path);
    }


    /**
     * Loads the image into OpenGL by reading teh pixels in ARGB, and supplying them to OpenGL in ABGR.
     * @param path The path to the image file.
     * @return The OpenGL texture id.
     */
    private int load(String path) {
        int[] pixels = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // switching from argb to abgr, what openGL uses
        int[] data = new int[width * height];
        for (int i = 0; i < data.length; i++) {
            int a = (pixels[i] & 0xFF000000) >> 24;
            int r = (pixels[i] & 0xFF0000) >> 16;
            int g = (pixels[i] & 0xFF00) >> 8;
            int b = (pixels[i] & 0xFF);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, RENDER_STYLE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, RENDER_STYLE);
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glBindTexture(GL_TEXTURE_2D, 0);

        return result;
    }

    /**
     * Binds the texture in OpenGL.
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
    }

    /**
     * Unbinds the texture in OpenGL.
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}
