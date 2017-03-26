package com.klinker.engine2d.draw;


import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.platformer2d.sprite.SimpleSprite;


public class WrapWidthSprite extends SimpleSprite {

    /**
     * @param position
     * @param height
     * @param textRes  The resource file for the image to be loaded.
     * @see Sprite#Sprite(Vector2f, Size)
     */
    public WrapWidthSprite(Vector2f position, float height, String textRes) {
        super(position, new Size<Float>(height), textRes);
        float ratioWoH = this.texture.getWidthPx() / (float) texture.getHeightPx();
        size.width = height * ratioWoH;
        System.out.printf("\nratio=%f, height=%f, %s\n", ratioWoH, height, size);
        System.out.printf("Texture: width=%d, height=%d\n", texture.getWidthPx(), texture.getHeightPx());
        initializeMesh(this.position.get2D(), size);
        initTextureAndShader();
    }

}
