package com.klinker.engine2d.opengl;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.Drawable;
import com.klinker.engine2d.utils.Log;
import com.klinker.engine2d.utils.Queue;
import com.klinker.platformer2d.utils.SparseArray;

public class Layers {

    public final class DrawableQueue extends Queue<Drawable> {}

    private SparseArray<DrawableQueue> layers;

    public Layers(Camera camera) {
        layers = new SparseArray<>(10);
    }

    public void addItem(Drawable drawable, float depth) {
        if (depth > 1) {
            Log.d("WARNING: %s's depth = %f (max = 1)", drawable.description(), depth);
            depth = 1;
        } else if (depth < 0) {
            Log.d("WARNING: %s's depth = %f (min = 0)", drawable.description(), depth);
            depth = 0;
        }
        DrawableQueue queue = layers.get(indexOf(depth));
        if (queue == null) {
            queue = new DrawableQueue();
            layers.put(indexOf(depth), queue);
        }
        queue.enqueue(drawable);
    }

    public SparseArray<DrawableQueue> getLayerQueues() {
        return layers;
    }

    private int indexOf(float depth) {
        return(int) (depth * 100);
    }

    @Override
    public String toString() {
        String result = "Items to render [\n";
        for (int i = 0; i < layers.size(); i++) {
            result += "\t" + layers.keyAt(i) + " (" + layers.valueAt(i).size() + "):\t";
            for (Drawable drawable : layers.valueAt(i)) {
                result += drawable.description() + ", ";
            }
            result += '\n';
        }
        result += '\n';
        return result;
    }
}
