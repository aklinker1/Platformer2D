package com.klinker.engine2d.inputs;

public interface InputSource {

    Object getSourceId();
    void update();
    boolean isPressed(int button);
    boolean isClicked(int button);

}
