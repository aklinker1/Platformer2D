package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.draw.WrapWidthSprite;
import com.klinker.engine2d.math.Matrix4f;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector2f;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.R;
import com.klinker.platformer2d.constants.Depth;

import java.awt.*;
import java.util.LinkedList;

public class TextView extends View {


    public static final Alignment DEFAULT_ALIGNMENT = Alignment.LEFT;
    public static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;
    public static final int DEFAULT_TEXT_SIZE = 1;
    public static final float DEFAULT_IMAGE_MARGIN = 0f;

    public static Shader FONT_SHADER = new Shader(R.shaders.vert.FONT, R.shaders.frag.FONT);


    private String fontDir;
    private int textColor;
    private int textSize;
    private Sprite image;
    private float imageMargin;
    private LinkedList<Glyph> characters;
    private Alignment alignment;


    // TODO: 3/25/2017 Add alignment?
    private enum Alignment {
        CENTER, LEFT, RIGHT
    }


    public TextView(String text, float hieght, Vector2f position, String fontDir) {
        super(position, new Size<Float>(null, hieght));
        this.fontDir = fontDir;

        this.alignment = DEFAULT_ALIGNMENT;
        this.textColor = DEFAULT_TEXT_COLOR;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.image = null;

        size.width = loadCharacters(text);
    }

    public void setText(String text) {
        size.width = loadCharacters(text);
    }

    private float loadCharacters(String text) {
        Log.d("Loading text for '" + text + "'");
        characters = new LinkedList<>();
        float width = 0f;
        for (int i = 0; i < text.length(); i++) {
            int c = text.charAt(i);
            Glyph _char = new Glyph(
                    new Vector2f(position.x + width, position.y),
                    size.height,
                    String.format("%s/%03d.png", fontDir, c)
            );
            characters.addLast(_char);
            width += _char.getSize().width;
        }
        return width;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setImage(Sprite image) {
        this.image = image;
    }

    public void setImageMargin(float imageMargin) {
        this.imageMargin = imageMargin;
    }

    public void render() {
        for (WrapWidthSprite character : characters) character.render();
        if (image != null) image.update();
    }

    public void update() {
        for (WrapWidthSprite character : characters) character.render();
        if (image != null) image.update();
    }

    protected class Glyph extends WrapWidthSprite {

        public Glyph(Vector2f position, float height, String textRes) {
            super(position, height, textRes);
        }

        @Override
        protected void setShaderProperties(Shader shader) {
            // not calling super to take into account the horizontal alignment
            //super.setShaderProperties(shader);
            float xAlign;// = TextView.this.alignment == Alignment.LEFT ? 0 : TextView.this.alignment == Alignment.CENTER ? TextView.this.size.width / 2;
            if (TextView.this.alignment == Alignment.LEFT) xAlign = 0f;
            else if (TextView.this.alignment == Alignment.RIGHT) xAlign = -TextView.this.size.width;
            else xAlign = -TextView.this.size.width / 2f;

            shader.setUniformMatrix4f("view_matrix", Matrix4f.translate(this.position.translate(xAlign, 0, 0)));
            shader.setUniformColorRGBA("font_color", new Color(textColor, true));
        }

        @Override
        public Shader getShader() {
            return FONT_SHADER;
        }

        @Override
        public float getDepth() {
            return Depth.HUD_LOW;
        }
    }

}
