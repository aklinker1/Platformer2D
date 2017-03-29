package com.klinker.engine2d.draw;


import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;


public class WrapWidthSprite extends SimpleSprite {

    public WrapWidthSprite(Vector3f position, float height, String textRes, Shader shader) {
        super(position, new Size<Float>(height), textRes, shader);
        float ratioWoH = this.texture.getWidthPx() / (float) texture.getHeightPx();
        setSize(new Size<>(height * ratioWoH, height));
    }

}
