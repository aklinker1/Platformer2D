package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;

public class Camera {

    private Matrix4f projection;
    private Vector3f position;
    private Size<Float> size;

    /**
     * Creates a camera with the following projection properties.
     *
     * @param projectionSize The total width and height of the projection.
     * @param projectionOrigin The origin location relative to the size.
     *                         Example: (0,0) would yield a positive coordinate system.
     *                         Example: (width/2, height/2) would put the origin in the center
     */
    public Camera(Size<Float> projectionSize, Vector3f projectionOrigin) {
        this.position = new Vector3f();
        this.projection = Matrix4f.orthographic(
                -projectionOrigin.x, projectionSize.width - projectionOrigin.x,
                projectionSize.height - projectionOrigin.y, -projectionOrigin.y ,
                -1, 1
        );
        this.size = projectionSize;
    }

    /**
     * Copy constructor for deep copy.
     */
    public Camera(Camera camera) {
        this(new Vector3f(camera.position), new Matrix4f(camera.projection));
    }

    private Camera(Vector3f position, Matrix4f projection) {
        this.position = position;
        this.projection = projection;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Size<Float> getSize() {
        return size;
    }

    public void centerXY(Vector3f position) {
        this.position.x = this.size.width / 2f - position.x;
        this.position.y = this.size.height / 2f - position.y;
    }

    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }
}
