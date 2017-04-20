package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Layers;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.utils.SparseArray;

public class Camera {

    private Matrix4f projection;
    private Vector3f position;
    private Size<Float> size;
    private Layers layers;
    private int minDepth = -1;
    private int maxDepth = 1;

    /**
     * Creates a camera with the following projection properties.
     *
     * @param projectionSize The total width and height of the projection.
     * @param projectionOrigin The origin location relative to the size.
     *                         Example: (0,0) would yield a positive coordinate system.
     *                         Example: (width/2, height/2) would put the origin in the center
     */
    public Camera(Size<Float> projectionSize, Vector2f projectionOrigin) {
        this.position = new Vector3f();
        this.projection = Matrix4f.orthographic(
                -projectionOrigin.x, projectionSize.width - projectionOrigin.x,
                projectionSize.height - projectionOrigin.y, -projectionOrigin.y,
                minDepth - 1, maxDepth
        );
        this.size = projectionSize;
        this.layers = new Layers(this);
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
        this.position.setLocalX(this.size.width / 2f - position.globalX());
        this.position.setLocalY(this.size.height / 2f - position.globalY());
    }

    public void setPosition(float x, float y) {
        this.position = new Vector3f(x, y, 0);
    }

    public void renderLayers() {
        SparseArray<Layers.DrawableQueue> layerQueues = layers.getLayerQueues();
        Log.d("Items to render [");
        for (int i = 0; i < layerQueues.size(); i++) {
            Log.d("\t" + layerQueues.keyAt(i) + ":\t" + layerQueues.valueAt(i).size());
        }
        Log.d("]");
        for (int i = 0; i < layerQueues.size(); i++) {
            Layers.DrawableQueue queue = layerQueues.valueAt(i);
            while (!queue.isEmpty()) {
                queue.dequeue().render(this);
            }
        }
    }

    public void addToLayers(Drawable drawable, float depth) {
        layers.addItem(drawable, depth);
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getMinDepth() {
        return minDepth;
    }

}
