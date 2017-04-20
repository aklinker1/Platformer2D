package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.WrapWidthSprite;
import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.platformer2d.R;

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


    public TextView(String text, Size<Float> size, Vector3f position, String fontDir) {
        super(position, size);
        this.text = text;
        this.fontDir = fontDir;

        this.innerHAlign = DEFAULT_INNER_H_ALIGN;
        this.innerVAlign = DEFAULT_INNER_V_ALIGN;
        this.textColor = DEFAULT_TEXT_COLOR;
        this.textSize = DEFAULT_TEXT_SIZE;

        loadCharacters();
    }

    public void setText(String text) {
        this.text = text;
        loadCharacters();
    }

    protected void loadCharacters() {
        // repeat twice if we are not wrapping width so it can properly set the innerHAlign
        float vertOffset = getInnerAlignmentVertOffset(textSize);

        characters = new LinkedList<>();
        float textWidth = 0f;
        float[] widths = new float[text.length()];
        for (int i = 0; i < text.length(); i++) {
            widths[i] = textWidth;
            int c = text.charAt(i);
            Vector3f pos = new Vector3f(0, vertOffset, 0.01f);
            pos.setRelative(this.position);
            Glyph _char = new Glyph(
                    pos,
                    textSize,
                    String.format("%s/%03d.png", fontDir, c)
            );
            characters.addLast(_char);
            textWidth += _char.getSize().width;
        }
        float horOffset = getInnerAlignmentHorOffset(textWidth);
        int i = 0;
        for (Glyph g : characters) {
            g.getPosition().setLocalX(horOffset + widths[i]);
            i++;
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

    public float getInnerAlignmentVertOffset(float textHeight) {
        if (innerVAlign == Alignment.TOP) return size.height - textHeight;
        else if (innerVAlign == Alignment.CENTER) return (size.height - textSize) / 2f;
        else return 0f;
    }

    public float getInnerAlignmentHorOffset(float textWidth) {
        if (innerHAlign == Alignment.RIGHT) return size.width - textWidth;
        else if (innerHAlign == Alignment.CENTER) return (size.width - textWidth) / 2f;
        else return 0f;
    }

    protected class Glyph extends WrapWidthSprite {

        public Glyph(Vector3f position, float height, String textRes) {
            super(position, height, textRes, FONT_SHADER);
        }

        @Override
        protected void setShaderProperties(Shader shader, Camera camera) {
            super.setShaderProperties(shader, camera);
            shader.setUniformColorRGBA("color_overlay", new Color(textColor, true));
        }

    }

}
