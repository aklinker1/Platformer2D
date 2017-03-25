package com.klinker.engine2d.graphics;


import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.engine2d.maths.Vector3f;
import com.klinker.engine2d.utils.CollisionBox;


public abstract class Sprite {

    protected Size<Float> size;
    public Vector3f position;
    private VertexArray mesh;
    protected CollisionBox collision;
    private Texture texture;
    private Shader shader;

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
        this.size = size;
        this.position = new Vector3f(position.x, position.y, getDepth());
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

    public Vector3f getPosition() {
        return position;
    }

}
