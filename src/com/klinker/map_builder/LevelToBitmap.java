package com.klinker.map_builder;



import com.klinker.engine2d.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;



public class LevelToBitmap {

    public static void main(String[] args) {
        System.out.println();
        String[] levelsToConvert = FileUtils.loadAsLineArray("res/scenes/scenes-to-bitmap.txt");
        int count = 0;
        for (String level : levelsToConvert) {
            System.out.print("Converting " + level.substring(level.lastIndexOf('\\') + 1) + "... ");
            LevelToBitmap converter = new LevelToBitmap(level);
            count += converter.convert() ? 1 : 0;
            System.out.println("Finished.");
        }
        System.out.println("\nConverted " + count + " level to images");
    }

    private static final File OUTPUT_PARENT = new File("log/map_builder");
    private static final File TILE_PARENT = new File("res/textures/tiles");
    private static final int TILE_SIZE = 80; // px

    private final File input;
    private final File output;

    private LevelToBitmap(String inputPath) {
        this.input = new File(inputPath);
        this.output = new File(OUTPUT_PARENT, getOutputName(this.input.getName()));
        if (!OUTPUT_PARENT.exists()) OUTPUT_PARENT.mkdirs();
    }

    private String getOutputName(String inputName) {
        if (inputName.contains(".")) {
            inputName = inputName.substring(0, inputName.lastIndexOf('.'));
        }
        return inputName + ".png";
    }

    private boolean convert() {
        try {
            String[] lines = FileUtils.loadAsLineArray(input.getPath());
            String[] sizing = lines[0].split("\\s+"); // white space
            int width = 75;
            int height = 28;
            int world = 1;
            try {
                width = Integer.parseInt(sizing[0]);
                height = Integer.parseInt(sizing[1]);
                world = Integer.parseInt(sizing[2]);
            } catch (Exception e) { }

            int[][] tiles = new int[height][width];
            for (int y = 1; y < lines.length; y++) {
                String[] row = lines[y].split("\\s+");
                assert row.length >= tiles[y].length;
                for (int x = 0; x < row.length; x++) {
                    tiles[y - 1][x] = Integer.parseInt(row[x], 16);
                }
            }

            BufferedImage result = new BufferedImage(width * TILE_SIZE, height * TILE_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics canvas = result.getGraphics();
            File backgroundFile = new File(
                    "res/textures/bg/" + String.format("%02X", world) + ".png"
            );
            if (backgroundFile.exists()) {
                BufferedImage background = ImageIO.read(backgroundFile);
                for (int i = 0; i < width * 80; ) {
                    canvas.drawImage(
                            background, // scales it square and matches the height of the result
                            i, 0, i + result.getHeight(), result.getHeight(),
                            0, 0, background.getWidth(), background.getHeight(),
                            null
                    );
                    i += result.getHeight();
                }
            }

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    File tile;
                    if (tiles[y][x] == 0xFF) {
                        tile = new File("res/textures/characters/hero/body.png");
                    } else {
                        tile = new File(
                                TILE_PARENT,
                                String.format("%02X", world) + "-" + String.format("%02X", tiles[y][x]) + ".png"
                        );
                    }
                    if (tile.exists()) {
                        BufferedImage image = ImageIO.read(tile);
                        canvas.drawImage(image, image.getWidth() * x, image.getHeight() * (y), null);
                    }
                }
            }
            ImageIO.write(result, "png", output);
            return true;
        } catch (Exception e) {
            System.err.println("Error converting level to bitmap");
            e.printStackTrace();
            return false;
        }
    }

}
