package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Camera;
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


    public static final Alignment DEFAULT_INNER_H_ALIGN = Alignment.LEFT;
    public static final Alignment DEFAULT_INNER_V_ALIGN = Alignment.CENTER;
    public static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;
    public static final int DEFAULT_TEXT_SIZE = 1;

    public static Shader FONT_SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.COLOR_OVERLAY);


    private String fontDir;
    private String text;
    private int textColor;
    private float textSize;
    private LinkedList<Glyph> characters;
    private Alignment innerHAlign;
    private Alignment innerVAlign;
    private boolean wrapWidth = true;


    public TextView(String text, Size<Float> size, Vector3f position, String fontDir) {
        super(position, size);
        this.text = text;
        this.fontDir = fontDir;
        this.wrapWidth = size.width == null;

        this.innerHAlign = DEFAULT_INNER_H_ALIGN;
        this.innerVAlign = DEFAULT_INNER_V_ALIGN;
        this.textColor = DEFAULT_TEXT_COLOR;
        this.textSize = DEFAULT_TEXT_SIZE;

        loadCharacters();
    }

    public TextView(String text, float height, Vector3f position, String fontDir) {
        this(text, new Size<Float>(null, height), position, fontDir);
    }

    public void setText(String text) {
        this.text = text;
        loadCharacters();
    }

    protected void loadCharacters() {
        // repeat twice if we are not wrapping width so it can properly set the innerHAlign
        float width = 0f;
        float height;
        if (innerVAlign == Alignment.CENTER) height = (size.height - textSize) / 2f;
        else if (innerVAlign == Alignment.TOP) height = size.height - textSize;
        else height = 0f;

        for (int j = 0; j < (!wrapWidth ? 2 : 1); j++) {
            characters = new LinkedList<>();
            // handle the innerHAlign of the text on the second time through
            if (!wrapWidth && j == 1) {
                if (innerHAlign == Alignment.CENTER) width = (size.width - width) / 2f;
                else if (innerHAlign == Alignment.RIGHT) width = size.width - width;
                else width = 0f;
            }
            else width = 0f;
            for (int i = 0; i < text.length(); i++) {
                int c = text.charAt(i);
                Glyph _char = new Glyph(
                        position.translate(width, height, 0.001f),
                        textSize,
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

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        loadCharacters();
    }

    public void setInnerHorAlign(Alignment algin) {
        this.innerHAlign = algin;
        loadCharacters();
    }

    public void setInnerVertAlign(Alignment algin) {
        this.innerVAlign = algin;
        loadCharacters();
    }

    @Override
    public void render(Camera camera) {
        super.render(camera);
        for (WrapWidthSprite character : characters) character.render(camera);
    }

    @Override
    public void update(Camera camera) {
        super.update(camera);
        for (WrapWidthSprite character : characters) character.update(camera);
    }

    protected class Glyph extends WrapWidthSprite {

        public Glyph(Vector3f position, float height, String textRes) {
            super(position, height, textRes, FONT_SHADER);
            setTranslation(getHorAlignmentOffset(), getVerAlignmentOffset(), 0);
        }

        @Override
        protected void setShaderProperties(Shader shader, Camera camera) {
            super.setShaderProperties(shader, camera);
            shader.setUniformColorRGBA("color_overlay", new Color(textColor, true));
        }

    }

}
