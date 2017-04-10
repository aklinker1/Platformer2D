package com.klinker.engine2d.draw;


import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.opengl.VertexArray;
import com.klinker.engine2d.utils.CollisionBox;

public abstract class Sprite implements Drawable {

    public static boolean showCollisions = false;

    protected Size<Float> size;
    public Vector3f position;
    public Vector3f cameraOffset;
    private VertexArray mesh;
    protected CollisionBox collision;
    protected Texture texture;
    protected Shader shader;


    /**
     * Creates a Sprite at a given position. Initializes
     *
     * @param position The initial position of the sprite.
     */
    protected Sprite(Vector3f position, Size<Float> size, Texture texture, Shader shader) {
        this.position = position;
        this.size = size;
        this.texture = texture;
        this.shader = shader;
        this.cameraOffset = new Vector3f();
        initializeMesh();
    }

    protected void initializeMesh() {
        this.mesh = new VertexArray(
                new float[] {
                        0, this.size.height, this.position.globalZ(),
                        this.size.width, this.size.height, this.position.globalZ(),
                        this.size.width, 0, this.position.globalZ(),
                        0, 0, this.position.globalZ(),
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

    @Override
    public void render(Camera camera) {
        shader.enable();
        setShaderProperties(shader, camera);
        texture.bind();
        mesh.render();
        texture.unbind();
        shader.disable();
        if (showCollisions && collision != null) collision.render(camera);
    }

    protected void setShaderProperties(Shader shader, Camera camera) {
        shader.setUniformMatrix4f("pos_matrix", Matrix4f.translate(position, camera.getPosition()));
    }

    public Size<Float> getSize() {
        return size;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setCollision(CollisionBox collision) {
        this.collision = collision;
    }

    public CollisionBox initializeCollision() {
        return collision;
    }

    public void setPosition(float x, float y, float z) {
        this.position.setLocalX(x);
        this.position.setLocalY(y);
        this.position.setLocalZ(z);
    }

    public void setSize(Size<Float> size) {
        this.size = size;
        initializeMesh();
    }

}
