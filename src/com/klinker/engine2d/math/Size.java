package com.klinker.engine2d.math;

public class Size<T extends Number> {

    public T width;
    public T height;

    public Size(T width, T height) {
        this.width = width;
        this.height = height;
    }

    public Size(T size) {
        this.width = size;
        this.height = size;
    }

    @Override
    public String toString() {
        return "Size(" + width + "globalX" + height + ")";
    }
}
