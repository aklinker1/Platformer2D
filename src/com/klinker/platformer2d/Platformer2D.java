package com.klinker.platformer2d;



import com.klinker.engine2d.Engine;
import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.maths.Vector2f;
import com.klinker.platformer2d.scenes.Level;


public class Platformer2D extends Engine {

    public static final Vector2f TILE_COUNT = new Vector2f(30f, 30f * 9f / 16f);
    public static Size<Integer> SIZE = new Size<>(1280, 720);

    public static void main(String[] args) {
        Platformer2D platformer = new Platformer2D();
        platformer.setStyle(Style.SMOOTH);
        platformer.setSize(SIZE);
        platformer.start();
    }

    public Platformer2D() {
        setScene(new Level("02X.lvl"));
    }

    @Override
    public String getWindowTitle() {
        return "Platformer 2D";
    }

}
