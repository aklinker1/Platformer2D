package com.klinker.engine2d.animations;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.opengl.Texture;
import com.klinker.engine2d.utils.Log;
import com.klinker.engine2d.utils.Queue;

import java.io.File;
import java.util.Scanner;

public class FrameAnimation<T extends Sprite> extends Animation {

    private Queue<FrameValue> queue;

    public FrameAnimation(String animDirRes, boolean loop) {
        super(loop);
        this.queue = new Queue<>(loop);
        File animDir = new File(animDirRes);
        try {
            Scanner scanner = new Scanner(new File(animDir, animDir.getName()));
            int frames = scanner.nextInt();
            String name = animDir.getName().substring(0, animDir.getName().indexOf('.'));
            for (int i = 0; i < frames; i++) {
                int duration = scanner.nextInt();
                String texture = String.format("%s/%s-%02d.png", animDirRes, name, i);
                queue.enqueue(new FrameValue(duration, texture));
            }
        } catch (Exception e) {
            Log.d("Error setting up animation.");

        }
    }



    @Override
    protected void update(Sprite sprite, final int frame) {
        FrameValue value = queue.peek();

        // if it's past the duration, move to next frame
        if (frame > value.duration) {
            queue.dequeue();
            resetFrameCounter();
            value = queue.peek();
            sprite.setTexture(value.texture);
        }
    }

    private class FrameValue {
        int duration;
        Texture texture;
        public FrameValue(int duration, String textureRes) {
            this.duration = duration;
            this.texture = new Texture(textureRes);
        }
    }

}
