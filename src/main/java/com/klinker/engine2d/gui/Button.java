package com.klinker.engine2d.gui;


public class Button extends TextView {

    public Button(Scene scene) {
        this(scene, View.DEFAULT_ID);
    }

    public Button(Scene scene, int id) {
        super(scene, id);
        setInnerHorAlign(Alignment.CENTER);
        setInnerVertAlign(Alignment.CENTER);
        setHorAlignment(Alignment.LEFT);
        setVertAlignment(Alignment.BOTTOM);
    }

}
