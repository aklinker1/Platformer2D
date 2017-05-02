package com.klinker.engine2d.gui;


import com.klinker.engine2d.draw.Camera;
import com.klinker.engine2d.draw.WrapWidthSprite;
import com.klinker.engine2d.math.Vector3f;
import com.klinker.engine2d.opengl.Shader;
import com.klinker.platformer2d.R;

import java.awt.*;
import java.util.LinkedList;


public class TextView extends View {

    // region Defaults
    public static final String DEFAULT_FONT_DIR = R.fonts.ROBOTO;
    public static final String DEFAULT_TEXT = "";
    public static final Alignment DEFAULT_INNER_H_ALIGN = Alignment.LEFT;
    public static final Alignment DEFAULT_INNER_V_ALIGN = Alignment.CENTER;
    public static final int DEFAULT_TEXT_SIZE = 20;
    public static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;
    // endregion


    // region Static Constants
    public static Shader FONT_SHADER = new Shader(R.shaders.vert.BASIC, R.shaders.frag.COLOR_OVERLAY);
    // endregion


    // region Member Variables
    private String fontDir = DEFAULT_FONT_DIR;
    private String text = DEFAULT_TEXT;
    private StateObject<Integer> textColor = new StateObject<>(DEFAULT_TEXT_COLOR);
    private float textSize = DEFAULT_TEXT_SIZE;
    private LinkedList<Glyph> characters = new LinkedList<>();
    private Alignment innerHAlign = DEFAULT_INNER_H_ALIGN;
    private Alignment innerVAlign = DEFAULT_INNER_V_ALIGN;
    // endregion


    // region Construction
    public TextView(Scene scene) {
        this(scene, View.DEFAULT_ID);
    }

    public TextView(Scene scene, int id) {
        super(scene, id);
    }
    // endregion


    // region Setters
    @Override
    public View setAlpha(float alpha) {
        for (Glyph glyph : characters) {
            glyph.setAlpha(alpha);
        }
        return super.setAlpha(alpha);
    }

    public TextView setText(String text) {
        this.text = text;
        updateComponents();
        return this;
    }

    public TextView setTextColor(int textColor) {
        this.textColor.setDefault(textColor);
        return this;
    }

    public TextView setTextColor(State state, int textColor) {
        this.textColor.put(state, textColor);
        return this;
    }

    public TextView setTextSize(float textSize) {
        this.textSize = textSize;
        updateComponents();
        return this;
    }

    public TextView setInnerHorAlign(Alignment algin) {
        this.innerHAlign = algin;
        updateComponents();
        return this;
    }

    public TextView setInnerVertAlign(Alignment algin) {
        this.innerVAlign = algin;
        updateComponents();
        return this;
    }

    public TextView setFont(String fontDir) {
        this.fontDir = fontDir;
        updateComponents();
        return this;
    }
    // endregion


    // region Getters
    public String getText() {
        return text;
    }
    // endregion


    // region Helper Methods

    @Override
    public void updateComponents() {
        super.updateComponents();
        loadCharacters();
    }

    private void loadCharacters() {
        // repeat twice if we are not wrapping width so it can properly set the innerHAlign
        float vertOffset = getInnerAlignmentVertOffset(textSize);

        characters = new LinkedList<>();
        float textWidth = 0f;
        float[] widths = new float[text.length()];
        for (int i = 0; i < text.length(); i++) {
            widths[i] = textWidth;
            int c = text.charAt(i);
            Vector3f pos = new Vector3f(0, vertOffset, 0.01f);
            pos.setRelative(getAlignmentOffset());
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

    public float getInnerAlignmentVertOffset(float textHeight) {
        if (innerVAlign == Alignment.TOP) return getSize().height - textHeight;
        else if (innerVAlign == Alignment.CENTER) return (getSize().height - textSize) / 2f;
        else return 0f;
    }

    public float getInnerAlignmentHorOffset(float textWidth) {
        if (innerHAlign == Alignment.RIGHT) return getSize().width - textWidth;
        else if (innerHAlign == Alignment.CENTER) return (getSize().width - textWidth) / 2f;
        else return 0f;
    }
    // endregion


    // region Drawable Overrides
    @Override
    public void update(Camera camera) {
        super.update(camera);
        for (WrapWidthSprite character : characters) character.update(camera);
    }

    @Override
    public String description() {
        return "TextView " + getId();
    }
    // endregion


    // region Glyph
    private class Glyph extends WrapWidthSprite {

        private Glyph(Vector3f position, float height, String textRes) {
            super(position, height, textRes, FONT_SHADER);
        }

        @Override
        protected void setShaderProperties(Shader shader, Camera camera) {
            super.setShaderProperties(shader, camera);
            shader.setUniformColorRGBA("color_overlay", new Color(textColor.get(getState()), true));
        }

        @Override
        public String description() {
            String textureRes = getTextureRes();
            String letter = textureRes.substring(textureRes.lastIndexOf('/') + 1, textureRes.indexOf('.'));
            return "Glyph'" + ((char) Integer.parseInt(letter) + "'");
        }
    }
    // endregion

}
