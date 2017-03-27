package com.klinker.engine2d.draw;


import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.opengl.VertexArray;
import com.klinker.engine2d.utils.CollisionBox;


public abstract class Sprite implements Drawable {

    protected Size<Float> size;
    public Vector3f position;
    private VertexArray mesh;
    protected CollisionBox collision;
    protected Texture texture;
    protected Shader shader;
    protected float depth;

    public abstract float getDepth();
    public abstract CollisionBox getCollision();
    public abstract Texture getTexture();
    public abstract Shader getShader();
    protected abstract void setShaderProperties(Shader shader);
    public abstract void update();

    /**
     * Creates a Sprite at a given position. Initializes
     *
     * @param position The initial position of the sprite.
     */
    public Sprite(Vector2f position, Size<Float> size) {
        this.depth = getDepth();
        initializeMesh(position, size);
    }

    protected void reinitializeMesh() {
        initializeMesh(position.get2D(), size);
    }

    protected void initializeMesh(Vector2f position, Size<Float> size) {
        this.size = size;
        this.position = new Vector3f(position.x, position.y, depth);
        this.collision = getCollision();

        this.mesh = new VertexArray(
                new float[] {
                        0, this.size.height, this.position.z,
                        this.size.width, this.size.height, this.position.z,
                        this.size.width, 0, this.position.z,
                        0, 0, this.position.z,
                },
                new byte[] {
                        0, 1, 2,
                        0, 3, 2
                },
                new float[] {
                        0, 0,
                        1, 0,
                        1, 1,
                        0, 1
                }
        );
    }

    public void initTextureAndShader() {
        this.texture = getTexture();
        this.shader = getShader();
    }

    public void render() {
        shader.enable();
        setShaderProperties(shader);
        texture.bind();
        mesh.render();
        texture.unbind();
        shader.disable();
    }

    public Size<Float> getSize() {
        return size;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void setDepth(float depth) {
        this.depth = depth;
        reinitializeMesh();
    }

    public Vector3f getPosition() {
        return position;
    }

}
