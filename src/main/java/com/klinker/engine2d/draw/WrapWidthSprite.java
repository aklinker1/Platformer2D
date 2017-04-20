package com.klinker.engine2d.draw;


import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;


public class WrapWidthSprite extends SimpleSprite {

    public WrapWidthSprite(Vector3f position, float height, String textRes, Shader shader) {
        super(position, new Size<Float>(height), textRes, shader);
        float ratioWoH = getTexture().getWidthPx() / (float) getTexture().getHeightPx();
        Log.d("W/H = " + ratioWoH);
        Log.d("    - Texture = " + textRes);
        Log.d("    - Width = " + getTexture().getWidthPx());
        Log.d("    - Height = " + getTexture().getHeightPx());
        setSize(new Size<>(height * ratioWoH, height));
    }

}
