package com.klinker.engine2d.gui;


import org.xml.sax.Attributes;

public class Button extends TextView {

    public Button(Scene scene) {
        this(scene, null);
    }

    public Button(Scene scene, Attributes attrs) {
        super(scene, attrs);
        setInnerHorAlign(Alignment.CENTER);
        setInnerVertAlign(Alignment.CENTER);
        setHorAlignment(Alignment.LEFT);
        setVertAlignment(Alignment.BOTTOM);
    }

}
