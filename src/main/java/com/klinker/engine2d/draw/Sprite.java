package com.klinker.engine2d.draw;


import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.opengl.VertexArray;
import com.klinker.engine2d.utils.CollisionBox;

public abstract class Sprite implements Drawable {

    protected Size<Float> size;
    public Vector3f position;
    public Vector3f translation;
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
        this.translation = new Vector3f();
        this.cameraOffset = new Vector3f();
        initializeMesh();
    }

    protected void initializeMesh() {
        this.mesh = new VertexArray(
                new float[] {
                        0, this.size.height, this.position.z(),
                        this.size.width, this.size.height, this.position.z(),
                        this.size.width, 0, this.position.z(),
                        0, 0, this.position.z(),
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
    }

    protected void setShaderProperties(Shader shader, Camera camera) {
        shader.setUniformMatrix4f("pos_matrix", Matrix4f.translate(position, translation, getCameraPosition(camera)));
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

    public CollisionBox getCollisionBox() {
        return collision;
    }

    public void setPosition(float x, float y, float z) {
        this.position.setX(x);
        this.position.setY(y);
        this.position.setZ(z);
    }

    public void setSize(Size<Float> size) {
        this.size = size;
        initializeMesh();
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    public Vector3f getCameraPosition(Camera camera) {
        return camera.getPosition();
    }

}
