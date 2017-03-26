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


    public static final Alignment DEFAULT_INNER_ALIGN = Alignment.LEFT;
    public static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;
    public static final int DEFAULT_TEXT_SIZE = 1;
    public static final float DEFAULT_IMAGE_MARGIN = 0f;

    public static Shader FONT_SHADER = new Shader(R.shaders.vert.FONT, R.shaders.frag.FONT);


    private String fontDir;
    private String text;
    private int textColor;
    private int textSize;
    private LinkedList<Glyph> characters;
    private Alignment innerAlign;
    private boolean wrapWidth = true;


    public TextView(String text, Size<Float> size, Vector2f position, String fontDir) {
        super(position, size);
        this.text = text;
        this.fontDir = fontDir;
        this.wrapWidth = size.width == null;

        this.textColor = DEFAULT_TEXT_COLOR;
        this.textSize = DEFAULT_TEXT_SIZE;

        loadCharacters();
        // cal twice to properly set horizontal align
        loadCharacters();
    }

    public TextView(String text, float hieght, Vector2f position, String fontDir) {
        this(text, new Size<Float>(null, hieght), position, fontDir);
    }

    public void setText(String text) {
        this.text = text;
        loadCharacters();
    }

    private void loadCharacters() {
        // repeat twice if we are not wrapping width so it can properly set the innerAlign
        float width = 0f;
        for (int j = 0; j < (!wrapWidth ? 2 : 1); j++) {
            characters = new LinkedList<>();
            // handle the innerAlign of the text on the second time through
            if (!wrapWidth && j == 2) {
                if (innerAlign == Alignment.CENTER) width = (size.width - width) / 2f;
                else if (innerAlign == Alignment.RIGHT) width = size.width - width;
                else width = 0f;
            }
            else width = 0f;
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
            if (wrapWidth) size.width = width;
        }
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setInnerAlign(Alignment algin) {
        this.innerAlign = algin;
        loadCharacters();
    }

    public void render() {
        for (WrapWidthSprite character : characters) character.render();
    }

    public void update() {
        for (WrapWidthSprite character : characters) character.render();
    }

    protected class Glyph extends WrapWidthSprite {

        public Glyph(Vector2f position, float height, String textRes) {
            super(position, height, textRes);
        }

        @Override
        protected void setShaderProperties(Shader shader) {
            // not calling super to take into account the horizontal alignment
            //super.setShaderProperties(shader);
            float xAlign = getAlignmentOffset();// = TextView.this.alignment == Alignment.LEFT ? 0 : TextView.this.alignment == Alignment.CENTER ? TextView.this.size.width / 2;


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
