package com.klinker.engine2d.gui;

import com.klinker.engine2d.draw.Sprite;
import com.klinker.engine2d.maths.Vector3f;

import java.io.File;

public class TextView extends View {


    public static final Alignment DEFAULT_ALIGNMENT = Alignment.LEFT;
    public static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;
    public static final int DEFAULT_TEXT_SIZE = 1;
    public static final float DEFAULT_IMAGE_MARGIN = 0f;


    private File fontDir;
    private String text;
    private int textColor;
    private int textSize;
    private Alignment alignment;
    private Sprite image;
    private float imageMargin;
    private Vector3f position;


    private enum Alignment {
        CENTER, LEFT, RIGHT
    }


    public TextView(String textRes, Vector3f position, String fontDir) {
        super(position, null);
        this.text = textRes;
        this.position = position;
        this.fontDir = new File(fontDir);

        this.textColor = DEFAULT_TEXT_COLOR;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.alignment = DEFAULT_ALIGNMENT;
        this.image = null;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setImage(Sprite image) {
        this.image = image;
    }

    public void setImageMargin(float imageMargin) {
        this.imageMargin = imageMargin;
    }

    public void render() {

    }

    public void update() {
        if (image != null) image.update();
    }

}
