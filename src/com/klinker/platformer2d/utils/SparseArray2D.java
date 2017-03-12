package com.klinker.platformer2d.utils;

import java.util.Iterator;
import java.util.LinkedList;

public class SparseArray2D<T> {

    private SparseArray<SparseArray<T>> array;

    public SparseArray2D() {
        array = new SparseArray<>();
    }

    public T get(int x, int y) {
        SparseArray<T> row = array.get(y);
        if (row == null) return null;
        return row.get(x);
    }

    public void append(int x, int y, T t) {
        SparseArray<T> row = array.get(y);
        if (row == null) {
            SparseArray<T> newRow = new SparseArray<T>();
            array.append(y, newRow);
            row = newRow;
        }
        row.append(x, t);
    }

    public int getHeight() {
        return array.size();
    }

    public SparseArray<T> getRow(int yi) {
        return array.get(array.keyAt(yi));
    }

}
