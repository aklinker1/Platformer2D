package com.klinker.platformer2d.utils;



import com.klinker.engine2d.maths.Size;
import com.klinker.engine2d.utils.FileUtils;
import com.klinker.platformer2d.scenes.Level;

import java.io.File;



public class MapCreator {

    private MapCreator() {}

    public static Map read(Level level, File file) {
        assert file.exists();
        String[] lines = FileUtils.loadAsLineArray(file.getPath());
        String[] sizing = lines[0].split("\\s+"); // white space
        int width = 75;
        int height = 28;
        int world = 1;
        try {
            width = Integer.parseInt(sizing[0]);
            height = Integer.parseInt(sizing[1]);
            world = Integer.parseInt(sizing[2]);
        } catch (Exception e) {
        }

        int[][] tiles = new int[height][width];
        for (int y = 1; y < lines.length; y++) {
            String[] row = lines[y].split("\\s+");
            assert row.length >= tiles[y].length;
            for (int x = 0; x < row.length; x++) {
                tiles[height - y][x] = Integer.parseInt(row[x], 16);
            }
        }
        return new Map(world, tiles, new Size<>(width, height), level);
    }

}
