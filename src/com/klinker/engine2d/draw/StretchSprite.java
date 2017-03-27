package com.klinker.engine2d.draw;

import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;

import java.io.File;

public class StretchSprite {

    private Vector3f position;
    private Size<Float> size;
    private Size<Float> cornerSize;
    private String textRes;
    private Sprite[][] sprites;

    public StretchSprite(String textRes, Vector3f position, Size<Float> size, Size<Float> cornerSize) {
        this.textRes = textRes;
        this.position = position;
        this.size = size;
        this.cornerSize = cornerSize;
        this.sprites = new Sprite[3][3];

        loadSprites();
    }

    private void loadSprites() {
        File dir = new File(textRes);
        float middleWidth = size.width - 2 * cornerSize.width;
        float middleHeight = size.height - 2 * cornerSize.height;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                String texture = String.format("%s/%d%d.png", textRes, x, y);
                Size<Float> size;
                if (x == y && x != 1) {
                    size = cornerSize;
                } else if (x == 0 || x == 2) {
                    size = new Size<Float>(cornerSize.width, middleHeight);
                } else if (y == 0 || y == 2) {
                    size = new Size<Float>(middleWidth, cornerSize.height);
                } else if (x == 1 && y == 1) {
                    size = new Size<Float>(middleWidth, middleHeight);
                }

                Vector3f position = new Vector3f(0, 0, this.position.z);
                if (x == 1) position.x = cornerSize.width;
                else if (x == 2) position.x = cornerSize.width + middleWidth;
                if (y == 1) position.y = cornerSize.height;
                else if (y == 2) position.y = cornerSize.height + middleHeight;

                sprites[x][y] = new SimpleSprite()
            }
        }
    }

}
