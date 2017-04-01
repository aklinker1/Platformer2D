package com.klinker.engine2d.inputs;

import java.io.Serializable;

public interface InputSource {

    Object getSourceId();
    void update();
    boolean isPressed(int button);

}
