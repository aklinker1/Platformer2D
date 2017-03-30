package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.platformer2d.constants.Depth;

public class Camera {

    private Matrix4f projection;
    private Vector3f position;

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
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Vector3f getPosition() {
        return position;
    }

}