package com.klinker.platformer2d.utils;


import com.klinker.engine2d.math.Size;
import com.klinker.engine2d.utils.FileUtils;
import com.klinker.engine2d.utils.Log;
import com.klinker.platformer2d.scenes.Level;
import com.klinker.platformer2d.sprites.Frenemy;
import com.klinker.platformer2d.sprites.Map;
import com.klinker.platformer2d.sprites.players.Player;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;


public class MapReader {

    public static class parameters {
        public static final String POS_X = "x";
        public static final String POS_Y = "y";
        public static final String DIR = "dir";
        public static final String HERO = "hero";
    }


    private MapReader() {}

    public static Map read(Level level, File file) {
        try {
            String content = FileUtils.loadAsString(file.getPath());
            Pattern brackets = Pattern.compile("(\\})[\\s]*");
            LinkedList<String> args = new LinkedList<>();
            args.addAll(Arrays.asList(
                    content.split(brackets.pattern())
            ));

            int width = -1;
            int height = -1;
            int world = 0;
            int[][] tiles = null;
            LinkedList<Frenemy> frenemies = new LinkedList<>();

            while (!args.isEmpty()) {
                String arg = args.removeFirst();
                String name = arg.substring(0, arg.indexOf('{')).trim();
                String data = arg.substring(arg.indexOf('{') + 1).trim();
                String[] dataObjects = data.split("\\s+");
                if ("Map".equals(name)) {
                    for (int i = 0; i < dataObjects.length; i++) {
                        String[] parts = dataObjects[i].split(":");
                        String key = parts[0].toLowerCase();
                        int value = Integer.parseInt(parts[1]);
                        if (key.equals("width")) width = value;
                        else if (key.equals("height")) height = value;
                        else if (key.equals("world")) world = value;
                    }
                    tiles = new int[height][width];
                } else if ("Tiles".equals(name)) {
                    if (tiles != null) readTiles(data, tiles, world);
                    else args.addLast(arg); // move it to the end.
                } else {
                    Frenemy frenemy = Frenemy.fromString(name, dataObjects);
                    if (frenemy != null) {
                        if (frenemy.getClass() != Player.class) frenemies.addLast(frenemy);
                        else frenemies.addFirst(frenemy);
                    }
                }
            }
            return new Map(world, tiles, new Size<>(width, height), frenemies);
        } catch (NegativeArraySizeException e) {
            Log.d("File does not specify a width or height parameter under Map.");
        } catch (Exception e) {
            Log.e("Could not read data from " + file.getPath(), e);
        }
        System.exit(-1);
        return null;
    }

    private static void readTiles(String data, int[][] tiles, int world) {
        String[] rows = data.split("\n");
        for (int y = 0; y < rows.length && y < tiles.length; y++) {
            String[] sCol = rows[y].split("\\s+");
            for (int x = 0; x < tiles[y].length && x < sCol.length; x++) {
                String tile = sCol[x];
                tiles[rows.length - y - 1][x] = Integer.parseInt(tile, 16);
            }
        }
    }


}
