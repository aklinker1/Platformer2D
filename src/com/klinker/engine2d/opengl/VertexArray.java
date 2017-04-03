package com.klinker.engine2d.opengl;


import com.klinker.engine2d.utils.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
//import static org.lwjgl.opengl.GL30.*;



/**
 * Stores and accesses OpenGL for graphic shapes.
 */
public class VertexArray {



    /**
     * Vertex Array OpenGL id
     */
    private int vao;

    /**
     * Vertex buffer OpenGL id
     */
    private int vbo;

    /**
     * Index buffer OpenGL id
     */
    private int ibo;

    /**
     * Texture Coordinates OpenGL id
     */
    private int tc;

    /**
     * The number of vertices used. Set from the indices length as those specify the
     * vertices used in each triangle.
     */
    private int count;



    /**
     * @param vertices The vertices that make up the graphic.
     * @param indices The indices that are used in the graphic.
     * @param textureCoordinates The texture coordinates for the graphic.
     */
    public VertexArray(float[] vertices, byte[] indices, float[] textureCoordinates) {
        count = indices.length;

        //vao = glGenVertexArrays();
        //glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.VERTEX_ATTR, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.VERTEX_ATTR);

        tc = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tc);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TEXT_COORD_ATTR, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(Shader.TEXT_COORD_ATTR);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        //glBindVertexArray(0);
    }


    /**
     * Binds the graphic in OpenGL.
     */
    public void bind() {
        //glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
    }

    /**
     * Unbind the graphic in OpenGL.
     */
    public void unbind() {
        //glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Draws the graphic.
     */
    public void draw() {
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
    }

    /**
     * Binds and draws the graphic to render it.
     */
    public void render() {
        bind();
        draw();
        unbind();   // TODO: 2/27/2017 Tutorial does not have this line, do I need it?
    }



}
